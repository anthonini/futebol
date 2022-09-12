package br.com.anthonini.futebol.repository.helper.campeonato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.anthonini.futebol.model.Campeonato;
import br.com.anthonini.futebol.repository.helper.campeonato.filter.CampeonatoFilter;

/**
 * 
 * @author Anthonini
 *
 */
public interface CampeonatoRepositoryQueries {

	public Page<Campeonato> filtrar(CampeonatoFilter filter, Pageable pageable);
}
	