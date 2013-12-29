package ejbs;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import dtos.CittaDTO;
import dtos.CollegamentoDTO;
import dtos.EscursioneDTO;
import dtos.HotelDTO;
import dtos.PacchettoPredefinitoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.DeleteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.InsertException;
import eccezioni.PacchettoInesistenteException;

@Local
public interface GestorePacchettoPredefinito {
	
	/**
	 * Resituisce il pacchetto associato all'identificativo inserito
	 * @param idPacchetto L'identificativo del pacchetto
	 * @return Il pacchetto corrispondente
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	PacchettoPredefinitoDTO getPacchetto (int idPacchetto) throws PacchettoInesistenteException;
	
	/**
	 * Mostra l'elenco dei pacchetti predefiniti
	 * @return L'elenco dei pacchetti predefiniti
	 */
	List<PacchettoPredefinitoDTO> elencoPacchetti();
	
	/**
	 * Permette la creazione di un nuovo pacchetto predefinito
	 * @param pacchetto I dati del pacchetto
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 */
	void creaPacchetto (PacchettoPredefinitoDTO pacchetto) throws HotelInesistenteException, CittaInesistenteException;
	
	/**
	 * Permette di aggiungere una città di partenza nel pacchetto
	 * @param pacchetto Il pacchetto nel quale aggiungere la città
	 * @param nomeCitta Il nome della città
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 * @throws InsertException Quando la città è già stata inserita
	 */
	void aggiuntaCittaPartenza (PacchettoPredefinitoDTO pacchetto, String nomeCitta) throws PacchettoInesistenteException, CittaInesistenteException, InsertException;
	
	/**
	 * Permette di rimuovere una città di partenza dal pacchetto
	 * @param pacchetto Il pacchetto dal quale rimuovere la città
	 * @param citta La città da rimuovere
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws CittaInesistenteException Quando la non viene trovata la città nel database
	 * @throws DeleteException Quando non è possibile rimuovere la città (nel caso sia l'unica rimasta nel pacchetto)
	 */
	void rimuoviCittaPartenza (PacchettoPredefinitoDTO pacchetto, CittaDTO citta) throws PacchettoInesistenteException, CittaInesistenteException, DeleteException;
	
	/**
	 * Permette l'aggiunta di una nuova data di partenza nel pacchetto
	 * @param pacchetto Il pacchetto nel quale si vuole aggiungere la data
	 * @param data La data che si vuole aggiungere
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws InsertException Quando la data è già presente nel database
	 */
	void aggiuntaDataPartenza (PacchettoPredefinitoDTO pacchetto, Date data) throws PacchettoInesistenteException, InsertException;
	
	/**
	 * Permette la rimozione di una data da un pacchetto
	 * @param pacchetto Il pacchetto dal quale si vuole rimuovere la data
	 * @param data La data che si vuole eliminare
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws DeleteException Quando non è possibile cancellare la data (per esempio, quando ne è rimasta solo una nel pacchetto)
	 */
	void rimuoviDataPartenza (PacchettoPredefinitoDTO pacchetto, Date data) throws PacchettoInesistenteException, DeleteException;
	
	/**
	 * Permette di aggiungere una nuova durata in un pacchetto
	 * @param pacchetto Il pacchetto nel quale si vuole aggiungere la durata
	 * @param durata La durata da aggiungere
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws InsertException Quando la durata esiste già nel database
	 */
	void aggiuntaDurata (PacchettoPredefinitoDTO pacchetto, int durata) throws PacchettoInesistenteException, InsertException;
	
	/**
	 * Permette di eliminare una durata da un pacchetto
	 * @param pacchetto Il pacchetto dal quale si vuole rimuovere la durata
	 * @param durata La durata che si vuole rimuovere
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws DeleteException Quando non è possibile cancellare la durata (per esempio, quando ne è rimasta solo una nel pacchetto)
	 */
	void rimuoviDurata (PacchettoPredefinitoDTO pacchetto, int durata) throws PacchettoInesistenteException, DeleteException;
	
	/**
	 * Permette l'aggiunta di un collegamento nel pacchetto
	 * @param idPacchetto L'identificativo del pacchetto nel quale si vuole aggiungere il collegamento
	 * @param collegamento Il collegamento da aggiungere
	 * @throws CollegamentoInesistenteException Quando non viene trovato il collegamento nel database
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws InsertException Quando il collegamento è già presente nel pacchetto
	 */
	void aggiuntaCollegamento (int idPacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException, PacchettoInesistenteException, InsertException;
	
	/**
	 * Permette la rimozione di un collegamento da un pacchetto
	 * @param pacchetto Il pacchetto dal quale si vuole rimuovere il collegamento
	 * @param collegamento Il collegamento che si vuole eliminare
	 * @throws CollegamentoInesistenteException Quando non viene trovato il collegamento nel database
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	void rimuoviCollegamento (PacchettoPredefinitoDTO pacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException, PacchettoInesistenteException;
	
	/**
	 * Permette l'aggiunta di un'escursione nel pacchetto
	 * @param pacchetto Il pacchetto nel quale si vuole aggiungere l'escursione
	 * @param escursione L'escursione che si vuole aggiungere
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	void aggiuntaEscursione (PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione) throws EscursioneInesistenteException, PacchettoInesistenteException;
	
	/**
	 * Permette la rimozione di un'escursione da un pacchetto
	 * @param pacchetto Il pacchetto dal quale si vuole rimuovere l'escursione
	 * @param escursione L'escursione da rimuovere
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	void rimuoviEscursione (PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione) throws EscursioneInesistenteException, PacchettoInesistenteException;
	
	/**
	 * Permette la modifica del nome del pacchetto
	 * @param pacchetto Il pacchetto da salvare
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	void modificaNomePacchetto (PacchettoPredefinitoDTO pacchetto) throws PacchettoInesistenteException;
	
	/**
	 * Permette la modifica del prezzo del pacchetto
	 * @param pacchetto Il pacchetto da salvare
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	void modificaPrezzo (PacchettoPredefinitoDTO pacchetto) throws PacchettoInesistenteException;
	
	/**
	 * Permette la modifica dell'hotel
	 * @param idPacchetto L'identificativo del pacchetto
	 * @param hotel L'hotel da inserire
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	void modificaHotel (int idPacchetto, HotelDTO hotel) throws PacchettoInesistenteException, HotelInesistenteException;
	
	/**
	 * Permette l'eliminazione di un pacchetto
	 * @param pacchetto Il pacchetto da eliminare
	 * @throws PacchettoInesistenteException Quando il pacchetto non viene trovato nel database
	 */
	void eliminaPacchetto (PacchettoPredefinitoDTO pacchetto) throws PacchettoInesistenteException;

}
