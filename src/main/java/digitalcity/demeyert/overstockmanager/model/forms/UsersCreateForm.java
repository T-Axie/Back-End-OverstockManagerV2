package digitalcity.demeyert.overstockmanager.model.forms;

import digitalcity.demeyert.overstockmanager.model.entity.Users;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UsersCreateForm {

    private String email;
    private String password;

    public Users toEntity() {

        Users users = new Users();
        users.setEmail(email);
        users.setPassword(password);
        users.setRoles(List.of("PROFESSIONAL"));
        return users;
    }
}
