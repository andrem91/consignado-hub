# 🎯 Guia Completo de Entrevista - Java Pleno/Sênior

> 80+ perguntas frequentes com respostas detalhadas e exemplos práticos para preparação de entrevistas em grandes empresas.

---

## 📚 1. PROGRAMAÇÃO ORIENTADA A OBJETOS (POO)

### O que é POO?

**Resposta:**
POO é um paradigma de programação que organiza o código em torno de objetos, que são instâncias de classes. Esses objetos combinam dados (atributos) e comportamentos (métodos), permitindo modelar problemas do mundo real de forma mais intuitiva. 

No meu dia a dia com Java, isso facilita muito a manutenção e reutilização de código. Por exemplo, ao modelar um sistema bancário, posso criar uma classe `Cliente` que encapsula CPF, nome e comportamentos como `validarIdade()`, refletindo exatamente como pensamos sobre clientes no mundo real.

A POO contrasta com a programação procedural, onde o código é organizado em funções que manipulam dados. Com POO, os dados e as operações sobre eles ficam juntos, aumentando a coesão e reduzindo o acoplamento.

---

### Quais são os pilares da orientação a objetos? Descreva sucintamente cada um deles.

**Resposta:**
Os quatro pilares são:

**Encapsulamento:**
Proteger os dados internos de uma classe, expondo apenas o necessário através de métodos públicos. Uso bastante modificadores de acesso e getters/setters quando apropriado. Por exemplo, no meu projeto de crédito consignado, o CPF é armazenado internamente como String limpa, mas exponho apenas métodos como `formatar()` e `mascarar()`. O estado interno fica protegido.

```java
public record CPF(String valor) {
    // valor é privado implicitamente
    public String formatar() { return "XXX.XXX.XXX-XX"; }
    public String mascarar() { return "***.***.***-XX"; }
}
```

**Herança:**
Permite que uma classe herde características de outra, promovendo reuso de código. Sempre tento usar com cuidado para não criar hierarquias muito complexas - prefiro composição sobre herança na maioria dos casos. Um bom uso é quando realmente existe uma relação "é um", como `ContaCorrente extends Conta`.

**Polimorfismo:**
Capacidade de objetos de classes diferentes responderem à mesma mensagem de formas distintas. Utilizo muito com interfaces e sobrescrita de métodos. Por exemplo, tenho uma interface `ClienteRepository` que pode ter implementações diferentes (`ClienteJpaRepository`, `ClienteInMemoryRepository`) - o código cliente não precisa saber qual está sendo usada.

**Abstração:**
Focar no essencial, ocultando detalhes complexos. Classes abstratas e interfaces são ferramentas que uso frequentemente para isso. No meu projeto, `CadastrarClienteUseCase` é uma interface que abstrai completamente como o cadastro é feito - o Controller só sabe que pode chamar `executar()`.

---

### Qual a diferença entre classe abstrata e interface?

**Resposta:**
Essa é uma pergunta clássica que mostra entendimento profundo de OOP:

**Classe Abstrata:**
- Pode ter implementação de métodos (concretos e abstratos)
- Pode ter estado (atributos de instância)
- Pode ter construtores
- Herança única: uma classe só pode estender uma classe abstrata
- Uso quando tenho código comum que quero compartilhar entre subclasses

```java
public abstract class Conta {
    protected BigDecimal saldo;
    
    public void depositar(BigDecimal valor) {
        this.saldo = saldo.add(valor);
    }
    
    public abstract void sacar(BigDecimal valor); // Cada tipo implementa
}
```

**Interface:**
- Antes do Java 8: apenas métodos abstratos
- Java 8+: pode ter métodos default e static
- Apenas constantes (public static final)
- Não tem construtores
- Múltipla implementação: uma classe pode implementar várias interfaces
- Uso para definir contratos

```java
public interface Pagavel {
    void pagar(BigDecimal valor);
    
    default boolean podePagar(BigDecimal valor) {
        return true; // Implementação default
    }
}
```

**Quando usar cada:**
- **Interface:** Quando quero definir um contrato que múltiplas classes não relacionadas podem implementar (ex: `Serializable`, `Comparable`)
- **Classe Abstrata:** Quando tenho código comum que quero compartilhar E as classes têm uma relação hierárquica clara (ex: `Conta -> ContaCorrente, ContaPoupanca`)

**Regra prática:** Prefira interfaces. Use classe abstrata apenas quando realmente precisa compartilhar código.

---

## 🏗️ 2. PRINCÍPIOS E PADRÕES DE DESIGN

### Fale sobre os princípios SOLID.

**Resposta:**
SOLID é um conjunto de princípios que me guiam para escrever código mais limpo e manutenível. Aprendi que aplicar SOLID não é sobre seguir regras cegamente, mas entender o "porquê" de cada princípio:

**S - Single Responsibility Principle (Responsabilidade Única):**
Cada classe deve ter apenas uma responsabilidade, apenas um motivo para mudar. Evito criar "classes Deus" que fazem tudo. 

No meu projeto, separei `ClienteService` (orquestração) de `ClienteRepository` (persistência) de `ClienteController` (HTTP). Cada um muda por motivos diferentes: regras de negócio, banco de dados, ou API.

**O - Open/Closed Principle (Aberto/Fechado):**
Classes devem estar abertas para extensão, mas fechadas para modificação. Uso muito interfaces e herança para isso. 

Exemplo: Se amanhã precisar de um novo tipo de cálculo de IOF, não modifico a classe existente - crio uma nova implementação da interface `CalculadoraIOF`.

**L - Liskov Substitution Principle (Substituição de Liskov):**
Subclasses devem poder substituir suas superclasses sem quebrar o sistema. 

Teste mental: se tenho um método que recebe `Conta`, posso passar `ContaCorrente` ou `ContaPoupanca` e tudo continua funcionando? Se não, violei Liskov.

**I - Interface Segregation Principle (Segregação de Interfaces):**
Prefiro interfaces menores e específicas a uma interface grande e genérica. Isso evita que classes implementem métodos que não precisam.

```java
// Ruim: interface grande
interface Trabalhador {
    void trabalhar();
    void comer();
    void dormir();
}

// Bom: interfaces específicas
interface Trabalhavel { void trabalhar(); }
interface Alimentavel { void comer(); }
```

**D - Dependency Inversion Principle (Inversão de Dependência):**
Dependo de abstrações, não de implementações concretas. Isso casa perfeitamente com injeção de dependências no Spring.

```java
// Ruim: depende de implementação
public class ClienteService {
    private ClienteJpaRepository repository = new ClienteJpaRepository();
}

// Bom: depende de abstração
public class ClienteService {
    private final ClienteRepository repository; // Interface
    
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }
}
```

---

### O que é Clean Code? Como você aplica no dia a dia?

**Resposta:**
Clean Code são práticas para escrever código legível e manutenível, popularizadas pelo livro do Uncle Bob (Robert C. Martin). A ideia central é que código é lido muito mais vezes do que escrito, então devemos otimizar para leitura.

**Princípios que aplico diariamente:**

**1. Nomes Significativos:**
```java
// Ruim
int d; // tempo em dias
void calc() { }

// Bom
int diasAteVencimento;
void calcularMargemDisponivel() { }
```

**2. Funções Pequenas:**
Cada função deve fazer uma coisa só, e fazer bem. Se preciso de um comentário explicando o que um bloco faz, deveria ser uma função separada.

```java
// Ruim: função que faz tudo
void processarContrato() {
    // 50 linhas de validação
    // 30 linhas de cálculo
    // 40 linhas de persistência
}

// Bom: funções focadas
void processarContrato() {
    validarDados();
    calcularParcelas();
    persistir();
}
```

**3. Sem Comentários Desnecessários:**
Código auto-explicativo não precisa de comentários. Comentários mentem quando o código muda e ninguém atualiza o comentário.

```java
// Ruim: comentário óbvio
// Incrementa contador
contador++;

// Bom: código que se explica
int numeroDeTentativas = 0;
numeroDeTentativas++;
```

**4. DRY (Don't Repeat Yourself):**
Se vejo código duplicado, extraio para um método ou classe.

**5. Tratamento de Erros:**
Prefiro exceptions a códigos de erro. E trato exceptions no nível apropriado (GlobalExceptionHandler no meu projeto).

**6. Testes:**
Código limpo tem testes. Se não consigo testar facilmente, provavelmente o código não está limpo.

---

### O que é Clean Architecture?

**Resposta:**
Clean Architecture é uma arquitetura proposta por Uncle Bob que organiza o código em camadas concêntricas, com a regra de que dependências sempre apontam para dentro.

```
┌─────────────────────────────────────────────────────────┐
│              FRAMEWORKS & DRIVERS (mais externo)         │
│  Spring, Hibernate, Controllers, Banco de Dados         │
│   ┌─────────────────────────────────────────────────┐   │
│   │           INTERFACE ADAPTERS                     │   │
│   │   Presenters, Gateways, Controllers              │   │
│   │   ┌─────────────────────────────────────────┐   │   │
│   │   │        APPLICATION BUSINESS RULES        │   │   │
│   │   │   Use Cases (orquestração)               │   │   │
│   │   │   ┌─────────────────────────────────┐   │   │   │
│   │   │   │   ENTERPRISE BUSINESS RULES     │   │   │   │
│   │   │   │   Entities (regras de negócio)  │   │   │   │
│   │   │   └─────────────────────────────────┘   │   │   │
│   │   └─────────────────────────────────────────┘   │   │
│   └─────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
```

**Regra da Dependência:**
O código nas camadas internas não pode saber NADA sobre as camadas externas. Isso significa que:
- Entities não conhecem Use Cases
- Use Cases não conhecem Controllers
- Nada no core conhece Spring ou JPA

**Benefícios que vejo na prática:**
1. **Testabilidade:** Posso testar regras de negócio sem banco, sem web server
2. **Independência de frameworks:** Posso trocar Spring por Quarkus sem tocar no domínio
3. **Independência de UI:** Web, mobile, CLI - mesmo core
4. **Independência de banco:** PostgreSQL, MongoDB - mesmo core

**No meu projeto ConsignadoHub:**
- **Entities:** `Cliente`, `Beneficio`, VOs como `CPF`, `Dinheiro`
- **Use Cases:** `CadastrarClienteUseCase`, `BuscarClienteQuery`
- **Interface Adapters:** `ClienteController`, `ClienteRepositoryAdapter`
- **Frameworks:** Spring Boot, JPA, PostgreSQL

---

### Quais Design Patterns você mais usa? Dê exemplos práticos.

**Resposta:**
Uso diversos patterns, mas alguns são realmente frequentes no meu dia a dia:

**Factory Method:**
Para criação de objetos complexos ou quando quero esconder a lógica de criação.

```java
public record ClienteId(UUID valor) {
    public static ClienteId novo() {
        return new ClienteId(UUID.randomUUID());
    }
    
    public static ClienteId of(UUID valor) {
        return new ClienteId(valor);
    }
}

// Uso
ClienteId id = ClienteId.novo(); // Gera novo
ClienteId existente = ClienteId.of(uuid); // Reconstitui
```

**Strategy:**
Para múltiplos algoritmos intercambiáveis. Muito útil quando tenho if/else ou switch que cresce.

```java
public interface CalculadoraIOF {
    Dinheiro calcular(Contrato contrato);
}

public class CalculadoraIOFPadrao implements CalculadoraIOF { ... }
public class CalculadoraIOFReducido implements CalculadoraIOF { ... }

// Uso: injeto a estratégia que preciso
@Service
public class ContratoService {
    private final CalculadoraIOF calculadoraIOF;
    // ...
}
```

**Repository:**
Abstrai a camada de persistência. O domínio não sabe se estou usando JPA, MongoDB ou arquivo.

```java
public interface ClienteRepository {
    Cliente salvar(Cliente cliente);
    Optional<Cliente> buscarPorCpf(CPF cpf);
    boolean existePorCpf(CPF cpf);
}
```

**Builder:**
Para construção de objetos complexos passo a passo. Lombok gera automaticamente.

```java
@Builder
public class SimulacaoRequest {
    private BigDecimal valorSolicitado;
    private int prazoMeses;
    private TipoBeneficio tipoBeneficio;
}

// Uso
SimulacaoRequest request = SimulacaoRequest.builder()
    .valorSolicitado(new BigDecimal("5000"))
    .prazoMeses(24)
    .tipoBeneficio(TipoBeneficio.APOSENTADORIA)
    .build();
```

**Adapter:**
Converte uma interface em outra. Uso muito para integrar camadas.

```java
// Adapter que implementa Port do domínio usando JPA
@Repository
public class ClienteRepositoryAdapter implements ClienteRepository {
    private final ClienteJpaRepository jpaRepository;
    private final ClienteMapper mapper;
    
    @Override
    public Cliente salvar(Cliente cliente) {
        ClienteJpaEntity entity = mapper.toEntity(cliente);
        jpaRepository.save(entity);
        return cliente;
    }
}
```

**Singleton:**
Uma única instância. No Spring, todos os beans são singleton por padrão - não preciso implementar manualmente.

---

### Como os princípios SOLID se combinam com Clean Architecture?

**Resposta:**
Clean Architecture e SOLID são complementares. A Clean Architecture organiza o código em camadas concêntricas com a regra de que dependências apontam para dentro. Isso é exatamente o princípio D do SOLID (Inversão de Dependência) aplicado em nível arquitetural.

| Princípio SOLID | Como Aplica na Clean Architecture |
|-----------------|-----------------------------------|
| **S - Single Responsibility** | Cada camada tem uma responsabilidade: Domain (regras), Application (orquestração), Adapters (conversão) |
| **O - Open/Closed** | Adiciono novos Use Cases sem modificar os existentes. Novos Adapters sem tocar no Domain |
| **L - Liskov Substitution** | Posso trocar `ClienteJpaRepository` por `ClienteInMemoryRepository` - ambos implementam `ClienteRepository` |
| **I - Interface Segregation** | Separo `CadastrarClienteUseCase` de `BuscarClienteQuery` - interfaces pequenas e focadas |
| **D - Dependency Inversion** | O Domain define interfaces (Ports), Adapters implementam. Dependência sempre aponta para dentro |

**Resposta para entrevista:**
*"Clean Architecture é a ESTRUTURA, SOLID são as REGRAS de como construir cada parte. O princípio D (Inversão de Dependência) é o coração da Clean Architecture - o Domain define interfaces como `ClienteRepository`, e a camada de Adapters implementa com `ClienteJpaRepository`. O Domain não conhece JPA, depende de abstrações. Resumindo: SOLID são os princípios para escrever classes boas, Clean Architecture é como organizar essas classes respeitando esses princípios."*

---

### O que são Use Cases?

**Resposta:**
Use Cases são as AÇÕES que o sistema sabe fazer. Cada Use Case representa uma operação de negócio que um usuário pode executar.

**Estrutura de um Use Case:**

```java
// Interface (Port de entrada)
public interface CadastrarClienteUseCase {
    ClienteId executar(CadastrarClienteCommand command);
}

// Implementação (Service)
@Service
public class CadastrarClienteService implements CadastrarClienteUseCase {
    
    private final ClienteRepository repository;  // Port de saída
    
    @Override
    public ClienteId executar(CadastrarClienteCommand command) {
        // 1. Criar entidade de domínio
        Cliente cliente = Cliente.criar(new CPF(command.cpf()), command.nome());
        
        // 2. Aplicar regras de negócio
        if (repository.existePorCpf(cliente.getCpf())) {
            throw BusinessException.cpfJaCadastrado(cliente.getCpf());
        }
        
        // 3. Persistir
        return repository.salvar(cliente);
    }
}
```

**Use Case vs Service Tradicional:**

| Service Tradicional | Use Case (Clean Architecture) |
|---------------------|-------------------------------|
| Classe com vários métodos | Uma interface = Uma ação |
| `ClienteService.cadastrar()`, `.buscar()` | `CadastrarClienteUseCase`, `BuscarClienteQuery` |
| Tende a virar "God Class" | Cada use case é pequeno e focado |
| Difícil de testar isoladamente | Fácil de mockar dependências |

**Resposta para entrevista:**
*"Use Cases representam as ações de negócio que o sistema oferece. Cada Use Case é uma interface com um único método - geralmente `executar()`. Isso segue o Single Responsibility: uma classe, uma razão para mudar. A vantagem é que o Controller só conhece a interface `CadastrarClienteUseCase`, não a implementação. Posso trocar a implementação sem tocar no Controller."*

---

## 🏛️ 3. ARQUITETURA E METODOLOGIAS

### Explique a metodologia DDD de acordo com o seu entendimento.

**Resposta:**
DDD (Domain-Driven Design) é uma abordagem que coloca o domínio do negócio no centro do desenvolvimento. Foi proposta por Eric Evans e mudou como penso sobre arquitetura de software.

**Conceitos Estratégicos:**

**Linguagem Ubíqua:**
Desenvolvedores e especialistas de negócio usam os mesmos termos. Se o negócio fala "margem consignável", meu código tem `margemConsignavel`, não `availableMargin` ou `margem_disponivel`.

**Bounded Contexts:**
Divisão do sistema em contextos com fronteiras claras. Cada contexto tem seu próprio modelo. "Cliente" no contexto de Cadastro é diferente de "Cliente" no contexto de Cobrança.

**Conceitos Táticos:**

**Entities:**
Objetos com identidade única. Dois clientes com mesmo nome são diferentes se têm IDs diferentes.

```java
public class Cliente {
    private final ClienteId id; // Identidade
    private String nome;        // Pode mudar
}
```

**Value Objects:**
Imutáveis, sem identidade. Dois CPFs com mesmo valor são iguais.

```java
public record CPF(String valor) {
    public CPF {
        // Validação no construtor
        if (!isValid(valor)) throw DomainException.invalidField("cpf", "inválido");
    }
}
```

**Aggregates:**
Cluster de entidades tratadas como unidade. Aggregate Root é o único ponto de entrada.

```java
public class Cliente { // Aggregate Root
    private List<Beneficio> beneficios; // Parte do agregado
    
    public void adicionarBeneficio(Beneficio beneficio) {
        // Regras de negócio aqui
        this.beneficios.add(beneficio);
    }
}
// Nunca acesso Beneficio diretamente, sempre via Cliente
```

**Repositories:**
Interface para persistência, definida no domínio, implementada na infraestrutura.

**Domain Services:**
Quando uma operação não pertence naturalmente a nenhuma entidade.

**No meu projeto ConsignadoHub:**
Aplico DDD dividindo claramente: VOs (CPF, Dinheiro), Entities (Cliente, Beneficio), Aggregates (Cliente como root), e Repositories (ClienteRepository).

---

### Como funciona a arquitetura de microserviços?

**Resposta:**
É uma abordagem onde dividimos a aplicação em serviços menores, independentes e especializados. Cada microserviço:

**Características:**
- **Tem seu próprio banco de dados:** Não compartilha dados diretamente com outros serviços
- **Deploy independente:** Posso atualizar um sem afetar outros
- **Tecnologia independente:** Cada um pode usar linguagem/framework diferentes
- **Time autônomo:** Um time cuida do serviço end-to-end

**Comunicação:**
- **Síncrona:** REST, gRPC (quando precisa de resposta imediata)
- **Assíncrona:** Mensageria como Kafka, RabbitMQ (para desacoplamento)

**Padrões importantes:**
- **API Gateway:** Ponto de entrada único
- **Service Discovery:** Serviços se encontram dinamicamente
- **Circuit Breaker:** Evita cascata de falhas
- **Saga:** Transações distribuídas

**Trade-offs que considero:**

| Benefícios | Desafios |
|------------|----------|
| Escala independente | Complexidade operacional |
| Times autônomos | Transações distribuídas |
| Deploy independente | Latência de rede |
| Falha isolada | Consistência eventual |
| Tecnologia heterogênea | Monitoramento distribuído |

**Quando usar:**
- Times grandes que precisam de autonomia
- Partes do sistema com escalas diferentes
- Quando já entende bem o domínio (não para MVP)

**Quando NÃO usar:**
- MVP, prova de conceito
- Times pequenos (<10 pessoas)
- Quando não tem observabilidade madura

---

### O que é arquitetura Hexagonal?

**Resposta:**
Também conhecida como Ports and Adapters, é um padrão arquitetural criado por Alistair Cockburn que isola a lógica de negócio das dependências externas.

**Estrutura:**

```
┌─────────────────────────────────────────────────────────┐
│                 ADAPTERS (Externos)                      │
│  ┌──────────────┐                    ┌───────────────┐  │
│  │  Controller  │                    │  JPA Adapter  │  │
│  │  (REST API)  │                    │  (Postgres)   │  │
│  └──────┬───────┘                    └───────┬───────┘  │
│         │                                    │          │
│  ┌──────▼────────────────────────────────────▼───────┐  │
│  │              PORTS (Interfaces)                    │  │
│  │  ┌─────────────────────────────────────────────┐  │  │
│  │  │           APPLICATION LAYER                  │  │  │
│  │  │  ┌───────────────────────────────────────┐  │  │  │
│  │  │  │            DOMAIN LAYER               │  │  │  │
│  │  │  │    (Entidades, VOs, Regras)           │  │  │  │
│  │  │  └───────────────────────────────────────┘  │  │  │
│  │  └─────────────────────────────────────────────┘  │  │
│  └───────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────┘
```

**Conceitos chave:**

**Ports (Portas):**
Interfaces que definem como o mundo externo interage com a aplicação.
- **Ports de Entrada (Driving):** Use Cases, como `CadastrarClienteUseCase`
- **Ports de Saída (Driven):** Repositórios, como `ClienteRepository`

**Adapters (Adaptadores):**
Implementações concretas das ports.
- **Adapters de Entrada:** `ClienteController` (HTTP → Use Case)
- **Adapters de Saída:** `ClienteRepositoryAdapter` (Use Case → JPA)

**Benefícios que experimento:**

1. **Testabilidade:** Testo domínio sem banco, sem web
2. **Independência de frameworks:** Domínio não conhece Spring
3. **Troca fácil de tecnologias:** Posso trocar PostgreSQL por MongoDB trocando só o adapter
4. **Código organizado:** Cada coisa no seu lugar

**No meu ConsignadoHub:**
```
adapter/in/web/          → Controllers (HTTP)
adapter/out/persistence/ → JPA (Banco)
application/port/in/     → Use Cases (interfaces)
application/port/out/    → Repositories (interfaces)
application/service/     → Implementações dos Use Cases
domain/model/            → Entidades
domain/vo/               → Value Objects
domain/exception/        → Exceções de domínio
```

---

### E outras arquiteturas? Pode citar algumas?

**Resposta:**
Além de Hexagonal, existem várias arquiteturas que uso ou conheço:

**Monolítica:**
Tudo em uma aplicação. Simples para começar, difícil de escalar partes independentemente.
- **Quando usar:** MVP, times pequenos, prova de conceito
- **Quando evitar:** Quando partes diferentes têm escalas muito diferentes

**Microserviços:**
Serviços pequenos, independentes, com seu próprio banco.
- **Quando usar:** Times grandes, escala independente necessária
- **Quando evitar:** MVP, times pequenos, sem observabilidade madura

**Event-Driven:**
Comunicação baseada em eventos. Serviços publicam/consomem eventos.
- **Quando usar:** Desacoplamento forte, eventual consistency OK
- **Quando evitar:** Quando precisa de consistência imediata

**Serverless:**
Funções executadas sob demanda (AWS Lambda, Azure Functions).
- **Quando usar:** Workloads variáveis, eventos esporádicos
- **Quando evitar:** Processamento contínuo, cold start é problema

**CQRS (Command Query Responsibility Segregation):**
Modelos separados para leitura e escrita, potencialmente bancos diferentes.
- **Quando usar:** Alto volume de leitura, Event Sourcing
- **Quando evitar:** CRUD simples, complexidade desnecessária

**Layered (Camadas):**
Tradicional: Presentation → Business → Data Access.
- **Quando usar:** Aplicações simples, equipe acostumada
- **Quando evitar:** Quando quer isolar domínio de frameworks

---

### O que é Saga Pattern?

**Resposta:**
Saga é um padrão para gerenciar transações distribuídas em arquiteturas de microserviços, onde não podemos usar transações ACID tradicionais.

**O problema:**
Em um monolito, posso fazer:
```java
@Transactional
public void criarContrato() {
    salvarContrato();
    atualizarMargem();
    enviarParaAverbacao();
    // Se qualquer um falhar, tudo faz rollback
}
```

Em microserviços, cada operação está em um serviço diferente com seu banco. Preciso de Saga.

**Como funciona:**
Uma Saga é uma sequência de transações locais. Cada serviço:
1. Executa sua transação local
2. Publica um evento
3. Próximo serviço reage ao evento

Se alguma etapa falhar, executa **compensações** (rollback semântico):

```
Contrato → Averbação → Liberação
    ↓          ↓           ↓
   OK         OK        FALHOU
    ↓          ↓
Compensar ← Compensar
```

**Tipos de Saga:**

**Choreography (Coreografado):**
Cada serviço sabe o que fazer quando recebe um evento. Descentralizado.
```
ContratoService --evento--> AverbacaoService --evento--> LiberacaoService
```
- **Vantagem:** Simples, desacoplado
- **Desvantagem:** Fluxo difícil de visualizar

**Orchestration (Orquestrado):**
Um orquestrador central controla o fluxo.
```
Orquestrador --> ContratoService
            --> AverbacaoService  
            --> LiberacaoService
```
- **Vantagem:** Fluxo claro, centralizado
- **Desvantagem:** Orquestrador é ponto único de falha

**No contexto de crédito consignado:**
1. **ContratoService:** Cria contrato (compensação: marcar como cancelado)
2. **INSSService:** Averba no INSS (compensação: desaverbar)
3. **PagamentoService:** Libera dinheiro (compensação: solicitar estorno)

---

### Qual a diferença entre CQS e CQRS?

**Resposta:**
Essa distinção é importante e muita gente confunde:

**CQS (Command Query Separation):**
Princípio de design onde métodos são ou Commands (alteram estado) ou Queries (leem estado), nunca ambos.

```java
// Command: altera estado, não retorna dados
void cadastrar(Cliente cliente);

// Query: lê dados, não altera estado
Cliente buscarPorCpf(CPF cpf);
```

No meu projeto, aplico separando interfaces:
```java
interface CadastrarClienteUseCase {  // Command
    ClienteId executar(CadastrarClienteCommand command);
}

interface BuscarClienteQuery {  // Query
    Optional<Cliente> buscarPorCpf(CPF cpf);
}
```

**Mesmo banco, interfaces separadas.** Complexidade baixa.

**CQRS (Command Query Responsibility Segregation):**
Arquitetura onde temos modelos diferentes para leitura e escrita, potencialmente bancos diferentes.

```
┌──────────────┐         ┌──────────────┐
│   Commands   │         │   Queries    │
└──────┬───────┘         └──────┬───────┘
       │                        │
       ▼                        ▼
┌──────────────┐         ┌──────────────┐
│ Write Model  │  ─────► │ Read Model   │
│  (DynamoDB)  │ eventos │ (PostgreSQL) │
└──────────────┘         └──────────────┘
```

**Bancos diferentes, modelos diferentes.** Complexidade alta.

**Quando usar cada:**

| Situação | Padrão |
|----------|--------|
| CRUD simples | Nem CQS (normal) |
| Separar leitura/escrita logicamente | CQS |
| Alto volume de leitura vs escrita | CQRS |
| Event Sourcing | CQRS (obrigatório) |

---

## 💾 4. BANCO DE DADOS

### Qual a diferença entre banco de dados relacional e não-relacional e quando utilizar cada um?

**Resposta:**
Esta é uma pergunta fundamental para arquitetura:

**Bancos Relacionais (SQL):**
Organizam dados em tabelas com linhas e colunas, relacionadas por chaves.

Características:
- Schema fixo e estruturado
- Garantias ACID
- SQL para queries complexas
- Joins eficientes
- Integridade referencial

Exemplos: PostgreSQL, MySQL, Oracle, SQL Server

**Quando usar:**
- Dados altamente estruturados
- Transações financeiras (ACID é crítico)
- Relatórios complexos com muitos joins
- Relacionamentos complexos entre entidades
- Quando consistência imediata é necessária

**Bancos Não-Relacionais (NoSQL):**
Várias categorias com modelos diferentes:

| Tipo | Exemplos | Uso |
|------|----------|-----|
| Documentos | MongoDB, Couchbase | Dados semi-estruturados |
| Chave-Valor | Redis, DynamoDB | Cache, sessões |
| Colunar | Cassandra, HBase | Analytics, séries temporais |
| Grafos | Neo4j | Relacionamentos complexos |

Características:
- Schema flexível
- Eventual consistency (geralmente)
- Escala horizontal mais fácil
- Otimizado para padrões específicos de acesso

**Quando usar:**
- Dados semi ou não estruturados (logs, documentos)
- Alta escala de escrita
- Esquema que muda frequentemente
- Quando eventual consistency é aceitável
- Cache de alta performance

**No meu projeto ConsignadoHub:**
Uso PostgreSQL (relacional) porque:
- Dados de clientes são estruturados
- Preciso de ACID para transações financeiras
- Relatórios com joins são importantes

Para Event Sourcing (futuro), usaria DynamoDB (NoSQL) pelo alto throughput de escrita.

---

### Explique transações ACID.

**Resposta:**
ACID é um acrônimo que define as propriedades que garantem confiabilidade em transações de banco de dados:

**A - Atomicidade (Atomicity):**
Tudo ou nada. Uma transação é indivisível. Se qualquer parte falha, toda a transação é revertida.

Exemplo prático:
```
Transferência bancária:
1. Debitar R$100 da conta A
2. Creditar R$100 na conta B

Com atomicidade: Se o crédito falhar, o débito é revertido.
Sem atomicidade: Dinheiro some (débito OK, crédito falhou).
```

**C - Consistência (Consistency):**
A transação leva o banco de um estado válido para outro estado válido. Todas as regras de integridade são respeitadas.

Exemplo: Se tenho uma constraint de saldo >= 0, a transação que deixaria saldo negativo falha.

**I - Isolamento (Isolation):**
Transações concorrentes não interferem umas nas outras. É como se cada uma executasse sozinha.

Níveis de isolamento (do mais permissivo ao mais restritivo):
- READ UNCOMMITTED: Vê dados não commitados (sujeira)
- READ COMMITTED: Só vê dados commitados
- REPEATABLE READ: Leituras consistentes na mesma transação
- SERIALIZABLE: Como se executasse em série

**D - Durabilidade (Durability):**
Uma vez que a transação é commitada, ela persiste mesmo em caso de falha do sistema (queda de energia, crash).

**No Spring:**
```java
@Transactional
public void transferir(Conta origem, Conta destino, BigDecimal valor) {
    origem.debitar(valor);  // Se isso OK mas...
    destino.creditar(valor); // ...isso falhar, origem é revertido
}
```

---

### O que é o problema N+1 e como resolver?

**Resposta:**
O problema N+1 é um dos bugs de performance mais comuns em aplicações com ORM (JPA/Hibernate).

**O problema:**
Quando tenho uma relação lazy e acesso os itens relacionados em um loop:

```java
List<Pedido> pedidos = pedidoRepository.findAll(); // 1 query
for (Pedido pedido : pedidos) {
    System.out.println(pedido.getCliente().getNome()); // N queries!
}
```

Se tenho 100 pedidos: 1 query inicial + 100 queries para buscar cada cliente = 101 queries!

**Soluções:**

**1. JOIN FETCH (JPQL):**
```java
@Query("SELECT p FROM Pedido p JOIN FETCH p.cliente")
List<Pedido> findAllWithCliente();
```

**2. @EntityGraph:**
```java
@EntityGraph(attributePaths = {"cliente"})
List<Pedido> findAll();
```

**3. @BatchSize:**
```java
@OneToMany
@BatchSize(size = 25)
private List<Item> itens;
// Em vez de N queries, faz N/25 queries
```

**4. Projections/DTOs:**
```java
@Query("SELECT new com.app.dto.PedidoResumo(p.id, c.nome) FROM Pedido p JOIN p.cliente c")
List<PedidoResumo> findResumos();
```

**Como detectar:**
- Logs de SQL com `show-sql: true`
- Ferramentas como P6Spy
- APM (Application Performance Monitoring)

---

## 📬 5. MENSAGERIA

### Como funciona um sistema de mensageria?

**Resposta:**
Um sistema de mensageria permite comunicação assíncrona entre aplicações através de filas ou tópicos. É fundamental em arquiteturas distribuídas.

**Funcionamento básico:**

```
┌──────────┐         ┌──────────────┐         ┌──────────┐
│ Produtor │ ──────► │ Broker/Fila  │ ──────► │Consumidor│
└──────────┘         └──────────────┘         └──────────┘
```

1. **Produtor** envia mensagem para o broker
2. **Broker** armazena a mensagem (persistência opcional)
3. **Consumidor** busca/recebe mensagens no seu ritmo
4. **Acknowledgment** confirma processamento

**Vantagens:**

| Benefício | Descrição |
|-----------|-----------|
| Desacoplamento | Produtor não conhece consumidor |
| Resiliência | Mensagens sobrevivem a falhas |
| Escalabilidade | Adicionar consumidores sob demanda |
| Buffer | Absorve picos de carga |
| Assíncrono | Não bloqueia o produtor |

**Exemplo prático no consignado:**
```
ContratoService publica "ContratoAssinado"
    │
    ├──► NotificacaoService: Envia email
    ├──► AverbacaoService: Inicia processo no INSS
    └──► AuditoriaService: Registra log
```

Sem mensageria, o ContratoService teria que chamar cada um desses serviços sincronamente, acoplando tudo.

---

### Qual a diferença entre RabbitMQ e Kafka?

**Resposta:**
São ferramentas diferentes para problemas diferentes:

**RabbitMQ:**
- Message Broker tradicional baseado em AMQP
- Modelo: fila de mensagens
- Mensagens são removidas após consumo
- Roteamento flexível com exchanges
- Garantia de entrega por mensagem

**Ideal para:**
- Comunicação entre microsserviços
- Filas de trabalho (work queues)
- RPC assíncrono
- Quando precisa de roteamento complexo

**Kafka:**
- Plataforma de streaming distribuído
- Modelo: log append-only
- Mensagens mantidas por período configurável (retention)
- Múltiplos consumidores podem ler mesma mensagem
- Otimizado para throughput massivo

**Ideal para:**
- Event sourcing
- Logs e métricas
- Processamento de streams em tempo real
- Quando precisa de replay de eventos
- Alta escala (milhões de mensagens/segundo)

**Comparação:**

| Aspecto | RabbitMQ | Kafka |
|---------|----------|-------|
| Modelo | Message Queue | Event Log |
| Retention | Até consumir | Configurável (dias/semanas) |
| Throughput | Alto | Muito alto |
| Replay | Não (padrão) | Sim |
| Complexidade | Menor | Maior |
| Caso de uso | Mensageria | Streaming |

**Minha escolha:**
- Comunicação microserviços simples → RabbitMQ
- Event Sourcing, analytics, alta escala → Kafka

---

### Explique os conceitos de fila e tópico em mensageria.

**Resposta:**

**Fila (Queue) - Modelo Ponto-a-Ponto:**
Cada mensagem é consumida por apenas um consumidor, mesmo que existam vários consumidores escutando.

```
Produtor ──► [Fila] ──► Consumidor 1
                   └──► Consumidor 2 (compete pela mensagem)
```

Útil para: Distribuir trabalho entre workers. Se tenho 1000 tarefas e 10 workers, cada um processa ~100.

**Tópico (Topic) - Modelo Pub/Sub:**
Uma mensagem é entregue a TODOS os consumidores inscritos.

```
                      ┌──► Consumidor 1 (recebe cópia)
Produtor ──► [Tópico]─┼──► Consumidor 2 (recebe cópia)
                      └──► Consumidor 3 (recebe cópia)
```

Útil para: Broadcasting de eventos. Quando "ContratoAssinado" acontece, vários serviços precisam reagir.

**No RabbitMQ:**
- Fila: tipo de exchange "direct" ou "default"
- Tópico: exchange tipo "fanout" ou "topic"

**No Kafka:**
- Tudo é tópico, mas com consumer groups posso simular fila (só um consumer do grupo processa cada mensagem)

---

### Como garantir que mensagens sejam processadas com sucesso?

**Resposta:**
Garantir processamento confiável requer várias estratégias:

**1. Acknowledgment (ACK):**
Só confirmo recebimento após processar com sucesso. Se der erro, a mensagem volta para a fila.

```java
@RabbitListener(queues = "contratos")
public void processar(Contrato contrato, Channel channel, 
                      @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
    try {
        service.processar(contrato);
        channel.basicAck(tag, false); // Sucesso
    } catch (Exception e) {
        channel.basicNack(tag, false, true); // Requeue
    }
}
```

**2. Dead Letter Queue (DLQ):**
Mensagens que falharam após múltiplas tentativas vão para uma fila especial para análise posterior.

```
Fila Principal ──► Falhou 3x ──► Dead Letter Queue
                                       │
                               Análise manual
```

**3. Retry Policies com Backoff Exponencial:**
Primeira tentativa imediata, segunda após 1s, terceira após 2s, quarta após 4s...

```java
@RetryableTopic(
    attempts = "4",
    backoff = @Backoff(delay = 1000, multiplier = 2)
)
```

**4. Idempotência:**
Processar a mesma mensagem múltiplas vezes não causa efeitos colaterais. Uso IDs únicos:

```java
public void processar(Mensagem msg) {
    if (jaProcessada(msg.getId())) {
        return; // Ignora duplicata
    }
    // Processa...
    marcarComoProcessada(msg.getId());
}
```

**5. Transações (Outbox Pattern):**
Garante atomicidade entre salvar no banco e publicar mensagem:

```java
@Transactional
public void criarContrato(Contrato contrato) {
    repository.save(contrato);
    outboxRepository.save(new Outbox("ContratoEvent", contrato));
    // Processo separado publica do Outbox para Kafka
}
```

---

## ☕ 6. JAVA CORE E COLLECTIONS

### Explique a diferença entre List, Set e Map.

**Resposta:**

**List:**
Coleção ordenada que permite elementos duplicados e acesso por índice.

```java
List<String> nomes = new ArrayList<>();
nomes.add("Ana");
nomes.add("Ana"); // Permite duplicata
nomes.get(0);     // Acesso por índice
```

Implementações:
- **ArrayList:** Acesso rápido O(1), inserção no meio O(n)
- **LinkedList:** Inserção rápida O(1) no início/fim, acesso O(n)

**Set:**
Coleção que não permite duplicatas.

```java
Set<String> emails = new HashSet<>();
emails.add("a@x.com");
emails.add("a@x.com"); // Não adiciona, já existe
```

Implementações:
- **HashSet:** Sem ordem, mais rápido
- **LinkedHashSet:** Mantém ordem de inserção
- **TreeSet:** Ordenado naturalmente ou por comparator

**Map:**
Armazena pares chave-valor, onde as chaves são únicas.

```java
Map<CPF, Cliente> clientes = new HashMap<>();
clientes.put(cpf, cliente);
Cliente c = clientes.get(cpf);
```

Implementações:
- **HashMap:** Mais comum, sem ordem
- **LinkedHashMap:** Mantém ordem de inserção
- **TreeMap:** Ordenado por chave

---

### O que são exceções tratáveis e não tratáveis?

**Resposta:**
Java divide exceções em duas categorias principais:

**Checked Exceptions (Tratáveis):**
- Herdam de `Exception` (exceto RuntimeException)
- Compilador OBRIGA tratamento (try/catch ou throws)
- Representam problemas recuperáveis
- Exemplos: `IOException`, `SQLException`, `FileNotFoundException`

```java
public void lerArquivo(String path) throws IOException {
    // Sou obrigado a declarar ou tratar
    Files.readString(Path.of(path));
}
```

**Unchecked Exceptions (Não Tratáveis):**
- Herdam de `RuntimeException`
- Compilador NÃO obriga tratamento
- Geralmente indicam erros de programação
- Exemplos: `NullPointerException`, `IllegalArgumentException`, `IndexOutOfBoundsException`

```java
public void processar(String valor) {
    if (valor == null) {
        throw new IllegalArgumentException("Valor não pode ser nulo");
        // Não preciso declarar throws
    }
}
```

**Best Practices:**

1. **Use Unchecked para erros de domínio:**
```java
public class DomainException extends RuntimeException {
    // Exceções de negócio não devem poluir assinaturas
}
```

2. **Wrapped Exceptions:**
```java
try {
    // Operação que lança IOException
} catch (IOException e) {
    throw new InfrastructureException("Erro ao acessar arquivo", e);
}
```

3. **Não use exceções para fluxo de controle:**
```java
// Ruim
try {
    return lista.get(0);
} catch (IndexOutOfBoundsException e) {
    return null;
}

// Bom
return lista.isEmpty() ? null : lista.get(0);
```

---

## 🌐 7. HTTP, REST E COMUNICAÇÃO

### Quais são os principais métodos HTTP e quando usar cada um?

**Resposta:**

| Método | Ação | Idempotente? | Exemplo |
|--------|------|--------------|---------|
| **GET** | Buscar/Ler | ✅ Sim | `GET /clientes/123` |
| **POST** | Criar | ❌ Não | `POST /clientes` (body com dados) |
| **PUT** | Atualizar completo | ✅ Sim | `PUT /clientes/123` (substitui tudo) |
| **PATCH** | Atualizar parcial | ✅ Sim | `PATCH /clientes/123` (só campos enviados) |
| **DELETE** | Remover | ✅ Sim | `DELETE /clientes/123` |

**O que é idempotência?**
Executar a mesma operação múltiplas vezes produz o mesmo resultado.

```
PUT /clientes/123 { "nome": "João" }  → Sempre deixa nome = João
PUT /clientes/123 { "nome": "João" }  → Mesmo resultado (idempotente)

POST /clientes { "nome": "João" }  → Cria cliente ID 1
POST /clientes { "nome": "João" }  → Cria cliente ID 2 (NÃO idempotente)
```

**Por que idempotência importa?**
Em sistemas distribuídos, retries são comuns. Se o cliente não recebe resposta (timeout), ele reenvia. Com operações idempotentes, reenviar é seguro.

---

### Quais são os principais códigos de status HTTP?

**Resposta:**

| Faixa | Categoria | Códigos Comuns |
|-------|-----------|----------------|
| **2xx** | Sucesso | 200 OK, 201 Created, 204 No Content |
| **3xx** | Redirecionamento | 301 Moved, 304 Not Modified |
| **4xx** | Erro do Cliente | 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 422 Unprocessable |
| **5xx** | Erro do Servidor | 500 Internal Error, 502 Bad Gateway, 503 Service Unavailable |

**Códigos que uso no ConsignadoHub:**

| Situação | Código | Por quê |
|----------|--------|---------|
| Cliente cadastrado | `201 Created` | Recurso criado |
| Cliente encontrado | `200 OK` | Sucesso |
| CPF inválido | `422 Unprocessable` | Dados válidos sintaticamente, mas inválidos semanticamente |
| Cliente não encontrado | `404 Not Found` | Recurso não existe |
| CPF já cadastrado | `409 Conflict` | Conflito de negócio |
| Erro interno | `500 Internal` | Algo quebrou no servidor |

---

### O que é REST e quais são as boas práticas?

**Resposta:**

REST (Representational State Transfer) é um estilo arquitetural para APIs.

**Princípios REST:**

1. **Stateless:** Cada request contém toda informação necessária (sem sessão no servidor)
2. **Client-Server:** Separação clara
3. **Uniform Interface:** URLs representam recursos, verbos HTTP representam ações
4. **Cacheable:** Respostas podem ser cacheadas

**Boas práticas de URLs:**

```
✅ BOM (substantivos, plural)
GET    /clientes           → Lista todos
GET    /clientes/123       → Busca um
POST   /clientes           → Cria
PUT    /clientes/123       → Atualiza
DELETE /clientes/123       → Remove

❌ RUIM (verbos na URL)
POST /criarCliente
GET  /buscarCliente/123
POST /deletarCliente/123
```

**Boas práticas de resposta:**
```json
// Sucesso
{
  "id": "123",
  "cpf": "529.982.247-25",
  "nome": "Maria Silva"
}

// Erro padronizado
{
  "code": "CPF_INVALIDO",
  "message": "CPF deve ter 11 dígitos",
  "field": "cpf",
  "timestamp": "2026-01-08T10:00:00"
}
```

---

### Quando usar comunicação síncrona vs assíncrona?

**Resposta:**

| Aspecto | Síncrona (REST/gRPC) | Assíncrona (Kafka/RabbitMQ) |
|---------|---------------------|----------------------------|
| **Resposta** | Imediata | Eventual |
| **Acoplamento** | Alto | Baixo |
| **Disponibilidade** | Depende do destino | Desacoplado |
| **Complexidade** | Menor | Maior |
| **Rastreabilidade** | Fácil | Requer correlationId |

**Use SÍNCRONA quando:**
- Precisa de resposta imediata
- Operação simples e rápida
- Validação que bloqueia o fluxo
- UI esperando resposta

**Exemplos:**
```
Cliente → GET /clientes/123 → Resposta agora
Cliente → POST /simulacoes → Resultado imediato
```

**Use ASSÍNCRONA quando:**
- Operação demorada (segundos/minutos)
- Não precisa de resposta imediata
- Desacoplar serviços
- Alta carga (buffer de mensagens)
- Múltiplos consumidores do mesmo evento

**Exemplos:**
```
ContratoAssinado → [Kafka] → EmailService (envia email)
                          → AuditoriaService (registra log)
                          → INSSService (averba)
```

**Decisão no ConsignadoHub:**

| Operação | Tipo | Por quê |
|----------|------|---------|
| Cadastrar cliente | Síncrono | UI espera confirmação |
| Buscar cliente | Síncrono | Precisa do dado agora |
| Simular empréstimo | Síncrono | Mostra resultado na tela |
| Enviar email | Assíncrono | Pode demorar, não bloqueia |
| Notificar INSS | Assíncrono | Serviço externo, desacopla |
| Processar averbação | Assíncrono | Processo longo |

---

### O que acontece se o serviço destino estiver fora do ar?

**Resposta:**

**Comunicação Síncrona:**
- Request falha imediatamente
- Cliente recebe erro 5xx
- Precisa de: Circuit Breaker, Retry, Fallback

```java
// Com Resilience4j
@CircuitBreaker(name = "inss", fallbackMethod = "fallback")
@Retry(name = "inss")
public Averbacao averbar(Contrato contrato) {
    return inssClient.averbar(contrato);
}

public Averbacao fallback(Contrato contrato, Exception ex) {
    return Averbacao.pendente(); // Fallback gracioso
}
```

**Comunicação Assíncrona:**
- Mensagem fica na fila esperando
- Consumer processa quando voltar
- Sistema origem não é afetado

```
Produtor → [Fila] → Consumer (offline)
                    ↓
           Mensagem espera
                    ↓
           Consumer volta → Processa
```

**Por isso, para integrações externas (INSS, bancos), prefiro assíncrono** - não fico refém da disponibilidade do outro sistema.

---

### Diferença entre REST e gRPC?

**Resposta:**

| Aspecto | REST | gRPC |
|---------|------|------|
| Protocolo | HTTP/1.1 (texto) | HTTP/2 (binário) |
| Formato | JSON (legível) | Protocol Buffers (compacto) |
| Performance | Boa | Excelente |
| Streaming | Limitado | Bidirecional nativo |
| Contrato | OpenAPI/Swagger | .proto files |
| Browser | ✅ Suportado | ⚠️ Limitado |

**Quando usar REST:**
- APIs públicas
- Integração com frontend
- Quando legibilidade importa
- Time não conhece gRPC

**Quando usar gRPC:**
- Comunicação interna entre microserviços
- Alta performance necessária
- Streaming de dados
- Contratos fortemente tipados

---

## 🍃 8. SPRING FRAMEWORK


### O que é Inversão de Controle (IoC) e Injeção de Dependência (DI)?

**Resposta:**

**Inversão de Controle (IoC):**
É um princípio onde o controle da criação e gerenciamento de objetos é "invertido". Em vez de eu criar os objetos que preciso, um container externo (no caso, o Spring) faz isso por mim.

Sem IoC:
```java
public class ClienteService {
    private ClienteRepository repository = new ClienteJpaRepository();
    // Eu controlo a criação, estou acoplado à implementação
}
```

Com IoC:
```java
public class ClienteService {
    private final ClienteRepository repository;
    
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
        // Spring controla a criação e me entrega pronto
    }
}
```

**Injeção de Dependência (DI):**
É a FORMA como o IoC é implementado. O container injeta as dependências necessárias nas classes.

**Tipos de injeção:**

1. **Por Construtor (Preferido):**
```java
@Service
@RequiredArgsConstructor // Lombok gera construtor
public class ClienteService {
    private final ClienteRepository repository; // Imutável
}
```

Vantagens: imutabilidade, dependências explícitas, fácil de testar, falha rápida.

2. **Por Setter:**
```java
@Service
public class ClienteService {
    private ClienteRepository repository;
    
    @Autowired
    public void setRepository(ClienteRepository repository) {
        this.repository = repository;
    }
}
```

Quando usar: dependências opcionais.

3. **Por Campo (Evitar):**
```java
@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;
}
```

Desvantagens: difícil de testar, dependências ocultas.

**Por que usar DI:**
- Desacoplamento
- Testabilidade (posso injetar mocks)
- Configuração centralizada
- Código mais limpo

---

## 🧪 9. TESTES

### O que é TDD e como você aplica?

**Resposta:**
TDD (Test-Driven Development) é uma metodologia onde escrevemos o teste ANTES da implementação.

**Ciclo TDD:**

1. 🔴 **RED:** Escrever um teste que falha
2. 🟢 **GREEN:** Escrever o mínimo de código para passar
3. 🔵 **REFACTOR:** Melhorar o código mantendo testes verdes

**Exemplo prático no meu projeto:**

```java
// 1. RED - Teste primeiro
@Test
void deveCriarCPFValido() {
    CPF cpf = new CPF("52998224725");
    assertThat(cpf.valor()).isEqualTo("52998224725");
}

@Test
void deveRejeitarCPFNulo() {
    assertThatThrownBy(() -> new CPF(null))
        .isInstanceOf(DomainException.class);
}

// Testes falham porque CPF não existe ainda

// 2. GREEN - Implementação mínima
public record CPF(String valor) {
    public CPF {
        if (valor == null) throw DomainException.required("cpf");
    }
}

// Testes passam!

// 3. REFACTOR - Melhorar
public record CPF(String valor) {
    public CPF {
        if (valor == null) throw DomainException.required("cpf");
        valor = valor.replaceAll("[^0-9]", ""); // Remove formatação
        if (!isValid(valor)) throw DomainException.invalidField("cpf", "inválido");
    }
}
```

**Benefícios que experimento:**
- Código testável por design
- Documentação viva (testes explicam comportamento)
- Confiança para refatorar
- Menos bugs em produção

**No ConsignadoHub:** 78 testes, todos escritos antes da implementação.

---

## 🐳 10. DEVOPS E DEPLOY

### Boas práticas para deploy seguro

**Resposta:**

**1. Feature Flags:**
Código vai para produção DESLIGADO. Posso ligar/desligar sem redeploy.

```java
if (featureFlags.isEnabled("nova-simulacao")) {
    return novaSimulacao.calcular();
}
return simulacaoAntiga.calcular();
```

**2. Canary Release:**
Libera para percentual pequeno de usuários primeiro:
- 1% por 1 hora
- 10% por 4 horas
- 50% por 1 dia
- 100%

Se métricas degradarem, faz rollback automático.

**3. Blue/Green Deployment:**
Dois ambientes idênticos. Deploy no ambiente inativo, troca o roteamento.

```
Load Balancer ──► Blue (v1.0) [ATIVO]
              └► Green (v1.1) [INATIVO, testando]
              
# Troca após validar
Load Balancer ──► Green (v1.1) [ATIVO]
              └► Blue (v1.0) [STANDBY para rollback]
```

**4. Health Checks:**
Kubernetes/Load balancer só roteia para instâncias saudáveis.

```yaml
livenessProbe:
  httpGet:
    path: /actuator/health/liveness
    port: 8080
readinessProbe:
  httpGet:
    path: /actuator/health/readiness
    port: 8080
```

**5. Circuit Breaker:**
Se dependência está degradada, falha rápido em vez de timeout.

**6. Rollback Automatizado:**
Se taxa de erro aumentar após deploy, reverte automaticamente.

**7. Smoke Tests Pós-Deploy:**
Testes automáticos que validam funcionalidades críticas após deploy.

---

### Como funciona o versionamento de aplicações? (Semantic Versioning)

**Resposta:**

Uso Semantic Versioning (SemVer) no formato `MAJOR.MINOR.PATCH`:

```
  2  .  1  .  3
  │     │     │
  │     │     └── PATCH: Bug fixes (correções)
  │     └──────── MINOR: Novas features (sem quebrar)
  └────────────── MAJOR: Breaking changes (quebra compatibilidade)
```

**Quando incrementar cada número:**

| Situação | Exemplo | Por quê |
|----------|---------|---------|
| **PATCH** (1.0.0 → 1.0.1) | Fix de null pointer | Bug fix, não muda comportamento |
| **MINOR** (1.0.1 → 1.1.0) | Novo endpoint | Nova feature, compatível com versão anterior |
| **MAJOR** (1.1.0 → 2.0.0) | Removeu campo obrigatório | Breaking change, quebra clientes existentes |

**Regras importantes:**
- Quando incrementa MINOR, PATCH volta para 0
- Quando incrementa MAJOR, MINOR e PATCH voltam para 0
- Adicionar campo opcional é MINOR (não quebra)
- Remover campo é MAJOR (quebra)

**Versões especiais:**

| Sufixo | Significado | Exemplo |
|--------|-------------|---------|
| `-SNAPSHOT` | Em desenvolvimento | `1.5.0-SNAPSHOT` |
| `-alpha` | Muito instável | `2.0.0-alpha.1` |
| `-beta` | Testando features | `2.0.0-beta.3` |
| `-RC` | Release Candidate | `2.0.0-RC1` |

**Resposta resumida para entrevista:**

*"Uso Semantic Versioning - MAJOR.MINOR.PATCH. PATCH para bug fixes, MINOR para features novas que são backward compatible, MAJOR para breaking changes. Por exemplo, se adiciono um campo opcional na API, é MINOR. Se removo um campo, é MAJOR porque quebra clientes existentes. No CI/CD, usamos tags no Git (v1.2.3) e o pipeline faz o build automaticamente."*

---

## 💡 11. PERGUNTAS COMPORTAMENTAIS


### Me fale sobre um desafio técnico que você resolveu

**Resposta 1 - Consolidação de Exceções:**

*"No meu projeto de crédito consignado, enfrentei o desafio de ter 16 classes de exceção diferentes - InvalidCPFException, InvalidEmailException, etc. Cada Value Object tinha sua própria exceção.*

*O problema é que isso gerava muito código duplicado e dificultava o tratamento centralizado de erros.*

*A solução foi consolidar em apenas 3 exceções com factory methods: DomainException, BusinessException e NotFoundException. Cada uma com código de erro padronizado.*

*O aprendizado foi que às vezes menos classes é melhor - códigos de erro bem definidos são mais flexíveis que hierarquias de exceção complexas."*

---

**Resposta 2 - Bug de CPF com Zeros à Esquerda:**

*"Tínhamos um bug onde clientes do Norte/Nordeste não conseguiam se cadastrar. O erro era 'CPF inválido'.*

*Descobri que CPFs que começam com zero estavam perdendo o zero quando salvos como número no banco. Quando reconstituído, virava 10 dígitos em vez de 11.*

*A solução foi usar String em vez de Long, criar um Value Object imutável que sempre mantém os zeros, e adicionar testes específicos.*

*Aprendi que CPF, CNPJ e CEP devem sempre ser tratados como Strings."*

---

**Resposta 3 - Problema N+1:**

*"O endpoint de listar clientes demorava 3 segundos para 100 registros. Em produção com 10.000, dava timeout.*

*Habilitei logs de SQL e vi 101 queries - o clássico problema N+1. Para cada cliente, uma query buscando benefícios.*

*A solução foi usar JOIN FETCH e @EntityGraph. Para listagens simples, criei DTOs de projeção.*

*Tempo caiu de 3s para 50ms. Aprendi a sempre monitorar queries quando uso ORM."*

---

**Resposta 4 - Transação Sem Rollback:**

*"Clientes eram cadastrados mesmo quando o envio de email falhava. Deveria ser atômico.*

*O método tinha @Transactional, mas a exceção era IOException (checked). Spring só faz rollback para RuntimeException por padrão.*

*Adicionei rollbackFor = Exception.class e refatorei exceções de domínio para RuntimeException.*

*Criei uma regra: exceções de negócio devem ser unchecked."*

---

**Resposta 5 - Mensagens Duplicadas no Kafka:**

*"Clientes recebiam dois emails de confirmação. O consumer processava a mesma mensagem duas vezes.*

*O consumer commitava offset antes de processar. Se falhava e reiniciava, reprocessava.*

*Implementei idempotência com uma tabela de controle e idempotency-key no header da mensagem.*

*Em sistemas distribuídos, idempotência é obrigatória."*

---

**Resposta 6 - Testes Quebrando no CI:**

*"Testes passavam local mas falhavam no pipeline. Erro de conexão com banco.*

*Os testes dependiam de PostgreSQL em Docker local. No CI não tinha.*

*Usei Testcontainers para subir container PostgreSQL dinamicamente.*

*Testes reproduzíveis em qualquer ambiente."*

---

**Resposta 7 - Latência Alta em Consultas:**

*"Busca de cliente por CPF demorava 500ms. Deveria ser instantânea.*

*A coluna CPF não tinha índice. Full table scan em 100.000 registros.*

*Adicionei índice único via Flyway migration. Tempo caiu para 2ms.*

*Índices são obrigatórios em colunas usadas em WHERE."*

---

**Resposta 8 - Acoplamento Entre Serviços:**

*"CustomerService chamava SimulationService por REST síncrono. Quando Simulation caía, Customer também falhava.*

*Desacoplei com eventos: CustomerService publica 'ClienteCadastrado', SimulationService consome de forma assíncrona. Implementei Circuit Breaker para chamadas restantes.*

*CustomerService ficou independente."*

---

### O que você aprendeu recentemente?

**Resposta modelo:**

*"Recentemente desenvolvi um projeto completo de crédito consignado INSS onde aprendi a aplicar vários conceitos na prática:*

*Arquitetura Hexagonal - separei claramente Domain, Application e Adapters. O domínio não conhece Spring ou JPA.*

*TDD rigoroso - 78 testes, todos escritos antes da implementação. Isso me deu confiança para refatorar.*

*CQS vs CQRS - entendi a diferença prática. CQS é separar interfaces de leitura/escrita no mesmo banco. CQRS é ter bancos diferentes.*

*Tratamento de exceções enterprise - consolidar exceções com códigos padronizados em vez de muitas classes.*

*O maior aprendizado foi que teoria só se consolida com prática. Eu conhecia esses conceitos, mas implementar do zero mostrou os trade-offs reais."*

---

### 💡 Dicas para Contar Desafios Técnicos

1. **Use a estrutura STAR:** Situação, Tarefa, Ação, Resultado
2. **Seja específico com números:** "caiu de 3s para 50ms" é convincente
3. **Mostre aprendizado:** O que você levou para projetos futuros
4. **Não invente tecnologias que não conhece:** Você será questionado
5. **Prepare 3-4 histórias:** Diferentes tipos de problemas (performance, bugs, arquitetura)

---

## ⚠️ 12. RED FLAGS EM ENTREVISTAS


O que entrevistadores observam negativamente:

1. ❌ **Não admite que não sabe** - Inventar é pior que falar "não tenho experiência com isso"
2. ❌ **Respostas decoradas** - Cite exemplos próprios, não definições de livro
3. ❌ **Não conhece trade-offs** - "Microsserviços é sempre melhor" é red flag
4. ❌ **Não testa código** - "Testar é responsabilidade do QA"
5. ❌ **Culpa nos outros** - "O código legado era terrível, não tinha como fazer diferente"
6. ❌ **Não faz perguntas** - Mostra desinteresse

---

## ⚖️ 13. PERGUNTAS DE DECISÃO (TRADE-OFFS)

> Essas perguntas separam Pleno de Sênior. Mostre que você pensa em prós/contras.

### Monolito vs Microserviços - quando migrar?

**Resposta:**

**Comece com Monolito quando:**
- MVP, prova de conceito
- Time pequeno (< 10 devs)
- Domínio não está bem definido
- Não tem observabilidade madura

**Migre para Microserviços quando:**
- Times precisam de autonomia
- Partes do sistema têm escalas muito diferentes
- Deploy independente é crítico
- Já entende bem os bounded contexts

**No ConsignadoHub:**
*"Comecei como monolito modular. Cada módulo (customer, simulation, contract) é um bounded context separado. Quando precisar escalar, extraio para microserviço sem reescrever - a separação já existe."*

---

### Herança vs Composição - quando usar cada?

**Resposta:**

**Use Herança quando:**
- Existe relação "é um" clara (ContaCorrente É UMA Conta)
- Quer compartilhar implementação entre subclasses
- Hierarquia é estável e não vai mudar muito

**Use Composição quando:**
- Existe relação "tem um" (Cliente TEM Endereço)
- Precisa de flexibilidade (trocar comportamento em runtime)
- Quer evitar acoplamento forte

```java
// Herança: "é um"
class ContaCorrente extends Conta { }

// Composição: "tem um"
class Cliente {
    private Endereco endereco;  // Composto, não herdado
    private List<Beneficio> beneficios;
}
```

**Regra geral:** Prefira composição. Use herança só quando realmente faz sentido.

---

### Exception vs Optional - quando usar cada?

**Resposta:**

**Lance Exception quando:**
- Situação é realmente excepcional
- Não deveria acontecer em fluxo normal
- É um erro de programação ou violação de regra

```java
if (cpf == null) {
    throw DomainException.required("cpf");
}
```

**Retorne Optional quando:**
- Ausência de valor é esperada e válida
- Método de busca que pode não encontrar
- Quero forçar o chamador a tratar o caso vazio

```java
Optional<Cliente> buscarPorCpf(CPF cpf);

// Chamador é obrigado a tratar
cliente.orElseThrow(() -> NotFoundException.cliente(cpf));
```

**No ConsignadoHub:**
- `buscarPorCpf()` → Retorna `Optional` (pode não existir)
- Validação de CPF inválido → Lança `DomainException` (erro)

---

### DTO vs Entity direto na API - por que usar DTO?

**Resposta:**

**Problemas de expor Entity direto:**
- Vaza estrutura interna do domínio
- Mudança no banco quebra a API
- Serializa relacionamentos indesejados (N+1)
- Campos sensíveis expostos (senha, tokens)

**Benefícios do DTO:**
- Contrato de API estável
- Controle do que é exposto
- Evita serialização de lazy loading
- Pode ter formato diferente do domínio

```java
// DTO de entrada (Command)
record CadastrarClienteRequest(String cpf, String nome, String email);

// DTO de saída (Response)
record ClienteResponse(UUID id, String cpfFormatado, String nome);

// Entity (Domínio) - nunca exposta
class Cliente {
    private ClienteId id;
    private CPF cpf;
    private String nome;
}
```

**No ConsignadoHub:**
*"Uso Commands (entrada) e Responses (saída). O Controller converte DTO → Domain e Domain → DTO via Mappers."*

---

### Lazy vs Eager Loading - qual é o padrão correto?

**Resposta:**

**Padrão: SEMPRE LAZY**

```java
@OneToMany(fetch = FetchType.LAZY)  // Padrão para coleções
private List<Beneficio> beneficios;

@ManyToOne(fetch = FetchType.LAZY)  // Deve forçar - padrão é EAGER
private Cliente cliente;
```

**Por que Lazy?**
- Não carrega dados desnecessários
- Evita N+1 acidental
- Melhor performance inicial

**Quando preciso dos dados relacionados:**
```java
// JOIN FETCH quando sei que vou usar
@Query("SELECT c FROM Cliente c JOIN FETCH c.beneficios WHERE c.id = :id")
Cliente buscarComBeneficios(@Param("id") Long id);

// Ou @EntityGraph
@EntityGraph(attributePaths = {"beneficios"})
List<Cliente> findAll();
```

**Regra:** Configure LAZY, use FETCH quando precisar explicitamente.

---

### Cache Local vs Distribuído - quando usar cada?

**Resposta:**

| Aspecto | Cache Local (Caffeine) | Cache Distribuído (Redis) |
|---------|------------------------|---------------------------|
| Performance | Ultra rápida (memória) | Rápida (rede) |
| Consistência | Por instância | Compartilhado |
| Escalabilidade | Cada pod tem sua cópia | Uma fonte de verdade |
| Invalidação | Complexa entre pods | Simples (centralizado) |

**Use Cache Local quando:**
- Dados mudam raramente (configurações, taxas)
- Pode ter inconsistência temporária entre pods
- Alta frequência de leitura, mesmo dado

**Use Cache Distribuído quando:**
- Precisa de consistência entre instâncias
- Sessão de usuário
- Dados que mudam frequentemente
- Rate limiting

**No ConsignadoHub:**
*"Taxas de juros e configurações: cache local (Caffeine) com TTL de 5 minutos. Sessão do atendente: Redis (precisa consistência)."*

---

### Event Sourcing vs CRUD - quando vale a complexidade?

**Resposta:**

**CRUD tradicional:**
- Salva estado atual
- Histórico limitado (audit log separado)
- Simples de entender e implementar
- Maioria dos casos

**Event Sourcing:**
- Salva todos os eventos que aconteceram
- Estado é derivado dos eventos
- Histórico completo nativo
- Complexidade maior

**Use Event Sourcing quando:**
- Auditoria completa é requisito legal (financeiro)
- Precisa de "time travel" (ver estado em qualquer momento)
- Eventos são business value (não só técnico)
- N sistemas reagem aos mesmos eventos

**Use CRUD quando:**
- CRUD simples de cadastro
- Histórico não é crítico
- Time não tem experiência com ES
- MVP, precisa entregar rápido

**No ConsignadoHub:**
*"Cadastro de cliente: CRUD (simples, sem requisito de histórico). Contratos financeiros: Event Sourcing (regulação exige trilha de auditoria completa)."*

---

### Saga Choreography vs Orchestration - qual padrão?

**Resposta:**

| Aspecto | Choreography | Orchestration |
|---------|--------------|---------------|
| Controle | Descentralizado | Centralizador |
| Acoplamento | Baixo | Médio |
| Visibilidade | Difícil de rastrear | Fluxo claro |
| Complexidade | Distribuída | Centralizada |
| Ponto de falha | Nenhum central | Orquestrador |

**Choreography (coreografado):**
```
ContratoService --evento--> AverbacaoService --evento--> LiberacaoService
```
Cada serviço sabe reagir ao evento anterior.

**Orchestration (orquestrado):**
```
Orquestrador --> ContratoService
            --> AverbacaoService
            --> LiberacaoService
```
Um serviço central controla o fluxo.

**Quando usar cada:**
- **Choreography:** Fluxos simples (3-4 passos), times autônomos
- **Orchestration:** Fluxos complexos, precisa de visibilidade, compensações elaboradas

**No ConsignadoHub:**
*"Para o fluxo de contratação (contrato → averbação → liberação), usaria Orchestration porque o fluxo é crítico, preciso de visibilidade clara, e as compensações são complexas."*

---

### Record vs Class - quando usar cada?

**Resposta:**

**Record** (Java 14+): Classe imutável para transportar dados.

```java
public record CPF(String valor) {
    // Gerado automaticamente:
    // - Construtor
    // - Getters (valor(), não getValor())
    // - equals(), hashCode(), toString()
}
```

**Class tradicional:** Quando precisa de mutabilidade ou comportamento complexo.

```java
public class Cliente {
    private String nome;
    
    public void alterarNome(String novoNome) {
        this.nome = novoNome;  // Mutável
    }
}
```

**Quando usar Record:**
- Value Objects (CPF, Dinheiro, Email)
- DTOs (Request, Response, Command)
- Dados imutáveis
- Quando não precisa de herança

**Quando usar Class:**
- Entities com ciclo de vida (Cliente, Contrato)
- Precisa de mutabilidade
- Herança é necessária
- Lógica de negócio complexa

| Aspecto | Record | Class |
|---------|--------|-------|
| Imutabilidade | ✅ Forçada | ❌ Opcional |
| Boilerplate | Mínimo | Muito (ou Lombok) |
| Herança | ❌ Não pode | ✅ Pode |
| Serialização | Simples | Precisa configurar |

**No ConsignadoHub:**
*"Uso Record para todos os Value Objects (CPF, Dinheiro, Email, DadosBancarios) e DTOs (ClienteResponse, CadastrarClienteCommand). Uso Class para entities como Cliente porque tem ciclo de vida e lógica de negócio."*

---

## 🚨 14. O QUE FAZER QUANDO... (TROUBLESHOOTING)

> Perguntas que testam experiência real em produção.

### O que fazer quando uma mensagem Kafka dá erro?

**Resposta:**

**Passo 1: Não perder a mensagem**
- Usar ACK manual (só confirma após processar)
- Configurar retry policy

**Passo 2: Estratégia de retry**
```
Tentativa 1 → Falhou → Espera 1s
Tentativa 2 → Falhou → Espera 2s
Tentativa 3 → Falhou → Espera 4s (backoff exponencial)
Tentativa 4 → Falhou → Dead Letter Queue (DLQ)
```

**Passo 3: Dead Letter Queue (DLQ)**
- Mensagens que falharam após N tentativas vão para fila especial
- Análise manual ou automática posterior
- Alerta para o time investigar

**Passo 4: Garantir idempotência**
- Se a mesma mensagem for reprocessada, não causa efeito duplicado
- Usar campo de controle (idempotencyKey)

```java
@KafkaListener(topics = "contratos")
public void processar(ContratoEvent evento) {
    if (jaProcessado(evento.idempotencyKey())) {
        log.info("Evento já processado, ignorando");
        return;
    }
    
    try {
        service.processar(evento);
        marcarComoProcessado(evento.idempotencyKey());
    } catch (Exception e) {
        log.error("Erro processando: {}", evento, e);
        throw e;  // Kafka faz retry
    }
}
```

---

### O que fazer quando você sobe uma alteração com bug para produção?

**Resposta:**

**Passo 1: Identificar impacto (1-2 min)**
- Quantos usuários afetados?
- Dados corrompidos?
- É crítico (financeiro, segurança)?

**Passo 2: Decisão rápida**
- **Rollback imediato:** Se impacto alto, voltar versão anterior
- **Hotfix:** Se rollback não é possível, corrigir e deploy rápido
- **Feature flag:** Se tiver, desligar a feature problemática

**Passo 3: Comunicação**
- Avisar time/liderança
- Se afetou clientes, avisar suporte

**Passo 4: Fix**
```
1. Reproduzir em ambiente local
2. Escrever teste que falha (prova do bug)
3. Corrigir
4. Teste passa
5. Deploy com monitoramento extra
```

**Passo 5: Post-mortem (depois)**
- O que causou?
- Por que não pegamos antes?
- Como evitar no futuro?

**Resposta para entrevista:**
*"Primeiro avalio impacto. Se crítico, faço rollback imediato - melhor voltar para versão estável do que deixar produção quebrada. Depois investigo com calma, crio teste que reproduz o bug, corrijo, e faço deploy com monitoramento extra. No dia seguinte, faço post-mortem para melhorar o processo."*

---

### O que fazer quando o banco de dados fica lento?

**Resposta:**

**Passo 1: Identificar a query lenta**
```sql
-- PostgreSQL: queries lentas ativas
SELECT pid, now() - query_start AS duration, query 
FROM pg_stat_activity 
WHERE state = 'active' 
ORDER BY duration DESC;
```

**Passo 2: Analisar com EXPLAIN ANALYZE**
```sql
EXPLAIN ANALYZE SELECT * FROM clientes WHERE cpf = '123';
```

**Possíveis causas e soluções:**

| Causa | Diagnóstico | Solução |
|-------|-------------|---------|
| Falta de índice | "Seq Scan" em tabela grande | Criar índice |
| Lock | Muitas transações esperando | Verificar transações longas |
| N+1 | Muitas queries similares | JOIN FETCH, @BatchSize |
| Tabela muito grande | Milhões de registros | Paginação, particionamento |
| Conexões esgotadas | Pool cheio | Aumentar pool, otimizar queries |

**Passo 3: Ações imediatas**
- Matar query problemática se necessário
- Reiniciar conexões do pool
- Escalar leitura (read replicas)

**Resposta para entrevista:**
*"Primeiro identifico qual query está lenta com pg_stat_activity. Depois rodo EXPLAIN ANALYZE para ver o plano de execução. Geralmente é falta de índice (Seq Scan) ou problema de N+1. Adiciono índice via migration ou otimizo a query com JOIN FETCH."*

---

### O que fazer quando um serviço externo (API) fica fora do ar?

**Resposta:**

**Se comunicação é SÍNCRONA:**

1. **Circuit Breaker:** Após N falhas, para de tentar
2. **Timeout:** Não esperar infinitamente
3. **Fallback:** Retornar valor default ou cached

```java
@CircuitBreaker(name = "inss", fallbackMethod = "fallback")
@Retry(name = "inss", maxAttempts = 3)
@Timeout(value = 5, unit = ChronoUnit.SECONDS)
public Averbacao consultar(String cpf) {
    return inssClient.consultar(cpf);
}

public Averbacao fallback(String cpf, Exception e) {
    log.warn("INSS indisponível, usando cache");
    return cacheService.buscarUltimaAverbacao(cpf);
}
```

**Se comunicação é ASSÍNCRONA:**
- Mensagem fica na fila esperando
- Consumer processa quando serviço voltar
- Sistema origem não é afetado

**Resposta para entrevista:**
*"Uso Circuit Breaker com Resilience4j. Após 5 falhas, o circuito abre e retorna fallback imediatamente por 30 segundos. Isso evita cascata de falhas e timeout desnecessário. Também tenho cache de última consulta bem-sucedida para fallback."*

---

### O que fazer quando a aplicação está consumindo muita memória?

**Resposta:**

**Passo 1: Coletar heap dump**
```bash
jmap -dump:format=b,file=heap.hprof <PID>
```

**Passo 2: Analisar com ferramenta**
- Eclipse MAT (Memory Analyzer Tool)
- VisualVM
- JProfiler

**Causas comuns:**

| Causa | Sintoma | Solução |
|-------|---------|---------|
| Memory leak | Objetos que nunca são liberados | Encontrar referência que segura |
| Cache sem limite | Cache cresce infinitamente | Configurar TTL e tamanho máximo |
| Strings duplicadas | Milhares de Strings iguais | String.intern() ou pool |
| Listas grandes | Carrega milhões de registros | Paginação, stream |

**Passo 3: Monitorar**
- Configurar alertas de memória (> 80%)
- Grafana/Prometheus com métricas JVM

**Resposta para entrevista:**
*"Coletaria um heap dump e analisaria com Eclipse MAT. Geralmente é cache sem limite, lista carregando dados demais, ou leak de conexões. Também verifico se o problema é no código ou se preciso aumentar memória da JVM."*

---

### O que fazer quando deploy em produção falha no meio?

**Resposta:**

**Prevenção (antes):**
- Blue/Green deployment
- Canary release
- Feature flags
- Health checks

**Durante a falha:**

1. **Rollback automático:** Se health check falha, volta versão anterior
2. **Se não tem automático:** kubectl rollout undo
3. **Comunicar:** Avisar time que está revertendo

```yaml
# Kubernetes: rollback automático
livenessProbe:
  httpGet:
    path: /actuator/health/liveness
    port: 8080
  initialDelaySeconds: 30
  failureThreshold: 3  # Após 3 falhas, reinicia
```

**Depois:**
- Verificar logs do pod que falhou
- Entender por que passou no staging e falhou em prod
- Melhorar testes/pipeline

**Resposta para entrevista:**
*"Primeiro, faço rollback para versão estável - produção tem que funcionar. Depois investigo por que falhou. Geralmente é diferença de ambiente (variáveis, secrets) ou carga de produção que não simulamos. Por isso uso Canary release: deploy para 1% dos usuários primeiro, monitoro, depois expando."*

---

### O que fazer quando recebe alerta de muitos erros 500?

**Resposta:**

**Passo 1: Verificar (1-2 min)**
- Dashboards (Grafana, Datadog)
- Qual endpoint? Qual serviço?
- Começou quando?

**Passo 2: Correlacionar**
- Houve deploy recente?
- Dependência externa caiu?
- Pico de tráfego?

**Passo 3: Logs**
```
# Buscar erros recentes
kubectl logs <pod> --since=5m | grep ERROR
```

**Passo 4: Ação baseada na causa**

| Causa | Ação |
|-------|------|
| Deploy quebrou | Rollback |
| Dependência fora | Circuit breaker, fallback |
| Pico de tráfego | Escalar pods |
| Bug de código | Hotfix |
| Banco lento | Matar queries, otimizar |

**Resposta para entrevista:**
*"Verifico imediatamente no Grafana qual endpoint e desde quando. Correlaciono com deploys ou mudanças. Se foi deploy, rollback. Se foi dependência, ativo fallback. Enquanto investigo, comunico o time. Depois de resolver, documento o que aconteceu."*

---

## 🔥 15. TÓPICOS AVANÇADOS (NÍVEL SÊNIOR)




### O que são Virtual Threads (Java 21) e por que são importantes?

**Resposta:**

Virtual Threads (Project Loom) são threads leves gerenciadas pela JVM, não pelo Sistema Operacional.

| Aspecto | Thread de Plataforma | Virtual Thread |
|---------|---------------------|----------------|
| Gerenciamento | Sistema Operacional | JVM |
| Memória | ~1 MB por thread | Poucos KB |
| Quantidade | Milhares = problema | Milhões = OK |
| Criação | Custosa | Barata |

**Quando usar:**
Operações I/O-bound (HTTP calls, banco de dados, arquivos). Não para CPU-bound.

**Exemplo prático:**
```java
// Antes: Thread pool limitado
ExecutorService executor = Executors.newFixedThreadPool(100);

// Depois: Virtual threads ilimitadas
ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
```

**No ConsignadoHub:**
*"Seria perfeito para o serviço que consulta a API do INSS. A thread fica parada esperando resposta HTTP. Com Virtual Threads, poderíamos escalar para milhares de requisições concorrentes sem estourar memória."*

---

### Qual a diferença entre Optimistic e Pessimistic Locking?

**Resposta:**

| Aspecto | Optimistic (Otimista) | Pessimistic (Pessimista) |
|---------|----------------------|--------------------------|
| Quando trava | No commit | Na leitura |
| Implementação | `@Version` no JPA | `SELECT FOR UPDATE` |
| Performance | Melhor (não trava banco) | Pior (trava registro) |
| Conflitos | Detecta no final | Previne desde o início |
| Uso ideal | Pouca concorrência | Muita concorrência |

**Optimistic Locking com JPA:**
```java
@Entity
public class Conta {
    @Id
    private Long id;
    private BigDecimal saldo;
    
    @Version  // ← Hibernate controla
    private Long version;
}

// UPDATE conta SET saldo = ?, version = 2 
// WHERE id = ? AND version = 1
// Se versão mudou → 0 linhas afetadas → OptimisticLockException
```

**Pessimistic Locking:**
```java
@Query("SELECT c FROM Conta c WHERE c.id = :id")
@Lock(LockModeType.PESSIMISTIC_WRITE)
Optional<Conta> findByIdForUpdate(@Param("id") Long id);
```

**No ConsignadoHub:**
*"Para reserva de margem consignável, usaria Optimistic Locking com @Version. Se dois atendentes tentarem vender empréstimo na mesma margem ao mesmo tempo, um deles recebe erro e deve tentar novamente. Evita travar o banco desnecessariamente."*

---

### Quais são os 3 Pilares da Observabilidade?

**Resposta:**

| Pilar | O que responde | Ferramentas |
|-------|----------------|-------------|
| **Logs** | O que aconteceu? | ELK Stack, Splunk, CloudWatch |
| **Métricas** | Quanto? Tendência? | Prometheus + Grafana, Micrometer |
| **Tracing** | Onde está a lentidão? | Jaeger, Zipkin, OpenTelemetry |

**Logs:**
Eventos textuais. "Cliente X cadastrado às 10:30".

**Métricas:**
Números agregados. "500 erros/minuto", "p99 latência = 200ms".

```java
// Micrometer no Spring Boot
@Timed(name = "cadastro_cliente", description = "Tempo para cadastrar cliente")
public ClienteId cadastrar(CadastrarClienteCommand cmd) { ... }
```

**Tracing Distribuído:**
Rastreia uma requisição através de múltiplos serviços.

```
Request ID: abc-123
├── Gateway (5ms)
├── CustomerService (50ms)
│   └── PostgreSQL (45ms)  ← Gargalo identificado!
└── SimulationService (10ms)
```

**Resposta resumida:**
*"Os três pilares são Logs, Métricas e Tracing. Logs mostram o que aconteceu, métricas mostram tendências numéricas, e tracing distribuído mostra onde está a lentidão em arquiteturas de microserviços. No Spring Boot, uso Micrometer para exportar métricas para Prometheus."*

---

### Como funciona autenticação com JWT/OAuth2?

**Resposta:**

**Fluxo simplificado:**

```
1. Frontend envia credenciais → Identity Provider (Keycloak/Auth0)
2. Identity Provider valida e retorna JWT
3. Frontend envia JWT no header → Backend
4. Backend valida assinatura do JWT (Stateless)
5. Request autorizada
```

**JWT (JSON Web Token):**
```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSJ9.signature
     HEADER             PAYLOAD              SIGNATURE
```

**Spring Security com JWT:**
```java
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        return http
            .oauth2ResourceServer(oauth2 -> oauth2.jwt())
            .build();
    }
}
```

**Resposta resumida:**
*"O frontend autentica no Identity Provider (Keycloak, Auth0) e recebe um JWT. A cada request, envia o token no header Authorization: Bearer <token>. O backend apenas valida a assinatura do JWT - é stateless, não precisa de sessão no servidor."*

---

### Como você otimiza um Dockerfile para Java?

**Resposta:**

**Multi-stage build:**
```dockerfile
# Stage 1: Build (imagem grande com JDK + Maven)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline   # Cache de dependências
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Runtime (imagem pequena só com JRE)
FROM eclipse-temurin:21-jre-alpine
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Otimizações:**
1. **Multi-stage:** Imagem final não tem JDK/Maven
2. **Imagem Alpine:** Muito menor que Ubuntu
3. **Cache de layers:** `COPY pom.xml` antes do `COPY src`
4. **JRE, não JDK:** Runtime não precisa compilador

**Resultado:** Imagem de ~400MB → ~150MB

---

### Diferença entre Pod, Deployment e Service no Kubernetes?

**Resposta:**

| Conceito | O que é | Analogia |
|----------|---------|----------|
| **Pod** | Menor unidade, contém 1+ containers | Uma instância da aplicação |
| **Deployment** | Gerencia réplicas de Pods | "Quero 3 cópias rodando" |
| **Service** | Exposição de rede, load balancer | DNS fixo para acessar os Pods |

```yaml
# Deployment: 3 réplicas do meu app
apiVersion: apps/v1
kind: Deployment
spec:
  replicas: 3
  template:
    spec:
      containers:
      - name: customer-service
        image: consignado/customer:1.0.0

---
# Service: Expõe na porta 80, roteia para Pods
apiVersion: v1
kind: Service
spec:
  type: ClusterIP
  ports:
  - port: 80
    targetPort: 8080
```

---

### Diferença entre Optional.of e Optional.ofNullable?

**Resposta (pegadinha clássica):**

| Método | Se valor for null | Quando usar |
|--------|-------------------|-------------|
| `Optional.of(valor)` | **Lança NullPointerException** | Quando null é erro de programação |
| `Optional.ofNullable(valor)` | Retorna `Optional.empty()` | Quando ausência é esperada |

```java
// of() - lança exceção se nulo
Optional<String> opt1 = Optional.of(null);  // NullPointerException!

// ofNullable() - retorna empty se nulo
Optional<String> opt2 = Optional.ofNullable(null);  // Optional.empty()
```

**Quando usar cada:**
```java
// of() - tenho certeza que não é nulo
Optional<Cliente> cliente = Optional.of(clienteRepository.findById(id).orElseThrow());

// ofNullable() - pode ser nulo
Optional<String> email = Optional.ofNullable(cliente.getEmail());
```

---

### Como identificar problemas de performance no banco? (EXPLAIN ANALYZE)

**Resposta:**

Quando endpoint está lento, rodo `EXPLAIN ANALYZE` na query:

```sql
EXPLAIN ANALYZE SELECT * FROM clientes WHERE cpf = '52998224725';
```

**Resultado ruim (Full Table Scan):**
```
Seq Scan on clientes  (cost=0.00..1850.00 rows=1)
  Filter: (cpf = '52998224725')
  Rows Removed by Filter: 99999
  Actual Time: 450.123ms
```

**Resultado bom (Index Scan):**
```
Index Scan using idx_clientes_cpf on clientes  (cost=0.29..8.30 rows=1)
  Index Cond: (cpf = '52998224725')
  Actual Time: 0.035ms
```

**Regra:** Se vejo "Seq Scan" em tabela grande, falta índice. Adiciono via Flyway:
```sql
CREATE INDEX idx_clientes_cpf ON clientes(cpf);
```

---

### O que fazer quando há dados inconsistentes no banco?

**Resposta:**

**Passo 1:** Identificar escopo - quantos registros? Quando começou?

**Passo 2:** Parar a causa - hotfix/rollback

**Passo 3:** Correção
```sql
-- Encontrar registros problemáticos
SELECT * FROM contratos WHERE valor_liberado > margem_disponivel;
```

**Passo 4:** Prevenir - adicionar constraint, validação

**Resposta para entrevista:**
*"Primeiro paro a causa (hotfix). Quantifico com queries. Corrijo com script SQL. Documento e adiciono validação para não acontecer de novo."*

---

### O que fazer quando transação trava (deadlock)?

**Resposta:**

**O que é:** Duas transações esperando lock uma da outra.

**Resolver imediato:**
```sql
SELECT pg_terminate_backend(<PID>);
```

**Prevenir:** Sempre lockar na mesma ordem
```java
Long first = Math.min(contaOrigemId, contaDestinoId);
Long second = Math.max(contaOrigemId, contaDestinoId);
// Locka sempre na ordem first → second
```

**Resposta para entrevista:**
*"Deadlock é quando duas transações ficam esperando lock uma da outra. Previno garantindo que todos lockam na mesma ordem - sempre pelo menor ID primeiro."*

---

### O que fazer quando há duplicação de dados?

**Resposta:**

**Causas:** Falta de UNIQUE constraint, retry sem idempotência, race condition.

**Corrigir:**
```sql
-- Adicionar constraint
ALTER TABLE clientes ADD CONSTRAINT uk_cpf UNIQUE (cpf);
```

**Idempotência:**
```java
if (repository.existsByIdempotencyKey(evento.key())) {
    return; // Já processado
}
```

**Resposta para entrevista:**
*"Duplicação é falta de constraint UNIQUE ou falta de idempotência. Corrijo dados, adiciono constraint, implemento idempotência com chave única."*

---

### O que fazer quando logs estão confusos/difícil de rastrear?

**Resposta:**

**Problema:** Muitos logs sem contexto de qual request.

**Solução: Correlation ID via MDC**
```java
MDC.put("correlationId", UUID.randomUUID().toString());
```

**Resultado:**
```
10:00:01 [abc-123] INFO Processando cliente
10:00:01 [abc-123] ERROR Falhou
→ grep "abc-123" para ver toda a jornada
```

**Resposta para entrevista:**
*"Uso Correlation ID via MDC. Cada request tem UUID único em todos os logs. Fácil filtrar e rastrear."*

---

### O que fazer quando não consegue reproduzir um bug?

**Resposta:**

1. **Coletar:** logs, request exato, timestamp, versão
2. **Reproduzir:** com mesmos dados em ambiente local
3. **Diferenças:** verificar configs, dados, timing
4. **Se não reproduzir:** adicionar logs extras, monitorar próxima ocorrência

**Resposta para entrevista:**
*"Coleto todos os dados disponíveis. Tento reproduzir localmente. Se não conseguir, adiciono logs extras e monitoro para capturar mais informações na próxima ocorrência."*

---

### O que fazer quando cliente reclama de lentidão?

**Resposta:**

**Passo 1:** Qual endpoint? Desde quando? Só ele ou todos?

**Passo 2:** Usar APM/tracing para ver gargalo
```
Request (500ms total)
├── Controller (5ms)
├── Service (20ms)  
├── Database (450ms)  ← GARGALO
└── Response (5ms)
```

**Passo 3:** Resolver por causa
- Banco lento → índice
- API externa → cache, async
- Muitos dados → paginação

**Resposta para entrevista:**
*"Uso APM para ver qual camada está lenta. Geralmente é banco sem índice ou API externa. Resolvo a causa e valido com cliente."*

---

## ✅ Checklist Pré-Entrevista

- [ ] Revise seu currículo - saiba explicar cada item
- [ ] Pesquise sobre a empresa e o produto
- [ ] Prepare 3-5 perguntas para fazer ao entrevistador
- [ ] Revise seus projetos com detalhes técnicos
- [ ] Pratique explicar conceitos em voz alta
- [ ] Teste equipamento (se remota)
- [ ] Durma bem na noite anterior

---

## 📊 Resumo do Seu Projeto (Para Citar na Entrevista)

| Aspecto | O que dizer |
|---------|-------------|
| **Arquitetura** | "Implementei Hexagonal com separação clara entre Domain, Application e Adapters. Domínio não conhece Spring." |
| **Testes** | "78 testes com TDD - escrevo teste antes, implementação depois." |
| **Exceptions** | "Consolidei 16 em 3 com códigos padronizados e factory methods." |
| **VOs** | "Records imutáveis com validação no construtor: CPF, Dinheiro, DadosBancarios." |
| **CQS** | "Separei Commands de Queries através de interfaces distintas." |
| **Logs** | "Logs estruturados com Slf4j, CPF mascarado para LGPD." |
| **Concorrência** | "Usaria Optimistic Locking com @Version para evitar race condition na margem." |
| **Negócio** | "Sistema de crédito consignado INSS - entendo margem consignável, averbação, desembolso." |

---

> **Dica Final:** Quando não souber, diga: *"Não tenho experiência prática com isso, mas pelo que entendo..."* É muito melhor que inventar.

**Boa sorte na entrevista! 🍀**

