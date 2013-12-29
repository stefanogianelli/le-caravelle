package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import utils.JsfUtil;
import dtos.HotelDTO;
import dtos.PacchettoPredefinitoDTO;
import eccezioni.HotelInesistenteException;
import eccezioni.PacchettoInesistenteException;
import ejbs.GestorePacchettoPredefinito;

@ManagedBean(name="pacchettoPredefinito")
@ViewScoped
public class PacchettoPredefinitoBean {
	
	@EJB
	private GestorePacchettoPredefinito pacchettoBean;

	private PacchettoPredefinitoDTO pacchetto;
	private List<PacchettoPredefinitoDTO> elenco;
	
	@PostConstruct
	public void setUp () {
		pacchetto = new PacchettoPredefinitoDTO();
		elenco = new ArrayList<PacchettoPredefinitoDTO>();
	}

	public PacchettoPredefinitoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoPredefinitoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}
	
	public List<PacchettoPredefinitoDTO> getElenco() {
		return elenco;
	}

	public void setElenco(List<PacchettoPredefinitoDTO> elenco) {
		this.elenco = elenco;
	}
	
	/**
	 * Mostra tutti i pacchetti predefiniti presente nel database
	 */
	public void elencoPacchetti () {
		if (this.getElenco().isEmpty())
			this.setElenco(pacchettoBean.elencoPacchetti());
	}
	
	/**
	 * Cerca il pacchetto corrispondente all'identificativo
	 * @param id L'identificativo del pacchetto
	 */
	public void getPacchetto (int id) {
		try {
			this.setPacchetto(pacchettoBean.getPacchetto(id));
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}

	/**
	 * Aggiunge una data al pacchetto
	 * @param data La data di partenza
	 */
	public void aggiuntaData (Date data) {
		this.getPacchetto().getDatePartenza().add(data);
	}
	
	/**
	 * Aggiunge una durata al pacchetto
	 * @param durata La durata
	 */
	public void aggiuntaDurata (int durata) {
		this.getPacchetto().getDurate().add(durata);
	}
	
	/**
	 * Crea un nuovo pacchetto predefinito
	 * @param hotel L'hotel da aggiungere al pacchetto
	 */
	public void creaPacchetto (HotelDTO hotel) {
		try {
			this.getPacchetto().setHotel(hotel);
			pacchettoBean.creaPacchetto(getPacchetto());
			JsfUtil.infoMessage("Pacchetto creato!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		}
	}
}
