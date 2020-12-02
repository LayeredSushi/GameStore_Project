package mainGroup.projectCore.repository;

import mainGroup.projectCore.model.GameUpdate;
import org.springframework.data.repository.CrudRepository;

public interface GameUpdateRepo extends CrudRepository<GameUpdate, Long> {
}
