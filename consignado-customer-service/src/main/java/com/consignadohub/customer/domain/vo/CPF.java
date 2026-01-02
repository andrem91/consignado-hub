package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidCPFException;

public record CPF(String valor) {
    public CPF {

        if (valor == null || valor.isBlank()) {
            throw new InvalidCPFException("CPF não pode ser nulo ou vazio");
        }
        valor = valor.replaceAll("[^0-9]", "");

        if (valor.length() != 11) {
            throw new InvalidCPFException("CPF deve conter 11 dígitos");
        }

        if (todosDigitosIguais(valor)) {
            throw new InvalidCPFException("CPF com dígitos repetidos é inválido");
        }
        if (!digitosVerificadoresValidos(valor)) {
            throw new InvalidCPFException("CPF inválido");
        }
    }

    public String formatar() {
        return valor.substring(0, 3) + "." + valor.substring(3, 6) + "." + valor.substring(6, 9) + "-" + valor.substring(9, 11);
    }

    public String mascarar() {
        return valor.substring(0, 3) + ".***.***-" + valor.substring(9, 11);
    }

    @Override
    public String toString() {
        return "CPF[" + mascarar() + "]";
    }

    private static boolean todosDigitosIguais(String cpf) {
        char first = cpf.charAt(0);
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != first) return false;
        }
        return true;
    }

    public String valor() {
        return valor;
    }

    private static boolean digitosVerificadoresValidos(String cpf) {
        int sum = 0;
        int weight = 10;
        for (int i = 0; i < 9; i++) {
            int num = cpf.charAt(i) - '0';
            sum += num * weight--;
        }

        int r = 11 - (sum % 11);
        int digit1 = (r == 10 || r == 11) ? 0 : r;

        if (digit1 != (cpf.charAt(9) - '0')) return false;

        sum = 0;
        weight = 11;
        for (int i = 0; i < 10; i++) {
            int num = cpf.charAt(i) - '0';
            sum += num * weight--;
        }

        r = 11 - (sum % 11);
        int digit2 = (r == 10 || r == 11) ? 0 : r;

        return digit2 == (cpf.charAt(10) - '0');
    }

}
