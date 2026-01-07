package com.consignadohub.customer.domain.model;

import com.consignadohub.customer.domain.exception.DomainException;
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
        if (cpf == null) throw DomainException.required("CPF");
        if (nome == null || nome.isBlank()) throw DomainException.required("Nome");
        if (dataNascimento == null) throw DomainException.required("DataNascimento");

        this.id = ClienteId.novo();
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
    }

    private Cliente(ClienteId id, CPF cpf, String nome, DataNascimento dataNascimento,
                    Email email, Telefone telefone) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
    }

    public void adicionarBeneficio(Beneficio beneficio) {
        if (beneficio == null) throw DomainException.required("Benefício");
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

    public static Cliente reconstituir(ClienteId id, CPF cpf, String nome, DataNascimento dataNascimento, Email email, Telefone telefone) {
        return new Cliente(id, cpf, nome, dataNascimento, email, telefone);
    }

}
