package com.consignadohub.simulation.domain.service;

import com.consignadohub.simulation.domain.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CalculadoraFinanceira Domain Service")
class CalculadoraFinanceiraTest {

    private static final TaxaJuros TAXA_PADRAO = TaxaJuros.mensal("1.66");

    @Nested
    @DisplayName("Cálculo de Parcela (Tabela Price)")
    class CalculoParcelaPrice {

        @Test
        @DisplayName("Deve calcular parcela de R$10.000 em 24x")
        void deveCalcularParcela10k24x() {
            Dinheiro valorParcela = CalculadoraFinanceira.calcularParcelaPrice(
                    Dinheiro.of("10000"),
                    PrazoParcela.meses(24),
                    TAXA_PADRAO);

            // PMT ≈ R$ 508.57
            assertThat(valorParcela.valor())
                    .isBetween(new BigDecimal("505"), new BigDecimal("515"));
        }

        @Test
        @DisplayName("Deve calcular parcela de R$5.000 em 12x")
        void deveCalcularParcela5k12x() {
            Dinheiro valorParcela = CalculadoraFinanceira.calcularParcelaPrice(
                    Dinheiro.of("5000"),
                    PrazoParcela.meses(12),
                    TAXA_PADRAO);

            // Parcela deve ser proporcional
            assertThat(valorParcela.valor())
                    .isGreaterThan(new BigDecimal("400"));
        }
    }

    @Nested
    @DisplayName("Cálculo de IOF")
    class CalculoIOF {

        @Test
        @DisplayName("Deve calcular IOF de 0.38% sobre R$10.000")
        void deveCalcularIOF10k() {
            Dinheiro iof = CalculadoraFinanceira.calcularIOF(Dinheiro.of("10000"));

            // 10000 * 0.0038 = 38
            assertThat(iof.valor()).isEqualByComparingTo("38.00");
        }

        @Test
        @DisplayName("Deve calcular IOF de 0.38% sobre R$25.000")
        void deveCalcularIOF25k() {
            Dinheiro iof = CalculadoraFinanceira.calcularIOF(Dinheiro.of("25000"));

            // 25000 * 0.0038 = 95
            assertThat(iof.valor()).isEqualByComparingTo("95.00");
        }
    }

    @Nested
    @DisplayName("Cálculo de CET")
    class CalculoCET {

        @Test
        @DisplayName("Deve calcular CET anualizado")
        void deveCalcularCET() {
            Dinheiro valorSolicitado = Dinheiro.of("10000");
            Dinheiro valorTotal = Dinheiro.of("12205.68"); // 24 x 508.57
            PrazoParcela prazo = PrazoParcela.meses(24);

            CET cet = CalculadoraFinanceira.calcularCET(valorSolicitado, valorTotal, prazo);

            // CET deve ser maior que 8% (valor simplificado)
            assertThat(cet.valorAnual()).isGreaterThan(new BigDecimal("8"));
        }
    }
}
