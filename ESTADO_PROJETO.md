# 📋 Estado do Projeto - ConsignadoHub

> **IMPORTANTE:** Este arquivo é atualizado pela IA a cada sessão.
> Ao iniciar nova conversa, use: `/modo-mentor`

---

## 🎯 Resumo Rápido

| Campo | Valor |
|-------|-------|
| **Sprint Atual** | Sprint 2 - ✅ CONCLUÍDO |
| **Status** | 🟢 Pronto para Sprint 3 |
| **Última Atualização** | 2026-01-14 |
| **Próxima Tarefa** | Swagger/OpenAPI + Contract Service |

---

## ✅ Progresso por Sprint

### Sprint 0: Setup & Value Objects ✅
- [x] CPF (7), Dinheiro (9), NumeroBeneficio (5), TaxaJuros (4)
- [x] PercentualMargem (4), PrazoParcela (3), CET (4)
- **Total: 35 testes**

### Sprint 1: Customer Service ✅

#### ✅ Fase 1: Domain (31 testes)
- [x] Email, Telefone, DataNascimento, TipoBeneficio, Beneficio, ClienteId, Cliente

#### ✅ Fase 2: Application (2 testes)
- [x] CadastrarClienteCommand, CadastrarClienteUseCase, BuscarClienteQuery
- [x] ClienteRepository, ClienteService, ClienteJaExisteException

#### ✅ Fase 3: Adapters
- [x] docker-compose.yml (PostgreSQL)
- [x] ClienteJpaEntity, ClienteJpaRepository, ClienteMapper, ClienteRepositoryAdapter
- [x] Flyway Migration V1__create_clientes.sql
- [x] ClienteController (REST API)
- [x] CadastrarClienteRequest, ClienteResponse

**Total Sprint 1: 33 testes**

---

### Sprint 2: Simulation Service ✅

#### ✅ Fase 1: Setup
- [x] Criar consignado-simulation-service
- [x] Estrutura hexagonal (domain/application/adapter)
- [x] Configurar application.yaml (porta 8081)
- [x] Configurar pom.xml (Redis em vez de JPA/PostgreSQL)
- [x] Atualizar docker-compose (Redis)

#### ✅ Fase 2: Domain
- [x] SimulacaoId + SimulacaoIdTest (3 testes)
- [x] Simulacao + SimulacaoTest (5 testes) - Cálculos Price, IOF, CET

#### ✅ Fase 3: Application
- [x] SimularEmprestimoCommand + Testes
- [x] SimularEmprestimoUseCase (interface)
- [x] SimulacaoCache (Port Out)
- [x] SimulacaoService + SimulacaoServiceTest

#### ✅ Fase 4: Adapters
- [x] SimulacaoController (POST /simulacoes)
- [x] SimularEmprestimoRequest, SimulacaoResponse (DTOs)
- [x] SimulacaoRedisAdapter (cache em memória)
- [x] SimulationConfig (bean TaxaJuros)

**Total Sprint 2: 10+ testes**

---

### Integração Customer ↔ Simulation ✅

- [x] Spring Cloud OpenFeign (2025.0.0)
- [x] SimulationClient (Feign interface)
- [x] SimulacaoDTO
- [x] Endpoint GET /clientes/{id}/simulacao
- [x] Testado com curl: integração funcionando!

---

## 🔧 Correções SonarQube Aplicadas

- [x] DataNascimento: constante FIELD_NAME
- [x] CPF: regex `\\D` em vez de `[^0-9]`
- [x] PercentualMargem: constante FIELD_NAME
- [x] Dinheiro: constante FIELD_NAME
- [x] ClienteTest: `hasSize()` em vez de `.size().isEqualTo()`

---

## 📊 Serviços Ativos

| Serviço | Porta | Status | Banco |
|---------|-------|--------|-------|
| Customer Service | 8080 | ✅ Funcionando | PostgreSQL |
| Simulation Service | 8081 | ✅ Funcionando | Redis (em memória) |

---

## 🔴 Incidentes Ativos

| ID | Sprint | Tipo | Status | Descrição |
|----|--------|------|--------|-----------|
| INC-002 | 0 | 📋 Feature | Pendente | Idade máxima no contrato |

---

## 📌 Última Sessão

**Data:** 2026-01-14

**O que foi feito:**
- Sprint 2 Simulation Service COMPLETO
- Integração Customer ↔ Simulation via Feign Client
- Endpoint GET /clientes/{id}/simulacao funcionando
- Correções SonarQube (constantes, regex, assertions)
- JavaDoc adicionado em 15+ classes

**Próximos passos:**
- Implementar Swagger/OpenAPI
- Sprint 3: Contract Service (averbação, contratos)

---

## 🔧 Como Continuar

Use: `/modo-mentor`
