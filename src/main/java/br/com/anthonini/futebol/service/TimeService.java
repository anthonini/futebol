package br.com.anthonini.futebol.service;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.anthonini.futebol.model.Time;
import br.com.anthonini.futebol.repository.TimeRepository;
import br.com.anthonini.futebol.repository.helper.time.filter.TimeFilter;
import br.com.anthonini.futebol.service.exception.NaoEPossivelRemoverEntidadeException;

/**
 * 
 * @author Anthonini
 *
 */
@Service
public class TimeService {

	@Autowired
	private TimeRepository repository;
	
	public Page<Time> filtrar(TimeFilter filter, Pageable pageable) {
		return repository.filtrar(filter, pageable);
	}
	
	@Transactional
	public void cadastrar(Time time) {
		repository.save(time);
	}

	@Transactional
	public void remover(Time time) {
		try {
			repository.delete(time);
			repository.flush();
		} catch (PersistenceException | DataIntegrityViolationException e) {
			throw new NaoEPossivelRemoverEntidadeException("Não é possivel remover o time. Time já associado a algum campeonato.");
		}
	}
}
