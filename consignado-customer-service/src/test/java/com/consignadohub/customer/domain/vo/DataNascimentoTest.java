package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidDataNascimentoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("DataNascimento Value Object")
class DataNascimentoTest {

    @Test
    @DisplayName("Deve criar data de nascimento válida")
    void deveCriarDataValida() {
        DataNascimento dataNascimento = new DataNascimento(LocalDate.of(1980, 5, 15));
        assertThat(dataNascimento.valor()).isEqualTo(LocalDate.of(1980, 5, 15));
    }

    @Test
    @DisplayName("Deve calcular idade corretamente")
    void deveCalcularIdade() {
        LocalDate hoje = LocalDate.now();
        DataNascimento data = new DataNascimento(hoje.minusYears(65));
        assertThat(data.idade()).isEqualTo(65);
    }

    @Test
    @DisplayName("Deve rejeitar data nula")
    void deveRejeitarDataNula() {
        assertThatThrownBy(() -> new DataNascimento(null))
                .isInstanceOf(InvalidDataNascimentoException.class);
    }

    @Test
    @DisplayName("Deve rejeitar data futura")
    void deveRejeitarDataFutura() {
        assertThatThrownBy(() -> new DataNascimento(LocalDate.now().plusDays(1)))
                .isInstanceOf(InvalidDataNascimentoException.class);
    }

    @Test
    @DisplayName("Deve rejeitar idade menor que 18 anos")
    void deveRejeitarIdadeMenorQue18() {
        assertThatThrownBy(() -> new DataNascimento(LocalDate.now().minusYears(17)))
                .isInstanceOf(InvalidDataNascimentoException.class);
    }
}
