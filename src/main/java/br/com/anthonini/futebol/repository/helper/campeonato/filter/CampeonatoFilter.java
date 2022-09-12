package br.com.anthonini.futebol.repository.helper.campeonato.filter;

/**
 * 
 * @author Anthonini
 *
 */
public class CampeonatoFilter {

	private String nome;
	private Integer anoInicio;
	private Integer anoFim;

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
}
