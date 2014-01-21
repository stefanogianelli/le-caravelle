package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * The persistent class for the hotel database table.
 * 
 */
@Entity
@NamedQueries ({
	@NamedQuery(name = "Hotel.elenco", query = "SELECT h FROM Hotel h WHERE h.attivo = 1"),
	@NamedQuery(name = "Hotel.getHotel", query = "SELECT h FROM Hotel h WHERE h.nome = :nome AND h.citta.nome = :citta"),
	@NamedQuery(name = "Hotel.elencoPerCitta", query = "SELECT h FROM Hotel h WHERE h.citta.nome = :citta AND h.attivo = 1"),
	@NamedQuery(name = "Hotel.getHotelInCitta", query = "SELECT h FROM Hotel h WHERE h.citta = :citta")
})	
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"nome", "citta"})})
public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String nome;
	
	private int stelle;
	
	private String indirizzo;
	
	private String telefono;
	
	private String website;

	private String email;	

	private double prezzo;

	@ManyToOne
	@JoinColumn(name="citta")
	private Citta citta;
	
	private String immagine;
	
	private int attivo;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
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

	public int getStelle() {
		return this.stelle;
	}

	public void setStelle(int stelle) {
		this.stelle = stelle;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Citta getCitta() {
		return this.citta;
	}

	public void setCitta(Citta citta) {
		this.citta = citta;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public int getAttivo() {
		return attivo;
	}

	public void setAttivo(int attivo) {
		this.attivo = attivo;
	}

}