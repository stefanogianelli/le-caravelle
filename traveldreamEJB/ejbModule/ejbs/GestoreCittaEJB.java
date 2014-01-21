package ejbs;

import interfaces.GestoreCittaLocal;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.Part;

import utils.FileUtils;
import dtos.CittaDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.InsertException;
import eccezioni.UploadException;
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
	public CittaDTO getCitta (int idCitta) throws CittaInesistenteException {		
		return this.convertiInDTO(this.convertiInEntita(idCitta));
	}
	
	@Override
	public CittaDTO cercaCitta (String nome)  throws CittaInesistenteException {
		return this.convertiInDTO(this.getCitta(nome));
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
	public void modificaCitta (CittaDTO citta) throws CittaInesistenteException, InsertException {
		Citta c = this.convertiInEntita(citta);
		
		//controllo che non esista un'altra città con lo stesso nome e nazione
		if (!c.getNome().equals(citta.getNome()) || !c.getNazione().equals(citta.getNazione())) {
			TypedQuery<Citta> q = em.createNamedQuery("Citta.getCittaDaNomeENazione", Citta.class);
			q.setParameter("nome", citta.getNome());
			q.setParameter("nazione", citta.getNazione());
			if (q.getResultList().isEmpty()) {
				c.setNome(citta.getNome());
				c.setNazione(citta.getNazione());
			} else
				throw new InsertException();
		}
		
		c.setRegione(citta.getRegione());		
		c.setLongitudine(citta.getLongitudine());
		c.setLatitudine(citta.getLatitudine());
		
		em.merge(c);
	}
	
	@Override
	public void aggiuntaImmagine (int idCitta, Part immagine) throws UploadException, CittaInesistenteException, InsertException {
		Citta c = this.convertiInEntita(idCitta);
		
		if (immagine.getSize() != 0) {
			FileUtils file = new FileUtils();
			ImmaginiCitta imm = new ImmaginiCitta();
			imm.setImmagine(file.upload(immagine, "citta"));
			c.addImmagini(imm);
			em.merge(c);
		} else
			throw new InsertException();
	}
	
	@Override
	public void rimuoviImmagine (int idCitta, String nomeImmagine) throws CittaInesistenteException {
		Citta c = this.convertiInEntita(idCitta);
		ImmaginiCitta imm = null;
		for (ImmaginiCitta i : c.getImmagini()) {
			if (i.getImmagine().equals(nomeImmagine)) {
				imm = i;
				break;
			}
		}
		FileUtils file = new FileUtils();
		file.deleteFile(nomeImmagine, "citta");
		c.removeImmagini(imm);
		em.merge(c);
	}
	
	@Override
	public void eliminaCitta (int idCitta) throws CittaInesistenteException {
		Citta c = this.convertiInEntita(idCitta);
		em.remove(c);
	}
    
	@Override
	public Citta convertiInEntita (CittaDTO citta) throws CittaInesistenteException {
		Citta cittaEntity = em.find(Citta.class, citta.getId());
		if (cittaEntity != null)
			return cittaEntity;
		else
			throw new CittaInesistenteException ();
	}	
	
	/**
	 * Permette di ottenere un'entità a partire dal suo identificativo
	 * @param idCitta L'identificativo della città
	 * @return L'entità desiderata
	 * @throws CittaInesistenteException Se la città non viene trovata nel database
	 */
	private Citta convertiInEntita (int idCitta) throws CittaInesistenteException {
		Citta cittaEntity = em.find(Citta.class, idCitta);
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
