package interfaces;

import javax.ejb.Local;

import dtos.CollegamentoDTO;
import eccezioni.CollegamentoInesistenteException;
import entities.Collegamenti;

@Local
public interface GestoreCollegamentoLocal {
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param collegamento Il DTO del collegamento
	 * @return L'entità desiderata
	 * @throws CollegamentoInesistenteException Quando il collegamento non viene trovato nel database
	 */
	Collegamenti convertiInEntita (CollegamentoDTO collegamento) throws CollegamentoInesistenteException;
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param collegamento L'entità di partenza
	 * @return Il relativo DTO
	 */
	CollegamentoDTO convertiInDTO (Collegamenti collegamento);
}
