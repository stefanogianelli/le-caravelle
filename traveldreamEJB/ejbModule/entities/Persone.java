package entities;

import java.io.Serializable;
import javax.persistence.*;


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
	@OneToOne(mappedBy="persona")
	private Utenti utenti;

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

	public Utenti getUtenti() {
		return this.utenti;
	}

	public void setUtenti(Utenti utenti) {
		this.utenti = utenti;
	}

}