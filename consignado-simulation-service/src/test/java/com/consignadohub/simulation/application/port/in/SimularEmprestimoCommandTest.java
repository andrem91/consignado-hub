package com.consignadohub.simulation.application.port.in;

import com.consignadohub.simulation.application.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Simular Empréstimo Command")
public class SimularEmprestimoCommandTest {

    @Nested
    @DisplayName("Criação")
    class Criacao {

        @Test
        @DisplayName("Deve criar command com dados válidos")
        void deveCriarComDadosValidos() {
            SimularEmprestimoCommand command = new SimularEmprestimoCommand(
                    new BigDecimal("10000"),
                    24
            );

            assertThat(command.valorSolicitado()).isEqualByComparingTo("10000");
            assertThat(command.prazoMeses()).isEqualTo(24);
        }
    }

    @Nested
    @DisplayName("Validações de valor")
    class ValidacaoValor {

        @Test
        @DisplayName("Deve rejeitar valor nulo")
        void deveRejeitarValorNulo() {
            assertThatThrownBy(() -> new SimularEmprestimoCommand(null, 24))
                    .isInstanceOf(BusinessException.class);
        }

        @Test
        @DisplayName("Deve rejeitar valor zero")
        void deveRejeitarValorZero() {
            assertThatThrownBy(() -> new SimularEmprestimoCommand(BigDecimal.ZERO, 24))
                    .isInstanceOf(BusinessException.class);
        }

        @Test
        @DisplayName("Deve rejeitar valor negativo")
        void deveRejeitarValorNegativo() {
            assertThatThrownBy(() -> new SimularEmprestimoCommand(new BigDecimal("-1000"), 24))
                    .isInstanceOf(BusinessException.class);

        }
    }

    @Nested
    @DisplayName("Validações de prazo")
    class ValidacaoPrazo {
        @Test
        @DisplayName("Deve rejeitar prazo menor que 6")
        void deveRejeitarPrazoMenorQue6() {
            assertThatThrownBy(() ->
                    new SimularEmprestimoCommand(new BigDecimal("10000"), 5))
                    .isInstanceOf(BusinessException.class);
        }
        @Test
        @DisplayName("Deve rejeitar prazo maior que 84")
        void deveRejeitarPrazoMaiorQue84() {
            assertThatThrownBy(() ->
                    new SimularEmprestimoCommand(new BigDecimal("10000"), 85))
                    .isInstanceOf(BusinessException.class);
        }


        @Test
        @DisplayName("Deve aceitar prazo mínimo (6)")
        void deveAceitarPrazoMinimo() {
            SimularEmprestimoCommand command = new SimularEmprestimoCommand(
                    new BigDecimal("10000"), 6);
            assertThat(command.prazoMeses()).isEqualTo(6);
        }
        @Test
        @DisplayName("Deve aceitar prazo máximo (84)")
        void deveAceitarPrazoMaximo() {
            SimularEmprestimoCommand command = new SimularEmprestimoCommand(
                    new BigDecimal("10000"), 84);
            assertThat(command.prazoMeses()).isEqualTo(84);
        }
    }
}
