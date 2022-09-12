package br.com.anthonini.futebol.model;

/**
 * 
 * @author Anthonini
 *
 */
public enum Formato {

	GRUPO("Grupo"),
	PONTOS_CORRIDOS("Pontos Corridos"),
	MATA_MATA("Mata Mata");
	
	private String descricao;
	
	private Formato(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
