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
import javax.persistence.Query;

import dtos.CittaDTO;
import dtos.CollegamentoDTO;
import dtos.EscursioneDTO;
import dtos.HotelDTO;
import dtos.PacchettoPredefinitoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.DeleteException;
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
		Query q = em.createNamedQuery("PacchettiPredefiniti.getPacchettoDaId", PacchettiPredefiniti.class);
		q.setParameter("id", idPacchetto);
		PacchettiPredefiniti pacchetto;
		try {
			pacchetto = (PacchettiPredefiniti) q.getSingleResult();
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
	public void creaPacchetto(PacchettoPredefinitoDTO pacchetto) throws HotelInesistenteException, CittaInesistenteException {
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
	}
	
	@Override
	public void aggiuntaCittaPartenza (PacchettoPredefinitoDTO pacchetto, String nomeCitta) throws PacchettoInesistenteException, CittaInesistenteException, InsertException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		for (Citta c : entity.getCittaPartenza()) {
			if (c.getNome().equalsIgnoreCase(nomeCitta))
				throw new InsertException();
		}

		entity.addCitta(citta.getCitta(nomeCitta));
			
		em.merge(entity);
	}
	
	@Override
	public void rimuoviCittaPartenza (PacchettoPredefinitoDTO pacchetto, CittaDTO citta) throws PacchettoInesistenteException, CittaInesistenteException, DeleteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		if (entity.getCittaPartenza().size() > 1) {
			entity.removeCitta(this.citta.convertiInEntita(citta));
			em.merge(entity);
		} else
			throw new DeleteException();
	}
	
	@Override
	public void aggiuntaDataPartenza(PacchettoPredefinitoDTO pacchetto, Date data) throws PacchettoInesistenteException, InsertException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);		
		
		Query q = em.createNamedQuery("DatePartenza.getDataPartenza", DatePartenza.class);
		q.setParameter("pacchetto", entity);
		q.setParameter("data", data);
		
		if (q.getResultList().isEmpty()) {
			DatePartenza dataPartenza = new DatePartenza();
			dataPartenza.setData(data);
			
			entity.addDataPartenza(dataPartenza);
			
			em.merge(entity);
		} else
			throw new InsertException();
	}

	@Override
	public void rimuoviDataPartenza(PacchettoPredefinitoDTO pacchetto, Date data) throws PacchettoInesistenteException, DeleteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		if (entity.getDatePartenza().size() > 1) {
			Query q = em.createNamedQuery("DatePartenza.getDataPartenza", DatePartenza.class);
			q.setParameter("pacchetto", entity);
			q.setParameter("data", data);
			DatePartenza dataPartenza = (DatePartenza) q.getSingleResult();
			
			entity.removeDataPartenza(dataPartenza);
			
			em.merge(entity);
		} else
			throw new DeleteException();
	}

	@Override
	public void aggiuntaDurata(PacchettoPredefinitoDTO pacchetto, int durata) throws PacchettoInesistenteException, InsertException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		Query q = em.createNamedQuery("Durate.getDurata", Durate.class);
		q.setParameter("pacchetto", entity);
		q.setParameter("durata", durata);
		
		if (q.getResultList().isEmpty()) {
			Durate nuovaDurata = new Durate();
			nuovaDurata.setDurata(durata);
			
			entity.addDurata(nuovaDurata);
			
			em.merge(entity);
		} else
			throw new InsertException();
	}

	@Override
	public void rimuoviDurata(PacchettoPredefinitoDTO pacchetto, int durata) throws PacchettoInesistenteException, DeleteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		if (entity.getDurate().size() > 1) {
			Query q = em.createNamedQuery("Durate.getDurata", Durate.class);
			q.setParameter("pacchetto", entity);
			q.setParameter("durata", durata);
			Durate d = (Durate) q.getSingleResult();
			
			entity.removeDurata(d);
			
			em.merge(entity);
		} else
			throw new DeleteException();
	}
	
	@Override
	public void aggiuntaCollegamento(int idPacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException, PacchettoInesistenteException, InsertException {
		PacchettiPredefiniti entity = this.convertiInEntita(idPacchetto);
		
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
	public void aggiuntaEscursione(PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione) throws EscursioneInesistenteException, PacchettoInesistenteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		Escursioni escursioneEntity = this.escursione.convertiInEntita(escursione);
		
		AttivitaPred attivita = new AttivitaPred();
		attivita.setEscursione(escursioneEntity);
		
		entity.addAttivita(attivita);
		
		em.merge(entity);		
	}

	@Override
	public void rimuoviEscursione(PacchettoPredefinitoDTO pacchetto, EscursioneDTO escursione) throws EscursioneInesistenteException, PacchettoInesistenteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		Escursioni escursioneEntity = this.escursione.convertiInEntita(escursione);
		
		Query q = em.createNamedQuery("AttivitaPred.getAttivita", AttivitaPred.class);
		q.setParameter("pacchetto", entity);
		q.setParameter("escursione", escursioneEntity);
		AttivitaPred attivita = (AttivitaPred) q.getSingleResult();
		
		entity.removeAttivita(attivita);
		
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
	public void modificaHotel (int idPacchetto, HotelDTO hotel) throws PacchettoInesistenteException, HotelInesistenteException {
		PacchettiPredefiniti entity = this.convertiInEntita(idPacchetto);
		
		entity.setHotel(this.hotel.convertiInEntita(hotel));
		
		em.merge(entity);
	}

	@Override
	public void eliminaPacchetto(PacchettoPredefinitoDTO pacchetto) throws PacchettoInesistenteException {
		em.remove(this.convertiInEntita(pacchetto));
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
		pacchettoDTO.setHotel(hotel.convertiInDTO(pacchetto.getHotel()));
		
		return pacchettoDTO;
	}
}
