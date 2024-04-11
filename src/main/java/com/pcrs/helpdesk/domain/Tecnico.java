package com.pcrs.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;

public class Tecnico extends Pessoa {

    private List<Chamado> chamados = new ArrayList<>();

    public Tecnico() {
        super();
    }

    public Tecnico(Integer id, String nome, String email, String cpf) {
        super(id, nome, email, cpf);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

}
