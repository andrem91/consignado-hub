# 🏦 ConsignadoHub

> Sistema de Crédito Consignado INSS - Baseado na Stack de grandes instituições financeiras

[![Build](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/andrem91/consignado-hub)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-green)](https://spring.io/projects/spring-boot)

---

## 📋 Sobre o Projeto

Este projeto é uma implementação completa de um **sistema de crédito consignado INSS**, desenvolvido para fins de **estudo e portfólio**. A arquitetura e tecnologias são baseadas na stack real utilizada pela **Comunidade de Consignado & FGTS de grandes instituições financeiras**.

### 🎯 Objetivos

- Aplicar **DDD** (Domain-Driven Design) com Value Objects, Entities e Aggregates
- Implementar **Arquitetura Hexagonal** (Ports & Adapters)
- Desenvolver **Microsserviços** com Spring Boot 3.5
- Utilizar **Event-Driven Architecture** com Apache Kafka
- Simular integração com **órgãos governamentais** (Dataprev/INSS)
- Configurar **CI/CD** com GitHub Actions
- Deploy em **Kubernetes** com **Terraform**

---

## 🎮 Modo Mentor: Desenvolvimento Ágil Simulado

Este projeto inclui um **sistema de incidentes simulados** para criar experiência real de desenvolvimento:

| Tipo | Descrição |
|------|-----------|
| 🐛 Bugs | Descobertos via logs, métricas ou testes |
| 🔴 Incidentes | Problemas de produção com pressão real |
| 📋 Features Urgentes | Requisitos de última hora |
| ⚡ Performance | Problemas identificados via monitoramento |
| 🔐 Segurança | Vulnerabilidades descobertas |

### Como Funciona

1. **Desenvolva** seguindo o `doc/ROTEIRO_IMPLEMENTACAO.md`
2. **A IA injeta** incidentes durante o desenvolvimento
3. **Você resolve** como se fosse produção
4. **Documentamos** para usar em entrevistas

### Arquivos do Sistema

| Arquivo | Descrição |
|---------|-----------|
| `ESTADO_PROJETO.md` | Estado atual, progresso, contexto entre conversas |
| `INCIDENTES.md` | Incidentes planejados (NÃO LEIA!) |

### Para Continuar em Nova Conversa

Use o comando: `/modo-mentor`

---

## 🏗️ Arquitetura

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              API GATEWAY                                     │
│                     (Spring Cloud Gateway + OAuth2)                          │
└───────────────────────────────────────────────────────────────────────────────┘
                                      │
     ┌────────────────┬───────────────┼───────────────┬────────────────┐
     │                │               │               │                │
     ▼                ▼               ▼               ▼                ▼
┌──────────┐   ┌────────────┐   ┌───────────┐   ┌──────────┐   ┌────────────┐
│ Customer │   │ Simulation │   │  Credit   │   │   Loan   │   │  Payment   │
│ Service  │   │  Service   │   │ Analysis  │   │ Service  │   │  Service   │
│ (DDD)    │   │            │   │           │   │(E.Sourc) │   │            │
└────┬─────┘   └─────┬──────┘   └─────┬─────┘   └────┬─────┘   └──────┬─────┘
     │               │               │               │                │
     └───────────────┴───────────────┴───────────────┴────────────────┘
                                     │
                            ┌────────┴────────┐
                            │  Apache Kafka   │
                            └────────┬────────┘
                                     │
                   ┌─────────────────┼─────────────────┐
                   │                 │                 │
            ┌──────┴──────┐  ┌───────┴───────┐  ┌──────┴──────┐
            │   Ledger    │  │ Notification  │  │   Audit     │
            │  Service    │  │   Service     │  │   Service   │
            └─────────────┘  └───────────────┘  └─────────────┘
```

### 💡 Decisão Arquitetural

| Módulo | Arquitetura | Justificativa |
|--------|-------------|---------------|
| **CustomerService** | Hexagonal | CRUD + integrações, padrão base |
| **SimulationService** | Hexagonal | Cálculos simples, poucos adapters |
| **CreditService** | Hexagonal | Eventos Kafka, múltiplos adapters |
| **ContractService** | **Clean Architecture** | Event Sourcing, domínio rico, múltiplos estados |
| **PaymentService** | Hexagonal | Muitas integrações bancárias |
| **LedgerService** | Hexagonal + Partidas Dobradas | Eventos de saldo para auditoria |

### 🗄️ Persistência Poliglota (Polyglot Persistence)

> *"Use o banco certo para cada tipo de dado"*

| Dado | Banco | Por quê |
|------|-------|---------|
| Clientes, Contratos (estado) | **PostgreSQL** | ACID, JOINs, queries complexas |
| Saldos Contábeis | **PostgreSQL** | Transações ACID |
| **Event Store (eventos)** | **DynamoDB** | Append-only, escala infinita, barato |
| Cache | **Redis** | Performance, sessões |

---

## 🛠️ Tecnologias

| Categoria | Tecnologias |
|-----------|-------------|
| **Backend** | Java 21, Spring Boot 3.5, Spring Cloud |
| **Arquitetura** | DDD, Hexagonal, Event Sourcing (Ledger), CQS, **Persistência Poliglota** |
| **SQL** | PostgreSQL (estado, transações ACID) |
| **NoSQL** | **Amazon DynamoDB** (Event Store) |
| **Cache** | Redis |
| **Mensageria** | Apache Kafka |
| **Patterns** | Feature Flags, BFF + GraphQL, Saga, Circuit Breaker, Partidas Dobradas |
| **DevOps** | Docker, GitHub Actions, SonarCloud |
| **Cloud** | AWS (LocalStack, DynamoDB), Kubernetes, Terraform |
| **Testes** | JUnit 5, Mockito, TestContainers, WireMock |
| **Observabilidade** | Prometheus, Grafana, Jaeger |

---

## 💰 Funcionalidades

### Fase 1: INSS Consignado
- ✅ Consulta de margem consignável
- ✅ Simulação de empréstimo (juros, parcelas, CET)
- ✅ Análise de crédito automática
- ✅ Averbação assíncrona (integração gov)
- ✅ Gestão de contratos
- ✅ Pagamento e quitação de parcelas
- ✅ Portabilidade e refinanciamento
- ✅ Multi-canal: Digital, Corban, Interno

### Fase 2: FGTS (futuro)
- ⏳ Antecipação saque-aniversário
- ⏳ Integração Mock Caixa

---

## 📁 Estrutura do Projeto

```
consignado-hub/
├── consignado-customer-service/      # Cadastro de clientes
├── consignado-simulation-service/    # Simulação de empréstimos
├── consignado-credit-service/        # Análise de crédito
├── consignado-loan-service/          # Gestão de contratos
├── consignado-payment-service/       # Pagamentos e parcelas
├── consignado-notification-service/  # Notificações
├── consignado-averbation-connector/  # Integração Gov (Mock)
├── consignado-api-gateway/           # Gateway + Auth
├── infrastructure/
│   ├── docker/
│   ├── kubernetes/
│   └── terraform/
└── doc/                              # Documentação de estudo
```

---

## 🚀 Como Executar

### Pré-requisitos

- Java 21
- Docker e Docker Compose
- Maven 3.9+

### Desenvolvimento Local

```bash
# Clone o repositório
git clone https://github.com/andrem91/consignado-hub.git
cd consignado-hub

# Suba a infraestrutura
docker-compose up -d postgres kafka redis

# Execute o serviço de clientes
cd consignado-customer-service
./mvnw spring-boot:run
```

### Testes

```bash
# Testes unitários e integração
./mvnw verify

# Mutation testing
./mvnw test-compile pitest:mutationCoverage

# Cobertura de código
./mvnw jacoco:report
```

---

## 📚 Documentação de Estudo

Este projeto faz parte de um plano de estudos para Java Pleno/Senior:

| Sprint | Foco | Estimativa |
|--------|------|------------|
| 0 | Setup + Value Objects + Modulith | 3-4 dias |
| 1 | Customer Service (Hexagonal) | 5-6 dias |
| 2 | Simulation Service (Cálculos) | 4-5 dias |
| 3 | Credit Analysis + Kafka | 5-6 dias |
| 4 | Loan Service + Averbation | 6-7 dias |
| 5 | Payment Service | 4-5 dias |
| 6 | API Gateway + Keycloak | 4-5 dias |
| 7 | DevOps + Observabilidade | 4-5 dias |
| 8-10 | Kubernetes + AWS + Terraform | 10-12 dias |
| 11 | Integração Final | 3-4 dias |

---

## 🤝 Contribuição

Este é um projeto de estudo pessoal, mas sugestões são bem-vindas!

---

## 📄 Licença

Este projeto está sob a licença MIT.
