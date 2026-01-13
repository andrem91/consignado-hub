package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("NumeroBeneficio Value Object")
class NumeroBeneficioTest {

    @Test
    @DisplayName("Deve criar número de benefício válido")
    void deveCriarNumeroValido() {
        NumeroBeneficio numero = new NumeroBeneficio("1234567890");
        assertThat(numero.valor()).isEqualTo("1234567890");
    }

    @Test
    @DisplayName("Deve rejeitar número de benefício nulo ou vazio")
    void deveRejeitarNuloOuVazio() {
        assertThatThrownBy(() -> new NumeroBeneficio(null))
                .isInstanceOf(DomainException.class);
        assertThatThrownBy(() -> new NumeroBeneficio(""))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("Deve remover formatação")
    void deveRemoverFormatacao() {
        NumeroBeneficio numero = new NumeroBeneficio("123.456.789-0");
        assertThat(numero.valor()).isEqualTo("1234567890");
    }

    @Test
    @DisplayName("Deve rejeitar número com tamanho inválido")
    void deveRejeitarTamanhoInvalido() {
        assertThatThrownBy(() -> new NumeroBeneficio("123456789"))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("Deve formatar número de benefício")
    void deveFormatar() {
        NumeroBeneficio numero = new NumeroBeneficio("1234567890");
        assertThat(numero.formatar()).isEqualTo("123.456.789-0");
    }

}
