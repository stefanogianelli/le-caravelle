package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.CittaDTO;
import dtos.DestinazioneDTO;
import entities.Attivita;
import entities.Citta;
import entities.Destinazioni;
import entities.Escursioni;

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
        
    }

    /**
     * Ritorna l'elenco di tutte le città presenti nel database
     * @return L'elenco delle città
     */
	@Override
	public List<CittaDTO> elencoCitta() {
		List<Citta> citta = em.createNamedQuery("Citta.elenco", Citta.class).getResultList();
		List<CittaDTO> dto = new ArrayList<CittaDTO>();
		for (Citta c : citta) {
			dto.add(this.citta.convertiInDTO(c));
		}
		return dto;
	}
	
	@Override
	public void aggiuntaDestinazione (DestinazioneDTO destinazione) {
		Destinazioni entity = new Destinazioni();
		
		entity.setDataArrivo(destinazione.getDataArrivo());
		entity.setDataPartenza(destinazione.getDataPartenza());
		entity.setCitta(citta.getCitta(destinazione.getCitta().getNome()));
		entity.setHotel(hotel.getHotel(destinazione.getHotel().getNome()));
		//entity.setPacchetto(pacchetto);
		
		em.persist(entity);
	}

	@Override
	public void modificaDatiDestinazione(DestinazioneDTO destinazione) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Permette l'aggiunta di una escursione
	 * @param idDestinazione L'identificativo della destinazione
	 * @param idEscursione L'identificativo dell'escursione
	 * @param numeroPartecipanti Il numero di partecipanti all'escursione
	 */
	@Override
	public void aggiuntaEscursione(int idDestinazione, int idEscursione, int numeroPartecipanti) {
		Destinazioni destinazione = this.getDestinazione(idDestinazione);
		
		Attivita attivita = new Attivita();
		attivita.setDestinazione(destinazione);
		attivita.getId().setIdDestinazione(idDestinazione);
		attivita.setEscursione(escursione.getEscursione(idEscursione));
		attivita.getId().setIdEscursione(idEscursione);
		attivita.setNumPartecipanti(numeroPartecipanti);
		
		destinazione.addAttivita(attivita);
		
		em.persist(destinazione);
		
	}

	/**
	 * Permette la modifica del numero di partecipanti ad una escursione
	 * @param idDestinazione L'identificativo della destinazione
	 * @param idEscursione L'identificativo dell'escursione
	 * @param numeroPartecipanti Il numero di partecipanti all'escursione
	 */
	@Override
	public void modificaDatiEscursione(int idDestinazione, int idEscursione, int numeroPartecipanti) {
		Destinazioni destinazione = this.getDestinazione(idDestinazione);
		
		Escursioni escursione = this.escursione.getEscursione(idEscursione);
		
		Query q = em.createNamedQuery("Attivita.getAttivita", Attivita.class);
		q.setParameter("destinazione", destinazione);
		q.setParameter("escursione", escursione);
		Attivita attivita = (Attivita) q.getSingleResult();

		attivita.setNumPartecipanti(numeroPartecipanti);
		
		em.merge(destinazione);		
	}

	/**
	 * Permette l'eliminazione di una escursione
	 * @param idDestinazione L'identificativo della destinazione
	 * @param idEscursione L'identificativo dell'escursione
	 */
	@Override
	public void eliminaEscursione(int idDestinazione, int idEscursione) {
		Destinazioni destinazione = this.getDestinazione(idDestinazione);
		
		Escursioni escursione = this.escursione.getEscursione(idEscursione);
		
		Query q = em.createNamedQuery("Attivita.getAttivita", Attivita.class);
		q.setParameter("destinazione", destinazione);
		q.setParameter("escursione", escursione);
		Attivita attivita = (Attivita) q.getSingleResult();
		
		em.remove(attivita);
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
		dto.setHotel(hotel.convertiInDTO(destinazione.getHotel()));
		//dto.setHotel(pacchetto.convertiInDTO(destinazione.getPacchetto()));
		
		return dto;
	}

}
