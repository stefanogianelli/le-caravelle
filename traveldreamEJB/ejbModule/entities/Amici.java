package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the amici database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Amici.elenco", query="SELECT a FROM Amici a"),
	@NamedQuery(name="Amici.getAmico", query="SELECT a FROM Amici a WHERE a.email = :email"),
	@NamedQuery(name="Amici.getPacchetti", query="SELECT a FROM Amici a JOIN a.pacchetti p WHERE p.id = :id")
})
public class Amici implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String email;
	
	private String nome;

	private String cognome;	

	@OneToMany(orphanRemoval=true)
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
		pacchetti = new ArrayList<Pacchetti>();
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