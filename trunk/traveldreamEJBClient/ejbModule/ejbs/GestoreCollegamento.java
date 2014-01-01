package ejbs;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityExistsException;

import dtos.CollegamentoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import enums.TipoCollegamento;

@Local
public interface GestoreCollegamento {

	/**
	 * Resituisce il collegamento associato all'identificativo inserito
	 * @param codiceCollegamento Il codice del collegamento
	 * @return Il collegamento corrispondente
	 * @throws CollegamentoInesistenteException Quando il collegamento non viene trovato nel database
	 */
	CollegamentoDTO getCollegamento (int codiceCollegamento) throws CollegamentoInesistenteException;
	
    /**
     * Mostra l'elenco dei collegamenti disponibili
     * @return L'elenco dei collegamenti
     */
	List<CollegamentoDTO> elencoCollegamenti ();
	
    /**
     * Mostra l'elenco dei collegamenti disponibili per tipologia
     * @param tipo La tipologia del collegamento
     * @return L'elenco dei collegamenti
     */
	List<CollegamentoDTO> elencoCollegamenti(TipoCollegamento tipo);
	
	/**
	 * Mostra l'elenco dei collegamenti disponibili tra due destinazioni nella data indicate e della tipologia selezionata
	 * @param data La data del collegamento
	 * @param idCittaPartenza L'identificativo della città di partenza
	 * @param idCittaArrivo L'identificativo della città di arrivo
	 * @param tipo La tipologia del collegamento
	 * @return L'elenco dei collegamenti trovati
	 */
	List<CollegamentoDTO> elencoCollegamenti(Date data, String idCittaPartenza, String idCittaArrivo, TipoCollegamento tipo);
	
	/**
	 * Aggiunge un nuovo collegamento nel database
	 * @param collegamento I dati del collegamento da aggiungere
	 * @throws CittaInesistenteException Quando non viene trovata la città nel database
	 * @throws EntityExistsException Quando il collegamento è già esistente nel database
	 */
	void creaCollegamento (CollegamentoDTO collegamento) throws CittaInesistenteException, EntityExistsException;
	
	/**
	 * Permette la modifica dei dati di un collegamento
	 * @param collegamento Il collegamento da modiicare
	 * @throws CollegamentoInesistenteException Quando non viene trovato il collegamento nel database
	 * @throws CittaInesistenteException Quando non viene trovata la città nel database
	 */
	void modificaDatiCollegamento (CollegamentoDTO collegamento) throws CollegamentoInesistenteException, CittaInesistenteException;
	
	/**
	 * Permette l'eliminazione di un collegamento dal database
	 * @param collegamento Il collegamento da eliminare
	 * @throws CollegamentoInesistenteException Quando non viene trovato il collegamento nel database
	 */
	void eliminaCollegamento (CollegamentoDTO collegamento) throws CollegamentoInesistenteException;
	
}
