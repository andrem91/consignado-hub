# 🏦 Projeto ConsignadoHub - Regras de Negócio

> Documentação completa das regras de negócio do crédito consignado INSS

---

## 📋 Índice

1. [Visão Geral](#visão-geral)
2. [Conceitos Fundamentais](#conceitos-fundamentais)
3. [Fluxos de Negócio](#fluxos-de-negócio)
4. [Regras de Cálculo](#regras-de-cálculo)
5. [Integrações](#integrações)
6. [Canais de Originação](#canais-de-originação)
7. [Entidades de Domínio](#entidades-de-domínio)
8. [Eventos de Domínio](#eventos-de-domínio)

---

## 1. Visão Geral

### O que é Crédito Consignado?

O crédito consignado é uma modalidade de empréstimo onde as parcelas são **descontadas diretamente** do benefício do INSS (aposentadoria ou pensão).

### Vantagens
- Taxas de juros mais baixas (risco menor para o banco)
- Aprovação facilitada (garantia de pagamento)
- Prazo estendido (até 96 meses)

### Público-alvo
- Aposentados do INSS
- Pensionistas do INSS
- Beneficiários do BPC/LOAS (regras especiais)

---

## 2. Conceitos Fundamentais

### 2.1 Margem Consignável

A **margem consignável** é o percentual máximo do benefício que pode ser comprometido com empréstimos.

| Tipo de Produto | Margem | Total |
|-----------------|--------|-------|
| **Empréstimo Pessoal** | 35% | |
| **Cartão de Crédito** | 5% | |
| **Cartão Benefício** | 5% | |
| **Total** | | **45%** |

**Exemplo:**
```
Benefício: R$ 2.000,00

Margem Empréstimo Pessoal: R$ 2.000 × 35% = R$ 700,00
Margem Cartão Crédito:     R$ 2.000 × 5%  = R$ 100,00
Margem Cartão Benefício:   R$ 2.000 × 5%  = R$ 100,00

Total Comprometível: R$ 900,00/mês
```

### 2.2 Teto de Juros

O Conselho Nacional de Previdência Social (CNPS) define o teto máximo de juros:

| Produto | Taxa Máxima (a.m.) | Taxa Máxima (a.a.) |
|---------|--------------------|--------------------|
| Empréstimo Pessoal | **1,66%** | ~21,7% |
| Cartão Consignado | **2,46%** | ~33,9% |

### 2.3 Prazo Máximo

| Período | Prazo Máximo |
|---------|--------------|
| Até jan/2025 | 84 meses (7 anos) |
| A partir fev/2025 | **96 meses (8 anos)** |

### 2.4 Regra dos 90 Dias

Para **novos beneficiários** (aposentados recentes):

| Período | Regra |
|---------|-------|
| Dias 1-90 | Só pode contratar no **banco pagador** |
| Dias 1-90 | **NÃO** pode fazer portabilidade |
| Dia 91+ | Liberado para qualquer banco |

---

## 3. Fluxos de Negócio

### 3.1 Fluxo de Nova Proposta

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│   Cliente    │────▶│  Simulação   │────▶│   Proposta   │
│  solicita    │     │  de valores  │     │   criada     │
└──────────────┘     └──────────────┘     └──────┬───────┘
                                                  │
                                                  ▼
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│   Crédito    │◀────│   Consulta   │◀────│  Validação   │
│   liberado   │     │   Dataprev   │     │   docs       │
└──────────────┘     └──────────────┘     └──────────────┘
                                                  │
                                                  ▼
                     ┌──────────────┐     ┌──────────────┐
                     │  Averbação   │◀────│   Análise    │
                     │  (registro)  │     │   crédito    │
                     └──────────────┘     └──────────────┘
```

#### Estados da Proposta

| Status | Descrição |
|--------|-----------|
| `DRAFT` | Proposta em rascunho |
| `PENDING_DOCUMENTS` | Aguardando documentos |
| `PENDING_MARGIN_CHECK` | Consultando margem Dataprev |
| `PENDING_CREDIT_ANALYSIS` | Em análise de crédito |
| `APPROVED` | Aprovada, aguardando averbação |
| `PENDING_AVERBATION` | Averbação em andamento |
| `AVERBATION_FAILED` | Falha na averbação (retry) |
| `CONTRACTED` | Contrato ativo |
| `REJECTED` | Proposta rejeitada |
| `CANCELLED` | Proposta cancelada |

### 3.2 Fluxo de Portabilidade

> Transferência de contrato de outro banco para o FinBank

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│   Cliente    │────▶│   Consulta   │────▶│   Proposta   │
│   solicita   │     │ saldo devedor│     │ portabilidade│
└──────────────┘     └──────────────┘     └──────┬───────┘
                                                  │
                                                  ▼
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│   Contrato   │◀────│  Liquidação  │◀────│   Análise    │
│   ativo      │     │ banco origem │     │   crédito    │
└──────────────┘     └──────────────┘     └──────────────┘
```

**Regras da Portabilidade:**
- NÃO precisa ter margem disponível
- Melhores condições (taxa/prazo)
- Quitação do banco original é automática

### 3.3 Fluxo de Refinanciamento

> Renegociação de contrato existente no mesmo banco

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│   Cliente    │────▶│   Consulta   │────▶│   Nova       │
│   solicita   │     │ saldo devedor│     │  simulação   │
└──────────────┘     └──────────────┘     └──────┬───────┘
                                                  │
                                                  ▼
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│   Novo       │◀────│  Encerrar    │◀────│   Aprovação  │
│   contrato   │     │  contrato    │     │              │
└──────────────┘     └──────────────┘     └──────────────┘
```

**Quando refinanciar:**
- Liberar margem para outro empréstimo
- Reduzir valor da parcela (estender prazo)
- Aproveitar queda de juros

---

## 4. Regras de Cálculo

### 4.1 Cálculo da Parcela (Price)

O consignado usa o sistema **Price** (parcelas fixas):

```
PMT = PV × [ i × (1 + i)^n ] / [ (1 + i)^n - 1 ]

Onde:
PMT = Valor da parcela
PV  = Valor presente (empréstimo)
i   = Taxa de juros mensal
n   = Número de parcelas
```

**Exemplo:**
```java
// Empréstimo de R$ 10.000,00 em 48 meses a 1,66% a.m.
double pv = 10000.00;
double i = 0.0166;
int n = 48;

double pmt = pv * (i * Math.pow(1 + i, n)) / (Math.pow(1 + i, n) - 1);
// Resultado: R$ 318,71
```

### 4.2 Cálculo do CET (Custo Efetivo Total)

O **CET** inclui todos os custos:
- Taxa de juros
- IOF
- Tarifas administrativas
- Seguro (se houver)

```
CET > Taxa nominal (sempre)
```

### 4.3 Cálculo do IOF

| Componente | Alíquota |
|------------|----------|
| IOF diário | 0,0082% ao dia (máx 365 dias) |
| IOF adicional | 0,38% (fixo) |

```java
// Exemplo para 30 dias
double iofDiario = valorEmprestimo * 0.000082 * 30;
double iofAdicional = valorEmprestimo * 0.0038;
double iofTotal = iofDiario + iofAdicional;
```

### 4.4 Valor Máximo do Empréstimo

O valor máximo depende da **margem disponível** e do **prazo**:

```
Valor Máximo = Margem Disponível × Fator de Multiplicação

Fator = [ (1 + i)^n - 1 ] / [ i × (1 + i)^n ]
```

**Exemplo:**
```
Margem disponível: R$ 500,00
Taxa: 1,66% a.m.
Prazo: 96 meses

Fator = 52,46
Valor Máximo = R$ 500 × 52,46 = R$ 26.230,00
```

### 4.5 Juros Pro-Rata (Apropriação Diária)

Os juros são **apropriados diariamente**, não no vencimento da parcela. Isso significa que:

| Conceito | Descrição |
|----------|-----------|
| **Saldo Devedor** | Atualizado diariamente com juros |
| **Pagamento Antecipado** | Cliente paga menos (juros até a data do pagamento) |
| **Pagamento Atrasado** | Cliente paga mais (juros de mora + multa) |

#### Fórmula de Juros Diários

```
Taxa Diária = (1 + Taxa Mensal)^(1/30) - 1

Juros do Dia = Saldo Devedor × Taxa Diária
```

**Exemplo:**
```
Taxa mensal: 1,66%
Taxa diária: (1 + 0,0166)^(1/30) - 1 = 0,0548% ao dia

Saldo devedor: R$ 10.000,00
Juros do dia: R$ 10.000 × 0,000548 = R$ 5,48
```

### 4.6 Quitação Antecipada

O cliente pode quitar o contrato antecipadamente com **desconto dos juros futuros**.

#### Cálculo do Valor de Quitação

```
Valor Quitação = Saldo Devedor + Juros Pro-Rata até a data

Onde:
- Saldo Devedor = Principal não amortizado
- Juros Pro-Rata = Juros acumulados desde o último pagamento
```

**Regras:**
- Cliente tem direito ao desconto (Lei 14.690/2023)
- Desconto proporcional às parcelas não vencidas
- IOF já pago NÃO é devolvido

#### Exemplo Prático

```
Contrato: R$ 10.000 em 48 parcelas de R$ 318,71
Quitação na parcela 12:

- Parcelas pagas: 12 × R$ 318,71 = R$ 3.824,52
- Principal amortizado: ~R$ 2.500
- Saldo devedor: ~R$ 7.500
- Juros pro-rata (15 dias): ~R$ 60
- Valor de quitação: ~R$ 7.560 (ao invés de 36 × R$ 318,71 = R$ 11.473,56)
```

### 4.7 Amortização Extraordinária

O cliente pode pagar um valor extra para **reduzir o saldo devedor**:

| Opção | Efeito |
|-------|--------|
| **Reduzir Prazo** | Mantém parcela, termina antes |
| **Reduzir Parcela** | Mantém prazo, parcela menor |

### 4.8 Mora e Multa (Atraso)

| Componente | Valor |
|------------|-------|
| **Multa** | Até 2% sobre a parcela |
| **Juros de Mora** | 1% ao mês (pro-rata diário) |
| **Correção Monetária** | IPCA/IGPM (se previsto em contrato) |

---

## 4.9 Operações Adicionais

### Portabilidade

> Transferência de contrato entre bancos

| Aspecto | Descrição |
|---------|-----------|
| **Objetivo** | Conseguir melhores condições (taxa/prazo) |
| **Margem** | NÃO consome margem adicional |
| **Processo** | Banco destino quita o banco origem |
| **Prazo** | Até 5 dias úteis |

### Refinanciamento

> Renegociação no mesmo banco

| Aspecto | Descrição |
|---------|-----------|
| **Objetivo** | Liberar margem ou reduzir parcela |
| **Margem** | Pode liberar parte da margem |
| **Restrição** | Aguardar 90 dias entre refinanciamentos |
| **Troco** | Pode receber valor adicional |

### Carência

> Período inicial sem pagamento de parcelas

| Aspecto | Descrição |
|---------|-----------|
| **Prazo típico** | 30 a 90 dias |
| **Juros** | Acumulam durante a carência |
| **Uso** | Promoções, situações especiais |

### Seguro Prestamista

> Cobertura em caso de morte ou invalidez

| Aspecto | Descrição |
|---------|-----------|
| **Cobertura** | Quita o saldo devedor |
| **Beneficiários** | Herdeiros não herdam a dívida |
| **Custo** | Incluso no CET |
| **Obrigatório** | Não, mas geralmente oferecido |

### Renegociação (Workout)

> Para clientes inadimplentes

| Aspecto | Descrição |
|---------|-----------|
| **Objetivo** | Regularizar situação |
| **Opções** | Desconto, parcelamento, carência |
| **Impacto** | Pode alterar condições originais |

### Bloqueio/Desbloqueio

> Suspensão temporária do contrato

| Motivo | Ação |
|--------|------|
| **Ordem judicial** | Bloqueio compulsório |
| **Suspeita de fraude** | Bloqueio preventivo |
| **Óbito do beneficiário** | Bloqueio + acionamento seguro |
| **Regularização** | Desbloqueio após análise |

---

## 5. Integrações

### 5.1 Dataprev (INSS)

A Dataprev é a empresa de TI do governo que gerencia os dados do INSS.

#### Endpoints (Mock no projeto)

| Operação | Método | Descrição |
|----------|--------|-----------|
| Consultar Margem | `GET` | Retorna margem disponível |
| Averbar Contrato | `POST` | Registra o contrato |
| Cancelar Averbação | `DELETE` | Cancela registro |
| Consultar Averbações | `GET` | Lista contratos ativos |

#### Dados Retornados

```json
{
  "beneficio": {
    "numero": "1234567890",
    "tipoCredito": "APOSENTADORIA_POR_IDADE",
    "valorBeneficio": 2000.00,
    "dataInicio": "2020-01-15"
  },
  "margem": {
    "pessoal": {
      "limite": 700.00,
      "utilizado": 200.00,
      "disponivel": 500.00
    },
    "cartao": {
      "limite": 100.00,
      "utilizado": 0.00,
      "disponivel": 100.00
    }
  },
  "restricoes": []
}
```

### 5.2 Características da Integração

| Aspecto | Comportamento |
|---------|---------------|
| **Latência** | Alta (2-10 segundos) |
| **Disponibilidade** | Instável (horário comercial melhor) |
| **Modo** | **Assíncrono** via Kafka |
| **Retry** | Exponential backoff |
| **Cache** | Redis (TTL 5 min para margem) |

---

## 6. Canais de Originação

### 6.1 Canais Disponíveis

| Canal | Código | Descrição |
|-------|--------|-----------|
| **Digital** | `DIGITAL` | 100% app, self-service |
| **Corban** | `CORBAN` | Correspondente bancário (parceiro) |
| **Interno** | `INTERNAL` | Agência, gerente, backoffice |

### 6.2 Fluxo por Canal

#### Digital (App)
```
Cliente → App → Simulação → Selfie + Docs → Aprovação automática → Crédito
```

#### Corban
```
Cliente → Corban → Sistema Corban → API Proposta → Análise → Averbação
         (físico)   (parceiro)      (FinBank)
```

#### Interno
```
Cliente → Agência → Gerente → Sistema → Análise → Averbação
         (físico)  (humano)   (FinBank)
```

### 6.3 Comissionamento (Corban)

O correspondente bancário recebe comissão por cada operação:

| Tipo | Comissão Típica |
|------|-----------------|
| Empréstimo novo | 2-5% do valor |
| Refinanciamento | 1-3% do valor |
| Portabilidade | 1-2% do valor |

---

## 7. Entidades de Domínio

### 7.1 Mapa de Entidades

```
┌─────────────────────────────────────────────────────────────────┐
│                        CUSTOMER BOUNDED CONTEXT                  │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────┐       ┌──────────────┐                        │
│  │   Customer   │───────│   Document   │                        │
│  │  (Aggregate) │       │              │                        │
│  └──────┬───────┘       └──────────────┘                        │
│         │                                                        │
│         │ 1:1                                                    │
│         ▼                                                        │
│  ┌──────────────┐       ┌──────────────┐                        │
│  │   Benefit    │───────│    Margin    │                        │
│  │   (Entity)   │  1:1  │ (ValueObject)│                        │
│  └──────────────┘       └──────────────┘                        │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                        LOAN BOUNDED CONTEXT                      │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────┐       ┌──────────────┐                        │
│  │   Proposal   │───────│  Simulation  │                        │
│  │  (Aggregate) │  1:1  │              │                        │
│  └──────┬───────┘       └──────────────┘                        │
│         │                                                        │
│         │ evolui para                                            │
│         ▼                                                        │
│  ┌──────────────┐       ┌──────────────┐                        │
│  │   Contract   │───────│ Installment  │                        │
│  │  (Aggregate) │  1:N  │   (Entity)   │                        │
│  └──────┬───────┘       └──────────────┘                        │
│         │                                                        │
│         │ 1:1                                                    │
│         ▼                                                        │
│  ┌──────────────┐                                                │
│  │  Averbation  │                                                │
│  │   (Entity)   │                                                │
│  └──────────────┘                                                │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
```

### 7.2 Value Objects

| Value Object | Atributos | Validações |
|--------------|-----------|------------|
| `CPF` | value: String | 11 dígitos, dígitos verificadores |
| `Money` | amount: BigDecimal | >= 0, 2 casas decimais |
| `BenefitNumber` | value: String | 10 dígitos |
| `MarginPercentage` | value: BigDecimal | 0-100% |
| `InterestRate` | monthly: BigDecimal | > 0, <= 1.66% |
| `LoanTerm` | months: Integer | 1-96 |
| `CET` | annual: BigDecimal | > taxa nominal |

### 7.3 Entities

#### Customer (Aggregate Root)
```java
public class Customer {
    private CustomerId id;
    private CPF cpf;
    private String fullName;
    private LocalDate birthDate;
    private Benefit benefit;
    private List<Document> documents;
    private CustomerStatus status;
    private LocalDateTime createdAt;
}
```

#### Contract (Aggregate Root)
```java
public class Contract {
    private ContractId id;
    private CustomerId customerId;
    private Money principalAmount;
    private InterestRate interestRate;
    private LoanTerm term;
    private Money installmentValue;
    private CET cet;
    private ContractStatus status;
    private Averbation averbation;
    private List<Installment> installments;
    private OperationType operationType; // NEW, REFINANCING, PORTABILITY
    private Channel channel;
    private LocalDateTime contractedAt;
}
```

---

## 8. Eventos de Domínio

### 8.1 Lista de Eventos

| Evento | Produtor | Consumidores |
|--------|----------|--------------|
| `ProposalCreated` | Proposal Service | Credit Analysis |
| `CreditApproved` | Credit Analysis | Loan Service |
| `CreditRejected` | Credit Analysis | Notification |
| `AverbationRequested` | Loan Service | Averbation Connector |
| `AverbationCompleted` | Averbation Connector | Loan Service, Payment |
| `AverbationFailed` | Averbation Connector | Loan Service |
| `ContractActivated` | Loan Service | Notification |
| `InstallmentDue` | Payment Service | Notification |
| `PaymentReceived` | Payment Service | Loan Service |
| `ContractSettled` | Loan Service | Notification |

### 8.2 Estrutura de Evento

```json
{
  "eventId": "uuid",
  "eventType": "ProposalCreated",
  "aggregateId": "proposal-123",
  "aggregateType": "Proposal",
  "occurredAt": "2025-01-01T10:00:00Z",
  "payload": {
    "customerId": "customer-456",
    "amount": 10000.00,
    "term": 48,
    "channel": "DIGITAL"
  },
  "metadata": {
    "correlationId": "uuid",
    "userId": "user-789"
  }
}
```

---

## 📚 Referências

- [INSS - Margem Consignável](https://www.gov.br/inss)
- [Dataprev - Portal do Desenvolvedor](https://www.gov.br/dataprev)
- [CNPS - Resoluções](https://www.gov.br/previdencia)

---

> **Próximo:** [ROTEIRO_IMPLEMENTACAO.md](ROTEIRO_IMPLEMENTACAO.md)
