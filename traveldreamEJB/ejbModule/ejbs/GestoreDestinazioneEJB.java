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
		entity.setCitta(citta.convertiInEntita(destinazione.getCitta()));
		entity.setHotel(hotel.convertiInEntita(destinazione.getHotel()));
		entity.setPacchetto(pacchetto.convertiInEntita(destinazione.getPacchetto()));
		
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
		entity.setCitta(citta.convertiInEntita(destinazione.getCitta()));
		entity.setHotel(hotel.convertiInEntita(destinazione.getHotel()));
		
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
		Destinazioni entity = this.convertiInEntita(destinazione);
		Escursioni escursioneEntity = this.escursione.convertiInEntita(escursione);
		
		Attivita attivita = new Attivita();
		attivita.setDestinazione(entity);
		attivita.getId().setIdDestinazione(entity.getId());
		attivita.setEscursione(escursioneEntity);
		attivita.getId().setIdEscursione(escursioneEntity.getId());
		attivita.setNumPartecipanti(numeroPartecipanti);
		
		entity.addAttivita(attivita);
		
		em.persist(entity);		
	}

	/**
	 * Permette la modifica del numero di partecipanti ad una escursione
	 * @param destinazione L'identificativo della destinazione
	 * @param escursione L'identificativo dell'escursione
	 * @param numeroPartecipanti Il numero di partecipanti all'escursione
	 */
	@Override
	public void modificaDatiEscursione(DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti) {
		Destinazioni entity = this.convertiInEntita(destinazione);
		
		Escursioni escursioneEntity = this.escursione.convertiInEntita(escursione);
		
		Query q = em.createNamedQuery("Attivita.getAttivita", Attivita.class);
		q.setParameter("destinazione", entity);
		q.setParameter("escursione", escursioneEntity);
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
		Destinazioni entity = this.convertiInEntita(destinazione);
		
		Escursioni escursioneEntity = this.escursione.convertiInEntita(escursione);
		
		Query q = em.createNamedQuery("Attivita.getAttivita", Attivita.class);
		q.setParameter("destinazione", entity);
		q.setParameter("escursione", escursioneEntity);
		Attivita attivita = (Attivita) q.getSingleResult();
		
		entity.removeAttivita(attivita);
		
		em.remove(entity);
	}
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param destinazione Il DTO della destinazione
	 * @return L'entit� desiderata
	 */
	protected Destinazioni convertiInEntita (DestinazioneDTO destinazione) {
		return em.find(Destinazioni.class, destinazione.getId());		
	}	
	
	/**
	 * Permette la conversione da un'entit� al rispettivo DTO
	 * @param destinazione L'entit� da convertire
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
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param destinazione Il DTO dell'attivita
	 * @return L'entit� desiderata
	 */
	protected Attivita convertiInEntita (AttivitaDTO attivita) {
		Query q = em.createNamedQuery("Attivita.getAttivita", Attivita.class);
		q.setParameter("destinazione", attivita.getIdDestinazione());
		q.setParameter("escursione", attivita.getIdEscursione());
		return (Attivita) q.getSingleResult();
	}
	
	/**
	 * Permette la conversione da un'entit� al rispettivo DTO
	 * @param attivita L'entit� da convertire
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
