package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.EscursioneDTO;

@Local
public interface GestoreEscursione {

	public List<EscursioneDTO> elencoEscursioni ();
	
	public EscursioneDTO dettagliEscursione (int idEscursione);
	
	public void creaEscursione (EscursioneDTO escursione);
	
	public void modificaDatiEscursione (EscursioneDTO escursione);
	
	public void eliminaEscursione (int idEscursione);
}
