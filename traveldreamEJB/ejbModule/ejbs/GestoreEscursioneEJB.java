package ejbs;

import interfaces.GestoreCittaLocal;
import interfaces.GestoreEscursioneLocal;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.EscursioneDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.EscursioneInesistenteException;
import entities.Escursioni;

/**
 * Session Bean implementation class GestoreEscursioneEJB
 */
@Stateless
public class GestoreEscursioneEJB implements GestoreEscursione, GestoreEscursioneLocal {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreCittaLocal citta;
	
    /**
     * Default constructor. 
     */
    public GestoreEscursioneEJB() {
    
    }
    
	@Override
	public List<EscursioneDTO> elencoEscursioni() {
		List<Escursioni> escursioni = em.createNamedQuery("Escursioni.elenco", Escursioni.class).getResultList();
		List<EscursioneDTO> dto = new ArrayList<EscursioneDTO>();
		for (Escursioni e : escursioni) {
			dto.add(this.convertiInDTO(e));
		}
		return dto;
	}
	
	@Override
	public List<EscursioneDTO> elencoEscursioni(String regione) {
		Query q = em.createNamedQuery("Escursioni.elencoPerRegione", Escursioni.class);
		q.setParameter("regione", regione);
		@SuppressWarnings("unchecked")
		List<Escursioni> escursioni = q.getResultList();
		List<EscursioneDTO> dto = new ArrayList<EscursioneDTO>();
		for (Escursioni e : escursioni) {
			dto.add(this.convertiInDTO(e));
		}
		return dto;
	}	
	
	@Override
	public List<EscursioneDTO> elencoEscursioni(Date dataArrivo, Date dataPartenza, String regione) {
		Query q = em.createNamedQuery("Escursioni.elencoPerPeriodo", Escursioni.class);
		q.setParameter("dataArrivo", dataArrivo);
		q.setParameter("dataPartenza", dataPartenza);
		q.setParameter("regione", regione);
		@SuppressWarnings("unchecked")
		List<Escursioni> escursioni = q.getResultList();
		List<EscursioneDTO> dto = new ArrayList<EscursioneDTO>();
		for (Escursioni e : escursioni) {
			dto.add(this.convertiInDTO(e));
		}
		return dto;
	}

	@Override
	public void creaEscursione(EscursioneDTO escursione) throws CittaInesistenteException, EntityExistsException {
		Escursioni entity = new Escursioni ();
		
		entity.setCategoria(escursione.getCategoria());
		entity.setData(escursione.getData());
		entity.setDurata(escursione.getDurata());
		entity.setNome(escursione.getNome());
		entity.setOra(new Time(escursione.getOra().getTime()));
		entity.setPrezzo(escursione.getPrezzo());
		entity.setCitta(citta.getCitta(escursione.getCitta().getNome()));
		
		em.persist(entity);
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
	public void eliminaEscursione(EscursioneDTO escursione) throws EscursioneInesistenteException {
		em.remove(this.convertiInEntita(escursione));
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
