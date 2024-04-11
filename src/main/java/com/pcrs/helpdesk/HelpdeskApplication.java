package com.pcrs.helpdesk;

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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;


    @Override
    public void run(String... args) throws Exception {
        Tecnico t1 = new Tecnico(null, "Paulo", "paulo@tre-rr.jus.br", "67794882591", "12345678");
        t1.addPerfil(Perfil.ADMIN);

        Cliente cl1 = new Cliente(null, "Linux Torvalds", "linux@torvalds.com", "123456789", "12345678");

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", cl1, t1);

        tecnicoRepository.saveAll(Arrays.asList(t1));
        clienteRepository.saveAll(Arrays.asList(cl1));
        chamadoRepository.saveAll(Arrays.asList(c1));


    }

    public static void main(String[] args) {
        SpringApplication.run(HelpdeskApplication.class, args);
    }

}
