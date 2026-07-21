package resersa.salas.Service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import resersa.salas.DAO.ReservaDAO;
import resersa.salas.DAO.SalaDAO;
import resersa.salas.DTO.ReservaInputDTO;
import resersa.salas.DTO.ReservaOutputDTO;
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
    private final ReservaDAO reservadao;
    private final SalaDAO saladao;
    private boolean verificador=false;
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
    public ReservaOutputDTO getReserva(int id){
        ReservaModel model = reservadao.findById(id).get();
        return converterParaDTO(model);
    }
    @Transactional
    public List<ReservaOutputDTO> getAllReserva() {
        return reservadao.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }
    @Transactional
    public List<ReservaOutputDTO> getAllReservaDataIdSala(){
        return reservadao.findAllByOrderBySalaIdSalaAscDataAscInicioAsc()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }
    @Transactional
    public String deletarReserva(int id){
        ReservaOutputDTO reserva = getReserva(id);
        if(reserva == null){
            return "Reserva não encontrada";
        }
        reservadao.deleteById(id);
        return "ok";

    }
    @Transactional
    public List<ReservaOutputDTO> getAllReservaByData(String data){
        LocalDate dataFormatada = LocalDate.parse(data,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return reservadao.findAllByDataOrderByInicioAsc(dataFormatada).stream()
                .map(this::converterParaDTO)
                .toList();
    }
    @Transactional
    public Page<ReservaOutputDTO> getAllReservaPaginacao(int pagina, int tamanho){
        Pageable page = PageRequest.of(
                pagina,
                tamanho,
                Sort.by("idreserva")
        );
        return reservadao.findAll(page).map(this::converterParaDTO);
    }
    //TODO: Lembrar de apagar esta rota quando finalizar o projeto
    private void criarReserva(
            int idSala,
            String responsavel,
            String data,
            int horaInicio,
            int minutoInicio,
            int horaFim,
            int minutoFim,
            String observacao) {


        SalaModel sala = new SalaModel();
        sala.setIdSala(idSala);

        ReservaModel reserva = new ReservaModel();
        reserva.setResponsavel(responsavel);
        reserva.setData(LocalDate.parse(data));
        reserva.setInicio(LocalTime.of(horaInicio, minutoInicio));
        reserva.setFim(LocalTime.of(horaFim, minutoFim));
        reserva.setObservacao(observacao);
        reserva.setSala(sala);

        reservadao.save(reserva);
    }
    @Transactional
    public String inserirEmLote() {
        if(verificador==true) {
            return "A inserção dos dados só pode ser feita uma vez por sessão";
        }

        criarReserva(1, "Carlos Mendes", "2026-07-22", 9, 0, 10, 30,
                "Reunião de alinhamento semanal");

        criarReserva(1, "Carlos Mendes de Sá", "2026-12-18", 11, 0, 12, 30,
                "Reunião de alinhamento semanal 2ª temporada");

        criarReserva(1, "Carlos Mendes de Sá Filho", "2025-07-18", 13, 0, 14, 30,
                "Reunião de alinhamento semanal 3ª temporada");

        criarReserva(2, "Ana Paula Souza", "2026-07-22", 8, 0, 9, 0,
                "Treinamento de equipe");

        criarReserva(3, "Marcos Oliveira", "2026-07-22", 9, 30, 10, 30,
                "Entrevista de candidatos");

        criarReserva(1, "Fernanda Lima", "2026-07-23", 14, 0, 15, 0,
                "Planejamento financeiro");

        criarReserva(2, "Ricardo Gomes", "2026-07-23", 15, 0, 16, 30,
                "Apresentação comercial");

        criarReserva(3, "Juliana Castro", "2026-07-24", 10, 0, 11, 0,
                "Workshop interno");

        criarReserva(1, "Paulo Henrique", "2026-07-24", 11, 30, 12, 30,
                "Revisão de contratos");

        criarReserva(2, "Camila Rocha", "2026-07-24", 13, 30, 15, 0,
                "Reunião com fornecedores");

        criarReserva(3, "Eduardo Nunes", "2026-07-25", 8, 30, 9, 30,
                "Treinamento operacional");

        criarReserva(1, "Larissa Almeida", "2026-07-25", 10, 0, 11, 30,
                "Revisão de projeto");

        criarReserva(2, "Roberto Silva", "2026-07-25", 14, 0, 16, 0,
                "Definição de orçamento");

        criarReserva(3, "Patrícia Costa", "2026-07-26", 9, 0, 10, 0,
                "Reunião estratégica");

        criarReserva(1, "Lucas Ferreira", "2026-07-26", 10, 30, 12, 0,
                "Sprint Planning");

        criarReserva(2, "Beatriz Santos", "2026-07-26", 13, 0, 14, 30,
                "Treinamento de integração");

        criarReserva(3, "Daniel Martins", "2026-07-27", 8, 0, 9, 30,
                "Alinhamento de TI");

        criarReserva(1, "Gabriela Moreira", "2026-07-27", 9, 30, 11, 0,
                "Apresentação de resultados");

        criarReserva(2, "Renato Almeida", "2026-07-27", 11, 30, 12, 30,
                "Reunião jurídica");

        criarReserva(3, "Vanessa Ribeiro", "2026-07-28", 14, 0, 15, 30,
                "Capacitação");

        criarReserva(1, "Felipe Andrade", "2026-07-28", 15, 30, 17, 0,
                "Discussão de arquitetura");

        criarReserva(2, "Mariana Duarte", "2026-07-29", 9, 0, 10, 30,
                "Planejamento trimestral");

        criarReserva(3, "Thiago Barros", "2026-07-29", 11, 0, 12, 0,
                "Reunião com clientes");
        verificador = true;
        return "ok";
    }
    //TODO: Talvez seja melhor colocar estes metodos em uma classe separada
    private String validacao(ReservaInputDTO dto){
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
    private ReservaOutputDTO converterParaDTO(ReservaModel model) {
        ReservaOutputDTO dto = new ReservaOutputDTO();

        dto.setIdReserva(model.getIdreserva());
        dto.setResponsavel(model.getResponsavel());
        dto.setData(model.getData());
        dto.setInicio(model.getInicio());
        dto.setFim(model.getFim());
        dto.setObservacao(model.getObservacao());
        dto.setSala(model.getSala());
        return dto;
    }

}
