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
		cq.where(predicati.toArray(new Predicate[]{}));
		TypedQuery<Escursioni> q = em.createQuery(cq);
		List<Escursioni> escursioni = q.getResultList();
		Collections.sort(escursioni);
		List<EscursioneDTO> dto = new ArrayList<EscursioneDTO>();
		for (Escursioni e : escursioni) {
			//data (workaround fino a quando non si trova una soluzione per comparare le date con le Criteria API)
			if (data != null) {
				if (e.getData().equals(data))
						dto.add(this.convertiInDTO(e));
			} else
				dto.add(this.convertiInDTO(e));			
		}
		return dto;
	}

	@Override
	public int creaEscursione(EscursioneDTO escursione) throws CittaInesistenteException, EntitaEsistenteException {
		//verifico che non esista già un'escursione con lo stesso nome nella stessa città
		TypedQuery<Escursioni> q = em.createNamedQuery("Escursioni.getEscursioneDaNome", Escursioni.class);
		q.setParameter("nome", escursione.getNome());
		q.setParameter("citta", escursione.getCitta().getNome());
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
	public void modificaDatiEscursione(EscursioneDTO escursione) throws EscursioneInesistenteException, CittaInesistenteException {
		Escursioni entity = this.convertiInEntita(escursione);
		
		entity.setCategoria(escursione.getCategoria());
		entity.setData(escursione.getData());
		entity.setDurata(escursione.getDurata());
		entity.setNome(escursione.getNome());
		entity.setOra(new Time(escursione.getOra().getTime()));
		entity.setPrezzo(escursione.getPrezzo());
		entity.setCitta(citta.getCitta(escursione.getCitta().getNome()));
		
		em.merge(entity);
	}

	@Override
	public void eliminaEscursione(int idEscursione) throws EscursioneInesistenteException {
		em.remove(this.convertiInEntita(idEscursione));
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
		
		return dto;
	}

}
