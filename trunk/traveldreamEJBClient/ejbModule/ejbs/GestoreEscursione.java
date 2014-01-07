package ejbs;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import dtos.EscursioneDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.EntitaEsistenteException;
import eccezioni.EscursioneInesistenteException;

@Local
public interface GestoreEscursione {

	/**
	 * Resituisce l'escursione associata all'identificativo inserito
	 * @param idEscursione L'identificativo dell'escursione
	 * @return L'escursione corrispondente
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 */
	EscursioneDTO getEscursione (int idEscursione) throws EscursioneInesistenteException;
	
    /**
     * Mostra l'elenco di tutte escursioni presenti nel database
     * @return L'elenco delle escursioni
     */
	List<EscursioneDTO> elencoEscursioni ();
	
    /**
     * Mostra l'elenco di tutte escursioni in una regione
     * @param data La data dell'escursione
     * @param regione Il nome della regione
     * @param categoria La categoria dell'escursione
     * @return L'elenco delle escursioni
     */
	List<EscursioneDTO> elencoEscursioni(Date data, String regione, String categoria);
	
	/**
	 * Crea una nuova escursione nel database
	 * @param escursione L'oggetto da salvare
	 * @return L'identificativo dell'escursione creata
	 * @throws CittaInesistenteException Quando non viene trovata la città nel database
	 * @throws EntitaEsistenteException Quando l'escursione è già esistente nel database
	 */
	int creaEscursione (EscursioneDTO escursione) throws CittaInesistenteException, EntitaEsistenteException;
	
	/**
	 * Permette di modificare i dati di una escursione
	 * @param escursione L'escursione da modifciare
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 * @throws CittaInesistenteException Quando non viene trovata la città nel database
	 */
	void modificaDatiEscursione (EscursioneDTO escursione) throws EscursioneInesistenteException, CittaInesistenteException;
	
	/**
	 * Permette l'eliminazione di una escursione dal database
	 * @param idEscursione L'identificativo dell'escursione da eliminare
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 */
	void eliminaEscursione (int idEscursione) throws EscursioneInesistenteException;
}
