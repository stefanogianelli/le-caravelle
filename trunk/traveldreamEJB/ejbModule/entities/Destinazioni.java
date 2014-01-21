package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the destinazioni database table.
 * 
 */
@Entity
@NamedQueries ({
	@NamedQuery(name="Destinazioni.elenco", query="SELECT d FROM Destinazioni d"),
	@NamedQuery(name="Destinazioni.getDestinazioneDaHotel", query="SELECT d FROM Destinazioni d WHERE d.hotel.id = :hotel"),
	@NamedQuery(name="Destinazioni.getDestinazioneInCitta", query="SELECT d FROM Destinazioni d WHERE d.citta = :citta")
})
public class Destinazioni implements Serializable, Comparable<Destinazioni> {
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
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="destinazione", orphanRemoval=true)
	private List<Attivita> attivita;
	
	@ManyToOne
	@JoinColumn(name="citta")
	private Citta citta;

	public Destinazioni() {
		attivita = new ArrayList<Attivita>();
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

		return attivita;
	}

	@Override
	public int compareTo(Destinazioni d) {
		if(this.getDataArrivo().before(d.getDataArrivo()))
			return -1;
		else if(this.getDataArrivo().compareTo(d.getDataArrivo()) == 0)
			return 0;
		else
			return 1;
	}
	
}