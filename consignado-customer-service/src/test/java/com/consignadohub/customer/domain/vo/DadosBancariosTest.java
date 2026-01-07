package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Dados Bancarios Value Object")
public class DadosBancariosTest {

    @Test
    @DisplayName("Deve criar dados bancários válidos")
    void deveCriarDadosBancariosValidos() {
        DadosBancarios dados = new DadosBancarios(
          "341", "1234", "12345", "6", TipoConta.CORRENTE
        );
        assertThat(dados.banco()).isEqualTo("341");
        assertThat(dados.contaCompleta()).isEqualTo("12345-6");
    }

    @Test
    @DisplayName("Deve rejeitar banco nulo")
    void deveRejeitarBancoNulo() {
        assertThatThrownBy(() -> new DadosBancarios(null, "1234", "12345", "6", TipoConta.CORRENTE))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("Deve rejeitar banco com formato inválido")
    void deveRejeitarBancoComFormatoInvalido() {
        assertThatThrownBy(() -> new DadosBancarios("12345", "1234", "12345", "6", TipoConta.CORRENTE))
                .isInstanceOf(DomainException.class);
    }


}
