package resersa.salas.Controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import resersa.salas.DTO.ReservaInputDTO;
import resersa.salas.DTO.ReservaOutputDTO;
import resersa.salas.Service.ReservaService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Reserva")
public class ReservaController {
    private final ReservaService service;
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
        String remover = service.deletarReserva(id);
        return ResponseEntity.status(HttpStatus.OK).body(remover);
    }
    @GetMapping("/getreserva/{id}")
    public ResponseEntity<ReservaOutputDTO> getReserva(@PathVariable Integer id){
        ReservaOutputDTO dto = service.getReserva(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @GetMapping("/getallreserva")
    public ResponseEntity<List<ReservaOutputDTO>> getAllReserva(){
        List<ReservaOutputDTO> dto = service.getAllReserva();
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @GetMapping("/getallreservadataidsala")
    public ResponseEntity<List<ReservaOutputDTO>> getAllReservaDataIdSala(){
        List<ReservaOutputDTO> dto = service.getAllReservaDataIdSala();
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @GetMapping("/getallreservadata/{data}")
    public ResponseEntity<List<ReservaOutputDTO>> getAllReservaData(@PathVariable String data){
        List<ReservaOutputDTO> dto = service.getAllReservaByData(data);
        return  ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @GetMapping("/getAllReservaPaginacao")
    public ResponseEntity<Page<ReservaOutputDTO>> getAllReservaPaginacao(@RequestParam(defaultValue = "0")  int pagina,
                                                                         @RequestParam(defaultValue = "10")  int tamanho){
        Page<ReservaOutputDTO> dto = service.getAllReservaPaginacao(pagina, tamanho);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    //TODO: Verificar o porque quando eu chamo o responseentity.status esta rota retorna 500 e 201 ao mesmo tempo
    //TODO: Lembrar de apagar esta rota
    @PostMapping("/inseriremlote")
    public ResponseEntity<String> inseriremlote(){
        String servico = service.inserirEmLote();
        return new ResponseEntity<>(servico,HttpStatus.CREATED);
    }
}
