package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.HotelDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.EntitaEsistenteException;
import eccezioni.HotelInesistenteException;

@Local
public interface GestoreHotel {

	/**
	 * Resituisce l'hotel associato all'identificativo inserito
	 * @param idHotel L'identificativo dell'hotel
	 * @return L'hotel corrispondente
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	HotelDTO getHotel (int idHotel) throws HotelInesistenteException;
	
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
	 * @return L'identificativo dell'hotel creato
	 * @throws CittaInesistenteException Quando non viene trovata la città nel database
	 * @throws EntitaEsistenteException Quando l'hotel è già esistente nel database
	 */
	int creaHotel (HotelDTO hotel) throws CittaInesistenteException, EntitaEsistenteException;
	
	/**
	 * Permette di modificare i dati di un hotel
	 * @param hotel L'hotel da modificare
	 * @throws CittaInesistenteException Quando non viene trovata la città nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	void modificaDatiHotel (HotelDTO hotel) throws CittaInesistenteException, HotelInesistenteException;
	
	/**
	 * Permette l'eliminazione di un hotel dal database
	 * @param idHotel L'identificativo dell'hotel da eliminare
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	void eliminaHotel (int idHotel) throws HotelInesistenteException;
}
