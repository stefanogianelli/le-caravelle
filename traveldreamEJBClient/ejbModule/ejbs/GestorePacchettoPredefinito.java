package ejbs;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import dtos.CollegamentoDTO;
import dtos.EscursioneDTO;
import dtos.PacchettoPredefinitoDTO;

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
	 */
	void creaPacchetto (PacchettoPredefinitoDTO pacchetto);
	
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
	 */
	void aggiuntaCollegamento (PacchettoPredefinitoDTO pacchetto, CollegamentoDTO collegamento);
	
	/**
	 * Permette la rimozione di un collegamento da un pacchetto
	 * @param pacchetto Il pacchetto dal quale si vuole rimuovere il collegamento
	 * @param collegamento Il collegamento che si vuole eliminare
	 */
	void rimuoviCollegamento (PacchettoPredefinitoDTO pacchetto, CollegamentoDTO collegamento);
	
	/**
	 * Permette l'aggiunta di un'escursione nel pacchetto
	 * @param pacchetto Il pacchetto nel quale si vuole aggiungere l'escursione
	 * @param escursione L'escursione che si vuole aggiungere
	 */
	void aggiuntaEscursione (PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione);
	
	/**
	 * Permette la rimozione di un'escursione da un pacchetto
	 * @param pacchetto Il pacchetto dal quale si vuole rimuovere l'escursione
	 * @param escursione L'escursione da rimuovere
	 */
	void rimuoviEscursione (PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione);
	
	/**
	 * Permette il salvataggio di un pacchetto
	 * @param pacchetto Il pacchetto da salvare
	 */
	void salvaPacchetto (PacchettoPredefinitoDTO pacchetto);
	
	/**
	 * Permette l'eliminazione di un pacchetto
	 * @param pacchetto Il pacchetto da eliminare
	 */
	void eliminaPacchetto (PacchettoPredefinitoDTO pacchetto);

}
