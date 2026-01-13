package com.consignadohub.customer.domain.model;

import com.consignadohub.customer.domain.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Cliente Aggregate Root")
class ClienteTest {

    @Test
    @DisplayName("Deve criar cliente válido")
    void deveCriarClienteValido() {
        Cliente cliente = new Cliente(
                new CPF("52998224725"),
                "João da Silva",
                new DataNascimento(LocalDate.of(1980, 5, 15)),
                new Email("joao@email.com"),
                new Telefone("11999887766"));

        assertThat(cliente.getId()).isNotNull();
        assertThat(cliente.getCpf().valor()).isEqualTo("52998224725");
        assertThat(cliente.getNome()).isEqualTo("João da Silva");
        assertThat(cliente.getDataNascimento().valor()).isEqualTo(LocalDate.of(1980, 5, 15));
        assertThat(cliente.getEmail().valor()).isEqualTo("joao@email.com");
        assertThat(cliente.getTelefone().valor()).isEqualTo("11999887766");
    }

    @Test
    @DisplayName("Deve adicionar benefício ao cliente")
    void deveAdicionarBeneficioAoCliente() {
        Cliente cliente = criarClienteValido();

        Beneficio beneficio = new Beneficio(
                new NumeroBeneficio("1234567890"),
                TipoBeneficio.APOSENTADORIA_POR_IDADE,
                Dinheiro.of("2000.00"),
                LocalDate.of(2020, 1, 15));

        cliente.adicionarBeneficio(beneficio);

        assertThat(cliente.getBeneficios()).hasSize(1);
    }

    @Test
    @DisplayName("Deve calcular margem total disponível")
    void deveCalcularMargemTotalDisponivel() {
        Cliente cliente = criarClienteValido();
        cliente.adicionarBeneficio(criarBeneficio(Dinheiro.of("2000.00")));
        cliente.adicionarBeneficio(criarBeneficio(Dinheiro.of("1000.00")));

        Dinheiro margemTotal = cliente.calcularMargemTotalDisponivel();
        assertThat(margemTotal.valor()).isEqualByComparingTo("1050.00");
    }

    private Cliente criarClienteValido() {
        return new Cliente(
                new CPF("52998224725"),
                "João da Silva",
                new DataNascimento(LocalDate.of(1980, 5, 15)),
                new Email("joao@email.com"),
                new Telefone("11999887766"));
    }

    private Beneficio criarBeneficio(Dinheiro valor) {
        return new Beneficio(
                new NumeroBeneficio("1234567890"),
                TipoBeneficio.APOSENTADORIA_POR_IDADE,
                Dinheiro.of(valor.valor()),
                LocalDate.of(2020, 1, 15));
    }

}
