package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Telefone Value Object")
public class TelefoneTest {

    @Test
    @DisplayName("Deve criar celular válido (11 dígitos)")
    void deveCriarCelular() {
        Telefone telefone = new Telefone("11987654321");
        assertThat(telefone.valor()).isEqualTo("11987654321");
    }

    @Test
    @DisplayName("Deve criar telefone válido (10 dígitos)")
    void deveCriarTelefone() {
        Telefone telefone = new Telefone("1234567890");
        assertThat(telefone.valor()).isEqualTo("1234567890");
    }

    @Test
    @DisplayName("Deve remover caracteres não numéricos")
    void deveRemoverCaracteresNaoNumericos() {
        Telefone telefone = new Telefone("(11) 99988-7766");
        assertThat(telefone.valor()).isEqualTo("11999887766");
    }

    @Test
    @DisplayName("Deve formatar telefone corretamente")
    void deveFormatarTelefone() {
        Telefone telefone = new Telefone("11987654321");
        assertThat(telefone.formatar()).isEqualTo("(11) 98765-4321");
        telefone = new Telefone("1234567890");
        assertThat(telefone.formatar()).isEqualTo("(12) 3456-7890");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "11", "119", "111234567890"}) // Muito curto ou muito longo
    @DisplayName("Não deve criar telefone com tamanho inválido")
    void naoDeveCriarInvalido(String numeroInvalido) {
        assertThatThrownBy(() -> new Telefone(numeroInvalido))
                .isInstanceOf(DomainException.class);
    }
}
