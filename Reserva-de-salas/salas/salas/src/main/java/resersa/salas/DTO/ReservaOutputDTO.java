package resersa.salas.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import resersa.salas.Model.SalaModel;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaOutputDTO {
    public class ReservaDTO {
        @NotNull
        @Size(max=100)
        private String responsavel;
        @NotNull
        @JsonFormat(pattern = "dd-MM-yyyy")
        @DateTimeFormat(pattern = "dd-MM-yyyy")
        private LocalDate data;
        @NotNull
        @JsonFormat(pattern = "HH:mm")
        private LocalTime inicio;
        @NotNull
        @JsonFormat(pattern = "HH:mm")
        private LocalTime fim;

        private String observacao;
        @NotNull
        private SalaModel sala;
    }
}
