package resersa.salas.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import resersa.salas.DTO.ReservaDTO;
import resersa.salas.Model.ReservaModel;
import resersa.salas.Service.ReservaService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Reserva")
public class ReservaController {
    private ReservaService service;
    public ReservaController(ReservaService service){
        this.service = service;
    }
    @PostMapping("/criarreserva")
    public ResponseEntity<String> criarReserva(@RequestBody ReservaDTO dto){
        ReservaModel model = service.criarReserva(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso");
    }
    /*@PutMapping("/atualizarreserva")
    public ResponseEntity<ReservaModel> atualizarReserva(@RequestBody ReservaDTO dto){
        ReservaModel model = service.atualizarReserva(dto);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @DeleteMapping("/deletarreserva")
    public ResponseEntity<String> deletarReserva(@RequestBody int id){
        service.deletarReserva(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleção concluida");
    }
    @GetMapping("/getreserva/{id}")
    public ResponseEntity<ReservaModel> getReserva(@PathVariable Integer id){
        ReservaModel model = service.getReserva(id);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @GetMapping("/getallreserva")
    public ResponseEntity<List<ReservaModel>> getReserva(){
        List model = new ArrayList(service.getAllReserva());
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }*/
}
