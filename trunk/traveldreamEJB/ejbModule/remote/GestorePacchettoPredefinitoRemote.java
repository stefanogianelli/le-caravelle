package remote;

import javax.ejb.Remote;

import dtos.PacchettoPredefinitoDTO;
import eccezioni.PacchettoInesistenteException;
import entities.PacchettiPredefiniti;

@Remote
public interface GestorePacchettoPredefinitoRemote {

	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param pacchetto Il DTO del pacchetto predefinito
	 * @return L'entità desiderata
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	PacchettiPredefiniti convertiInEntita (PacchettoPredefinitoDTO pacchetto) throws PacchettoInesistenteException;
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param pacchetto L'entità di partenza
	 * @return Il relativo DTO
	 */
	PacchettoPredefinitoDTO convertiInDTO (PacchettiPredefiniti pacchetto);
}
