package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;


/**
 * The persistent class for the persone database table.
 * 
 */
@Entity
@NamedQuery(name="Persone.elenco", query="SELECT p FROM Persone p")
public class Persone implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PersonePK id;

	@Column(name="documentoIdentita")
	private String documentoIdentita;

	private String telefono;

	//relazione bidirezionale one-to-one con l'entità Utenti
	@OneToOne(mappedBy="persona")
	private Utenti utente;
	
	//relazione bidirezionale many-to-many con l'entità Pacchetti
	@ManyToMany
	@JoinTable(
		name="dati_partecipanti"
		, joinColumns={
			@JoinColumn(name="cognomePart", referencedColumnName="cognome"),
			@JoinColumn(name="dataNascitaPart", referencedColumnName="dataNascita"),
			@JoinColumn(name="nomePart", referencedColumnName="nome")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idPacchetto")
			}
		)
	private List<Pacchetti> pacchetti;

	public Persone() {
		pacchetti = new ArrayList<Pacchetti>();
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

	public Utenti getUtente() {
		return this.utente;
	}

	public void setUtente(Utenti utente) {
		this.utente = utente;
	}

	public List<Pacchetti> getPacchetti() {
		return pacchetti;
	}

	public void setPacchetti(List<Pacchetti> pacchetti) {
		this.pacchetti = pacchetti;
	}
	
	public void addPacchetto (Pacchetti pacchetto) {
		this.getPacchetti().add(pacchetto);
	}
	
	public void removePacchetto (Pacchetti pacchetto) {
		this.getPacchetti().remove(pacchetto);
	}
	
	public String getNome () {
		return this.getId().getNome();
	}
	
	public void setNome (String nome) {
		this.getId().setNome(nome);
	}
	
	public String getCognome () {
		return this.getId().getCognome();
	}
	
	public void setCognome (String cognome) {
		this.getId().setCognome(cognome);
	}
	
	public Date getDataNascita () {
		return this.getId().getDataNascita();
	}
	
	public void setDataNascita (Date data) {
		this.getId().setDataNascita(data);
	}
}