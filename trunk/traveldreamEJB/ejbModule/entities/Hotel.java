package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hotel database table.
 * 
 */
@Entity
@NamedQueries ({
	@NamedQuery(name = "Hotel.elenco", query = "SELECT h FROM Hotel h"),
	@NamedQuery(name = "Hotel.elencoPerCitta", query = "SELECT h FROM Hotel h WHERE h.citta = :citta"),
	@NamedQuery(name = "Hotel.getHotel", query = "SELECT h FROM Hotel h WHERE h.id = :id")
})	
public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String email;

	private String indirizzo;

	private String nome;

	private double prezzo;

	private int stelle;

	private String telefono;

	private String website;

	//bi-directional many-to-one association to Citta
	@ManyToOne
	@JoinColumn(name="citta")
	private Citta citta;

	public Hotel() {
	}

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

}