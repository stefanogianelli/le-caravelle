package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityExistsException;

import utils.JsfUtil;
import dtos.CollegamentoDTO;
import dtos.DestinazioneDTO;
import dtos.PacchettoDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.CollegamentoInesistenteException;
import eccezioni.DestinazioneInesistenteException;
import eccezioni.HotelInesistenteException;
import eccezioni.PacchettoInesistenteException;
import ejbs.GestorePacchetto;
import enums.TipoPacchetto;

@ManagedBean(name="pacchetto")
@RequestScoped
public class PacchettoBean {

	@EJB
	private GestorePacchetto pacchettoBean;
	
	private PacchettoDTO pacchetto;
	
	@PostConstruct
	public void setUp () {
		pacchetto = new PacchettoDTO();
	}

	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}
	
	public List<PacchettoDTO> cercaPacchetti (TipoPacchetto tipo) {
		return pacchettoBean.elencoPacchetti(tipo);
	}
	
	/**
	 * Permette la creazione di un nuovo pacchetto
	 * @param pacchetto I dati del pacchetto
	 */
	public void creaPacchetto (PacchettoDTO pacchetto) {
		try {
			pacchettoBean.creaPacchettoPersonalizzato(pacchetto);
		} catch (EntityExistsException e) {
			JsfUtil.errorMessage("Il pacchetto è già presente nel database!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		}
	}
	
	/**
	 * Permette il salvataggio delle modifiche fatte nel pacchetto
	 * @param pacchetto I dati del pacchetto
	 */
	public void salvaPacchetto (PacchettoDTO pacchetto) {
		try {
			pacchettoBean.salvaPacchetto(pacchetto);
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette la creazione di un pacchetto a partire da un pacchetto predefinito
	 * @param pacchetto I dati del pacchetto
	 */
	public void salvaPacchettoPredefinito (PacchettoDTO pacchetto) {
		try {
			pacchettoBean.salvaPacchettoPredefinito(pacchetto);
		} catch (EntityExistsException e) {
			JsfUtil.errorMessage("Il pacchetto è già presente nel database!");
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette l'acquisto di un pacchetto
	 * @param pacchetto Il pacchetto da acquistare
	 */
	public void acquistaPacchetto (PacchettoDTO pacchetto) {
		try {
			pacchettoBean.acquistaPacchetto(pacchetto);
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette la condivisione di un pacchetto con un amico
	 * @param pacchetto Il pacchetto che si desidera condividere
	 * @param email L'email dell'amico con il quale condividere il pacchetto
	 */
	public void condividiPacchetto (PacchettoDTO pacchetto, String email) {
		try {
			pacchettoBean.condividiPacchetto(pacchetto, email);
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		}
	}
	
	/**
	 * Permette l'eliminazione di un pacchetto
	 * @param pacchetto Il pacchetto che si desidera eliminare
	 */
	public void eliminaPacchetto (PacchettoDTO pacchetto) {
		try {
			pacchettoBean.eliminaPacchetto(pacchetto);
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette l'aggiunta di una nuova destinazione nel pacchetto
	 * @param pacchetto Il pacchetto nel quale aggiungere la destinazione
	 * @param destinazione I dati della nuova destinazione
	 */
	public void aggiuntaDestinazione (PacchettoDTO pacchetto, DestinazioneDTO destinazione) {
		try {
			pacchettoBean.aggiuntaDestinazione(pacchetto, destinazione);
		} catch (CittaInesistenteException e) {
			JsfUtil.errorMessage("Città sconosciuta!");
		} catch (HotelInesistenteException e) {
			JsfUtil.errorMessage("Hotel inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette di eliminare una destinazione da un pacchetto
	 * @param pacchetto Il pacchetto dal quale eliminare la destinazione
	 * @param destinazione La destinazione da eliminare
	 */
	public void eliminaDestinazione (PacchettoDTO pacchetto, DestinazioneDTO destinazione) {
		try {
			pacchettoBean.eliminaDestinazione(pacchetto, destinazione);
		} catch (DestinazioneInesistenteException e) {
			JsfUtil.errorMessage("Destinazione inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette di aggiungere un collegamento nel pacchetto
	 * @param pacchetto Il pacchetto nel quale aggiungere il collegamento
	 * @param collegamento Il collegamento scelto
	 */
	public void aggiuntaCollegamento (PacchettoDTO pacchetto, CollegamentoDTO collegamento) {
		try {
			pacchettoBean.aggiuntaCollegamento(pacchetto, collegamento);
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
	
	/**
	 * Permette la sostituzione di un collegamento con un altro
	 * @param pacchetto Il pacchetto nel quale effettuare la sostituzione
	 * @param collegamento IL nuovo collegamento scelto
	 */
	public void modificaCollegamento (PacchettoDTO pacchetto, CollegamentoDTO collegamento) {
		try {
			pacchettoBean.modificaCollegamento(pacchetto, collegamento);
		} catch (CollegamentoInesistenteException e) {
			JsfUtil.errorMessage("Collegamento inesistente!");
		} catch (PacchettoInesistenteException e) {
			JsfUtil.errorMessage("Pacchetto inesistente!");
		}
	}
}
