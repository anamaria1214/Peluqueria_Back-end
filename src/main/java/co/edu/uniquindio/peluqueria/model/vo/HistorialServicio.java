package co.edu.uniquindio.peluqueria.model.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HistorialServicio {
    private String servicioId;
    private LocalDateTime fecha;
    private String comentarios;
}
