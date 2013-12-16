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
	
	HotelDTO dettagliHotel (int idHotel) throws NoResultException;
	
	void creaHotel (HotelDTO hotel) throws EntityExistsException, NoResultException;
	
	void modificaDatiHotel (int idHotel, HotelDTO hotel) throws NoResultException;
	
	void eliminaHotel (int idHotel) throws NoResultException;
}
