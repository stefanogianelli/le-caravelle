package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
import javax.persistence.Table;


/**
 * The persistent class for the pacchetti_predefiniti database table.
 * 
 */
@Entity
@Table(name="pacchetti_predefiniti")
@NamedQueries ({
	@NamedQuery(name="PacchettiPredefiniti.elenco", query="SELECT p FROM PacchettiPredefiniti p WHERE p.attivo = 1"),
	@NamedQuery(name="PacchettiPredefiniti.elencoUltimiPacchetti", query="SELECT p FROM PacchettiPredefiniti p WHERE p.attivo = 1 ORDER BY p.id DESC"),
	@NamedQuery(name="PacchettiPredefiniti.getPacchettoDaId", query="SELECT p FROM PacchettiPredefiniti p WHERE p.id = :id"),
	@NamedQuery(name="PacchettiPredefiniti.getPacchettoDaCitta", query="SELECT p FROM PacchettiPredefiniti p WHERE p.hotel.citta.nome = :citta AND p.attivo = 1"),
	@NamedQuery(name="PacchettiPredefiniti.getPacchettoDaHotel", query="SELECT p FROM PacchettiPredefiniti p WHERE p.hotel.id = :hotel")
})
public class PacchettiPredefiniti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nome;

	private double prezzo;
	
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name="citta_origine_pred"
			, joinColumns={
				@JoinColumn(name="idPacchettoPredefinito")
				}
			, inverseJoinColumns={
				@JoinColumn(name="idCitta")
				}
			)
	private List<Citta> cittaPartenza;

	//relazione bidirezionale one-to-many con l'entità DatePartenza	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="pacchettoPredefinito", orphanRemoval=true)
	private List<DatePartenza> datePartenza;

	//relazione bidirezionale one-to-many con l'entità Durate
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="pacchettoPredefinito", orphanRemoval=true)
	private List<Durate> durate;

	@ManyToOne
	@JoinColumn(name="idHotel")
	private Hotel hotel;
	
	@ManyToMany(cascade={CascadeType.MERGE})
	@JoinTable(
			name="mezzi_trasporto_pred"
			, joinColumns={
				@JoinColumn(name="idPacchettoPredefinito")
				}
			, inverseJoinColumns={
				@JoinColumn(name="idCollegamento")
				}
			)
	private List<Collegamenti> collegamenti;
	
	@OneToMany(cascade={CascadeType.MERGE}, mappedBy="pacchetto", orphanRemoval=true)
	private List<AttivitaPred> attivita;
	
	private Integer attivo;

	public PacchettiPredefiniti() {
		cittaPartenza = new ArrayList<Citta>();
		datePartenza = new ArrayList<DatePartenza>();
		durate = new ArrayList<Durate>();
		collegamenti = new ArrayList<Collegamenti>();
		attivita = new ArrayList<AttivitaPred>();
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

	public List<Citta> getCittaPartenza() {
		return cittaPartenza;
	}

	public void setCittaPartenza(List<Citta> cittaPartenza) {
		this.cittaPartenza = cittaPartenza;
	}
	
	public Citta addCitta (Citta citta) {
		this.getCittaPartenza().add(citta);
		
		return citta;
	}
	
	public Citta removeCitta (Citta citta) {
		this.getCittaPartenza().remove(citta);
		
		return citta;
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
		getDurate().remove(durata);

		return durata;
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public List<Collegamenti> getCollegamenti() {
		Collections.sort(collegamenti);
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
		
		return attivita;
	}

	public Integer getAttivo() {
		return attivo;
	}

	public void setAttivo(Integer attivo) {
		this.attivo = attivo;
	}

}