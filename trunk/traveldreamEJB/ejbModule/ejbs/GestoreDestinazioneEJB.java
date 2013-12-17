package ejbs;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.CittaDTO;
import dtos.DestinazioneDTO;
import entities.Citta;
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
	 * @throws NoResultException Quando non esiste una destinazione nel database con l'id scelto
	 */
	private Destinazioni getDestinazione (int id) throws NoResultException {
		Query q = em.createNamedQuery("Destinazioni.getDestinazione", Destinazioni.class);
		q.setParameter("id", id);
		return (Destinazioni) q.getSingleResult();
	}
	
	/**
	 * Ritorna l'oggetto città col nome in input
	 * @param nome Il nome della città
	 * @return L'oggetto desiderato
	 * @throws NoResultException Quando non viene trovata la città nel database
	 */
	private Citta getCitta (String nome) throws NoResultException {
		Query q = em.createNamedQuery("Citta.getCitta", Citta.class);
		q.setParameter("nome", nome);
		return (Citta) q.getSingleResult();		
	}	
	
	/**
	 * Ritorna l'oggetto città con l'id in input
	 * @param id L'identificativo della città
	 * @return L'oggetto desiderato
	 * @throws NoResultException Quando non viene trovata la città nel database
	 */
	private Citta getCitta (int id) throws NoResultException {
		Query q = em.createNamedQuery("Citta.getCitta", Citta.class);
		q.setParameter("id", id);
		return (Citta) q.getSingleResult();		
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
		dto.setCitta(new CittaDTO (destinazione.getCitta().getNazione(), destinazione.getCitta().getNome(), destinazione.getCitta().getRegione()));
		//dto.setHotel(hotel.convertiInDTO(destinazione.getHotel()));
		//dto.setHotel(pacchetto.convertiInDTO(destinazione.getPacchetto()));
		
		return dto;
	}

}
