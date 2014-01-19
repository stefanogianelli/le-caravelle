package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import eccezioni.UploadException;

public class UploadBean {
	
	private final String resourcesPath = "/resources/images/";
	
	/**
	 * Carica il file sul server e ne restituisce il nome
	 * @param resourceType La directory all'interno delle risorse dove salvare il file
	 * @return Il nome del file caricato
	 * @throws UploadException Quando si verifica un errore nel caricamento del file
	 */
	public String upload (Part uploadFile, String resourceType) throws UploadException {
		File file = null;
        OutputStream output = null; 

        try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			
			String prefix = FilenameUtils.getBaseName(getFilename(uploadFile));
		    String suffix = FilenameUtils.getExtension(getFilename(uploadFile));	
		    
	    	String absoluteDiskPath = externalContext.getRealPath(resourcesPath + resourceType + "/");
            	
			file = File.createTempFile(prefix + "_", "." + suffix, new File(absoluteDiskPath));
			output = new FileOutputStream(file);
			IOUtils.copy(uploadFile.getInputStream(), output);
			return file.getName();
		} catch (IOException e) {
			if (file != null) file.delete();
			throw new UploadException();
		} finally {
            IOUtils.closeQuietly(output);
		}
	}
	
	/**
	 * Restituisce il nome del file caricato
	 * @param part Il file del quale si vuole conoscere il nome
	 * @return Il nome del file (comprensivo di estensione)
	 */
	private String getFilename(Part part) {  
		for (String cd : part.getHeader("content-disposition").split(";")) {  
			if (cd.trim().startsWith("filename")) {  
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");  
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);  
			}  
		}  
		return null;  
	} 
	
}
