package com.example.Market.Controller;

import com.example.Market.Dto.PartenaireDto;
import com.example.Market.Dto.ProductDto;
import com.example.Market.Model.Partenaire;
import com.example.Market.Services.PartenaireService;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/partenaire")
public class PartenaireController {
    private final PartenaireService partenaireService ;
    private final ModelMapper modelMapper;

    public PartenaireController(PartenaireService partenaireService, ModelMapper modelMapper) {
        this.partenaireService = partenaireService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<PartenaireDto> FindPartenaire(@PathVariable("id") Long id){
        Optional<PartenaireDto> partenaire=partenaireService.findPartenaireById(id);
        if (partenaire.isPresent()) {
            return new ResponseEntity<>(partenaire.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/add")
    public  ResponseEntity<PartenaireDto> addProduct(@RequestBody PartenaireDto partenaireDto, HttpServletResponse response){
        PartenaireDto newProduct =partenaireService.addPartenaire(partenaireDto)  ;
        return new ResponseEntity<>(newProduct,HttpStatus.CREATED );
    }
    @PutMapping("/Update")
    public ResponseEntity<PartenaireDto> UpdatePartenaire (PartenaireDto partenaireDto){

        PartenaireDto partenaire=partenaireService.UpdatePartenaire(partenaireDto);
        return new ResponseEntity<>(partenaire,HttpStatus.OK);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteUser(@PathVariable("id") Long id){
        partenaireService.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
