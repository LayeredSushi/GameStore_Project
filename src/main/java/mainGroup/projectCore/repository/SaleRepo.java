package mainGroup.projectCore.repository;

import mainGroup.projectCore.model.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SaleRepo extends CrudRepository<Sale, Long> {


}
