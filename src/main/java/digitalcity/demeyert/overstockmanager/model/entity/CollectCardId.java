package digitalcity.demeyert.overstockmanager.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectCardId implements Serializable {

    private Long idCollec;
    private Long idCard;

}
