package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CollegamentoDTO;
import dtos.DestinazioneDTO;
import dtos.HotelDTO;
import dtos.PacchettoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.InsertException;
import eccezioni.PacchettoInesistenteException;
import enums.TipoPacchetto;

@Local
public interface GestorePacchetto {

	/**
	 * Resituisce il pacchetto associato all'identificativo inserito
	 * @param idPacchetto L'identificativo del pacchetto
	 * @return Il pacchetto corrispondente
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	PacchettoDTO getPacchetto (int idPacchetto) throws PacchettoInesistenteException;
	
    /**
     * Mostra l'elenco dei pacchetti per tipologia posseduti da un utente
     * @param email L'indirizzo email dell'utente
     * @param tipo La tipologia di pacchetto da cercare
     * @return L'elenco dei pacchetti
     */
	List<PacchettoDTO> elencoPacchetti (String email, TipoPacchetto tipo);
	
	/**
	 * Permette la creazione di un nuovo pacchetto personalizzato
	 * @param pacchetto I dati del pacchetto
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 * @throws InsertException Quando il nome del pacchetto esiste già nel database
	 * @return L'identificativo del pacchetto creato
	 */
	int creaPacchettoPersonalizzato (PacchettoDTO pacchetto) throws CittaInesistenteException, HotelInesistenteException, InsertException;
	
	/**
	 * Modifica il nome di un pacchetto
	 * @param pacchetto Il pacchetto da modificare
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws InsertException Quando il nome del pacchetto esiste già nel database
	 */
	void modificaNomePacchetto (PacchettoDTO pacchetto) throws PacchettoInesistenteException, InsertException;
	
	/**
	 * Modifica la città di partenza del pacchetto
	 * @param pacchetto Il pacchetto da modificare
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 */
	void modificaCittaPartenzaPacchetto (PacchettoDTO pacchetto) throws PacchettoInesistenteException, CittaInesistenteException;
	
	/**
	 * Modifica il numero di partecipanti al viaggio
	 * @param pacchetto Il pacchetto da modificare
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	void modificaNumeroPartecipanti (PacchettoDTO pacchetto) throws PacchettoInesistenteException;
	
	/**
	 * Permette il salvataggiuo di un pacchetto predefinito
	 * @param pacchetto I dati del pacchetto
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws InsertException Quando il nome del pacchetto esiste già nel database
	 */
	void salvaPacchettoPredefinito (PacchettoDTO pacchetto) throws CittaInesistenteException, HotelInesistenteException, PacchettoInesistenteException, InsertException;
	
	/**
	 * Permette l'acquisto di un pacchetto
	 * @param pacchetto Il pacchetto da acquistare
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	void acquistaPacchetto (PacchettoDTO pacchetto) throws PacchettoInesistenteException;
	
	/**
	 * Permette la condivisione di un pacchetto
	 * @param pacchetto Il pacchetto da condividere
	 * @param email L'indirizzo email dell'amico con cui condividere il paccheto
	 * @param nome Il nome dell'amico
	 * @param cognome Il cognome dell'amico
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 * @throws CollegamentoInesistenteException Quando non viene trovato il collegamento nel database
	 * @throws EscursioneInesistenteException Quando non viene trovata l'escursione nel database
	 */
	void condividiPacchetto (PacchettoDTO pacchetto, String email, String nome, String cognome) throws CittaInesistenteException, HotelInesistenteException, CollegamentoInesistenteException, EscursioneInesistenteException;
	
	/**
	 * Permette l'eliminazione di un pacchetto
	 * @param pacchetto Il pacchetto da eliminare
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	void eliminaPacchetto (PacchettoDTO pacchetto) throws PacchettoInesistenteException;
	
	/**
	 * Permette l'aggiunta di una nuova destinazione nel pacchetto
	 * @param idPacchetto L'dentificativo del pacchetto nel quale aggiungere la destinazione
	 * @param destinazione La destinazione da aggiungere
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws InsertException Può capitare per diversi motivi:
	 * - la data di arrivo è maggiore della data di partenza
	 * - la città scelta come destinazione è uguale alla città di partenza
	 * - le date scelte non sono valide (si ricorda che è possibile aggiungere una nuova destinazione in "testa" o in "coda" alla prima o all'ultima destinazione inserita nel pacchetto)
	 */
	void aggiuntaDestinazione (int idPacchetto, DestinazioneDTO destinazione) throws CittaInesistenteException, HotelInesistenteException, PacchettoInesistenteException, InsertException;
	
	/**
	 * Permette la modifica delle date di una destinazione
	 * @param pacchetto Il pacchetto nel quale è contenuta la destinazione
	 * @param destinazione La destinazione da modificare
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 */
	void modificaDateDestinazione(PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws PacchettoInesistenteException, CittaInesistenteException;
	
	/**
	 * Permette la modifica dell'hotel di una destinazione
	 * @param idPacchetto L'identificativo del pacchetto nel quale è contenuta la destinazione
	 * @param idDestinazione L'identificativo della destinazione da modificare
	 * @param hotel L'hotel da aggiungere
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 * @throws InsertException Quando l'hotel non si trova nella stessa città della destinazione
	 */
	public void modificaHotelDestinazione (int idPacchetto, int idDestinazione, HotelDTO hotel) throws PacchettoInesistenteException, HotelInesistenteException, DestinazioneInesistenteException, InsertException;
	
	/**
	 * Permette l'eliminazione di una destinazione da un pacchetto
	 * @param pacchetto Il pacchetto nel quale si vuole eliminare la destinazione
	 * @param destinazione La destinazione da eliminare
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	void eliminaDestinazione (PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws DestinazioneInesistenteException, PacchettoInesistenteException;
	
	/**
	 * Permette l'aggiunta di un collegamento nel pacchetto
	 * @param idPacchetto L'identificativo pacchetto nel quale aggiungere il collegamento
	 * @param collegamento Il collegamento selezionato
	 * @throws CollegamentoInesistenteException Quando non viene trovato il collegamento nel database
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	void aggiuntaCollegamento (int idPacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException, PacchettoInesistenteException;

}