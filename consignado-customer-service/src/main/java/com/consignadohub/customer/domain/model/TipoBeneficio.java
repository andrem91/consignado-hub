package com.consignadohub.customer.domain.model;

public enum TipoBeneficio {

    APOSENTADORIA_POR_IDADE("Aposentadoria por idade", true),
    APOSENTADORIA_POR_TEMPO_CONTRIBUICAO("Aposentadoria por Tempo de Contribuição", true),
    APOSENTADORIA_POR_INVALIDEZ("Aposentadoria por Invalidez", true),
    PENSAO_POR_MORTE("Pensão por Morte", true),
    AUXILIO_DOENCA("Auxílio Doença", false),
    BPC_LOAS("Benefício de Prestação Continuada", false);

    private final String descricao;
    private final boolean consignavel;

    TipoBeneficio(String descricao, boolean consignavel) {
        this.descricao = descricao;
        this.consignavel = consignavel;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isConsignavel() {
        return consignavel;
    }
}
