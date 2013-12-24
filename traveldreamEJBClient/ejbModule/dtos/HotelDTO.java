package dtos;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class HotelDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	@NotEmpty
	private String email;

	@NotEmpty
	private String indirizzo;

	@NotEmpty
	private String nome;

	private double prezzo;

	private int stelle;

	@NotEmpty
	private String telefono;

	@NotEmpty
	private String website;

	@NotEmpty
	private CittaDTO citta;
	
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
}
