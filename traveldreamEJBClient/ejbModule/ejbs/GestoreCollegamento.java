package ejbs;

import java.util.List;

import javax.ejb.Local;
import dtos.CollegamentoDTO;
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
	 */
	void creaCollegamento (CollegamentoDTO collegamento);
	
	/**
	 * Permette la modifica dei dati di un collegamento
	 * @param collegamento Il collegamento da modiicare
	 */
	void modificaDatiCollegamento (CollegamentoDTO collegamento);
	
	/**
	 * Permette l'eliminazione di un collegamento dal database
	 * @param collegamento Il collegamento da eliminare
	 */
	void eliminaCollegamento (CollegamentoDTO collegamento);
	
}
