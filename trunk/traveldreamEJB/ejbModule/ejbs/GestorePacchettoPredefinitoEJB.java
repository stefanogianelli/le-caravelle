package ejbs;

import interfaces.GestoreCittaLocal;
import interfaces.GestoreCollegamentoLocal;
import interfaces.GestoreEscursioneLocal;
import interfaces.GestoreHotelLocal;
import interfaces.GestorePacchettoPredefinitoLocal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import dtos.AttivitaPredDTO;
import dtos.CittaDTO;
import dtos.CollegamentoDTO;
import dtos.HotelDTO;
import dtos.PacchettoPredefinitoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.InsertException;
import eccezioni.PacchettoInesistenteException;
import entities.AttivitaPred;
import entities.Citta;
import entities.Collegamenti;
import entities.DatePartenza;
import entities.Durate;
import entities.Escursioni;
import entities.PacchettiPredefiniti;

/**
 * Session Bean implementation class GestorePacchettoPredefinitoEJB
 */
@Stateless
public class GestorePacchettoPredefinitoEJB implements GestorePacchettoPredefinito, GestorePacchettoPredefinitoLocal {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreHotelLocal hotel;
	
	@EJB
	private GestoreCollegamentoLocal collegamento;
	
	@EJB
	private GestoreEscursioneLocal escursione;
	
	@EJB
	private GestoreCittaLocal citta;
	
	@Override
	public PacchettoPredefinitoDTO getPacchetto (int idPacchetto) throws PacchettoInesistenteException {
		TypedQuery<PacchettiPredefiniti> q = em.createNamedQuery("PacchettiPredefiniti.getPacchettoDaId", PacchettiPredefiniti.class);
		q.setParameter("id", idPacchetto);
		PacchettiPredefiniti pacchetto;
		try {
			pacchetto = q.getSingleResult();
		} catch (NoResultException e) {
			throw new PacchettoInesistenteException();
		}
		return this.convertiInDTO(pacchetto);		
	}
    
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
	public List<PacchettoPredefinitoDTO> elencoPacchettoPerCitta (String nomeCitta) {
		TypedQuery<PacchettiPredefiniti> q = em.createNamedQuery("PacchettiPredefiniti.getPacchettoDaCitta", PacchettiPredefiniti.class);
		q.setParameter("citta", nomeCitta);
		List<PacchettoPredefinitoDTO> elenco = new ArrayList<PacchettoPredefinitoDTO>();
		for (PacchettiPredefiniti p : q.getResultList()) {
			elenco.add(this.convertiInDTO(p));
		}
		return elenco;
	}

	@Override
	public int creaPacchetto(PacchettoPredefinitoDTO pacchetto) throws HotelInesistenteException, CittaInesistenteException {
		PacchettiPredefiniti entity = new PacchettiPredefiniti();		
		
		entity.setNome(pacchetto.getNome());
		entity.setPrezzo(pacchetto.getPrezzo());
		for (CittaDTO c : pacchetto.getCittaPartenza()) {
			entity.addCitta(citta.getCitta(c.getNome()));
		}
		for (Date d : pacchetto.getDatePartenza()) {
			DatePartenza data = new DatePartenza();
			data.setData(d);			
			entity.addDataPartenza(data);
		}
		for (Integer i : pacchetto.getDurate()) {
			Durate durata = new Durate();
			durata.setDurata(i);	
			entity.addDurata(durata);
		}
		entity.setHotel(hotel.convertiInEntita(pacchetto.getHotel()));
		
		em.persist(entity);
		em.flush();
		
		return entity.getId();
	}
	
	@Override
	public void aggiuntaCittaPartenza (PacchettoPredefinitoDTO pacchetto, String nomeCitta) throws PacchettoInesistenteException, CittaInesistenteException, InsertException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		//verifico che la città non sia già stata inserita
		for (Citta c : entity.getCittaPartenza()) {
			if (c.getNome().equalsIgnoreCase(nomeCitta))
				throw new InsertException();
		}

		entity.addCitta(citta.getCitta(nomeCitta));
			
		em.merge(entity);
	}
	
	@Override
	public void rimuoviCittaPartenza (PacchettoPredefinitoDTO pacchetto, CittaDTO citta) throws PacchettoInesistenteException, CittaInesistenteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);

		entity.removeCitta(this.citta.convertiInEntita(citta));
		
		em.merge(entity);
	}
	
	@Override
	public void aggiuntaDataPartenza(PacchettoPredefinitoDTO pacchetto, Date data) throws PacchettoInesistenteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);			

		DatePartenza dataPartenza = new DatePartenza();
		dataPartenza.setData(data);
		
		entity.addDataPartenza(dataPartenza);
		
		em.merge(entity);
	}

	@Override
	public void rimuoviDataPartenza(PacchettoPredefinitoDTO pacchetto, Date data) throws PacchettoInesistenteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);

		TypedQuery<DatePartenza> q = em.createNamedQuery("DatePartenza.getDataPartenza", DatePartenza.class);
		q.setParameter("pacchetto", entity);
		q.setParameter("data", data);
		DatePartenza dataPartenza = q.getSingleResult();
		
		entity.removeDataPartenza(dataPartenza);
		
		em.merge(entity);
	}

	@Override
	public void aggiuntaDurata(PacchettoPredefinitoDTO pacchetto, int durata) throws PacchettoInesistenteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);		

		Durate nuovaDurata = new Durate();
		nuovaDurata.setDurata(durata);
		
		entity.addDurata(nuovaDurata);
		
		em.merge(entity);
	}

	@Override
	public void rimuoviDurata(PacchettoPredefinitoDTO pacchetto, int durata) throws PacchettoInesistenteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		TypedQuery<Durate> q = em.createNamedQuery("Durate.getDurata", Durate.class);
		q.setParameter("pacchetto", entity);
		q.setParameter("durata", durata);
		Durate d = q.getSingleResult();
		
		entity.removeDurata(d);
		
		em.merge(entity);
	}
	
	@Override
	public void aggiuntaCollegamento(int idPacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException, PacchettoInesistenteException, InsertException {
		PacchettiPredefiniti entity = this.convertiInEntita(idPacchetto);
		
		//verifico che il collegamento non sia già stato inserito
		for (Collegamenti c : entity.getCollegamenti()) {
			if (c.getCodice() == collegamento.getCodice())
				throw new InsertException();
		}
		
		entity.addCollegamento(this.collegamento.convertiInEntita(collegamento));
		
		em.merge(entity);		
	}

	@Override
	public void rimuoviCollegamento(PacchettoPredefinitoDTO pacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException, PacchettoInesistenteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		entity.removeCollegamento(this.collegamento.convertiInEntita(collegamento));
		
		em.merge(entity);
	}

	@Override
	public void aggiuntaEscursione(int idPacchetto, int idEscursione) throws EscursioneInesistenteException, PacchettoInesistenteException, InsertException {
		PacchettiPredefiniti entity = this.convertiInEntita(idPacchetto);
		
		Escursioni escursioneEntity = escursione.convertiInEntita(idEscursione);
		
		//controllo che l'escursione non sia già stata aggiunta
		TypedQuery<AttivitaPred> q = em.createNamedQuery("AttivitaPred.getAttivita", AttivitaPred.class);
		q.setParameter("pacchetto", entity);
		q.setParameter("escursione", escursioneEntity);
		
		if (q.getResultList().isEmpty()) {
			//controllo che l'escursione sia nella stessa regione dell'hotel
			if (entity.getHotel().getCitta().getRegione().equals(escursioneEntity.getCitta().getRegione())) {	
				AttivitaPred attivita = new AttivitaPred();
				attivita.setEscursione(escursioneEntity);
				
				entity.addAttivita(attivita);
				
				em.merge(entity);	
			} else
				throw new InsertException("L'escursione non è nella stessa regione dell'hotel");
		} else
			throw new InsertException("Escursione già inserita");
	}

	@Override
	public void rimuoviEscursione(AttivitaPredDTO attivita) throws EscursioneInesistenteException, PacchettoInesistenteException {
		AttivitaPred attivitaEntity = this.convertiInEntita(attivita);
		PacchettiPredefiniti entity = this.convertiInEntita(attivita.getPacchetto());
		
		entity.removeAttivita(attivitaEntity);
		
		em.merge(entity);		
	}

	@Override
	public void modificaNomePacchetto (PacchettoPredefinitoDTO pacchetto) throws PacchettoInesistenteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);

		entity.setNome(pacchetto.getNome());
			
		em.merge(entity);
	}
	
	@Override
	public void modificaPrezzo (PacchettoPredefinitoDTO pacchetto) throws PacchettoInesistenteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		entity.setPrezzo(pacchetto.getPrezzo());
		
		em.merge(entity);
	}
	
	@Override
	public void modificaHotel (int idPacchetto, HotelDTO hotel) throws PacchettoInesistenteException, HotelInesistenteException, InsertException {
		PacchettiPredefiniti entity = this.convertiInEntita(idPacchetto);
		
		//verifico che l'hotel aggiunto non sia in una delle città di partenza inserite nel pacchetto
		for (Citta c : entity.getCittaPartenza()) {
			if (c.getNome().equals(hotel.getCitta().getNome()))
				throw new InsertException();
		}
		
		entity.setHotel(this.hotel.convertiInEntita(hotel));
		
		em.merge(entity);
	}

	@Override
	public void eliminaPacchetto(int idPacchetto) throws PacchettoInesistenteException {
		em.remove(this.convertiInEntita(idPacchetto));
	}
	
	@Override
	public PacchettiPredefiniti convertiInEntita (PacchettoPredefinitoDTO pacchetto) throws PacchettoInesistenteException {
		PacchettiPredefiniti pacchettoEntity = em.find(PacchettiPredefiniti.class, pacchetto.getId());
		if (pacchettoEntity != null)
			return pacchettoEntity;
		else
			throw new PacchettoInesistenteException ();
	}
	
	@Override
	public PacchettiPredefiniti convertiInEntita (int idPacchetto) throws PacchettoInesistenteException {
		PacchettiPredefiniti pacchettoEntity = em.find(PacchettiPredefiniti.class, idPacchetto);
		if (pacchettoEntity != null)
			return pacchettoEntity;
		else
			throw new PacchettoInesistenteException ();
	}
	
	@Override
	public PacchettoPredefinitoDTO convertiInDTO (PacchettiPredefiniti pacchetto) {
		PacchettoPredefinitoDTO pacchettoDTO = new PacchettoPredefinitoDTO();
		
		pacchettoDTO.setId(pacchetto.getId());
		pacchettoDTO.setNome(pacchetto.getNome());
		pacchettoDTO.setPrezzo(pacchetto.getPrezzo());
		List<CittaDTO> cittaPartenza = new ArrayList<CittaDTO>();
		for (Citta c : pacchetto.getCittaPartenza()) {
			cittaPartenza.add(citta.convertiInDTO(c));
		}
		pacchettoDTO.setCittaPartenza(cittaPartenza);
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
		List<CollegamentoDTO> collegamenti = new ArrayList<CollegamentoDTO>();
		for (Collegamenti c : pacchetto.getCollegamenti()) {
			collegamenti.add(this.collegamento.convertiInDTO(c));
		}
		pacchettoDTO.setCollegamenti(collegamenti);
		List<AttivitaPredDTO> attivita = new ArrayList<AttivitaPredDTO>();
		for (AttivitaPred a : pacchetto.getAttivita()) {
			attivita.add(this.convertiInDTO(a, pacchettoDTO));			
		}
		pacchettoDTO.setAttivita(attivita);
		pacchettoDTO.setHotel(hotel.convertiInDTO(pacchetto.getHotel()));
		
		return pacchettoDTO;
	}
	
	private AttivitaPred convertiInEntita (AttivitaPredDTO attivita) {
		TypedQuery<AttivitaPred> q = em.createNamedQuery("AttivitaPred.getAttivitaDaId", AttivitaPred.class);
		q.setParameter("pacchetto", attivita.getPacchetto().getId());
		q.setParameter("escursione", attivita.getEscursione().getId());
		return q.getSingleResult();
	}
	
	private AttivitaPredDTO convertiInDTO (AttivitaPred attivita, PacchettoPredefinitoDTO pacchetto) {
		AttivitaPredDTO dto = new AttivitaPredDTO();
		dto.setEscursione(escursione.convertiInDTO(attivita.getEscursione()));
		dto.setPacchetto(pacchetto);
		return dto;
	}
}
