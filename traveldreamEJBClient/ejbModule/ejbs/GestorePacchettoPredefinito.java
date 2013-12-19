package ejbs;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import dtos.CollegamentoDTO;
import dtos.EscursioneDTO;
import dtos.PacchettoPredefinitoDTO;

@Local
public interface GestorePacchettoPredefinito {
	
	List<PacchettoPredefinitoDTO> elencoPacchetti();
	
	void creaPacchetto (PacchettoPredefinitoDTO pacchetto);
	
	void aggiuntaDataPartenza (PacchettoPredefinitoDTO pacchetto, Date data);
	
	void rimuoviDataPartenza (PacchettoPredefinitoDTO pacchetto, Date data);
	
	void aggiuntaDurata (PacchettoPredefinitoDTO pacchetto, int durata);
	
	void rimuoviDurata (PacchettoPredefinitoDTO pacchetto, int durata);
	
	void aggiuntaCollegamento (PacchettoPredefinitoDTO pacchetto, CollegamentoDTO collegamento);
	
	void rimuoviCollegamento (PacchettoPredefinitoDTO pacchetto, CollegamentoDTO collegamento);
	
	void aggiuntaEscursione (PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione);
	
	void rimuoviEscursione (PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione);
	
	void salvaPacchetto (PacchettoPredefinitoDTO pacchetto);
	
	void eliminaPacchetto (PacchettoPredefinitoDTO pacchetto);

}
