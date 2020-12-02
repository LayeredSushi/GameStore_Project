package mainGroup.projectCore.repository;

import mainGroup.projectCore.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepo extends CrudRepository<Employee, Long> {

    @Query(value = "SELECT DISTINCT emp FROM Employee emp LEFT JOIN FETCH emp.specializations WHERE emp.personId = :#{#id}")
    Optional<Employee> findById(@Param("id") Long id);

    @Query(value = "SELECT DISTINCT emp FROM Employee emp LEFT JOIN FETCH emp.specializations")
    Iterable<Employee> findAll();
}
