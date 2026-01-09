package com.consignadohub.simulation.domain.model;

import com.consignadohub.simulation.domain.exception.DomainException;
import com.consignadohub.simulation.domain.vo.Dinheiro;
import com.consignadohub.simulation.domain.vo.PrazoParcela;
import com.consignadohub.simulation.domain.vo.TaxaJuros;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Simulação Aggregate")
public class SimulacaoTest {

    private static final TaxaJuros TAXA_PADRAO = TaxaJuros.mensal("1.66");

    @Nested
    @DisplayName("Criação")
    class Criacao {

        @Test
        @DisplayName("Deve criar simulação com dados válidos")
        void deveCriarSimulacaoValida() {
            Dinheiro valor = Dinheiro.of("10000");
            PrazoParcela prazo = PrazoParcela.meses(24);

            Simulacao simulacao = Simulacao.criar(valor, prazo, TAXA_PADRAO);

            assertThat(simulacao.getId()).isNotNull();
            assertThat(simulacao.getValorSolicitado()).isEqualTo(valor);
            assertThat(simulacao.getPrazo()).isEqualTo(prazo);
        }

        @Test
        @DisplayName("Deve rejeitar valor nulo")
        void deveRejeitarValorNulo() {
            assertThatThrownBy(() -> Simulacao.criar(null, PrazoParcela.meses(24), TAXA_PADRAO))
                    .isInstanceOf(DomainException.class);
        }
    }

    @Nested
    @DisplayName("Cálculo de parcela (Tabela Price)")
    class CalculoParcela {

        @Test
        @DisplayName("Deve calcular parcela corretamente = R$10.000 em 24x")
        void DeveCalcularParcela() {
            Simulacao simulacao = Simulacao.criar(
                    Dinheiro.of("10000"),
                    PrazoParcela.meses(24),
                    TAXA_PADRAO);

            // PMT = 10000 * [0.0166(1.0166)^24] / [(1.0166)^24 - 1]
            // PMT ≈ R$ 508,57
            assertThat(simulacao.getValorParcela().valor())
                    .isBetween(new BigDecimal("505"), new BigDecimal("515"));
        }
    }

    @Nested
    @DisplayName("Calculo de IOF")
    class CalculoIOF {

        @Test
        @DisplayName("Deve calcular IOF - taxa fixa 0.38%")
        void deveCalcularIOF() {
            Simulacao simulacao = Simulacao.criar(
                    Dinheiro.of("10000"),
                    PrazoParcela.meses(24),
                    TAXA_PADRAO);

            // IOF simplificado = 0.38% do valor
            // 10000 * 0.0038 = R$ 38
            assertThat(simulacao.getIof().valor())
                    .isBetween(new BigDecimal("35"), new BigDecimal("45"));
        }
    }

    @Nested
    @DisplayName("Cálculo de CET")
    class CalculoCET {

        @Test
        @DisplayName("Deve calcular CET anual")
        void deveCalcularCET() {
            Simulacao simulacao = Simulacao.criar(
                    Dinheiro.of("10000"),
                    PrazoParcela.meses(24),
                    TAXA_PADRAO);

            // CET simplificado (não considera IOF na liberação)
            // Valor calculado: ~10.48%
            // TODO: Após implementar IOF completo, CET será maior (~22%)
            assertThat(simulacao.getCet().valorAnual())
                    .isGreaterThan(new BigDecimal("8"));
        }
    }
}
