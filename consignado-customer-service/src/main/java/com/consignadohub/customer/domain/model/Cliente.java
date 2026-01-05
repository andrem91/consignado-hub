package com.consignadohub.customer.domain.model;

import com.consignadohub.customer.domain.exception.InvalidClienteException;
import com.consignadohub.customer.domain.vo.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Cliente {

    private final ClienteId id;
    private final CPF cpf;
    private String nome;
    private final DataNascimento dataNascimento;
    private final Email email;
    private final Telefone telefone;
    private final List<Beneficio> beneficios = new ArrayList<>();

    public Cliente(CPF cpf, String nome, DataNascimento dataNascimento, Email email, Telefone telefone) {
        if (cpf == null) throw new InvalidClienteException("CPF é obrigatório");
        if (nome == null || nome.isBlank()) throw new InvalidClienteException("Nome é obrigatório");
        if (dataNascimento == null) throw new InvalidClienteException("Data de nascimento é obrigatória");

        this.id = ClienteId.novo();
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
    }

    public void adicionarBeneficio(Beneficio beneficio) {
        if (beneficio == null) throw new InvalidClienteException("Benefício não pode ser nulo");
        this.beneficios.add(beneficio);
    }

    public Dinheiro calcularMargemTotalDisponivel() {
        return beneficios.stream()
                .map(Beneficio::calcularMargemEmprestimo)
                .reduce(Dinheiro.ZERO, Dinheiro::somar);
    }
    
    public List<Beneficio> getBeneficios() {
        return Collections.unmodifiableList(beneficios);
    }

}
