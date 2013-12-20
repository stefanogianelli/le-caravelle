package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CollegamentoDTO;
import dtos.DestinazioneDTO;
import dtos.PacchettoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.HotelInesistenteException;
import enums.TipoPacchetto;

@Local
interface GestorePacchetto {

    /**
     * Mostra l'elenco dei pacchetti per tipologia
     * @param tipo La tipologia di pacchetto da cercare
     * @return L'elenco dei pacchetti
     */
	List<PacchettoDTO> elencoPacchetti (TipoPacchetto tipo);
	
	/**
	 * Permette la creazione di un nuovo pacchetto personalizzato
	 * @param pacchetto I dati del pacchetto
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	void creaPacchettoPersonalizzato (PacchettoDTO pacchetto) throws CittaInesistenteException, HotelInesistenteException;
	
	/**
	 * Permette il salvataggio di un pacchetto personalizzato
	 * @param pacchetto I dati del pacchetto
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 */
	void salvaPacchetto (PacchettoDTO pacchetto) throws CittaInesistenteException;
	
	/**
	 * Permette il salvataggiuo di un pacchetto predefinito
	 * @param pacchetto I dati del pacchetto
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	void salvaPacchettoPredefinito (PacchettoDTO pacchetto) throws CittaInesistenteException, HotelInesistenteException;
	
	/**
	 * Permette l'acquisto di un pacchetto
	 * @param pacchetto Il pacchetto da acquistare
	 */
	void acquistaPacchetto (PacchettoDTO pacchetto);
	
	/**
	 * Permette la condivisione di un pacchetto
	 * @param pacchetto Il pacchetto da condividere
	 * @param email L'indirizzo email dell'amico con cui condividere il paccheto
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	void condividiPacchetto (PacchettoDTO pacchetto, String email) throws CittaInesistenteException, HotelInesistenteException;
	
	/**
	 * Permette l'eliminazione di un pacchetto
	 * @param pacchetto Il pacchetto da eliminare
	 */
	void eliminaPacchetto (PacchettoDTO pacchetto);
	
	/**
	 * Permette l'aggiunta di una nuova destinazione nel pacchetto
	 * @param pacchetto Il pacchetto nel quale aggiungere la destinazione
	 * @param destinazione La destinazione da aggiungere
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	void aggiuntaDestinazione (PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws CittaInesistenteException, HotelInesistenteException;
	
	/**
	 * Permette l'eliminazione di una destinazione da un pacchetto
	 * @param pacchetto Il pacchetto nel quale si vuole eliminare la destinazione
	 * @param destinazione La destinazione da eliminare
	 */
	void eliminaDestinazione (PacchettoDTO pacchetto, DestinazioneDTO destinazione);
	
	/**
	 * Permette l'aggiunta di un collegamento nel pacchetto
	 * @param pacchetto Il pacchetto nel quale aggiungere il collegamento
	 * @param collegamento Il collegamento selezionato
	 * @throws CollegamentoInesistenteException Quando non viene trovato il collegamento nel database
	 */
	void aggiuntaCollegamento (PacchettoDTO pacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException;
	
	/**
	 * Permette l'eliminazione di un collegamento dal pacchetto
	 * @param pacchetto Il pacchetto dal quale di vuole rimuovere il collegamento
	 * @param collegamento Il collegamento da rimuovere
	 * @throws CollegamentoInesistenteException Quando non viene trovato il collegamento nel database
	 */
	void modificaCollegamento (PacchettoDTO pacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException;
}