package digitalcity.demeyert.overstockmanager.model.forms;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserModifyForm {
    private String username;
    private String description;
    private String URLImage;
}
