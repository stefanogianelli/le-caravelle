package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * The persistent class for the citta database table.
 * 
 */
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"nome", "nazione"})})
@NamedQueries ({
    @NamedQuery(name = "Citta.elenco", query = "SELECT c FROM Citta c WHERE c.attivo = 1"),
    @NamedQuery(name = "Citta.getCitta", query = "SELECT c FROM Citta c WHERE c.nome = :nome"),
    @NamedQuery(name = "Citta.getCittaDaNomeENazione", query = "SELECT c FROM Citta c WHERE c.nome = :nome AND c.nazione = :nazione")
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
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="citta", orphanRemoval=true)
	private List<ImmaginiCitta> immagini;
	
	private Integer attivo;
	
	public Citta () {
		immagini = new ArrayList<ImmaginiCitta>();
	}

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

	public ImmaginiCitta addImmagini (ImmaginiCitta immagini) {
		getImmagini().add(immagini);
		immagini.setCitta(this);

		return immagini;
	}

	public ImmaginiCitta removeImmagini(ImmaginiCitta immagini) {
		getImmagini().remove(immagini);

		return immagini;
	}
	
	public Integer getAttivo() {
		return attivo;
	}

	public void setAttivo(Integer attivo) {
		this.attivo = attivo;
	}

	@Override
	public boolean equals (Object other) {		
		if (this.getNome().equals(((Citta) other).getNome()))
			return true;
		else
			return false;
	}

}