package validators;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

@ManagedBean(name="imageValidator")
@RequestScoped
public class ImageValidator {
	
	private final int MAX_IMAGE_SIZE = 104857600;
	private final String [] CONTENT_TYPE = {"image/jpeg", "image/jpg", "image/png"};

	public void validateImage (FacesContext ctx, UIComponent comp, Object value) {
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		Part file = (Part)value;
		boolean check_type = false;
				
		if (file.getSize() > MAX_IMAGE_SIZE) {
			msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Immagine di dimesioni elevate", null));
		} else if (file.getSize() > 0) {		
			for (int i = 0; i < CONTENT_TYPE.length; i++) {
				if (CONTENT_TYPE[i].equals(file.getContentType())) {
					check_type = true;
					break;
				}
			}			
			if (!check_type) {
				msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato non supportato", null));
			}
		}
		
		if (!msgs.isEmpty()) {
			throw new ValidatorException(msgs);
		}
	}
}
