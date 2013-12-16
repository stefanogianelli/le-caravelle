package ejbs;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import dtos.HotelDTO;

@Local
public interface GestoreHotel {

	public List<HotelDTO> elencoHotel ();
	
	public HotelDTO dettagliHotel (int idHotel) throws NoResultException;
	
	public void creaHotel (HotelDTO hotel) throws EntityExistsException, NoResultException;
	
	public void modificaDatiHotel (int idHotel, HotelDTO hotel) throws NoResultException;
	
	public void eliminaHotel (int idHotel) throws NoResultException;
}
