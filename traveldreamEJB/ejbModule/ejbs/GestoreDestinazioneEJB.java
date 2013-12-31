package ejbs;

import interfaces.GestoreCittaLocal;
import interfaces.GestoreDestinazioneLocal;
import interfaces.GestoreEscursioneLocal;
import interfaces.GestoreHotelLocal;
import interfaces.GestorePacchettoLocal;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.AttivitaDTO;
import dtos.DestinazioneDTO;
import dtos.HotelDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.EntitaEsistenteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.InsertException;
import eccezioni.NumeroPartecipantiException;
import entities.Attivita;
import entities.Destinazioni;
import entities.Escursioni;

/**
 * Session Bean implementation class GestoreDestinazioneEJB
 */
@Stateless
public class GestoreDestinazioneEJB implements GestoreDestinazione, GestoreDestinazioneLocal {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreHotelLocal hotel;
	
	@EJB
	private GestorePacchettoLocal pacchetto;
	
	@EJB
	private GestoreEscursioneLocal escursione;
	
	@EJB
	private GestoreCittaLocal citta;
	
    @Override
    public Destinazioni creaDestinazione (DestinazioneDTO destinazione) throws CittaInesistenteException, HotelInesistenteException {
		Destinazioni entity = new Destinazioni();
		
		entity.setDataArrivo(destinazione.getDataArrivo());
		entity.setDataPartenza(destinazione.getDataPartenza());
		entity.setCitta(citta.getCitta(destinazione.getCitta().getNome()));
		entity.setHotel(hotel.convertiInEntita(destinazione.getHotel()));
		
		return entity;
	}

    @Override
	public void modificaDateDestinazione(DestinazioneDTO destinazione) throws CittaInesistenteException {
		Destinazioni entity = em.find(Destinazioni.class, destinazione.getId());
		
		entity.setDataArrivo(destinazione.getDataArrivo());
		entity.setDataPartenza(destinazione.getDataPartenza());
	}
    
    @Override
    public void modificaHotel (int idDestinazione, HotelDTO hotel) throws HotelInesistenteException, DestinazioneInesistenteException, InsertException {
    	Destinazioni entity = this.convertiInEntita(idDestinazione);
    			
		//controllo che l'hotel sia nella stessa citt� della destinazione
		if (entity.getCitta().getNome().equals(hotel.getCitta().getNome())) {
			entity.setHotel(this.hotel.convertiInEntita(hotel));
		} else
			throw new InsertException();
		
		em.merge(entity);
    }
	
	@Override
	public void aggiuntaEscursione(int idDestinazione, int idEscursione, int numeroPartecipanti) throws EscursioneInesistenteException, DestinazioneInesistenteException, EntitaEsistenteException, NumeroPartecipantiException {
		Destinazioni entity = this.convertiInEntita(idDestinazione);
		
		//Controllo che il numero di partecipanti all'escursione sia minore o uguale al numero di partecipanti al viaggio
		if (numeroPartecipanti <= entity.getPacchetto().getNumPartecipanti()) {
			Escursioni escursioneEntity = escursione.convertiInEntita(idEscursione);
			
			//controllo che l'escursione non sia gi� stata aggiunta
			Query q = em.createNamedQuery("Attivita.getAttivita", Attivita.class);
			q.setParameter("destinazione", entity);
			q.setParameter("escursione", escursioneEntity);
			
			if (q.getResultList().isEmpty()) {
				Attivita attivita = new Attivita();		
				attivita.setEscursione(escursioneEntity);		
				attivita.setNumPartecipanti(numeroPartecipanti);
				
				entity.addAttivita(attivita);
				//aggiorno il prezzo del pacchetto
				entity.getPacchetto().setPrezzo(entity.getPacchetto().getPrezzo() + escursioneEntity.getPrezzo()*numeroPartecipanti);
				
				em.merge(entity);		
			} else
				throw new EntitaEsistenteException();
		} else
			throw new NumeroPartecipantiException();
	}

	@Override
	public void modificaDatiEscursione(AttivitaDTO attivita) throws EscursioneInesistenteException, DestinazioneInesistenteException, NumeroPartecipantiException {
		Attivita attivitaEntity = this.convertiInEntita(attivita);

		//Controllo che il numero di partecipanti all'escursione sia minore o uguale al numero di partecipanti al viaggio
		if (attivita.getNumeroPartecipanti() <= attivitaEntity.getDestinazione().getPacchetto().getNumPartecipanti()) {
			//aggiorno il prezzo del pacchetto
			if (attivita.getNumeroPartecipanti() > attivitaEntity.getNumPartecipanti())
				attivitaEntity.getDestinazione().getPacchetto().setPrezzo(attivitaEntity.getDestinazione().getPacchetto().getPrezzo() + attivitaEntity.getEscursione().getPrezzo() * (attivita.getNumeroPartecipanti() - attivitaEntity.getNumPartecipanti()));
			else
				attivitaEntity.getDestinazione().getPacchetto().setPrezzo(attivitaEntity.getDestinazione().getPacchetto().getPrezzo() - attivitaEntity.getEscursione().getPrezzo() * (attivitaEntity.getNumPartecipanti() - attivita.getNumeroPartecipanti()));
			attivitaEntity.setNumPartecipanti(attivita.getNumeroPartecipanti());		
			em.merge(attivitaEntity.getDestinazione().getPacchetto());		
		} else
			throw new NumeroPartecipantiException();
	}

	@Override
	public void eliminaEscursione(AttivitaDTO attivita) throws EscursioneInesistenteException, DestinazioneInesistenteException {
		Attivita attivitaEntity = this.convertiInEntita(attivita);
		Destinazioni destinazione = attivitaEntity.getDestinazione();
		
		//aggiorno il prezzo del pacchetto
		destinazione.getPacchetto().setPrezzo(destinazione.getPacchetto().getPrezzo() - attivitaEntity.getEscursione().getPrezzo()*attivitaEntity.getNumPartecipanti());
		
		destinazione.removeAttivita(attivitaEntity);
		
		em.merge(destinazione.getPacchetto());
	}
	
	@Override
	public Destinazioni convertiInEntita (DestinazioneDTO destinazione) throws DestinazioneInesistenteException {
		Destinazioni destinazioneEntity = em.find(Destinazioni.class, destinazione.getId());
		if (destinazioneEntity != null) 
			return destinazioneEntity;
		else
			throw new DestinazioneInesistenteException ();
	}	
	
	@Override
	public Destinazioni convertiInEntita (int idDestinazione) throws DestinazioneInesistenteException {
		Destinazioni destinazioneEntity = em.find(Destinazioni.class, idDestinazione);
		if (destinazioneEntity != null) 
			return destinazioneEntity;
		else
			throw new DestinazioneInesistenteException ();
	}	
	
	@Override
	public DestinazioneDTO convertiInDTO (Destinazioni destinazione) {
		DestinazioneDTO dto = new DestinazioneDTO();
		
		dto.setId(destinazione.getId());
		dto.setDataArrivo(destinazione.getDataArrivo());
		dto.setDataPartenza(destinazione.getDataPartenza());	
		dto.setHotel(hotel.convertiInDTO(destinazione.getHotel()));
		List<AttivitaDTO> attivitaDTO = new ArrayList<AttivitaDTO>();
		for (Attivita a : destinazione.getAttivita()) {
			attivitaDTO.add(this.convertiInDTO(a));
			attivitaDTO.get(attivitaDTO.size() - 1).setDestinazione(dto);
		}
		dto.setAttivita(attivitaDTO);
		dto.setCitta(citta.convertiInDTO(destinazione.getCitta()));
		
		return dto;
	}
	
	@Override
	public Attivita convertiInEntita (AttivitaDTO attivita) {
		Query q = em.createNamedQuery("Attivita.getAttivitaDaId", Attivita.class);
		q.setParameter("destinazione", attivita.getDestinazione().getId());
		q.setParameter("escursione", attivita.getEscursione().getId());
		return (Attivita) q.getSingleResult();
	}
	
	@Override
	public AttivitaDTO convertiInDTO (Attivita attivita) {
		AttivitaDTO attivitaDTO = new AttivitaDTO();
		
		attivitaDTO.setEscursione(escursione.convertiInDTO(attivita.getEscursione()));
		attivitaDTO.setNumeroPartecipanti(attivita.getNumPartecipanti());
		
		return attivitaDTO;
	}

}
