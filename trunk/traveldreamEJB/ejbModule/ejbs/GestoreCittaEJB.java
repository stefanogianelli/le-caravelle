package ejbs;

import interfaces.GestoreCittaLocal;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.Part;

import utils.FileUtils;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;

import dtos.CittaDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.InsertException;
import eccezioni.UploadException;
import entities.Citta;
import entities.Collegamenti;
import entities.Destinazioni;
import entities.Escursioni;
import entities.Hotel;
import entities.ImmaginiCitta;
import entities.Pacchetti;

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
		//controllo che la citt� sia gi� presente nel database
		TypedQuery<Citta> q = em.createNamedQuery("Citta.getCittaDaNomeENazione", Citta.class);
		q.setParameter("nome", citta.getNome());
		q.setParameter("nazione", citta.getNazione());
		if (q.getResultList().isEmpty()) {
			Citta c = new Citta();
			
			c.setNome(citta.getNome());
			c.setRegione(citta.getRegione());
			c.setNazione(citta.getNazione());
			//recupero coordinate
			final Geocoder geocoder = new Geocoder();
			GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(citta.getNome() +"," + citta.getNazione()).setLanguage("it").getGeocoderRequest();
			GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
			if (!geocoderResponse.getResults().isEmpty()) {
				c.setLongitudine(geocoderResponse.getResults().get(0).getGeometry().getBounds().getNortheast().getLng().floatValue());
				c.setLatitudine(geocoderResponse.getResults().get(0).getGeometry().getBounds().getNortheast().getLat().floatValue());
			}
			for(String i : citta.getImmagini()) {
				ImmaginiCitta imm = new ImmaginiCitta();
				imm.setImmagine(i);
				c.addImmagini(imm);
			}
			c.setAttivo(1);
			em.persist(c);
		} else
			throw new InsertException();
	}
	
	@Override
	public void modificaCitta (CittaDTO citta) throws CittaInesistenteException, InsertException {
		Citta c = this.convertiInEntita(citta);
		
		//controllo che non esista un'altra citt� con lo stesso nome e nazione
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
		//controllo che la citt� sia inutilizzata
		TypedQuery<Collegamenti> q = em.createNamedQuery("Collegamenti.getCollegamentiConCitta", Collegamenti.class);
		q.setParameter("citta", c);
		TypedQuery<Destinazioni> q1 = em.createNamedQuery("Destinazioni.getDestinazioneInCitta", Destinazioni.class);
		q1.setParameter("citta", c);
		TypedQuery<Escursioni> q2 = em.createNamedQuery("Escursioni.getEscursioneInCitta", Escursioni.class);
		q2.setParameter("citta", c);
		TypedQuery<Hotel> q3 = em.createNamedQuery("Hotel.getHotelInCitta", Hotel.class);
		q3.setParameter("citta", c);
		TypedQuery<Pacchetti> q4 = em.createNamedQuery("Pacchetti.getPacchettiInCitta", Pacchetti.class);
		q4.setParameter("citta", c);
		Query q5 = em.createNativeQuery("SELECT * FROM citta_origine_pred WHERE idCitta = ?1");
		q5.setParameter("1", c.getId());
		if (q.getResultList().isEmpty() && q1.getResultList().isEmpty() && q2.getResultList().isEmpty() && q3.getResultList().isEmpty() && q4.getResultList().isEmpty() && q5.getResultList().isEmpty()) {
			FileUtils file = new FileUtils();
			for (ImmaginiCitta i : c.getImmagini()) {
				file.deleteFile(i.getImmagine(), "citta");
			}
			em.remove(c);
		} else {
			c.setAttivo(0);
			em.merge(c);
		}					
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
	 * Permette di ottenere un'entit� a partire dal suo identificativo
	 * @param idCitta L'identificativo della citt�
	 * @return L'entit� desiderata
	 * @throws CittaInesistenteException Se la citt� non viene trovata nel database
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
		dto.setAttivo(citta.getAttivo());
		
		return dto;
	}

}
