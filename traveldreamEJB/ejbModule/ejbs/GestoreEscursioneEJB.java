package ejbs;

import java.util.List;

import javax.ejb.Stateless;

import dtos.EscursioneDTO;

/**
 * Session Bean implementation class GestoreEscursioneEJB
 */
@Stateless
public class GestoreEscursioneEJB implements GestoreEscursione {

    /**
     * Default constructor. 
     */
    public GestoreEscursioneEJB() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<EscursioneDTO> elencoEscursioni() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EscursioneDTO dettagliEscursione(int idEscursione) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void creaEscursione(EscursioneDTO escursione) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificaDatiEscursione(EscursioneDTO escursione) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminaEscursione(int idEscursione) {
		// TODO Auto-generated method stub
		
	}

}
