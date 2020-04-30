package wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import wallet.entity.Customer;
import wallet.entity.Wallet;
@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
	
	}
