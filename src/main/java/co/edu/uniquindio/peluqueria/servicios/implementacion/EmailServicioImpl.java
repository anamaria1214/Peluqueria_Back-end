package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.dto.EmailDTO;
import co.edu.uniquindio.peluqueria.servicios.interfaces.EmailServicio;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServicioImpl implements EmailServicio {
    @Override
    @Async
    public void enviarCorreo(EmailDTO emailDTO) throws Exception {

        String destinatarioLimpio = emailDTO.destinatario().trim().replace("\"", "");

        Email email = EmailBuilder.startingBlank()
                .from("barbershop.uniquindio@gmail.com")
                .to(destinatarioLimpio)
                .withSubject(emailDTO.asunto())
                .withPlainText(emailDTO.cuerpo())
                .buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "barbershop.uniquindio@gmail.com", "yryn avyd fiev intb") // Correo como usuario SMTP
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {

            mailer.sendMail(email);
        }
    }
}
