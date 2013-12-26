package eccezioni;

public class EntitaEsistenteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public EntitaEsistenteException () {
		super();
	}
	
	public EntitaEsistenteException (String messaggio) {
		super(messaggio);
	}

}
