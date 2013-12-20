package ejbs;

import javax.ejb.Local;

import dtos.DestinazioneDTO;
import dtos.EscursioneDTO;

@Local
interface GestoreDestinazione {

	/**
	 * Permette la modifica dei dati di una destinazione
	 * @param destinazione La destinazione da modificare
	 */
	void modificaDatiDestinazione (DestinazioneDTO destinazione);
	
	/**
	 * Permette l'aggiunta di una escursione
	 * @param destinazione L'identificativo della destinazione
	 * @param escursione L'identificativo dell'escursione
	 * @param numeroPartecipanti Il numero di partecipanti all'escursione
	 */
	void aggiuntaEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti);
	
	/**
	 * Permette la modifica del numero di partecipanti ad una escursione
	 * @param destinazione L'identificativo della destinazione
	 * @param escursione L'identificativo dell'escursione
	 * @param numeroPartecipanti Il numero di partecipanti all'escursione
	 */
	void modificaDatiEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti);
	
	/**
	 * Permette l'eliminazione di una escursione
	 * @param destinazione L'identificativo della destinazione
	 * @param escursione L'identificativo dell'escursione
	 */
	void eliminaEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione);
}
