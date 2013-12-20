package eccezioni;

public class EscursioneInesistenteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public EscursioneInesistenteException () {
		super();
	}
	
	public EscursioneInesistenteException (String messaggio) {
		super(messaggio);
	}

}
