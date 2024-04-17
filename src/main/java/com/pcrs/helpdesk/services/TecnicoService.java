package com.pcrs.helpdesk.services;

import com.pcrs.helpdesk.domain.Tecnico;
import com.pcrs.helpdesk.domain.dtos.TecnicoDTO;
import com.pcrs.helpdesk.repositories.TecnicoRepository;
import com.pcrs.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;


    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = tecnicoRepository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Tecnico> findAll() {
        List<Tecnico> list = tecnicoRepository.findAll();
        return list;
    }

    public Tecnico save(TecnicoDTO objDTO) {
        objDTO.setId(null);
        Tecnico tecnico = new Tecnico(objDTO);
        return tecnicoRepository.save(tecnico);

    }
}
