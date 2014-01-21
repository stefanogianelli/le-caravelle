package ejbs;

import java.util.List;

import javax.ejb.Local;
import javax.servlet.http.Part;

import dtos.CittaDTO;
import eccezioni.CittaInesistenteException;
import eccezioni.InsertException;
import eccezioni.UploadException;

@Local
public interface GestoreCitta {

	/**
	 * Ritorna l'elenco di tutte le città presenti nel database
	 * @return L'elenco delle città
	 */
	List<CittaDTO> elencoCitta();
	
	/**
	 * Restituisce la città associata all'identificativo
	 * @param idCitta L'identificativo della città
	 * @return Il DTO relativo
	 * @throws CittaInesistenteException Quando la città non viene trovata nel database
	 */
	CittaDTO getCitta (int idCitta) throws CittaInesistenteException;
	
	/**
	 * Permette di restituire il DTO della città associata ad un nome
	 * @param nome Il nome della città
	 * @return Il rispettivo DTO
	 * @throws CittaInesistenteException Se la città non viene trovata nel database
	 */
	CittaDTO cercaCitta (String nome)  throws CittaInesistenteException;
	
	/**
	 * Permette di aggiungere una nuova città nel database
	 * @param citta I dati della città
	 * @throws InsertException Quando la città è già presente nel database
	 */
	void nuovaCitta (CittaDTO citta) throws InsertException;
	
	/**
	 * Permette di modificare i dati di una città
	 * @param citta I dati della città
	 * @throws CittaInesistenteException Quando la città da modificare non viene trovata nel database
	 * @throws InsertException Quando esiste un'altra città con lo stesso nome e nella stessa nazione
	 */
	void modificaCitta (CittaDTO citta) throws CittaInesistenteException, InsertException;
	
	/**
	 * Permette di aggiungere un'immagine alla città
	 * @param idCitta L'identificativo della città
	 * @param immagine L'immagine da aggiungere
	 * @throws UploadException Quando si verifica un errore durante il caricamento dell'immagine
	 * @throws CittaInesistenteException Quando la città non viene trovata nel database
	 * @throws InsertException Quando viene passata un'immagine nulla
	 */
	void aggiuntaImmagine (int idCitta, Part immagine) throws UploadException, CittaInesistenteException, InsertException;
	
	/**
	 * Permette la rimozione di un'immagine
	 * @param idCitta L'identificativo della città
	 * @param nomeImmagine Il nome dell'immagine da rimuovere
	 * @throws CittaInesistenteException Quando la città non viene trovata nel database
	 */
	void rimuoviImmagine (int idCitta, String nomeImmagine) throws CittaInesistenteException;
	
	/**
	 * Permette l'eliminazione di una città
	 * @param idCitta L'identificativo della città da rimuovere
	 * @throws CittaInesistenteException Quando la città non viene trovata nel database
	 */
	void eliminaCitta (int idCitta) throws CittaInesistenteException;
}
