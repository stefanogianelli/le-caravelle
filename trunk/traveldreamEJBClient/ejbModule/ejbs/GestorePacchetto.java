package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.DestinazioneDTO;
import dtos.HotelDTO;
import dtos.PacchettoDTO;
import dtos.PacchettoPredefinitoDTO;
import enums.TipoPacchetto;

@Local
interface GestorePacchetto {

	List<PacchettoDTO> elencoPacchetti (TipoPacchetto tipo);
	
	void aggiuntaPacchettoPersonalizzato (PacchettoDTO pacchetto);
	
	PacchettoDTO dettagliPacchetto (int idPacchetto);
	
	void modificaDatiPacchetto (PacchettoDTO pacchetto);
	
	void salvaPacchetto (PacchettoDTO pacchetto);
	
	void acquistaPacchetto (int idPacchetto);
	
	void condividiPacchetto (int idPacchetto);
	
	void eliminaPacchetto (int idPacchetto);
	
	void aggiuntaDestinazione (DestinazioneDTO destinazione);
	
	void eliminaDestinazione (int idDestinazione);
	
	void aggiuntaCollegamento (int codiceCollegamento);
	
	void modificaCollegamento (int codiceCollegamento);
	
	void creaPacchettoPredefinito (PacchettoPredefinitoDTO pacchetto);
	
	void aggiuntaHotel (HotelDTO hotel);
	
	void rimuoviHotel (int idHotel);
	
	void rimuoviCollegamento (int idCollegamento);
	
	void aggiuntaEscursione (int idEscursione);
	
	void rimuoviEscursione (int idEscursione);
	
	void modificaDatiPacchetto (PacchettoPredefinitoDTO pacchetto);
	
	PacchettoPredefinitoDTO dettagliPacchettoPredefinito (int idPacchetto);
	
	void salvaPacchetto (PacchettoPredefinitoDTO pacchetto);
}