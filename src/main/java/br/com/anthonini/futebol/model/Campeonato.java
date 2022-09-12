package br.com.anthonini.futebol.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Anthonini
 *
 */
@Entity
@Table(name = "campeonato")
public class Campeonato implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_campeonato")
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@NotNull(message = "É obrigatório informar o ano de inicio")
	@Column(name = "ano_inicio")
	private Integer anoInicio;
	
	@NotNull(message = "É obrigatório informar o ano de término")
	@Column(name = "ano_fim")
	private Integer anoFim;
	
	@NotEmpty(message = "É obrigatório adicionar pelo menos um time")
	@ManyToMany
	@JoinTable(name = "campeonato_time", joinColumns = @JoinColumn(name = "id_campeonato"), inverseJoinColumns = @JoinColumn(name = "id_time"))
	private List<Time> times;
	
	@NotEmpty(message = "É obrigatório adicionar pelo menos uma configuração")
	@OneToMany(mappedBy = "campeonato", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Configuracao> configuracoes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getAnoInicio() {
		return anoInicio;
	}

	public void setAnoInicio(Integer anoInicio) {
		this.anoInicio = anoInicio;
	}

	public Integer getAnoFim() {
		return anoFim;
	}

	public void setAnoFim(Integer anoFim) {
		this.anoFim = anoFim;
	}

	public List<Time> getTimes() {
		return times;
	}

	public void setTimes(List<Time> times) {
		this.times = times;
	}

	public List<Configuracao> getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(List<Configuracao> configuracoes) {
		this.configuracoes = configuracoes;
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
		Campeonato other = (Campeonato) obj;
		return Objects.equals(id, other.id);
	}
}
