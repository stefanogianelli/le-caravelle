package dtos;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import enums.TipoCollegamento;

public class CollegamentoDTO implements Serializable, Comparable<CollegamentoDTO> {

	private static final long serialVersionUID = 1L;

	private int codice;

	private Date dataPartenza;

	@NotEmpty
	private String destinazione;

	private Date oraArrivo;

	private Date oraPartenza;

	@NotEmpty
	private String origine;
	
	private double prezzo;

	private TipoCollegamento tipoCollegamento;
	
	private CittaDTO cittaArrivo;
	
	private CittaDTO cittaPartenza;
	
	public CollegamentoDTO () {
		cittaArrivo = new CittaDTO();
		cittaPartenza = new CittaDTO();
	}
	
	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

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

	public Date getOraArrivo() {
		return oraArrivo;
	}

	public void setOraArrivo(Date oraArrivo) {
		this.oraArrivo = oraArrivo;
	}

	public Date getOraPartenza() {
		return oraPartenza;
	}

	public void setOraPartenza(Date oraPartenza) {
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
	
	@Override
	public int compareTo(CollegamentoDTO c) {
		if (this.getDataPartenza().before(c.getDataPartenza())) {
			return -1;
		} else if (this.getDataPartenza().compareTo(c.getDataPartenza()) == 0) {
			if (this.getOraPartenza().before(c.getOraPartenza())) {
				return -1;
			} else if (this.getOraPartenza().compareTo(c.getOraPartenza()) == 0) {
				return 0;
			} else {
				return 1;
			}
		} else {
			return 1;
		}
	}	
	
}
