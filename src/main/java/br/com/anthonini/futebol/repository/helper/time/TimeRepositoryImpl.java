package br.com.anthonini.futebol.repository.helper.time;

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
import br.com.anthonini.futebol.model.Time;
import br.com.anthonini.futebol.repository.helper.time.filter.TimeFilter;

/**
 * 
 * @author Anthonini
 *
 */
public class TimeRepositoryImpl implements TimeRepositoryQueries {

	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	private PaginationUtil<Time> paginationUtil;
	
	@Override
	public Page<Time> filtrar(TimeFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Time> criteriaQuery = builder.createQuery(Time.class);
		Root<Time> time = criteriaQuery.from(Time.class);		
		
		criteriaQuery.where(getWhere(filter, builder, time)).distinct(true);
		
		TypedQuery<Time> query = paginationUtil.prepare(builder, criteriaQuery, time, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}
	
	private Long total(TimeFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Time> time = criteriaQuery.from(Time.class);
		
		criteriaQuery.select(builder.count(time)).where(getWhere(filter, builder, time));
		
		return manager.createQuery(criteriaQuery).getSingleResult();
	}


	private Predicate[] getWhere(TimeFilter filter, CriteriaBuilder builder, Root<Time> time) {
		List<Predicate> where = new ArrayList<>();
		
		if(filter != null) {			
			if (!StringUtils.isEmpty(filter.getNome())) {
				where.add(builder.like(builder.upper(time.get("nome")), "%"+filter.getNome().toUpperCase()+"%"));
			}
		}
		
		return where.stream().toArray(Predicate[]::new);
	}

}
