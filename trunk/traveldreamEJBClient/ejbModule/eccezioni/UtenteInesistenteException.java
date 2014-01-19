package eccezioni;

public class UtenteInesistenteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UtenteInesistenteException () {
		super();
	}
	
	public UtenteInesistenteException (String messaggio) {
		super(messaggio);
	}

}
