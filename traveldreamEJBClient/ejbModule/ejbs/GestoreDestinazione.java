package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CittaDTO;
import dtos.DestinazioneDTO;
import dtos.EscursioneDTO;

@Local
interface GestoreDestinazione {

	List<CittaDTO> elencoCitta ();
	
	void modificaDatiDestinazione (DestinazioneDTO destinazione);
	
	void aggiuntaEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti);
	
	void modificaDatiEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti);
	
	void eliminaEscursione (DestinazioneDTO destinazione, EscursioneDTO escursione);
}
