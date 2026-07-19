package resersa.salas.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import resersa.salas.DAO.ReservaDAO;
import resersa.salas.DAO.SalaDAO;
import resersa.salas.DTO.ReservaInputDTO;
import resersa.salas.Model.ReservaModel;
import resersa.salas.Model.SalaModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {
    private ReservaDAO reservadao;
    private SalaDAO saladao;
    public ReservaService(ReservaDAO reservadao, SalaDAO saladao){
        this.reservadao = reservadao;
        this.saladao = saladao;
    }
    @Transactional
    public String criarReserva(ReservaInputDTO dto){
        String validacao = validacao(dto);
        if(!validacao.equals("ok")){
            return validacao;
        }
        SalaModel salaEncontrada = saladao.getReferenceById(dto.getIdSala());
        ReservaModel novaReserva = new ReservaModel();
        novaReserva.setResponsavel(dto.getResponsavel());
        novaReserva.setData(dto.getData());
        novaReserva.setInicio(dto.getInicio());
        novaReserva.setFim(dto.getFim());
        novaReserva.setObservacao(dto.getObservacao());
        novaReserva.setSala(salaEncontrada);
        reservadao.save(novaReserva);
        return validacao;
    }
    @Transactional
    public String atualizarReserva(ReservaInputDTO dto){
        String validacao = validacao(dto);
        if(!validacao.equals("ok")){
            return validacao;
        }
        ReservaModel novaReserva = reservadao.findById(dto.getIdReserva())
                .orElseThrow(()->new RuntimeException("Reserva não encontrada"));
        SalaModel salaEncontrada = saladao.getReferenceById(dto.getIdSala());
        novaReserva.setResponsavel(dto.getResponsavel());
        novaReserva.setData(dto.getData());
        novaReserva.setInicio(dto.getInicio());
        novaReserva.setFim(dto.getFim());
        novaReserva.setObservacao(dto.getObservacao());
        novaReserva.setSala(salaEncontrada);
        reservadao.save(novaReserva);
        return validacao;
    }
    @Transactional
    public Optional<ReservaModel> getReserva(int id){
        return reservadao.findById(id);
    }
    @Transactional
    public List<ReservaModel> getAllReserva(){
        return reservadao.findAll();
    }
    @Transactional
    public String deletarReserva(int id){
        Optional<ReservaModel> reserva = getReserva(id);
        if(reserva.isEmpty()){
            return "Reserva não encontrada";
        }
        reservadao.deleteById(id);
        return "ok";

    }
    //TODO: Lembrar de apagar esta rota quando finalizar o projeto
    @Transactional
    public void inserirEmLot(){
        LocalDate dataPadrao = LocalDate.parse("2026-07-18");

        SalaModel sala1 = new SalaModel();
        sala1.setIdSala(1);
        ReservaModel reserva1 = new ReservaModel();
        reserva1.setResponsavel("Carlos Mendes");
        reserva1.setData(dataPadrao);
        reserva1.setInicio(LocalTime.of(9, 0));
        reserva1.setFim(LocalTime.of(10, 30));
        reserva1.setObservacao("Reunião de alinhamento semanal");
        reserva1.setSala(sala1);
        reservadao.save(reserva1);

        dataPadrao = LocalDate.parse("2026-07-18");
        SalaModel sala2 = new SalaModel();
        sala2.setIdSala(1);
        ReservaModel reserva2 = new ReservaModel();
        reserva2.setResponsavel("Carlos Mendes de Sá");
        reserva2.setData(dataPadrao);
        reserva2.setInicio(LocalTime.of(11,00 ));
        reserva2.setFim(LocalTime.of(12, 30));
        reserva2.setObservacao("Reunião de alinhamento semanal 2º temporada");
        reserva2.setSala(sala2);
        reservadao.save(reserva2);

        dataPadrao = LocalDate.parse("2026-07-18");
        SalaModel sala3 = new SalaModel();
        sala3.setIdSala(1);
        ReservaModel reserva3 = new ReservaModel();
        reserva3.setResponsavel("Carlos Mendes de Sá Filho");
        reserva3.setData(dataPadrao);
        reserva3.setInicio(LocalTime.of(13, 0));
        reserva3.setFim(LocalTime.of(14, 30));
        reserva3.setObservacao("Reunião de alinhamento semanal 3º temporada");
        reserva3.setSala(sala3);
        reservadao.save(reserva3);

    }
    public String validacao(ReservaInputDTO dto){
        Optional<SalaModel> reserva = saladao.findById(dto.getIdSala());
        if(reserva.isEmpty()){
            return "Sala não encontrada";
        }
        if(dto.getInicio().isAfter(dto.getFim()) || dto.getInicio().equals(dto.getFim())){
            return "A o início da reserva não pode ser igual ou superior ao fim da reserva";
        }
        List<ReservaModel> listaReserva = new ArrayList<>(
                reservadao.findBySalaIdSalaAndData(dto.getIdSala(),dto.getData())
        );
        Optional<ReservaModel> existeConflito = listaReserva.stream()
                .filter(id->!id.getIdreserva().equals(dto.getIdReserva()))
                .filter( hora ->
                         dto.getInicio().isBefore(hora.getFim()) &&
                         dto.getFim().isAfter(hora.getInicio())
                ).findFirst();
        if (existeConflito.isPresent()) {
            return String.format(
                    "Já existe uma reserva nesse horário!\nResponsável: %s\nData: %s\nInício: %s\nFim: %s",
                    existeConflito.get().getResponsavel(),
                    existeConflito.get().getData().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    existeConflito.get().getInicio(),
                    existeConflito.get().getFim()
            );
        }
        return "ok";
    }

}
