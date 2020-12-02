package mainGroup.projectCore.repository;

import mainGroup.projectCore.model.Purchase;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepo extends CrudRepository<Purchase, Long> {
}
