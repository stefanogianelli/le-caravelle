package dtos;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import enums.CategoriaEscursione;

public class EscursioneDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	@NotEmpty
	private CategoriaEscursione categoria;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CategoriaEscursione getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaEscursione categoria) {
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
