package ejbs;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import dtos.EscursioneDTO;

@Local
public interface GestoreEscursione {

	List<EscursioneDTO> elencoEscursioni ();
	
	List<EscursioneDTO> elencoEscursioni(String nomeCitta);
	
	void creaEscursione (EscursioneDTO escursione) throws EntityExistsException, NoResultException;
	
	void modificaDatiEscursione (EscursioneDTO escursione) throws NoResultException;
	
	void eliminaEscursione (EscursioneDTO escursione) throws NoResultException;
}
