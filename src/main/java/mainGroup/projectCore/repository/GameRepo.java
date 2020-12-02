package mainGroup.projectCore.repository;

import mainGroup.projectCore.model.Customer;
import mainGroup.projectCore.model.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface GameRepo extends CrudRepository<Game, Long> {

    @Query(value = "SELECT DISTINCT game FROM Game game LEFT JOIN FETCH game.purchases")
    Set<Game> findAll();
}
