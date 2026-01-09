package com.consignadohub.simulation.domain.service;

import com.consignadohub.simulation.domain.vo.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Domain Service responsável por cálculos financeiros.
 * Contém fórmulas de Tabela Price, IOF e CET.
 */
public class CalculadoraFinanceira {

    private CalculadoraFinanceira() {
        // Utility class
    }

    /**
     * Calcula a parcela mensal usando Tabela Price.
     * Fórmula: PMT = PV × [i(1+i)^n] / [(1+i)^n - 1]
     *
     * @param valorSolicitado Valor do empréstimo (PV)
     * @param prazo           Prazo em meses (n)
     * @param taxa            Taxa de juros mensal (i)
     * @return Valor da parcela mensal
     */
    public static Dinheiro calcularParcelaPrice(Dinheiro valorSolicitado,
            PrazoParcela prazo,
            TaxaJuros taxa) {
        BigDecimal pv = valorSolicitado.valor();
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

    /**
     * Calcula o IOF sobre o valor solicitado.
     * Versão simplificada: apenas alíquota fixa de 0.38%.
     * 
     * TODO: Implementar alíquota diária (0.0082%/dia, limitada a 365 dias)
     *
     * @param valorSolicitado Valor do empréstimo
     * @return Valor do IOF
     */
    public static Dinheiro calcularIOF(Dinheiro valorSolicitado) {
        BigDecimal aliquotaFixa = new BigDecimal("0.0038"); // 0.38%
        return valorSolicitado.multiplicar(aliquotaFixa);
    }

    /**
     * Calcula o CET (Custo Efetivo Total) anualizado.
     * Fórmula simplificada: ((valorTotal/valorSolicitado)^(12/meses) - 1) * 100
     *
     * @param valorSolicitado Valor do empréstimo
     * @param valorTotal      Soma de todas as parcelas
     * @param prazo           Prazo em meses
     * @return CET anualizado
     */
    public static CET calcularCET(Dinheiro valorSolicitado,
            Dinheiro valorTotal,
            PrazoParcela prazo) {
        BigDecimal total = valorTotal.valor();
        BigDecimal solicitado = valorSolicitado.valor();
        int meses = prazo.meses();

        double razao = total.divide(solicitado, 10, RoundingMode.HALF_UP).doubleValue();
        double expoente = 12.0 / meses;
        double taxaAnual = Math.pow(razao, expoente) - 1;

        BigDecimal cetAnual = BigDecimal.valueOf(taxaAnual * 100)
                .setScale(2, RoundingMode.HALF_UP);

        return CET.of(cetAnual);
    }
}
