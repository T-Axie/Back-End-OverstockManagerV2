package digitalcity.demeyert.overstockmanager.model.dto;

import digitalcity.demeyert.overstockmanager.model.entity.State;
import digitalcity.demeyert.overstockmanager.model.entity.Language;
import digitalcity.demeyert.overstockmanager.model.entity.Rarity;
import lombok.*;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CardDTO {

    private Long id;
    private int cardmarketId;
    private String name;
    private String gameSet;
    private Language language;
    private int count;
    private Boolean foil;
    private Boolean signed;
    private Boolean playset;
    private Rarity rarity;
    private State state;
    private String comment;

    public CardDTO(int cardmarketId, String name, String gameSet, Language language, int count, Boolean foil, Boolean signed, Boolean playset, Rarity rarity, State state, String comment) {
        this.cardmarketId = cardmarketId;
        this.name = name;
        this.gameSet = gameSet;
        this.language = language;
        this.count = count;
        this.foil = foil;
        this.signed = signed;
        this.playset = playset;
        this.rarity = rarity;
        this.state = state;
        this.comment = comment;
    }

}
