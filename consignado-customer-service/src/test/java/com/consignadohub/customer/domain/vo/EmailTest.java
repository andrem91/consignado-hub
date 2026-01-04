package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Email Value Object")
class EmailTest {

    @Test
    @DisplayName("Deve criar email válido")
    void deveCriarEmailValido() {
        Email email = new Email("joao@exemplo.com");
        assertThat(email.valor()).isEqualTo("joao@exemplo.com");
    }

    @Test
    @DisplayName("Deve converter para minúsculas")
    void deveConverterParaMinusculas() {
        Email email = new Email("JOAO@EXEMPLO.COM");
        assertThat(email.valor()).isEqualTo("joao@exemplo.com");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "joao", "joao@", "@gmail.com", "joao@.com"})
    @DisplayName("Não deve criar email com formato inválido")
    void naoDeveCriarInvalido(String valorInvalido) {
        assertThatThrownBy(() -> new Email(valorInvalido))
                .isInstanceOf(InvalidEmailException.class);
    }

    @Test
    @DisplayName("Deve rejeitar email nulo ou vazio")
    void deveRejeitarNuloOuVazio() {
        assertThatThrownBy(() -> new Email(null))
                .isInstanceOf(InvalidEmailException.class);
        assertThatThrownBy(() -> new Email(""))
                .isInstanceOf(InvalidEmailException.class);
    }
}
