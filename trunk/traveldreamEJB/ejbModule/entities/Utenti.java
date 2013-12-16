package entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the utenti database table.
 * 
 */
@Entity
@NamedQueries ({
	@NamedQuery(name = "Utenti.findAll", query = "SELECT u FROM Utenti u"),
	@NamedQuery(name = "Utenti.getUtente", query = "SELECT u FROM Utenti u  WHERE u.email = :email")
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

	//bi-directional many-to-one association to Persone
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="persone_cognome", referencedColumnName="cognome"),
		@JoinColumn(name="persone_dataNascita", referencedColumnName="dataNascita"),
		@JoinColumn(name="persone_nome", referencedColumnName="nome")
		})
	private Persone persona;

	public Utenti() {
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
		this.password = password;
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
	}

}