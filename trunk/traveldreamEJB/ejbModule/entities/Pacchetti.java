package entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the pacchetti database table.
 * 
 */
@Entity
@NamedQuery(name="Pacchetti.elenco", query="SELECT p FROM Pacchetti p")
public class Pacchetti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nome;

	private int numPartecipanti;

	private double prezzo;

	private String tipoPacchetto;

	//relazione bidirezionale one-to-many con l'entità Destinazioni
	@OneToMany(cascade=CascadeType.ALL, mappedBy="pacchetto")
	private List<Destinazioni> destinazioni;

	@ManyToOne
	@JoinColumn(name="idCittaOrigine")
	private Citta citta;

	@ManyToOne
	@JoinColumn(name="idPred")
	private PacchettiPredefiniti pacchettoPredefinito;

	//relazione bidirezionale many-to-one con l'entità Utenti
	@ManyToOne
	@JoinColumn(name="emailUtente")
	private Utenti utente;

	public Pacchetti() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumPartecipanti() {
		return this.numPartecipanti;
	}

	public void setNumPartecipanti(int numPartecipanti) {
		this.numPartecipanti = numPartecipanti;
	}

	public double getPrezzo() {
		return this.prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public String getTipoPacchetto() {
		return this.tipoPacchetto;
	}

	public void setTipoPacchetto(String tipoPacchetto) {
		this.tipoPacchetto = tipoPacchetto;
	}

	public List<Destinazioni> getDestinazioni() {
		return this.destinazioni;
	}

	public void setDestinazioni(List<Destinazioni> destinazioni) {
		this.destinazioni = destinazioni;
	}

	public Destinazioni addDestinazioni(Destinazioni destinazioni) {
		getDestinazioni().add(destinazioni);
		destinazioni.setPacchetto(this);

		return destinazioni;
	}

	public Destinazioni removeDestinazioni(Destinazioni destinazioni) {
		getDestinazioni().remove(destinazioni);
		destinazioni.setPacchetto(null);

		return destinazioni;
	}

	public Citta getCitta() {
		return this.citta;
	}

	public void setCitta(Citta citta) {
		this.citta = citta;
	}

	public PacchettiPredefiniti getPacchettoPredefinito() {
		return this.pacchettoPredefinito;
	}

	public void setPacchettoPredefinito(PacchettiPredefiniti pacchettoPredefinito) {
		this.pacchettoPredefinito = pacchettoPredefinito;
	}

	public Utenti getUtente() {
		return this.utente;
	}

	public void setUtente(Utenti utente) {
		this.utente = utente;
	}

}