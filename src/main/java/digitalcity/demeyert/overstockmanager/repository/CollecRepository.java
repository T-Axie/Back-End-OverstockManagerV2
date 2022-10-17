package digitalcity.demeyert.overstockmanager.repository;

import digitalcity.demeyert.overstockmanager.model.entity.Card;
import digitalcity.demeyert.overstockmanager.model.entity.Collec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollecRepository extends JpaRepository<Collec, Long> {

}
