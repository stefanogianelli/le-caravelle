package interfaces;

import java.util.Date;

import javax.ejb.Local;

import dtos.DestinazioneDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.HotelInesistenteException;
import entities.Destinazioni;

@Local
public interface GestoreDestinazioneLocal {

	/**
	 * Permette l'aggiunta di una nuova destinazione
	 * @param destinazione I dati della nuova destinazione
	 * @param La destinazione creata
	 * @throws CittaInesistenteException  Quando non viene trovata la città nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	Destinazioni creaDestinazione (DestinazioneDTO destinazione) throws CittaInesistenteException, HotelInesistenteException;
	
	/**
	 * Permette la modifica della data di arrivo in una destinazione
	 * @param destinazione La destinazione da modificare
	 * @return La data di arrivo precedente
	 * @throws DestinazioneInesistenteException  Quando non viene trovata la destinazione nel database
	 */
	Date modificaDataArrivo(DestinazioneDTO destinazione) throws DestinazioneInesistenteException;
	
	/**
	 * Permette la modifica della data di partenza da una destinazione
	 * @param destinazione La destinazione da modificare
	 * @return La data di partenza precedente
	 * @throws DestinazioneInesistenteException  Quando non viene trovata la destinazione nel database
	 */
	Date modificaDataPartenza (DestinazioneDTO destinazione) throws DestinazioneInesistenteException;
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param destinazione Il DTO della destinazione
	 * @return L'entità desiderata
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 */
	Destinazioni convertiInEntita (DestinazioneDTO destinazione) throws DestinazioneInesistenteException;
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param destinazione L'entità da convertire
	 * @return Il relativo DTO
	 */
	DestinazioneDTO convertiInDTO (Destinazioni destinazione);
	
}
