package utils;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="data")
@RequestScoped
public class DataBean {

	/**
	 * Si occupa di formattare la data in dd/MM/yyyy (es.: 28/12/2013)
	 * @param data La data da formattare
	 * @return La stringa del formato desiderato
	 */
	public String getData (Date data) {
		return DataUtils.getData(data);
	}
	
	/**
	 * Restituisce la data odierna
	 * @return La data odierna
	 */
	public Date getDataOdierna () {
		return DataUtils.getDataOdierna();
	}
	
	/**
	 * Restituisce la date di domani
	 * @return La data di domani
	 */
	public Date getDataGiornoSuccessivo () {
		return DataUtils.getDataGiornoSuccessivo();
	}
	
}
