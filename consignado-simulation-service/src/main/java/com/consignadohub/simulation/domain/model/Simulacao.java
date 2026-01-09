package com.consignadohub.simulation.domain.model;

import com.consignadohub.simulation.domain.exception.DomainException;
import com.consignadohub.simulation.domain.service.CalculadoraFinanceira;
import com.consignadohub.simulation.domain.vo.*;
import lombok.Getter;

/**
 * Aggregate Root para Simulação de Empréstimo.
 * Encapsula os cálculos financeiros e garante consistência.
 */
@Getter
public class Simulacao {

    private final SimulacaoId id;
    private final Dinheiro valorSolicitado;
    private final PrazoParcela prazo;
    private final TaxaJuros taxaJuros;
    private final Dinheiro valorParcela;
    private final Dinheiro valorTotal;
    private final Dinheiro iof;
    private final CET cet;

    /**
     * Factory Method para criar uma nova simulação.
     * Calcula automaticamente parcela, total, IOF e CET.
     */
    public static Simulacao criar(Dinheiro valorSolicitado, PrazoParcela prazo, TaxaJuros taxaJuros) {
        validar(valorSolicitado, prazo, taxaJuros);

        Dinheiro valorParcela = CalculadoraFinanceira.calcularParcelaPrice(valorSolicitado, prazo, taxaJuros);
        Dinheiro valorTotal = valorParcela.multiplicar(prazo.meses());
        Dinheiro iof = CalculadoraFinanceira.calcularIOF(valorSolicitado);
        CET cet = CalculadoraFinanceira.calcularCET(valorSolicitado, valorTotal, prazo);

        return new Simulacao(
                SimulacaoId.gerar(),
                valorSolicitado,
                prazo,
                taxaJuros,
                valorParcela,
                valorTotal,
                iof,
                cet);
    }

    private static void validar(Dinheiro valorSolicitado, PrazoParcela prazo, TaxaJuros taxaJuros) {
        if (valorSolicitado == null)
            throw DomainException.required("valorSolicitado");
        if (prazo == null)
            throw DomainException.required("prazo");
        if (taxaJuros == null)
            throw DomainException.required("taxaJuros");
    }

    private Simulacao(SimulacaoId id, Dinheiro valorSolicitado, PrazoParcela prazo, TaxaJuros taxaJuros,
            Dinheiro valorParcela, Dinheiro valorTotal, Dinheiro iof, CET cet) {
        this.id = id;
        this.valorSolicitado = valorSolicitado;
        this.prazo = prazo;
        this.taxaJuros = taxaJuros;
        this.valorParcela = valorParcela;
        this.valorTotal = valorTotal;
        this.iof = iof;
        this.cet = cet;
    }
}
