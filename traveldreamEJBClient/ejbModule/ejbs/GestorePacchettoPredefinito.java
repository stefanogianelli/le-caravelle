package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CollegamentoDTO;
import dtos.EscursioneDTO;
import dtos.HotelDTO;
import dtos.PacchettoPredefinitoDTO;

@Local
public interface GestorePacchettoPredefinito {
	
	List<PacchettoPredefinitoDTO> elencoPacchetti();
	
	void creaPacchetto (PacchettoPredefinitoDTO pacchetto);
	
	void aggiuntaHotel (PacchettoPredefinitoDTO pacchetto, HotelDTO hotel);
	
	void rimuoviHotel (PacchettoPredefinitoDTO pacchetto, HotelDTO hotel);
	
	void rimuoviCollegamento (PacchettoPredefinitoDTO pacchetto, CollegamentoDTO collegamento);
	
	void aggiuntaEscursione (PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione);
	
	void rimuoviEscursione (PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione);
	
	void salvaPacchetto (PacchettoPredefinitoDTO pacchetto);
	
	void eliminaPacchetto (PacchettoPredefinitoDTO pacchetto);

}
