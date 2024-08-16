package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Authority;


public interface AuthorityService {

	<S extends Authority> List<S> findAll(Example<S> example, Sort sort);

	<S extends Authority> List<S> findAll(Example<S> example);

	Authority getReferenceById(Integer id);

	Authority getById(Integer id);

	void deleteAll();

	void deleteAll(Iterable<? extends Authority> entities);

	Authority getOne(Integer id);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Integer> ids);

	<S extends Authority, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	void delete(Authority entity);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	void deleteById(Integer id);

	<S extends Authority> boolean exists(Example<S> example);

	long count();

	void deleteAllInBatch(Iterable<Authority> entities);

	<S extends Authority> long count(Example<S> example);

	void deleteInBatch(Iterable<Authority> entities);

	<S extends Authority> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Authority> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Authority> S saveAndFlush(S entity);

	boolean existsById(Integer id);

	void flush();

	<S extends Authority> List<S> saveAll(Iterable<S> entities);

	Optional<Authority> findById(Integer id);

	List<Authority> findAllById(Iterable<Integer> ids);

	List<Authority> findAll(Sort sort);

	Page<Authority> findAll(Pageable pageable);

	List<Authority> findAll();

	<S extends Authority> Optional<S> findOne(Example<S> example);

	<S extends Authority> S save(S entity);

	List<Authority> findAuthoritiesOfAdministrators();

	Authority create(Authority auth);

	List<Authority> findRoleByUser(String username);



	

	

}
