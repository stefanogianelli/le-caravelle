package utils;

import interfaces.EmailBeanLocal;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Session Bean implementation class EmailBean
 */
@Stateless
public class EmailBean implements EmailBeanLocal {
	
	private final String domain = "http://localhost:8080/traveldreamWeb/";

	@Resource(name = "mail/traveldream")
	private Session mailSession;
    
    /**
     * Permette l'invio di email
     * @param email L'indirizzo email del destinatario
     * @param oggetto L'oggetto del messaggio
     * @param messaggio Il corpo del messaggio
     * @throws MessagingException
     */
    private void inviaMessaggio (String email, String oggetto, String messaggio) throws MessagingException {
    	Message msg = new MimeMessage(mailSession);
		    
    	msg.setSubject(oggetto);
    	msg.setRecipient(RecipientType.TO, new InternetAddress(email));
    	msg.setText(messaggio);
	      
    	Transport.send(msg);
    }
    
    @Override
    public void inviaPassword (String email, String password) throws MessagingException {
    	String messaggio = "Benvenuto!\n\nEcco i dati che utilizzerai per accedere ai servizi:\n\n- email: " + email + "\n\n- password: " + password;
    	this.inviaMessaggio(email, "Benvenuto in TravelDream!", messaggio);
    }
    
    @Override
    public void resetPassword (String email, String password) throws MessagingException {
    	String messaggio = "La tua nuova password è: " + password;
    	this.inviaMessaggio(email, "Reset Password", messaggio);
    }
    
    @Override
    public void condividiPacchetto (String nome, String emailAmico, int idPacchetto) throws MessagingException {
    	String messaggio = nome + " ha condiviso un pacchetto con te!\n\nPuoi vedere i dettagli del pacchetto cliccando sul link:\n" + domain + "dettagliPacchettoCondiviso.xhtml?idPacchetto=" + idPacchetto + "&email=" + emailAmico;
    	this.inviaMessaggio(emailAmico, "Condivisione pacchetto", messaggio);
    }
    
    @Override
    public void confermaAcquisto (String email, String nomePacchetto) throws MessagingException {
    	String messaggio = "Grazie per aver acquistato il pacchetto " + nomePacchetto;
    	this.inviaMessaggio(email, "Conferma acquisto", messaggio);
    }
}
