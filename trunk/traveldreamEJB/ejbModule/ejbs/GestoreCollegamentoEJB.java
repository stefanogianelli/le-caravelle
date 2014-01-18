package ejbs;

import interfaces.GestoreCittaLocal;
import interfaces.GestoreCollegamentoLocal;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import dtos.CollegamentoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import entities.Citta;
import entities.Collegamenti;
import enums.TipoCollegamento;

/**
 * Session Bean implementation class GestoreCollegamentoEJB
 */
@Stateless
public class GestoreCollegamentoEJB implements GestoreCollegamento, GestoreCollegamentoLocal {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreCittaLocal citta;
	
	@Override
	public CollegamentoDTO getCollegamento (int codiceCollegamento) throws CollegamentoInesistenteException {
		return this.convertiInDTO(this.convertiInEntita(codiceCollegamento));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOrigini (String nomeCitta) {
		Query q = em.createNamedQuery("Collegamenti.getOrigini");
		q.setParameter("nomeCitta", nomeCitta);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDestinazioni (String nomeCitta) {
		Query q = em.createNamedQuery("Collegamenti.getDestinazioni");
		q.setParameter("nomeCitta", nomeCitta);
		return q.getResultList();
	}
	
	@Override
	public List<CollegamentoDTO> elencoCollegamenti() {
		List<Collegamenti> collegamenti = em.createNamedQuery("Collegamenti.elenco", Collegamenti.class).getResultList();
		Collections.sort(collegamenti);
		List<CollegamentoDTO> dto = new ArrayList<CollegamentoDTO>();
		for (Collegamenti c : collegamenti) {
			dto.add(this.convertiInDTO(c));
		}
		return dto;
	}
	
	@Override
	public List<CollegamentoDTO> elencoCollegamenti (TipoCollegamento tipo) {
		TypedQuery<Collegamenti> q = em.createNamedQuery("Collegamenti.elencoPerTipo", Collegamenti.class);
		q.setParameter("tipo", tipo);
		List<Collegamenti> collegamenti = q.getResultList();
		Collections.sort(collegamenti);
		List<CollegamentoDTO> dto = new ArrayList<CollegamentoDTO>();
		for (Collegamenti c : collegamenti) {
			dto.add(this.convertiInDTO(c));
		}
		return dto;
	}	
	
	@Override
	public List<CollegamentoDTO> elencoCollegamenti(Date data, String cittaPartenza, String cittaArrivo, TipoCollegamento tipo, String origine, String destinazione) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Collegamenti> cq = cb.createQuery(Collegamenti.class);
		Root<Collegamenti> collegamenti = cq.from(Collegamenti.class);		
		List<Predicate> predicati = new ArrayList<Predicate>();
		//data
		if (data != null) {
			predicati.add(cb.lessThanOrEqualTo(collegamenti.<Date>get("dataPartenza"), data));
			predicati.add(cb.greaterThanOrEqualTo(collegamenti.<Date>get("dataPartenza"), data));
		}
		//citta partenza
		if (cittaPartenza != null && !cittaPartenza.isEmpty() && !cittaPartenza.equals("Qualsiasi")) {
			Join<Collegamenti, Citta> partenza = collegamenti.join("cittaPartenza");
			predicati.add(cb.equal(partenza.get("nome"), cittaPartenza));
		}
		//citta arrivo
		if (cittaArrivo != null && !cittaArrivo.isEmpty() && !cittaArrivo.equals("Qualsiasi")) {
			Join<Collegamenti, Citta> arrivo = collegamenti.join("cittaArrivo");
			predicati.add(cb.equal(arrivo.get("nome"), cittaArrivo));
		}
		//tipo
		if (tipo != null)
			predicati.add(cb.equal(collegamenti.get("tipoCollegamento"), tipo));
		//origine
		if (origine != null && !origine.equals("Qualsiasi"))
			predicati.add(cb.equal(collegamenti.get("origine"), origine));
		//destinazione
		if (destinazione != null && !destinazione.equals("Qualsiasi"))
			predicati.add(cb.equal(collegamenti.get("destinazione"), destinazione));		
		cq.where(predicati.toArray(new Predicate[]{}));
		TypedQuery<Collegamenti> q = em.createQuery(cq);
		List<Collegamenti> elenco = q.getResultList();
		Collections.sort(elenco);
		List<CollegamentoDTO> dto = new ArrayList<CollegamentoDTO>();
		for (Collegamenti c : elenco) {
			dto.add(this.convertiInDTO(c));
		}
		return dto;
	}
	
	@Override
	public int creaCollegamento(CollegamentoDTO collegamento) throws CittaInesistenteException, EntityExistsException {
		Collegamenti entity = new Collegamenti ();
		
		entity.setDataPartenza(collegamento.getDataPartenza());
		entity.setDestinazione(collegamento.getDestinazione());
		entity.setOraArrivo(new Time(collegamento.getOraArrivo().getTime()));
		entity.setOraPartenza(new Time(collegamento.getOraPartenza().getTime()));
		entity.setOrigine(collegamento.getOrigine());
		entity.setPrezzo(collegamento.getPrezzo());
		entity.setTipoCollegamento(collegamento.getTipoCollegamento());
		entity.setCittaArrivo(citta.getCitta(collegamento.getCittaArrivo().getNome()));
		entity.setCittaPartenza(citta.getCitta(collegamento.getCittaPartenza().getNome()));
		
		em.persist(entity);
		em.flush();
		
		return entity.getCodice();
	}

	@Override
	public void modificaDatiCollegamento(CollegamentoDTO collegamento) throws CollegamentoInesistenteException, CittaInesistenteException {
		Collegamenti entity = this.convertiInEntita(collegamento);
		
		entity.setDataPartenza(collegamento.getDataPartenza());
		entity.setDestinazione(collegamento.getDestinazione());
		entity.setOraArrivo(new Time(collegamento.getOraArrivo().getTime()));
		entity.setOraPartenza(new Time(collegamento.getOraPartenza().getTime()));
		entity.setOrigine(collegamento.getOrigine());
		entity.setPrezzo(collegamento.getPrezzo());
		entity.setTipoCollegamento(collegamento.getTipoCollegamento());
		entity.setCittaArrivo(citta.getCitta(collegamento.getCittaArrivo().getNome()));
		entity.setCittaPartenza(citta.getCitta(collegamento.getCittaPartenza().getNome()));
		
		em.merge(entity);
	}

	@Override
	public void eliminaCollegamento (int codiceCollegamento) throws CollegamentoInesistenteException {
		em.remove(this.convertiInEntita(codiceCollegamento));
	}
	
	@Override
	public Collegamenti convertiInEntita (CollegamentoDTO collegamento) throws CollegamentoInesistenteException {
		Collegamenti collegamentoEntity = em.find(Collegamenti.class, collegamento.getCodice());
		if (collegamentoEntity != null)
			return collegamentoEntity;
		else
			throw new CollegamentoInesistenteException ();
	}
	
	@Override
	public Collegamenti convertiInEntita (int codiceCollegamento) throws CollegamentoInesistenteException {
		Collegamenti collegamentoEntity = em.find(Collegamenti.class, codiceCollegamento);
		if (collegamentoEntity != null)
			return collegamentoEntity;
		else
			throw new CollegamentoInesistenteException ();
	}
	
	@Override
	public CollegamentoDTO convertiInDTO (Collegamenti collegamento) {
		CollegamentoDTO dto = new CollegamentoDTO ();
		
		dto.setCodice(collegamento.getCodice());
		dto.setDataPartenza(collegamento.getDataPartenza());
		dto.setDestinazione(collegamento.getDestinazione());
		dto.setOraArrivo(collegamento.getOraArrivo());
		dto.setOraPartenza(collegamento.getOraPartenza());
		dto.setOrigine(collegamento.getOrigine());
		dto.setPrezzo(collegamento.getPrezzo());
		dto.setTipoCollegamento(collegamento.getTipoCollegamento());
		dto.setCittaArrivo(citta.convertiInDTO(collegamento.getCittaArrivo()));
		dto.setCittaPartenza(citta.convertiInDTO(collegamento.getCittaPartenza()));
		
		return dto;
	}

}
