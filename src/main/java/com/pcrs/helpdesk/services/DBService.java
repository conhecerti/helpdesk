package com.pcrs.helpdesk.services;

import com.pcrs.helpdesk.domain.Chamado;
import com.pcrs.helpdesk.domain.Cliente;
import com.pcrs.helpdesk.domain.Tecnico;
import com.pcrs.helpdesk.domain.enums.Perfil;
import com.pcrs.helpdesk.domain.enums.Prioridade;
import com.pcrs.helpdesk.domain.enums.Status;
import com.pcrs.helpdesk.repositories.ChamadoRepository;
import com.pcrs.helpdesk.repositories.ClienteRepository;
import com.pcrs.helpdesk.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;


    public void instanciaDB() {

        Tecnico t1 = new Tecnico(null, "Paulo", "paulo@tre-rr.jus.br", "67794882591", "12345678");
        t1.addPerfil(Perfil.ADMIN);

        Tecnico t2 = new Tecnico(null, "Rafael", "rafael@tre-rr.jus.br", "301.168.430-80", "31313213");
        t2.addPerfil(Perfil.TECNICO);

        Tecnico t3 = new Tecnico(null, "Juliano", "conhecer.ti@tre-rr.jus.br", "685.103.800-58", "31313213");
        t2.addPerfil(Perfil.TECNICO);

        Cliente cl1 = new Cliente(null, "Linux Torvalds", "linux@torvalds.com", "176.221.150-53", "12345678");
        Cliente cl2 = new Cliente(null, "Santos", "stic@tre-rr.jus.br", "586.077.060-04", "25452");

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", cl1, t1);
        Chamado c2 = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "Chamado 02", "Segundo chamado", cl2, t2);


        tecnicoRepository.saveAll(Arrays.asList(t1,t2,t3));
        clienteRepository.saveAll(Arrays.asList(cl1, cl2));
        chamadoRepository.saveAll(Arrays.asList(c1,c2));

    }
}
