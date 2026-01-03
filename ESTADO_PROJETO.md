# 📋 Estado do Projeto - ConsignadoHub

> **IMPORTANTE:** Este arquivo é atualizado pela IA a cada sessão.
> Ao iniciar nova conversa, use: `/modo-mentor` ou cole o conteúdo deste arquivo.

---

## 🎯 Resumo Rápido

| Campo | Valor |
|-------|-------|
| **Sprint Atual** | Sprint 0 - CONCLUÍDA ✅ |
| **Status** | 🟢 Pronto para Sprint 1 |
| **Última Atualização** | 2026-01-03 |
| **Próxima Tarefa** | Spring Modulith + docker-compose OU Sprint 1 Customer |

---

## ✅ Progresso por Sprint

### Sprint 0: Setup & Value Objects ✅
- [x] Criar repositório GitHub
- [x] Configurar projeto Maven (Spring Initializr)
- [x] Estrutura de pacotes Hexagonal (domain, application, adapter)
- [ ] docker-compose.yml base
- [x] CPF + tests (7 testes) ✅
- [x] Dinheiro + tests (9 testes) ✅
- [x] NumeroBeneficio + tests (5 testes) ✅
- [x] TaxaJuros + tests (4 testes) ✅
- [x] PercentualMargem + tests (3 testes) ✅
- [x] PrazoParcela + tests (3 testes) ✅
- [x] CET + tests (4 testes) ✅
- [ ] Spring Modulith setup

**Total: 35 testes unitários passando!**

### Sprint 1: Customer Service
- [ ] Customer (Aggregate)
- [ ] Benefit (Entity)
- [ ] Margin (Value Object)
- [ ] Ports (Use Cases)
- [ ] Adapters (JPA, REST)
- [ ] Mock Dataprev
- [ ] Flyway migrations
- [ ] Testes de integração

### Sprint 2: Simulation Service
- [ ] Simulation (Aggregate)
- [ ] Cálculo Price
- [ ] Cálculo IOF
- [ ] Cálculo CET
- [ ] Validação margem
- [ ] Feign Client

### Sprint 3: Credit Analysis
- [ ] CreditAnalysis (Aggregate)
- [ ] CreditScore (VO)
- [ ] Regras de negócio
- [ ] Regra dos 90 dias
- [ ] Kafka Producer
- [ ] Idempotência Redis

### Sprint 4: Loan + Averbation
- [ ] Contract (Aggregate)
- [ ] Averbation (Entity)
- [ ] Saga de Averbação
- [ ] Kafka Consumer
- [ ] Resilience4j
- [ ] Mock Dataprev

### Sprint 5: Payment Service
- [ ] Installment (Entity)
- [ ] Payment (Entity)
- [ ] Scheduler vencimentos
- [ ] Quitação antecipada

### Sprint 6: Gateway + Keycloak
- [ ] Spring Cloud Gateway
- [ ] Keycloak setup
- [ ] OAuth2/JWT
- [ ] Multi-canal
- [ ] Rate Limiting

### Sprint 7: DevOps
- [ ] Dockerfile otimizado
- [ ] Docker Compose completo
- [ ] GitHub Actions
- [ ] Prometheus + Grafana
- [ ] Jaeger

### Sprint 8-11: Cloud
- [ ] Kubernetes manifests
- [ ] Helm charts
- [ ] AWS LocalStack
- [ ] Terraform
- [ ] E2E Tests

---

## 🔴 Incidentes Ativos

| ID | Sprint | Tipo | Status | Descrição |
|----|--------|------|--------|-----------|
| - | - | - | - | Nenhum incidente ativo |

---

## 🐛 Bugs Conhecidos

| ID | Sprint | Status | Descrição |
|----|--------|--------|-----------|
| - | - | - | Nenhum bug pendente |

---

## 📝 Histórico de Incidentes Resolvidos

| ID | Sprint | Tipo | Causa Raiz | Solução |
|----|--------|------|------------|---------|
| - | - | - | - | (será preenchido durante o projeto) |

---

## 💡 Lições Aprendidas

> Esta seção é preenchida após resolver incidentes.
> Use para entrevistas: "Conte um problema que você resolveu".

1. *(será preenchido durante o projeto)*

---

## 📌 Última Sessão

**Data:** 2026-01-02

**O que foi feito:**
- Value Object `CPF` implementado com TDD
  - Validação completa (nulo, formato, dígitos repetidos, dígitos verificadores)
  - Métodos: `formatar()`, `mascarar()`, `toString()`
  - 7 testes unitários
- Value Object `Dinheiro` implementado com TDD
  - `BigDecimal` com 2 casas decimais (RoundingMode.HALF_UP)
  - Factory method `of()` para criação
  - Método `somar()` imutável
  - 4 testes unitários
- Exceções de domínio: `DomainException`, `InvalidCPFException`, `InvalidDinheiroException`
- Nomenclatura em português para termos de domínio

**Próximos passos:**
- Implementar `BenefitNumber` (número do benefício INSS)
- Implementar `InterestRate`, `MarginPercentage`, `LoanTerm`, `CET`
- Configurar docker-compose.yml

---

## 🔧 Como Continuar em Nova Conversa

1. Abra o arquivo `/modo-mentor` ou cole:
   ```
   Continuando projeto ConsignadoHub. 
   Leia ESTADO_PROJETO.md para contexto.
   Sprint atual: [VER ACIMA]
   ```

2. A IA vai:
   - Ler o estado atual
   - Continuar de onde parou
   - Injetar incidentes conforme o plano
