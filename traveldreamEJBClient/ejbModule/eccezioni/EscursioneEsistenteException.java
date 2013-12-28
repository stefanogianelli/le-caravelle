package eccezioni;

public class EscursioneEsistenteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public EscursioneEsistenteException () {
		super();
	}
	
	public EscursioneEsistenteException (String messaggio) {
		super(messaggio);
	}

}
