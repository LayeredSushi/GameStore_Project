package mainGroup.projectCore.repository;

import mainGroup.projectCore.model.Localization;
import org.springframework.data.repository.CrudRepository;

public interface LocalizationRepo extends CrudRepository<Localization, Long> {
}
