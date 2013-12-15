package entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the amici database table.
 * 
 */
@Entity
@NamedQuery(name="Amici.findAll", query="SELECT a FROM Amici a")
public class Amici implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String email;

	private String cognome;

	private String nome;

	//bi-directional many-to-many association to Pacchetti
	@ManyToMany
	@JoinTable(
			name="condiviso_con"
			, joinColumns={
				@JoinColumn(name="emailAmico")
				}
			, inverseJoinColumns={
				@JoinColumn(name="idPacchetto")
				}
			)		
	private List<Pacchetti> pacchetti;

	public Amici() {
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Pacchetti> getPacchetti() {
		return this.pacchetti;
	}

	public void setPacchetti(List<Pacchetti> pacchetti) {
		this.pacchetti = pacchetti;
	}

}