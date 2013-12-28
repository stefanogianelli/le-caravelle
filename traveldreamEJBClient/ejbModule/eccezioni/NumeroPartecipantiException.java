package eccezioni;

public class NumeroPartecipantiException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NumeroPartecipantiException () {
		super();
	}
	
	public NumeroPartecipantiException (String messaggio) {
		super(messaggio);
	}

}
