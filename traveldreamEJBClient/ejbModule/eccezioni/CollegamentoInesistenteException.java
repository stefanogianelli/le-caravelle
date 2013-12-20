package eccezioni;

public class CollegamentoInesistenteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CollegamentoInesistenteException () {
		super();
	}
	
	public CollegamentoInesistenteException (String messaggio) {
		super(messaggio);
	}

}
