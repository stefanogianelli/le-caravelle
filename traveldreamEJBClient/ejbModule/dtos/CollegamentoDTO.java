package dtos;

import java.sql.Time;
import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;

import enums.TipoCollegamento;

public class CollegamentoDTO {

	@NotEmpty
	private Date dataPartenza;

	@NotEmpty
	private String destinazione;

	@NotEmpty
	private Time oraArrivo;

	@NotEmpty
	private Time oraPartenza;

	@NotEmpty
	private String origine;
	
	@NotEmpty
	private double prezzo;

	@NotEmpty
	private TipoCollegamento tipoCollegamento;
	
	@NotEmpty
	private CittaDTO cittaArrivo;
	
	@NotEmpty
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

	public TipoCollegamento getTipoCollegamento() {
		return tipoCollegamento;
	}

	public void setTipoCollegamento(TipoCollegamento tipoCollegamento) {
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
