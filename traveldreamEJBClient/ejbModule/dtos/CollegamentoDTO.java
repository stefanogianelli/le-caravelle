package dtos;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import enums.TipoCollegamento;

public class CollegamentoDTO implements Serializable {

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
	
	/*
	 * Utilizzati per la creazione di un collegamento
	 */
	private String cittaPartenzaText;
	
	private String cittaArrivoText;
	
	/*
	 * Utilizzato per la modifica del collegamento
	 */
	private boolean editable;
	
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

	public String getCittaPartenzaText() {
		return cittaPartenzaText;
	}

	public void setCittaPartenzaText(String cittaPartenzaText) {
		this.cittaPartenzaText = cittaPartenzaText;
	}

	public String getCittaArrivoText() {
		return cittaArrivoText;
	}

	public void setCittaArrivoText(String cittaArrivoText) {
		this.cittaArrivoText = cittaArrivoText;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
}
