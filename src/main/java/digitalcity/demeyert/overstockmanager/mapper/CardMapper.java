package digitalcity.demeyert.overstockmanager.mapper;

import digitalcity.demeyert.overstockmanager.model.dto.CardDTO;
import digitalcity.demeyert.overstockmanager.model.entity.Card;
import digitalcity.demeyert.overstockmanager.model.entity.Language;
import digitalcity.demeyert.overstockmanager.model.entity.State;
import org.api.mkm.modele.LightArticle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDTO fromEntities(Card card);
    Card toEntities(CardDTO cardDTO);

    default Card toEntitiesLight(LightArticle lightArticle) {

        if( lightArticle == null )
            return null;

        return Card.builder()
                .cardmarketId(lightArticle.getIdProduct())
                .language(Language.fromLabel( lightArticle.getLanguage().getLanguageName()))
                .foil(lightArticle.isFoil())
                .signed(lightArticle.isSigned())
                .count(lightArticle.getCount())
                .state(State.valueOf(lightArticle.getCondition()))
                .build();
    }
}
