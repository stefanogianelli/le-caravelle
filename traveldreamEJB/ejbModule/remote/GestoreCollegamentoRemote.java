package remote;

import javax.ejb.Remote;

import dtos.CollegamentoDTO;
import eccezioni.CollegamentoInesistenteException;
import entities.Collegamenti;

@Remote
public interface GestoreCollegamentoRemote {

	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param collegamento Il DTO del collegamento
	 * @return L'entit� desiderata
	 * @throws CollegamentoInesistenteException Quando il collegamento non viene trovato nel database
	 */
	Collegamenti convertiInEntita (CollegamentoDTO collegamento) throws CollegamentoInesistenteException;
	
	/**
	 * Permette la conversione da un'entit� al rispettivo DTO
	 * @param collegamento L'entit� di partenza
	 * @return Il relativo DTO
	 */
	CollegamentoDTO convertiInDTO (Collegamenti collegamento);
}
