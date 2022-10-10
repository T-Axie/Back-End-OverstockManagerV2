package digitalcity.demeyert.overstockmanager.model.dto;

import digitalcity.demeyert.overstockmanager.model.entity.Card;
import lombok.*;

import java.util.List;

@Data
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CollecDTO {

    private Long id;
    private String name;
    private String type;
    private List<CollectCardDTO> cardList = new java.util.ArrayList<>();

    public CollecDTO(String name, String type, List<CollectCardDTO> cardList) {
        this.name = name;
        this.type = type;
        this.cardList = cardList;
    }
}
