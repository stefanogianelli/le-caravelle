package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CollegamentoDTO;
import dtos.DestinazioneDTO;
import dtos.PacchettoDTO;
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
	 */
	void creaPacchettoPersonalizzato (PacchettoDTO pacchetto);
	
	/**
	 * Permette il salvataggio di un pacchetto personalizzato
	 * @param pacchetto I dati del pacchetto
	 */
	void salvaPacchetto (PacchettoDTO pacchetto);
	
	/**
	 * Permette l'acquisto di un pacchetto
	 * @param pacchetto Il pacchetto da acquistare
	 */
	void acquistaPacchetto (PacchettoDTO pacchetto);
	
	/**
	 * Permette la condivisione di un pacchetto
	 * @param pacchetto Il pacchetto da condividere
	 * @param email L'indirizzo email dell'amico con cui condividere il paccheto
	 */
	void condividiPacchetto (PacchettoDTO pacchetto, String email);
	
	/**
	 * Permette l'eliminazione di un pacchetto
	 * @param pacchetto Il pacchetto da eliminare
	 */
	void eliminaPacchetto (PacchettoDTO pacchetto);
	
	/**
	 * Permette l'aggiunta di una nuova destinazione nel pacchetto
	 * @param pacchetto Il pacchetto nel quale aggiungere la destinazione
	 * @param destinazione La destinazione da aggiungere
	 */
	void aggiuntaDestinazione (PacchettoDTO pacchetto, DestinazioneDTO destinazione);
	
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
	 */
	void aggiuntaCollegamento (PacchettoDTO pacchetto, CollegamentoDTO collegamento);
	
	/**
	 * Permette l'eliminazione di un collegamento dal pacchetto
	 * @param pacchetto Il pacchetto dal quale di vuole rimuovere il collegamento
	 * @param collegamento Il collegamento da rimuovere
	 */
	void modificaCollegamento (PacchettoDTO pacchetto, CollegamentoDTO collegamento);
}