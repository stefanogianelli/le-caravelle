package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CollegamentoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import enums.TipoCollegamento;

@Local
public interface GestoreCollegamento {

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
	 * Aggiunge un nuovo collegamento nel database
	 * @param collegamento I dati del collegamento da aggiungere
	 * @throws CittaInesistenteException Quando non viene trovata la città nel database
	 */
	void creaCollegamento (CollegamentoDTO collegamento) throws CittaInesistenteException;
	
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
