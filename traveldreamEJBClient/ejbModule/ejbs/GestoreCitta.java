package ejbs;

import java.util.List;

import javax.ejb.Local;

import dtos.CittaDTO;

@Local
public interface GestoreCitta {

	List<CittaDTO> elencoCitta();
}
