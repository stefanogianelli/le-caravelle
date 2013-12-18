package ejbs;

import javax.ejb.Local;

import dtos.DestinazioneDTO;
import dtos.EscursioneDTO;

@Local
interface GestoreDestinazione {

	void modificaDatiDestinazione (DestinazioneDTO destinazione);
	
	void aggiuntaEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti);
	
	void modificaDatiEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti);
	
	void eliminaEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione);
}
