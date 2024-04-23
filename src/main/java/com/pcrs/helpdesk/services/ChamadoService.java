package com.pcrs.helpdesk.services;

import com.pcrs.helpdesk.domain.Chamado;
import com.pcrs.helpdesk.domain.Cliente;
import com.pcrs.helpdesk.domain.Tecnico;
import com.pcrs.helpdesk.domain.dtos.ChamadoDTO;
import com.pcrs.helpdesk.domain.enums.Prioridade;
import com.pcrs.helpdesk.domain.enums.Status;
import com.pcrs.helpdesk.repositories.ChamadoRepository;
import com.pcrs.helpdesk.resources.ChamadoResource;
import com.pcrs.helpdesk.services.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private TecnicoService tecnicoService;
    @Autowired
    private ClienteService clienteService;

    public Chamado findById(Integer id) {
        Optional<Chamado> obj = chamadoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id ));

    }

    public List<Chamado> findAll() {
        return chamadoRepository.findAll();
    }

    public Chamado create(ChamadoDTO objDTO) {
        return chamadoRepository.save(newChamado(objDTO));
    }

    private Chamado newChamado(ChamadoDTO objDTO) {
        Tecnico tecnico = tecnicoService.findById(objDTO.getTecnico());
        Cliente cliente = clienteService.findById(objDTO.getCliente());

        Chamado chamado = new Chamado();
        if (objDTO.getId() != null) {
            chamado.setId(objDTO.getId());
        }

        if(objDTO.getStatus().equals(2)) {
            chamado.setDataFechamento(LocalDate.now());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(objDTO.getPrioridade()));
        chamado.setStatus(Status.toEnum(objDTO.getStatus()));
        chamado.setTitulo(objDTO.getTitulo());
        chamado.setObservacoes(objDTO.getObservacoes());
        return chamado;
    }

    public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
        objDTO.setId(id);
        Chamado objOld = findById(id);
        objOld = newChamado(objDTO);
        return chamadoRepository.save(objOld);
    }
}
