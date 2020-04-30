package wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import wallet.entity.Customer;
import wallet.entity.Wallet;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Integer>{

    @Modifying
    @Query(value = "UPDATE customer_master  set wallet_id =:wallet where  id=:userId",
            nativeQuery = true)
    void updateUser(@Param("wallet") Wallet wallet, @Param("userId") Integer userId);
    
   }
