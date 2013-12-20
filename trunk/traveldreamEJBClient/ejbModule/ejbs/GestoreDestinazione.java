package ejbs;

import javax.ejb.Local;

import dtos.DestinazioneDTO;
import dtos.EscursioneDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.EscursioneInesistenteException;

@Local
interface GestoreDestinazione {

	/**
	 * Permette la modifica dei dati di una destinazione
	 * @param destinazione La destinazione da modificare
	 * @throws CittaInesistenteException  Quando non viene trovata la città nel database
	 */
	void modificaDatiDestinazione (DestinazioneDTO destinazione) throws CittaInesistenteException;
	
	/**
	 * Permette l'aggiunta di una escursione
	 * @param destinazione L'identificativo della destinazione
	 * @param escursione L'identificativo dell'escursione
	 * @param numeroPartecipanti Il numero di partecipanti all'escursione.
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 */
	void aggiuntaEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti) throws EscursioneInesistenteException;
	
	/**
	 * Permette la modifica del numero di partecipanti ad una escursione
	 * @param destinazione L'identificativo della destinazione
	 * @param escursione L'identificativo dell'escursione
	 * @param numeroPartecipanti Il numero di partecipanti all'escursione
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 */
	void modificaDatiEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti) throws EscursioneInesistenteException;
	
	/**
	 * Permette l'eliminazione di una escursione
	 * @param destinazione L'identificativo della destinazione
	 * @param escursione L'identificativo dell'escursione
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 */
	void eliminaEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione) throws EscursioneInesistenteException;
}
