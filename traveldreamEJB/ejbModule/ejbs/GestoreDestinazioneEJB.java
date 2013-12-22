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
import eccezioni.CittaInesistenteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.HotelInesistenteException;
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
	 * @throws CittaInesistenteException  Quando non viene trovata la citt� nel database
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	protected Destinazioni creaDestinazione (DestinazioneDTO destinazione) throws CittaInesistenteException, HotelInesistenteException {
		Destinazioni entity = new Destinazioni();
		
		entity.setDataArrivo(destinazione.getDataArrivo());
		entity.setDataPartenza(destinazione.getDataPartenza());
		entity.setCitta(citta.convertiInEntita(destinazione.getCitta()));
		entity.setHotel(hotel.convertiInEntita(destinazione.getHotel()));
		
		return entity;
	}

	@Override
	public void modificaDatiDestinazione(DestinazioneDTO destinazione) throws CittaInesistenteException, HotelInesistenteException {
		Destinazioni entity = em.find(Destinazioni.class, destinazione.getId());
		
		entity.setDataArrivo(destinazione.getDataArrivo());
		entity.setDataPartenza(destinazione.getDataPartenza());
		entity.setHotel(hotel.convertiInEntita(destinazione.getHotel()));
		
		em.merge(entity);
	}
	
	@Override
	public void aggiuntaEscursione(int idDestinazione, int idEscursione, int numeroPartecipanti) throws EscursioneInesistenteException, DestinazioneInesistenteException {
		Destinazioni entity = this.convertiInEntita(idDestinazione);
		Escursioni escursioneEntity = escursione.convertiInEntita(idEscursione);
		
		Attivita attivita = new Attivita();		
		attivita.setEscursione(escursioneEntity);		
		attivita.setNumPartecipanti(numeroPartecipanti);
		
		entity.addAttivita(attivita);
		
		em.persist(entity);		
	}

	@Override
	public void modificaDatiEscursione(int idDestinazione, int idEscursione, int numeroPartecipanti) throws EscursioneInesistenteException, DestinazioneInesistenteException {
		Query q = em.createNamedQuery("Attivita.getAttivita", Attivita.class);
		q.setParameter("destinazione", idDestinazione);
		q.setParameter("escursione", idEscursione);
		Attivita attivita = (Attivita) q.getSingleResult();

		attivita.setNumPartecipanti(numeroPartecipanti);
		
		em.merge(attivita);		
	}

	@Override
	public void eliminaEscursione(int idDestinazione, int idEscursione) throws EscursioneInesistenteException, DestinazioneInesistenteException {
		Destinazioni entity = this.convertiInEntita(idDestinazione);
		
		Query q = em.createNamedQuery("Attivita.getAttivita", Attivita.class);
		q.setParameter("destinazione", idDestinazione);
		q.setParameter("escursione", idEscursione);
		Attivita attivita = (Attivita) q.getSingleResult();
		
		entity.removeAttivita(attivita);
		
		em.remove(entity);
	}
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param destinazione Il DTO della destinazione
	 * @return L'entit� desiderata
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 */
	protected Destinazioni convertiInEntita (DestinazioneDTO destinazione) throws DestinazioneInesistenteException {
		Destinazioni destinazioneEntity = em.find(Destinazioni.class, destinazione.getId());
		if (destinazioneEntity != null) 
			return destinazioneEntity;
		else
			throw new DestinazioneInesistenteException ();
	}	
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entit�
	 * @param idDestinazione L'identificativo della destinazione
	 * @return L'entit� desiderata
	 * @throws DestinazioneInesistenteException Quando la destinazione non viene trovata nel database
	 */
	protected Destinazioni convertiInEntita (int idDestinazione) throws DestinazioneInesistenteException {
		Destinazioni destinazioneEntity = em.find(Destinazioni.class, idDestinazione);
		if (destinazioneEntity != null) 
			return destinazioneEntity;
		else
			throw new DestinazioneInesistenteException ();
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
		q.setParameter("destinazione", attivita.getDestinazione().getId());
		q.setParameter("escursione", attivita.getEscursione().getId());
		return (Attivita) q.getSingleResult();
	}
	
	/**
	 * Permette la conversione da un'entit� al rispettivo DTO
	 * @param attivita L'entit� da convertire
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
