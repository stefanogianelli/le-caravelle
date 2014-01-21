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
	@NamedQuery(name="Attivita.getAttivita", query="SELECT a FROM Attivita a WHERE a.destinazione = :destinazione AND a.escursione = :escursione"),
	@NamedQuery(name="Attivita.getAttivitaDaId", query="SELECT a FROM Attivita a WHERE a.destinazione.id = :destinazione AND a.escursione.id = :escursione")
})
public class Attivita implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AttivitaPK id;

	private int numPartecipanti;
	
	//relazione bidirezionale many-to-one con l'entità Destinazioni
	@ManyToOne
	@JoinColumn(name="idDestinazione")
	private Destinazioni destinazione;
	
	@ManyToOne
	@JoinColumn(name="idEscursione")
	private Escursioni escursione;

	public Attivita() {
		id = new AttivitaPK();
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
		this.getId().setIdDestinazione(destinazione.getId());
	}

	public Escursioni getEscursione() {
		return escursione;
	}

	public void setEscursione(Escursioni escursione) {
		this.escursione = escursione;
		this.getId().setIdEscursione(escursione.getId());
	}
	
	@Override
	public boolean equals(Object other) {
		if (this.getDestinazione().getId() == ((Attivita)other).getDestinazione().getId() && this.getEscursione().getId() == ((Attivita)other).getEscursione().getId())
			return true;
		else
			return false;		
	}

}