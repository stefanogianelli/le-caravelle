package ejbs;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import dtos.CollegamentoDTO;
import dtos.EscursioneDTO;
import dtos.PacchettoPredefinitoDTO;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.HotelInesistenteException;

@Local
public interface GestorePacchettoPredefinito {
	
	/**
	 * Mostra l'elenco dei pacchetti predefiniti
	 * @return L'elenco dei pacchetti predefiniti
	 */
	List<PacchettoPredefinitoDTO> elencoPacchetti();
	
	/**
	 * Permette la creazione di un nuovo pacchetto predefinito
	 * @param pacchetto I dati del pacchetto
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	void creaPacchetto (PacchettoPredefinitoDTO pacchetto) throws HotelInesistenteException;
	
	/**
	 * Permette l'aggiunta di una nuova data di partenza nel pacchetto
	 * @param pacchetto Il pacchetto nel quale si vuole aggiungere la data
	 * @param data La data che si vuole aggiungere
	 */
	void aggiuntaDataPartenza (PacchettoPredefinitoDTO pacchetto, Date data);
	
	/**
	 * Permette la rimozione di una data da un pacchetto
	 * @param pacchetto Il pacchetto dal quale si vuole rimuovere la data
	 * @param data La data che si vuole eliminare
	 */
	void rimuoviDataPartenza (PacchettoPredefinitoDTO pacchetto, Date data);
	
	/**
	 * Permette di aggiungere una nuova durata in un pacchetto
	 * @param pacchetto Il pacchetto nel quale si vuole aggiungere la durata
	 * @param durata La durata da aggiungere
	 */
	void aggiuntaDurata (PacchettoPredefinitoDTO pacchetto, int durata);
	
	/**
	 * Permette di eliminare una durata da un pacchetto
	 * @param pacchetto Il pacchetto dal quale si vuole rimuovere la durata
	 * @param durata La durata che si vuole rimuovere
	 */
	void rimuoviDurata (PacchettoPredefinitoDTO pacchetto, int durata);
	
	/**
	 * Permette l'aggiunta di un collegamento nel pacchetto
	 * @param pacchetto Il pacchetto nel quale si vuole aggiungere il collegamento
	 * @param collegamento Il collegamento da aggiungere
	 * @throws CollegamentoInesistenteException Quando non viene trovato il collegamento nel database
	 */
	void aggiuntaCollegamento (PacchettoPredefinitoDTO pacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException;
	
	/**
	 * Permette la rimozione di un collegamento da un pacchetto
	 * @param pacchetto Il pacchetto dal quale si vuole rimuovere il collegamento
	 * @param collegamento Il collegamento che si vuole eliminare
	 * @throws CollegamentoInesistenteException Quando non viene trovato il collegamento nel database
	 */
	void rimuoviCollegamento (PacchettoPredefinitoDTO pacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException;
	
	/**
	 * Permette l'aggiunta di un'escursione nel pacchetto
	 * @param pacchetto Il pacchetto nel quale si vuole aggiungere l'escursione
	 * @param escursione L'escursione che si vuole aggiungere
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 */
	void aggiuntaEscursione (PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione) throws EscursioneInesistenteException;
	
	/**
	 * Permette la rimozione di un'escursione da un pacchetto
	 * @param pacchetto Il pacchetto dal quale si vuole rimuovere l'escursione
	 * @param escursione L'escursione da rimuovere
	 * @throws EscursioneInesistenteException Quando l'escursione non viene trovata nel database
	 */
	void rimuoviEscursione (PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione) throws EscursioneInesistenteException;
	
	/**
	 * Permette il salvataggio di un pacchetto
	 * @param pacchetto Il pacchetto da salvare
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	void salvaPacchetto (PacchettoPredefinitoDTO pacchetto) throws HotelInesistenteException;
	
	/**
	 * Permette l'eliminazione di un pacchetto
	 * @param pacchetto Il pacchetto da eliminare
	 */
	void eliminaPacchetto (PacchettoPredefinitoDTO pacchetto);

}
