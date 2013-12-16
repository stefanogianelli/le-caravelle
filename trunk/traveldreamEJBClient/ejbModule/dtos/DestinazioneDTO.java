package dtos;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class DestinazioneDTO {

	@NotEmpty
	private Date dataArrivo;
	
	@NotEmpty
	private Date dataPartenza;
	
	@NotEmpty
	private CittaDTO citta;
	
	@NotEmpty
	private HotelDTO hotel;
	
	@NotEmpty
	private PacchettoDTO pacchetto;

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

	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}

}
