package ejbs;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import dtos.HotelDTO;

@Local
public interface GestoreHotel {

	List<HotelDTO> elencoHotel ();
	
	List<HotelDTO> elencoHotel(String nomeCitta);
	
	void creaHotel (HotelDTO hotel) throws EntityExistsException, NoResultException;
	
	void modificaDatiHotel (HotelDTO hotel) throws NoResultException;
	
	void eliminaHotel (HotelDTO hotel) throws NoResultException;
}
