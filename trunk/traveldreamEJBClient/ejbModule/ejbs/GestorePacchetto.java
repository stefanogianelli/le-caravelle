package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.DestinazioneDTO;
import dtos.PacchettoDTO;
import enums.TipoPacchetto;

@Local
interface GestorePacchetto {

	List<PacchettoDTO> elencoPacchetti (TipoPacchetto tipo);
	
	void aggiuntaPacchettoPersonalizzato (PacchettoDTO pacchetto);
	
	void salvaPacchetto (PacchettoDTO pacchetto);
	
	void acquistaPacchetto (int idPacchetto);
	
	void condividiPacchetto (int idPacchetto);
	
	void eliminaPacchetto (int idPacchetto);
	
	void aggiuntaDestinazione (DestinazioneDTO destinazione);
	
	void eliminaDestinazione (int idDestinazione);
	
	void aggiuntaCollegamento (int codiceCollegamento);
	
	void modificaCollegamento (int codiceCollegamento);
}