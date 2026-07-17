package resersa.salas.DTO;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import resersa.salas.Model.ReservaModel;

import java.util.List;

public class SalaDTO {
    @Size(max=10)
    private String nome;
    @NotNull
    private Integer capacidade;
    private List<ReservaModel> reservas;
}
