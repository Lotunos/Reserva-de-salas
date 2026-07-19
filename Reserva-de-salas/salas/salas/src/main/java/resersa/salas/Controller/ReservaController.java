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
        String resposta = service.criarReserva(dto);
        return ResponseEntity.ok(resposta);
    }
    @PutMapping("/atualizarreserva")
    public ResponseEntity<String> atualizarReserva(@RequestBody ReservaInputDTO dto) {
        String resposta = service.atualizarReserva(dto);
        return ResponseEntity.ok(resposta);
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
    //TODO: Verificar o porque quando eu chamo o responseentity.status esta rota retorna 500 e 201 ao mesmo tempo
    @PostMapping("/inseriremlote")
    public ResponseEntity<Void> inseriremlote(){
        service.inserirEmLot();
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
