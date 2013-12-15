package dtos;

import java.sql.Time;
import java.util.Date;

public class CollegamentoDTO {

	private Date dataPartenza;

	private String destinazione;

	private Time oraArrivo;

	private Time oraPartenza;

	private String origine;
	
	private double prezzo;

	private String tipoCollegamento;
	
	private CittaDTO cittaArrivo;
	
	private CittaDTO cittaPartenza;

	public Date getDataPartenza() {
		return dataPartenza;
	}

	public void setDataPartenza(Date dataPartenza) {
		this.dataPartenza = dataPartenza;
	}

	public String getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}

	public Time getOraArrivo() {
		return oraArrivo;
	}

	public void setOraArrivo(Time oraArrivo) {
		this.oraArrivo = oraArrivo;
	}

	public Time getOraPartenza() {
		return oraPartenza;
	}

	public void setOraPartenza(Time oraPartenza) {
		this.oraPartenza = oraPartenza;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public String getTipoCollegamento() {
		return tipoCollegamento;
	}

	public void setTipoCollegamento(String tipoCollegamento) {
		this.tipoCollegamento = tipoCollegamento;
	}

	public CittaDTO getCittaArrivo() {
		return cittaArrivo;
	}

	public void setCittaArrivo(CittaDTO cittaArrivo) {
		this.cittaArrivo = cittaArrivo;
	}

	public CittaDTO getCittaPartenza() {
		return cittaPartenza;
	}

	public void setCittaPartenza(CittaDTO cittaPartenza) {
		this.cittaPartenza = cittaPartenza;
	}
	
}
