package resersa.salas.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import resersa.salas.DAO.ReservaDAO;
import resersa.salas.DTO.ReservaDTO;
import resersa.salas.Model.ReservaModel;

import java.util.List;

@Service
public class ReservaService {
    private ReservaDAO dao;
    public ReservaService(ReservaDAO dao){
        this.dao = dao;
    }
    @Transactional
    public ReservaModel criarReserva(ReservaDTO dto){
        ReservaModel reserva = new ReservaModel(dto);
        dao.save(reserva);
        return reserva;
    }
    @Transactional
    public ReservaModel getReserva(int id){
        if(dao.findById(id)==null) {
            throw new IllegalArgumentException("Não existe reserva com esta id");
        }
        return  dao.getById(id);
    }
    @Transactional
    public List<ReservaModel> getAllReserva(){
        return dao.findAll();
    }
    @Transactional
    public String deletarReserva(int id){
        dao.deleteById(id);
        return "Sucesso";
    }
    @Transactional
    public String atualizarReserva(ReservaDTO dto){
        if(dao.findById(dto.getIdReserva())==null) {
            return "Não existe reserva com esta id";
        }
        dao.save(new ReservaModel(dto));
        return "Sucesso";
    }

}
