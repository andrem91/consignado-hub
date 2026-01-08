# 📋 Estado do Projeto - ConsignadoHub

> **IMPORTANTE:** Este arquivo é atualizado pela IA a cada sessão.
> Ao iniciar nova conversa, use: `/modo-mentor`

---

## 🎯 Resumo Rápido

| Campo | Valor |
|-------|-------|
| **Sprint Atual** | Sprint 2 - 🚧 EM ANDAMENTO |
| **Status** | 🟡 Criando consignado-simulation-service |
| **Última Atualização** | 2026-01-08 |
| **Próxima Tarefa** | Estrutura do novo microsserviço |

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
**Total Geral: 68+ testes ✅**

---

## 🔴 Incidentes Ativos

| ID | Sprint | Tipo | Status | Descrição |
|----|--------|------|--------|-----------|
| INC-002 | 0 | 📋 Feature | Pendente | Idade máxima no contrato |

---

## 📌 Última Sessão

**Data:** 2026-01-06

**O que foi feito:**
- Sprint 1 Customer Service COMPLETA
- Fase 2: Ports CQS (Command/Query), ClienteService com TDD
- Fase 3: docker-compose, JPA Adapters, Controller REST
- API testada com curl: POST /clientes funcionando

**Próximos passos:**
- Sprint 2: Simulation Service (cálculo de parcelas)
- Implementar endpoint de simulação
- Cálculo Price + IOF

---

## 🔧 Como Continuar

Use: `/modo-mentor`
