package entities;

import java.io.Serializable;

import javax.persistence.*;

import enums.TipoCollegamento;

import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the collegamenti database table.
 * 
 */
@Entity
@NamedQueries ({
	@NamedQuery(name = "Collegamenti.elenco", query = "SELECT c FROM Collegamenti c"),
	@NamedQuery(name = "Collegamenti.elencoPerTipo", query = "SELECT c FROM Collegamenti c WHERE c.tipoCollegamento = :tipo"),
	@NamedQuery(name = "Collegamenti.getCollegamento", query = "SELECT c FROM Collegamenti c WHERE c.codice = :codice")
})
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

	@ElementCollection(targetClass = TipoCollegamento.class)
	@Enumerated(EnumType.STRING)
	private List<TipoCollegamento> tipoCollegamento;

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

	public List<TipoCollegamento> getTipoCollegamento() {
		return this.tipoCollegamento;
	}

	public void setTipoCollegamento(List<TipoCollegamento> tipoCollegamento) {
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