package ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.HotelDTO;
import eccezioni.CittaInesistenteException;
import entities.Hotel;

/**
 * Session Bean implementation class GestoreHotelEJB
 */
@Stateless
public class GestoreHotelEJB implements GestoreHotel {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GestoreCittaEJB citta;
	
    /**
     * Default constructor. 
     */
    public GestoreHotelEJB() {

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
		Query q = em.createNamedQuery("Hotel.elencoPerCitta", Hotel.class);
		q.setParameter("citta", nomeCitta);
		@SuppressWarnings("unchecked")
		List<Hotel> hotel = q.getResultList();
		List<HotelDTO> dto = new ArrayList<HotelDTO>();
		for (Hotel h : hotel) {
			dto.add(this.convertiInDTO(h));
		}
		return dto;
	}	

	@Override
	public void creaHotel(HotelDTO hotel) {
		try {
			Hotel entity = new Hotel ();
			
			entity.setEmail(hotel.getEmail());
			entity.setIndirizzo(hotel.getIndirizzo());
			entity.setNome(hotel.getNome());
			entity.setPrezzo(hotel.getPrezzo());
			entity.setStelle(hotel.getStelle());
			entity.setTelefono(hotel.getTelefono());
			entity.setWebsite(hotel.getWebsite());
			entity.setCitta(citta.convertiInEntita(hotel.getCitta()));			
			
			em.persist(entity);
		} catch (CittaInesistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void modificaDatiHotel(HotelDTO hotel) {
		try {
			Hotel entity = this.convertiInEntita(hotel);
			
			entity.setEmail(hotel.getEmail());
			entity.setIndirizzo(hotel.getIndirizzo());
			entity.setNome(hotel.getNome());
			entity.setPrezzo(hotel.getPrezzo());
			entity.setStelle(hotel.getStelle());
			entity.setTelefono(hotel.getTelefono());
			entity.setWebsite(hotel.getWebsite());
			entity.setCitta(citta.convertiInEntita(hotel.getCitta()));		
			
			em.merge(entity);
		} catch (CittaInesistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void eliminaHotel(HotelDTO hotel) {
		em.remove(this.convertiInEntita(hotel));
	}
	
	/**
	 * Permette la conversione da un DTO alla rispettiva entità
	 * @param hotel Il DTO dell'hotel
	 * @return L'entità desiderata
	 */
	protected Hotel convertiInEntita (HotelDTO hotel) {
		return em.find(Hotel.class, hotel.getId());		
	}	
	
	/**
	 * Permette la conversione da un'entità hotel al rispettivo DTO
	 * @param hotel L'oggetto da converitre
	 * @return Il DTO risultante
	 */
	protected HotelDTO convertiInDTO (Hotel hotel) {
		HotelDTO dto = new HotelDTO ();
		
		dto.setEmail(hotel.getEmail());
		dto.setIndirizzo(hotel.getIndirizzo());
		dto.setNome(hotel.getNome());
		dto.setPrezzo(hotel.getPrezzo());
		dto.setStelle(hotel.getStelle());
		dto.setTelefono(hotel.getTelefono());
		dto.setWebsite(hotel.getWebsite());
		dto.setCitta(citta.convertiInDTO(hotel.getCitta()));
		
		return dto;
	}
	
}
