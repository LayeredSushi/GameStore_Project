package mainGroup.projectCore.repository;

import mainGroup.projectCore.model.Contract;
import org.springframework.data.repository.CrudRepository;

public interface ContractRepo extends CrudRepository<Contract, Long> {
}
