package ejbs;

import java.util.List;

import javax.ejb.Local;
import dtos.HotelDTO;

@Local
public interface GestoreHotel {

    /**
     * Mostra l'elenco di tutti gli hotel presenti nel database
     * @return L'elenco degli hotel
     */
	List<HotelDTO> elencoHotel ();
	
    /**
     * Mostra l'elenco di tutti gli hotel in una città
     * @param nomeCitta Il nome della città
     * @return L'elenco degli hotel
     */
	List<HotelDTO> elencoHotel(String nomeCitta);
	
	/**
	 * Crea un nuovo hotel nel database
	 * @param hotel L'oggetto da salvare
	 */
	void creaHotel (HotelDTO hotel);
	
	/**
	 * Permette di modificare i dati di un hotel
	 * @param hotel L'hotel da modificare
	 */
	void modificaDatiHotel (HotelDTO hotel);
	
	/**
	 * Permette l'eliminazione di un hotel dal database
	 * @param hotel L'hotel da eliminare
	 */
	void eliminaHotel (HotelDTO hotel);
}
