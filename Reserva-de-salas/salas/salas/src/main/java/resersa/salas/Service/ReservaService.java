package resersa.salas.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import resersa.salas.DAO.ReservaDAO;
import resersa.salas.DTO.ReservaDTO;
import resersa.salas.Model.ReservaModel;
import resersa.salas.Model.SalaModel;

import java.util.List;

@Service
public class ReservaService {
    private ReservaDAO dao;
    public ReservaService(ReservaDAO dao){
        this.dao = dao;
    }
    @Transactional
    public String criarReserva(ReservaDTO dto, SalaModel sala){
        try {
            ReservaModel model = new ReservaModel(dto);
            model.setSala(sala);
            dao.save(model);
        }catch(Exception e){
            return "" + e;
        }
        return "Sucesso";
    }
    @Transactional
    public ReservaModel getReserva(int id){
        return  dao.getById(id);
    }
    @Transactional
    public List<ReservaModel> getAllReserva(){
        return dao.findAll();
    }
    @Transactional
    public String deletarReserva(int id){
        try {
            dao.deleteById(id);
        }catch (Exception e){
            return "" + e;
        }
        return "Sucesso";
    }
    @Transactional
    public String atualizarReserva(ReservaDTO dto){
        return "Sucesso";
    }
}
