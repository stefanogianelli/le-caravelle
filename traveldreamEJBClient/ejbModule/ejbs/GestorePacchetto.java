package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CollegamentoDTO;
import dtos.DestinazioneDTO;
import dtos.PacchettoDTO;
import enums.TipoPacchetto;

@Local
interface GestorePacchetto {

	List<PacchettoDTO> elencoPacchetti (TipoPacchetto tipo);
	
	void creaPacchettoPersonalizzato (PacchettoDTO pacchetto);
	
	void salvaPacchetto (PacchettoDTO pacchetto);
	
	void acquistaPacchetto (PacchettoDTO pacchetto);
	
	void condividiPacchetto (PacchettoDTO pacchetto, String email);
	
	void eliminaPacchetto (PacchettoDTO pacchetto);
	
	void aggiuntaDestinazione (PacchettoDTO pacchetto, DestinazioneDTO destinazione);
	
	void eliminaDestinazione (PacchettoDTO pacchetto, DestinazioneDTO destinazione);
	
	void aggiuntaCollegamento (PacchettoDTO pacchetto, CollegamentoDTO collegamento);
	
	void modificaCollegamento (PacchettoDTO pacchetto, CollegamentoDTO collegamento);
}