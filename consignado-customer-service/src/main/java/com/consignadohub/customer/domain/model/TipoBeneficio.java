package com.consignadohub.customer.domain.model;

public enum TipoBeneficio {

    APOSENTADORIA_POR_IDADE("Aposentadoria por idade"),
    APOSENTADORIA_POR_TEMPO_CONTRIBUICAO("Aposentadoria por Tempo de Contribuição"),
    APOSENTADORIA_POR_INVALIDEZ("Aposentadoria por Invalidez"),
    PENSAO_POR_MORTE("Pensão por Morte"),
    AUXILIO_DOENCA("Auxílio Doença"),
    BPC_LOAS("Benefício de Prestação Continuada");

    private final String descricao;

    TipoBeneficio(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
