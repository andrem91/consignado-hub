package com.consignadohub.simulation.domain.model;

import com.consignadohub.simulation.domain.exception.DomainException;
import com.consignadohub.simulation.domain.vo.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public static Simulacao criar(Dinheiro valorSolicitado, PrazoParcela prazo, TaxaJuros taxaJuros) {

        if (valorSolicitado == null)
            throw DomainException.required("valorSolicitado");
        if (prazo == null)
            throw DomainException.required("prazo");
        if (taxaJuros == null)
            throw DomainException.required("taxaJuros");

        Dinheiro valorParcela = calcularParcela(valorSolicitado, prazo, taxaJuros);
        Dinheiro valorTotal = valorParcela.multiplicar(prazo.meses());
        Dinheiro iof = calcularIOF(valorSolicitado);
        CET cet = calcularCET(valorSolicitado, valorTotal, prazo);

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

    private static Dinheiro calcularParcela(Dinheiro valor, PrazoParcela prazo, TaxaJuros taxa) {
        // Fórmula Price: PMT = PV × [i(1+i)^n] / [(1+i)^n - 1]
        BigDecimal pv = valor.valor();
        BigDecimal i = taxa.comoDecimal(); // 1.66% → 0.0166
        int n = prazo.meses();
        BigDecimal umMaisI = BigDecimal.ONE.add(i);
        BigDecimal umMaisIPowN = umMaisI.pow(n);

        BigDecimal numerador = i.multiply(umMaisIPowN);
        BigDecimal denominador = umMaisIPowN.subtract(BigDecimal.ONE);

        BigDecimal fator = numerador.divide(denominador, 10, RoundingMode.HALF_UP);
        BigDecimal parcela = pv.multiply(fator);

        return Dinheiro.of(parcela);
    }

    private static Dinheiro calcularIOF(Dinheiro valorSolicitado) {
        // IOF simplificado: 0.38% fixo
        // TODO: Implementar alíquota diária (0.0082%/dia)
        BigDecimal aliquota = new BigDecimal("0.0038");
        return valorSolicitado.multiplicar(aliquota);
    }

    private static CET calcularCET(Dinheiro valorSolicitado, Dinheiro valorTotal, PrazoParcela prazo) {
        // CET simplificado usando taxa equivalente anual
        // Fórmula: ((valorTotal/valorSolicitado)^(12/meses) - 1) * 100
        BigDecimal total = valorTotal.valor();
        BigDecimal solicitado = valorSolicitado.valor();
        int meses = prazo.meses();

        // razao = valorTotal / valorSolicitado
        double razao = total.divide(solicitado, 10, RoundingMode.HALF_UP).doubleValue();

        // taxa anualizada = (razao ^ (12/meses)) - 1
        double expoente = 12.0 / meses;
        double taxaAnual = Math.pow(razao, expoente) - 1;

        BigDecimal cetAnual = BigDecimal.valueOf(taxaAnual * 100)
                .setScale(2, RoundingMode.HALF_UP);

        return CET.of(cetAnual);
    }

}
