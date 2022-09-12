package br.com.anthonini.futebol.service;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.anthonini.futebol.model.Campeonato;
import br.com.anthonini.futebol.repository.CampeonatoRepository;
import br.com.anthonini.futebol.repository.helper.campeonato.filter.CampeonatoFilter;
import br.com.anthonini.futebol.service.exception.NaoEPossivelRemoverEntidadeException;

@Service
public class CampeonatoService {

	@Autowired
	private CampeonatoRepository repository;
	
	public Page<Campeonato> filtrar(CampeonatoFilter filter, Pageable pageable) {
		return repository.filtrar(filter, pageable);
	}
	
	@Transactional
	public void cadastrar(Campeonato campeonato) {
		repository.save(campeonato);
	}

	@Transactional
	public void remover(Campeonato campeonato) {
		try {
			repository.delete(campeonato);
			repository.flush();
		} catch (PersistenceException | DataIntegrityViolationException e) {
			throw new NaoEPossivelRemoverEntidadeException("Não é possivel remover o campeonato.");
		}
	}
}
