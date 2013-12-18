package ejbs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dtos.CollegamentoDTO;
import dtos.EscursioneDTO;
import dtos.HotelDTO;
import dtos.PacchettoPredefinitoDTO;
import entities.DatePartenza;
import entities.Durate;
import entities.PacchettiPredefiniti;

/**
 * Session Bean implementation class GestorePacchettoPredefinitoEJB
 */
@Stateless
public class GestorePacchettoPredefinitoEJB implements GestorePacchettoPredefinito {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreHotelEJB hotel;
	
    /**
     * Default constructor. 
     */
    public GestorePacchettoPredefinitoEJB() {
        
    }
    
	/**
	 * Mostra l'elenco dei pacchetti predefiniti
	 * @return L'elenco dei pacchetti predefiniti
	 */
	@Override
	public List<PacchettoPredefinitoDTO> elencoPacchetti() {
		List<PacchettiPredefiniti> pacchetti = em.createNamedQuery("PacchettiPredefiniti.elenco", PacchettiPredefiniti.class).getResultList();
		List<PacchettoPredefinitoDTO> pacchettiDTO = new ArrayList<PacchettoPredefinitoDTO>();
		for (PacchettiPredefiniti p : pacchetti) {
			pacchettiDTO.add(this.convertiInDTO(p));
		}
		return pacchettiDTO;
	}

	@Override
	public void creaPacchetto(PacchettoPredefinitoDTO pacchetto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aggiuntaHotel(PacchettoPredefinitoDTO pacchetto, HotelDTO hotel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rimuoviHotel(PacchettoPredefinitoDTO pacchetto, HotelDTO hotel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rimuoviCollegamento(PacchettoPredefinitoDTO pacchetto,
			CollegamentoDTO collegamento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aggiuntaEscursione(PacchettoPredefinitoDTO pacchetto,
			EscursioneDTO escursione) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rimuoviEscursione(PacchettoPredefinitoDTO pacchetto,
			EscursioneDTO escursione) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void salvaPacchetto(PacchettoPredefinitoDTO pacchetto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminaPacchetto(PacchettoPredefinitoDTO pacchetto) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param pacchetto Il DTO del pacchetto predefinito
	 * @return L'entità desiderata
	 */
	protected PacchettiPredefiniti convertiInDAO (PacchettoPredefinitoDTO pacchetto) {
		return em.find(PacchettiPredefiniti.class, pacchetto.getId());
	}
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param pacchetto L'entità di partenza
	 * @return Il relativo DTO
	 */
	protected PacchettoPredefinitoDTO convertiInDTO (PacchettiPredefiniti pacchetto) {
		PacchettoPredefinitoDTO pacchettoDTO = new PacchettoPredefinitoDTO();
		
		pacchettoDTO.setId(pacchetto.getId());
		pacchettoDTO.setNome(pacchetto.getNome());
		pacchettoDTO.setPrezzo(pacchetto.getPrezzo());
		List<Date> datePartenza = new ArrayList<Date>();
		for (DatePartenza d : pacchetto.getDatePartenza()) {
			datePartenza.add(d.getId().getData());
		}
		pacchettoDTO.setDatePartenza(datePartenza);
		List<Integer> durate = new ArrayList<Integer>();
		for (Durate d : pacchetto.getDurate()) {
			durate.add(d.getId().getDurata());
		}
		pacchettoDTO.setDurate(durate);
		pacchettoDTO.setHotel(hotel.convertiInDTO(pacchetto.getHotel()));
		
		return pacchettoDTO;
	}

}
