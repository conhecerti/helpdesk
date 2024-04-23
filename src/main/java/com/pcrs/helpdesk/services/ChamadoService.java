package com.pcrs.helpdesk.services;

import com.pcrs.helpdesk.domain.Chamado;
import com.pcrs.helpdesk.repositories.ChamadoRepository;
import com.pcrs.helpdesk.resources.ChamadoResource;
import com.pcrs.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    public Chamado findById(Integer id) {
        Optional<Chamado> obj = chamadoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id ));

    }

    public List<Chamado> findAll() {
        return chamadoRepository.findAll();
    }
}
