package com.example.Market.Controller;

import com.example.Market.Dto.LigneCommandeDto;
import com.example.Market.Dto.UserDto;
import com.example.Market.Model.LigneCommande;
import com.example.Market.Services.LigneCommandeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ligneCommande")
public class LigneCommandeController {
    private  final LigneCommandeService ligneCommandeService ;

    public LigneCommandeController(LigneCommandeService ligneCommandeService) {
        this.ligneCommandeService = ligneCommandeService;
    }
    @GetMapping("/allLigneCommande")
    public ResponseEntity<List<LigneCommandeDto>> getAllLigneCommande(){
        List<LigneCommandeDto> ligneCommandeDtos=ligneCommandeService.findAllLigneCommande();
        return new ResponseEntity<>(ligneCommandeDtos, HttpStatus.OK);
    }

    @PostMapping("/saveLigneCommande")

    public  ResponseEntity<LigneCommandeDto> saveLigneCommande(@RequestBody LigneCommandeDto ligneCommandeDto, HttpServletResponse response){
        LigneCommandeDto savedLigneCommande =ligneCommandeService.saveLigneCommande(ligneCommandeDto)  ;
        return new ResponseEntity<>(savedLigneCommande,HttpStatus.CREATED );
    }
}





