package com.bispo.projecting.model.mapper;

import com.bispo.projecting.dto.auth.RegisterRequest;
import com.bispo.projecting.dto.user.UserResponse;
import com.bispo.projecting.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(RegisterRequest request);

    @Mapping(target = "projectId", source = "project.id")
    UserResponse toUserResponse(User user);

    default Optional<Long> map(Long value) {
        return Optional.ofNullable(value);
    }

}
