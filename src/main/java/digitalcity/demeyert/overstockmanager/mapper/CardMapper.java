package digitalcity.demeyert.overstockmanager.mapper;

import digitalcity.demeyert.overstockmanager.model.dto.CardDTO;
import digitalcity.demeyert.overstockmanager.model.entity.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDTO fromEntities(Card card);
    Card toEntities(CardDTO cardDTO);
}
