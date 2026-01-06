# 🚀 Roteiro de Implementação: ConsignadoHub

> **Sistema de Crédito Consignado INSS** - Baseado na Stack de grandes instituições financeiras
> 
> ⚡ **Metodologia Ágil Real**: Entregas incrementais, bugs simulados, features urgentes

---

## 📌 Filosofia

### Desenvolvimento Ágil de Verdade

| Princípio | Como Aplicamos |
|-----------|----------------|
| **Entregar rápido** | MVP em cada sprint → deploy |
| **Iterar** | Refatorar quando necessário |
| **Feedback** | Bugs descobertos via métricas |
| **Simplicidade** | Hardcode primeiro, parametrizar depois |

### O que você vai aprender

- ✅ Começar simples e evoluir
- ✅ Lidar com bugs em "produção"
- ✅ Responder a features urgentes
- ✅ Refatorar sob pressão
- ✅ Ter histórias reais para entrevistas

---

## 🎮 Modo Mentor

Este projeto simula um ambiente **real** de desenvolvimento:

| Tipo | O que Acontece |
|------|----------------|
| 🐛 **Bugs** | Código de exemplo tem bugs sutis para você descobrir |
| 🔴 **Incidentes** | A IA injeta problemas de "produção" |
| 📋 **Features** | Requisitos novos aparecem no meio da sprint |
| 🔧 **Refactor** | Dívida técnica planejada exige evolução |

### Arquivos do Sistema

| Arquivo | Descrição |
|---------|-----------|
| `ESTADO_PROJETO.md` | Seu progresso, incidentes, contexto |
| `INCIDENTES.md` | Cenários planejados (NÃO LEIA!) |

### Nova Conversa?

```
/modo-mentor
```

---

## 🗓️ Roadmap Ágil

```
Sprint 0 ████░░░░░░░░░░░░░░░░░░░░░░░░  MVP Setup           → DEPLOY LOCAL
Sprint 1 ░░░░████░░░░░░░░░░░░░░░░░░░░  MVP Customer        → DEPLOY DOCKER
Sprint 2 ░░░░░░░░████░░░░░░░░░░░░░░░░  MVP Simulation      → DEPLOY
Sprint 3 ░░░░░░░░░░░░████░░░░░░░░░░░░  Kafka + INCIDENTE   → REFACTOR
Sprint 4 ░░░░░░░░░░░░░░░░████░░░░░░░░  Loan + INCIDENTE    → REFACTOR  
Sprint 5 ░░░░░░░░░░░░░░░░░░░░████░░░░  Payment + FEATURE   → REFACTOR
Sprint 6 ░░░░░░░░░░░░░░░░░░░░░░░░████  Gateway + FEATURE   → REFACTOR
Sprint 7+ ░░░░░░░░░░░░░░░░░░░░░░░░░░██  DevOps/K8s/AWS
```

### O que muda em cada sprint

| Sprint | Entrega MVP | Bug/Incidente | Refactor/Pattern |
|--------|-------------|---------------|------------------|
| 0 | CPF + Money | 🐛 CPF com zeros | - |
| 1 | Customer básico | 🐛 N+1 query | CQS (interfaces separadas) |
| 2 | Simulation básica | 🐛 IOF errado | - |
| 3 | Kafka + Credit | 🔴 Duplicação | Idempotência |
| 4 | Loan + Averbação | 🔴 Circuit breaker | Resilience4j, Saga |
| 5 | Payment | 📋 Margem config | External config |
| 6 | Gateway + **Feature Flags** | 📋 Multi-canal | Strategy, **LaunchDarkly** |
| 7 | Docker/K8s + **BFF GraphQL** | - | **Spring for GraphQL** |
| 8 | AWS LocalStack | - | **Lambda Mock Dataprev** |

---

## 🏗️ Arquiteturas e Patterns Adicionais

### ✅ Planejados para o Projeto

| Pattern | Sprint | O que é | Por que usar |
|---------|--------|---------|--------------|
| **CQS** | 1+ | Separar interfaces Read/Write (mesmo banco) | Clareza no código, prepara para CQRS |
| **Event Sourcing + Ledger** | 4 | Eventos de negócio + eventos de saldo | Conciliação contábil, auditoria |
| **Feature Flags** | 6 | Ligar/desligar features sem redeploy | Deploy seguro, rollout gradual |
| **BFF + GraphQL** | 7 | API específica para Mobile/Web | Experience API vs Domain API |
| **Serverless** | 8 | Lambda para Mock Dataprev | Simula webhook externo |
| **Canary Release** | 8 | Deploy gradual (1% → 100%) | Validar com % de usuários |

### ❌ Não Planejados (mas saiba explicar)

| Pattern | Por que não | Alternativa |
|---------|-------------|-------------|
| **CQRS Puro** | Requer bancos separados | CQS lógico (mesma infra) |
| **Service Mesh** | Muito pesado localmente | Mencionar Istio, não implementar |
| **Strangler Fig** | Projeto greenfield (sem legado) | Saber explicar em entrevista |

### 💡 CQS vs CQRS

```
CQS (Vamos usar):                 CQRS (Não usar):
┌─────────────────────────┐       ┌─────────────────────────────────┐
│   CadastrarClienteUseCase │       │   Write Model    │   Read Model │
│   (Command - Write)       │       │   PostgreSQL     │   MongoDB    │
├───────────────────────────┤       │       ↓          │       ↓      │
│   BuscarClienteQuery      │       │   Event Bus  ←───────────────── │
│   (Query - Read)          │       └─────────────────────────────────┘
└───────────────────────────┘       Complexidade: 🔴 Alta
     ↓ Mesmo banco ↓                Complexidade: 🟢 Baixa (CQS)
    PostgreSQL
```

### 🏦 Event Sourcing + Ledger (Sprint 4)

#### Decisão Arquitetural

| Módulo | Arquitetura | Justificativa |
|--------|-------------|---------------|
| **CustomerService** | DDD Clássico | Cadastro não precisa auditoria contábil |
| **ContractService** | Event Sourcing + Ledger | Contrato financeiro PRECISA rastreabilidade |
| **LedgerService** | Partidas Dobradas | Conciliação contábil profissional |

#### Por que usar apenas no ContractService?

> *"Não usei Event Sourcing em tudo porque seria complexidade desnecessária. 
> Usei apenas no módulo Financeiro/Contratos para garantir a conciliação contábil."*

---

### 📚 Partidas Dobradas (Double-Entry Bookkeeping)

**Regra de Ouro da Contabilidade:**
> *"Para todo Débito existe um Crédito de igual valor. A soma total deve ser ZERO."*

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                    PARTIDAS DOBRADAS                                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│   Evento: ContratoAverbado (R$ 1.000)                                       │
│                                                                              │
│   ┌────────────────────────────────────────────────────────────────────┐    │
│   │                    TransacaoContabil                                │    │
│   ├────────────────────────────────────────────────────────────────────┤    │
│   │  Lançamento 1: DÉBITO  Carteira_Emprestimos    R$ 1.000,00         │    │
│   │  Lançamento 2: CRÉDITO Obrigacoes_Liberar      R$ 1.000,00         │    │
│   ├────────────────────────────────────────────────────────────────────┤    │
│   │  VALIDAÇÃO: Débito (1000) - Crédito (1000) = 0 ✓                   │    │
│   └────────────────────────────────────────────────────────────────────┘    │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

### 📋 Plano de Contas (Chart of Accounts)

```java
// Enum com as contas contábeis do sistema
public enum ContaContabil {
    
    // ATIVO (recursos do banco)
    ATIVO_CAIXA("1.1.01", "Caixa", TipoConta.ATIVO),
    ATIVO_CARTEIRA_CONSIGNADO("1.2.01", "Carteira de Empréstimos", TipoConta.ATIVO),
    
    // PASSIVO (obrigações do banco)
    PASSIVO_OBRIGACOES_LIBERAR("2.1.01", "Obrigações a Liberar", TipoConta.PASSIVO),
    
    // RECEITA (lucro do banco)
    RECEITA_JUROS("4.1.01", "Receita de Juros", TipoConta.RECEITA),
    RECEITA_TARIFAS("4.1.02", "Receita de Tarifas", TipoConta.RECEITA);
    
    private final String codigo;
    private final String descricao;
    private final TipoConta tipo;
}

public enum TipoConta {
    ATIVO,    // Débito aumenta, Crédito diminui
    PASSIVO,  // Crédito aumenta, Débito diminui
    RECEITA,  // Crédito aumenta
    DESPESA   // Débito aumenta
}
```

---

### 🔄 Fluxo de Eventos Contábeis

#### Cenário: Contratação de R$ 1.000,00

**Evento 1: ContratoAverbado** (averbação aprovada)
```
Débito:  ATIVO_CARTEIRA_CONSIGNADO  R$ 1.000,00 (ativo ↑)
Crédito: PASSIVO_OBRIGACOES_LIBERAR R$ 1.000,00 (passivo ↑)
```

**Evento 2: TEDEnviada** (dinheiro liberado)
```
Débito:  PASSIVO_OBRIGACOES_LIBERAR R$ 1.000,00 (passivo ↓)
Crédito: ATIVO_CAIXA                R$ 1.000,00 (ativo ↓)
```

**Evento 3: ParcelaRecebida** (cliente pagou R$ 100: R$ 80 principal + R$ 20 juros)
```
Débito:  ATIVO_CAIXA                R$ 100,00 (entrou dinheiro)
Crédito: ATIVO_CARTEIRA_CONSIGNADO  R$  80,00 (dívida ↓)
Crédito: RECEITA_JUROS              R$  20,00 (lucro ↑)
```

---

### 💻 Implementação Java (Sprint 4)

```java
// Tipo de movimento contábil
public enum TipoLancamento {
    DEBITO,
    CREDITO
}

// Lançamento atômico (Value Object)
public record Lancamento(
    ContaContabil conta,
    TipoLancamento tipo,
    Dinheiro valor
) {}

// Transação Contábil (Partida Dobrada)
public class TransacaoContabil {
    
    private final UUID id;
    private final ContratoId contratoId;
    private final String descricao;
    private final List<Lancamento> lancamentos;
    private final LocalDateTime momento;
    
    public TransacaoContabil(ContratoId contratoId, String descricao, 
                              List<Lancamento> lancamentos) {
        validarPartidasDobradas(lancamentos);  // ← Invariante
        this.id = UUID.randomUUID();
        this.contratoId = contratoId;
        this.descricao = descricao;
        this.lancamentos = List.copyOf(lancamentos);
        this.momento = LocalDateTime.now();
    }
    
    // INVARIANTE: Débito DEVE ser igual a Crédito
    private void validarPartidasDobradas(List<Lancamento> lancamentos) {
        BigDecimal totalDebito = lancamentos.stream()
            .filter(l -> l.tipo() == TipoLancamento.DEBITO)
            .map(l -> l.valor().valor())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        BigDecimal totalCredito = lancamentos.stream()
            .filter(l -> l.tipo() == TipoLancamento.CREDITO)
            .map(l -> l.valor().valor())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        if (totalDebito.compareTo(totalCredito) != 0) {
            throw new ContabilidadeDesbalanceadaException(
                "Débito (" + totalDebito + ") != Crédito (" + totalCredito + ")"
            );
        }
    }
}

// Factory para criar transações comuns
public class TransacaoContabilFactory {
    
    public static TransacaoContabil contratoAverbado(ContratoId id, Dinheiro valor) {
        return new TransacaoContabil(id, "Contrato Averbado", List.of(
            new Lancamento(ContaContabil.ATIVO_CARTEIRA_CONSIGNADO, DEBITO, valor),
            new Lancamento(ContaContabil.PASSIVO_OBRIGACOES_LIBERAR, CREDITO, valor)
        ));
    }
    
    public static TransacaoContabil tedEnviada(ContratoId id, Dinheiro valor) {
        return new TransacaoContabil(id, "TED Enviada", List.of(
            new Lancamento(ContaContabil.PASSIVO_OBRIGACOES_LIBERAR, DEBITO, valor),
            new Lancamento(ContaContabil.ATIVO_CAIXA, CREDITO, valor)
        ));
    }
    
    public static TransacaoContabil parcelaRecebida(ContratoId id, 
            Dinheiro principal, Dinheiro juros) {
        Dinheiro total = principal.somar(juros);
        return new TransacaoContabil(id, "Parcela Recebida", List.of(
            new Lancamento(ContaContabil.ATIVO_CAIXA, DEBITO, total),
            new Lancamento(ContaContabil.ATIVO_CARTEIRA_CONSIGNADO, CREDITO, principal),
            new Lancamento(ContaContabil.RECEITA_JUROS, CREDITO, juros)
        ));
    }
}
```

---

### 🔍 Conciliação e Auditoria

```java
// Consultar saldo de uma conta
public interface LedgerQuery {
    Dinheiro saldoConta(ContaContabil conta);
    List<Lancamento> extratoConta(ContaContabil conta, LocalDate inicio, LocalDate fim);
    boolean verificarIntegridade();  // Soma de todos débitos = soma de todos créditos
}
```

#### Fluxo Kafka

```
ContractService                    LedgerService
      │                                  │
      │  ContratoAverbadoEvent           │
      ├──────────────────────────────────►
      │                                  │ ← Cria TransacaoContabil
      │                                  │ ← Valida Partidas Dobradas
      │     TransacaoRegistradaEvent     │ ← Persiste no Event Store (DynamoDB)
      │◄──────────────────────────────────
      │                                  │
```

---

### 🗄️ DynamoDB Event Store (Sprint 4)

#### Decisão de Persistência

| Dado | Banco | Justificativa |
|------|-------|---------------|
| **Clientes** | PostgreSQL | Queries complexas, JOINs, ACID |
| **Contratos (estado)** | PostgreSQL | Transações, relacionamentos |
| **Saldos Contábeis** | PostgreSQL | ACID para conciliação |
| **Eventos (histórico)** | **DynamoDB** | Append-only, escala infinita, barato |

#### Por que DynamoDB para Event Store?

| Aspecto | PostgreSQL | DynamoDB |
|---------|------------|----------|
| **Padrão de acesso** | Queries complexas | Key-value (PK + SK) |
| **Escalabilidade** | Manual (DBA) | Automática |
| **Custo** | Servidor fixo | Pay-per-use |
| **Append-only** | 🟡 Funciona | ✅ Perfeito |

#### Modelagem DynamoDB

```
Tabela: consignado-events

┌─────────────────────────────────────────────────────────────────────────────┐
│ PK (Partition Key)         │ SK (Sort Key)    │ Attributes                  │
├────────────────────────────┼──────────────────┼─────────────────────────────┤
│ CONTRATO#550e8400-e29b...  │ 0000000001       │ eventType, timestamp, payload│
│ CONTRATO#550e8400-e29b...  │ 0000000002       │ eventType, timestamp, payload│
│ CONTRATO#550e8400-e29b...  │ 0000000003       │ eventType, timestamp, payload│
└─────────────────────────────────────────────────────────────────────────────┘
```

#### Implementação Java

```java
// Entidade DynamoDB
@DynamoDbBean
public class EventStoreModel {

    private String pk;           // CONTRATO#uuid
    private String sk;           // 0000000001 (versão formatada)
    private String eventType;    // ContratoAverbado
    private Instant timestamp;
    private String payload;      // JSON do evento
    private String metadata;     // { "usuario": "andre" }

    @DynamoDbPartitionKey
    public String getPk() { return pk; }
    
    @DynamoDbSortKey
    public String getSk() { return sk; }
}

// Repositório Event Store
@Repository
@RequiredArgsConstructor
public class DynamoEventStore {

    private final DynamoDbEnhancedClient dynamoClient;
    private final ObjectMapper objectMapper;

    public void salvarEventos(UUID aggregateId, List<DomainEvent> eventos, int versaoAtual) {
        var table = getTable();
        
        List<TransactWriteItem> items = new ArrayList<>();
        
        for (DomainEvent evento : eventos) {
            versaoAtual++;
            
            EventStoreModel model = new EventStoreModel();
            model.setPk("CONTRATO#" + aggregateId);
            model.setSk(String.format("%010d", versaoAtual));  // 0000000001
            model.setEventType(evento.getClass().getSimpleName());
            model.setTimestamp(evento.occurredAt());
            model.setPayload(toJson(evento));

            // Transação atômica com verificação de duplicata
            items.add(TransactWriteItem.builder()
                .put(Put.builder()
                    .tableName("consignado-events")
                    .item(toAttributeMap(model))
                    .conditionExpression("attribute_not_exists(pk)")
                    .build())
                .build());
        }
        
        dynamoClient.transactWriteItems(TransactWriteItemsRequest.builder()
            .transactItems(items)
            .build());
    }

    public List<DomainEvent> carregarEventos(UUID aggregateId) {
        var table = getTable();
        
        // Query ordenada por SK automaticamente
        Key key = Key.builder()
            .partitionValue("CONTRATO#" + aggregateId)
            .build();
        
        return table.query(r -> r.queryConditional(
                QueryConditional.keyEqualTo(key)))
            .items().stream()
            .map(this::toDomainEvent)
            .toList();
    }
}
```

#### Uso no ContractService

```java
public void averbarContrato(UUID contratoId) {
    // 1. Carrega histórico do DynamoDB (Event Sourcing)
    List<DomainEvent> historico = dynamoEventStore.carregarEventos(contratoId);
    
    // 2. Reconstroi o Contrato em memória (Replay)
    Contrato contrato = Contrato.reconstruir(historico);
    
    // 3. Executa regra de negócio (gera novo evento)
    contrato.averbar();
    
    // 4. Salva novo evento no DynamoDB
    dynamoEventStore.salvarEventos(
        contrato.getId(), 
        contrato.getChanges(), 
        contrato.getVersion()
    );
    
    // 5. Publica evento no Kafka para outros serviços
    kafkaTemplate.send("contratos-topic", contrato.getChanges());
}
```

#### Arquitetura Híbrida Final

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                         ARQUITETURA HÍBRIDA                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│   CustomerService        ContractService       LedgerService                │
│   ┌────────────┐        ┌────────────┐        ┌────────────┐               │
│   │ PostgreSQL │        │ PostgreSQL │        │ PostgreSQL │               │
│   │ (Clientes) │        │ (Contratos)│        │ (Saldos)   │               │
│   │  DDD + CQS │        │  + Estado  │        │  + ACID    │               │
│   └────────────┘        └─────┬──────┘        └──────┬─────┘               │
│                               │                       │                     │
│                               ▼                       ▼                     │
│                    ┌─────────────────────────────────────────┐             │
│                    │            DynamoDB                      │             │
│                    │       consignado-events                  │             │
│                    │   (Event Store - Append Only)           │             │
│                    └─────────────────────────────────────────┘             │
│                                       │                                     │
│                               ┌───────┴───────┐                            │
│                               │ Apache Kafka  │                            │
│                               └───────────────┘                            │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

#### Argumento de Entrevista

> *"Usei arquitetura híbrida: PostgreSQL para estado transacional (ACID),
> DynamoDB para Event Store (append-only, barato, escala infinita).
> Exatamente como Nubank e Itaú fazem em produção."*


---

## 🔧 Dívida Técnica Planejada

### Configurações

| MVP (Hardcode) | Evolução | Quando |
|----------------|----------|--------|
| Taxa `1.66%` no código | Parameter Store | Sprint 5 |
| Canal `CORBAN` fixo | Token dinâmico | Sprint 6 |
| Margem `35%` constante | Config table | Sprint 5 |
| Mock inline | Feign Client | Sprint 4 |
| Features hardcoded | Feature Flags | Sprint 6 |

### Patterns

| Início | Evolução | Gatilho |
|--------|----------|---------|
| Classe concreta | Strategy + Factory | Novo parceiro |
| `if/else` | Strategy Pattern | 3º canal |
| `.yml` | AWS Parameter Store | Multi-ambiente |
| Use Case único | CQS (Command/Query) | Clareza |

---

## 📐 Regras de Desenvolvimento

### Metodologia TDD

Todos os componentes devem ser implementados seguindo **Test-Driven Development**:

1. **Red** - Escrever teste que falha
2. **Green** - Implementar mínimo para passar
3. **Refactor** - Melhorar código mantendo testes verdes

### Nomenclatura

| Elemento | Idioma | Exemplo |
|----------|--------|---------|
| **Classes de domínio** | 🇧🇷 Português | `Beneficio`, `Cliente`, `Dinheiro` |
| **Atributos de domínio** | 🇧🇷 Português | `valorMensal`, `dataInicio` |
| **Métodos de negócio** | 🇧🇷 Português | `calcularMargem()`, `formatar()` |
| **Factory methods** | 🇺🇸 Inglês | `of()`, `novo()` |
| **Patterns técnicos** | 🇺🇸 Inglês | `Repository`, `Service`, `@Getter` |
| **Exceções** | Híbrido | `InvalidCPFException` |

### Arquitetura Hexagonal

```
┌─────────────────────────────────────────────────────────┐
│                      DOMAIN                              │
│  - Sem dependências externas                            │
│  - Sem @Entity JPA, sem @Service Spring                 │
│  - Value Objects como records imutáveis                 │
│  - Entities com validação fail-fast no construtor       │
├─────────────────────────────────────────────────────────┤
│                    APPLICATION                           │
│  - Ports (interfaces)                                   │
│  - Services (implementam use cases)                     │
├─────────────────────────────────────────────────────────┤
│                     ADAPTERS                             │
│  - JPA Entities separadas das Domain Entities           │
│  - Controllers, Repositories, Mappers                   │
│  - Anotações de framework permitidas                    │
└─────────────────────────────────────────────────────────┘
```

### Validações em Entities

```java
public Beneficio(NumeroBeneficio numero, TipoBeneficio tipo, ...) {
    // 1. Fail-fast: null checks primeiro
    if (numero == null) throw new InvalidBeneficioException("...");
    
    // 2. Regras de negócio
    if (dataInicio.isAfter(LocalDate.now())) throw new ...
    
    // 3. Atribuições
    this.numero = numero;
}
```

---

## 🎯 Sprint 0: MVP Setup

> **Objetivo:** Ter o projeto rodando com os VOs essenciais

### 📋 User Stories

```
Como desenvolvedor
Quero criar a estrutura do projeto
Para começar a implementar as features
```

```
Como sistema
Quero validar CPF do cliente
Para garantir dados corretos
```

```
Como sistema
Quero representar valores monetários
Para fazer cálculos financeiros
```

### 📖 Estude Primeiro

| Tópico | Arquivo | Tempo |
|--------|---------|-------|
| Records Java 21 | MODULO_02 | 30min |
| Value Objects | MODULO_03 | 1h |

### 🛠️ Implementação

#### Dia 1: Setup do Projeto

**1. Criar estrutura:**

```bash
mkdir consignado-hub
cd consignado-hub
git init
```

**2. Estrutura MVP (mínima):**

```
consignado-hub/
├── consignado-customer-service/
│   ├── src/main/java/com/consignadohub/customer/
│   │   └── domain/
│   │       └── vo/          ← Value Objects
│   └── pom.xml
├── pom.xml
└── docker-compose.yml
```

**3. pom.xml (parent) MVP:**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.0</version>
    </parent>
    
    <groupId>com.consignadohub</groupId>
    <artifactId>consignado-hub</artifactId>
    <version>0.1.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    
    <modules>
        <module>consignado-customer-service</module>
    </modules>
    
    <properties>
        <java.version>21</java.version>
    </properties>
</project>
```

**4. docker-compose.yml MVP:**

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15-alpine
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: consignado
      POSTGRES_PASSWORD: consignado
      POSTGRES_DB: consignado_db
```

#### Dia 2: CPF Value Object (TDD)

**⚠️ ATENÇÃO: O código abaixo tem um bug sutil. Implemente e veja se consegue encontrar!**

**1. Teste PRIMEIRO (Red):**

```java
// src/test/java/com/consignadohub/customer/domain/vo/CPFTest.java

@DisplayName("CPF Value Object")
class CPFTest {

    @Test
    @DisplayName("Deve criar CPF válido")
    void deveCriarCpfValido() {
        CPF cpf = new CPF("40453683886");
        assertThat(cpf.value()).isEqualTo("40453683886");
    }

    @Test
    @DisplayName("Deve remover formatação")
    void deveRemoverFormatacao() {
        CPF cpf = new CPF("404.536.838-86");
        assertThat(cpf.value()).isEqualTo("40453683886");
    }

    @Test
    @DisplayName("Deve formatar para exibição")
    void deveFormatar() {
        CPF cpf = new CPF("40453683886");
        assertThat(cpf.formatted()).isEqualTo("404.536.838-86");
    }

    @Test
    @DisplayName("Deve rejeitar CPF inválido")
    void deveRejeitarInvalido() {
        assertThatThrownBy(() -> new CPF("12345678901"))
            .isInstanceOf(InvalidCPFException.class);
    }

    @Test
    @DisplayName("Deve rejeitar nulo ou vazio")
    void deveRejeitarNuloOuVazio() {
        assertThatThrownBy(() -> new CPF(null))
            .isInstanceOf(InvalidCPFException.class);
        assertThatThrownBy(() -> new CPF(""))
            .isInstanceOf(InvalidCPFException.class);
    }
}
```

**2. Implementação (Green):**

```java
// src/main/java/com/consignadohub/customer/domain/vo/CPF.java

public record CPF(String value) {

    public CPF {
        if (value == null || value.isBlank()) {
            throw new InvalidCPFException("CPF não pode ser nulo ou vazio");
        }
        
        // Remove formatação
        String cleaned = value.replaceAll("[^0-9]", "");
        
        // Valida tamanho
        if (cleaned.length() != 11) {
            throw new InvalidCPFException("CPF deve ter 11 dígitos");
        }
        
        // Valida dígitos repetidos
        if (cleaned.matches("(\\d)\\1{10}")) {
            throw new InvalidCPFException("CPF com dígitos repetidos");
        }
        
        // Valida dígitos verificadores
        if (!isValid(cleaned)) {
            throw new InvalidCPFException("CPF inválido");
        }
        
        value = cleaned;
    }

    private static boolean isValid(String cpf) {
        int[] w1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] w2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum1 = 0;
        for (int i = 0; i < 9; i++) {
            sum1 += Character.getNumericValue(cpf.charAt(i)) * w1[i];
        }
        int d1 = 11 - (sum1 % 11);
        if (d1 > 9) d1 = 0;

        int sum2 = 0;
        for (int i = 0; i < 10; i++) {
            sum2 += Character.getNumericValue(cpf.charAt(i)) * w2[i];
        }
        int d2 = 11 - (sum2 % 11);
        if (d2 > 9) d2 = 0;

        return cpf.charAt(9) == Character.forDigit(d1, 10)
            && cpf.charAt(10) == Character.forDigit(d2, 10);
    }

    public String formatted() {
        return value.substring(0, 3) + "." +
               value.substring(3, 6) + "." +
               value.substring(6, 9) + "-" +
               value.substring(9);
    }
}
```

```java
// src/main/java/com/consignadohub/customer/domain/vo/InvalidCPFException.java

public class InvalidCPFException extends RuntimeException {
    public InvalidCPFException(String message) {
        super(message);
    }
}
```

#### Dia 3: Money Value Object

```java
// src/test/java/com/consignadohub/customer/domain/vo/MoneyTest.java

@DisplayName("Money Value Object")
class MoneyTest {

    @Test
    @DisplayName("Deve criar com valor positivo")
    void deveCriarPositivo() {
        Money money = Money.of("1000.50");
        assertThat(money.amount()).isEqualByComparingTo("1000.50");
    }

    @Test
    @DisplayName("Deve arredondar para 2 casas")
    void deveArredondar() {
        Money money = Money.of("100.555");
        assertThat(money.amount()).isEqualByComparingTo("100.56");
    }

    @Test
    @DisplayName("Deve somar mantendo imutabilidade")
    void deveSomar() {
        Money a = Money.of("100");
        Money b = Money.of("50");
        Money result = a.add(b);
        
        assertThat(result.amount()).isEqualByComparingTo("150");
        assertThat(a.amount()).isEqualByComparingTo("100"); // Original não muda
    }

    @Test
    @DisplayName("Não deve permitir negativo")
    void naoDevePermitirNegativo() {
        assertThatThrownBy(() -> Money.of("-100"))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
```

```java
// src/main/java/com/consignadohub/customer/domain/vo/Money.java

public record Money(BigDecimal amount) {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money {
        if (amount == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor não pode ser negativo");
        }
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public static Money of(String value) {
        return new Money(new BigDecimal(value));
    }

    public static Money of(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) == 0) {
            return ZERO;
        }
        return new Money(value);
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }

    public Money multiply(int qty) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(qty)));
    }

    public boolean isGreaterThan(Money other) {
        return this.amount.compareTo(other.amount) > 0;
    }

    public boolean isPositive() {
        return this.amount.compareTo(BigDecimal.ZERO) > 0;
    }
}
```

### ✅ Definition of Done

- [ ] `mvn clean test` passa
- [ ] CPF validando corretamente
- [ ] Money com operações básicas
- [ ] docker-compose up funciona

### 📊 Métricas (Observar depois)

Após deploy, observe:
- Logs de erro de CPF
- Requests com CPF começando com zero

### 📋 Backlog Técnico (Próximas Sprints)

- [ ] Adicionar mais Value Objects (BenefitNumber, InterestRate...)
- [ ] Spring Modulith para validação
- [ ] CI/CD básico

### 💡 Reflexão

> O código do CPF parece funcionar. Mas e se testarmos com `01234567890`?
> Por que isso é importante para clientes do Norte/Nordeste?

---

## 🎯 Sprint 1: MVP Customer Service

> **Objetivo:** API REST básica para cadastrar e consultar clientes

### 📋 User Stories

```
Como operador do Corban
Quero cadastrar um cliente com CPF
Para iniciar o processo de empréstimo
```

```
Como sistema
Quero consultar cliente por CPF
Para verificar se já existe cadastro
```

### 📖 Estude Primeiro

| Tópico | Arquivo | Tempo |
|--------|---------|-------|
| Spring Boot REST | MODULO_04 | 1h |
| JPA Básico | MODULO_05 | 1h |

### 🛠️ Implementação

#### Dia 1: Entidade e Repositório (MVP)

**⚠️ O código abaixo tem um problema de performance. Você descobrirá via métricas.**

**1. Customer Entity (simples, sem Benefit ainda):**

```java
// domain/model/Customer.java

public class Customer {
    
    private CustomerId id;
    private CPF cpf;
    private String fullName;
    private LocalDate birthDate;
    private CustomerStatus status;
    private LocalDateTime createdAt;
    
    // Factory method
    public static Customer create(String fullName, CPF cpf, LocalDate birthDate) {
        Customer customer = new Customer();
        customer.id = CustomerId.generate();
        customer.cpf = cpf;
        customer.fullName = fullName;
        customer.birthDate = birthDate;
        customer.status = CustomerStatus.ACTIVE; // MVP: sempre ativo
        customer.createdAt = LocalDateTime.now();
        return customer;
    }
    
    // Getters
    public CustomerId getId() { return id; }
    public CPF getCpf() { return cpf; }
    public String getFullName() { return fullName; }
    public LocalDate getBirthDate() { return birthDate; }
    public CustomerStatus getStatus() { return status; }
}

// domain/model/CustomerStatus.java
public enum CustomerStatus {
    ACTIVE, BLOCKED
}

// domain/model/CustomerId.java
public record CustomerId(UUID value) {
    public static CustomerId generate() {
        return new CustomerId(UUID.randomUUID());
    }
    public static CustomerId of(UUID value) {
        return new CustomerId(value);
    }
}
```

**2. JPA Entity (separada!):**

```java
// adapter/out/persistence/entity/CustomerJpaEntity.java

@Entity
@Table(name = "customers")
@Getter @Setter
public class CustomerJpaEntity {
    
    @Id
    private UUID id;
    
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
```

**3. Repository (MVP - sem otimização):**

```java
// adapter/out/persistence/CustomerJpaRepository.java

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, UUID> {
    Optional<CustomerJpaEntity> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    
    // 🐛 BUG ESCONDIDO: Este método vai causar problema N+1
    List<CustomerJpaEntity> findByStatus(CustomerStatus status);
}
```

**4. Mapper:**

```java
// adapter/out/persistence/mapper/CustomerMapper.java

@Component
public class CustomerMapper {
    
    public CustomerJpaEntity toJpa(Customer domain) {
        CustomerJpaEntity entity = new CustomerJpaEntity();
        entity.setId(domain.getId().value());
        entity.setCpf(domain.getCpf().value());
        entity.setFullName(domain.getFullName());
        entity.setBirthDate(domain.getBirthDate());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
    
    public Customer toDomain(CustomerJpaEntity entity) {
        return Customer.reconstitute(
            CustomerId.of(entity.getId()),
            new CPF(entity.getCpf()),
            entity.getFullName(),
            entity.getBirthDate(),
            entity.getStatus(),
            entity.getCreatedAt()
        );
    }
}
```

#### Dia 2: Service e Controller

**1. Use Case:**

```java
// application/port/in/RegisterCustomerUseCase.java

public interface RegisterCustomerUseCase {
    CustomerId execute(RegisterCustomerCommand command);
}

public record RegisterCustomerCommand(
    String fullName,
    String cpf,
    LocalDate birthDate
) {}
```

**2. Service:**

```java
// application/service/RegisterCustomerService.java

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterCustomerService implements RegisterCustomerUseCase {
    
    private final CustomerRepository customerRepository;
    
    @Override
    public CustomerId execute(RegisterCustomerCommand command) {
        CPF cpf = new CPF(command.cpf());
        
        if (customerRepository.existsByCpf(cpf)) {
            throw new CustomerAlreadyExistsException(cpf);
        }
        
        Customer customer = Customer.create(
            command.fullName(),
            cpf,
            command.birthDate()
        );
        
        return customerRepository.save(customer).getId();
    }
}
```

**3. Controller:**

```java
// adapter/in/web/CustomerController.java

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    
    private final RegisterCustomerUseCase registerCustomer;
    private final CustomerRepository customerRepository;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse register(@Valid @RequestBody RegisterRequest request) {
        RegisterCustomerCommand command = new RegisterCustomerCommand(
            request.fullName(),
            request.cpf(),
            request.birthDate()
        );
        
        CustomerId id = registerCustomer.execute(command);
        
        Customer customer = customerRepository.findById(id)
            .orElseThrow();
        
        return toResponse(customer);
    }
    
    @GetMapping("/{cpf}")
    public CustomerResponse getByCpf(@PathVariable String cpf) {
        return customerRepository.findByCpf(new CPF(cpf))
            .map(this::toResponse)
            .orElseThrow(() -> new CustomerNotFoundException(cpf));
    }
    
    // 🐛 BUG ESCONDIDO: Este endpoint vai ter problema N+1
    @GetMapping
    public List<CustomerResponse> listAll() {
        return customerRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }
    
    private CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(
            c.getId().value(),
            c.getCpf().formatted(),
            c.getFullName(),
            c.getBirthDate(),
            c.getStatus().name()
        );
    }
}

// adapter/in/web/dto/RegisterRequest.java
public record RegisterRequest(
    @NotBlank String fullName,
    @NotBlank String cpf,
    @NotNull LocalDate birthDate
) {}

// adapter/in/web/dto/CustomerResponse.java
public record CustomerResponse(
    UUID id,
    String cpf,
    String fullName,
    LocalDate birthDate,
    String status
) {}
```

#### Dia 3: Flyway + Teste

**1. Migration:**

```sql
-- V1__create_customers.sql

CREATE TABLE customers (
    id UUID PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_customers_cpf ON customers(cpf);
```

**2. application.yml:**

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/consignado_db
    username: consignado
    password: consignado
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true  # Para ver queries
  flyway:
    enabled: true
```

**3. Teste de integração básico:**

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {
    
    @Autowired
    TestRestTemplate restTemplate;
    
    @Test
    void deveCadastrarCliente() {
        RegisterRequest request = new RegisterRequest(
            "Maria Silva",
            "40453683886",
            LocalDate.of(1960, 5, 15)
        );
        
        ResponseEntity<CustomerResponse> response = restTemplate.postForEntity(
            "/api/v1/customers",
            request,
            CustomerResponse.class
        );
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().cpf()).isEqualTo("404.536.838-86");
    }
}
```

### ✅ Definition of Done

- [ ] POST /api/v1/customers funciona
- [ ] GET /api/v1/customers/{cpf} funciona
- [ ] Flyway migration aplicada
- [ ] docker-compose up e app conecta no postgres

### 📊 Métricas (Observar)

Após deploy, monitore:
- Tempo de resposta do GET /api/v1/customers (listAll)
- Número de queries por request (deveria ser 1, vai ser N+1)

### 📋 Backlog Técnico

- [ ] Adicionar Benefit ao Customer
- [ ] Paginação na listagem
- [ ] Cache de consultas frequentes
- [ ] Validação de idade mínima

---

## 🎯 Sprint 2: MVP Simulation Service

> **Objetivo:** Calcular parcela de empréstimo com taxa fixa

### 📋 User Stories

```
Como cliente
Quero simular um empréstimo
Para saber o valor da parcela antes de contratar
```

### 🛠️ Implementação

#### Dia 1: Simulation Domain

**⚠️ O cálculo de IOF abaixo está simplificado demais. Você descobrirá via reclamação do jurídico.**

```java
// domain/model/Simulation.java

public class Simulation {
    
    private SimulationId id;
    private Money requestedAmount;
    private int termMonths;
    private Money installmentValue;
    private Money totalAmount;
    private Money iofValue;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    
    // 🔧 DÍVIDA TÉCNICA: Taxa hardcoded, depois parametrizar
    private static final BigDecimal MONTHLY_RATE = new BigDecimal("0.0166"); // 1.66%
    
    public static Simulation create(Money requestedAmount, int termMonths) {
        Simulation sim = new Simulation();
        sim.id = SimulationId.generate();
        sim.requestedAmount = requestedAmount;
        sim.termMonths = termMonths;
        sim.createdAt = LocalDateTime.now();
        sim.expiresAt = sim.createdAt.plusHours(24);
        
        sim.calculate();
        
        return sim;
    }
    
    private void calculate() {
        // Sistema Price
        this.installmentValue = calculateInstallment();
        
        // Total
        this.totalAmount = installmentValue.multiply(termMonths);
        
        // 🐛 BUG: IOF simplificado (errado!)
        // Correto seria calcular por parcela, não pelo total
        this.iofValue = calculateIOF();
    }
    
    /**
     * Tabela Price: PMT = PV × [i(1+i)^n] / [(1+i)^n - 1]
     */
    private Money calculateInstallment() {
        BigDecimal pv = requestedAmount.amount();
        BigDecimal i = MONTHLY_RATE;
        int n = termMonths;
        
        BigDecimal factor = i.add(BigDecimal.ONE).pow(n);
        BigDecimal numerator = i.multiply(factor);
        BigDecimal denominator = factor.subtract(BigDecimal.ONE);
        
        BigDecimal pmt = pv.multiply(numerator)
            .divide(denominator, 2, RoundingMode.HALF_UP);
        
        return Money.of(pmt);
    }
    
    /**
     * 🐛 CÁLCULO ERRADO DE IOF
     * 
     * Correto: IOF deve ser calculado sobre cada parcela
     * com prazo proporcional ao vencimento.
     * 
     * Aqui usamos prazo médio fixo (simplificação).
     */
    private Money calculateIOF() {
        BigDecimal principal = requestedAmount.amount();
        
        // IOF adicional fixo: 0.38%
        BigDecimal iofAdicional = principal.multiply(new BigDecimal("0.0038"));
        
        // IOF diário: 0.0082% ao dia × prazo médio
        // 🐛 ERRO: usando prazo médio fixo ao invés de por parcela
        int prazoMedio = (termMonths * 30) / 2;
        prazoMedio = Math.min(prazoMedio, 365);
        
        BigDecimal iofDiario = principal
            .multiply(new BigDecimal("0.000082"))
            .multiply(BigDecimal.valueOf(prazoMedio));
        
        return Money.of(iofAdicional.add(iofDiario));
    }
    
    // Getters
    public SimulationId getId() { return id; }
    public Money getRequestedAmount() { return requestedAmount; }
    public int getTermMonths() { return termMonths; }
    public Money getInstallmentValue() { return installmentValue; }
    public Money getTotalAmount() { return totalAmount; }
    public Money getIofValue() { return iofValue; }
}
```

#### Dia 2: Controller

```java
// adapter/in/web/SimulationController.java

@RestController
@RequestMapping("/api/v1/simulations")
@RequiredArgsConstructor
public class SimulationController {
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SimulationResponse simulate(@Valid @RequestBody SimulateRequest request) {
        
        Simulation simulation = Simulation.create(
            Money.of(request.requestedAmount()),
            request.termMonths()
        );
        
        return new SimulationResponse(
            simulation.getId().value(),
            simulation.getRequestedAmount().amount(),
            simulation.getTermMonths(),
            simulation.getInstallmentValue().amount(),
            simulation.getTotalAmount().amount(),
            simulation.getIofValue().amount(),
            new BigDecimal("1.66"), // 🔧 Hardcoded por enquanto
            simulation.getExpiresAt()
        );
    }
}

// DTOs
public record SimulateRequest(
    @NotNull @Positive BigDecimal requestedAmount,
    @NotNull @Min(1) @Max(96) Integer termMonths
) {}

public record SimulationResponse(
    UUID simulationId,
    BigDecimal requestedAmount,
    int termMonths,
    BigDecimal installmentValue,
    BigDecimal totalAmount,
    BigDecimal iofValue,
    BigDecimal monthlyRate,
    LocalDateTime expiresAt
) {}
```

### ✅ Definition of Done

- [ ] POST /api/v1/simulations funciona
- [ ] Parcela calculada com tabela Price
- [ ] IOF calculado (mesmo que simplificado)
- [ ] Resposta inclui todos os valores

### 📊 Métricas (Observar)

- Compare o IOF calculado com calculadoras online
- A diferença pode chegar a 0.3% no CET

### 📋 Backlog Técnico

- [ ] Externalizar taxa de juros
- [ ] Corrigir cálculo de IOF
- [ ] Calcular CET corretamente
- [ ] Validar margem disponível
- [ ] Persistir simulação

---

## 🔴 Após Sprint 2: Primeiro Feedback

Neste ponto, você terá:
- Customer Service rodando
- Simulation Service rodando
- Alguns clientes cadastrados
- Algumas simulações feitas

**E então... os problemas começam a aparecer:**

1. QA vai reportar: "CPF 01234567890 está dando erro"
2. Grafana vai mostrar: "Latência do GET /customers aumentou"
3. Jurídico vai reclamar: "CET informado está diferente do contrato"

Prepare-se para a Sprint 3! 🔥

---

## 🎯 Sprint 3: Kafka + Primeiro Incidente Real

> **Objetivo:** Adicionar análise de crédito assíncrona com Kafka
> **Incidente:** Mensagens duplicadas detectadas

### 📋 User Stories

```
Como banco
Quero analisar crédito do cliente antes de aprovar
Para reduzir inadimplência
```

```
Como sistema
Quero processar análises de forma assíncrona
Para não bloquear o fluxo principal
```

### 🛠️ Implementação MVP

#### Dia 1: Kafka Producer

```yaml
# docker-compose.yml (adicionar)
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
```

```java
// domain/event/SimulationRequestedEvent.java

public record SimulationRequestedEvent(
    String eventId,
    String simulationId,
    String customerId,
    BigDecimal requestedAmount,
    int termMonths,
    LocalDateTime occurredAt
) {}
```

```java
// adapter/out/kafka/KafkaEventPublisher.java

@Component
@RequiredArgsConstructor
public class KafkaEventPublisher {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    private static final String TOPIC = "simulation-events";
    
    // 🐛 BUG: Sem idempotência! Se falhar e reenviar, duplica
    public void publish(SimulationRequestedEvent event) {
        kafkaTemplate.send(TOPIC, event.simulationId(), event);
    }
}
```

#### Dia 2: Kafka Consumer (Credit Service)

```java
// credit-service/adapter/in/kafka/SimulationEventConsumer.java

@Component
@RequiredArgsConstructor
@Slf4j
public class SimulationEventConsumer {
    
    private final CreditAnalysisService analysisService;
    
    // 🐛 BUG: Consumer processa duplicatas! Sem verificação de idempotência
    @KafkaListener(topics = "simulation-events", groupId = "credit-service")
    public void consume(SimulationRequestedEvent event) {
        log.info("Processando evento: {}", event.eventId());
        
        // Analisa crédito
        CreditAnalysisResult result = analysisService.analyze(event);
        
        // Publica resultado
        // ...
    }
}
```

### 🔴 INCIDENTE: Duplicação Detectada!

Após alguns dias em "produção", você recebe:

```
🔴 ALERTA DE LOGS

[2024-01-15 10:00:01] Processando evento: EVT-001
[2024-01-15 10:00:01] Processando evento: EVT-001
[2024-01-15 10:00:01] Processando evento: EVT-001

Cliente recebeu 3 e-mails de "análise em andamento"!
```

### 🔧 REFACTOR: Adicionar Idempotência

```java
// Solução: Usar Redis para tracking

@Component
@RequiredArgsConstructor
public class IdempotentEventConsumer {
    
    private final StringRedisTemplate redis;
    private final CreditAnalysisService analysisService;
    
    @KafkaListener(topics = "simulation-events", groupId = "credit-service")
    public void consume(SimulationRequestedEvent event) {
        String key = "processed:" + event.eventId();
        
        // Verifica se já processou
        Boolean isNew = redis.opsForValue().setIfAbsent(key, "1", Duration.ofDays(1));
        
        if (Boolean.FALSE.equals(isNew)) {
            log.info("Evento já processado, ignorando: {}", event.eventId());
            return;
        }
        
        analysisService.analyze(event);
    }
}
```

### 📝 Retrospectiva do Incidente

```markdown
## Retrospectiva: INC-003 - Mensagens Duplicadas

**O que aconteceu:**
Eventos Kafka sendo processados múltiplas vezes

**Causa raiz:**
- Kafka usa "at-least-once delivery"
- Consumer não verificava se já processou o evento

**Solução:**
- Adicionado Redis para verificar idempotência
- Key: eventId, TTL: 24h

**Lição aprendida:**
Sempre assumir que mensagens podem ser duplicadas em sistemas distribuídos

**Para entrevista:**
"Tivemos um problema de duplicação de mensagens no Kafka. Descobrimos
através dos logs que clientes recebiam múltiplos e-mails. A causa era
que o Kafka garante at-least-once, não exactly-once. Resolvemos
adicionando verificação de idempotência com Redis."
```

---

## 🎯 Sprint 4: Loan + Averbação (com Incidente)

> **Objetivo:** Criar contrato e averbar no INSS
> **Incidente:** Circuit breaker não protege o sistema

### 🛠️ Implementação MVP

#### Mock da Dataprev (Inline)

```java
// adapter/out/dataprev/DataprevClient.java

@Component
@Slf4j
public class DataprevClient {
    
    // 🔧 DÍVIDA: Mock inline, depois usar Feign/WireMock
    public String averbar(String benefitNumber, BigDecimal installment) {
        log.info("Averbando no INSS: benefício={}", benefitNumber);
        
        // Simular latência
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Simular falha ocasional (30%)
        if (Math.random() < 0.3) {
            throw new DataprevUnavailableException("INSS fora do ar");
        }
        
        return "AVB" + System.currentTimeMillis();
    }
}
```

#### Circuit Breaker (Configuração Errada!)

```java
// 🐛 BUG: Configuração muito permissiva!

@Component
@Slf4j
public class ResilientDataprevClient {
    
    private final DataprevClient dataprevClient;
    
    // 🐛 slidingWindowSize=100 é MUITO grande!
    // Com 30% de erro, precisa de 100 requests para abrir
    @CircuitBreaker(name = "dataprev", fallbackMethod = "fallback")
    public String averbar(String benefitNumber, BigDecimal installment) {
        return dataprevClient.averbar(benefitNumber, installment);
    }
    
    public String fallback(String benefitNumber, BigDecimal installment, Exception e) {
        log.warn("Fallback: {}", e.getMessage());
        throw new ServiceTemporarilyUnavailableException("Tente novamente em alguns minutos");
    }
}
```

```yaml
# application.yml - Configuração ERRADA
resilience4j:
  circuitbreaker:
    instances:
      dataprev:
        slidingWindowSize: 100  # 🐛 Muito grande!
        failureRateThreshold: 50
        waitDurationInOpenState: 60s
```

### 🔴 INCIDENTE: Sistema Travando!

```
🔴🔴🔴 INCIDENTE CRÍTICO 🔴🔴🔴

Dataprev está fora do ar há 30 minutos.
Todas as threads estão bloqueadas esperando resposta.

Métricas:
- Threads ativas: 200/200 (ESGOTADO!)
- Requests pendentes: 500
- Circuit breaker: CLOSED (deveria estar OPEN!)

Sistema vai cair em 5 minutos!
```

### 🔧 REFACTOR: Configuração Correta

```yaml
# application.yml - CORRIGIDO
resilience4j:
  circuitbreaker:
    instances:
      dataprev:
        slidingWindowSize: 10     # Janela menor
        failureRateThreshold: 50  # 50% de falha = abre
        waitDurationInOpenState: 30s
        permittedNumberOfCallsInHalfOpenState: 3
  
  timelimiter:
    instances:
      dataprev:
        timeoutDuration: 3s  # Timeout de 3 segundos!
```

### 📝 Retrospectiva

```markdown
## Retrospectiva: INC-004 - Circuit Breaker não Protegeu

**O que aconteceu:**
Sistema travou com Dataprev fora do ar

**Causa raiz:**
- slidingWindowSize=100 muito grande
- Sem timeout configurado
- Threads ficavam bloqueadas eternamente

**Solução:**
- Reduzir sliding window para 10
- Adicionar timeout de 3s
- Configurar TimeLimiter

**Lição:**
Circuit breaker precisa de tuning para cada serviço.
Valores default raramente são adequados.
```

---

## 🎯 Sprint 5: Payment + Feature Urgente

> **Objetivo:** Gerenciar parcelas de pagamento
> **Feature:** "Margem precisa ser configurável por produto!"

### 🛠️ Implementação MVP

```java
// Parcela básica
public class Installment {
    private InstallmentId id;
    private ContractId contractId;
    private int number;
    private Money value;
    private LocalDate dueDate;
    private InstallmentStatus status;
    
    // ... métodos
}
```

### 📋 FEATURE URGENTE: Configuração Dinâmica

No meio da sprint, você recebe:

```
📢 E-MAIL DO PO:

"Acabamos de fechar parceria com a Caixa!
Eles querem margem diferente: 40% ao invés de 35%.

Precisamos que a margem seja configurável por parceiro.
Pode ser para amanhã?"
```

### 🔧 REFACTOR: Externalizar Configuração

**ANTES (Hardcoded):**

```java
public static final BigDecimal MARGIN = new BigDecimal("35");
```

**DEPOIS (Configurável):**

```java
// config/MarginProperties.java

@Configuration
@ConfigurationProperties(prefix = "consignado.margin")
public class MarginProperties {
    
    private Map<String, BigDecimal> byPartner = new HashMap<>();
    
    public BigDecimal forPartner(String partnerId) {
        return byPartner.getOrDefault(partnerId, new BigDecimal("35"));
    }
    
    // getter/setter
}
```

```yaml
# application.yml
consignado:
  margin:
    by-partner:
      CORBAN: 35
      CAIXA: 40
      INTERNO: 35
```

**FUTURO (AWS Parameter Store):**

```java
// Quando for para AWS, trocar para:
@Value("${/consignado/margin/corban}")
private BigDecimal corbanMargin;
```

---

## 🎯 Sprint 6: Gateway + Multi-Canal

> **Objetivo:** API Gateway com autenticação
> **Feature:** "Precisamos diferenciar canal interno do FinBank!"

### 🛠️ Implementação MVP

```java
// Gateway básico com Spring Cloud Gateway

@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("customers", r -> r
                .path("/api/v1/customers/**")
                .uri("lb://customer-service"))
            .route("simulations", r -> r
                .path("/api/v1/simulations/**")
                .uri("lb://simulation-service"))
            .build();
    }
}
```

### 📋 FEATURE URGENTE: Canal Interno

```
📢 REUNIÃO COM GERENTE:

"O time interno do FinBank precisa de acesso especial.
Eles têm regras diferentes:
- Podem aprovar clientes nos 90 dias iniciais
- Taxa especial de 1.50%
- Margem 45%

Precisamos identificar quando o request vem do canal interno."
```

### 🔧 REFACTOR: Strategy Pattern para Canais

**ANTES (if/else):**

```java
public void process(Request request) {
    if (request.getChannel().equals("CORBAN")) {
        // lógica corban
    } else if (request.getChannel().equals("INTERNAL")) {
        // lógica interno
    } else {
        // digital
    }
}
```

**DEPOIS (Strategy):**

```java
// domain/strategy/ChannelStrategy.java

public interface ChannelStrategy {
    BigDecimal getMarginPercentage();
    BigDecimal getInterestRate();
    boolean canApproveWithin90Days();
}

@Component("CORBAN")
public class CorbanStrategy implements ChannelStrategy {
    public BigDecimal getMarginPercentage() { return new BigDecimal("35"); }
    public BigDecimal getInterestRate() { return new BigDecimal("1.66"); }
    public boolean canApproveWithin90Days() { return false; }
}

@Component("INTERNAL")
public class InternalStrategy implements ChannelStrategy {
    public BigDecimal getMarginPercentage() { return new BigDecimal("45"); }
    public BigDecimal getInterestRate() { return new BigDecimal("1.50"); }
    public boolean canApproveWithin90Days() { return true; }
}

// Factory
@Component
@RequiredArgsConstructor
public class ChannelStrategyFactory {
    
    private final Map<String, ChannelStrategy> strategies;
    
    public ChannelStrategy forChannel(String channel) {
        return strategies.getOrDefault(channel, strategies.get("DIGITAL"));
    }
}
```

---

## 📊 Resumo das Sprints 3-6

| Sprint | Entrega | Incidente/Feature | Refactor |
|--------|---------|-------------------|----------|
| 3 | Kafka básico | 🔴 Duplicação | Idempotência Redis |
| 4 | Averbação mock | 🔴 Circuit breaker | Resilience4j tuning |
| 5 | Payment | 📋 Margem config | External config |
| 6 | Gateway | 📋 Multi-canal | Strategy pattern |

### 💡 O que você aprendeu

1. **Kafka at-least-once** precisa de idempotência
2. **Circuit breaker** precisa de tuning
3. **Hardcode** vira dívida técnica
4. **if/else** vira Strategy quando cresce

---

## 🎯 Sprint 7+: DevOps, Kubernetes, AWS

> A partir daqui, o foco muda de **features** para **infraestrutura**

### Sprint 7: DevOps + Observabilidade

**Objetivo:** Docker otimizado, CI/CD, métricas

#### Dockerfile Multi-stage

```dockerfile
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY . .
RUN ./mvnw package -DskipTests

# 🐛 BUG: Sem configuração de JVM!
FROM eclipse-temurin:21-jre-alpine
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### 🔴 INCIDENTE: OOMKilled

```
🔴 PODS CRASHANDO

kubectl get pods:
customer-service-abc123   0/1   OOMKilled   5   10m

Container memory: 512Mi limit
JVM heap: usando toda memória disponível!
```

#### 🔧 REFACTOR: JVM Tuning

```dockerfile
FROM eclipse-temurin:21-jre-alpine
COPY --from=builder /app/target/*.jar app.jar

# Configurar JVM para container
ENV JAVA_OPTS="-Xms256m -Xmx384m -XX:+UseContainerSupport"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

#### GitHub Actions

```yaml
# .github/workflows/ci.yml
name: CI

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
      - run: ./mvnw verify
      - uses: codecov/codecov-action@v3
```

#### Prometheus + Grafana

```yaml
# prometheus.yml
scrape_configs:
  - job_name: 'consignado'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['customer-service:8080']
```

---

### Sprint 8: Kubernetes

**Objetivo:** Deploy em K8s local (Kind)

```yaml
# k8s/customer-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: customer-service
spec:
  replicas: 2
  selector:
    matchLabels:
      app: customer-service
  template:
    spec:
      containers:
        - name: customer-service
          image: consignado/customer-service:latest
          resources:
            requests:
              memory: "512Mi"
              cpu: "250m"
            limits:
              memory: "1Gi"
              cpu: "500m"
          readinessProbe:
            httpGet:
              path: /actuator/health/readiness
              port: 8080
          livenessProbe:
            httpGet:
              path: /actuator/health/liveness
              port: 8080
```

---

### Sprint 9: AWS + LocalStack

**Objetivo:** Simular AWS localmente

```yaml
# docker-compose-localstack.yml
services:
  localstack:
    image: localstack/localstack:3.0
    ports:
      - "4566:4566"
    environment:
      SERVICES: s3,sqs,secretsmanager
```

```bash
# Criar recursos
awslocal s3 mb s3://consignado-documents
awslocal sqs create-queue --queue-name averbation-requests
awslocal secretsmanager create-secret --name consignado/database --secret-string '{"password":"xxx"}'
```

---

### Sprint 10: Terraform

**Objetivo:** Infraestrutura como Código

```hcl
# terraform/main.tf
module "vpc" {
  source = "./modules/vpc"
}

module "rds" {
  source = "./modules/rds"
  vpc_id = module.vpc.id
}

module "eks" {
  source = "./modules/eks"
  vpc_id = module.vpc.id
}
```

---

### Sprint 11: Integração Final

**Objetivo:** E2E tests, load tests, documentação

```java
@Test
void fluxoCompleto() {
    // 1. Cadastrar cliente
    var customer = cadastrar();
    
    // 2. Simular empréstimo
    var simulation = simular(customer.id());
    
    // 3. Criar proposta
    var proposal = criarProposta(simulation.id());
    
    // 4. Aguardar aprovação
    await().until(() -> isApproved(proposal.id()));
    
    // 5. Verificar contrato
    assertThat(getContract(proposal.id()).status()).isEqualTo("ACTIVE");
}
```

---

## 🎓 Conclusão

### O que você construiu

- ✅ Sistema de crédito consignado completo
- ✅ Arquitetura de microsserviços
- ✅ Event-driven com Kafka
- ✅ CI/CD automatizado
- ✅ Deploy em Kubernetes

### O que você aprendeu (para entrevistas!)

| Situação | Problema | Solução |
|----------|----------|---------|
| Kafka at-least-once | Duplicação | Idempotência Redis |
| Serviço externo lento | Thread starvation | Circuit breaker tuning |
| Config hardcoded | Inflexível | External config pattern |
| Muitos if/else | Código ilegível | Strategy pattern |
| JVM em container | OOMKilled | Xmx + ContainerSupport |

### Histórias para Entrevista

1. **"Conte um bug que você resolveu"**
   > "Tivemos duplicação de mensagens no Kafka. Descobri via logs,
   > implementei idempotência com Redis."

2. **"Conte um incidente de produção"**
   > "Circuit breaker não estava protegendo. Threads travavam.
   > Ajustei sliding window e adicionei timeout."

3. **"Como você lida com mudança de requisitos?"**
   > "Recebemos pedido de margem configurável no meio da sprint.
   > Externalizei a config e entreguei em 1 dia."

4. **"Como você evolui código legado?"**
   > "Tinha if/else para canais. Quando veio terceiro canal,
   > refatorei para Strategy pattern."

---

## 📋 Checklist Final

### Funcionalidades
- [ ] Cadastro de cliente
- [ ] Vinculação de benefício INSS
- [ ] Simulação de empréstimo
- [ ] Análise de crédito
- [ ] Criação de contrato
- [ ] Averbação (mock)
- [ ] Gestão de parcelas

### Técnico
- [ ] Value Objects imutáveis
- [ ] Arquitetura Hexagonal
- [ ] Testes unitários > 80%
- [ ] Testes de integração
- [ ] Kafka funcionando
- [ ] Resilience4j configurado
- [ ] Docker otimizado
- [ ] CI/CD rodando

### DevOps
- [ ] Prometheus + Grafana
- [ ] Jaeger (tracing)
- [ ] Kubernetes manifests
- [ ] Helm charts
- [ ] Terraform modules

---

## 🎯 Próximos Passos (Pós-Projeto)

1. **FGTS** - Adicionar consulta e saque-aniversário
2. **Portabilidade** - Receber de outros bancos
3. **Machine Learning** - Score de crédito preditivo
4. **Multi-tenancy** - Suportar outros parceiros

---

> **Parabéns! 🎉**
> 
> Você completou o ConsignadoHub com metodologia ágil real!

