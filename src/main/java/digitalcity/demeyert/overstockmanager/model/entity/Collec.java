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
public class Collec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @OneToMany(mappedBy = "collec")
    private List<CollectCard> cardList = new java.util.ArrayList<>();

    public List<CollectCard> getCardList() {
        return cardList;
    }

    public void setCardList(List<CollectCard> cardList) {
        this.cardList = cardList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
