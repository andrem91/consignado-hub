package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidDinheiroException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Dinheiro Value Object")
public class DinheiroTest {

    @Test
    @DisplayName("Deve criar Dinheiro com valor positivo")
    void deveCriarDinheiroComValorPositivo() {
        Dinheiro dinheiro = Dinheiro.of(new BigDecimal("1000.50"));
        assertThat(dinheiro.valor()).isEqualByComparingTo("1000.50");
    }

    @Test
    @DisplayName("Deve arredondar para 2 casas decimais")
    void deveArredondar() {
        Dinheiro dinheiro = Dinheiro.of(new BigDecimal("1000.4999"));
        assertThat(dinheiro.valor()).isEqualByComparingTo("1000.50");

    }

    @Test
    @DisplayName("Não deve permitir valor negativo")
    void naoDevePermitirValorNegativo() {
        assertThatThrownBy(() -> Dinheiro.of(new BigDecimal("-100")))
                .isInstanceOf(InvalidDinheiroException.class);
    }

    @Test
    @DisplayName("Deve somar valores mantendo imutabilidade")
    void deveSomar() {
        Dinheiro a = Dinheiro.of("100");
        Dinheiro b = Dinheiro.of("50.50");

        assertThat(a.somar(b)).isEqualTo(Dinheiro.of("150.50"));
        assertThat(a.valor()).isEqualByComparingTo("100");
    }

    @Test
    @DisplayName("Deve garantir escala 2 mesmo recebendo valor com 1 casa")
    void deveGarantirEscala() {
        Dinheiro dinheiro = Dinheiro.of(new BigDecimal("1000.5"));
        assertThat(dinheiro.valor()).isEqualTo(new BigDecimal("1000.50"));
        assertThat(dinheiro.valor().scale()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve formatar para Real Brasileiro")
    void deveFormatar() {
        Dinheiro dinheiro = Dinheiro.of("1500.50");
        // O caractere de espaço no Java pode variar (non-breaking space),
        // então verificamos se contém as partes essenciais
        assertThat(dinheiro.formatado())
                .contains("R$")
                .contains("1.500,50");
    }

    @Test
    @DisplayName("Deve subtrair valores")
    void deveSubtrair() {
        Dinheiro a = Dinheiro.of("100");
        Dinheiro b = Dinheiro.of("30");
        assertThat(a.subtrair(b)).isEqualTo(Dinheiro.of("70"));
    }
    @Test
    @DisplayName("Deve multiplicar por quantidade")
    void deveMultiplicar() {
        Dinheiro parcela = Dinheiro.of("500");
        assertThat(parcela.multiplicar(12)).isEqualTo(Dinheiro.of("6000"));
    }
    @Test
    @DisplayName("Deve comparar valores")
    void deveComparar() {
        Dinheiro maior = Dinheiro.of("1000");
        Dinheiro menor = Dinheiro.of("500");
        assertThat(maior.maiorQue(menor)).isTrue();
        assertThat(menor.menorOuIgual(maior)).isTrue();
    }

}
