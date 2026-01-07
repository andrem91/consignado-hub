package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("TaxaJuros Value Object")
public class TaxaJurosTest {

    @Test
    @DisplayName("Deve criar taxa de juros válida")
    void deveCriarTaxaJurosValida() {
        TaxaJuros taxa = TaxaJuros.of(new BigDecimal("1.99"));
        assertThat(taxa.valorMensal()).isEqualByComparingTo("1.99");
    }

    @Test
    @DisplayName("Deve converter taxa mensal para anual")
    void deveConverterMensalParaAnual() {
        TaxaJuros taxa = TaxaJuros.of(new BigDecimal("2.00"));
        assertThat(taxa.valorAnual()).isEqualByComparingTo("26.82");
    }

    @Test
    @DisplayName("Não deve permitir taxa negativa")
    void naoDevePermitirTaxaNegativa() {
        assertThatThrownBy(() -> TaxaJuros.of(new BigDecimal("-1.00")))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("Não deve permitir taxa acima do limite legal")
    void naoDevePermitirTaxaAcimaDoLimite() {
        assertThatThrownBy(() -> TaxaJuros.of(new BigDecimal("5.00")))
                .isInstanceOf(DomainException.class);
    }
}
