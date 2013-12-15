package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the persone database table.
 * 
 */
@Entity
@NamedQuery(name="Persone.findAll", query="SELECT p FROM Persone p")
public class Persone implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PersonePK id;

	@Column(name="documentoIdentita")
	private String documentoIdentita;

	private String telefono;

	//bi-directional many-to-one association to Utenti
	@OneToMany(mappedBy="persona")
	private List<Utenti> utenti;

	public Persone() {
	}

	public PersonePK getId() {
		return this.id;
	}

	public void setId(PersonePK id) {
		this.id = id;
	}

	public String getDocumentoIdentita() {
		return this.documentoIdentita;
	}

	public void setDocumentoIdentita(String documentoIdentita) {
		this.documentoIdentita = documentoIdentita;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Utenti> getUtenti() {
		return this.utenti;
	}

	public void setUtenti(List<Utenti> utenti) {
		this.utenti = utenti;
	}

	public Utenti addUtenti(Utenti utenti) {
		getUtenti().add(utenti);
		utenti.setPersona(this);

		return utenti;
	}

	public Utenti removeUtenti(Utenti utenti) {
		getUtenti().remove(utenti);
		utenti.setPersona(null);

		return utenti;
	}

}