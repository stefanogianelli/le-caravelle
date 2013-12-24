package remote;

import javax.ejb.Remote;

import dtos.HotelDTO;
import eccezioni.HotelInesistenteException;
import entities.Hotel;

@Remote
public interface GestoreHotelRemote {

	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param hotel Il DTO dell'hotel
	 * @return L'entità desiderata
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	Hotel convertiInEntita (HotelDTO hotel) throws HotelInesistenteException;
	
	/**
	 * Permette la conversione da un'entità hotel al rispettivo DTO
	 * @param hotel L'oggetto da converitre
	 * @return Il DTO risultante
	 */
	HotelDTO convertiInDTO (Hotel hotel);
}
