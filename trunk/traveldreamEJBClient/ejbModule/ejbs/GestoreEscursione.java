package ejbs;

import java.util.List;

import javax.ejb.Local;
import dtos.EscursioneDTO;

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
	 */
	void creaEscursione (EscursioneDTO escursione);
	
	/**
	 * Permette di modificare i dati di una escursione
	 * @param escursione L'escursione da modifciare
	 */
	void modificaDatiEscursione (EscursioneDTO escursione);
	
	/**
	 * Permette l'eliminazione di una escursione dal database
	 * @param escursione L'escursione da eliminare
	 */
	void eliminaEscursione (EscursioneDTO escursione);
}
