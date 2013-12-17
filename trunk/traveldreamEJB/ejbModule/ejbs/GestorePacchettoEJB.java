package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dtos.DestinazioneDTO;
import dtos.HotelDTO;
import dtos.PacchettoDTO;
import dtos.PacchettoPredefinitoDTO;
import entities.Destinazioni;
import entities.Pacchetti;
import entities.PacchettiPredefiniti;
import enums.TipoPacchetto;

/**
 * Session Bean implementation class GestorePacchettoEJB
 */
@Stateless
public class GestorePacchettoEJB implements GestorePacchetto {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreHotelEJB hotel;
	
	@EJB
	private GestoreDestinazioneEJB destinazione;
	
	@EJB
	private GestoreCittaEJB citta;
	
	@EJB
	private GestoreProfiloEJB profilo;
	
    /**
     * Default constructor. 
     */
    public GestorePacchettoEJB() {
        
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
	
	protected Pacchetti convertiInDAO (PacchettoDTO pacchetto) {
		return em.find(Pacchetti.class, pacchetto.getId());
	}
	
	protected PacchettiPredefiniti convertiInDAO (PacchettoPredefinitoDTO pacchetto) {
		return em.find(PacchettiPredefiniti.class, pacchetto.getId());
	}
	
	protected PacchettoDTO convertiInDTO (Pacchetti pacchetto) {
		PacchettoDTO pacchettoDTO = new PacchettoDTO();
		
		pacchettoDTO.setId(pacchetto.getId());
		pacchettoDTO.setNome(pacchetto.getNome());
		pacchettoDTO.setNumPartecipanti(pacchetto.getNumPartecipanti());
		pacchettoDTO.setPrezzo(pacchetto.getPrezzo());
		pacchettoDTO.setTipoPacchetto(pacchetto.getTipoPacchetto());
		List<DestinazioneDTO> destinazioni = new ArrayList<DestinazioneDTO>();
		for (Destinazioni d : pacchetto.getDestinazioni()) {
			destinazioni.add(this.destinazione.convertiInDTO(d));
		}
		pacchettoDTO.setDestinazioni(destinazioni);
		pacchettoDTO.setCitta(citta.convertiInDTO(pacchetto.getCitta()));
		pacchettoDTO.setPacchettoPredefinito(this.convertiInDTO(pacchetto.getPacchettoPredefinito()));
		//pacchettoDTO.setUtente(profilo);
		
		return pacchettoDTO;
	}
	
	protected PacchettoPredefinitoDTO convertiInDTO (PacchettiPredefiniti pacchetto) {
		PacchettoPredefinitoDTO pacchettoDTO = new PacchettoPredefinitoDTO();
		
		pacchettoDTO.setId(pacchetto.getId());
		pacchettoDTO.setNome(pacchetto.getNome());
		pacchettoDTO.setPrezzo(pacchetto.getPrezzo());
		//pacchettoDTO.setDatePartenza(pacchetto.getDatePartenza());
		//pacchettoDTO.setDurate(pacchetto.getDurate());
		pacchettoDTO.setHotel(hotel.convertiInDTO(pacchetto.getHotel()));
		
		return pacchettoDTO;
	}

}
