package ejbs;

import java.util.List;

import javax.ejb.Local;
import dtos.HotelDTO;

@Local
public interface GestoreHotel {

	List<HotelDTO> elencoHotel ();
	
	List<HotelDTO> elencoHotel(String nomeCitta);
	
	void creaHotel (HotelDTO hotel);
	
	void modificaDatiHotel (HotelDTO hotel);
	
	void eliminaHotel (HotelDTO hotel);
}
