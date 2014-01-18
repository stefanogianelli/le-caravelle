package ejbs;

import java.util.List;

import javax.ejb.Local;
import javax.mail.MessagingException;

import dtos.AmicoDTO;
import dtos.CollegamentoDTO;
import dtos.DestinazioneDTO;
import dtos.PacchettoDTO;
import dtos.PersonaDTO;
import eccezioni.AcquistoException;
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
	 * Restituisce il pacchetto condiviso richiesto
	 * @param idPacchetto L'identificativo del pacchetto
	 * @param email L'indirizzo email dell'amico
	 * @return Il paccheto condiviso
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	PacchettoDTO getPacchettoCondiviso (int idPacchetto, String email)  throws PacchettoInesistenteException;
	
    /**
     * Mostra l'elenco dei pacchetti per tipologia posseduti da un utente
     * @param tipo La tipologia di pacchetto da cercare
     * @return L'elenco dei pacchetti
     */
	List<PacchettoDTO> elencoPacchetti (TipoPacchetto tipo);
	
    /**
     * Mostra l'elenco dei primi tre pacchetti per tipologia posseduti da un utente
     * @param email L'indirizzo email dell'utente
     * @param tipo La tipologia di pacchetto da cercare
     * @return L'elenco dei pacchetti
     */
	List<PacchettoDTO> elencoTrePacchetti(TipoPacchetto tipo);
	
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
	 * @param idPacchetto L'identificativo del pacchetto predefinito
	 * @param cittaPartenza Il nome della città di partenza scelta
	 * @param dataArrivo La data di arrivo scelta
	 * @param durata La durata scelta
	 * @return L'identificativo del pacchetto creato
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws InsertException Quando il nome del pacchetto esiste già nel database
	 */
	int salvaPacchettoPredefinito (int idPacchetto,String cittaPartenza, String dataArrivo, int durata) throws PacchettoInesistenteException, CittaInesistenteException, InsertException;
	
	/**
	 * Permette l'acquisto di un pacchetto
	 * @param idPacchetto L'identificativo pacchetto da acquistare
	 * @param partecipanti I dati personali dei partecipanti al viaggio
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws AcquistoException Quando il pacchetto è incompleto o quando il dati dei partecipanti e il numero di partecipanti del pacchetto non combaciano
	 * @throws MessagingException Quando si verifica un errore nell'invio della email
	 */
	void acquistaPacchetto (int idPacchetto, List<PersonaDTO> partecipanti) throws PacchettoInesistenteException, AcquistoException, MessagingException;
	
	/**
	 * Permette la condivisione di un pacchetto
	 * @param pacchetto Il pacchetto da condividere
	 * @param datiAmico I dati dell'amico
	 * @throws CittaInesistenteException Quando la città non viene trovata la città nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 * @throws CollegamentoInesistenteException Quando non viene trovato il collegamento nel database
	 * @throws EscursioneInesistenteException Quando non viene trovata l'escursione nel database
	 * @param MessagingException Quando si verifica un errore nell'invio della email
	 * @throws PacchettoInesistenteException Quando non viene trovato il pacchetto predefinito originale
	 */
	void condividiPacchetto (PacchettoDTO pacchetto, AmicoDTO datiAmico) throws CittaInesistenteException, HotelInesistenteException, EscursioneInesistenteException, CollegamentoInesistenteException, MessagingException, PacchettoInesistenteException;
	
	/**
	 * Permette il salvataggio di un pacchetto condiviso
	 * @param pacchetto Il pacchetto condiviso da salvare
	 * @return L'identificativo del pacchetto creato
	 * @throws CittaInesistenteException Quando la città non viene trovata la città nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 * @throws EscursioneInesistenteException Quando non viene trovata l'escursione nel database
	 * @throws CollegamentoInesistenteException Quando non viene trovato il collegamento nel database
	 * @throws PacchettoInesistenteException Quando non viene trovato il pacchetto predefinito originale
	 */
	int salvaPacchettoCondiviso (PacchettoDTO pacchetto) throws CittaInesistenteException, HotelInesistenteException, EscursioneInesistenteException, CollegamentoInesistenteException, PacchettoInesistenteException;
	
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
	 * Permette la modifica della data di arrivo in una destinazione
	 * @param pacchetto Il pacchetto nel quale è contenuta la destinazione
	 * @param destinazione La destinazione da modificare
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 */	
	void modificaDataArrivo (PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws PacchettoInesistenteException, DestinazioneInesistenteException;	
	
	/**
	 * Permette la modifica della data di partenza da una destinazione
	 * @param pacchetto Il pacchetto nel quale è contenuta la destinazione
	 * @param destinazione La destinazione da modificare
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 */
	void modificaDataPartenza (PacchettoDTO pacchetto, DestinazioneDTO destinazione) throws PacchettoInesistenteException, DestinazioneInesistenteException;
	
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
	 * @throws InsertException Quando il collegamento che si vuole aggiungere non è coerente con il pacchetto
	 */
	void aggiuntaCollegamento (int idPacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException, PacchettoInesistenteException, InsertException;

}