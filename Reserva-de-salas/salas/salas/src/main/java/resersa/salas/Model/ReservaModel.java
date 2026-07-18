package resersa.salas.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import resersa.salas.DTO.ReservaDTO;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name="reserva")
public class ReservaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idreserva;
    @Column(nullable = false, length = 100)
    private String responsavel;
    @Column(nullable = false)
    private LocalDate data;
    @Column(nullable=false)
    private LocalTime inicio;
    @Column(nullable=false)
    private LocalTime fim;
    @Column(nullable=false)
    private String observacao;
    @ManyToOne
    @JoinColumn(name = "id_sala", nullable = false)
    private SalaModel sala;
    public ReservaModel(ReservaDTO dto){
        this.responsavel = dto.getResponsavel();
        this.data = dto.getData();
        this.inicio = dto.getInicio();
        this.fim = dto.getFim();
        this.observacao = dto.getObservacao();
        this.sala = dto.getSala();
    }
    public ReservaModel(){

    }
}
