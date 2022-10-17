package digitalcity.demeyert.overstockmanager.mapper;

import digitalcity.demeyert.overstockmanager.model.dto.CollecDTO;
import digitalcity.demeyert.overstockmanager.model.entity.Collec;
import digitalcity.demeyert.overstockmanager.model.forms.CollecCreateForm;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CollecMapper {

    CollecDTO fromEntities(Collec collec);
    Collec toEntities(CollecDTO collecDTO);

    Collec toEntities(CollecCreateForm collecCreateForm);


}
