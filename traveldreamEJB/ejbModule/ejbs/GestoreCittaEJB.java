package ejbs;

import interfaces.GestoreCittaLocal;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.CittaDTO;
import dtos.ImmagineCittaDTO;
import eccezioni.CittaInesistenteException;
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
		Query q = em.createNamedQuery("Citta.getCitta", Citta.class);
		q.setParameter("nome", nome);
		try {
			return (Citta) q.getSingleResult();
		} catch (NoResultException e) {
			throw new CittaInesistenteException();
		}
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
		List<ImmagineCittaDTO> immagini = new ArrayList<ImmagineCittaDTO>();
		for (ImmaginiCitta i : citta.getImmagini()) {
			ImmagineCittaDTO immagine = new ImmagineCittaDTO();
			immagine.setImmagine(i.getImmagine());
			immagini.add(immagine);
		}
		dto.setImmagini(immagini);
		
		return dto;
	}

}
