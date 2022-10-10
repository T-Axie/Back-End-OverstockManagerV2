package digitalcity.demeyert.overstockmanager.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String description;
    private String username;
    @OneToMany
    private List<Collec> collec;
    private String URLImage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
