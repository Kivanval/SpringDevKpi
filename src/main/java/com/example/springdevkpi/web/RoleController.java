package com.example.springdevkpi.web;

import com.example.springdevkpi.service.RoleService;
import com.example.springdevkpi.web.data.transfer.RoleAddPayload;
import com.example.springdevkpi.web.data.transfer.RolePayload;
import com.example.springdevkpi.web.data.transfer.RoleUpdatePayload;
import org.hibernate.validator.constraints.Range;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;
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
    public ResponseEntity<List<RolePayload>> getAll(
            @RequestParam(defaultValue = "0") @Min(0) final int page,
            @RequestParam(defaultValue = "20") @Range(min = 0, max = 1000) final int size,
            @RequestParam(defaultValue = "id") @Pattern(regexp = ROLE_PROPERTIES) final String sortBy) {
        return ResponseEntity.ok(roleService.findAll(page, size, sortBy)
                .stream()
                .map(topic -> modelMapper.map(topic, RolePayload.class))
                .toList());
    }

    @GetMapping("/{name}")
    public ResponseEntity<RolePayload> getById(
            @PathVariable @NotBlank final String name) {
        var optRole = roleService.findByName(name);
        return optRole.map(role -> ResponseEntity.ok(modelMapper.map(role, RolePayload.class)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/")
    public ResponseEntity<RolePayload> addOne(
            @RequestBody @Valid final RoleAddPayload payload) {
        return roleService.create(payload) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<RolePayload> deleteByName(
            @PathVariable @NotBlank final String name) {
        roleService.deleteByName(name);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{name}")
    public ResponseEntity<RolePayload> update(
            @RequestBody @Valid final RoleUpdatePayload payload,
            @PathVariable @NotBlank final String name) {
        return roleService.update(payload, name) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

}
