package com.pcrs.helpdesk.services;

import com.pcrs.helpdesk.domain.Pessoa;
import com.pcrs.helpdesk.domain.Cliente;
import com.pcrs.helpdesk.domain.dtos.ClienteDTO;
import com.pcrs.helpdesk.repositories.ClienteRepository;
import com.pcrs.helpdesk.repositories.PessoaRepository;
import com.pcrs.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.pcrs.helpdesk.services.exceptions.ObjectNotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Cliente> findAll() {
        List<Cliente> list = clienteRepository.findAll();
        return list;
    }

    public Cliente save(ClienteDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Cliente Cliente = new Cliente(objDTO);
        return clienteRepository.save(Cliente);
    }

    private void validaPorCpfEEmail(ClienteDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("E-Mail já cadastrado no sistema!");
        }
    }


    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return clienteRepository.save(oldObj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if(obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("O Cliente possui ordens de serviço e não pode ser deletado!");
        }
        clienteRepository.delete(obj);

    }
}
