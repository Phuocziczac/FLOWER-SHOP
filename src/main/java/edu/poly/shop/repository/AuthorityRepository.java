package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Authority;
import edu.poly.shop.domain.Role;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
	@Query("SELECT DISTINCT a FROM Authority a WHERE a.account in ?1")
	List<Authority> authoritiesOf(List<Account> accounts);

	@Query("SELECT DISTINCT a FROM Authority a WHERE a.account.username = ?1")
	List<Authority> findRoleByUser(String username);

}
