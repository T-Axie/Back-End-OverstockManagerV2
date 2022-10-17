package digitalcity.demeyert.overstockmanager.repository;

import digitalcity.demeyert.overstockmanager.model.entity.CollectCard;
import digitalcity.demeyert.overstockmanager.model.entity.CollectCardId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectCardRepository extends JpaRepository<CollectCard, CollectCardId> {
    CollectCard findByCollec_Id(Long collec_id);
}
