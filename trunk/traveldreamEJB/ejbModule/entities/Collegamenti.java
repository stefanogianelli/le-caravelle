package entities;

import java.io.Serializable;

import javax.persistence.*;

import enums.TipoCollegamento;

import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the collegamenti database table.
 * 
 */
@Entity
@NamedQueries ({
	@NamedQuery(name = "Collegamenti.elenco", query = "SELECT c FROM Collegamenti c"),
	@NamedQuery(name = "Collegamenti.elencoPerTipo", query = "SELECT c FROM Collegamenti c WHERE c.tipoCollegamento = :tipo"),
	@NamedQuery(name = "Collegamenti.elencoTraDestinazioni", 
		query = "SELECT c FROM Collegamenti c WHERE (c.dataPartenza = :data AND c.cittaPartenza.nome = :partenza AND c.cittaArrivo.nome = :arrivo AND c.tipoCollegamento = :tipo)"),
	@NamedQuery(name = "Collegamenti.getCollegamentoDaData", query = "SELECT c FROM Collegamenti c WHERE c.dataPartenza = :data"),
	@NamedQuery(name = "Collegamenti.getOrigini" , query = "SELECT DISTINCT c.origine FROM Collegamenti c WHERE c.cittaPartenza.nome = :nomeCitta"),
	@NamedQuery(name = "Collegamenti.getDestinazioni" , query = "SELECT DISTINCT c.destinazione FROM Collegamenti c WHERE c.cittaArrivo.nome = :nomeCitta")
})
public class Collegamenti implements Serializable, Comparable<Collegamenti> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codice;
	
	@Enumerated(EnumType.STRING)
	private TipoCollegamento tipoCollegamento;
	
	private String origine;
	
	private String destinazione;

	@Temporal(TemporalType.DATE)
	private Date dataPartenza;	

	private Time oraPartenza;
	
	private Time oraArrivo;		

	private double prezzo;

	@ManyToOne
	@JoinColumn(name="cittaArrivo")
	private Citta cittaArrivo;

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

	public TipoCollegamento getTipoCollegamento() {
		return this.tipoCollegamento;
	}

	public void setTipoCollegamento(TipoCollegamento tipoCollegamento) {
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

	@Override
	public int compareTo(Collegamenti c) {
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