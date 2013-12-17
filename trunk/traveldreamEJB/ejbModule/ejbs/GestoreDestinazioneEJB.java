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
import dtos.EscursioneDTO;
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
		entity.setCitta(citta.convertiInDAO(destinazione.getCitta()));
		entity.setHotel(hotel.convertiInDAO(destinazione.getHotel()));
		//entity.setPacchetto(pacchetto);
		
		em.persist(entity);
	}

	@Override
	public void modificaDatiDestinazione(DestinazioneDTO destinazione) {
		Destinazioni entity = em.find(Destinazioni.class, destinazione.getId());
		
		entity.setDataArrivo(destinazione.getDataArrivo());
		entity.setDataPartenza(destinazione.getDataPartenza());
		entity.setCitta(citta.convertiInDAO(destinazione.getCitta()));
		entity.setHotel(hotel.convertiInDAO(destinazione.getHotel()));
		//entity.setPacchetto(pacchetto);
		
		em.merge(entity);		
	}

	/**
	 * Permette l'aggiunta di una escursione
	 * @param idDestinazione L'identificativo della destinazione
	 * @param idEscursione L'identificativo dell'escursione
	 * @param numeroPartecipanti Il numero di partecipanti all'escursione
	 */
	@Override
	public void aggiuntaEscursione(DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti) {
		Destinazioni destinazioneDAO = this.convertiInDAO(destinazione);
		Escursioni escursioneDAO = this.escursione.convertiInDAO(escursione);
		
		Attivita attivita = new Attivita();
		attivita.setDestinazione(destinazioneDAO);
		attivita.getId().setIdDestinazione(destinazioneDAO.getId());
		attivita.setEscursione(escursioneDAO);
		attivita.getId().setIdEscursione(escursioneDAO.getId());
		attivita.setNumPartecipanti(numeroPartecipanti);
		
		destinazioneDAO.addAttivita(attivita);
		
		em.persist(destinazioneDAO);
		
	}

	/**
	 * Permette la modifica del numero di partecipanti ad una escursione
	 * @param idDestinazione L'identificativo della destinazione
	 * @param idEscursione L'identificativo dell'escursione
	 * @param numeroPartecipanti Il numero di partecipanti all'escursione
	 */
	@Override
	public void modificaDatiEscursione(DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti) {
		Destinazioni destinazioneDAO = this.convertiInDAO(destinazione);
		
		Escursioni escursioneDAO = this.escursione.convertiInDAO(escursione);
		
		Query q = em.createNamedQuery("Attivita.getAttivita", Attivita.class);
		q.setParameter("destinazione", destinazioneDAO);
		q.setParameter("escursione", escursioneDAO);
		Attivita attivita = (Attivita) q.getSingleResult();

		attivita.setNumPartecipanti(numeroPartecipanti);
		
		em.merge(attivita);		
	}

	/**
	 * Permette l'eliminazione di una escursione
	 * @param idDestinazione L'identificativo della destinazione
	 * @param idEscursione L'identificativo dell'escursione
	 */
	@Override
	public void eliminaEscursione(DestinazioneDTO destinazione, EscursioneDTO escursione) {
		Destinazioni destinazioneDAO = this.convertiInDAO(destinazione);
		
		Escursioni escursioneDAO = this.escursione.convertiInDAO(escursione);
		
		Query q = em.createNamedQuery("Attivita.getAttivita", Attivita.class);
		q.setParameter("destinazione", destinazioneDAO);
		q.setParameter("escursione", escursioneDAO);
		Attivita attivita = (Attivita) q.getSingleResult();
		
		destinazioneDAO.removeAttivita(attivita);
		
		em.remove(destinazioneDAO);
	}
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param destinazione Il DTO della destinazione
	 * @return L'entità desiderata
	 */
	protected Destinazioni convertiInDAO (DestinazioneDTO destinazione) {
		return em.find(Destinazioni.class, destinazione.getId());		
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
