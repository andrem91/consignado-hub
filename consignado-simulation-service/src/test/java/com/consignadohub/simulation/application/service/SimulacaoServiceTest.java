package com.consignadohub.simulation.application.service;

import com.consignadohub.simulation.application.port.in.SimularEmprestimoCommand;
import com.consignadohub.simulation.application.port.out.SimulacaoCache;
import com.consignadohub.simulation.domain.model.Simulacao;
import com.consignadohub.simulation.domain.vo.Dinheiro;
import com.consignadohub.simulation.domain.vo.PrazoParcela;
import com.consignadohub.simulation.domain.vo.TaxaJuros;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Simulação Service")
public class SimulacaoServiceTest {

    @Mock
    private SimulacaoCache cache;
    private SimulacaoService service;

    private static final TaxaJuros TAXA_PADRAO = TaxaJuros.mensal("1.66");

    @BeforeEach
    void setUp() {
        service = new SimulacaoService(cache, TAXA_PADRAO);
    }

    @Nested
    @DisplayName("Simulação nova")
    class SimulacaoNova {

        @Test
        @DisplayName("Deve criar simulação quando não existe no cache")
        void deveCriarSimulacaoQuandoNaoExisteNoCache() {
            var command = new SimularEmprestimoCommand(new BigDecimal("10000"), 24);
            when(cache.buscarPorAssinatura(anyString())).thenReturn(Optional.empty());

            Simulacao resultado = service.executar(command);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getValorSolicitado().valor()).isEqualByComparingTo("10000");
            verify(cache).salvar(any(Simulacao.class), anyString());
        }
    }

    @Nested
    @DisplayName("Cache hit")
    class cacheHit {

        @Test
        @DisplayName("Deve retornar do cache quando simulação já existe")
        void deveRetornarDoCacheQuandoSimulacaoJaExiste() {
            var command = new SimularEmprestimoCommand(new BigDecimal("10000"), 24);
            var simulacaoCacheada = criarSimulacaoMock();
            when(cache.buscarPorAssinatura(anyString())).thenReturn(Optional.of(simulacaoCacheada));

            Simulacao resultado = service.executar(command);

            assertThat(resultado).isEqualTo(simulacaoCacheada);
            verify(cache, never()).salvar(any(), anyString());

        }
    }

    private Simulacao criarSimulacaoMock() {
        return Simulacao.criar(
                Dinheiro.of("10000"),
                PrazoParcela.meses(24),
                TAXA_PADRAO
        );
    }

}
