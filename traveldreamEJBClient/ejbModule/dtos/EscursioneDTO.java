package dtos;

import java.sql.Time;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class EscursioneDTO {

	@NotEmpty
	private String categoria;

	@NotEmpty
	private Date data;

	@NotEmpty
	private int durata;

	@NotEmpty
	private String nome;

	@NotEmpty
	private Time ora;

	@NotEmpty
	private double prezzo;

	@NotEmpty
	private CittaDTO citta;

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getDurata() {
		return durata;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Time getOra() {
		return ora;
	}

	public void setOra(Time ora) {
		this.ora = ora;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public CittaDTO getCitta() {
		return citta;
	}

	public void setCitta(CittaDTO citta) {
		this.citta = citta;
	}
}
