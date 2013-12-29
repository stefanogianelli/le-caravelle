package beans;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import utils.JsfUtil;
import dtos.HotelDTO;
import dtos.PacchettoPredefinitoDTO;
import eccezioni.HotelInesistenteException;
import ejbs.GestorePacchettoPredefinito;

@ManagedBean(name="pacchettoPredefinito")
@ViewScoped
public class PacchettoPredefinitoBean {
	
	@EJB
	private GestorePacchettoPredefinito pacchettoBean;

	private PacchettoPredefinitoDTO pacchetto;
	
	@PostConstruct
	public void setUp () {
		pacchetto = new PacchettoPredefinitoDTO();
	}

	public PacchettoPredefinitoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoPredefinitoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}
	
	public void aggiuntaData (Date data) {
		this.getPacchetto().getDatePartenza().add(data);
	}
	
	public void aggiuntaDurata (int durata) {
		this.getPacchetto().getDurate().add(durata);
	}
	
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
