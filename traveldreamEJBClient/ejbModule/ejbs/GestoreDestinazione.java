package ejbs;

import javax.ejb.Local;

import eccezioni.DestinazioneInesistenteException;
import eccezioni.EscursioneEsistenteException;
import eccezioni.EscursioneInesistenteException;

@Local
public interface GestoreDestinazione {
	
	/**
	 * Permette l'aggiunta di una escursione
	 * @param idDestinazione L'identificativo della destinazione
	 * @param idEscursione L'identificativo dell'escursione
	 * @param numeroPartecipanti Il numero di partecipanti all'escursione.
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 * @throws EscursioneEsistenteException Quando l'escursione è già stata inserita
	 */
	void aggiuntaEscursione (int idDestinazione, int idEscursione, int numeroPartecipanti) throws EscursioneInesistenteException, DestinazioneInesistenteException, EscursioneEsistenteException;
	
	/**
	 * Permette la modifica del numero di partecipanti ad una escursione
	 * @param idDestinazione L'identificativo della destinazione
	 * @param idEscursione L'identificativo dell'escursione
	 * @param numeroPartecipanti Il numero di partecipanti all'escursione
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 */
	void modificaDatiEscursione (int idDestinazione, int idEscursione, int numeroPartecipanti) throws EscursioneInesistenteException, DestinazioneInesistenteException;
	
	/**
	 * Permette l'eliminazione di una escursione
	 * @param idDestinazione L'identificativo della destinazione
	 * @param idEscursione L'identificativo dell'escursione
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 */
	void eliminaEscursione (int idDestinazione, int idEscursione) throws EscursioneInesistenteException, DestinazioneInesistenteException;
}
