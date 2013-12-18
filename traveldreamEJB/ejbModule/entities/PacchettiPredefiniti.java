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
	@OneToMany(cascade=CascadeType.ALL, mappedBy="pacchettoPredefinito")
	private List<DatePartenza> datePartenza;

	//relazione bidirezionale one-to-many con l'entità Durate
	@OneToMany(cascade=CascadeType.ALL, mappedBy="pacchettoPredefinito")
	private List<Durate> durate;

	@ManyToOne
	@JoinColumn(name="idHotel")
	private Hotel hotel;

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

	public DatePartenza addDatePartenza(DatePartenza datePartenza) {
		getDatePartenza().add(datePartenza);
		datePartenza.setPacchettoPredefinito(this);

		return datePartenza;
	}

	public DatePartenza removeDatePartenza(DatePartenza datePartenza) {
		getDatePartenza().remove(datePartenza);
		datePartenza.setPacchettoPredefinito(null);

		return datePartenza;
	}

	public List<Durate> getDurate() {
		return this.durate;
	}

	public void setDurate(List<Durate> durate) {
		this.durate = durate;
	}

	public Durate addDurate(Durate durate) {
		getDurate().add(durate);
		durate.setPacchettoPredefinito(this);

		return durate;
	}

	public Durate removeDurate(Durate durate) {
		getDurate().remove(durate);
		durate.setPacchettoPredefinito(null);

		return durate;
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

}