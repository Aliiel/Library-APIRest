package com.insy2s.Library_APIRest.Models.Mapper;

import com.insy2s.Library_APIRest.Models.DTO.UserDTO;
import com.insy2s.Library_APIRest.Models.Entities.User;

public class UserMapper {

    // méthode permettant de convertir UserDTO en User
    public static UserDTO toUserDTO(User user) {

        if(user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }


    // méthode permettant de convertir User en UserDTO
    public static User toUser(UserDTO userDTO) {
        if(userDTO == null) {
            return null;
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        return user;
    }
}
