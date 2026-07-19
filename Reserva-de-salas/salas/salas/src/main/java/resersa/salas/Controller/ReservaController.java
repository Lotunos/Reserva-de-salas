package resersa.salas.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import resersa.salas.DTO.ReservaInputDTO;
import resersa.salas.Model.ReservaModel;
import resersa.salas.Service.ReservaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Reserva")
public class ReservaController {
    private ReservaService service;
    public ReservaController(ReservaService service){
        this.service = service;
    }
    @PostMapping("/criarreserva")
    public ResponseEntity<String> criarReserva(@RequestBody ReservaInputDTO dto) {
        ReservaModel model = service.criarReserva(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso");
    }
    @PutMapping("/atualizarreserva")
    public ResponseEntity<String> atualizarReserva(@RequestBody ReservaInputDTO dto) {
     try{
        ReservaModel model = service.atualizarReserva(dto);
        return ResponseEntity.status(HttpStatus.OK).body("Atualizado");
        } catch(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(""+e);
        }
    }
    @DeleteMapping("/deletarreserva/{id}")
    public ResponseEntity<String> deletarReserva(@PathVariable int id){
        service.deletarReserva(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleção concluida");
    }
    @GetMapping("/getreserva/{id}")
    public ResponseEntity<Optional<ReservaModel>> getReserva(@PathVariable Integer id){
        Optional<ReservaModel> model = service.getReserva(id);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @GetMapping("/getallreserva")
    public ResponseEntity<List<ReservaModel>> getReserva(){
        List model = new ArrayList(service.getAllReserva());
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @PostMapping("/inseriremlote")
    public ResponseEntity<String> inseriremlote(){
        service.inserirEmLot();
        return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso");
    }
}
