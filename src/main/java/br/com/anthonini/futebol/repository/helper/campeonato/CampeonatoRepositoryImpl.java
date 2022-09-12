package br.com.anthonini.futebol.repository.helper.campeonato;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.anthonini.arquitetura.controller.page.PaginationUtil;
import br.com.anthonini.futebol.model.Campeonato;
import br.com.anthonini.futebol.repository.helper.campeonato.filter.CampeonatoFilter;

/**
 * 
 * @author Anthonini
 *
 */
public class CampeonatoRepositoryImpl implements CampeonatoRepositoryQueries {

	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	private PaginationUtil<Campeonato> paginationUtil;
	
	@Override
	public Page<Campeonato> filtrar(CampeonatoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Campeonato> criteriaQuery = builder.createQuery(Campeonato.class);
		Root<Campeonato> campeonato = criteriaQuery.from(Campeonato.class);		
		
		criteriaQuery.where(getWhere(filter, builder, campeonato)).distinct(true);
		
		TypedQuery<Campeonato> query = paginationUtil.prepare(builder, criteriaQuery, campeonato, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}
	
	private Long total(CampeonatoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Campeonato> campeonato = criteriaQuery.from(Campeonato.class);
		
		criteriaQuery.select(builder.count(campeonato)).where(getWhere(filter, builder, campeonato));
		
		return manager.createQuery(criteriaQuery).getSingleResult();
	}


	private Predicate[] getWhere(CampeonatoFilter filter, CriteriaBuilder builder, Root<Campeonato> campeonato) {
		List<Predicate> where = new ArrayList<>();
		
		if(filter != null) {			
			if (!StringUtils.isEmpty(filter.getNome())) {
				where.add(builder.like(builder.upper(campeonato.get("nome")), "%"+filter.getNome().toUpperCase()+"%"));
			}
			if (filter.getAnoInicio() != null) {
				where.add(builder.equal(campeonato.get("anoInicio"), filter.getAnoInicio()));
			}
			if (filter.getAnoFim() != null) {
				where.add(builder.equal(campeonato.get("anoFim"), filter.getAnoFim()));
			}
		}
		
		return where.stream().toArray(Predicate[]::new);
	}

}
