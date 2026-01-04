package com.consignadohub.customer.domain.model;

import com.consignadohub.customer.domain.vo.Dinheiro;
import com.consignadohub.customer.domain.vo.NumeroBeneficio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Beneficio Entity")
class BeneficioTest {

    @Test
    @DisplayName("Deve criar benefício válido")
    void deveCriarBeneficioValido() {
        Beneficio beneficio = new Beneficio(
          new NumeroBeneficio("1234567890"),
          TipoBeneficio.APOSENTADORIA_POR_IDADE,
                Dinheiro.of("2000.00"),
                LocalDate.of(2020, 1, 15)
        );

        assertThat(beneficio.getId()).isNotNull();
        assertThat(beneficio.getNumero().valor()).isEqualTo("1234567890");
        assertThat(beneficio.getTipo()).isEqualTo(TipoBeneficio.APOSENTADORIA_POR_IDADE);
        assertThat(beneficio.getValorMensal().valor()).isEqualByComparingTo("2000.00");
        assertThat(beneficio.getDataInicio()).isEqualTo(LocalDate.of(2020, 1, 15));
    }

    @Test
    @DisplayName("Deve calcular margem de empréstimo (35%)")
    void deveCalcularMargemDeEmprestimo() {
        Beneficio beneficio = new Beneficio(
                new NumeroBeneficio("1234567890"),
                TipoBeneficio.APOSENTADORIA_POR_IDADE,
                Dinheiro.of("2000.00"),
                LocalDate.now()
        );

        Dinheiro margem  = beneficio.calcularMargemEmprestimo();
        assertThat(margem.valor()).isEqualByComparingTo("700.00");
    }

    @Test
    @DisplayName("Deve retornar zero para benefício não consignável")
    void deveRetornarZeroParaNaoConsignavel() {
        Beneficio beneficio = new Beneficio(
                new NumeroBeneficio("1234567890"),
                TipoBeneficio.BPC_LOAS,  // Não permite consignado
                Dinheiro.of("2000.00"),
                LocalDate.now()
        );

        assertThat(beneficio.calcularMargemEmprestimo())
                .isEqualTo(Dinheiro.ZERO);
    }

}
