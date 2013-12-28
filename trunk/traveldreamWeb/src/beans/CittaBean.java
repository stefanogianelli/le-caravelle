package beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import dtos.CittaDTO;
import ejbs.GestoreCitta;

@ManagedBean(name="citta")
@RequestScoped
public class CittaBean {

	@EJB
	private GestoreCitta citta;
	
	/**
	 * Mostra l'elenco di città presenti nel database
	 * @return I nomi delle città
	 */
	public List<String> getNomiCitta () {
		List<CittaDTO> citta = this.citta.elencoCitta();
		List<String> nomi = new ArrayList<String>();
		for (CittaDTO c : citta) {
			nomi.add(c.getNome());
		}
		return nomi;
	}
}
