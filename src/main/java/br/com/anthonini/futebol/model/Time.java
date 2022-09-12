package br.com.anthonini.futebol.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.util.StringUtils;

import br.com.anthonini.futebol.repository.listener.TimeEntityListener;

/**
 * 
 * @author Anthonini
 *
 */
@EntityListeners(TimeEntityListener.class)
@Entity
@Table(name = "time")
public class Time implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_time")
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	private String escudo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_arquivo_foto")
	private Arquivo arquivoFoto;
	
	@Transient
	private String urlThumbnailFoto;
	
	public String getFotoOuMock() {
		return arquivoFoto != null && !StringUtils.isEmpty(arquivoFoto.getNome()) ? arquivoFoto.getNome() : "mock.png";
	}

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

	public String getEscudo() {
		return escudo;
	}

	public void setEscudo(String escudo) {
		this.escudo = escudo;
	}

	public Arquivo getArquivoFoto() {
		return arquivoFoto;
	}

	public void setArquivoFoto(Arquivo arquivoFoto) {
		this.arquivoFoto = arquivoFoto;
	}

	public String getUrlThumbnailFoto() {
		return urlThumbnailFoto;
	}

	public void setUrlThumbnailFoto(String urlThumbnailFoto) {
		this.urlThumbnailFoto = urlThumbnailFoto;
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
		Time other = (Time) obj;
		return Objects.equals(id, other.id);
	}
}
