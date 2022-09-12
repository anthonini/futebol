package br.com.anthonini.futebol.repository.helper.time;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.anthonini.futebol.model.Time;
import br.com.anthonini.futebol.repository.helper.time.filter.TimeFilter;

/**
 * 
 * @author Anthonini
 *
 */
public interface TimeRepositoryQueries {

	public Page<Time> filtrar(TimeFilter filter, Pageable pageable);
}
	