package ejbs;

import java.util.List;

import javax.ejb.Local;
import dtos.EscursioneDTO;

@Local
public interface GestoreEscursione {

	List<EscursioneDTO> elencoEscursioni ();
	
	List<EscursioneDTO> elencoEscursioni(String nomeCitta);
	
	void creaEscursione (EscursioneDTO escursione);
	
	void modificaDatiEscursione (EscursioneDTO escursione);
	
	void eliminaEscursione (EscursioneDTO escursione);
}
