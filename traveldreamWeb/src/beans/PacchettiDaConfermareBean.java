package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import utils.JsfUtil;
import dtos.PacchettoDTO;
import eccezioni.PacchettoInesistenteException;
import ejbs.GestorePacchetto;

@ManagedBean(name="pacchettiUtente")
@ViewScoped
public class PacchettiDaConfermareBean {

	@EJB
	private GestorePacchetto pacchettoBean;
	
	private PaginatorBean paginator;
	private List<PacchettoDTO> elenco;
	
	@PostConstruct
	public void setUp () {
		elenco = new ArrayList<PacchettoDTO>();
	}
	
	public List<PacchettoDTO> getElenco() {
		return elenco;
	}

	public void setElenco(List<PacchettoDTO> elenco) {
		this.elenco = elenco;
	}

	public PaginatorBean getPaginator() {
		return paginator;
	}

	/**
	 * Mostra l'elenco dei pacchetti degli utenti da confermare (e successivamente anche di quelli già confermati)
	 * @param force Per forzare la generazione di un nuovo elenco
	 */
	public void elencoPacchettiDaConfermare (boolean force) {
		if (paginator == null || force == true) {
			getElenco().addAll(pacchettoBean.elencoPacchettiUtenti());
			if (!getElenco().isEmpty())
				paginator = new PaginatorBean(getElenco());
			else
				JsfUtil.infoMessage("Nessun pacchetto da visualizzare");
		}
	}
	
	/**
	 * Ritorna il nome di un immagine casuale tra le destinazioni inserite nel pacchetto
	 * @param idPacchetto L'identificativo del pacchetto
	 * @return Il nome dell'immagine
	 */
	public String getImmagine (int idPacchetto) {
		try {
			PacchettoDTO pacchetto = pacchettoBean.getPacchettoDipendente(idPacchetto);
			int size = pacchetto.getDestinazioni().size();
			int index = 0 + (int)(Math.random() * size);
			int immSize = pacchetto.getDestinazioni().get(index).getCitta().getImmagini().size();
			if (immSize != 0) {
				int immIndex = 0 + (int)(Math.random() * immSize);
				return pacchetto.getDestinazioni().get(index).getCitta().getImmagini().get(immIndex);
			} else
				return "noImage.png";
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente");
		}
		return null;
	}
}
