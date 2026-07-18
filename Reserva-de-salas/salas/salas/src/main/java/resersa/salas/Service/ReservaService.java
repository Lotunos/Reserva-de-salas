package resersa.salas.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import resersa.salas.DAO.ReservaDAO;
import resersa.salas.DAO.SalaDAO;
import resersa.salas.DTO.ReservaDTO;
import resersa.salas.Model.ReservaModel;
import resersa.salas.Model.SalaModel;

import java.util.List;

@Service
public class ReservaService {
    private ReservaDAO reservadao;
    private SalaDAO saladao;
    public ReservaService(ReservaDAO reservadao, SalaDAO saladao){
        this.reservadao = reservadao;
        this.saladao = saladao;
    }
    @Transactional
    public ReservaModel criarReserva(ReservaDTO dto){
        SalaModel salaEncontrada = saladao.findById(dto.getIdSala())
                .orElseThrow(()->new RuntimeException("Sala não encontrada"));
        ReservaModel novaReserva = new ReservaModel();
        novaReserva.setResponsavel(dto.getResponsavel());
        novaReserva.setData(dto.getData());
        novaReserva.setInicio(dto.getInicio());
        novaReserva.setFim(dto.getFim());
        novaReserva.setObservacao(dto.getObservacao());
        novaReserva.setSala(salaEncontrada);
        return reservadao.save(novaReserva);
    }
    /*@Transactional
    public ReservaModel atualizarReserva(ReservaDTO dto){
        ReservaModel reserva = new ReservaModel(dto);
        reservadao.save(reserva);
        return reserva;
    }
    @Transactional
    public ReservaModel getReserva(int id){
        return  reservadao.getById(id);
    }
    @Transactional
    public List<ReservaModel> getAllReserva(){
        return reservadao.findAll();
    }
    @Transactional
    public void deletarReserva(int id){
        reservadao.deleteById(id);
    }*/


}
