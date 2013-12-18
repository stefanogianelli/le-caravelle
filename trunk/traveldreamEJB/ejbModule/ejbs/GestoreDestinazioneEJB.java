package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.AttivitaDTO;
import dtos.DestinazioneDTO;
import dtos.EscursioneDTO;
import entities.Attivita;
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
	 * Permette l'aggiunta di una nuova destinazione
	 * @param destinazione I dati della nuova destinazione
	 * @param La destinazione creata
	 */
	protected Destinazioni creaDestinazione (DestinazioneDTO destinazione) {
		Destinazioni entity = new Destinazioni();
		
		entity.setDataArrivo(destinazione.getDataArrivo());
		entity.setDataPartenza(destinazione.getDataPartenza());
		entity.setCitta(citta.convertiInDAO(destinazione.getCitta()));
		entity.setHotel(hotel.convertiInDAO(destinazione.getHotel()));
		entity.setPacchetto(pacchetto.convertiInDAO(destinazione.getPacchetto()));
		
		return entity;
	}

	/**
	 * Permette la modifica dei dati di una destinazione
	 * @param destinazione La destinazione da modificare
	 */
	@Override
	public void modificaDatiDestinazione(DestinazioneDTO destinazione) {
		Destinazioni entity = em.find(Destinazioni.class, destinazione.getId());
		
		entity.setDataArrivo(destinazione.getDataArrivo());
		entity.setDataPartenza(destinazione.getDataPartenza());
		entity.setCitta(citta.convertiInDAO(destinazione.getCitta()));
		entity.setHotel(hotel.convertiInDAO(destinazione.getHotel()));
		
		em.merge(entity);		
	}
	
	/**
	 * Permette l'aggiunta di una escursione
	 * @param destinazione L'identificativo della destinazione
	 * @param escursione L'identificativo dell'escursione
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
	 * @param destinazione L'identificativo della destinazione
	 * @param escursione L'identificativo dell'escursione
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
	 * @param destinazione L'identificativo della destinazione
	 * @param escursione L'identificativo dell'escursione
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
		dto.setPacchetto(pacchetto.convertiInDTO(destinazione.getPacchetto()));		
		dto.setHotel(hotel.convertiInDTO(destinazione.getHotel()));
		List<AttivitaDTO> attivitaDTO = new ArrayList<AttivitaDTO>();
		for (Attivita a : destinazione.getAttivita()) {
			attivitaDTO.add(this.convertiInDTO(a));
		}
		dto.setAttivita(attivitaDTO);
		dto.setCitta(citta.convertiInDTO(destinazione.getCitta()));
		
		return dto;
	}
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param destinazione Il DTO dell'attivita
	 * @return L'entità desiderata
	 */
	protected Attivita convertiInDAO (AttivitaDTO attivita) {
		Query q = em.createNamedQuery("Attivita.getAttivita", Attivita.class);
		q.setParameter("destinazione", attivita.getIdDestinazione());
		q.setParameter("escursione", attivita.getIdEscursione());
		return (Attivita) q.getSingleResult();
	}
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param attivita L'entità da convertire
	 * @return Il relativo DTO
	 */
	protected AttivitaDTO convertiInDTO (Attivita attivita) {
		AttivitaDTO attivitaDTO = new AttivitaDTO();
		
		attivitaDTO.setIdDestinazione(attivita.getDestinazione().getId());
		attivitaDTO.setIdEscursione(attivita.getEscursione().getId());
		attivitaDTO.setNumeroPartecipanti(attivita.getNumPartecipanti());
		
		return attivitaDTO;
	}

}
