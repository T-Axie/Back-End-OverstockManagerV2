package digitalcity.demeyert.overstockmanager.model.dto;

import digitalcity.demeyert.overstockmanager.model.entity.Collec;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private String description;
    private String username;
    private List<Collec> collec;
    private String URLImage;

    public UserDTO(String description, String username, String URLImage) {
        this.description = description;
        this.username = username;
        this.URLImage = URLImage;
    }
}
