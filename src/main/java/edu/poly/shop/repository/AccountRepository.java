package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.Account;

import edu.poly.shop.domain.Product;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	Page<Account> findByUsernameContaining(String username, Pageable pageable);

	@Query("SELECT DISTINCT ar.account FROM Authority ar WHERE ar.role.id in (1,2, 3)")
	List<Account> getAdministrators();
}
