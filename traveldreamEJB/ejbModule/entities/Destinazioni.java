package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the destinazioni database table.
 * 
 */
@Entity
@NamedQueries ({
	@NamedQuery(name="Destinazioni.elenco", query="SELECT d FROM Destinazioni d")
})
public class Destinazioni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;	

	@Temporal(TemporalType.DATE)
	private Date dataArrivo;

	@Temporal(TemporalType.DATE)
	private Date dataPartenza;
	
	//relazione bidirezionale many-to-one con l'entità Pacchetti
	@ManyToOne
	@JoinColumn(name="idPacchetto")
	private Pacchetti pacchetto;

	@ManyToOne
	@JoinColumn(name="idHotel")
	private Hotel hotel;	

	//relazione bidirezionale one-to-many con l'entità Attivita
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="destinazione")
	private List<Attivita> attivita;
	
	@ManyToOne
	@JoinColumn(name="citta")
	private Citta citta;

	public Destinazioni() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Citta getCitta() {
		return this.citta;
	}

	public void setCitta(Citta citta) {
		this.citta = citta;
	}

	public Date getDataArrivo() {
		return this.dataArrivo;
	}

	public void setDataArrivo(Date dataArrivo) {
		this.dataArrivo = dataArrivo;
	}

	public Date getDataPartenza() {
		return this.dataPartenza;
	}

	public void setDataPartenza(Date dataPartenza) {
		this.dataPartenza = dataPartenza;
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Pacchetti getPacchetto() {
		return this.pacchetto;
	}

	public void setPacchetto(Pacchetti pacchetto) {
		this.pacchetto = pacchetto;
	}

	public List<Attivita> getAttivita() {
		return this.attivita;
	}

	public void setAttivita(List<Attivita> attivita) {
		this.attivita = attivita;
	}

	public Attivita addAttivita(Attivita attivita) {
		getAttivita().add(attivita);
		attivita.setDestinazione(this);

		return attivita;
	}

	public Attivita removeAttivita(Attivita attivita) {
		getAttivita().remove(attivita);
		attivita.setDestinazione(null);

		return attivita;
	}
	
}