package dtos;

import java.io.Serializable;

public class CittaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String nazione;

	private String nome;

	private String regione;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}
	
}
