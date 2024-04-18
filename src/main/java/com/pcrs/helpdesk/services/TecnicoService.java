package com.pcrs.helpdesk.services;

import com.pcrs.helpdesk.domain.Pessoa;
import com.pcrs.helpdesk.domain.Tecnico;
import com.pcrs.helpdesk.domain.dtos.TecnicoDTO;
import com.pcrs.helpdesk.repositories.PessoaRepository;
import com.pcrs.helpdesk.repositories.TecnicoRepository;
import com.pcrs.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.pcrs.helpdesk.services.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

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
        validaPorCpfEEmail(objDTO);
        Tecnico tecnico = new Tecnico(objDTO);
        return tecnicoRepository.save(tecnico);
    }

    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("E-Mail já cadastrado no sistema!");
        }
    }


    public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
        objDTO.setId(id);
        Tecnico oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Tecnico(objDTO);
        return tecnicoRepository.save(oldObj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if(obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("O Técnico possui ordens de serviço e não pode ser deletado!");
        }
        tecnicoRepository.delete(obj);

    }
}
