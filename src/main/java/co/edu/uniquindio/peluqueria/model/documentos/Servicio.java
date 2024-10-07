package co.edu.uniquindio.peluqueria.model.documentos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("servicios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

public class Servicio {
    @Id
    private String id;
    private String nombreServicio;
    private String descripcion;
    private double precio;
    private int duracionMinutos;
}

