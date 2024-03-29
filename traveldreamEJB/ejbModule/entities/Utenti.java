package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.codec.digest.DigestUtils;


/**
 * The persistent class for the utenti database table.
 * 
 */
@Entity
@NamedQueries ({
	@NamedQuery(name = "Utenti.elenco", query = "SELECT u FROM Utenti u")
})
public class Utenti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String email;

	private String password;

	@ElementCollection(targetClass = Gruppi.class)
	@CollectionTable(name = "gruppi",
    	joinColumns = @JoinColumn(name = "email"))	
    @Enumerated(EnumType.STRING)
    @Column(name="gruppo")	
	private List<Gruppi> gruppi;

	//relazione bidirezionale one-to-one con l'entit� Persone
	@OneToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumns({
		@JoinColumn(name="persone_cognome", referencedColumnName="cognome"),
		@JoinColumn(name="persone_dataNascita", referencedColumnName="dataNascita"),
		@JoinColumn(name="persone_nome", referencedColumnName="nome")
		})
	private Persone persona;
	
	//relazione bidirezionale one-to-many con l'entit� Pacchetti
	@OneToMany(mappedBy="utente")
	private List<Pacchetti> pacchetti;

	public Utenti() {
		pacchetti = new ArrayList<Pacchetti>();
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = DigestUtils.sha256Hex(password);
	}

	public List<Gruppi> getGruppi() {
		return this.gruppi;
	}

	public void setGruppi(List<Gruppi> gruppi) {
		this.gruppi = gruppi;
	}

	public Persone getPersona() {
		return this.persona;
	}

	public void setPersona(Persone persona) {
		this.persona = persona;
		persona.setUtente(this);
	}

	public List<Pacchetti> getPacchetti() {
		return pacchetti;
	}

	public void setPacchetti(List<Pacchetti> pacchetti) {
		this.pacchetti = pacchetti;
	}
	
	public Pacchetti addPacchetto (Pacchetti pacchetto) {
		this.getPacchetti().add(pacchetto);
		pacchetto.setUtente(this);
		
		return pacchetto;
	}
	
	public Pacchetti removePacchetto (Pacchetti pacchetto) {
		this.getPacchetti().remove(pacchetti);
		pacchetto.setUtente(null);
		
		return pacchetto;
	}
	
	@Override
	public boolean equals (Object other) {
		if (this.getEmail().equals(((Utenti) other).getEmail()))
			return true;
		else
			return false;
	}

}