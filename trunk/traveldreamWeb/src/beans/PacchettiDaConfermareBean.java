package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import utils.JsfUtil;
import dtos.CollegamentoDTO;
import dtos.DestinazioneDTO;
import dtos.PacchettoDTO;
import eccezioni.PacchettoInesistenteException;
import ejbs.GestorePacchetto;
import enums.TipoPacchetto;

@ManagedBean(name="pacchettiUtente")
@ViewScoped
public class PacchettiDaConfermareBean {

	@EJB
	private GestorePacchetto pacchettoBean;
	
	private PacchettoDTO pacchetto;
	private PaginatorBean paginator;
	private List<PacchettoDTO> elenco;
	private TipoPacchetto tipo;
	
	@PostConstruct
	public void setUp () {
		elenco = new ArrayList<PacchettoDTO>();
	}
	
	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
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
	
	public TipoPacchetto getTipo() {
		return tipo;
	}

	public void setTipo(TipoPacchetto tipo) {
		this.tipo = tipo;
	}

	/**
	 * Cerca il pacchetto corrispondente all'identificativo
	 * @param id L'identificativo del pacchetto
	 */
	public void getPacchetto (int id) {
		try {
			this.setPacchetto(pacchettoBean.getPacchettoDipendente(id));
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente");
		}
	}

	/**
	 * Mostra l'elenco dei pacchetti degli utenti da confermare (e successivamente anche di quelli già confermati)
	 * @param force Per forzare la generazione di un nuovo elenco
	 */
	public void elencoPacchettiDaConfermare (boolean force) {
		if (paginator == null || force == true) {
			getElenco().addAll(pacchettoBean.elencoPacchettiUtenti(null));
			if (!getElenco().isEmpty())
				paginator = new PaginatorBean(getElenco());
			else
				JsfUtil.infoMessage("Nessun pacchetto da visualizzare");
		}
	}
	
	/**
	 * Ricerca i pacchetti di una particolare tipologia
	 */
	public void cercaPacchetti () {
		List<PacchettoDTO> lista = pacchettoBean.elencoPacchettiUtenti(getTipo());
		if (lista.isEmpty())
			JsfUtil.infoMessage("Nessun risultato");
		else
			paginator = new PaginatorBean (lista);
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
	
	/**
	 * Restituisce il collegamento, se esistente, nella data desiderata
	 * @param data La data desiderata
	 * @return Il collegamento, se esiste, null altrimenti
	 */
	public CollegamentoDTO getCollegamento (Date data) {
		for (CollegamentoDTO c : this.getPacchetto().getCollegamenti()) {
			if (data.compareTo(c.getDataPartenza()) == 0)
				return c;
		}
		return null;
	}
	
	/**
	 * Verifica se la destinazione desiderata è l'ultima (in ordine di data di partenza)
	 * @param destinazione La destinazione da controllare
	 * @return true se la destinazione è l'ultima, false altrimenti
	 */
	public boolean isUltimaDestinazione (DestinazioneDTO destinazione) {
		if (this.getPacchetto().getDestinazioni().indexOf(destinazione) == (this.getPacchetto().getDestinazioni().size() - 1)) {
			return true;
		} else
			return false;
	}	
	
	/**
	 * Permette di confermare il pacchetto
	 */
	public void confermaPacchetto () {
		try {
			pacchettoBean.confermaPacchetto(getPacchetto().getId());
			JsfUtil.infoMessage("Pacchetto confermato");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente");
		}
	}
	
	/**
	 * Verificata se il pacchetto corrente è già stato confermato
	 * @return true se il pacchetto è stato confermato, false altrimenti
	 */
	public boolean isConfermato () {
		if (this.getPacchetto().getTipoPacchetto() == TipoPacchetto.ACQUISTATO)
			return true;
		else
			return false;
	}	
}
