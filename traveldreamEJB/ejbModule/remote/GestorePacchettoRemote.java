package remote;

import javax.ejb.Remote;

import dtos.PacchettoDTO;
import eccezioni.PacchettoInesistenteException;
import entities.Pacchetti;

@Remote
public interface GestorePacchettoRemote {

	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param pacchetto Il DTO del pacchetto
	 * @return L'entit� desiderata
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	Pacchetti convertiInEntita (PacchettoDTO pacchetto) throws PacchettoInesistenteException;
	
	/**
	 * Permette la conversione da un'entit� al rispettivo DTO
	 * @param pacchetto L'entit� di partenza
	 * @return Il relativo DTO
	 */
	PacchettoDTO convertiInDTO (Pacchetti pacchetto);
}
