package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the destinazioni database table.
 * 
 */
@Entity
@NamedQuery(name="Destinazioni.findAll", query="SELECT d FROM Destinazioni d")
public class Destinazioni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date dataArrivo;

	@Temporal(TemporalType.DATE)
	private Date dataPartenza;

	//bi-directional many-to-one association to Citta
	@ManyToOne
	@JoinColumn(name="citta")
	private Citta citta;

	//bi-directional many-to-one association to Hotel
	@ManyToOne
	@JoinColumn(name="idHotel")
	private Hotel hotel;

	//bi-directional many-to-one association to Pacchetti
	@ManyToOne
	@JoinColumn(name="idPacchetto")
	private Pacchetti pacchetto;

	public Destinazioni() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Citta getCitta() {
		return this.citta;
	}

	public void setCitta(Citta citta) {
		this.citta = citta;
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

}