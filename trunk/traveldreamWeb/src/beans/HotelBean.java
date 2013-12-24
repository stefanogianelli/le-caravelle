package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityExistsException;

import utils.JsfUtil;
import dtos.HotelDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.HotelInesistenteException;
import ejbs.GestoreCitta;
import ejbs.GestoreHotel;

@ManagedBean(name="hotel")
@RequestScoped
public class HotelBean {

	@EJB
	private GestoreHotel hotelBean;
	
	@EJB
	private GestoreCitta cittaBean;
	
	private HotelDTO hotel;
	private String nomeCitta;
	
	@PostConstruct
	public void setUp () {
		hotel = new HotelDTO();
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}
	
	public String getNomeCitta() {
		return nomeCitta;
	}

	public void setNomeCitta(String nomeCitta) {
		this.nomeCitta = nomeCitta;
	}

	/**
	 * Ricerca gli hotel nella città selezionata
	 * @param citta Il nome della città
	 * @return L'elenco degli hotel trovati
	 */
	public List<HotelDTO> cercaHotel (String citta) {
		return hotelBean.elencoHotel(citta);
	}

	/**
	 * Permette di creare un nuovo hotel
	 */
	public void creaHotel () {
		//this.getHotel().setCitta(cittaBean.cercaCitta(nomeCitta));
		try {
			hotelBean.creaHotel(this.getHotel());
		} catch (EntityExistsException e) {
			JsfUtil.errorMessage("L'hotel è già presente nel database!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		}
	}
	
	/**
	 * Permette la modifica di un hotel
	 * @param I dati dell'hotel
	 */
	public void modificaHotel (HotelDTO hotel) {
		try {
			hotelBean.modificaDatiHotel(hotel);
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel sconosciuta!");
		}
	}
	
	/**
	 * Permette l'eliminazione di un hotel
	 * @param hotel L'hotel da eliminare
	 */
	public void eliminaHotel (HotelDTO hotel) {
		try {
			hotelBean.eliminaHotel(hotel);
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel sconosciuta!");
		}
	}
}
