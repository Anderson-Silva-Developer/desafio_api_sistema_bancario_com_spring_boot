package br.com.anderson_silva.Banking_system.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NotificationService {


    public int sendMail(String userFromName,String amount,String email) throws IOException {
        Email from = new Email(System.getenv("SENDER_MAIL"));
        String subject = "Bank\nTransação Bancária de Transferência";
        Email to = new Email(email);
        Content content = new Content("text/plain", " Transfêrencia recebida de :"+userFromName+"\n\n Valor da Transfêrencia: "+amount);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("KEY_MAIL"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            return  response.getStatusCode();
        } catch (IOException ex) {
            throw ex;
        }
    }

}
