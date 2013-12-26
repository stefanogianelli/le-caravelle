package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import utils.JsfUtil;
import dtos.CittaDTO;
import dtos.HotelDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.EntitaEsistenteException;
import eccezioni.HotelInesistenteException;
import ejbs.GestoreCitta;
import ejbs.GestoreHotel;

@ManagedBean(name="hotel")
@ViewScoped
public class HotelBean {

	@EJB
	private GestoreHotel hotelBean;
	
	@EJB
	private GestoreCitta cittaBean;
	
	private HotelDTO hotel;
	private String nomeCitta;
	private List<HotelDTO> elenco;
	
	@PostConstruct
	public void setUp () {
		hotel = new HotelDTO();
		hotel.setCitta(new CittaDTO());
		elenco = new ArrayList<HotelDTO>();
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
	
	public List<HotelDTO> getElenco() {
		return elenco;
	}

	public void setElenco(List<HotelDTO> elenco) {
		this.elenco = elenco;
	}
	
	/**
	 * Mostra tutti gli hotel presenti nel database
	 */
	public void elencoHotel () {
		if (this.getElenco().isEmpty())
			this.getElenco().addAll(hotelBean.elencoHotel());
		if (this.getElenco().isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
	}

	/**
	 * Ricerca gli hotel nella città selezionata
	 */
	public void cercaHotel () {
		this.getElenco().clear();
		this.getElenco().addAll(hotelBean.elencoHotel(this.getNomeCitta()));
		if (this.getElenco().isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
	}

	/**
	 * Permette di creare un nuovo hotel
	 */
	public void creaHotel () {
		try {
			hotelBean.creaHotel(this.getHotel());
			JsfUtil.infoMessage("Hotel aggiunto correttamente!");
		} catch (EntitaEsistenteException e) {
			JsfUtil.errorMessage("L'hotel è già presente nel database!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		}
	}
	
	/**
	 * Abilita gli input text per la modifica di un hotel
	 * @param hotel L'hotel da modificare
	 */
	public void abilitaModifica (HotelDTO hotel) {
		hotel.setEditable(true);
	}
	
	/**
	 * Disabilita gli input text per la modifica di un hotel
	 * @param hotel L'hotel da ripristinare
	 */
	public void disabilitaModifica (HotelDTO hotel) {
		hotel.setEditable(false);
	}
	
	/**
	 * Permette la modifica di un hotel
	 * @param I dati dell'hotel
	 */
	public void modificaHotel (HotelDTO hotel) {
		try {
			hotelBean.modificaDatiHotel(hotel);
			hotel.setEditable(false);
			JsfUtil.infoMessage("Hotel modificato correttamente!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel sconosciuto!");
		}
	}
	
	/**
	 * Permette l'eliminazione di un hotel
	 * @param hotel L'hotel da eliminare
	 */
	public void eliminaHotel (HotelDTO hotel) {
		try {
			hotelBean.eliminaHotel(hotel);
			JsfUtil.infoMessage("Hotel eliminato!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel sconosciuta!");
		}
	}
}
