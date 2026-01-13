package com.consignadohub.simulation.adapter.in.web;

import com.consignadohub.simulation.adapter.in.web.dto.SimulacaoResponse;
import com.consignadohub.simulation.adapter.in.web.dto.SimularEmprestimoRequest;
import com.consignadohub.simulation.application.port.in.SimularEmprestimoCommand;
import com.consignadohub.simulation.application.port.in.SimularEmprestimoUseCase;
import com.consignadohub.simulation.domain.model.Simulacao;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para simulação de empréstimos.
 */

@RestController
@RequestMapping("/simulacoes")
@RequiredArgsConstructor
public class SimulacaoController {

    private final SimularEmprestimoUseCase simularEmprestimo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SimulacaoResponse simular(@Valid @RequestBody SimularEmprestimoRequest request) {
        var command = new SimularEmprestimoCommand(
                request.valorSolicitado(),
                request.prazoMeses());

        Simulacao simulacao = simularEmprestimo.executar(command);

        return toResponse(simulacao);
    }

    private SimulacaoResponse toResponse(Simulacao simulacao) {
        return new SimulacaoResponse(
                simulacao.getId().valor(),
                simulacao.getValorSolicitado().valor(),
                simulacao.getPrazo().meses(),
                simulacao.getTaxaJuros().valorMensal(),
                simulacao.getValorParcela().valor(),
                simulacao.getValorTotal().valor(),
                simulacao.getIof().valor(),
                simulacao.getCet().valorAnual());
    }
}
