package edu.poly.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
