package interfaces;

import javax.ejb.Local;

import dtos.PacchettoDTO;
import eccezioni.PacchettoInesistenteException;
import entities.Pacchetti;

@Local
public interface GestorePacchettoLocal {

	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param pacchetto Il DTO del pacchetto
	 * @return L'entit� desiderata
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	Pacchetti convertiInEntita (PacchettoDTO pacchetto) throws PacchettoInesistenteException;
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param idPacchetto L'identificativo del pacchetto
	 * @return L'entit� desiderata
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	Pacchetti convertiInEntita (int idPacchetto) throws PacchettoInesistenteException;
	
	/**
	 * Permette la conversione da un'entit� al rispettivo DTO
	 * @param pacchetto L'entit� di partenza
	 * @return Il relativo DTO
	 */
	PacchettoDTO convertiInDTO (Pacchetti pacchetto);
}
