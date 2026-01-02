package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidCPFException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("CPF Value Object")
public class CPFTest {

    @Test
    @DisplayName("Deve criar CPF válido")
    void deveCriarCPFValido() {
        CPF cpf = new CPF("16945511057");
        assertThat(cpf.valor()).isEqualTo("16945511057");
    }

    @Test
    @DisplayName("Deve remover formatação")
    void deveRemoverFormatacao() {
        CPF cpf = new CPF("169.455.110-57");
        assertThat(cpf.valor()).isEqualTo("16945511057");
    }

    @Test
    @DisplayName("Deve rejeitar CPF nulo ou vazio")
    void deveRejeitarCPFNuloOuVazio() {
    assertThatThrownBy(() -> new CPF(null))
            .isInstanceOf(InvalidCPFException.class);
    assertThatThrownBy(() -> new CPF(""))
            .isInstanceOf(InvalidCPFException.class);
    }

    @Test
    @DisplayName("Deve formatar CPF para exibição")
    void deveFormatar() {
        CPF cpf = new CPF("16945511057");
        assertThat(cpf.formatar()).isEqualTo("169.455.110-57");
    }
    @Test
    @DisplayName("Deve mascarar CPF para segurança")
    void deveMascarar() {
        CPF cpf = new CPF("16945511057");
        assertThat(cpf.mascarar()).isEqualTo("169.***.***-57");
    }
    @Test
    @DisplayName("Deve rejeitar CPF com dígitos repetidos")
    void deveRejeitarDigitosRepetidos() {
        assertThatThrownBy(() -> new CPF("11111111111"))
                .isInstanceOf(InvalidCPFException.class);
    }
    @Test
    @DisplayName("Deve rejeitar CPF com dígito verificador inválido")
    void deveRejeitarDigitoInvalido() {
        assertThatThrownBy(() -> new CPF("12345678901"))
                .isInstanceOf(InvalidCPFException.class);
    }

}
