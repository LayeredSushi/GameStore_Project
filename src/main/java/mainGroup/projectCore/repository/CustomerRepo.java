package mainGroup.projectCore.repository;

import mainGroup.projectCore.model.Customer;
import mainGroup.projectCore.model.Purchase;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CustomerRepo extends CrudRepository<Customer, Long> {

    @Query(value = "SELECT DISTINCT customer FROM Customer customer LEFT JOIN FETCH customer.purchases WHERE customer.personId = :#{#id}")
    Optional<Customer> findById(@Param("id") Long id);

    @Query(value = "SELECT DISTINCT customer FROM Customer customer LEFT JOIN FETCH customer.purchases")
    Iterable<Customer> findAll();

    @Query(value = "SELECT DISTINCT purchase FROM Purchase purchase LEFT JOIN FETCH purchase.game WHERE purchase.purchaseId = :#{#id}")
    Optional<Purchase> ownedGame(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Customer customer SET customer.billingAddress = :#{#billing} WHERE customer.personId = :#{#id}")
    void updateBillingField(@Param("billing") String billing, @Param("id") Long id);

}
