package ejbs;

import interfaces.GestoreCittaLocal;
import interfaces.GestoreHotelLocal;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import utils.FileUtils;
import dtos.HotelDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.DeleteException;
import eccezioni.EntitaEsistenteException;
import eccezioni.HotelInesistenteException;
import entities.Hotel;

/**
 * Session Bean implementation class GestoreHotelEJB
 */
@Stateless
public class GestoreHotelEJB implements GestoreHotel, GestoreHotelLocal {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreCittaLocal citta;
	
	@Override
	public HotelDTO getHotel (int idHotel) throws HotelInesistenteException {
		return this.convertiInDTO(this.convertiInEntita(idHotel));
	}

	@Override
	public List<HotelDTO> elencoHotel() {
		List<Hotel> hotel = em.createNamedQuery("Hotel.elenco", Hotel.class).getResultList();
		List<HotelDTO> dto = new ArrayList<HotelDTO>();
		for (Hotel h : hotel) {
			dto.add(this.convertiInDTO(h));
		}
		return dto;
	}
	
	@Override
	public List<HotelDTO> elencoHotel(String nomeCitta) {
		TypedQuery<Hotel> q = em.createNamedQuery("Hotel.elencoPerCitta", Hotel.class);
		q.setParameter("citta", nomeCitta);
		List<Hotel> hotel = q.getResultList();
		List<HotelDTO> dto = new ArrayList<HotelDTO>();
		for (Hotel h : hotel) {
			dto.add(this.convertiInDTO(h));
		}
		return dto;
	}	

	@Override
	public int creaHotel(HotelDTO hotel) throws CittaInesistenteException, EntitaEsistenteException {
		//controllo che non esista un hotel con lo stesso nome nella stessa città
		TypedQuery<Hotel> q = em.createNamedQuery("Hotel.getHotel", Hotel.class);
		q.setParameter("nome", hotel.getNome());
		q.setParameter("citta", hotel.getCitta().getNome());
		if (!q.getResultList().isEmpty())
			throw new EntitaEsistenteException ();
		
		Hotel entity = new Hotel ();
		
		entity.setEmail(hotel.getEmail());
		entity.setIndirizzo(hotel.getIndirizzo());
		entity.setNome(hotel.getNome());
		entity.setPrezzo(hotel.getPrezzo());
		entity.setStelle(hotel.getStelle());
		entity.setTelefono(hotel.getTelefono());
		entity.setWebsite(hotel.getWebsite());
		entity.setCitta(citta.getCitta(hotel.getCitta().getNome()));
		if(!hotel.getImmagine().isEmpty())
			entity.setImmagine(hotel.getImmagine());
		else
			entity.setImmagine("NULL");
		
		em.persist(entity);
		em.flush();
		
		return entity.getId();
	}

	@Override
	public void modificaDatiHotel(HotelDTO hotel) throws CittaInesistenteException, HotelInesistenteException, EntitaEsistenteException {	
		Hotel entity = this.convertiInEntita(hotel);
		
		if (!entity.getNome().equals(hotel.getNome()) && !entity.getCitta().equals(hotel.getCitta())) {
			//controllo che non esista un hotel con lo stesso nome nella stessa città
			TypedQuery<Hotel> q = em.createNamedQuery("Hotel.getHotel", Hotel.class);
			q.setParameter("nome", hotel.getNome());
			q.setParameter("citta", hotel.getCitta().getNome());
			if (!q.getResultList().isEmpty())
				throw new EntitaEsistenteException ();	
			entity.setNome(hotel.getNome());
			entity.setCitta(citta.getCitta(hotel.getCitta().getNome()));
		}
		
		entity.setEmail(hotel.getEmail());
		entity.setIndirizzo(hotel.getIndirizzo());		
		entity.setPrezzo(hotel.getPrezzo());
		entity.setStelle(hotel.getStelle());
		entity.setTelefono(hotel.getTelefono());
		entity.setWebsite(hotel.getWebsite());
		
		if(!hotel.getImmagine().isEmpty())
			entity.setImmagine(hotel.getImmagine());
		else
			entity.setImmagine("NULL");
		
		em.merge(entity);
	}

	@Override
	public void eliminaHotel(int idHotel) throws HotelInesistenteException, DeleteException {
		FileUtils file = new FileUtils();
		Hotel hotel = this.convertiInEntita(idHotel);
		if (!hotel.getImmagine().equals("NULL"))
			file.deleteFile(hotel.getImmagine(), "hotel");
		em.remove(hotel);
	}
	
	@Override
	public Hotel convertiInEntita (HotelDTO hotel) throws HotelInesistenteException {
		Hotel hotelEntity = em.find(Hotel.class, hotel.getId());
		if (hotelEntity != null)
			return hotelEntity;
		else
			throw new HotelInesistenteException ();
	}
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param idHotel L'identificativo dell'hotel
	 * @return L'entità desiderata
	 * @throws HotelInesistenteException Quando l'hotel non viene trovato nel database
	 */
	private Hotel convertiInEntita (int idHotel) throws HotelInesistenteException {
		Hotel hotelEntity = em.find(Hotel.class, idHotel);
		if (hotelEntity != null)
			return hotelEntity;
		else
			throw new HotelInesistenteException ();
	}	
	
	@Override
	public HotelDTO convertiInDTO (Hotel hotel) {
		HotelDTO dto = new HotelDTO ();
		
		dto.setId(hotel.getId());
		dto.setEmail(hotel.getEmail());
		dto.setIndirizzo(hotel.getIndirizzo());
		dto.setNome(hotel.getNome());
		dto.setPrezzo(hotel.getPrezzo());
		dto.setStelle(hotel.getStelle());
		dto.setTelefono(hotel.getTelefono());
		dto.setWebsite(hotel.getWebsite());
		dto.setCitta(citta.convertiInDTO(hotel.getCitta()));
		dto.setImmagine(hotel.getImmagine());
		
		return dto;
	}
	
}
