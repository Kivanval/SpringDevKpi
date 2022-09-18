package com.example.springdevkpi.service.security;

import com.example.springdevkpi.data.RoleRepository;
import com.example.springdevkpi.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    public void setUserRepository(UserRepository userService, RoleRepository roleRepository) {
        this.userRepository = userService;
        this.roleRepository = roleRepository;
    }

    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        Set<SimpleGrantedAuthority> authorities = Stream.concat(
                        Stream.of(user.getRole()),
                        roleRepository.findByRankLessThan(user.getRole().getRank()).stream()).
                map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.getName()))
                .collect(Collectors.toSet());
        return new UserDetailsImpl(user, authorities);
    }
}
