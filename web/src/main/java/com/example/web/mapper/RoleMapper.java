package com.example.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.web.dto.request.RoleRequest;
import com.example.web.dto.response.RoleResponse;
import com.example.web.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true) // Assuming permissions are handled separately
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role permission);

}
