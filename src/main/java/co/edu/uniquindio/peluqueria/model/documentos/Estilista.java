package co.edu.uniquindio.peluqueria.model.documentos;

import co.edu.uniquindio.peluqueria.model.vo.Horario;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("estilistas")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estilista {
    @Id
    private String id;
    private String nombreEstilista;
    //No se si sea necesario o si ponerlo en un enum
    private String especialidad;
    private List<Horario> horariosDisponibles;
}

