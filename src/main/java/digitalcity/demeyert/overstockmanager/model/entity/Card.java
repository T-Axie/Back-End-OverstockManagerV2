package digitalcity.demeyert.overstockmanager.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private int cardmarketId;
    private String name;
    private String gameSet;
    private Language language;
    private int count;
    private boolean foil;
    private boolean signed;
    private boolean playset;
    private Rarity rarity;
    private State state;
    private String comment;

    public Card(int cardmarketId, String name, String gameSet, Language language, int count, boolean foil, boolean signed, boolean playset, Rarity rarity, State state, String comment) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Card card = (Card) o;
        return id != null && Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}