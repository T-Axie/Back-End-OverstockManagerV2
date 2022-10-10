package digitalcity.demeyert.overstockmanager.model.forms;

import lombok.*;

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
}
