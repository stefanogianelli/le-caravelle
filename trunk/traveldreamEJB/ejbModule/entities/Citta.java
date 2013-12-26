package entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the citta database table.
 * 
 */
@Entity
@NamedQueries ({
	@NamedQuery(name = "Citta.elenco", query = "SELECT c FROM Citta c"),
	@NamedQuery(name = "Citta.getCitta", query = "SELECT c FROM Citta c WHERE c.nome = :nome")
})
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"nome", "nazione"})})
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