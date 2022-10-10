package digitalcity.demeyert.overstockmanager.repository;

import digitalcity.demeyert.overstockmanager.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
