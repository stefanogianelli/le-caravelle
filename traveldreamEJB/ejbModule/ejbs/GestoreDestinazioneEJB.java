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
import eccezioni.CittaInesistenteException;
import eccezioni.EscursioneInesistenteException;
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
	 * @throws CittaInesistenteException  Quando non viene trovata la città nel database
	 */
	protected Destinazioni creaDestinazione (DestinazioneDTO destinazione) throws CittaInesistenteException {
		Destinazioni entity = new Destinazioni();
		
		entity.setDataArrivo(destinazione.getDataArrivo());
		entity.setDataPartenza(destinazione.getDataPartenza());
		entity.setCitta(citta.convertiInEntita(destinazione.getCitta()));
		entity.setHotel(hotel.convertiInEntita(destinazione.getHotel()));
		entity.setPacchetto(pacchetto.convertiInEntita(destinazione.getPacchetto()));
		
		return entity;
	}

	@Override
	public void modificaDatiDestinazione(DestinazioneDTO destinazione) throws CittaInesistenteException {
		Destinazioni entity = em.find(Destinazioni.class, destinazione.getId());
		
		entity.setDataArrivo(destinazione.getDataArrivo());
		entity.setDataPartenza(destinazione.getDataPartenza());
		entity.setCitta(citta.convertiInEntita(destinazione.getCitta()));
		entity.setHotel(hotel.convertiInEntita(destinazione.getHotel()));
		
		em.merge(entity);
	}
	
	@Override
	public void aggiuntaEscursione(DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti) throws EscursioneInesistenteException {
		Destinazioni entity = this.convertiInEntita(destinazione);
		Escursioni escursioneEntity = this.escursione.convertiInEntita(escursione);
		
		Attivita attivita = new Attivita();		
		attivita.setEscursione(escursioneEntity);		
		attivita.setNumPartecipanti(numeroPartecipanti);
		
		entity.addAttivita(attivita);
		
		em.persist(entity);		
	}

	@Override
	public void modificaDatiEscursione(DestinazioneDTO destinazione, EscursioneDTO escursione, int numeroPartecipanti) throws EscursioneInesistenteException {
		Destinazioni entity = this.convertiInEntita(destinazione);
		
		Escursioni escursioneEntity = this.escursione.convertiInEntita(escursione);
		
		Query q = em.createNamedQuery("Attivita.getAttivita", Attivita.class);
		q.setParameter("destinazione", entity);
		q.setParameter("escursione", escursioneEntity);
		Attivita attivita = (Attivita) q.getSingleResult();

		attivita.setNumPartecipanti(numeroPartecipanti);
		
		em.merge(attivita);		
	}

	@Override
	public void eliminaEscursione(DestinazioneDTO destinazione, EscursioneDTO escursione) throws EscursioneInesistenteException {
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
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param destinazione Il DTO della destinazione
	 * @return L'entità desiderata
	 */
	protected Destinazioni convertiInEntita (DestinazioneDTO destinazione) {
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
	protected Attivita convertiInEntita (AttivitaDTO attivita) {
		Query q = em.createNamedQuery("Attivita.getAttivita", Attivita.class);
		q.setParameter("destinazione", attivita.getDestinazione());
		q.setParameter("escursione", attivita.getEscursione());
		return (Attivita) q.getSingleResult();
	}
	
	/**
	 * Permette la conversione da un'entità al rispettivo DTO
	 * @param attivita L'entità da convertire
	 * @return Il relativo DTO
	 */
	protected AttivitaDTO convertiInDTO (Attivita attivita) {
		AttivitaDTO attivitaDTO = new AttivitaDTO();
		
		attivitaDTO.setDestinazione(this.convertiInDTO(attivita.getDestinazione()));
		attivitaDTO.setEscursione(escursione.convertiInDTO(attivita.getEscursione()));
		attivitaDTO.setNumeroPartecipanti(attivita.getNumPartecipanti());
		
		return attivitaDTO;
	}

}
