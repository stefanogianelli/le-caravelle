package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the citta database table.
 * 
 */
@Entity
@NamedQuery(name="Citta.findAll", query="SELECT c FROM Citta c")
public class Citta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nazione;

	private String nome;

	private String regione;

	public Citta() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazione() {
		return this.nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRegione() {
		return this.regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

}