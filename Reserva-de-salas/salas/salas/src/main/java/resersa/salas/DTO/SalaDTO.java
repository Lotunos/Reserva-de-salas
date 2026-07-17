package resersa.salas.DTO;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import resersa.salas.Model.ReservaModel;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalaDTO {
    @Size(max=10)
    private String nome;
    @NotNull
    private Integer capacidade;
    private List<ReservaModel> reservas;
    public SalaDTO(String nome,Integer capacidade){
        this.capacidade = capacidade;
        this.nome = nome;
    }
}
