package com.example.Market.Controller;

import com.example.Market.Dto.CommandeDto;
import com.example.Market.Model.ProductCmd;
import com.example.Market.Services.CommandeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commande")
public class CommandeController {
    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }
    @GetMapping("/allCommande")
    public ResponseEntity<List<CommandeDto>> getAllCommande(){
        List<CommandeDto> commandesDto= commandeService.findAllCommande();
        return new ResponseEntity<>(commandesDto, HttpStatus.OK);
    }
    // To see ***
    @PostMapping("/addCommandeByUser/{UserId}")
    public ResponseEntity<CommandeDto> adddCommande(@RequestBody List<ProductCmd> productCmds, @PathVariable("UserId") Long UserId) {
        CommandeDto newCommande = commandeService.adddCommande(productCmds, UserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCommande);
    }

    @PutMapping("/update")
    public ResponseEntity<CommandeDto> UpdateCommande(@RequestBody CommandeDto commandeDto , HttpServletResponse response){
        CommandeDto updateCommade=commandeService.UpdateCommande(commandeDto);
        return new ResponseEntity<>(updateCommade, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteCommande(@PathVariable("id") Long id){
        commandeService.DeleteCommandeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/isCommandeValid/{id}")
    public ResponseEntity<Boolean> isCommandeValid(@PathVariable("id") Long id){
        boolean isCommandeValid = commandeService.isCommandeValid(id);
        return new ResponseEntity<Boolean>(isCommandeValid, HttpStatus.OK);
    }
}
