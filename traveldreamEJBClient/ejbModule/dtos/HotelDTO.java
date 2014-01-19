package dtos;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class HotelDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message="email non valida")
	private String email;

	@NotEmpty(message = "Inserire un indirizzo")
	private String indirizzo;

	@NotEmpty(message = "Inserire un nome")
	private String nome;

	private double prezzo;

	@Min(value = 1, message = "Il numero di stelle deve essere maggiore o uguale a 1")
	@Max(value = 5, message = "Il numero di stelle deve essere minore o uguale a 5")
	private int stelle;
	
	@Pattern(regexp="^\\+(?:[0-9] ?){6,14}[0-9]$",
			message="Numero di telefono non valido")
	private String telefono;

	@NotEmpty(message = "Inserire l'indirizzo del sito web")
	private String website;

	private CittaDTO citta;
	
	private String immagine;
	
	public HotelDTO () {
		citta = new CittaDTO();
		immagine = new String();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getStelle() {
		return stelle;
	}

	public void setStelle(int stelle) {
		this.stelle = stelle;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public CittaDTO getCitta() {
		return citta;
	}

	public void setCitta(CittaDTO citta) {
		this.citta = citta;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}
}
