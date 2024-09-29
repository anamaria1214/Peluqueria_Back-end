package co.edu.uniquindio.peluqueria.model.documentos;

import co.edu.uniquindio.peluqueria.model.enums.EstadoCita;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("citas")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cita {
    @Id
    private String id;
    private String idCliente;
    private String idServicio;
    private String idEstilista;
    private LocalDateTime fecha;
    private EstadoCita estado;

}
