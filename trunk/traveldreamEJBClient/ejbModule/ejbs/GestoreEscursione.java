package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.EscursioneDTO;
import eccezioni.CittaInesistenteException;

@Local
public interface GestoreEscursione {

    /**
     * Mostra l'elenco di tutte escursioni presenti nel database
     * @return L'elenco delle escursioni
     */
	List<EscursioneDTO> elencoEscursioni ();
	
    /**
     * Mostra l'elenco di tutte escursioni in una città
     * @param nomeCitta Il nome della città
     * @return L'elenco delle escursioni
     */
	List<EscursioneDTO> elencoEscursioni(String nomeCitta);
	
	/**
	 * Crea una nuova escursione nel database
	 * @param escursione L'oggetto da salvare
	 * @param CittaInesistenteException Quando non viene trovata la città nel database
	 */
	void creaEscursione (EscursioneDTO escursione) throws CittaInesistenteException;
	
	/**
	 * Permette di modificare i dati di una escursione
	 * @param escursione L'escursione da modifciare
	 * @param CittaInesistenteException Quando non viene trovata la città nel database
	 */
	void modificaDatiEscursione (EscursioneDTO escursione) throws CittaInesistenteException;
	
	/**
	 * Permette l'eliminazione di una escursione dal database
	 * @param escursione L'escursione da eliminare
	 */
	void eliminaEscursione (EscursioneDTO escursione);
}
