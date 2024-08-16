package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.Role;
import edu.poly.shop.repository.RoleRepository;
import edu.poly.shop.service.RoleSevice;

@Service
public class RoleServiceImpl implements RoleSevice {
    @Autowired
	RoleRepository roleRepository;

	@Override
	public <S extends Role> S save(S entity) {
		return roleRepository.save(entity);
	}

	@Override
	public <S extends Role> Optional<S> findOne(Example<S> example) {
		return roleRepository.findOne(example);
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Page<Role> findAll(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

	@Override
	public List<Role> findAll(Sort sort) {
		return roleRepository.findAll(sort);
	}

	@Override
	public List<Role> findAllById(Iterable<Integer> ids) {
		return roleRepository.findAllById(ids);
	}

	@Override
	public Optional<Role> findById(Integer id) {
		return roleRepository.findById(id);
	}

	@Override
	public <S extends Role> List<S> saveAll(Iterable<S> entities) {
		return roleRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		roleRepository.flush();
	}

	@Override
	public boolean existsById(Integer id) {
		return roleRepository.existsById(id);
	}

	@Override
	public <S extends Role> S saveAndFlush(S entity) {
		return roleRepository.saveAndFlush(entity);
	}

	@Override
	public <S extends Role> List<S> saveAllAndFlush(Iterable<S> entities) {
		return roleRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Role> Page<S> findAll(Example<S> example, Pageable pageable) {
		return roleRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Role> entities) {
		roleRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Role> long count(Example<S> example) {
		return roleRepository.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Role> entities) {
		roleRepository.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return roleRepository.count();
	}

	@Override
	public <S extends Role> boolean exists(Example<S> example) {
		return roleRepository.exists(example);
	}

	@Override
	public void deleteById(Integer id) {
		roleRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		roleRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Role entity) {
		roleRepository.delete(entity);
	}

	@Override
	public <S extends Role, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return roleRepository.findBy(example, queryFunction);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		roleRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		roleRepository.deleteAllInBatch();
	}

	@Override
	public Role getOne(Integer id) {
		return roleRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Role> entities) {
		roleRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		roleRepository.deleteAll();
	}

	@Override
	public Role getById(Integer id) {
		return roleRepository.getById(id);
	}

	@Override
	public Role getReferenceById(Integer id) {
		return roleRepository.getReferenceById(id);
	}

	@Override
	public <S extends Role> List<S> findAll(Example<S> example) {
		return roleRepository.findAll(example);
	}

	@Override
	public <S extends Role> List<S> findAll(Example<S> example, Sort sort) {
		return roleRepository.findAll(example, sort);
	}

}
