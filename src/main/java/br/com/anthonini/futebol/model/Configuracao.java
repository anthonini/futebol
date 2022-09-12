package br.com.anthonini.futebol.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Anthonini
 *
 */
@Entity
@Table(name = "configuracao")
public class Configuracao implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_configuracao")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Formato formato;
	
	private boolean idaEVolta;
	
	@ManyToOne
	@JoinColumn(name = "id_campeonato", nullable = false)
	private Campeonato campeonato;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Formato getFormato() {
		return formato;
	}

	public void setFormato(Formato formato) {
		this.formato = formato;
	}

	public boolean isIdaEVolta() {
		return idaEVolta;
	}

	public void setIdaEVolta(boolean idaEVolta) {
		this.idaEVolta = idaEVolta;
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuracao other = (Configuracao) obj;
		return Objects.equals(id, other.id);
	}
}
