package remote;

import javax.ejb.Remote;

import dtos.PacchettoDTO;
import eccezioni.PacchettoInesistenteException;
import entities.Pacchetti;

@Remote
public interface GestorePacchettoRemote {

	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param pacchetto Il DTO del pacchetto
	 * @return L'entità desiderata
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	Pacchetti convertiInEntita (PacchettoDTO pacchetto) throws PacchettoInesistenteException;
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param pacchetto L'entità di partenza
	 * @return Il relativo DTO
	 */
	PacchettoDTO convertiInDTO (Pacchetti pacchetto);
}
