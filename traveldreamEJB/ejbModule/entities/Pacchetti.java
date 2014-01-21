package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import enums.TipoPacchetto;


/**
 * The persistent class for the pacchetti database table.
 * 
 */
@Entity
@NamedQueries ({
	@NamedQuery(name="Pacchetti.elenco", query="SELECT p FROM Pacchetti p"),
	@NamedQuery(name="Pacchetti.getPacchettiPerTipo", query="SELECT p FROM Pacchetti p WHERE p.utente.email = :utente AND p.tipoPacchetto = :tipo"),
	@NamedQuery(name="Pacchetti.getPacchettiPerNome", query="SELECT p FROM Pacchetti p WHERE p.nome = :nome AND p.utente.email = :utente"),
	@NamedQuery(name="Pacchetti.getPacchettiUtenti", query="SELECT p FROM Pacchetti p WHERE p.tipoPacchetto = :tipo"),
	@NamedQuery(name="Pacchetti.getPacchettiInCitta", query="SELECT p FROM Pacchetti p WHERE p.citta = :citta")
})
public class Pacchetti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nome;

	private int numPartecipanti;

	private double prezzo;

	@Enumerated(EnumType.STRING)
	private TipoPacchetto tipoPacchetto;

	//relazione bidirezionale one-to-many con l'entità Destinazioni
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="pacchetto", orphanRemoval=true)
	private List<Destinazioni> destinazioni;
	
	@ManyToMany(cascade={CascadeType.MERGE})
	@JoinTable(
			name="mezzi_trasporto"
			, joinColumns={
				@JoinColumn(name="idPacchetto")
				}
			, inverseJoinColumns={
				@JoinColumn(name="idCollegamento")
				}
			)
	private List<Collegamenti> collegamenti;

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
	
	//relazione bidirezionale many-to-many con l'entità Persone
	@ManyToMany(cascade={CascadeType.PERSIST}, mappedBy="pacchetti")
	private List<Persone> datiPartecipanti;

	public Pacchetti() {
		destinazioni = new ArrayList<Destinazioni>();
		collegamenti = new ArrayList<Collegamenti>();
		datiPartecipanti = new ArrayList<Persone>();
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

	public TipoPacchetto getTipoPacchetto() {
		return this.tipoPacchetto;
	}

	public void setTipoPacchetto(TipoPacchetto tipoPacchetto) {
		this.tipoPacchetto = tipoPacchetto;
	}

	public List<Destinazioni> getDestinazioni() {
		Collections.sort(destinazioni);
		return this.destinazioni;
	}

	public void setDestinazioni(List<Destinazioni> destinazioni) {
		this.destinazioni = destinazioni;
	}

	public Destinazioni addDestinazione(Destinazioni destinazione) {
		getDestinazioni().add(destinazione);
		destinazione.setPacchetto(this);

		return destinazione;
	}

	public Destinazioni removeDestinazione(Destinazioni destinazione) {
		getDestinazioni().remove(destinazione);

		return destinazione;
	}

	public List<Collegamenti> getCollegamenti() {
		return collegamenti;
	}

	public void setCollegamenti(List<Collegamenti> collegamenti) {
		this.collegamenti = collegamenti;
	}
	
	public Collegamenti addCollegamento (Collegamenti collegamento) {
		this.getCollegamenti().add(collegamento);
		
		return collegamento;
	}
	
	public Collegamenti removeCollegamento (Collegamenti collegamento) {
		this.getCollegamenti().remove(collegamento);
		
		return collegamento;
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

	public List<Persone> getDatiPartecipanti() {
		return datiPartecipanti;
	}

	public void setDatiPartecipanti(List<Persone> datiPartecipanti) {
		this.datiPartecipanti = datiPartecipanti;
	}
	
	public void addPartecipante (Persone datiPartecipante) {
		this.getDatiPartecipanti().add(datiPartecipante);
		datiPartecipante.addPacchetto(this);
	}
	
	public void removePartecipante (Persone datiPartecipante) {
		this.getDatiPartecipanti().remove(datiPartecipante);
	}
}