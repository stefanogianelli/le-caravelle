package ejbs;

import interfaces.GestoreCittaLocal;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import dtos.CittaDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.InsertException;
import entities.Citta;
import entities.ImmaginiCitta;

/**
 * Session Bean implementation class GestoreCittaEJB
 */
@Stateless
public class GestoreCittaEJB implements GestoreCitta, GestoreCittaLocal {

	@PersistenceContext
	private EntityManager em;
    
	@Override
	public List<CittaDTO> elencoCitta() {
		List<Citta> citta = em.createNamedQuery("Citta.elenco", Citta.class).getResultList();
		List<CittaDTO> dto = new ArrayList<CittaDTO>();
		for (Citta c : citta) {
			dto.add(this.convertiInDTO(c));
		}
		return dto;
	}
	
	@Override
	public Citta getCitta (String nome) throws CittaInesistenteException {
		TypedQuery<Citta> q = em.createNamedQuery("Citta.getCitta", Citta.class);
		q.setParameter("nome", nome);
		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			throw new CittaInesistenteException();
		}
	}

	@Override
	public void nuovaCitta (CittaDTO citta) throws InsertException {
		//controllo che la città sia già presente nel database
		TypedQuery<Citta> q = em.createNamedQuery("Citta.getCittaDaNomeENazione", Citta.class);
		q.setParameter("nome", citta.getNome());
		q.setParameter("nazione", citta.getNazione());
		if (q.getResultList().isEmpty()) {
			Citta c = new Citta();
			
			c.setNome(citta.getNome());
			c.setRegione(citta.getRegione());
			c.setNazione(citta.getNazione());
			c.setLongitudine(citta.getLongitudine());
			c.setLatitudine(citta.getLatitudine());
			for(String i : citta.getImmagini()) {
				ImmaginiCitta imm = new ImmaginiCitta();
				imm.setImmagine(i);
				c.addImmagini(imm);
			}
			
			em.persist(c);
		} else
			throw new InsertException();
	}
	
	@Override
	public CittaDTO cercaCitta (String nome)  throws CittaInesistenteException {
		return this.convertiInDTO(this.getCitta(nome));
	}
    
	@Override
	public Citta convertiInEntita (CittaDTO citta) throws CittaInesistenteException {
		Citta cittaEntity = em.find(Citta.class, citta.getId());
		if (cittaEntity != null)
			return cittaEntity;
		else
			throw new CittaInesistenteException ();
	}	
	
	@Override
	public CittaDTO convertiInDTO (Citta citta) {
		CittaDTO dto = new CittaDTO();
		
		dto.setId(citta.getId());
		dto.setNazione(citta.getNazione());
		dto.setNome(citta.getNome());
		dto.setRegione(citta.getRegione());
		dto.setLatitudine(citta.getLatitudine());
		dto.setLongitudine(citta.getLongitudine());
		List<String> immagini = new ArrayList<String>();
		for (ImmaginiCitta i : citta.getImmagini()) {
			immagini.add(i.getImmagine());
		}
		dto.setImmagini(immagini);
		
		return dto;
	}

}
