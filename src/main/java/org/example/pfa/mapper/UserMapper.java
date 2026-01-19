package org.example.pfa.mapper;

import org.example.pfa.dto.UserCreateDTO;
import org.example.pfa.dto.UserDTO;
import org.example.pfa.entity.Order;
import org.example.pfa.entity.Role;
import org.example.pfa.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .roles(user.getRoleList() != null 
                        ? user.getRoleList().stream().map(Role::getName).toList() 
                        : Collections.emptyList())
                .orderIds(user.getOrderList() != null 
                        ? user.getOrderList().stream().map(Order::getId).toList() 
                        : Collections.emptyList())
                .build();
    }

    public User toEntity(UserCreateDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public List<UserDTO> toDTOList(List<User> users) {
        if (users == null) return Collections.emptyList();
        return users.stream().map(this::toDTO).toList();
    }
}
