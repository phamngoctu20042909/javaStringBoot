package com.example.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.web.dto.request.UserCreateRequest;
import com.example.web.dto.request.UserUpdateRequest;
import com.example.web.dto.response.UserResponse;
import com.example.web.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);

    UserResponse toUserResponse(User user);

    void toUpdateUser(@MappingTarget User user, UserUpdateRequest request);
}
