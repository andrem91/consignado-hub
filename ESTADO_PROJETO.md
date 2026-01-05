# 📋 Estado do Projeto - ConsignadoHub

> **IMPORTANTE:** Este arquivo é atualizado pela IA a cada sessão.
> Ao iniciar nova conversa, use: `/modo-mentor` ou cole o conteúdo deste arquivo.

---

## 🎯 Resumo Rápido

| Campo | Valor |
|-------|-------|
| **Sprint Atual** | Sprint 1 - Em andamento 🔄 |
| **Status** | 🟢 Fase 1 COMPLETA, iniciando Fase 2 |
| **Última Atualização** | 2026-01-05 |
| **Próxima Tarefa** | Ports (CQS) + ClienteService |

---

## ✅ Progresso por Sprint

### Sprint 0: Setup & Value Objects ✅
- [x] Repositório GitHub, projeto Maven, estrutura Hexagonal
- [x] CPF (7), Dinheiro (9), NumeroBeneficio (5), TaxaJuros (4)
- [x] PercentualMargem (3+1), PrazoParcela (3), CET (4)
- **Total Sprint 0: 35 testes ✅**

### Sprint 1: Customer Service 🔄

#### ✅ Fase 1: Domain (COMPLETA)
- [x] Email VO (4 testes) ✅
- [x] Telefone VO (5 testes) ✅
- [x] DataNascimento VO (5 testes) ✅
- [x] TipoBeneficio Enum (8 testes) - com `isConsignavel()` ✅
- [x] Beneficio Entity (3 testes) - validações fail-fast ✅
- [x] ClienteId VO (3 testes) ✅
- [x] Cliente Aggregate (3 testes) ✅

#### ⏳ Fase 2: Application (PRÓXIMA)
- [ ] Ports CQS: Commands (Write) e Queries (Read)
- [ ] ClienteService

#### Fase 3: Adapters (precisa docker-compose)
- [ ] docker-compose.yml
- [ ] JPA Entities + Migrations
- [ ] Controllers + Integration Tests

**Total Sprint 1 Fase 1: 31 testes ✅**
**Total Geral: 66+ testes ✅**

---

## 📐 Regras de Desenvolvimento

### Nomenclatura
| Elemento | Idioma | Exemplo |
|----------|--------|---------|
| Classes de domínio | 🇧🇷 Português | `Beneficio`, `Cliente`, `Dinheiro` |
| Atributos/métodos negócio | 🇧🇷 Português | `valorMensal`, `calcularMargem()` |
| Factory methods | 🇺🇸 Inglês | `of()`, `novo()` |
| Patterns técnicos | 🇺🇸 Inglês | `@Getter`, `Repository`, `Service` |

### TDD
1. Escrever TESTE primeiro (Red)
2. Implementar mínimo para passar (Green)
3. Refatorar (Refactor)

### Arquitetura Hexagonal
- **Domain**: Sem dependências externas, sem @Entity JPA
- **Application**: Ports (interfaces) + Services
- **Adapter**: JPA, REST, Kafka (com anotações framework)

---

## 🔴 Incidentes Ativos

| ID | Sprint | Tipo | Status | Descrição |
|----|--------|------|--------|-----------|
| INC-001 | 0 | 🐛 Bug | N/A | CPF zeros - código já correto |
| INC-002 | 0 | 📋 Feature | Pendente | Idade máxima no contrato |

---

## 📌 Última Sessão

**Data:** 2026-01-04

**O que foi feito:**
- Sprint 0 concluída (7 Value Objects, 35 testes)
- Sprint 1 iniciada - Fase 1 Domain
- VOs: Email, Telefone, DataNascimento
- Enum: TipoBeneficio com `isConsignavel()`
- Entity: Beneficio com validações fail-fast
- PercentualMargem: adicionado `calcularMargem()`
- Módulo 1 dividido em Parte 1 (Básicos) e Parte 2 (Avançados)

**Próximos passos:**
- Implementar ClienteId VO
- Implementar Cliente Aggregate Root
- Continuar Fase 1 Domain

---

## 🔧 Como Continuar em Nova Conversa

Use: `/modo-mentor`

Ou cole:
```
Continuando projeto ConsignadoHub. 
Leia ESTADO_PROJETO.md para contexto.
Sprint atual: Sprint 1 - Fase 1 Domain
```
