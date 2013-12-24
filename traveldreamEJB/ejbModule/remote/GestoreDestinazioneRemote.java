package remote;

import javax.ejb.Remote;

import dtos.AttivitaDTO;
import dtos.DestinazioneDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.HotelInesistenteException;
import entities.Attivita;
import entities.Destinazioni;

@Remote
public interface GestoreDestinazioneRemote {

	/**
	 * Permette l'aggiunta di una nuova destinazione
	 * @param destinazione I dati della nuova destinazione
	 * @param La destinazione creata
	 * @throws CittaInesistenteException  Quando non viene trovata la citt� nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	Destinazioni creaDestinazione (DestinazioneDTO destinazione) throws CittaInesistenteException, HotelInesistenteException;
	
	/**
	 * Permette la modifica dei dati di una destinazione
	 * @param destinazione La destinazione da modificare
	 * @throws CittaInesistenteException  Quando non viene trovata la citt� nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	void modificaDatiDestinazione(DestinazioneDTO destinazione) throws CittaInesistenteException, HotelInesistenteException;
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param destinazione Il DTO della destinazione
	 * @return L'entit� desiderata
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 */
	Destinazioni convertiInEntita (DestinazioneDTO destinazione) throws DestinazioneInesistenteException;
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param idDestinazione L'identificativo della destinazione
	 * @return L'entit� desiderata
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 */
	Destinazioni convertiInEntita (int idDestinazione) throws DestinazioneInesistenteException;
	
	/**
	 * Permette la conversione da un'entit� al rispettivo DTO
	 * @param destinazione L'entit� da convertire
	 * @return Il relativo DTO
	 */
	DestinazioneDTO convertiInDTO (Destinazioni destinazione);
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param destinazione Il DTO dell'attivita
	 * @return L'entit� desiderata
	 */
	Attivita convertiInEntita (AttivitaDTO attivita);
	
	/**
	 * Permette la conversione da un'entit� al rispettivo DTO
	 * @param attivita L'entit� da convertire
	 * @return Il relativo DTO
	 */
	AttivitaDTO convertiInDTO (Attivita attivita);
}
