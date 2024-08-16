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

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Authority;
import edu.poly.shop.repository.AuthorityRepository;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
	AuthorityRepository authorityRepository;
	@Autowired
	AccountService accountService;

	@Override
	public <S extends Authority> S save(S entity) {
		return authorityRepository.save(entity);
	}

	@Override
	public <S extends Authority> Optional<S> findOne(Example<S> example) {
		return authorityRepository.findOne(example);
	}

	@Override
	public List<Authority> findAll() {
		return authorityRepository.findAll();
	}

	@Override
	public Page<Authority> findAll(Pageable pageable) {
		return authorityRepository.findAll(pageable);
	}

	@Override
	public List<Authority> findAll(Sort sort) {
		return authorityRepository.findAll(sort);
	}

	@Override
	public List<Authority> findAllById(Iterable<Integer> ids) {
		return authorityRepository.findAllById(ids);
	}

	@Override
	public Optional<Authority> findById(Integer id) {
		return authorityRepository.findById(id);
	}

	@Override
	public <S extends Authority> List<S> saveAll(Iterable<S> entities) {
		return authorityRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		authorityRepository.flush();
	}

	@Override
	public boolean existsById(Integer id) {
		return authorityRepository.existsById(id);
	}

	@Override
	public <S extends Authority> S saveAndFlush(S entity) {
		return authorityRepository.saveAndFlush(entity);
	}

	@Override
	public <S extends Authority> List<S> saveAllAndFlush(Iterable<S> entities) {
		return authorityRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Authority> Page<S> findAll(Example<S> example, Pageable pageable) {
		return authorityRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Authority> entities) {
		authorityRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Authority> long count(Example<S> example) {
		return authorityRepository.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Authority> entities) {
		authorityRepository.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return authorityRepository.count();
	}

	@Override
	public <S extends Authority> boolean exists(Example<S> example) {
		return authorityRepository.exists(example);
	}

	@Override
	public void deleteById(Integer id) {
		authorityRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		authorityRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Authority entity) {
		authorityRepository.delete(entity);
	}

	@Override
	public <S extends Authority, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return authorityRepository.findBy(example, queryFunction);
	}

	

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		authorityRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		authorityRepository.deleteAllInBatch();
	}

	@Override
	public Authority getOne(Integer id) {
		return authorityRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Authority> entities) {
		authorityRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		authorityRepository.deleteAll();
	}

	@Override
	public Authority getById(Integer id) {
		return authorityRepository.getById(id);
	}

	@Override
	public Authority getReferenceById(Integer id) {
		return authorityRepository.getReferenceById(id);
	}

	@Override
	public <S extends Authority> List<S> findAll(Example<S> example) {
		return authorityRepository.findAll(example);
	}

	@Override
	public <S extends Authority> List<S> findAll(Example<S> example, Sort sort) {
		return authorityRepository.findAll(example, sort);
	}

	@Override
	public List<Authority> findAuthoritiesOfAdministrators() {
		List<Account> accounts = accountService.getAdministrators();
		return authorityRepository.authoritiesOf(accounts);
	}

	@Override
	public Authority create(Authority auth) {
		// TODO Auto-generated method stub
		return authorityRepository.save(auth);
	}

	@Override
	public List<Authority> findRoleByUser(String username) {
		// TODO Auto-generated method stub
		return authorityRepository.findRoleByUser(username);
	}

	
	
}
