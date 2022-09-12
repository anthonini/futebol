package br.com.anthonini.futebol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.anthonini.futebol.model.Time;
import br.com.anthonini.futebol.repository.helper.time.TimeRepositoryQueries;

/**
 * 
 * @author Anthonini
 *
 */
@Repository
public interface TimeRepository extends JpaRepository<Time, Long>, TimeRepositoryQueries {

}
