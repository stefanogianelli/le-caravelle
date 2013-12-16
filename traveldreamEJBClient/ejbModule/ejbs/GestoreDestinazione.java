package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CittaDTO;
import dtos.DestinazioneDTO;

@Local
public interface GestoreDestinazione {

	public List<CittaDTO> elencoCitta ();
	
	public void modificaDatiDestinazione (DestinazioneDTO destinazione);
	
	public void aggiuntaEscursione (int idEscursione, int numeroPartecipanti);
	
	public void modificaDatiEscursione (int idEscursione, int numeroPartecipanti);
	
	public void eliminaEscursione (int idEscursione);
}
