package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the immagini_citta database table.
 * 
 */
@Entity
@Table(name="immagini_citta")
@NamedQuery(name="ImmaginiCitta.elenco", query="SELECT i FROM ImmaginiCitta i")
public class ImmaginiCitta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String immagine;

	//bi-directional many-to-one association to Citta
	@ManyToOne
	@JoinColumn(name="idCitta")
	private Citta citta;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImmagine() {
		return this.immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public Citta getCitta() {
		return this.citta;
	}

	public void setCitta(Citta citta) {
		this.citta = citta;
	}

}