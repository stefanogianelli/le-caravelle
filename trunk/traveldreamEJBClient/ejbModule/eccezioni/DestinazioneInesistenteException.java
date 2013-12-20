package eccezioni;

public class DestinazioneInesistenteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DestinazioneInesistenteException () {
		super();
	}
	
	public DestinazioneInesistenteException (String messaggio) {
		super(messaggio);
	}

}
