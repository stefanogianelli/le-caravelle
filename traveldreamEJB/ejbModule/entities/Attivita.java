package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the attivita database table.
 * 
 */
@Entity
@NamedQueries ({
	@NamedQuery(name="Attivita.elenco", query="SELECT a FROM Attivita a"),
	@NamedQuery(name="Attivita.getAttivita", query="SELECT a FROM Attivita a WHERE a.destinazione = :destinazione AND a.escursione = :escursione")
})
public class Attivita implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AttivitaPK id;

	private int numPartecipanti;
	
	@ManyToOne
	@JoinColumn(name="idDestinazione")
	private Destinazioni destinazione;
	
	@OneToOne
	@JoinColumn(name="idEscursione")
	private Escursioni escursione;

	public Attivita() {
	}

	public AttivitaPK getId() {
		return this.id;
	}

	public void setId(AttivitaPK id) {
		this.id = id;
	}

	public int getNumPartecipanti() {
		return this.numPartecipanti;
	}

	public void setNumPartecipanti(int numPartecipanti) {
		this.numPartecipanti = numPartecipanti;
	}

	public Destinazioni getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(Destinazioni destinazione) {
		this.destinazione = destinazione;
	}

	public Escursioni getEscursione() {
		return escursione;
	}

	public void setEscursione(Escursioni escursione) {
		this.escursione = escursione;
	}

}