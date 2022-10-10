package digitalcity.demeyert.overstockmanager.mapper;

import digitalcity.demeyert.overstockmanager.model.dto.UserDTO;
import digitalcity.demeyert.overstockmanager.model.entity.Users;
import digitalcity.demeyert.overstockmanager.model.forms.UserModifyForm;
import digitalcity.demeyert.overstockmanager.model.forms.UsersCreateForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UsersMapper {

    UserDTO fromEntity(Users user);
    Users toEntity(UserDTO userDTO);

    Users toEntity(UsersCreateForm usersCreateForm);
    Users toEntity(UserModifyForm userModifyForm);
}
