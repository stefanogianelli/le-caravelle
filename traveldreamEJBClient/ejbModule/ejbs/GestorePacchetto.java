package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.DestinazioneDTO;
import dtos.HotelDTO;
import dtos.PacchettoDTO;
import dtos.PacchettoPredefinitoDTO;
import enums.TipoPacchetto;

@Local
public interface GestorePacchetto {

	public List<PacchettoDTO> elencoPacchetti (TipoPacchetto tipo);
	
	public void aggiuntaPacchettoPersonalizzato (PacchettoDTO pacchetto);
	
	public PacchettoDTO dettagliPacchetto (int idPacchetto);
	
	public void modificaDatiPacchetto (PacchettoDTO pacchetto);
	
	public void salvaPacchetto (PacchettoDTO pacchetto);
	
	public void acquistaPacchetto (int idPacchetto);
	
	public void condividiPacchetto (int idPacchetto);
	
	public void eliminaPacchetto (int idPacchetto);
	
	public void aggiuntaDestinazione (DestinazioneDTO destinazione);
	
	public void eliminaDestinazione (int idDestinazione);
	
	public void aggiuntaCollegamento (int codiceCollegamento);
	
	public void modificaCollegamento (int codiceCollegamento);
	
	public void creaPacchettoPredefinito (PacchettoPredefinitoDTO pacchetto);
	
	public void aggiuntaHotel (HotelDTO hotel);
	
	public void rimuoviHotel (int idHotel);
	
	public void rimuoviCollegamento (int idCollegamento);
	
	public void aggiuntaEscursione (int idEscursione);
	
	public void rimuoviEscursione (int idEscursione);
	
	public void modificaDatiPacchetto (PacchettoPredefinitoDTO pacchetto);
	
	public PacchettoPredefinitoDTO dettagliPacchettoPredefinito (int idPacchetto);
	
	public void salvaPacchetto (PacchettoPredefinitoDTO pacchetto);
}