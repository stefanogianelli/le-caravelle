package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Future;

public class DestinazioneDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	@Future
	private Date dataArrivo;
	
	@Future
	private Date dataPartenza;
	
	private CittaDTO citta;
	
	private HotelDTO hotel;
	
	private List<AttivitaDTO> attivita;
	
	private PacchettoDTO pacchetto;
	
	public DestinazioneDTO () {
		citta = new CittaDTO();
		attivita = new ArrayList<AttivitaDTO>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataArrivo() {
		return dataArrivo;
	}

	public void setDataArrivo(Date dataArrivo) {
		this.dataArrivo = dataArrivo;
	}

	public Date getDataPartenza() {
		return dataPartenza;
	}

	public void setDataPartenza(Date dataPartenza) {
		this.dataPartenza = dataPartenza;
	}

	public CittaDTO getCitta() {
		return citta;
	}

	public void setCitta(CittaDTO citta) {
		this.citta = citta;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}

	public List<AttivitaDTO> getAttivita() {
		return attivita;
	}

	public void setAttivita(List<AttivitaDTO> attivita) {
		this.attivita = attivita;
	}

	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}

}
