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

import edu.poly.shop.domain.Role;



public interface RoleSevice  {

	<S extends Role> List<S> findAll(Example<S> example, Sort sort);

	<S extends Role> List<S> findAll(Example<S> example);

	Role getReferenceById(Integer id);

	Role getById(Integer id);

	void deleteAll();

	void deleteAll(Iterable<? extends Role> entities);

	Role getOne(Integer id);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Integer> ids);

	<S extends Role, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	void delete(Role entity);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	void deleteById(Integer id);

	<S extends Role> boolean exists(Example<S> example);

	long count();

	void deleteAllInBatch(Iterable<Role> entities);

	<S extends Role> long count(Example<S> example);

	void deleteInBatch(Iterable<Role> entities);

	<S extends Role> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Role> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Role> S saveAndFlush(S entity);

	boolean existsById(Integer id);

	void flush();

	<S extends Role> List<S> saveAll(Iterable<S> entities);

	Optional<Role> findById(Integer id);

	List<Role> findAllById(Iterable<Integer> ids);

	List<Role> findAll(Sort sort);

	Page<Role> findAll(Pageable pageable);

	List<Role> findAll();

	<S extends Role> Optional<S> findOne(Example<S> example);

	<S extends Role> S save(S entity);

}
