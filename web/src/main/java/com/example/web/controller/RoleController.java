package com.example.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.security.Permission;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.web.dto.request.PermissionRequest;
import com.example.web.dto.request.RoleRequest;
import com.example.web.dto.response.ApiResponse;
import com.example.web.dto.response.PermissionResponse;
import com.example.web.dto.response.RoleResponse;
import com.example.web.service.PermissionService;
import com.example.web.service.RoleService;

@Slf4j
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> createPermission(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .message("Role created successfully")
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAllRole() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .message("Role retrieved successfully")
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<Void> deleteRole(@PathVariable String name) {
        roleService.delete(name);
        return ApiResponse.<Void>builder()
                .message("Role deleted successfully")
                .build();
    }
}
