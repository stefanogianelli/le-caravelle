package entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the pacchetti_predefiniti database table.
 * 
 */
@Entity
@Table(name="pacchetti_predefiniti")
@NamedQuery(name="PacchettiPredefiniti.elenco", query="SELECT p FROM PacchettiPredefiniti p")
public class PacchettiPredefiniti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nome;

	private double prezzo;

	//relazione bidirezionale one-to-many con l'entità DatePartenza	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="pacchettoPredefinito")
	private List<DatePartenza> datePartenza;

	//relazione bidirezionale one-to-many con l'entità Durate
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="pacchettoPredefinito")
	private List<Durate> durate;

	@ManyToOne
	@JoinColumn(name="idHotel")
	private Hotel hotel;
	
	@ManyToMany
	@JoinTable(
			name="mezzi_trasporto_pred"
			, joinColumns={
				@JoinColumn(name="idCollegamento")
				}
			, inverseJoinColumns={
				@JoinColumn(name="idPacchettoPredefinito")
				}
			)
	private List<Collegamenti> collegamenti;
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="pacchetto")
	private List<AttivitaPred> attivita;

	public PacchettiPredefiniti() {
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

	public double getPrezzo() {
		return this.prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public List<DatePartenza> getDatePartenza() {
		return this.datePartenza;
	}

	public void setDatePartenza(List<DatePartenza> datePartenza) {
		this.datePartenza = datePartenza;
	}

	public DatePartenza addDataPartenza(DatePartenza dataPartenza) {
		getDatePartenza().add(dataPartenza);
		dataPartenza.setPacchettoPredefinito(this);

		return dataPartenza;
	}

	public DatePartenza removeDataPartenza(DatePartenza dataPartenza) {
		getDatePartenza().remove(dataPartenza);
		dataPartenza.setPacchettoPredefinito(null);

		return dataPartenza;
	}

	public List<Durate> getDurate() {
		return this.durate;
	}

	public void setDurate(List<Durate> durate) {
		this.durate = durate;
	}

	public Durate addDurata(Durate durata) {
		getDurate().add(durata);
		durata.setPacchettoPredefinito(this);

		return durata;
	}

	public Durate removeDurata(Durate durata) {
		getDurate().remove(durate);
		durata.setPacchettoPredefinito(null);

		return durata;
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
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

	public List<AttivitaPred> getAttivita() {
		return attivita;
	}

	public void setAttivita(List<AttivitaPred> attivita) {
		this.attivita = attivita;
	}
	
	public AttivitaPred addAttivita (AttivitaPred attivita) {
		this.getAttivita().add(attivita);
		attivita.setPacchetto(this);
		
		return attivita;
	}
	
	public AttivitaPred removeAttivita (AttivitaPred attivita) {
		this.getAttivita().remove(attivita);
		attivita.setPacchetto(null);
		
		return attivita;
	}

}