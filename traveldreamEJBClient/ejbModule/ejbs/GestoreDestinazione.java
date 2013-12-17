package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CittaDTO;
import dtos.DestinazioneDTO;

@Local
interface GestoreDestinazione {

	List<CittaDTO> elencoCitta ();
	
	void modificaDatiDestinazione (DestinazioneDTO destinazione);
	
	void aggiuntaEscursione (int idEscursione, int numeroPartecipanti);
	
	void modificaDatiEscursione (int idEscursione, int numeroPartecipanti);
	
	void eliminaEscursione (int idDestinazione, int idEscursione);
}
