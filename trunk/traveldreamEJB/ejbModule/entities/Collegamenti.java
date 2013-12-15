package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the collegamenti database table.
 * 
 */
@Entity
@NamedQuery(name="Collegamenti.findAll", query="SELECT c FROM Collegamenti c")
public class Collegamenti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codice;

	@Temporal(TemporalType.DATE)
	private Date dataPartenza;

	private String destinazione;

	private Time oraArrivo;

	private Time oraPartenza;

	private String origine;

	private double prezzo;

	private String tipoCollegamento;

	//bi-directional many-to-one association to Citta
	@ManyToOne
	@JoinColumn(name="cittaArrivo")
	private Citta cittaArrivo;

	//bi-directional many-to-one association to Citta
	@ManyToOne
	@JoinColumn(name="cittaPartenza")
	private Citta cittaPartenza;

	public Collegamenti() {
	}

	public int getCodice() {
		return this.codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public Date getDataPartenza() {
		return this.dataPartenza;
	}

	public void setDataPartenza(Date dataPartenza) {
		this.dataPartenza = dataPartenza;
	}

	public String getDestinazione() {
		return this.destinazione;
	}

	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}

	public Time getOraArrivo() {
		return this.oraArrivo;
	}

	public void setOraArrivo(Time oraArrivo) {
		this.oraArrivo = oraArrivo;
	}

	public Time getOraPartenza() {
		return this.oraPartenza;
	}

	public void setOraPartenza(Time oraPartenza) {
		this.oraPartenza = oraPartenza;
	}

	public String getOrigine() {
		return this.origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public double getPrezzo() {
		return this.prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public String getTipoCollegamento() {
		return this.tipoCollegamento;
	}

	public void setTipoCollegamento(String tipoCollegamento) {
		this.tipoCollegamento = tipoCollegamento;
	}

	public Citta getCittaArrivo() {
		return this.cittaArrivo;
	}

	public void setCittaArrivo(Citta cittaArrivo) {
		this.cittaArrivo = cittaArrivo;
	}

	public Citta getCittaPartenza() {
		return this.cittaPartenza;
	}

	public void setCittaPartenza(Citta cittaPartenza) {
		this.cittaPartenza = cittaPartenza;
	}

}