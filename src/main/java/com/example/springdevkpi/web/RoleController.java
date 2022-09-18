package com.example.springdevkpi.web;

import com.example.springdevkpi.service.RoleService;
import com.example.springdevkpi.web.dto.RolePayload;
import org.hibernate.validator.constraints.Range;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    private final ModelMapper modelMapper;

    @Autowired
    public RoleController(RoleService roleService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    private static final String ROLE_PROPERTIES = "id|name";

    @GetMapping("/")
    public Collection<RolePayload> getRoles(
            @RequestParam(defaultValue = "20") @Range(min = 0, max = 1000) final int size,
            @RequestParam(defaultValue = "0") @Min(0) final int page,
            @RequestParam(defaultValue = "id") @Pattern(regexp = ROLE_PROPERTIES) final String sortBy) {
        return roleService.findAll(PageRequest.of(page, size)
                        .withSort(Sort.by(sortBy)))
                .stream()
                .map(topic -> modelMapper.map(topic, RolePayload.class))
                .collect(Collectors.toSet());
    }

    @GetMapping("/{name}")
    public ResponseEntity<RolePayload> getRoleById(
            @PathVariable final String name) {
        var optRole = roleService.findByName(name);
        return optRole.map(role -> ResponseEntity.ok(modelMapper.map(role, RolePayload.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<RolePayload> addRole(
            @RequestBody @Valid final RolePayload payload) {
        return roleService.create(payload) ?
                ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<RolePayload> deleteRoleByName(
            @PathVariable final String name) {
        roleService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }

}
