package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.HotelDTO;
import eccezioni.CittaInesistenteException;

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
	 * @throws CittaInesistenteException Quando non viene trovata la città nel database
	 */
	void creaHotel (HotelDTO hotel) throws CittaInesistenteException;
	
	/**
	 * Permette di modificare i dati di un hotel
	 * @param hotel L'hotel da modificare
	 * @throws CittaInesistenteException Quando non viene trovata la città nel database
	 */
	void modificaDatiHotel (HotelDTO hotel) throws CittaInesistenteException;
	
	/**
	 * Permette l'eliminazione di un hotel dal database
	 * @param hotel L'hotel da eliminare
	 */
	void eliminaHotel (HotelDTO hotel);
}
