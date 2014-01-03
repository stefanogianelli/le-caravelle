package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the citta database table.
 * 
 */
@Entity
@NamedQueries ({
    @NamedQuery(name = "Citta.elenco", query = "SELECT c FROM Citta c"),
    @NamedQuery(name = "Citta.getCitta", query = "SELECT c FROM Citta c WHERE c.nome = :nome")
})
public class Citta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    @Column(name = "lat")
    private float latitudine;
    
    @Column(name = "lon")
    private float longitudine;

	private String nazione;

	private String nome;

	private String regione;

	//bi-directional many-to-one association to ImmaginiCitta
	@OneToMany(mappedBy="citta")
	private List<ImmaginiCitta> immagini;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getLatitudine() {
		return this.latitudine;
	}

	public void setLatitudine(float latitudine) {
		this.latitudine = latitudine;
	}

	public float getLongitudine() {
		return this.longitudine;
	}

	public void setLongitudine(float longitudine) {
		this.longitudine = longitudine;
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

	public List<ImmaginiCitta> getImmagini() {
		return this.immagini;
	}

	public void setImmagini(List<ImmaginiCitta> immagini) {
		this.immagini = immagini;
	}

	public ImmaginiCitta addImmagini(ImmaginiCitta immagini) {
		getImmagini().add(immagini);
		immagini.setCitta(this);

		return immagini;
	}

	public ImmaginiCitta removeImmagini(ImmaginiCitta immagini) {
		getImmagini().remove(immagini);
		immagini.setCitta(null);

		return immagini;
	}

}