package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import utils.JsfUtil;
import dtos.CittaDTO;
import dtos.CollegamentoDTO;
import dtos.HotelDTO;
import dtos.PacchettoPredefinitoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.InsertException;
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
	 * @return L'elenco dei pacchetti trovati
	 */
	public List<PacchettoPredefinitoDTO> elencoPacchetti () {		
		return pacchettoBean.elencoPacchetti();
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
	 * Ritorna il nome di un immagine casuale tra le destinazioni inserite nel pacchetto
	 * @param idPacchetto L'identificativo del pacchetto
	 * @return Il nome dell'immagine
	 */
	public String getImmagine (int idPacchetto) {
		try {
			PacchettoPredefinitoDTO pacchetto = pacchettoBean.getPacchetto(idPacchetto);
			int size = pacchetto.getHotel().getCitta().getImmagini().size();
			if (size != 0) {
				int index = 0 + (int)(Math.random() * size);
				return pacchetto.getHotel().getCitta().getImmagini().get(index); 
			} else
				return "noImage.png";
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
		return null;
	}	

	/**
	 * Aggiunge una data al pacchetto
	 * @param data La data di partenza
	 */
	public void aggiuntaData (Date data) {
		//verifico che la data non sia già stata inserita
		if (this.getPacchetto().getDatePartenza().indexOf(data) == -1) {
			this.getPacchetto().getDatePartenza().add(data);
			JsfUtil.infoMessage("Data aggiunta!");
		} else
			JsfUtil.errorMessage("Data già inserita");
	}
	
	/**
	 * Aggiunge una durata al pacchetto
	 * @param durata La durata
	 */
	public void aggiuntaDurata (int durata) {
		//verifico che la durata non sia già stata inserita
		if (this.getPacchetto().getDurate().indexOf(durata) == -1) {
			this.getPacchetto().getDurate().add(durata);
			JsfUtil.infoMessage("Durata aggiunta!");
		} else
			JsfUtil.errorMessage("Durata già inserita");
	}
	
	/**
	 * Aggiunge una città al pacchetto
	 * @param nome Il nome della città
	 */
	public void aggiuntaCitta (String nome) {
		CittaDTO citta = new CittaDTO();
		citta.setNome(nome);
		//verifico che la città non sia già stata inserita
		if (this.getPacchetto().getCittaPartenza().indexOf(citta) == -1) {
			this.getPacchetto().getCittaPartenza().add(citta);
			JsfUtil.infoMessage("Città aggiunta!");
		} else
			JsfUtil.errorMessage("Città già inserita");
	}
	
	/**
	 * Crea un nuovo pacchetto predefinito
	 * @param hotel L'hotel da aggiungere al pacchetto
	 * @return L'indirizzo della pagina con i dettagli del pacchetto creato
	 */
	public String creaPacchetto (HotelDTO hotel) {
		boolean check = true;
		
		//verifico che sia stata inserita almeno una città di partenza
		if (!this.getPacchetto().getCittaPartenza().isEmpty()) {	
			//verifico che sia stata inserita almeno una data di partenza
			if (!this.getPacchetto().getDatePartenza().isEmpty()) {
				//verifico che sia stata inserita almeno una durata
				if (!this.getPacchetto().getDurate().isEmpty()) {				
					//verifico che l'hotel inserito non sia nella stessa città di una delle città di partenza
					for (CittaDTO c : this.getPacchetto().getCittaPartenza()) {
						if (c.equals(hotel.getCitta())) {
							check = false;
							break;
						}
					}
					
					if (check) {								
						try {
							this.getPacchetto().setHotel(hotel);
							return "dettagliPacchetto?idPacchetto=" + pacchettoBean.creaPacchetto(getPacchetto()) + "&faces-redirect=true";
						} catch (HotelInesistenteException e) {
							JsfUtil.errorMessage("Hotel inesistente!");
						} catch (CittaInesistenteException w) {
							JsfUtil.errorMessage("Città inesistente!");
						} catch (InsertException e) {
							JsfUtil.errorMessage("Nome già usato!");
						}		
					} else
						JsfUtil.errorMessage("L'hotel si trova nella stessa città di una delle città di partenza!");
				} else
					JsfUtil.errorMessage("Inserire almeno una durata!");
			} else
				JsfUtil.errorMessage("Inserire almeno una data di partenza!");
		} else
			JsfUtil.errorMessage("Inserire almeno una città di partenza!");
		
		return null;
	}
	
	/**
	 * Permette di eliminare un pacchetto
	 * @param idPacchetto L'identificativo del pacchetto
	 * @return L'indirizzo della pagina con l'elenco dei pacchetti
	 */
	public String eliminaPacchetto (int idPacchetto) {
		try {
			pacchettoBean.eliminaPacchetto(idPacchetto);
			return "elencoPacchetti?faces-redirect=true";
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
		return null;
	}
	
	/**
	 * Permette la modifica del nome del pacchetto
	 */
	public void modificaNomePacchetto () {
		try {
			pacchettoBean.modificaNomePacchetto(getPacchetto());
			JsfUtil.infoMessage("Nome modificato!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (InsertException e) {
			JsfUtil.errorMessage("Nome già usato!");
		}
	}
	
	/**
	 * Permette la modifica del prezzo del pacchetto
	 */
	public void modificaPrezzoPacchetto () {
		try {
			pacchettoBean.modificaPrezzo(getPacchetto());
			JsfUtil.infoMessage("Prezzo modificato!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette di modificare l'hotel inserito nel pacchetto
	 * @param idPacchetto L'identificativo del pacchetto
	 * @param hotel L'hotel da inserire
	 * @return L'indirizzo della pagina dettagli del pacchetto
	 */
	public String modificaHotel (int idPacchetto, HotelDTO hotel) {
		try {
			pacchettoBean.modificaHotel(idPacchetto, hotel);
			return "dettagliPacchetto?idPacchetto=" + idPacchetto + "&faces-redirect=true";
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		} catch (InsertException e) {
			JsfUtil.errorMessage("L'hotel si trova nella stessa città di una delle città di partenza!");
		}
		return null;
	}
	
	/**
	 * Permette di aggiungere una città di partenza nel pacchetto
	 * @param nome Il nome della città
	 */
	public void salvaCitta (String nome) {
		try {
			pacchettoBean.aggiuntaCittaPartenza(getPacchetto(), nome);
			JsfUtil.infoMessage("Città aggiunta");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città inesistente!");
		} catch (InsertException e) {
			JsfUtil.errorMessage("Città già inserita!");
		}
	}
	
	/**
	 * Permette di rimuovere una città di partenza dal pacchetto
	 * @param citta La città da rimuovere
	 */
	public void rimuoviCitta (CittaDTO citta) {
		//verifico che rimanga almeno una città di partenza nel pacchetto
		if (this.getPacchetto().getCittaPartenza().size() > 1) {
			try {
				pacchettoBean.rimuoviCittaPartenza(getPacchetto(), citta);
				JsfUtil.infoMessage("Città rimossa!");
			} catch (PacchettoInesistenteException e) {
				JsfUtil.errorMessage("Pacchetto inesistente!");
			} catch (CittaInesistenteException e) {
				JsfUtil.errorMessage("Città inesistente!");
			}
		} else
			JsfUtil.errorMessage("Ultima città rimasta, impossibile eliminare!");
	}
	
	/**
	 * Permette di aggiungere una data di partenza nel pacchetto
	 * @param data
	 */
	public void salvaData (Date data) {
		//verifico che la data non sia già stata inserita
		if (this.getPacchetto().getDatePartenza().indexOf(data) == -1) {
			try {
				pacchettoBean.aggiuntaDataPartenza(getPacchetto(), data);
				JsfUtil.infoMessage("Data aggiunta");
			} catch (PacchettoInesistenteException e) {
				JsfUtil.errorMessage("Pacchetto inesistente!");
			}
		} else
			JsfUtil.errorMessage("Data già inserita!");
	}
	
	/**
	 * Permette di rimuovere una data di partenza dal pacchetto
	 * @param data La data da rimuovere
	 */
	public void rimuoviDataPartenza (Date data) {
		//controllo che rimanaga almeno una data nel pacchetto
		if (this.getPacchetto().getDatePartenza().size() > 1) {
			try {
				pacchettoBean.rimuoviDataPartenza(getPacchetto(), data);
				JsfUtil.infoMessage("Data rimossa!");
			} catch (PacchettoInesistenteException e) {
				JsfUtil.errorMessage("Pacchetto inesistente!");
			}
		} else
			JsfUtil.errorMessage("Ultima data rimasta, impossibile eliminare!");
	}
	
	/**
	 * Permette di aggiungere una durata nel pacchetto
	 * @param durata La durata da aggiungere
	 */
	public void salvaDurata (int durata) {
		//verifico che la durata non sia già stata inserita
		if (this.getPacchetto().getDurate().indexOf(durata) == -1) {
			try {
				pacchettoBean.aggiuntaDurata(getPacchetto(), durata);
				JsfUtil.infoMessage("Durata aggiunta!");
			} catch (PacchettoInesistenteException e) {
				JsfUtil.errorMessage("Pacchetto inesistente!");
			}
		} else
			JsfUtil.errorMessage("Durata già esistente!");
	}
	
	/**
	 * Permette di rimuovere una durata dal pacchetto
	 * @param durata La durata da rimuovere
	 */
	public void rimuoviDurata (int durata) {
		//controllo che rimanga almeno una durata nel pacchetto
		if (this.getPacchetto().getDurate().size() > 1) {
			try {
				pacchettoBean.rimuoviDurata(getPacchetto(), durata);
				JsfUtil.infoMessage("Durata rimossa!");
			} catch (PacchettoInesistenteException e) {
				JsfUtil.errorMessage("Pacchetto inesistente!");
			}
		} else
			JsfUtil.errorMessage("Impossibile rimuovere la durata!");
	}
	
	/**
	 * Permette di aggiungere un collegamento nel pacchetto
	 * @param idPacchetto L'identificativo del pacchetto
	 * @param collegamento Il collegamento
	 * @return L'indirizzo della pagina di dettaglio del pacchetto
	 */
	public String aggiuntaCollegamento (int idPacchetto, CollegamentoDTO collegamento) {
		try {
			pacchettoBean.aggiuntaCollegamento(idPacchetto, collegamento);
			return "dettagliPacchetto?idPacchetto=" + idPacchetto + "&faces-redirect=true";
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		} catch (InsertException e) {
			JsfUtil.errorMessage("Collegamento già esistente!");
		}
		return null;
	}
	
	/**
	 * Permette di rimuovere un collegamento dal pacchetto
	 * @param collegamento Il collegamento che si vuole rimuovere
	 */
	public void rimuoviCollegamento (CollegamentoDTO collegamento) {
		try {
			pacchettoBean.rimuoviCollegamento(getPacchetto(), collegamento);
			JsfUtil.infoMessage("Collegamento rimosso!");
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
}
