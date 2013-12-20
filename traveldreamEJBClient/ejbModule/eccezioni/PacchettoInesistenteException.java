package eccezioni;

public class PacchettoInesistenteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public PacchettoInesistenteException () {
		super();
	}
	
	public PacchettoInesistenteException (String messaggio) {
		super(messaggio);
	}

}
