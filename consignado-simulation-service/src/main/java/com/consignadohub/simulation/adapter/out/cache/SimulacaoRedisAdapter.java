package com.consignadohub.simulation.adapter.out.cache;

import com.consignadohub.simulation.application.port.out.SimulacaoCache;
import com.consignadohub.simulation.domain.model.Simulacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementação em memória do cache de simulações.
 * TODO: Substituir por Redis em produção.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SimulacaoRedisAdapter implements SimulacaoCache {

    //Cahce em memória (substituir por RedisTemplate depois)
    private final Map<String, Simulacao> cache = new ConcurrentHashMap<>();

    @Override
    public Optional<Simulacao> buscarPorAssinatura(String assinatura) {
        log.debug("Buscando no cache: {}", assinatura);
        return Optional.ofNullable(cache.get(assinatura));
    }

    @Override
    public void salvar(Simulacao simulacao, String assinatura) {
        log.debug("Salvando no cache: {}", assinatura);
        cache.put(assinatura, simulacao);
    }
}
