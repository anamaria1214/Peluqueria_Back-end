package co.edu.uniquindio.peluqueria.servicios.interfaces;

import org.springframework.scheduling.annotation.Async;

public interface EmailServicio {

    void enviarCorreo (co.edu.uniquindio.peluqueria.dto.EmailDTO emailDTO) throws Exception;
}
