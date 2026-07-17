package resersa.salas.DTO;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaDTO {
    @NotNull
    @Size(max=100)
    private String responsavel;
    @NotNull
    private LocalDate data;
    @NotNull
    private LocalTime inicio;
    @NotNull
    private LocalTime fim;
    @NotNull
    private String observacao;
    @NotNull
    private Integer idSala;
}
