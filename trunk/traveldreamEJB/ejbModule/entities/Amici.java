package entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the amici database table.
 * 
 */
@Entity
@NamedQuery(name="Amici.elenco", query="SELECT a FROM Amici a")
public class Amici implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String email;
	
	private String nome;

	private String cognome;	

	@OneToMany(cascade=CascadeType.PERSIST)
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
	
	public Pacchetti addPacchetto (Pacchetti pacchetto) {
		this.getPacchetti().add(pacchetto);
		
		return pacchetto;
	}
	
	public Pacchetti removePacchetto (Pacchetti pacchetto) {
		this.getPacchetti().remove(pacchetto);
		
		return pacchetto;
	}

}