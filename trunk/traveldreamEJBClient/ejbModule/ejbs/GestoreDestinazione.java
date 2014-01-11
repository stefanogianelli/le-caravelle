package ejbs;

import javax.ejb.Local;

import dtos.AttivitaDTO;
import dtos.HotelDTO;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.EntitaEsistenteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.InsertException;
import eccezioni.NumeroPartecipantiException;

@Local
public interface GestoreDestinazione {
	
	/**
	 * Permette la modifica dell'hotel di una destinazione
	 * @param idDestinazione L'identificativo della destinazione da modificare
	 * @param hotel L'hotel da aggiungere
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 * @throws InsertException Quando l'hotel non si trova nella stessa città della destinazione
	 */
	void modificaHotel (int idDestinazione, HotelDTO hotel) throws HotelInesistenteException, DestinazioneInesistenteException, InsertException;
	
	/**
	 * Permette l'aggiunta di una escursione
	 * @param idDestinazione L'identificativo della destinazione
	 * @param idEscursione L'identificativo dell'escursione
	 * @param numeroPartecipanti Il numero di partecipanti all'escursione.
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 * @throws EntitaEsistenteException Quando l'escursione è già stata inserita
	 * @throws NumeroPartecipantiException Quando il numero di partecipanti inserito non è valido
	 * @throws InsertException Quando la data dell'escursione è al di fuori delle date della destinazione o se le regioni non combaciano
	 */
	void aggiuntaEscursione (int idDestinazione, int idEscursione, int numeroPartecipanti) throws EscursioneInesistenteException, DestinazioneInesistenteException, EntitaEsistenteException, NumeroPartecipantiException, InsertException;
	
	/**
	 * Permette la modifica del numero di partecipanti ad una escursione
	 * @param attivita L'attività da modificare
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 * @throws NumeroPartecipantiException Quando il numero di partecipanti inserito non è valido
	 */
	void modificaDatiEscursione (AttivitaDTO attivita) throws EscursioneInesistenteException, DestinazioneInesistenteException, NumeroPartecipantiException;
	
	/**
	 * Permette l'eliminazione di una escursione
	 * @param attivita L'attivita da rimuovere
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 */
	void eliminaEscursione (AttivitaDTO attivita) throws EscursioneInesistenteException, DestinazioneInesistenteException;
}
