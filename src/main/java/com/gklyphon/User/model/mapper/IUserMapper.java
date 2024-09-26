
package com.gklyphon.User.model.mapper;

import com.gklyphon.User.model.dto.UserDTO;
import com.gklyphon.User.model.dto.UserRequestDTO;
import com.gklyphon.User.model.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    UserDTO toUserDTO(User user);
    List<UserDTO> toUsersDTO(List<User> user);

    @InheritInverseConfiguration
    User toUser(UserDTO userDTO);

    User toUserFromUserRequestDTO(UserRequestDTO userRequestDTO);
}
