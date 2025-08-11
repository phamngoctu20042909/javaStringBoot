package com.example.web.mapper;

import org.mapstruct.Mapper;
import com.example.web.dto.request.PermissionRequest;
import com.example.web.dto.response.PermissionResponse;
import com.example.web.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
