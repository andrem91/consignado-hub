# 📋 Estado do Projeto - ConsignadoHub

> **IMPORTANTE:** Este arquivo é atualizado pela IA a cada sessão.
> Ao iniciar nova conversa, use: `/modo-mentor` ou cole o conteúdo deste arquivo.

---

## 🎯 Resumo Rápido

| Campo | Valor |
|-------|-------|
| **Sprint Atual** | Sprint 0 - Setup & Value Objects |
| **Status** | 🟡 Em andamento |
| **Última Atualização** | 2026-01-02 |
| **Próxima Tarefa** | Implementar CPF Value Object (TDD) |

---

## ✅ Progresso por Sprint

### Sprint 0: Setup & Value Objects
- [x] Criar repositório GitHub
- [x] Configurar projeto Maven (Spring Initializr)
- [x] Estrutura de pacotes Hexagonal (domain, application, adapter)
- [ ] docker-compose.yml base
- [ ] CPF + tests ← **PRÓXIMO**
- [ ] Money + tests
- [ ] BenefitNumber + tests
- [ ] InterestRate + tests
- [ ] MarginPercentage + tests
- [ ] LoanTerm + tests
- [ ] CET + tests
- [ ] Spring Modulith setup

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
- Repositório GitHub criado e configurado
- Projeto `consignado-customer-service` gerado via Spring Initializr
  - Java 21, Spring Boot 3.5.9, Maven
  - Dependências: Web, JPA, PostgreSQL, Flyway, Validation, Lombok, Actuator
- Estrutura de pacotes Arquitetura Hexagonal criada:
  - `domain/` (vo, model, exception)
  - `application/` (port/in, port/out, service)
  - `adapter/` (in/web, out/persistence)
- `application.properties` configurado (JPA/Flyway desabilitados temporariamente)
- Documentação completa adicionada (ROTEIRO, PROJETO_CONSIGNADO, INCIDENTES)

**Próximos passos:**
- Criar `DomainException` e `InvalidCPFException`
- Implementar `CPFTest.java` (teste primeiro - TDD)
- Implementar `CPF.java` Value Object
- Continuar com demais Value Objects

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
