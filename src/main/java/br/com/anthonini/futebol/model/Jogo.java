package br.com.anthonini.futebol.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "jogo")
public class Jogo implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_jogo")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_rodada")
	private Rodada rodada;
	
	@Column(name = "data_horario")
	private LocalDateTime dataHorario;
	
	@ManyToOne
	@JoinColumn(name = "id_time_casa")
	private Time timeCasa;
	
	@ManyToOne
	@JoinColumn(name = "id_time_fora")
	private Time timeFora;

	@Column(name = "gols_time_casa")
	private int golsTimeCasa;
	
	@Column(name = "gols_time_fora_casa")
	private int golsTimeForaCasa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Rodada getRodada() {
		return rodada;
	}

	public void setRodada(Rodada rodada) {
		this.rodada = rodada;
	}

	public LocalDateTime getDataHorario() {
		return dataHorario;
	}

	public void setDataHorario(LocalDateTime dataHorario) {
		this.dataHorario = dataHorario;
	}

	public Time getTimeCasa() {
		return timeCasa;
	}

	public void setTimeCasa(Time timeCasa) {
		this.timeCasa = timeCasa;
	}

	public Time getTimeFora() {
		return timeFora;
	}

	public void setTimeFora(Time timeFora) {
		this.timeFora = timeFora;
	}

	public int getGolsTimeCasa() {
		return golsTimeCasa;
	}

	public void setGolsTimeCasa(int golsTimeCasa) {
		this.golsTimeCasa = golsTimeCasa;
	}

	public int getGolsTimeForaCasa() {
		return golsTimeForaCasa;
	}

	public void setGolsTimeForaCasa(int golsTimeForaCasa) {
		this.golsTimeForaCasa = golsTimeForaCasa;
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
		Jogo other = (Jogo) obj;
		return Objects.equals(id, other.id);
	}
}
