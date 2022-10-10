package digitalcity.demeyert.overstockmanager.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.io.Serializable;

@Entity
@Data
@Getter @Setter
public class CollectCard implements Serializable {

    @EmbeddedId
    private CollectCardId id = new CollectCardId();

    @MapsId("idCard")
    @ManyToOne
    private Card card;

    @MapsId("idCollec")
    @ManyToOne
    private Collec collec;

    private int qtt;

}
