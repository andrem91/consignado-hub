package com.consignadohub.simulation.application.service;

import com.consignadohub.simulation.application.port.in.SimularEmprestimoCommand;
import com.consignadohub.simulation.application.port.in.SimularEmprestimoUseCase;
import com.consignadohub.simulation.application.port.out.SimulacaoCache;
import com.consignadohub.simulation.domain.model.Simulacao;
import com.consignadohub.simulation.domain.vo.Dinheiro;
import com.consignadohub.simulation.domain.vo.PrazoParcela;
import com.consignadohub.simulation.domain.vo.TaxaJuros;
import org.springframework.stereotype.Service;

@Service
public class SimulacaoService implements SimularEmprestimoUseCase {

    private final SimulacaoCache cache;
    private final TaxaJuros taxaPadrao;

    public SimulacaoService(SimulacaoCache cache, TaxaJuros taxaPadrao) {
        this.cache = cache;
        this.taxaPadrao = taxaPadrao;
    }

    @Override
    public Simulacao executar(SimularEmprestimoCommand command) {
        String assinatura = gerarAssinatura(command);

        var simulacaoCacheada = cache.buscarPorAssinatura(assinatura);
        if (simulacaoCacheada.isPresent()) {
            return simulacaoCacheada.get();
        }

        Simulacao novaSimulacao = Simulacao.criar(
                Dinheiro.of(command.valorSolicitado()),
                PrazoParcela.meses(command.prazoMeses()),
                taxaPadrao);

        cache.salvar(novaSimulacao, assinatura);
        return novaSimulacao;
    }

    private String gerarAssinatura(SimularEmprestimoCommand command) {
        return String.format("V%s:P%s:T%s",
                command.valorSolicitado(),
                command.prazoMeses(),
                taxaPadrao.valorMensal());
    }
}
