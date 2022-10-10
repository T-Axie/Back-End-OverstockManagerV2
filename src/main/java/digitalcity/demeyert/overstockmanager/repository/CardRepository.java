package digitalcity.demeyert.overstockmanager.repository;

import digitalcity.demeyert.overstockmanager.model.entity.Card;
import digitalcity.demeyert.overstockmanager.model.entity.Language;
import digitalcity.demeyert.overstockmanager.model.entity.Rarity;
import digitalcity.demeyert.overstockmanager.model.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findCardsByGameSet (String set);
    Card findCardsByName (String name);
    List<Card> findCardsByRarity (Rarity rarity);

    Card findByCardmarketIdAndFoilAndSignedAndStateAndLanguage(int cardMarketId, boolean foil, boolean signed, State state, Language language);
}
