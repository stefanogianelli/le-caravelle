package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CittaDTO;
import dtos.DestinazioneDTO;

@Local
interface GestoreDestinazione {

	List<CittaDTO> elencoCitta ();
	
	void aggiuntaDestinazione (DestinazioneDTO destinazione);
	
	void modificaDatiDestinazione (DestinazioneDTO destinazione);
	
	void aggiuntaEscursione (int idDestinazione, int idEscursione, int numeroPartecipanti);
	
	void modificaDatiEscursione (int idDestinazione, int idEscursione, int numeroPartecipanti);
	
	void eliminaEscursione (int idDestinazione, int idEscursione);
}
