package ejbs;

import java.util.List;

import javax.ejb.Stateless;

import dtos.CittaDTO;
import dtos.DestinazioneDTO;

/**
 * Session Bean implementation class GestoreDestinazioneEJB
 */
@Stateless
public class GestoreDestinazioneEJB implements GestoreDestinazione {

    /**
     * Default constructor. 
     */
    public GestoreDestinazioneEJB() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<CittaDTO> elencoCitta() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modificaDatiDestinazione(DestinazioneDTO destinazione) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aggiuntaEscursione(int idEscursione, int numeroPartecipanti) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificaDatiEscursione(int idEscursione, int numeroPartecipanti) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminaEscursione(int idEscursione) {
		// TODO Auto-generated method stub
		
	}

}
