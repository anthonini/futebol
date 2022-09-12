package br.com.anthonini.futebol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.anthonini.futebol.model.Campeonato;
import br.com.anthonini.futebol.repository.helper.campeonato.CampeonatoRepositoryQueries;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long>, CampeonatoRepositoryQueries {

}
