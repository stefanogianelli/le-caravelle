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
	 * Ritorna l'elenco di tutte le citt� presenti nel database
	 * @return L'elenco delle citt�
	 */
	List<CittaDTO> elencoCitta();
	
	/**
	 * Restituisce la citt� associata all'identificativo
	 * @param idCitta L'identificativo della citt�
	 * @return Il DTO relativo
	 * @throws CittaInesistenteException Quando la citt� non viene trovata nel database
	 */
	CittaDTO getCitta (int idCitta) throws CittaInesistenteException;
	
	/**
	 * Permette di restituire il DTO della citt� associata ad un nome
	 * @param nome Il nome della citt�
	 * @return Il rispettivo DTO
	 * @throws CittaInesistenteException Se la citt� non viene trovata nel database
	 */
	CittaDTO cercaCitta (String nome)  throws CittaInesistenteException;
	
	/**
	 * Permette di aggiungere una nuova citt� nel database
	 * @param citta I dati della citt�
	 * @throws InsertException Quando la citt� � gi� presente nel database
	 */
	void nuovaCitta (CittaDTO citta) throws InsertException;
	
	/**
	 * Permette di modificare i dati di una citt�
	 * @param citta I dati della citt�
	 * @throws CittaInesistenteException Quando la citt� da modificare non viene trovata nel database
	 * @throws InsertException Quando esiste un'altra citt� con lo stesso nome e nella stessa nazione
	 */
	void modificaCitta (CittaDTO citta) throws CittaInesistenteException, InsertException;
	
	/**
	 * Permette di aggiungere un'immagine alla citt�
	 * @param idCitta L'identificativo della citt�
	 * @param immagine L'immagine da aggiungere
	 * @throws UploadException Quando si verifica un errore durante il caricamento dell'immagine
	 * @throws CittaInesistenteException Quando la citt� non viene trovata nel database
	 * @throws InsertException Quando viene passata un'immagine nulla
	 */
	void aggiuntaImmagine (int idCitta, Part immagine) throws UploadException, CittaInesistenteException, InsertException;
	
	/**
	 * Permette la rimozione di un'immagine
	 * @param idCitta L'identificativo della citt�
	 * @param nomeImmagine Il nome dell'immagine da rimuovere
	 * @throws CittaInesistenteException Quando la citt� non viene trovata nel database
	 */
	void rimuoviImmagine (int idCitta, String nomeImmagine) throws CittaInesistenteException;
	
	/**
	 * Permette l'eliminazione di una citt�
	 * @param idCitta L'identificativo della citt� da rimuovere
	 * @throws CittaInesistenteException Quando la citt� non viene trovata nel database
	 */
	void eliminaCitta (int idCitta) throws CittaInesistenteException;
}
