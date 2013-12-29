package ejbs;

import javax.ejb.Local;

import dtos.AttivitaDTO;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.EscursioneEsistenteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.NumeroPartecipantiException;

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
	 * @throws NumeroPartecipantiException Quando il numero di partecipanti inserito non è valido
	 */
	void aggiuntaEscursione (int idDestinazione, int idEscursione, int numeroPartecipanti) throws EscursioneInesistenteException, DestinazioneInesistenteException, EscursioneEsistenteException, NumeroPartecipantiException;
	
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
