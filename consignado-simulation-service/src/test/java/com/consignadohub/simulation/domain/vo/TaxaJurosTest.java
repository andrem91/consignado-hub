package com.consignadohub.simulation.domain.vo;

import com.consignadohub.simulation.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("TaxaJuros Value Object")
class TaxaJurosTest {

    @Test
    @DisplayName("Deve criar taxa válida (1.66%)")
    void deveCriarTaxaValida() {
        TaxaJuros taxa = TaxaJuros.of(new BigDecimal("1.66"));
        assertThat(taxa.valorMensal()).isEqualByComparingTo("1.66");
    }

    @Test
    @DisplayName("Deve rejeitar taxa negativa")
    void deveRejeitarTaxaNegativa() {
        assertThatThrownBy(() -> TaxaJuros.of(new BigDecimal("-1")))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("Deve rejeitar taxa acima do limite (2.14%)")
    void deveRejeitarTaxaAcimaDoLimite() {
        assertThatThrownBy(() -> TaxaJuros.of(new BigDecimal("2.15")))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("Deve calcular taxa anual corretamente")
    void deveCalcularTaxaAnual() {
        TaxaJuros taxa = TaxaJuros.of(new BigDecimal("1.66"));
        // (1 + 0.0166)^12 - 1 = ~21.85%
        assertThat(taxa.valorAnual()).isBetween(new BigDecimal("21"), new BigDecimal("22"));
    }

    @Test
    @DisplayName("Deve converter para decimal")
    void deveConverterParaDecimal() {
        TaxaJuros taxa = TaxaJuros.of("1.66");
        assertThat(taxa.comoDecimal()).isEqualByComparingTo("0.0166");
    }
}
