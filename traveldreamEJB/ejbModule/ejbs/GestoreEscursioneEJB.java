package ejbs;

import interfaces.GestoreCittaLocal;
import interfaces.GestoreEscursioneLocal;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import dtos.EscursioneDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.EntitaEsistenteException;
import eccezioni.EscursioneInesistenteException;
import entities.Attivita;
import entities.AttivitaPred;
import entities.Citta;
import entities.Escursioni;
import enums.CategoriaEscursione;

/**
 * Session Bean implementation class GestoreEscursioneEJB
 */
@Stateless
public class GestoreEscursioneEJB implements GestoreEscursione, GestoreEscursioneLocal {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreCittaLocal citta;
	
	@Override
	public EscursioneDTO getEscursione (int idEscursione) throws EscursioneInesistenteException {
		return this.convertiInDTO(this.convertiInEntita(idEscursione));
	}
	   
	@Override
	public List<EscursioneDTO> elencoEscursioni() {
		List<Escursioni> escursioni = em.createNamedQuery("Escursioni.elenco", Escursioni.class).getResultList();
		Collections.sort(escursioni);
		List<EscursioneDTO> dto = new ArrayList<EscursioneDTO>();
		for (Escursioni e : escursioni) {
			dto.add(this.convertiInDTO(e));
		}
		return dto;
	}
	
	@Override
	public List<EscursioneDTO> elencoEscursioni(Date data, String regione, String categoria) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Escursioni> cq = cb.createQuery(Escursioni.class);
		Root<Escursioni> escursione = cq.from(Escursioni.class);
		List<Predicate> predicati = new ArrayList<Predicate>();
		//regione
		Join<Escursioni, Citta> citta = escursione.join("citta");
		predicati.add(cb.equal(citta.get("regione"), regione));
		//categoria
		if (categoria != null && !categoria.isEmpty() && !categoria.equals("Qualsiasi")) {
			for (CategoriaEscursione c : CategoriaEscursione.values()) {
				if (categoria.equals(c.getLabel())) {
					predicati.add(cb.equal(escursione.get("categoria"), c));
					break;
				}
			}
		}
		//data
		if (data != null) {
			predicati.add(cb.lessThanOrEqualTo(escursione.<Date>get("data"), data));
			predicati.add(cb.greaterThanOrEqualTo(escursione.<Date>get("data"), data));
		}
		//seleziono solo le escursioni attive
		predicati.add(cb.equal(escursione.get("attivo"), 1));
		cq.where(predicati.toArray(new Predicate[]{}));
		TypedQuery<Escursioni> q = em.createQuery(cq);
		List<Escursioni> escursioni = q.getResultList();
		Collections.sort(escursioni);
		List<EscursioneDTO> dto = new ArrayList<EscursioneDTO>();
		for (Escursioni e : escursioni) {
			dto.add(this.convertiInDTO(e));			
		}
		return dto;
	}

	@Override
	public int creaEscursione(EscursioneDTO escursione) throws CittaInesistenteException, EntitaEsistenteException {
		//verifico che non esista già un'escursione con lo stesso nome nella stessa città e nello stesso giorno
		TypedQuery<Escursioni> q = em.createNamedQuery("Escursioni.getEscursioneDaNomeEData", Escursioni.class);
		q.setParameter("nome", escursione.getNome());
		q.setParameter("citta", escursione.getCitta().getNome());
		q.setParameter("data", escursione.getData());
		if(!q.getResultList().isEmpty())
			throw new EntitaEsistenteException();
		
		Escursioni entity = new Escursioni ();
		
		entity.setCategoria(escursione.getCategoria());
		entity.setData(escursione.getData());
		entity.setDurata(escursione.getDurata());
		entity.setNome(escursione.getNome());
		entity.setOra(new Time(escursione.getOra().getTime()));
		entity.setPrezzo(escursione.getPrezzo());
		entity.setCitta(citta.getCitta(escursione.getCitta().getNome()));
		
		em.persist(entity);
		em.flush();
		
		return entity.getId();
	}

	@Override
	public void modificaDatiEscursione(EscursioneDTO escursione) throws EscursioneInesistenteException, CittaInesistenteException, EntitaEsistenteException {
		Escursioni entity = this.convertiInEntita(escursione);
		
		if (!entity.getNome().equals(escursione.getNome()) && !entity.getCitta().getNome().equals(escursione.getCitta().getNome()) && !entity.getData().equals(escursione.getData())) {
			//verifico che non esista già un'escursione con lo stesso nome nella stessa città e nello stesso giorno
			TypedQuery<Escursioni> q = em.createNamedQuery("Escursioni.getEscursioneDaNomeEData", Escursioni.class);
			q.setParameter("nome", escursione.getNome());
			q.setParameter("citta", escursione.getCitta().getNome());
			q.setParameter("data", escursione.getData());
			if(!q.getResultList().isEmpty())
				throw new EntitaEsistenteException();
			entity.setNome(escursione.getNome());
			entity.setCitta(citta.getCitta(escursione.getCitta().getNome()));
			entity.setData(escursione.getData());
		}		
		
		entity.setCategoria(escursione.getCategoria());		
		entity.setDurata(escursione.getDurata());		
		entity.setOra(new Time(escursione.getOra().getTime()));
		entity.setPrezzo(escursione.getPrezzo());
		
		
		em.merge(entity);
	}

	@Override
	public void eliminaEscursione(int idEscursione) throws EscursioneInesistenteException {
		Escursioni e = this.convertiInEntita(idEscursione);
		//controllo che l'escursione da eliminare non sia inserita in nessun pacchetto
		TypedQuery<Attivita> q = em.createNamedQuery("Attivita.getAttivitaConEscursione", Attivita.class);
		q.setParameter("escursione", e);
		TypedQuery<AttivitaPred> q1 = em.createNamedQuery("AttivitaPred.getAttivitaConEscursione", AttivitaPred.class);
		q1.setParameter("escursione", e);
		if (q.getResultList().isEmpty() && q1.getResultList().isEmpty())
			em.remove(e);
		else {
			e.setAttivo(0);
			em.merge(e);
		}
	}

	@Override
	public Escursioni convertiInEntita (EscursioneDTO escursione) throws EscursioneInesistenteException {
		Escursioni escursioneEntity = em.find(Escursioni.class, escursione.getId());	
		if (escursioneEntity != null)
			return escursioneEntity;
		else
			throw new EscursioneInesistenteException ();
	}
	
	@Override
	public Escursioni convertiInEntita (int idEscursione) throws EscursioneInesistenteException {
		Escursioni escursioneEntity = em.find(Escursioni.class, idEscursione);	
		if (escursioneEntity != null)
			return escursioneEntity;
		else
			throw new EscursioneInesistenteException ();
	}
	
	@Override
	public EscursioneDTO convertiInDTO (Escursioni escursione) {
		EscursioneDTO dto = new EscursioneDTO();
		
		dto.setId(escursione.getId());
		dto.setCategoria(escursione.getCategoria());
		dto.setData(escursione.getData());
		dto.setDurata(escursione.getDurata());
		dto.setNome(escursione.getNome());
		dto.setOra(escursione.getOra());
		dto.setPrezzo(escursione.getPrezzo());
		dto.setCitta(citta.convertiInDTO(escursione.getCitta()));
		dto.setAttivo(escursione.getAttivo());
		
		return dto;
	}

}
