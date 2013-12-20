package eccezioni;

public class CittaInesistenteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CittaInesistenteException () {
		super();
	}
	
	public CittaInesistenteException (String messaggio) {
		super(messaggio);
	}

}
