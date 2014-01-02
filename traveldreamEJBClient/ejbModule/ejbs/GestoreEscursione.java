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
     * Mostra l'elenco di tutte escursioni presenti nel database
     * @return L'elenco delle escursioni
     */
	List<EscursioneDTO> elencoEscursioni ();
	
    /**
     * Mostra l'elenco di tutte escursioni in una regione
     * @param regione Il nome della regione
     * @return L'elenco delle escursioni
     */
	List<EscursioneDTO> elencoEscursioni(String regione);
	
	/**
	 * Mostra l'elenco di escursioni in un regione nell'intervallo di tempo indicato
	 * @param dataArrivo La data di arrivo nella destinazione
	 * @param dataPartenza La data di partenza dalla destinazione
	 * @param regione La regione della destinazione
	 * @return L'elenco delle escursioni trovate
	 */
	List<EscursioneDTO> elencoEscursioni(Date dataArrivo, Date dataPartenza, String regione);
	
	/**
	 * Crea una nuova escursione nel database
	 * @param escursione L'oggetto da salvare
	 * @throws CittaInesistenteException Quando non viene trovata la città nel database
	 * @throws EntitaEsistenteException Quando l'escursione è già esistente nel database
	 */
	void creaEscursione (EscursioneDTO escursione) throws CittaInesistenteException, EntitaEsistenteException;
	
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
