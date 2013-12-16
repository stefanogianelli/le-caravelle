package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.HotelDTO;

@Local
public interface GestoreHotel {

	public List<HotelDTO> elencoHotel ();
	
	public HotelDTO dettagliHotel (int idHotel);
	
	public void creaHotel (HotelDTO hotel);
	
	public void modificaDatiHotel (HotelDTO hotel);
	
	public void eliminaHotel (int idHotel);
}
