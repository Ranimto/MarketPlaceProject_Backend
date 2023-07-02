package com.example.Market.Services;

import com.example.Market.Dto.PartenaireDto;
import com.example.Market.Model.Partenaire;
import com.example.Market.Repository.PartenaireRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Service
public class PartenaireService {
    private  final PartenaireRepo partenaireRepo ;
    private  final ModelMapper modelMapper ;

    public PartenaireDto addPartenaire(PartenaireDto partenaireDto){
        Partenaire partenaire = modelMapper.map(partenaireDto, Partenaire.class);
        Partenaire savedPartenaire = partenaireRepo.save(partenaire);
        return modelMapper.map(savedPartenaire, PartenaireDto.class);
        }

    public Optional<PartenaireDto> findPartenaireById(Long id) {
        if (id==0 ){log.error("l'id est null");}
        Optional<Partenaire> partenaire = partenaireRepo.findById(id);
        return partenaire.map(u -> modelMapper.map(u, PartenaireDto.class));
    }

    public  PartenaireDto UpdatePartenaire(PartenaireDto partenaireDto){
        Partenaire partenaire= modelMapper.map(partenaireDto,Partenaire.class);
        partenaire.setName(partenaire.getName());
        partenaire.setManager(partenaire.getManager());
        partenaire.setAddress(partenaire.getAddress());
        return  modelMapper.map(partenaire,PartenaireDto.class);

    }
    public void DeleteById(long id){
        if (id==0 ){log.error("l'id est null");}
        partenaireRepo.deleteById(id);
    }

    }


