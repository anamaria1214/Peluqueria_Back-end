package co.edu.uniquindio.peluqueria.model.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Horario {
    private String dia;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
}
