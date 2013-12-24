package ejbs;

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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.CollegamentoDTO;
import dtos.EscursioneDTO;
import dtos.PacchettoPredefinitoDTO;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.EscursioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.PacchettoInesistenteException;
import entities.AttivitaPred;
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
	
    /**
     * Default constructor. 
     */
    public GestorePacchettoPredefinitoEJB() {
        
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
	public void creaPacchetto(PacchettoPredefinitoDTO pacchetto) throws HotelInesistenteException {
		PacchettiPredefiniti entity = new PacchettiPredefiniti();
		
		entity.setNome(pacchetto.getNome());
		entity.setPrezzo(pacchetto.getPrezzo());
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
	public void aggiuntaDataPartenza(PacchettoPredefinitoDTO pacchetto, Date data) {
		PacchettiPredefiniti entity = new PacchettiPredefiniti();
		
		DatePartenza dataPartenza = new DatePartenza();
		dataPartenza.setData(data);
		
		entity.addDataPartenza(dataPartenza);
		
		em.merge(entity);
	}

	@Override
	public void rimuoviDataPartenza(PacchettoPredefinitoDTO pacchetto, Date data) {
		PacchettiPredefiniti entity = new PacchettiPredefiniti();
		
		Query q = em.createNamedQuery("DatePartenza.getDataPartenza", DatePartenza.class);
		q.setParameter("pacchetto", entity);
		q.setParameter("data", data);
		DatePartenza dataPartenza = (DatePartenza) q.getSingleResult();
		
		entity.removeDataPartenza(dataPartenza);
		
		em.merge(entity);
	}

	@Override
	public void aggiuntaDurata(PacchettoPredefinitoDTO pacchetto, int durata) {
		PacchettiPredefiniti entity = new PacchettiPredefiniti();
		
		Durate nuovaDurata = new Durate();
		nuovaDurata.setDurata(durata);
		
		entity.addDurata(nuovaDurata);
		
		em.merge(entity);
	}

	@Override
	public void rimuoviDurata(PacchettoPredefinitoDTO pacchetto, int durata) {
		PacchettiPredefiniti entity = new PacchettiPredefiniti();
		
		Query q = em.createNamedQuery("DatePartenza.getDataPartenza", DatePartenza.class);
		q.setParameter("pacchetto", entity);
		q.setParameter("durata", durata);
		Durate d = (Durate) q.getSingleResult();
		
		entity.removeDurata(d);
		
		em.merge(entity);	
	}
	
	@Override
	public void aggiuntaCollegamento(PacchettoPredefinitoDTO pacchetto, CollegamentoDTO collegamento) throws CollegamentoInesistenteException, PacchettoInesistenteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
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
	public void salvaPacchetto(PacchettoPredefinitoDTO pacchetto) throws HotelInesistenteException, PacchettoInesistenteException {
		PacchettiPredefiniti entity = this.convertiInEntita(pacchetto);
		
		entity.setNome(pacchetto.getNome());
		entity.setPrezzo(pacchetto.getPrezzo());
		entity.setHotel(hotel.convertiInEntita(pacchetto.getHotel()));
		
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
	public PacchettoPredefinitoDTO convertiInDTO (PacchettiPredefiniti pacchetto) {
		PacchettoPredefinitoDTO pacchettoDTO = new PacchettoPredefinitoDTO();
		
		pacchettoDTO.setId(pacchetto.getId());
		pacchettoDTO.setNome(pacchetto.getNome());
		pacchettoDTO.setPrezzo(pacchetto.getPrezzo());
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
		pacchettoDTO.setHotel(hotel.convertiInDTO(pacchetto.getHotel()));
		
		return pacchettoDTO;
	}
}
