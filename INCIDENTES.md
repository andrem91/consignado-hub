# 🔴 Incidentes & Desafios do ConsignadoHub

> **ATENÇÃO DESENVOLVEDOR:** NÃO LEIA ESTE ARQUIVO!
> 
> Os incidentes serão **revelados pela IA** no momento apropriado.
> Ler antecipadamente estraga a experiência de aprendizado.

---

## 📋 Como Funciona

1. **Você desenvolve** seguindo o roteiro
2. **A IA injeta** um incidente no momento certo
3. **Você resolve** como se fosse produção real
4. **Documentamos** a solução (para entrevistas!)

---

<!--
╔═══════════════════════════════════════════════════════════════════════════════╗
║                                                                                ║
║   ⚠️  SEÇÃO CONFIDENCIAL - APENAS PARA A IA (NÃO LEIA!)                       ║
║                                                                                ║
║   Se você é o DESENVOLVEDOR, pare de ler aqui.                                ║
║   Se você é a IA, continue para ver os incidentes planejados.                 ║
║                                                                                ║
╚═══════════════════════════════════════════════════════════════════════════════╝

## 🎯 INSTRUÇÕES PARA A IA

Ao executar o modo mentor, injete estes incidentes nos momentos indicados.
Atualize ESTADO_PROJETO.md após cada incidente.

### REGRAS:
1. Revele apenas UM incidente por vez
2. Não revele a causa raiz - deixe o dev investigar
3. Dê pistas (logs, métricas, stack traces)
4. Após resolução, documente em "Lições Aprendidas"

---

## 📍 SPRINT 0 - INCIDENTES

### INC-001: Bug Sutil no CPF (Trigger: após implementar CPF)
**Tipo:** 🐛 Bug
**Quando revelar:** Depois que o dev implementar CPF e os testes passarem
**Narrativa para o dev:**
```
🔴 FEEDBACK DO QA: "Encontramos um erro em produção. 
CPFs que começam com zero estão sendo rejeitados 
como inválidos. Exemplo: '01234567890' falha."

Logs do erro:
InvalidCPFException: CPF deve ter 11 dígitos: 1234567890

Prioridade: CRÍTICA - Vários clientes do Norte/Nordeste afetados.
```

**Causa real:** O desenvolvedor pode ter usado Integer.parseInt() ou 
similar que remove zeros à esquerda. Ou a formatação está truncando.

**Solução esperada:** Manter como String, não converter para número.

---

### INC-002: Feature de Última Hora (Trigger: fim da Sprint 0)
**Tipo:** 📋 Feature Urgente
**Quando revelar:** Quando todos os VOs estiverem prontos
**Narrativa:**
```
📢 REUNIÃO URGENTE COM PO:

"O Banco Central publicou nova regra ontem. 
A partir de hoje, precisamos calcular a 
IDADE DO CLIENTE no momento da contratação.

Se o cliente tiver mais de 80 anos no fim do contrato 
(idade atual + prazo em anos), não podemos aprovar.

Exemplo: Cliente com 75 anos + prazo de 8 anos = 83 anos = REJEITADO

Precisamos disso implementado até amanhã."
```

**Solução esperada:** Criar Value Object `Age` ou adicionar validação no `LoanTerm`.

---

## 📍 SPRINT 1 - INCIDENTES

### INC-003: Problema N+1 (Trigger: após Customer Service funcionar)
**Tipo:** ⚡ Performance
**Quando revelar:** Após testes de integração passarem
**Narrativa:**
```
⚠️ ALERTA GRAFANA: Latência p99 do endpoint GET /customers aumentou de 50ms para 2000ms

Screenshot do Prometheus mostra:
- Queries por request: 47 (deveria ser 2)
- Tempo médio por query: 40ms

O DBA está reclamando. Resolva antes que ele escale para o gerente.
```

**Causa real:** Carregamento LAZY de Benefit ao listar Customers.
**Solução esperada:** @EntityGraph ou JOIN FETCH

---

### INC-004: Race Condition na Margem (Trigger: durante implementação de margem)
**Tipo:** 🐛 Bug
**Quando revelar:** Após implementar reserva de margem
**Narrativa:**
```
🔴 INCIDENTE PRODUÇÃO - SEV2

Dois empréstimos foram aprovados para o mesmo cliente simultaneamente.
Margem disponível: R$ 500
Empréstimo 1: R$ 400 (aprovado)
Empréstimo 2: R$ 400 (aprovado) <-- NÃO DEVERIA!

Total comprometido: R$ 800 > R$ 500 disponível

Como isso aconteceu? Investigue e corrija.
```

**Causa real:** Falta de lock otimista/pessimista na atualização da margem.
**Solução esperada:** @Version ou SELECT FOR UPDATE

---

## 📍 SPRINT 2 - INCIDENTES

### INC-005: Cálculo Errado do IOF (Trigger: após cálculos funcionarem)
**Tipo:** 💰 Bug Financeiro
**Quando revelar:** Após simulação retornar valores
**Narrativa:**
```
📞 LIGAÇÃO DO JURÍDICO:

"Recebemos 3 reclamações no Procon esta semana. 
Clientes alegando que o CET informado na simulação 
é diferente do CET no contrato final.

Diferença média: 0.3% a.a.

Isso é GRAVE. Podemos ser multados pelo Banco Central.
Revise os cálculos imediatamente."
```

**Causa real:** IOF calculado com prazo médio fixo ao invés de por parcela.
**Solução esperada:** Calcular IOF proporcional ao prazo de cada parcela.

---

## 📍 SPRINT 3 - INCIDENTES

### INC-006: Mensagens Duplicadas no Kafka (Trigger: após Kafka funcionar)
**Tipo:** 🔄 Bug
**Quando revelar:** Após publicar primeiro evento no Kafka
**Narrativa:**
```
🔴 ALERTA: Análises de crédito duplicadas!

Logs mostram:
10:00:01 - CreditAnalysisCompletedEvent(id=ABC123, result=APPROVED)
10:00:01 - CreditAnalysisCompletedEvent(id=ABC123, result=APPROVED)
10:00:01 - CreditAnalysisCompletedEvent(id=ABC123, result=APPROVED)

Cliente recebeu 3 e-mails de aprovação.
Faturamento contabilizou 3x o valor.

Por que está duplicando?
```

**Causa real:** Consumer sem idempotência + Kafka at-least-once delivery
**Solução esperada:** Implementar @Idempotent com Redis

---

### INC-007: Feature Urgente - Portabilidade (Trigger: meio da Sprint 3)
**Tipo:** 📋 Feature
**Quando revelar:** No meio da implementação da Sprint 3
**Narrativa:**
```
📢 EMAIL DO DIRETOR:

"Concorrência está agressiva na portabilidade.
Precisamos implementar RECEBER portabilidade de outros bancos.

Requisitos:
1. Endpoint para receber solicitação de portabilidade
2. Consultar saldo devedor do banco origem (mock)
3. Oferecer melhores condições
4. Se aceito, quitar banco origem e criar contrato nosso

Prazo: Final desta sprint.
Obs: Pode ser MVP, poliremos depois."
```

---

## 📍 SPRINT 4 - INCIDENTES

### INC-008: Circuit Breaker não abre (Trigger: após configurar Resilience4j)
**Tipo:** 🔴 Incidente Produção
**Quando revelar:** Após implementar circuit breaker
**Narrativa:**
```
🔴🔴🔴 INCIDENTE CRÍTICO - SEV1 🔴🔴🔴

Dataprev está fora do ar há 30 minutos.
Nosso sistema deveria estar retornando fallback.
MAS ESTÁ TRAVANDO!

Métricas Resilience4j:
- Circuit State: CLOSED (deveria ser OPEN!)
- Failure Rate: 100%
- Slow Call Rate: 100%

Threads estão bloqueadas esperando Dataprev.
Todo o sistema vai cair em 5 minutos.

RESOLVA AGORA!
```

**Causa real:** slidingWindowSize muito grande ou configuração errada
**Solução esperada:** Ajustar configuração, adicionar timeout

---

### INC-009: Saga falha sem compensação (Trigger: após implementar Saga)
**Tipo:** 🐛 Bug
**Quando revelar:** Durante testes da saga de averbação
**Narrativa:**
```
🔴 INCIDENTE: Estado inconsistente detectado

Contrato ABC está com status "ACTIVE" mas não tem averbação confirmada.
Averbação falhou mas contrato não foi revertido.

Timeline do contrato:
09:00 - Contrato criado (PENDING_AVERBATION)
09:01 - Averbação solicitada
09:02 - Averbação FALHOU (Dataprev timeout)
09:02 - ??? Contrato continua ACTIVE ???

Margem do cliente foi reservada mas empréstimo não existe no INSS.
```

**Causa real:** Saga sem tratamento de falha/compensação
**Solução esperada:** Implementar compensação na Saga

---

## 📍 SPRINT 5 - INCIDENTES

### INC-010: Scheduler executando múltiplas vezes (Trigger: após scheduler)
**Tipo:** 🐛 Bug
**Quando revelar:** Após implementar scheduler
**Narrativa:**
```
⚠️ LOGS SUSPEITOS

06:00:01 - [pod-1] Verificando parcelas vencidas...
06:00:01 - [pod-2] Verificando parcelas vencidas...
06:00:01 - [pod-3] Verificando parcelas vencidas...

Clientes receberam 3 notificações de vencimento cada!

Temos 3 replicas do serviço. O scheduler está rodando em todas.
```

**Causa real:** @Scheduled sem lock distribuído
**Solução esperada:** ShedLock ou líder election

---

## 📍 SPRINT 6 - INCIDENTES

### INC-011: Token não invalidado (Trigger: após Keycloak)
**Tipo:** 🔐 Segurança
**Quando revelar:** Após autenticação funcionar
**Narrativa:**
```
🔐 ALERTA SEGURANÇA

Teste de penetração identificou:
1. Usuário fez logout
2. Token ainda funciona por 30 minutos
3. Se token vazou, atacante tem 30 min de acesso

Auditor está exigindo correção imediata.
Compliance em risco.
```

**Causa real:** JWT stateless sem blacklist
**Solução esperada:** Blacklist de tokens no Redis

---

## 📍 SPRINT 7 - INCIDENTES

### INC-012: OOMKilled em Produção (Trigger: após Docker)
**Tipo:** 🔴 Incidente
**Quando revelar:** Após deploy com Docker
**Narrativa:**
```
🔴 PODS CRASHANDO

kubectl get pods:
customer-service-abc123   0/1   OOMKilled   5   10m
customer-service-def456   0/1   OOMKilled   3   8m

Logs:
java.lang.OutOfMemoryError: Java heap space

Configuração atual:
- Container limit: 512Mi
- JVM não configurada

Serviço está indisponível!
```

**Causa real:** JVM pegando toda memória disponível sem -Xmx
**Solução esperada:** Configurar -Xmx e -Xms corretamente

---

## 📍 CHECKLIST PARA IA

Ao injetar incidentes:
- [ ] Atualizar ESTADO_PROJETO.md
- [ ] Adicionar incidente na tabela "Incidentes Ativos"
- [ ] Dar contexto realista (logs, métricas)
- [ ] NÃO revelar causa raiz
- [ ] Após resolução, mover para "Histórico"
- [ ] Preencher "Lições Aprendidas"

-->
