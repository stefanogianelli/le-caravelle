package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The primary key class for the persone database table.
 * 
 */
@Embeddable
public class PersonePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String nome;

	private String cognome;

	@Temporal(TemporalType.DATE)
	private Date dataNascita;

	public PersonePK() {
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public Date getDataNascita() {
		return this.dataNascita;
	}
	
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PersonePK)) {
			return false;
		}
		PersonePK castOther = (PersonePK)other;
		return 
			this.nome.equals(castOther.nome)
			&& this.cognome.equals(castOther.cognome)
			&& this.dataNascita.equals(castOther.dataNascita);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.nome.hashCode();
		hash = hash * prime + this.cognome.hashCode();
		hash = hash * prime + this.dataNascita.hashCode();
		
		return hash;
	}
}