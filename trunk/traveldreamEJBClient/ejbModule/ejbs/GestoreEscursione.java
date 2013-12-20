package ejbs;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityExistsException;

import dtos.EscursioneDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.EscursioneInesistenteException;

@Local
public interface GestoreEscursione {

    /**
     * Mostra l'elenco di tutte escursioni presenti nel database
     * @return L'elenco delle escursioni
     */
	List<EscursioneDTO> elencoEscursioni ();
	
    /**
     * Mostra l'elenco di tutte escursioni in una citt�
     * @param nomeCitta Il nome della citt�
     * @return L'elenco delle escursioni
     */
	List<EscursioneDTO> elencoEscursioni(String nomeCitta);
	
	/**
	 * Crea una nuova escursione nel database
	 * @param escursione L'oggetto da salvare
	 * @throws CittaInesistenteException Quando non viene trovata la citt� nel database
	 * @throws EntityExistsException Quando l'escursione � gi� esistente nel database
	 */
	void creaEscursione (EscursioneDTO escursione) throws CittaInesistenteException, EntityExistsException;
	
	/**
	 * Permette di modificare i dati di una escursione
	 * @param escursione L'escursione da modifciare
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 * @throws CittaInesistenteException Quando non viene trovata la citt� nel database
	 */
	void modificaDatiEscursione (EscursioneDTO escursione) throws EscursioneInesistenteException, CittaInesistenteException;
	
	/**
	 * Permette l'eliminazione di una escursione dal database
	 * @param escursione L'escursione da eliminare
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 */
	void eliminaEscursione (EscursioneDTO escursione) throws EscursioneInesistenteException;
}
