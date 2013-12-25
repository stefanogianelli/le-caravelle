package ejbs;

import interfaces.GestoreCittaLocal;
import interfaces.GestoreCollegamentoLocal;

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

import dtos.CollegamentoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
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
	
    /**
     * Default constructor. 
     */
    public GestoreCollegamentoEJB() {

    }

	@Override
	public List<CollegamentoDTO> elencoCollegamenti() {
		List<Collegamenti> collegamenti = em.createNamedQuery("Collegamenti.elenco", Collegamenti.class).getResultList();
		List<CollegamentoDTO> dto = new ArrayList<CollegamentoDTO>();
		for (Collegamenti c : collegamenti) {
			dto.add(this.convertiInDTO(c));
		}
		return dto;
	}
	
	@Override
	public List<CollegamentoDTO> elencoCollegamenti(TipoCollegamento tipo) {
		Query q = em.createNamedQuery("Collegamenti.elencoPerTipo", Collegamenti.class);
		q.setParameter("tipo", tipo);
		@SuppressWarnings("unchecked")
		List<Collegamenti> collegamenti = q.getResultList();
		List<CollegamentoDTO> dto = new ArrayList<CollegamentoDTO>();
		for (Collegamenti c : collegamenti) {
			dto.add(this.convertiInDTO(c));
		}
		return dto;
	}	
	
	@Override
	public List<CollegamentoDTO> elencoCollegamenti(Date data, String cittaPartenza, String cittaArrivo, TipoCollegamento tipo) {
		Query q = em.createNamedQuery("Collegamenti.elencoTraDestinazioni", Collegamenti.class);
		q.setParameter("data", data);
		q.setParameter("partenza", cittaPartenza);
		q.setParameter("arrivo", cittaArrivo);
		q.setParameter("tipo", tipo);
		@SuppressWarnings("unchecked")
		List<Collegamenti> collegamenti = q.getResultList();
		List<CollegamentoDTO> dto = new ArrayList<CollegamentoDTO>();
		for (Collegamenti c : collegamenti) {
			dto.add(this.convertiInDTO(c));
		}
		return dto;
	}

	@Override
	public void creaCollegamento(CollegamentoDTO collegamento) throws CittaInesistenteException, EntityExistsException {
		Collegamenti entity = new Collegamenti ();
		
		entity.setDataPartenza(collegamento.getDataPartenza());
		entity.setDestinazione(collegamento.getDestinazione());
		entity.setOraArrivo(new Time(collegamento.getOraArrivo().getTime()));
		entity.setOraPartenza(new Time(collegamento.getOraPartenza().getTime()));
		entity.setOrigine(collegamento.getOrigine());
		entity.setPrezzo(collegamento.getPrezzo());
		entity.setTipoCollegamento(collegamento.getTipoCollegamento());
		entity.setCittaArrivo(citta.getCitta(collegamento.getCittaArrivoText()));
		entity.setCittaPartenza(citta.getCitta(collegamento.getCittaPartenzaText()));
		
		em.persist(entity);
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
		entity.setCittaArrivo(citta.getCitta(collegamento.getCittaArrivoText()));
		entity.setCittaPartenza(citta.getCitta(collegamento.getCittaPartenzaText()));
		
		em.merge(entity);
	}

	@Override
	public void eliminaCollegamento(CollegamentoDTO collegamento) throws CollegamentoInesistenteException{
		em.remove(this.convertiInEntita(collegamento));		
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
		dto.setCittaArrivoText(collegamento.getCittaArrivo().getNome());
		dto.setCittaPartenzaText(collegamento.getCittaPartenza().getNome());
		
		return dto;
	}

}
