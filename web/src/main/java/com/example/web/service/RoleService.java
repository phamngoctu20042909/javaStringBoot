package com.example.web.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.web.dto.request.RoleRequest;
import com.example.web.dto.response.RoleResponse;
import com.example.web.mapper.RoleMapper;
import com.example.web.repository.PermissionRepository;
import com.example.web.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository
                .findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void delete(String name) {
        var role = roleRepository.findById(name)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roleRepository.delete(role);
    }

    public RoleResponse getByName(String name) {
        var role = roleRepository.findById(name)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return roleMapper.toRoleResponse(role);
    }
}
