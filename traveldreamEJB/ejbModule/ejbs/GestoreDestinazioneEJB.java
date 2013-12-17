package ejbs;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import dtos.CittaDTO;
import dtos.DestinazioneDTO;
import entities.Destinazioni;

/**
 * Session Bean implementation class GestoreDestinazioneEJB
 */
@Stateless
public class GestoreDestinazioneEJB implements GestoreDestinazione {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreHotelEJB hotel;
	
	@EJB
	private GestorePacchettoEJB pacchetto;
	
	@EJB
	private GestoreEscursioneEJB escursione;
	
	@EJB
	private GestoreCittaEJB citta;
	
    /**
     * Default constructor. 
     */
    public GestoreDestinazioneEJB() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<CittaDTO> elencoCitta() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modificaDatiDestinazione(DestinazioneDTO destinazione) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aggiuntaEscursione(int idEscursione, int numeroPartecipanti) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificaDatiEscursione(int idEscursione, int numeroPartecipanti) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminaEscursione(int idDestinazione, int idEscursione) {
		//this.getDestinazione(idDestinazione).se
	}
	
	/**
	 * Ritorna l'oggetto destinazione con l'identificativo in input
	 * @param id L'identificativo della destinazione
	 * @return L'oggetto desiderato
	 */
	protected Destinazioni getDestinazione (int id) {
		return em.find(Destinazioni.class, id);
	}
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param destinazione L'entità da convertire
	 * @return Il relativo DTO
	 */
	protected DestinazioneDTO convertiInDTO (Destinazioni destinazione) {
		DestinazioneDTO dto = new DestinazioneDTO();
		
		dto.setDataArrivo(destinazione.getDataArrivo());
		dto.setDataPartenza(destinazione.getDataPartenza());
		dto.setCitta(citta.convertiInDTO(destinazione.getCitta()));
		//dto.setHotel(hotel.convertiInDTO(destinazione.getHotel()));
		//dto.setHotel(pacchetto.convertiInDTO(destinazione.getPacchetto()));
		
		return dto;
	}

}
