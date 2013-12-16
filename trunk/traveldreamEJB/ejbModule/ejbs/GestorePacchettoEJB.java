package ejbs;

import java.util.List;

import javax.ejb.Stateless;

import dtos.DestinazioneDTO;
import dtos.HotelDTO;
import dtos.PacchettoDTO;
import dtos.PacchettoPredefinitoDTO;
import enums.TipoPacchetto;

/**
 * Session Bean implementation class GestorePacchettoEJB
 */
@Stateless
public class GestorePacchettoEJB implements GestorePacchetto {

    /**
     * Default constructor. 
     */
    public GestorePacchettoEJB() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<PacchettoDTO> elencoPacchetti(TipoPacchetto tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aggiuntaPacchettoPersonalizzato(PacchettoDTO pacchetto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PacchettoDTO dettagliPacchetto(int idPacchetto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modificaDatiPacchetto(PacchettoDTO pacchetto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void salvaPacchetto(PacchettoDTO pacchetto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acquistaPacchetto(int idPacchetto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void condividiPacchetto(int idPacchetto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminaPacchetto(int idPacchetto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aggiuntaDestinazione(DestinazioneDTO destinazione) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminaDestinazione(int idDestinazione) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aggiuntaCollegamento(int codiceCollegamento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificaCollegamento(int codiceCollegamento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void creaPacchettoPredefinito(PacchettoPredefinitoDTO pacchetto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aggiuntaHotel(HotelDTO hotel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rimuoviHotel(int idHotel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rimuoviCollegamento(int idCollegamento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aggiuntaEscursione(int idEscursione) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rimuoviEscursione(int idEscursione) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificaDatiPacchetto(PacchettoPredefinitoDTO pacchetto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PacchettoPredefinitoDTO dettagliPacchettoPredefinito(int idPacchetto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void salvaPacchetto(PacchettoPredefinitoDTO pacchetto) {
		// TODO Auto-generated method stub
		
	}

}
