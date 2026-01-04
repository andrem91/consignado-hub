# 🚀 Roteiro de Implementação: ConsignadoHub

> **Sistema de Crédito Consignado INSS** - Baseado na Stack de grandes instituições financeiras

---

## 📌 Filosofia do Roteiro

Este roteiro segue a metodologia de **desenvolvimento profissional** usada em bancos:

1. **TDD:** Teste primeiro, código depois (Red-Green-Refactor)
2. **DDD:** Domínio no centro, infraestrutura nas bordas
3. **Event-Driven:** Comunicação assíncrona entre serviços
4. **Incremental:** Comece simples, evolua com complexidade

---

## 🎮 Modo Mentor: Desenvolvimento Ágil Simulado

> **IMPORTANTE:** Este projeto simula um ambiente de desenvolvimento real com incidentes e bugs inesperados.

### Como Funciona

Durante o desenvolvimento, a IA irá **injetar incidentes** em momentos específicos:

| Tipo | Quando Aparece | Objetivo |
|------|----------------|----------|
| 🐛 **Bugs Sutis** | Após implementar uma feature | Desenvolver habilidade de debugging |
| 🔴 **Incidentes de Produção** | Durante sprints | Resolver sob pressão |
| 📋 **Features Urgentes** | No meio de uma sprint | Lidar com mudanças de requisito |
| ⚡ **Problemas de Performance** | Após deploy | Usar observabilidade |
| 🔐 **Vulnerabilidades** | Durante revisão | Pensar em segurança |

### Por que isso é importante?

Em entrevistas, você será perguntado:

> *"Me conte sobre um problema que você enfrentou e como resolveu."*

Com este sistema, você terá **histórias reais** para contar, documentadas em `ESTADO_PROJETO.md`.

### Arquivos do Sistema

| Arquivo | Descrição |
|---------|-----------|
| `ESTADO_PROJETO.md` | Progresso atual, incidentes ativos, histórico |
| `INCIDENTES.md` | Cenários planejados (NÃO LEIA!) |

### Para Retomar em Nova Conversa

Use o comando:
```
/modo-mentor
```

Ou:
```
Continuando ConsignadoHub. Leia ESTADO_PROJETO.md
```

### Exemplo de Incidente

Após implementar CPF, você pode receber:

```
🔴 FEEDBACK DO QA:

CPFs que começam com zero estão falhando em produção.
Exemplo: '01234567890' retorna erro.

Logs:
InvalidCPFException: CPF deve ter 11 dígitos: 1234567890

Prioridade: CRÍTICA
```

Você precisa investigar, encontrar a causa raiz e corrigir - assim como em produção real.

---

## 🏦 Contexto de Negócio

### O que é Crédito Consignado INSS?

Empréstimo onde as parcelas são descontadas **diretamente do benefício** do aposentado/pensionista.

### Regras Fundamentais (ver `PROJETO_CONSIGNADO.md`)

| Regra | Valor |
|-------|-------|
| Margem Empréstimo Pessoal | 35% do benefício |
| Margem Total (com cartões) | 45% do benefício |
| Teto de Juros | 1,66% a.m. |
| Prazo Máximo | 96 meses |
| Órgão de Averbação | Dataprev/INSS |

### Canais de Originação

| Canal | Código | Descrição |
|-------|--------|-----------|
| Digital | `DIGITAL` | 100% app/web |
| Corban | `CORBAN` | Correspondente bancário |
| Interno | `INTERNAL` | Agência/gerente |

---

## 🗓️ Visão Geral das Sprints

```
Sprint 0  ████░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░  Setup & Value Objects
Sprint 1  ░░░░████░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░  Customer Service
Sprint 2  ░░░░░░░░████░░░░░░░░░░░░░░░░░░░░░░░░░░  Simulation Service
Sprint 3  ░░░░░░░░░░░░████░░░░░░░░░░░░░░░░░░░░░░  Credit Analysis + Kafka
Sprint 4  ░░░░░░░░░░░░░░░░████░░░░░░░░░░░░░░░░░░  Loan + Averbation
Sprint 5  ░░░░░░░░░░░░░░░░░░░░████░░░░░░░░░░░░░░  Payment Service
Sprint 6  ░░░░░░░░░░░░░░░░░░░░░░░░████░░░░░░░░░░  Gateway + Keycloak
Sprint 7  ░░░░░░░░░░░░░░░░░░░░░░░░░░░░████░░░░░░  DevOps + Observabilidade
Sprint 8  ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░███░░░  ☸️ Kubernetes
Sprint 9  ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░███  ☁️ AWS + LocalStack
Sprint 10 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██ 🏗️ Terraform
Sprint 11 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█ 🚀 Integração Final
```

| Sprint | Foco | Módulos Teóricos | Estimativa |
|--------|------|------------------|------------|
| 0 | Setup + Value Objects + Modulith | 3, 13 | 3-4 dias |
| 1 | Customer Service (Hexagonal) | 4, 5 | 5-6 dias |
| 2 | Simulation Service (Cálculos) | 1, 6 | 4-5 dias |
| 3 | Credit Analysis + Kafka | 6, 7 | 5-6 dias |
| 4 | Loan Service + Averbation | 6, 13 | 6-7 dias |
| 5 | Payment Service | 5, 6 | 4-5 dias |
| 6 | Gateway + Keycloak OAuth2 | 4, 12 | 4-5 dias |
| 7 | DevOps + Observabilidade | 7, 8 | 4-5 dias |
| 8 | ☸️ Kubernetes | 10 | 4-5 dias |
| 9 | ☁️ AWS + LocalStack | 9 | 5-6 dias |
| 10 | 🏗️ Terraform | 11 | 4-5 dias |
| 11 | 🚀 Integração Final | Todos | 3-4 dias |

**Total Estimado:** 55-65 dias (~12-14 semanas)

---

## 🎯 Sprint 0: Setup & Fundamentos DDD

### Objetivo
Criar a estrutura do projeto e implementar os Value Objects específicos do domínio de consignado usando TDD.

### 📖 Estude Primeiro

| Tópico | Arquivo | Seções |
|--------|---------|--------|
| OOP e Encapsulamento | `MODULO_03_OOP_SOLID.md` | 3.1, 3.2 |
| SOLID Principles | `MODULO_03_OOP_SOLID.md` | 3.3 |
| Value Objects | `MODULO_03_OOP_SOLID.md` | 3.5, 3.6 |
| Records Java 21 | `MODULO_02_JAVA_MODERNO.md` | 2.1 |
| Spring Modulith | `MODULO_13_SPRING_MODULITH.md` | 13.1, 13.2 |

**Leitura complementar:**
- DDD: O que são Value Objects vs Entities
- Arquitetura Hexagonal: Conceito de Ports & Adapters
- TDD: Ciclo Red-Green-Refactor
- Spring Modulith: Validação de arquitetura em tempo de teste

### 🛠️ Implemente

#### Dia 1: Setup do Projeto

**1. Criar estrutura do monorepo:**

```bash
mkdir consignado-hub
cd consignado-hub
git init
```

**2. Estrutura inicial:**

```
consignado-hub/
├── consignado-customer-service/
│   ├── src/main/java/com/consignadohub/customer/
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   ├── vo/         ← Value Objects
│   │   │   └── exception/
│   │   ├── application/
│   │   │   ├── port/in/    ← Use Cases
│   │   │   ├── port/out/   ← Repositories
│   │   │   └── service/
│   │   └── adapter/
│   │       ├── in/web/     ← Controllers
│   │       └── out/        ← JPA, Feign, Kafka
│   └── pom.xml
├── consignado-simulation-service/
├── consignado-credit-service/
├── consignado-loan-service/
├── consignado-payment-service/
├── consignado-commons/
│   └── pom.xml
├── pom.xml (parent)
└── docker-compose.yml
```

**3. Parent pom.xml:**

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
    <version>1.0.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    
    <modules>
        <module>consignado-commons</module>
        <module>consignado-customer-service</module>
        <module>consignado-simulation-service</module>
        <module>consignado-credit-service</module>
        <module>consignado-loan-service</module>
        <module>consignado-payment-service</module>
    </modules>
    
    <properties>
        <java.version>21</java.version>
        <spring-cloud.version>2024.0.0</spring-cloud.version>
        <spring-modulith.version>1.3.0</spring-modulith.version>
    </properties>
    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

**4. docker-compose.yml base:**

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
    volumes:
      - postgres_data:/var/lib/postgresql/data

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    depends_on:
      - zookeeper

  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181

volumes:
  postgres_data:
```

#### Dia 2-3: Value Objects do Domínio Consignado (TDD)

**Ordem de implementação:**

**1. CPF (Value Object)**

```java
// ❌ RED: Escreva o teste PRIMEIRO
// src/test/java/.../domain/vo/CPFTest.java

@DisplayName("CPF Value Object")
class CPFTest {

    @Test
    @DisplayName("Deve criar CPF válido")
    void deveCriarCpfValido() {
        CPF cpf = new CPF("40453683886");
        assertThat(cpf.value()).isEqualTo("40453683886");
    }

    @Test
    @DisplayName("Deve remover formatação do CPF")
    void deveRemoverFormatacao() {
        CPF cpf = new CPF("404.536.838-86");
        assertThat(cpf.value()).isEqualTo("40453683886");
    }

    @Test
    @DisplayName("Deve formatar CPF para exibição")
    void deveFormatarCpf() {
        CPF cpf = new CPF("40453683886");
        assertThat(cpf.formatted()).isEqualTo("404.536.838-86");
    }

    @Test
    @DisplayName("Deve rejeitar CPF com dígitos repetidos")
    void deveRejeitarDigitosRepetidos() {
        assertThatThrownBy(() -> new CPF("11111111111"))
            .isInstanceOf(InvalidCPFException.class)
            .hasMessageContaining("dígitos repetidos");
    }

    @Test
    @DisplayName("Deve rejeitar CPF com dígito verificador inválido")
    void deveRejeitarDigitoVerificadorInvalido() {
        assertThatThrownBy(() -> new CPF("12345678901"))
            .isInstanceOf(InvalidCPFException.class);
    }

    @Test
    @DisplayName("Deve rejeitar CPF nulo ou vazio")
    void deveRejeitarNuloOuVazio() {
        assertThatThrownBy(() -> new CPF(null))
            .isInstanceOf(InvalidCPFException.class);
        assertThatThrownBy(() -> new CPF(""))
            .isInstanceOf(InvalidCPFException.class);
    }

    @Test
    @DisplayName("CPFs iguais devem ter mesmo hashCode")
    void cpfsIguaisDevemTerMesmoHashCode() {
        CPF cpf1 = new CPF("40453683886");
        CPF cpf2 = new CPF("404.536.838-86");
        
        assertThat(cpf1).isEqualTo(cpf2);
        assertThat(cpf1.hashCode()).isEqualTo(cpf2.hashCode());
    }
}
```

```java
// ✅ GREEN: Implementação
// src/main/java/.../domain/vo/CPF.java

public record CPF(String value) {

    public CPF {
        if (value == null || value.isBlank()) {
            throw new InvalidCPFException("CPF não pode ser nulo ou vazio");
        }
        
        String cleaned = value.replaceAll("[^0-9]", "");
        
        if (cleaned.length() != 11) {
            throw new InvalidCPFException("CPF deve ter 11 dígitos: " + value);
        }
        
        if (cleaned.matches("(\\d)\\1{10}")) {
            throw new InvalidCPFException("CPF com dígitos repetidos não é válido");
        }
        
        if (!isValidCheckDigits(cleaned)) {
            throw new InvalidCPFException("CPF inválido: " + value);
        }
        
        value = cleaned;
    }

    private static boolean isValidCheckDigits(String cpf) {
        int[] weights1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum1 = 0;
        for (int i = 0; i < 9; i++) {
            sum1 += Character.getNumericValue(cpf.charAt(i)) * weights1[i];
        }
        int digit1 = 11 - (sum1 % 11);
        if (digit1 > 9) digit1 = 0;

        int sum2 = 0;
        for (int i = 0; i < 10; i++) {
            sum2 += Character.getNumericValue(cpf.charAt(i)) * weights2[i];
        }
        int digit2 = 11 - (sum2 % 11);
        if (digit2 > 9) digit2 = 0;

        return cpf.charAt(9) == Character.forDigit(digit1, 10)
            && cpf.charAt(10) == Character.forDigit(digit2, 10);
    }

    public String formatted() {
        return value.substring(0, 3) + "." +
               value.substring(3, 6) + "." +
               value.substring(6, 9) + "-" +
               value.substring(9);
    }
}
```

**2. Money (Value Object)**

```java
// Teste
@DisplayName("Money Value Object")
class MoneyTest {

    @Test
    @DisplayName("Deve criar Money com valor positivo")
    void deveCriarComValorPositivo() {
        Money money = Money.of(new BigDecimal("1000.50"));
        assertThat(money.amount()).isEqualByComparingTo("1000.50");
    }

    @Test
    @DisplayName("Deve arredondar para 2 casas decimais (HALF_UP)")
    void deveArredondarPara2Casas() {
        Money money = Money.of(new BigDecimal("100.555"));
        assertThat(money.amount()).isEqualByComparingTo("100.56");
    }

    @Test
    @DisplayName("ZERO deve ser constante compartilhada")
    void zeroDeveSerConstante() {
        assertThat(Money.ZERO.amount()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(Money.ZERO).isSameAs(Money.ZERO);
    }

    @Test
    @DisplayName("Deve somar valores mantendo imutabilidade")
    void deveSomarValores() {
        Money a = Money.of(new BigDecimal("100.00"));
        Money b = Money.of(new BigDecimal("50.00"));
        
        Money result = a.add(b);
        
        assertThat(result.amount()).isEqualByComparingTo("150.00");
        assertThat(a.amount()).isEqualByComparingTo("100.00"); // Original não muda
    }

    @Test
    @DisplayName("Deve subtrair valores")
    void deveSubtrairValores() {
        Money a = Money.of(new BigDecimal("100.00"));
        Money b = Money.of(new BigDecimal("30.00"));
        
        Money result = a.subtract(b);
        
        assertThat(result.amount()).isEqualByComparingTo("70.00");
    }

    @Test
    @DisplayName("Deve multiplicar por quantidade")
    void deveMultiplicarPorQuantidade() {
        Money parcela = Money.of(new BigDecimal("318.71"));
        
        Money total = parcela.multiply(48);
        
        assertThat(total.amount()).isEqualByComparingTo("15298.08");
    }

    @Test
    @DisplayName("Não deve permitir valor negativo")
    void naoDevePermitirNegativo() {
        assertThatThrownBy(() -> Money.of(new BigDecimal("-100.00")))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("negativo");
    }

    @Test
    @DisplayName("Deve comparar valores corretamente")
    void deveCompararValores() {
        Money cem = Money.of(new BigDecimal("100.00"));
        Money cinquenta = Money.of(new BigDecimal("50.00"));
        
        assertThat(cem.isGreaterThan(cinquenta)).isTrue();
        assertThat(cinquenta.isLessThan(cem)).isTrue();
        assertThat(cem.isPositive()).isTrue();
    }
}
```

```java
// Implementação
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

    public static Money of(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return ZERO;
        }
        return new Money(amount);
    }

    public static Money of(String amount) {
        return of(new BigDecimal(amount));
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        BigDecimal result = this.amount.subtract(other.amount);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Resultado negativo não permitido");
        }
        return new Money(result);
    }

    public Money multiply(int quantity) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(quantity)));
    }

    public Money multiply(BigDecimal factor) {
        return new Money(this.amount.multiply(factor));
    }

    public boolean isGreaterThan(Money other) {
        return this.amount.compareTo(other.amount) > 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return this.amount.compareTo(other.amount) >= 0;
    }

    public boolean isLessThan(Money other) {
        return this.amount.compareTo(other.amount) < 0;
    }

    public boolean isPositive() {
        return this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }
}
```

**3. BenefitNumber (Número do Benefício INSS)**

```java
// Teste
@DisplayName("BenefitNumber Value Object")
class BenefitNumberTest {

    @Test
    @DisplayName("Deve criar número de benefício válido com 10 dígitos")
    void deveCriarNumeroValido() {
        BenefitNumber benefit = new BenefitNumber("1234567890");
        assertThat(benefit.value()).isEqualTo("1234567890");
    }

    @Test
    @DisplayName("Deve remover formatação")
    void deveRemoverFormatacao() {
        BenefitNumber benefit = new BenefitNumber("123.456.789-0");
        assertThat(benefit.value()).isEqualTo("1234567890");
    }

    @Test
    @DisplayName("Deve rejeitar número com tamanho inválido")  
    void deveRejeitarTamanhoInvalido() {
        assertThatThrownBy(() -> new BenefitNumber("123456789"))
            .isInstanceOf(InvalidBenefitNumberException.class);
    }
}
```

```java
// Implementação
public record BenefitNumber(String value) {

    public BenefitNumber {
        if (value == null || value.isBlank()) {
            throw new InvalidBenefitNumberException("Número do benefício não pode ser vazio");
        }
        
        String cleaned = value.replaceAll("[^0-9]", "");
        
        if (cleaned.length() != 10) {
            throw new InvalidBenefitNumberException(
                "Número do benefício deve ter 10 dígitos: " + value);
        }
        
        value = cleaned;
    }

    public String formatted() {
        return value.substring(0, 3) + "." +
               value.substring(3, 6) + "." +
               value.substring(6, 9) + "-" +
               value.substring(9);
    }
}
```

**4. InterestRate (Taxa de Juros)**

```java
// Teste
@DisplayName("InterestRate Value Object")
class InterestRateTest {

    @Test
    @DisplayName("Deve criar taxa dentro do limite CNPS")
    void deveCriarTaxaDentroDoLimite() {
        InterestRate rate = InterestRate.of(new BigDecimal("1.66"));
        assertThat(rate.monthly()).isEqualByComparingTo("1.66");
    }

    @Test
    @DisplayName("Deve calcular taxa anual corretamente")
    void deveCalcularTaxaAnual() {
        InterestRate rate = InterestRate.of(new BigDecimal("1.66"));
        // (1 + 0.0166)^12 - 1 = ~21.74%
        assertThat(rate.annual()).isCloseTo(new BigDecimal("21.74"), Percentage.withPercentage(1));
    }

    @Test
    @DisplayName("Deve rejeitar taxa acima do teto CNPS (1.66%)")
    void deveRejeitarTaxaAcimaDoTeto() {
        assertThatThrownBy(() -> InterestRate.of(new BigDecimal("2.00")))
            .isInstanceOf(InterestRateExceededException.class)
            .hasMessageContaining("1.66");
    }

    @Test
    @DisplayName("Deve rejeitar taxa negativa ou zero")
    void deveRejeitarTaxaNegativaOuZero() {
        assertThatThrownBy(() -> InterestRate.of(new BigDecimal("-1.00")))
            .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> InterestRate.of(BigDecimal.ZERO))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
```

```java
// Implementação
public record InterestRate(BigDecimal monthly) {

    public static final BigDecimal CNPS_MAX_PERSONAL_LOAN = new BigDecimal("1.66");
    public static final BigDecimal CNPS_MAX_CREDIT_CARD = new BigDecimal("2.46");

    public InterestRate {
        if (monthly == null || monthly.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Taxa de juros deve ser positiva");
        }
        if (monthly.compareTo(CNPS_MAX_PERSONAL_LOAN) > 0) {
            throw new InterestRateExceededException(monthly, CNPS_MAX_PERSONAL_LOAN);
        }
        monthly = monthly.setScale(2, RoundingMode.HALF_UP);
    }

    public static InterestRate of(BigDecimal monthly) {
        return new InterestRate(monthly);
    }

    public static InterestRate of(String monthly) {
        return new InterestRate(new BigDecimal(monthly));
    }

    /**
     * Calcula taxa anual equivalente.
     * Fórmula: (1 + i)^12 - 1
     */
    public BigDecimal annual() {
        BigDecimal monthlyDecimal = monthly.divide(new BigDecimal("100"), 6, RoundingMode.HALF_UP);
        BigDecimal factor = BigDecimal.ONE.add(monthlyDecimal);
        BigDecimal annual = factor.pow(12).subtract(BigDecimal.ONE);
        return annual.multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Retorna taxa mensal como decimal (ex: 0.0166)
     */
    public BigDecimal asDecimal() {
        return monthly.divide(new BigDecimal("100"), 6, RoundingMode.HALF_UP);
    }
}
```

**5. MarginPercentage (Margem Consignável)**

```java
// Teste
@DisplayName("MarginPercentage Value Object")
class MarginPercentageTest {

    @Test
    @DisplayName("Constante PERSONAL_LOAN deve ser 35%")
    void constantePersonalLoanDeve Ser35() {
        assertThat(MarginPercentage.PERSONAL_LOAN.value())
            .isEqualByComparingTo("35");
    }

    @Test
    @DisplayName("Constante CREDIT_CARD deve ser 5%")
    void constanteCreditCardDeveSer5() {
        assertThat(MarginPercentage.CREDIT_CARD.value())
            .isEqualByComparingTo("5");
    }

    @Test
    @DisplayName("Deve calcular margem disponível corretamente")
    void deveCalcularMargemDisponivel() {
        Money beneficio = Money.of(new BigDecimal("2000.00"));
        
        Money margem = MarginPercentage.PERSONAL_LOAN.calculateMargin(beneficio);
        
        // 2000 * 35% = 700
        assertThat(margem.amount()).isEqualByComparingTo("700.00");
    }

    @Test
    @DisplayName("Deve rejeitar margem acima de 100%")
    void deveRejeitarMargemAcimaDe100() {
        assertThatThrownBy(() -> MarginPercentage.of(new BigDecimal("101")))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Deve rejeitar margem negativa")
    void deveRejeitarMargemNegativa() {
        assertThatThrownBy(() -> MarginPercentage.of(new BigDecimal("-5")))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
```

```java
// Implementação
public record MarginPercentage(BigDecimal value) {

    public static final MarginPercentage PERSONAL_LOAN = new MarginPercentage(new BigDecimal("35"));
    public static final MarginPercentage CREDIT_CARD = new MarginPercentage(new BigDecimal("5"));
    public static final MarginPercentage BENEFIT_CARD = new MarginPercentage(new BigDecimal("5"));

    public MarginPercentage {
        if (value == null) {
            throw new IllegalArgumentException("Margem não pode ser nula");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Margem não pode ser negativa");
        }
        if (value.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Margem não pode exceder 100%");
        }
    }

    public static MarginPercentage of(BigDecimal value) {
        return new MarginPercentage(value);
    }

    /**
     * Calcula o valor da margem a partir do valor do benefício.
     * Ex: benefício R$ 2.000 com margem 35% = R$ 700
     */
    public Money calculateMargin(Money benefitValue) {
        BigDecimal marginAmount = benefitValue.amount()
            .multiply(value)
            .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        return Money.of(marginAmount);
    }
}
```

**6. LoanTerm (Prazo do Empréstimo)**

```java
// Teste
@DisplayName("LoanTerm Value Object")
class LoanTermTest {

    @Test
    @DisplayName("Deve criar prazo válido")
    void deveCriarPrazoValido() {
        LoanTerm term = LoanTerm.of(48);
        assertThat(term.months()).isEqualTo(48);
    }

    @Test
    @DisplayName("Deve calcular anos corretamente")
    void deveCalcularAnos() {
        LoanTerm term = LoanTerm.of(96);
        assertThat(term.years()).isEqualTo(8);
    }

    @Test
    @DisplayName("Constante MAX_MONTHS deve ser 96")
    void constanteMaxDeveSer96() {
        assertThat(LoanTerm.MAX_MONTHS).isEqualTo(96);
    }

    @Test
    @DisplayName("Deve rejeitar prazo acima do máximo (96 meses)")
    void deveRejeitarPrazoAcimaDoMaximo() {
        assertThatThrownBy(() -> LoanTerm.of(97))
            .isInstanceOf(InvalidLoanTermException.class)
            .hasMessageContaining("96");
    }

    @Test
    @DisplayName("Deve rejeitar prazo zero ou negativo")
    void deveRejeitarPrazoZeroOuNegativo() {
        assertThatThrownBy(() -> LoanTerm.of(0))
            .isInstanceOf(InvalidLoanTermException.class);
        assertThatThrownBy(() -> LoanTerm.of(-12))
            .isInstanceOf(InvalidLoanTermException.class);
    }
}
```

```java
// Implementação
public record LoanTerm(int months) {

    public static final int MIN_MONTHS = 1;
    public static final int MAX_MONTHS = 96; // 8 anos (a partir de fev/2025)

    public LoanTerm {
        if (months < MIN_MONTHS || months > MAX_MONTHS) {
            throw new InvalidLoanTermException(months, MIN_MONTHS, MAX_MONTHS);
        }
    }

    public static LoanTerm of(int months) {
        return new LoanTerm(months);
    }

    public int years() {
        return months / 12;
    }

    public int remainingMonths() {
        return months % 12;
    }

    public String formatted() {
        int y = years();
        int m = remainingMonths();
        if (m == 0) {
            return y + " anos";
        }
        return y + " anos e " + m + " meses";
    }
}
```

#### Dia 4: Exceptions de Domínio

```java
// domain/exception/InvalidCPFException.java
public class InvalidCPFException extends DomainException {
    public InvalidCPFException(String message) {
        super(message);
    }
}

// domain/exception/InvalidBenefitNumberException.java
public class InvalidBenefitNumberException extends DomainException {
    public InvalidBenefitNumberException(String message) {
        super(message);
    }
}

// domain/exception/InterestRateExceededException.java
public class InterestRateExceededException extends DomainException {
    public InterestRateExceededException(BigDecimal rate, BigDecimal max) {
        super(String.format("Taxa %.2f%% excede o teto CNPS de %.2f%%", rate, max));
    }
}

// domain/exception/InvalidLoanTermException.java
public class InvalidLoanTermException extends DomainException {
    public InvalidLoanTermException(int term, int min, int max) {
        super(String.format("Prazo %d meses inválido. Deve ser entre %d e %d meses", 
            term, min, max));
    }
}

// domain/exception/DomainException.java (base)
public abstract class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
```

#### Dia 4 (continuação): Spring Modulith Setup

**1. Adicionar dependência no pom.xml:**

```xml
<dependency>
    <groupId>org.springframework.modulith</groupId>
    <artifactId>spring-modulith-starter-core</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.modulith</groupId>
    <artifactId>spring-modulith-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

**2. Teste de Arquitetura:**

```java
@Test
@DisplayName("Módulos devem estar bem estruturados")
void shouldBeWellStructuredModules() {
    ApplicationModules modules = ApplicationModules.of(CustomerApplication.class);
    modules.verify();
}

@Test
@DisplayName("Documentação de módulos")
void shouldGenerateModuleDocumentation() {
    ApplicationModules modules = ApplicationModules.of(CustomerApplication.class);
    new Documenter(modules).writeDocumentation();
}
```

### ✅ Critérios de Sucesso Sprint 0

- [ ] Projeto Maven multi-módulo criado
- [ ] **CPF, Money, BenefitNumber** implementados com TDD
- [ ] **InterestRate, MarginPercentage, LoanTerm** implementados com TDD
- [ ] **100%** dos testes passando
- [ ] Nenhuma dependência de framework no pacote `domain`
- [ ] Spring Modulith verificando arquitetura

### 💡 Reflexão

> Por que criamos Value Objects separados para `InterestRate` (taxa) e `MarginPercentage` (margem) se ambos são percentuais? Qual a vantagem?

---

## 🎯 Sprint 1: Customer Service (Arquitetura Hexagonal)

### Objetivo
Implementar o serviço de cadastro de clientes com benefício INSS e consulta de margem usando Arquitetura Hexagonal.

### 📖 Estude Primeiro

| Tópico | Arquivo | Seções |
|--------|---------|--------|
| Spring DI | `MODULO_04_SPRING.md` | 4.1 Dependency Injection |
| @Transactional | `MODULO_04_SPRING.md` | 4.3 @Transactional |
| JPA Entities | `MODULO_05_JPA_PERSISTENCIA.md` | 5.1 Entity Lifecycle |
| N+1 Problem | `MODULO_05_JPA_PERSISTENCIA.md` | 5.4 Problema N+1 |
| Flyway | `MODULO_05_JPA_PERSISTENCIA.md` | 5.8 Flyway |

**Leitura complementar:**
- Arquitetura Hexagonal: Ports & Adapters em detalhe
- Por que separar Domain Entity de JPA Entity

### 🛠️ Implemente

#### Dia 1: Entidades de Domínio

**1. Customer (Aggregate Root)**

```java
// domain/model/Customer.java
public class Customer {
    
    private CustomerId id;
    private CPF cpf;
    private String fullName;
    private LocalDate birthDate;
    private Benefit benefit;
    private CustomerStatus status;
    private Channel originChannel;
    private LocalDateTime createdAt;
    
    // Construtor privado - use factory method
    private Customer(String fullName, CPF cpf, LocalDate birthDate, Channel channel) {
        this.id = CustomerId.generate();
        this.fullName = fullName;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.status = CustomerStatus.PENDING_BENEFIT;
        this.originChannel = channel;
        this.createdAt = LocalDateTime.now();
    }
    
    // Factory method
    public static Customer create(String fullName, CPF cpf, LocalDate birthDate, Channel channel) {
        validateAge(birthDate);
        return new Customer(fullName, cpf, birthDate, channel);
    }
    
    private static void validateAge(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < 18) {
            throw new CustomerUnderageException(age);
        }
    }
    
    // Adicionar benefício (vincula cliente ao INSS)
    public void addBenefit(Benefit benefit) {
        if (this.benefit != null) {
            throw new BenefitAlreadyLinkedException(this.id);
        }
        this.benefit = benefit;
        this.status = CustomerStatus.ACTIVE;
    }
    
    // Verificar se pode solicitar empréstimo
    public boolean isEligibleForLoan() {
        return this.status == CustomerStatus.ACTIVE 
            && this.benefit != null
            && this.benefit.hasAvailableMargin();
    }
    
    // Verificar regra dos 90 dias
    public boolean isWithinRestrictionPeriod() {
        if (benefit == null) return false;
        LocalDate benefitStart = benefit.getStartDate();
        return benefitStart.plusDays(90).isAfter(LocalDate.now());
    }
    
    // Getters (sem setters - imutabilidade!)
    public CustomerId getId() { return id; }
    public CPF getCpf() { return cpf; }
    public String getFullName() { return fullName; }
    public LocalDate getBirthDate() { return birthDate; }
    public Benefit getBenefit() { return benefit; }
    public CustomerStatus getStatus() { return status; }
    public Channel getOriginChannel() { return originChannel; }
    
    // Reconstituição do banco (sem validações)
    public static Customer reconstitute(
        CustomerId id, CPF cpf, String fullName, LocalDate birthDate,
        Benefit benefit, CustomerStatus status, Channel channel, LocalDateTime createdAt
    ) {
        Customer customer = new Customer();
        customer.id = id;
        customer.cpf = cpf;
        customer.fullName = fullName;
        customer.birthDate = birthDate;
        customer.benefit = benefit;
        customer.status = status;
        customer.originChannel = channel;
        customer.createdAt = createdAt;
        return customer;
    }
    
    private Customer() {} // Para reconstituição
}
```

**2. Benefit (Entity)**

```java
// domain/model/Benefit.java
public class Benefit {

    private BenefitId id;
    private BenefitNumber number;
    private BenefitType type;
    private Money value;
    private LocalDate startDate;
    private Margin margin;
    
    public Benefit(BenefitNumber number, BenefitType type, Money value, LocalDate startDate) {
        this.id = BenefitId.generate();
        this.number = number;
        this.type = type;
        this.value = value;
        this.startDate = startDate;
        this.margin = Margin.initial(value);
    }
    
    public boolean hasAvailableMargin() {
        return margin.personalLoanAvailable().isPositive();
    }
    
    public Money getAvailableMargin() {
        return margin.personalLoanAvailable();
    }
    
    public void reserveMargin(Money amount) {
        margin = margin.reserve(amount);
    }
    
    public void releaseMargin(Money amount) {
        margin = margin.release(amount);
    }
    
    // Getters
    public BenefitId getId() { return id; }
    public BenefitNumber getNumber() { return number; }
    public BenefitType getType() { return type; }
    public Money getValue() { return value; }
    public LocalDate getStartDate() { return startDate; }
    public Margin getMargin() { return margin; }
}
```

**3. Margin (Value Object)**

```java
// domain/vo/Margin.java
public record Margin(
    Money personalLoanLimit,
    Money personalLoanUsed,
    Money creditCardLimit,
    Money creditCardUsed
) {
    
    public static Margin initial(Money benefitValue) {
        Money personalLimit = MarginPercentage.PERSONAL_LOAN.calculateMargin(benefitValue);
        Money cardLimit = MarginPercentage.CREDIT_CARD.calculateMargin(benefitValue);
        
        return new Margin(personalLimit, Money.ZERO, cardLimit, Money.ZERO);
    }
    
    public Money personalLoanAvailable() {
        return personalLoanLimit.subtract(personalLoanUsed);
    }
    
    public Money creditCardAvailable() {
        return creditCardLimit.subtract(creditCardUsed);
    }
    
    public boolean hasAvailableMargin(Money amount) {
        return personalLoanAvailable().isGreaterThanOrEqual(amount);
    }
    
    public Margin reserve(Money amount) {
        if (!hasAvailableMargin(amount)) {
            throw new InsufficientMarginException(personalLoanAvailable(), amount);
        }
        return new Margin(
            personalLoanLimit, 
            personalLoanUsed.add(amount),
            creditCardLimit,
            creditCardUsed
        );
    }
    
    public Margin release(Money amount) {
        Money newUsed = personalLoanUsed.subtract(amount);
        return new Margin(personalLoanLimit, newUsed, creditCardLimit, creditCardUsed);
    }
}
```

**4. Enums de Domínio**

```java
// domain/model/CustomerStatus.java
public enum CustomerStatus {
    PENDING_BENEFIT,   // Aguardando vinculação de benefício
    ACTIVE,            // Ativo, pode solicitar empréstimo
    BLOCKED,           // Bloqueado por inadimplência ou fraude
    INACTIVE           // Inativo
}

// domain/model/BenefitType.java
public enum BenefitType {
    APOSENTADORIA_POR_IDADE("Aposentadoria por Idade"),
    APOSENTADORIA_POR_TEMPO("Aposentadoria por Tempo de Contribuição"),
    APOSENTADORIA_INVALIDEZ("Aposentadoria por Invalidez"),
    PENSAO_POR_MORTE("Pensão por Morte"),
    BPC_LOAS("BPC/LOAS");
    
    private final String description;
    
    BenefitType(String description) {
        this.description = description;
    }
    
    public String getDescription() { return description; }
}

// domain/model/Channel.java
public enum Channel {
    DIGITAL,    // App/Web
    CORBAN,     // Correspondente Bancário
    INTERNAL    // Agência/Gerente
}
```

#### Dia 2: Ports (Interfaces)

**1. Input Ports (Use Cases)**

```java
// application/port/in/RegisterCustomerUseCase.java
public interface RegisterCustomerUseCase {
    CustomerId execute(RegisterCustomerCommand command);
}

// application/port/in/RegisterCustomerCommand.java
public record RegisterCustomerCommand(
    String fullName,
    String cpf,
    LocalDate birthDate,
    Channel channel
) {}
```

```java
// application/port/in/LinkBenefitUseCase.java
public interface LinkBenefitUseCase {
    void execute(LinkBenefitCommand command);
}

public record LinkBenefitCommand(
    CustomerId customerId,
    String benefitNumber,
    BenefitType type,
    BigDecimal benefitValue,
    LocalDate startDate
) {}
```

```java
// application/port/in/CheckMarginUseCase.java
public interface CheckMarginUseCase {
    MarginResponse execute(CheckMarginQuery query);
}

public record CheckMarginQuery(String benefitNumber) {}

public record MarginResponse(
    String benefitNumber,
    BigDecimal benefitValue,
    BigDecimal personalLoanLimit,
    BigDecimal personalLoanUsed,
    BigDecimal personalLoanAvailable,
    boolean eligible
) {}
```

```java
// application/port/in/GetCustomerUseCase.java
public interface GetCustomerUseCase {
    Optional<Customer> byId(CustomerId id);
    Optional<Customer> byCpf(CPF cpf);
}
```

**2. Output Ports (Repositories e Gateways)**

```java
// application/port/out/CustomerRepository.java
public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(CustomerId id);
    Optional<Customer> findByCpf(CPF cpf);
    boolean existsByCpf(CPF cpf);
}
```

```java
// application/port/out/MarginGateway.java
public interface MarginGateway {
    /**
     * Consulta margem no órgão governamental (Dataprev).
     * Em produção: chamada HTTP real.
     * No projeto: Mock.
     */
    MarginInfo checkMargin(BenefitNumber benefitNumber);
}

// application/port/out/MarginInfo.java
public record MarginInfo(
    BenefitNumber benefitNumber,
    BenefitType type,
    Money benefitValue,
    LocalDate startDate,
    Margin margin,
    List<String> restrictions
) {
    public boolean hasRestrictions() {
        return restrictions != null && !restrictions.isEmpty();
    }
}
```

```java
// application/port/out/EventPublisher.java
public interface EventPublisher {
    void publish(DomainEvent event);
}
```

#### Dia 3: Application Services

```java
// application/service/RegisterCustomerService.java
@Service
@RequiredArgsConstructor
@Transactional
public class RegisterCustomerService implements RegisterCustomerUseCase {
    
    private final CustomerRepository customerRepository;
    private final EventPublisher eventPublisher;
    
    @Override
    public CustomerId execute(RegisterCustomerCommand command) {
        CPF cpf = new CPF(command.cpf());
        
        // Verificar se já existe
        if (customerRepository.existsByCpf(cpf)) {
            throw new CustomerAlreadyExistsException(cpf);
        }
        
        // Criar cliente
        Customer customer = Customer.create(
            command.fullName(),
            cpf,
            command.birthDate(),
            command.channel()
        );
        
        // Salvar
        Customer saved = customerRepository.save(customer);
        
        // Publicar evento
        eventPublisher.publish(new CustomerRegisteredEvent(saved.getId(), saved.getCpf()));
        
        return saved.getId();
    }
}
```

```java
// application/service/LinkBenefitService.java
@Service
@RequiredArgsConstructor
@Transactional
public class LinkBenefitService implements LinkBenefitUseCase {
    
    private final CustomerRepository customerRepository;
    private final MarginGateway marginGateway;
    private final EventPublisher eventPublisher;
    
    @Override
    public void execute(LinkBenefitCommand command) {
        // Buscar cliente
        Customer customer = customerRepository.findById(command.customerId())
            .orElseThrow(() -> new CustomerNotFoundException(command.customerId()));
        
        // Consultar margem no órgão governamental
        BenefitNumber benefitNumber = new BenefitNumber(command.benefitNumber());
        MarginInfo marginInfo = marginGateway.checkMargin(benefitNumber);
        
        // Verificar restrições
        if (marginInfo.hasRestrictions()) {
            throw new BenefitRestrictedException(marginInfo.restrictions());
        }
        
        // Criar benefício
        Benefit benefit = new Benefit(
            benefitNumber,
            marginInfo.type(),
            marginInfo.benefitValue(),
            marginInfo.startDate()
        );
        
        // Vincular ao cliente
        customer.addBenefit(benefit);
        
        // Salvar
        customerRepository.save(customer);
        
        // Publicar evento
        eventPublisher.publish(new BenefitLinkedEvent(
            customer.getId(), 
            benefit.getId(),
            benefit.getValue()
        ));
    }
}
```

```java
// application/service/CheckMarginService.java
@Service
@RequiredArgsConstructor
public class CheckMarginService implements CheckMarginUseCase {
    
    private final MarginGateway marginGateway;
    
    @Override
    public MarginResponse execute(CheckMarginQuery query) {
        BenefitNumber number = new BenefitNumber(query.benefitNumber());
        MarginInfo info = marginGateway.checkMargin(number);
        
        Margin margin = info.margin();
        
        return new MarginResponse(
            info.benefitNumber().formatted(),
            info.benefitValue().amount(),
            margin.personalLoanLimit().amount(),
            margin.personalLoanUsed().amount(),
            margin.personalLoanAvailable().amount(),
            margin.personalLoanAvailable().isPositive()
        );
    }
}
```

#### Dia 4: Adapters de Saída (Persistence)

**1. JPA Entity (separada da Domain Entity)**

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
    
    @Enumerated(EnumType.STRING)
    @Column(name = "origin_channel", nullable = false)
    private Channel originChannel;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "benefit_id")
    private BenefitJpaEntity benefit;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Version
    private Long version;
}
```

```java
// adapter/out/persistence/entity/BenefitJpaEntity.java
@Entity
@Table(name = "benefits")
@Getter @Setter
public class BenefitJpaEntity {
    
    @Id
    private UUID id;
    
    @Column(name = "benefit_number", nullable = false, unique = true, length = 10)
    private String benefitNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "benefit_type", nullable = false)
    private BenefitType type;
    
    @Column(name = "benefit_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal value;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    // Margem embutida (desnormalizada para performance)
    @Column(name = "personal_loan_limit", precision = 15, scale = 2)
    private BigDecimal personalLoanLimit;
    
    @Column(name = "personal_loan_used", precision = 15, scale = 2)
    private BigDecimal personalLoanUsed;
    
    @Column(name = "credit_card_limit", precision = 15, scale = 2)
    private BigDecimal creditCardLimit;
    
    @Column(name = "credit_card_used", precision = 15, scale = 2)
    private BigDecimal creditCardUsed;
}
```

**2. Mapper entre Domain e JPA**

```java
// adapter/out/persistence/mapper/CustomerPersistenceMapper.java
@Component
public class CustomerPersistenceMapper {
    
    public CustomerJpaEntity toJpaEntity(Customer domain) {
        CustomerJpaEntity entity = new CustomerJpaEntity();
        entity.setId(domain.getId().value());
        entity.setCpf(domain.getCpf().value());
        entity.setFullName(domain.getFullName());
        entity.setBirthDate(domain.getBirthDate());
        entity.setStatus(domain.getStatus());
        entity.setOriginChannel(domain.getOriginChannel());
        entity.setCreatedAt(domain.getCreatedAt());
        
        if (domain.getBenefit() != null) {
            entity.setBenefit(toBenefitJpaEntity(domain.getBenefit()));
        }
        
        return entity;
    }
    
    public Customer toDomain(CustomerJpaEntity entity) {
        Benefit benefit = null;
        if (entity.getBenefit() != null) {
            benefit = toBenefitDomain(entity.getBenefit());
        }
        
        return Customer.reconstitute(
            CustomerId.of(entity.getId()),
            new CPF(entity.getCpf()),
            entity.getFullName(),
            entity.getBirthDate(),
            benefit,
            entity.getStatus(),
            entity.getOriginChannel(),
            entity.getCreatedAt()
        );
    }
    
    private BenefitJpaEntity toBenefitJpaEntity(Benefit domain) {
        BenefitJpaEntity entity = new BenefitJpaEntity();
        entity.setId(domain.getId().value());
        entity.setBenefitNumber(domain.getNumber().value());
        entity.setType(domain.getType());
        entity.setValue(domain.getValue().amount());
        entity.setStartDate(domain.getStartDate());
        
        Margin margin = domain.getMargin();
        entity.setPersonalLoanLimit(margin.personalLoanLimit().amount());
        entity.setPersonalLoanUsed(margin.personalLoanUsed().amount());
        entity.setCreditCardLimit(margin.creditCardLimit().amount());
        entity.setCreditCardUsed(margin.creditCardUsed().amount());
        
        return entity;
    }
    
    private Benefit toBenefitDomain(BenefitJpaEntity entity) {
        Margin margin = new Margin(
            Money.of(entity.getPersonalLoanLimit()),
            Money.of(entity.getPersonalLoanUsed()),
            Money.of(entity.getCreditCardLimit()),
            Money.of(entity.getCreditCardUsed())
        );
        
        return Benefit.reconstitute(
            BenefitId.of(entity.getId()),
            new BenefitNumber(entity.getBenefitNumber()),
            entity.getType(),
            Money.of(entity.getValue()),
            entity.getStartDate(),
            margin
        );
    }
}
```

**3. Repository Adapter**

```java
// adapter/out/persistence/CustomerRepositoryAdapter.java
@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {
    
    private final CustomerJpaRepository jpaRepository;
    private final CustomerPersistenceMapper mapper;
    
    @Override
    public Customer save(Customer customer) {
        CustomerJpaEntity entity = mapper.toJpaEntity(customer);
        CustomerJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Customer> findById(CustomerId id) {
        return jpaRepository.findById(id.value())
            .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Customer> findByCpf(CPF cpf) {
        return jpaRepository.findByCpf(cpf.value())
            .map(mapper::toDomain);
    }
    
    @Override
    public boolean existsByCpf(CPF cpf) {
        return jpaRepository.existsByCpf(cpf.value());
    }
}

// adapter/out/persistence/CustomerJpaRepository.java
public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, UUID> {
    Optional<CustomerJpaEntity> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}
```

**4. Mock do Gateway da Dataprev**

```java
// adapter/out/dataprev/DataprevMarginGateway.java
@Component
@RequiredArgsConstructor
@Slf4j
public class DataprevMarginGateway implements MarginGateway {
    
    // Em produção: usar Feign Client
    // No projeto: Mock
    
    @Override
    public MarginInfo checkMargin(BenefitNumber benefitNumber) {
        log.info("Consultando margem na Dataprev para benefício: {}", benefitNumber.formatted());
        
        // Simular latência do órgão governamental
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Mock: retorna dados fictícios
        Money benefitValue = Money.of(new BigDecimal("2000.00"));
        Margin margin = Margin.initial(benefitValue);
        
        return new MarginInfo(
            benefitNumber,
            BenefitType.APOSENTADORIA_POR_IDADE,
            benefitValue,
            LocalDate.now().minusMonths(6),
            margin,
            List.of() // Sem restrições
        );
    }
}
```

#### Dia 5: Adapters de Entrada (REST)

**1. Controller**

```java
// adapter/in/web/CustomerController.java
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Gerenciamento de clientes")
public class CustomerController {
    
    private final RegisterCustomerUseCase registerCustomerUseCase;
    private final LinkBenefitUseCase linkBenefitUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final CustomerDtoMapper mapper;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar novo cliente")
    public CustomerResponse register(@Valid @RequestBody RegisterCustomerRequest request) {
        RegisterCustomerCommand command = mapper.toCommand(request);
        CustomerId customerId = registerCustomerUseCase.execute(command);
        
        Customer customer = getCustomerUseCase.byId(customerId)
            .orElseThrow(() -> new IllegalStateException("Cliente recém criado não encontrado"));
        
        return mapper.toResponse(customer);
    }
    
    @PostMapping("/{customerId}/benefit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Vincular benefício INSS ao cliente")
    public void linkBenefit(
        @PathVariable UUID customerId,
        @Valid @RequestBody LinkBenefitRequest request
    ) {
        LinkBenefitCommand command = new LinkBenefitCommand(
            CustomerId.of(customerId),
            request.benefitNumber(),
            request.type(),
            request.value(),
            request.startDate()
        );
        linkBenefitUseCase.execute(command);
    }
    
    @GetMapping("/{customerId}")
    @Operation(summary = "Buscar cliente por ID")
    public CustomerResponse getById(@PathVariable UUID customerId) {
        return getCustomerUseCase.byId(CustomerId.of(customerId))
            .map(mapper::toResponse)
            .orElseThrow(() -> new CustomerNotFoundException(CustomerId.of(customerId)));
    }
    
    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar cliente por CPF")
    public CustomerResponse getByCpf(@PathVariable String cpf) {
        return getCustomerUseCase.byCpf(new CPF(cpf))
            .map(mapper::toResponse)
            .orElseThrow(() -> new CustomerNotFoundException(new CPF(cpf)));
    }
}
```

**2. DTOs**

```java
// adapter/in/web/dto/RegisterCustomerRequest.java
public record RegisterCustomerRequest(
    @NotBlank(message = "Nome é obrigatório")
    String fullName,
    
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido")
    String cpf,
    
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    LocalDate birthDate,
    
    @NotNull(message = "Canal de origem é obrigatório")
    Channel channel
) {}

// adapter/in/web/dto/LinkBenefitRequest.java
public record LinkBenefitRequest(
    @NotBlank String benefitNumber,
    @NotNull BenefitType type,
    @NotNull @Positive BigDecimal value,
    @NotNull LocalDate startDate
) {}

// adapter/in/web/dto/CustomerResponse.java
public record CustomerResponse(
    UUID id,
    String cpf,
    String fullName,
    LocalDate birthDate,
    String status,
    String channel,
    BenefitResponse benefit,
    LocalDateTime createdAt
) {}

// adapter/in/web/dto/BenefitResponse.java
public record BenefitResponse(
    String benefitNumber,
    String type,
    BigDecimal value,
    LocalDate startDate,
    MarginResponse margin
) {}

// adapter/in/web/dto/MarginResponse.java  
public record MarginResponse(
    BigDecimal personalLoanLimit,
    BigDecimal personalLoanUsed,
    BigDecimal personalLoanAvailable,
    boolean eligible
) {}
```

#### Dia 6: Flyway + Testes de Integração

**1. Migrations**

```sql
-- V1__create_customers.sql
CREATE TABLE customers (
    id UUID PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    origin_channel VARCHAR(20) NOT NULL,
    benefit_id UUID,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    
    CONSTRAINT chk_status CHECK (status IN ('PENDING_BENEFIT', 'ACTIVE', 'BLOCKED', 'INACTIVE')),
    CONSTRAINT chk_channel CHECK (origin_channel IN ('DIGITAL', 'CORBAN', 'INTERNAL'))
);

CREATE INDEX idx_customers_cpf ON customers(cpf);
CREATE INDEX idx_customers_status ON customers(status);
```

```sql
-- V2__create_benefits.sql
CREATE TABLE benefits (
    id UUID PRIMARY KEY,
    benefit_number VARCHAR(10) NOT NULL UNIQUE,
    benefit_type VARCHAR(50) NOT NULL,
    benefit_value DECIMAL(15, 2) NOT NULL,
    start_date DATE NOT NULL,
    personal_loan_limit DECIMAL(15, 2) NOT NULL,
    personal_loan_used DECIMAL(15, 2) NOT NULL DEFAULT 0,
    credit_card_limit DECIMAL(15, 2) NOT NULL,
    credit_card_used DECIMAL(15, 2) NOT NULL DEFAULT 0
);

ALTER TABLE customers ADD CONSTRAINT fk_customers_benefit 
    FOREIGN KEY (benefit_id) REFERENCES benefits(id);

CREATE INDEX idx_benefits_number ON benefits(benefit_number);
```

**2. Teste de Integração**

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class CustomerIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
        .withDatabaseName("consignado_test")
        .withUsername("test")
        .withPassword("test");
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    
    @Autowired
    TestRestTemplate restTemplate;
    
    @Test
    @DisplayName("Deve cadastrar cliente e vincular benefício")
    void deveCadastrarClienteEVincularBeneficio() {
        // 1. Cadastrar cliente
        RegisterCustomerRequest request = new RegisterCustomerRequest(
            "Maria Silva",
            "40453683886",
            LocalDate.of(1960, 5, 15),
            Channel.DIGITAL
        );
        
        ResponseEntity<CustomerResponse> response = restTemplate.postForEntity(
            "/api/v1/customers",
            request,
            CustomerResponse.class
        );
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo("PENDING_BENEFIT");
        
        UUID customerId = response.getBody().id();
        
        // 2. Vincular benefício
        LinkBenefitRequest benefitRequest = new LinkBenefitRequest(
            "1234567890",
            BenefitType.APOSENTADORIA_POR_IDADE,
            new BigDecimal("2000.00"),
            LocalDate.now().minusMonths(6)
        );
        
        restTemplate.postForEntity(
            "/api/v1/customers/{id}/benefit",
            benefitRequest,
            Void.class,
            customerId
        );
        
        // 3. Verificar cliente atualizado
        ResponseEntity<CustomerResponse> updated = restTemplate.getForEntity(
            "/api/v1/customers/{id}",
            CustomerResponse.class,
            customerId
        );
        
        assertThat(updated.getBody().status()).isEqualTo("ACTIVE");
        assertThat(updated.getBody().benefit()).isNotNull();
        assertThat(updated.getBody().benefit().margin().eligible()).isTrue();
    }
}
```

### ✅ Critérios de Sucesso Sprint 1

- [ ] Arquitetura hexagonal implementada (ports & adapters)
- [ ] Domain Entity separada de JPA Entity
- [ ] Mappers entre camadas funcionando
- [ ] CRUD de clientes funcionando
- [ ] Vinculação de benefício funcionando
- [ ] Mock da Dataprev retornando margem
- [ ] Testes de integração com TestContainers passando
- [ ] Flyway migrations aplicando corretamente

### 💡 Reflexão

> Por que criamos um Mock da Dataprev ao invés de chamar a API real? Quais técnicas usaremos depois para testar a integração real?

---

## 🎯 Sprint 2: Simulation Service (Cálculos Financeiros)

### Objetivo
Implementar o serviço de simulação de empréstimo com cálculos de parcela (Price), IOF e CET.

### 📖 Estude Primeiro

| Tópico | Arquivo | Seções |
|--------|---------|--------|
| Streams API | `MODULO_01_FUNDAMENTOS_JAVA.md` | 1.4 |
| BigDecimal | `MODULO_01_FUNDAMENTOS_JAVA.md` | 1.2 |
| Feign Client | `MODULO_06_ARQUITETURA.md` | 6.2 |
| Circuit Breaker | `MODULO_06_ARQUITETURA.md` | 6.3 |

### 🛠️ Implemente

#### Dia 1: Entidades de Domínio de Simulação

**1. Simulation (Aggregate Root)**

```java
// domain/model/Simulation.java
public class Simulation {
    
    private SimulationId id;
    private BenefitNumber benefitNumber;
    private Money requestedAmount;
    private LoanTerm term;
    private InterestRate interestRate;
    private Money installmentValue;
    private Money totalAmount;
    private Money totalInterest;
    private Money iofValue;
    private CET cet;
    private SimulationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    
    private static final int EXPIRATION_HOURS = 24;
    
    private Simulation(
        BenefitNumber benefitNumber,
        Money requestedAmount,
        LoanTerm term,
        InterestRate interestRate
    ) {
        this.id = SimulationId.generate();
        this.benefitNumber = benefitNumber;
        this.requestedAmount = requestedAmount;
        this.term = term;
        this.interestRate = interestRate;
        this.status = SimulationStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusHours(EXPIRATION_HOURS);
        
        calculate();
    }
    
    public static Simulation create(
        BenefitNumber benefitNumber,
        Money requestedAmount,
        LoanTerm term,
        InterestRate interestRate,
        Money availableMargin
    ) {
        // Validar se parcela cabe na margem
        Simulation simulation = new Simulation(benefitNumber, requestedAmount, term, interestRate);
        
        if (simulation.installmentValue.isGreaterThan(availableMargin)) {
            throw new InstallmentExceedsMarginException(
                simulation.installmentValue, 
                availableMargin
            );
        }
        
        return simulation;
    }
    
    private void calculate() {
        // 1. Calcular parcela (Sistema Price)
        this.installmentValue = calculateInstallment();
        
        // 2. Calcular total
        this.totalAmount = installmentValue.multiply(term.months());
        
        // 3. Calcular juros total
        this.totalInterest = totalAmount.subtract(requestedAmount);
        
        // 4. Calcular IOF
        this.iofValue = calculateIOF();
        
        // 5. Calcular CET
        this.cet = calculateCET();
    }
    
    /**
     * Sistema de Amortização PRICE (Parcelas Fixas)
     * PMT = PV × [ i × (1 + i)^n ] / [ (1 + i)^n - 1 ]
     */
    private Money calculateInstallment() {
        BigDecimal pv = requestedAmount.amount();
        BigDecimal i = interestRate.asDecimal(); // Taxa mensal como decimal
        int n = term.months();
        
        // (1 + i)^n
        BigDecimal onePlusIPowN = BigDecimal.ONE.add(i).pow(n);
        
        // i × (1 + i)^n
        BigDecimal numerator = i.multiply(onePlusIPowN);
        
        // (1 + i)^n - 1
        BigDecimal denominator = onePlusIPowN.subtract(BigDecimal.ONE);
        
        // PMT = PV × (numerator / denominator)
        BigDecimal pmt = pv.multiply(numerator.divide(denominator, 10, RoundingMode.HALF_UP));
        
        return Money.of(pmt);
    }
    
    /**
     * Cálculo de IOF para Pessoa Física
     * - IOF diário: 0.0082% ao dia (máx 365 dias)
     * - IOF adicional: 0.38% (fixo)
     */
    private Money calculateIOF() {
        BigDecimal principal = requestedAmount.amount();
        
        // IOF adicional (fixo)
        BigDecimal iofAdicional = principal.multiply(new BigDecimal("0.0038"));
        
        // IOF diário (considerando prazo médio das parcelas)
        int prazoMedioDias = (term.months() * 30) / 2; // Simplificação
        prazoMedioDias = Math.min(prazoMedioDias, 365); // Máximo 365 dias
        
        BigDecimal taxaDiaria = new BigDecimal("0.000082"); // 0.0082%
        BigDecimal iofDiario = principal.multiply(taxaDiaria).multiply(BigDecimal.valueOf(prazoMedioDias));
        
        return Money.of(iofAdicional.add(iofDiario));
    }
    
    /**
     * CET - Custo Efetivo Total (simplificado)
     * CET > Taxa nominal sempre
     */
    private CET calculateCET() {
        // CET = (Total pago / Valor líquido)^(12/n) - 1
        Money valorLiquido = requestedAmount.subtract(iofValue);
        
        BigDecimal fator = totalAmount.amount()
            .divide(valorLiquido.amount(), 10, RoundingMode.HALF_UP);
        
        // Raiz n-ésima aproximada
        double cetAnual = Math.pow(fator.doubleValue(), 12.0 / term.months()) - 1;
        
        return CET.of(BigDecimal.valueOf(cetAnual * 100));
    }
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
    
    public void expire() {
        this.status = SimulationStatus.EXPIRED;
    }
    
    public void convertToProposal() {
        if (isExpired()) {
            throw new SimulationExpiredException(this.id);
        }
        this.status = SimulationStatus.CONVERTED;
    }
    
    // Getters
    public SimulationId getId() { return id; }
    public BenefitNumber getBenefitNumber() { return benefitNumber; }
    public Money getRequestedAmount() { return requestedAmount; }
    public LoanTerm getTerm() { return term; }
    public InterestRate getInterestRate() { return interestRate; }
    public Money getInstallmentValue() { return installmentValue; }
    public Money getTotalAmount() { return totalAmount; }
    public Money getTotalInterest() { return totalInterest; }
    public Money getIofValue() { return iofValue; }
    public CET getCet() { return cet; }
    public SimulationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
}
```

**2. CET (Value Object)**

```java
// domain/vo/CET.java
public record CET(BigDecimal annual) {
    
    public CET {
        if (annual == null || annual.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("CET deve ser positivo");
        }
        annual = annual.setScale(2, RoundingMode.HALF_UP);
    }
    
    public static CET of(BigDecimal annual) {
        return new CET(annual);
    }
    
    public BigDecimal monthly() {
        // Aproximação: CET mensal = CET anual / 12
        return annual.divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
    }
    
    public String formatted() {
        return annual.setScale(2, RoundingMode.HALF_UP) + "% a.a.";
    }
}
```

**3. Testes de Cálculo**

```java
@DisplayName("Simulation - Cálculos Financeiros")
class SimulationCalculationTest {
    
    @Test
    @DisplayName("Deve calcular parcela corretamente (Price)")
    void deveCalcularParcelaCorretamente() {
        // Given: Empréstimo de R$ 10.000 em 48 meses a 1.66% a.m.
        Money requestedAmount = Money.of(new BigDecimal("10000.00"));
        LoanTerm term = LoanTerm.of(48);
        InterestRate rate = InterestRate.of(new BigDecimal("1.66"));
        BenefitNumber benefit = new BenefitNumber("1234567890");
        Money availableMargin = Money.of(new BigDecimal("700.00")); // Margem suficiente
        
        // When
        Simulation simulation = Simulation.create(
            benefit, requestedAmount, term, rate, availableMargin
        );
        
        // Then - Parcela esperada: ~R$ 318,71
        assertThat(simulation.getInstallmentValue().amount())
            .isCloseTo(new BigDecimal("318.71"), Percentage.withPercentage(1));
    }
    
    @Test
    @DisplayName("Deve calcular IOF corretamente")
    void deveCalcularIOFCorretamente() {
        // Given
        Money amount = Money.of(new BigDecimal("10000.00"));
        LoanTerm term = LoanTerm.of(48);
        InterestRate rate = InterestRate.of(new BigDecimal("1.66"));
        BenefitNumber benefit = new BenefitNumber("1234567890");
        Money margin = Money.of(new BigDecimal("700.00"));
        
        // When
        Simulation simulation = Simulation.create(benefit, amount, term, rate, margin);
        
        // Then - IOF deve ser positivo e menor que 4% do valor
        assertThat(simulation.getIofValue().amount())
            .isGreaterThan(BigDecimal.ZERO)
            .isLessThan(amount.amount().multiply(new BigDecimal("0.04")));
    }
    
    @Test
    @DisplayName("Deve rejeitar simulação quando parcela excede margem")
    void deveRejeitarQuandoParcelaExcedeMargem() {
        // Given: Margem de R$ 200 mas parcela será ~R$ 318
        Money amount = Money.of(new BigDecimal("10000.00"));
        LoanTerm term = LoanTerm.of(48);
        InterestRate rate = InterestRate.of(new BigDecimal("1.66"));
        BenefitNumber benefit = new BenefitNumber("1234567890");
        Money availableMargin = Money.of(new BigDecimal("200.00")); // Margem insuficiente
        
        // When/Then
        assertThatThrownBy(() -> 
            Simulation.create(benefit, amount, term, rate, availableMargin)
        )
        .isInstanceOf(InstallmentExceedsMarginException.class)
        .hasMessageContaining("margem");
    }
    
    @Test
    @DisplayName("CET deve ser maior que taxa nominal")
    void cetDevSerMaiorQueTaxaNominal() {
        // Given
        Money amount = Money.of(new BigDecimal("10000.00"));
        LoanTerm term = LoanTerm.of(48);
        InterestRate rate = InterestRate.of(new BigDecimal("1.66"));
        BenefitNumber benefit = new BenefitNumber("1234567890");
        Money margin = Money.of(new BigDecimal("700.00"));
        
        // When
        Simulation simulation = Simulation.create(benefit, amount, term, rate, margin);
        
        // Then - CET anual deve ser maior que taxa anual nominal (~21.7%)
        assertThat(simulation.getCet().annual())
            .isGreaterThan(rate.annual());
    }
}
```

#### Dia 2: Ports e Services

```java
// application/port/in/SimulateLoanUseCase.java
public interface SimulateLoanUseCase {
    SimulationResponse execute(SimulateLoanCommand command);
}

public record SimulateLoanCommand(
    String benefitNumber,
    BigDecimal requestedAmount,
    int termMonths,
    BigDecimal interestRate // Pode ser null para usar taxa padrão
) {}

public record SimulationResponse(
    UUID simulationId,
    String benefitNumber,
    BigDecimal requestedAmount,
    int termMonths,
    BigDecimal monthlyRate,
    BigDecimal installmentValue,
    BigDecimal totalAmount,
    BigDecimal totalInterest,
    BigDecimal iofValue,
    BigDecimal cetAnnual,
    LocalDateTime expiresAt
) {}
```

```java
// application/service/SimulateLoanService.java
@Service
@RequiredArgsConstructor
public class SimulateLoanService implements SimulateLoanUseCase {
    
    private final MarginGateway marginGateway;
    private final SimulationRepository simulationRepository;
    
    private static final BigDecimal DEFAULT_RATE = new BigDecimal("1.66");
    
    @Override
    @Transactional
    public SimulationResponse execute(SimulateLoanCommand command) {
        // 1. Consultar margem disponível
        BenefitNumber benefitNumber = new BenefitNumber(command.benefitNumber());
        MarginInfo marginInfo = marginGateway.checkMargin(benefitNumber);
        
        Money availableMargin = marginInfo.margin().personalLoanAvailable();
        
        // 2. Determinar taxa de juros
        BigDecimal rateValue = command.interestRate() != null 
            ? command.interestRate() 
            : DEFAULT_RATE;
        InterestRate rate = InterestRate.of(rateValue);
        
        // 3. Criar simulação
        Simulation simulation = Simulation.create(
            benefitNumber,
            Money.of(command.requestedAmount()),
            LoanTerm.of(command.termMonths()),
            rate,
            availableMargin
        );
        
        // 4. Salvar
        simulationRepository.save(simulation);
        
        // 5. Retornar resposta
        return toResponse(simulation);
    }
    
    private SimulationResponse toResponse(Simulation s) {
        return new SimulationResponse(
            s.getId().value(),
            s.getBenefitNumber().formatted(),
            s.getRequestedAmount().amount(),
            s.getTerm().months(),
            s.getInterestRate().monthly(),
            s.getInstallmentValue().amount(),
            s.getTotalAmount().amount(),
            s.getTotalInterest().amount(),
            s.getIofValue().amount(),
            s.getCet().annual(),
            s.getExpiresAt()
        );
    }
}
```

#### Dia 3: Feign Client para comunicação entre serviços

```java
// adapter/out/feign/CustomerServiceClient.java
@FeignClient(name = "consignado-customer-service", url = "${customer-service.url}")
public interface CustomerServiceClient {
    
    @GetMapping("/api/v1/margin/{benefitNumber}")
    MarginResponse checkMargin(@PathVariable String benefitNumber);
}

// adapter/out/feign/FeignMarginGateway.java
@Component
@RequiredArgsConstructor
public class FeignMarginGateway implements MarginGateway {
    
    private final CustomerServiceClient customerClient;
    
    @Override
    public MarginInfo checkMargin(BenefitNumber benefitNumber) {
        MarginResponse response = customerClient.checkMargin(benefitNumber.value());
        
        return new MarginInfo(
            benefitNumber,
            BenefitType.APOSENTADORIA_POR_IDADE, // Simplificado
            Money.of(response.benefitValue()),
            LocalDate.now(),
            new Margin(
                Money.of(response.personalLoanLimit()),
                Money.of(response.personalLoanUsed()),
                Money.ZERO,
                Money.ZERO
            ),
            List.of()
        );
    }
}
```

#### Dia 4: Controller e Testes

```java
// adapter/in/web/SimulationController.java
@RestController
@RequestMapping("/api/v1/simulations")
@RequiredArgsConstructor
public class SimulationController {
    
    private final SimulateLoanUseCase simulateLoanUseCase;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Simular empréstimo consignado")
    public SimulationResponse simulate(@Valid @RequestBody SimulateLoanRequest request) {
        SimulateLoanCommand command = new SimulateLoanCommand(
            request.benefitNumber(),
            request.requestedAmount(),
            request.termMonths(),
            request.interestRate()
        );
        return simulateLoanUseCase.execute(command);
    }
}

// adapter/in/web/dto/SimulateLoanRequest.java
public record SimulateLoanRequest(
    @NotBlank String benefitNumber,
    @NotNull @Positive @Max(50000) BigDecimal requestedAmount,
    @NotNull @Min(1) @Max(96) Integer termMonths,
    @Positive @DecimalMax("1.66") BigDecimal interestRate
) {}
```

### ✅ Critérios de Sucesso Sprint 2

- [ ] Cálculo de parcela Price implementado e testado
- [ ] IOF calculado corretamente
- [ ] CET calculado e maior que taxa nominal
- [ ] Validação de margem funcionando
- [ ] Feign Client configurado
- [ ] Simulação com expiração (24h)

---

## 🎯 Sprint 3: Credit Analysis + Kafka

### Objetivo
Implementar análise de crédito automática com regras de negócio e comunicação via Kafka.

### 📖 Estude Primeiro

| Tópico | Arquivo | Seções |
|--------|---------|--------|
| Kafka | `MODULO_06_ARQUITETURA.md` | 6.4 |
| Event-Driven | `MODULO_06_ARQUITETURA.md` | 6.5 |
| Contract Testing | `MODULO_07_TESTES.md` | 7.6 |

### 🛠️ Implemente

#### Dia 1: Análise de Crédito

```java
// domain/model/CreditAnalysis.java
public class CreditAnalysis {
    
    private CreditAnalysisId id;
    private SimulationId simulationId;
    private CustomerId customerId;
    private CreditScore score;
    private AnalysisResult result;
    private List<String> rejectionReasons;
    private LocalDateTime analyzedAt;
    
    public static CreditAnalysis analyze(
        SimulationId simulationId,
        CustomerId customerId,
        CreditScore score,
        boolean hasRestrictions,
        boolean isWithin90Days,
        Channel channel
    ) {
        CreditAnalysis analysis = new CreditAnalysis();
        analysis.id = CreditAnalysisId.generate();
        analysis.simulationId = simulationId;
        analysis.customerId = customerId;
        analysis.score = score;
        analysis.rejectionReasons = new ArrayList<>();
        analysis.analyzedAt = LocalDateTime.now();
        
        // Aplicar regras de negócio
        analysis.applyRules(hasRestrictions, isWithin90Days, channel);
        
        return analysis;
    }
    
    private void applyRules(boolean hasRestrictions, boolean isWithin90Days, Channel channel) {
        // Regra 1: Score mínimo
        if (score.value() < 400) {
            rejectionReasons.add("Score de crédito insuficiente (mínimo 400)");
        }
        
        // Regra 2: Restrições cadastrais
        if (hasRestrictions) {
            rejectionReasons.add("Cliente possui restrições cadastrais");
        }
        
        // Regra 3: Regra dos 90 dias (novos beneficiários)
        if (isWithin90Days && channel != Channel.INTERNAL) {
            // Dentro dos 90 dias, só pode contratar pelo banco pagador
            rejectionReasons.add("Beneficiário recente - apenas canal interno permitido");
        }
        
        // Determinar resultado
        this.result = rejectionReasons.isEmpty() 
            ? AnalysisResult.APPROVED 
            : AnalysisResult.REJECTED;
    }
    
    public boolean isApproved() {
        return result == AnalysisResult.APPROVED;
    }
    
    // Getters
}

// domain/vo/CreditScore.java
public record CreditScore(int value) {
    
    public static final int MIN = 0;
    public static final int MAX = 1000;
    
    public CreditScore {
        if (value < MIN || value > MAX) {
            throw new IllegalArgumentException("Score deve estar entre 0 e 1000");
        }
    }
    
    public static CreditScore of(int value) {
        return new CreditScore(value);
    }
    
    public String classification() {
        if (value >= 800) return "EXCELENTE";
        if (value >= 600) return "BOM";
        if (value >= 400) return "REGULAR";
        return "RUIM";
    }
}
```

#### Dia 2: Eventos de Domínio e Kafka

```java
// domain/event/CreditAnalysisCompletedEvent.java
public record CreditAnalysisCompletedEvent(
    String eventId,
    String analysisId,
    String simulationId,
    String customerId,
    String result, // APPROVED ou REJECTED
    List<String> rejectionReasons,
    LocalDateTime occurredAt
) implements DomainEvent {}

// application/port/out/EventPublisher.java
public interface EventPublisher {
    void publish(DomainEvent event);
}
```

```java
// adapter/out/kafka/KafkaEventPublisher.java
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    private static final String CREDIT_ANALYSIS_TOPIC = "credit-analysis-events";
    
    @Override
    public void publish(DomainEvent event) {
        if (event instanceof CreditAnalysisCompletedEvent e) {
            kafkaTemplate.send(CREDIT_ANALYSIS_TOPIC, e.analysisId(), e);
        }
    }
}
```

```java
// Configuração Kafka
@Configuration
public class KafkaConfig {
    
    @Bean
    public NewTopic creditAnalysisTopic() {
        return TopicBuilder.name("credit-analysis-events")
            .partitions(3)
            .replicas(1)
            .build();
    }
    
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }
}
```

#### Dia 3: Consumer no Loan Service

```java
// loan-service: adapter/in/kafka/CreditAnalysisEventConsumer.java
@Component
@RequiredArgsConstructor
@Slf4j
public class CreditAnalysisEventConsumer {
    
    private final ProcessApprovedProposalUseCase processApprovedUseCase;
    private final RejectProposalUseCase rejectProposalUseCase;
    
    @KafkaListener(topics = "credit-analysis-events", groupId = "loan-service")
    public void consume(CreditAnalysisCompletedEvent event) {
        log.info("Recebido evento de análise: {}", event.analysisId());
        
        if ("APPROVED".equals(event.result())) {
            processApprovedUseCase.execute(new ApprovedProposalCommand(
                event.simulationId(),
                event.customerId()
            ));
        } else {
            rejectProposalUseCase.execute(new RejectProposalCommand(
                event.simulationId(),
                event.rejectionReasons()
            ));
        }
    }
}
```

#### Dia 4: Idempotência com Redis

```java
// Anotação customizada para idempotência
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    String keyExpression();
    int ttlSeconds() default 86400; // 24 horas
}

// Aspect para idempotência
@Aspect
@Component
@RequiredArgsConstructor
public class IdempotencyAspect {
    
    private final StringRedisTemplate redisTemplate;
    
    @Around("@annotation(idempotent)")
    public Object checkIdempotency(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {
        String key = resolveKey(joinPoint, idempotent.keyExpression());
        String redisKey = "idempotency:" + key;
        
        Boolean isNew = redisTemplate.opsForValue().setIfAbsent(
            redisKey, 
            "processing",
            Duration.ofSeconds(idempotent.ttlSeconds())
        );
        
        if (Boolean.FALSE.equals(isNew)) {
            throw new DuplicateOperationException("Operação já processada: " + key);
        }
        
        try {
            Object result = joinPoint.proceed();
            redisTemplate.opsForValue().set(redisKey, "completed");
            return result;
        } catch (Exception e) {
            redisTemplate.delete(redisKey);
            throw e;
        }
    }
    
    private String resolveKey(ProceedingJoinPoint joinPoint, String expression) {
        // Implementar resolução de SpEL
        // Exemplo: #command.simulationId
        return expression; // Simplificado
    }
}
```

```java
// Uso da anotação
@Service
public class ProcessApprovedProposalService implements ProcessApprovedProposalUseCase {
    
    @Override
    @Idempotent(keyExpression = "#command.simulationId")
    @Transactional
    public void execute(ApprovedProposalCommand command) {
        // Processar proposta aprovada
        // Mesmo que o evento chegue duplicado, será executado apenas uma vez
    }
}
```

### ✅ Critérios de Sucesso Sprint 3

- [ ] Análise de crédito com regras de negócio
- [ ] Score como Value Object
- [ ] Eventos publicados no Kafka
- [ ] Consumer processando eventos
- [ ] Idempotência com Redis
- [ ] Regra dos 90 dias implementada

---

## 🎯 Sprint 4: Loan Service + Averbation

### Objetivo
Implementar gestão de contratos e integração assíncrona com Dataprev para averbação.

### 📖 Estude Primeiro

| Tópico | Arquivo | Seções |
|--------|---------|--------|
| Saga Pattern | `MODULO_06_ARQUITETURA.md` | 6.6 |
| Resilience4j | `MODULO_06_ARQUITETURA.md` | 6.3 |
| Idempotência | `MODULO_13_SPRING_MODULITH.md` | 13.4 |

### 🛠️ Implemente

#### Dia 1: Contract Aggregate

```java
// domain/model/Contract.java
public class Contract {
    
    private ContractId id;
    private CustomerId customerId;
    private SimulationId simulationId;
    private BenefitNumber benefitNumber;
    private Money principalAmount;
    private InterestRate interestRate;
    private LoanTerm term;
    private Money installmentValue;
    private CET cet;
    private OperationType operationType;
    private Channel channel;
    private ContractStatus status;
    private Averbation averbation;
    private LocalDateTime contractedAt;
    private LocalDateTime activatedAt;
    
    public static Contract create(
        CustomerId customerId,
        Simulation simulation,
        Channel channel,
        OperationType operationType
    ) {
        simulation.convertToProposal();
        
        Contract contract = new Contract();
        contract.id = ContractId.generate();
        contract.customerId = customerId;
        contract.simulationId = simulation.getId();
        contract.benefitNumber = simulation.getBenefitNumber();
        contract.principalAmount = simulation.getRequestedAmount();
        contract.interestRate = simulation.getInterestRate();
        contract.term = simulation.getTerm();
        contract.installmentValue = simulation.getInstallmentValue();
        contract.cet = simulation.getCet();
        contract.operationType = operationType;
        contract.channel = channel;
        contract.status = ContractStatus.PENDING_AVERBATION;
        contract.contractedAt = LocalDateTime.now();
        
        return contract;
    }
    
    public void requestAverbation(String protocol) {
        this.averbation = Averbation.requested(protocol);
        this.status = ContractStatus.AVERBATION_IN_PROGRESS;
    }
    
    public void confirmAverbation(String averbationCode) {
        if (this.averbation == null) {
            throw new IllegalStateException("Averbação não foi solicitada");
        }
        this.averbation = this.averbation.confirm(averbationCode);
        this.status = ContractStatus.ACTIVE;
        this.activatedAt = LocalDateTime.now();
    }
    
    public void failAverbation(String reason) {
        if (this.averbation != null) {
            this.averbation = this.averbation.fail(reason);
        }
        this.status = ContractStatus.AVERBATION_FAILED;
    }
    
    public void cancel(String reason) {
        this.status = ContractStatus.CANCELLED;
    }
    
    // Getters
}

// domain/model/Averbation.java
public record Averbation(
    String protocol,
    String averbationCode,
    AverbationStatus status,
    String failureReason,
    LocalDateTime requestedAt,
    LocalDateTime confirmedAt,
    int retryCount
) {
    public static Averbation requested(String protocol) {
        return new Averbation(
            protocol, null, AverbationStatus.REQUESTED, null,
            LocalDateTime.now(), null, 0
        );
    }
    
    public Averbation confirm(String averbationCode) {
        return new Averbation(
            this.protocol, averbationCode, AverbationStatus.CONFIRMED, null,
            this.requestedAt, LocalDateTime.now(), this.retryCount
        );
    }
    
    public Averbation fail(String reason) {
        return new Averbation(
            this.protocol, null, AverbationStatus.FAILED, reason,
            this.requestedAt, null, this.retryCount
        );
    }
    
    public Averbation retry() {
        return new Averbation(
            this.protocol, null, AverbationStatus.RETRYING, null,
            this.requestedAt, null, this.retryCount + 1
        );
    }
}
```

#### Dia 2-3: Saga de Averbação

```java
// application/service/AverbationSagaOrchestrator.java
@Service
@RequiredArgsConstructor
@Slf4j
public class AverbationSagaOrchestrator {
    
    private final ContractRepository contractRepository;
    private final EventPublisher eventPublisher;
    
    @Transactional
    public void startAverbation(ContractId contractId) {
        Contract contract = contractRepository.findById(contractId)
            .orElseThrow(() -> new ContractNotFoundException(contractId));
        
        String protocol = generateProtocol();
        contract.requestAverbation(protocol);
        contractRepository.save(contract);
        
        // Publicar evento para o conector processar
        eventPublisher.publish(new AverbationRequestedEvent(
            contract.getId().value().toString(),
            protocol,
            contract.getBenefitNumber().value(),
            contract.getInstallmentValue().amount(),
            contract.getTerm().months()
        ));
        
        log.info("Averbação solicitada para contrato {} com protocolo {}", 
            contractId, protocol);
    }
    
    @Transactional
    public void completeAverbation(String protocol, String averbationCode) {
        Contract contract = contractRepository.findByAverbationProtocol(protocol)
            .orElseThrow(() -> new ContractNotFoundException("Protocolo: " + protocol));
        
        contract.confirmAverbation(averbationCode);
        contractRepository.save(contract);
        
        eventPublisher.publish(new ContractActivatedEvent(
            contract.getId().value().toString(),
            contract.getCustomerId().value().toString(),
            averbationCode
        ));
        
        log.info("Contrato {} ativado com averbação {}", 
            contract.getId(), averbationCode);
    }
    
    @Transactional
    public void handleAverbationFailure(String protocol, String reason, boolean shouldRetry) {
        Contract contract = contractRepository.findByAverbationProtocol(protocol)
            .orElseThrow();
        
        if (shouldRetry && contract.getAverbation().retryCount() < 3) {
            // Agenda retry
            log.warn("Averbação falhou para contrato {}, agendando retry", contract.getId());
            eventPublisher.publish(new AverbationRetryScheduledEvent(
                contract.getId().value().toString(),
                protocol,
                contract.getAverbation().retryCount() + 1
            ));
        } else {
            contract.failAverbation(reason);
            contractRepository.save(contract);
            log.error("Averbação falhou definitivamente para contrato {}: {}", 
                contract.getId(), reason);
        }
    }
    
    private String generateProtocol() {
        return "AVB" + System.currentTimeMillis();
    }
}
```

#### Dia 4: Conector Dataprev com Resilience4j

```java
// averbation-connector/adapter/in/kafka/AverbationRequestConsumer.java
@Component
@RequiredArgsConstructor
@Slf4j
public class AverbationRequestConsumer {
    
    private final DataprevClient dataprevClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @KafkaListener(topics = "averbation-requests", groupId = "averbation-connector")
    @Idempotent(keyExpression = "#event.protocol")
    public void consume(AverbationRequestedEvent event) {
        log.info("Processando averbação: {}", event.protocol());
        
        try {
            String averbationCode = dataprevClient.averbar(
                event.benefitNumber(),
                event.installmentValue(),
                event.termMonths()
            );
            
            kafkaTemplate.send("averbation-responses", 
                new AverbationCompletedEvent(event.protocol(), averbationCode));
                
        } catch (DataprevUnavailableException e) {
            kafkaTemplate.send("averbation-responses",
                new AverbationFailedEvent(event.protocol(), e.getMessage(), true));
                
        } catch (Exception e) {
            kafkaTemplate.send("averbation-responses",
                new AverbationFailedEvent(event.protocol(), e.getMessage(), false));
        }
    }
}

// averbation-connector/adapter/out/dataprev/DataprevClient.java
@Component
@Slf4j
public class DataprevClient {
    
    @CircuitBreaker(name = "dataprev", fallbackMethod = "fallback")
    @Retry(name = "dataprev")
    @TimeLimiter(name = "dataprev")
    public String averbar(String benefitNumber, BigDecimal installment, int term) {
        log.info("Chamando Dataprev para averbação: benefício={}", benefitNumber);
        
        // Simular chamada HTTP para Dataprev
        simulateLatency();
        
        if (shouldSimulateError()) {
            throw new DataprevUnavailableException("Dataprev indisponível");
        }
        
        // Retornar código de averbação
        return "AVB" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private String fallback(String benefitNumber, BigDecimal installment, int term, Exception e) {
        log.warn("Fallback acionado para Dataprev: {}", e.getMessage());
        throw new DataprevUnavailableException("Dataprev temporariamente indisponível");
    }
    
    private void simulateLatency() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(500, 2000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private boolean shouldSimulateError() {
        return ThreadLocalRandom.current().nextInt(10) < 2; // 20% de erro
    }
}
```

```yaml
# application.yml - Configuração Resilience4j
resilience4j:
  circuitbreaker:
    instances:
      dataprev:
        slidingWindowSize: 10
        failureRateThreshold: 50
        waitDurationInOpenState: 30s
        permittedNumberOfCallsInHalfOpenState: 3
  
  retry:
    instances:
      dataprev:
        maxAttempts: 3
        waitDuration: 2s
        exponentialBackoffMultiplier: 2
        
  timelimiter:
    instances:
      dataprev:
        timeoutDuration: 5s
```

### ✅ Critérios de Sucesso Sprint 4

- [ ] Contract Aggregate com estados
- [ ] Saga de averbação implementada
- [ ] Eventos de averbação no Kafka
- [ ] Resilience4j configurado (circuit breaker, retry)
- [ ] Fallback para indisponibilidade
- [ ] Retry automático com backoff

---

## 🎯 Sprint 5: Payment Service

### Objetivo
Implementar gestão de parcelas, vencimentos e quitação.

### 📖 Estude Primeiro

| Tópico | Arquivo | Seções |
|--------|---------|--------|
| Scheduler | `MODULO_04_SPRING.md` | 4.5 |
| JPA Locking | `MODULO_05_JPA_PERSISTENCIA.md` | 5.6 |

### 🛠️ Implemente

#### Dia 1-2: Installment e Payment

```java
// domain/model/Installment.java
public class Installment {
    
    private InstallmentId id;
    private ContractId contractId;
    private int number;
    private Money principalAmount;
    private Money interestAmount;
    private Money totalAmount;
    private LocalDate dueDate;
    private InstallmentStatus status;
    private Payment payment;
    
    public static List<Installment> generate(Contract contract) {
        List<Installment> installments = new ArrayList<>();
        
        Money monthlyValue = contract.getInstallmentValue();
        LocalDate firstDueDate = LocalDate.now().plusMonths(1).withDayOfMonth(5);
        
        for (int i = 1; i <= contract.getTerm().months(); i++) {
            Installment installment = new Installment();
            installment.id = InstallmentId.generate();
            installment.contractId = contract.getId();
            installment.number = i;
            installment.totalAmount = monthlyValue;
            installment.dueDate = firstDueDate.plusMonths(i - 1);
            installment.status = InstallmentStatus.PENDING;
            
            // Calcular amortização e juros (simplificado)
            installment.interestAmount = calculateInterest(contract, i);
            installment.principalAmount = monthlyValue.subtract(installment.interestAmount);
            
            installments.add(installment);
        }
        
        return installments;
    }
    
    public void pay(Money amount, LocalDate paymentDate) {
        if (status != InstallmentStatus.PENDING && status != InstallmentStatus.OVERDUE) {
            throw new InstallmentAlreadyPaidException(this.id);
        }
        
        this.payment = new Payment(
            PaymentId.generate(),
            amount,
            paymentDate,
            paymentDate.isAfter(dueDate) ? PaymentType.LATE : PaymentType.ON_TIME
        );
        
        this.status = InstallmentStatus.PAID;
    }
    
    public void markAsOverdue() {
        if (status == InstallmentStatus.PENDING && LocalDate.now().isAfter(dueDate)) {
            this.status = InstallmentStatus.OVERDUE;
        }
    }
    
    public Money calculateEarlyPaymentDiscount() {
        if (status != InstallmentStatus.PENDING) {
            return Money.ZERO;
        }
        
        long daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
        if (daysUntilDue <= 0) {
            return Money.ZERO;
        }
        
        // Desconto proporcional aos dias antecipados (simplificado)
        BigDecimal discountRate = new BigDecimal("0.0005"); // 0.05% ao dia
        BigDecimal discount = totalAmount.amount()
            .multiply(discountRate)
            .multiply(BigDecimal.valueOf(daysUntilDue));
        
        return Money.of(discount);
    }
    
    // Getters
}
```

#### Dia 3: Scheduler de Vencimentos

```java
// application/service/InstallmentScheduler.java
@Component
@RequiredArgsConstructor
@Slf4j
public class InstallmentScheduler {
    
    private final InstallmentRepository installmentRepository;
    private final NotificationService notificationService;
    private final EventPublisher eventPublisher;
    
    // Executar todo dia às 6h
    @Scheduled(cron = "0 0 6 * * *")
    @Transactional
    public void checkDueInstallments() {
        log.info("Verificando parcelas vencidas...");
        
        LocalDate today = LocalDate.now();
        
        // 1. Marcar parcelas vencidas
        List<Installment> overdue = installmentRepository
            .findByStatusAndDueDateBefore(InstallmentStatus.PENDING, today);
        
        overdue.forEach(installment -> {
            installment.markAsOverdue();
            installmentRepository.save(installment);
            
            eventPublisher.publish(new InstallmentOverdueEvent(
                installment.getId().value().toString(),
                installment.getContractId().value().toString()
            ));
        });
        
        log.info("{} parcelas marcadas como vencidas", overdue.size());
        
        // 2. Notificar parcelas a vencer em 3 dias
        LocalDate threeDaysFromNow = today.plusDays(3);
        List<Installment> upcoming = installmentRepository
            .findByStatusAndDueDate(InstallmentStatus.PENDING, threeDaysFromNow);
        
        upcoming.forEach(installment -> {
            notificationService.sendDueReminder(installment);
        });
        
        log.info("{} lembretes de vencimento enviados", upcoming.size());
    }
}
```

#### Dia 4: Quitação Antecipada

```java
// application/port/in/SettleContractUseCase.java
public interface SettleContractUseCase {
    SettlementResponse execute(SettleContractCommand command);
}

public record SettleContractCommand(
    UUID contractId,
    boolean fullSettlement // true = quitar tudo, false = apenas parcelas selecionadas
) {}

public record SettlementResponse(
    UUID contractId,
    BigDecimal originalBalance,
    BigDecimal discount,
    BigDecimal finalAmount,
    int installmentsPaid
) {}

// application/service/SettleContractService.java
@Service
@RequiredArgsConstructor
@Transactional
public class SettleContractService implements SettleContractUseCase {
    
    private final ContractRepository contractRepository;
    private final InstallmentRepository installmentRepository;
    private final EventPublisher eventPublisher;
    
    @Override
    public SettlementResponse execute(SettleContractCommand command) {
        ContractId contractId = ContractId.of(command.contractId());
        
        Contract contract = contractRepository.findById(contractId)
            .orElseThrow(() -> new ContractNotFoundException(contractId));
        
        List<Installment> pendingInstallments = installmentRepository
            .findByContractIdAndStatus(contractId, InstallmentStatus.PENDING);
        
        if (pendingInstallments.isEmpty()) {
            throw new NoInstallmentsToPay(contractId);
        }
        
        // Calcular valores
        Money originalBalance = pendingInstallments.stream()
            .map(Installment::getTotalAmount)
            .reduce(Money.ZERO, Money::add);
        
        Money totalDiscount = pendingInstallments.stream()
            .map(Installment::calculateEarlyPaymentDiscount)
            .reduce(Money.ZERO, Money::add);
        
        Money finalAmount = originalBalance.subtract(totalDiscount);
        
        // Quitar parcelas
        LocalDate today = LocalDate.now();
        pendingInstallments.forEach(installment -> {
            installment.pay(installment.getTotalAmount(), today);
            installmentRepository.save(installment);
        });
        
        // Encerrar contrato
        contract.settle();
        contractRepository.save(contract);
        
        // Publicar evento
        eventPublisher.publish(new ContractSettledEvent(
            contractId.value().toString(),
            finalAmount.amount()
        ));
        
        return new SettlementResponse(
            contractId.value(),
            originalBalance.amount(),
            totalDiscount.amount(),
            finalAmount.amount(),
            pendingInstallments.size()
        );
    }
}
```

### ✅ Critérios de Sucesso Sprint 5

- [ ] Parcelas geradas ao ativar contrato
- [ ] Scheduler verificando vencimentos
- [ ] Notificações de vencimento
- [ ] Cálculo de desconto antecipação
- [ ] Quitação total funcionando

---

## 🎯 Sprint 6: API Gateway + Keycloak

### Objetivo
Implementar gateway de entrada com autenticação OAuth2/OIDC.

### 📖 Estude Primeiro

| Tópico | Arquivo | Seções |
|--------|---------|--------|
| Spring Security | `MODULO_12_SEGURANCA.md` | 12.1, 12.2 |
| OAuth2/OIDC | `MODULO_12_SEGURANCA.md` | 12.3 |
| JWT | `MODULO_12_SEGURANCA.md` | 12.4 |

### 🛠️ Implemente

#### Dia 1-2: Spring Cloud Gateway

```java
// gateway/src/main/resources/application.yml
spring:
  cloud:
    gateway:
      routes:
        - id: customer-service
          uri: lb://consignado-customer-service
          predicates:
            - Path=/api/v1/customers/**
          filters:
            - RewritePath=/api/v1/customers/(?<segment>.*), /api/v1/customers/${segment}
            - AddRequestHeader=X-Request-Source, GATEWAY
            
        - id: simulation-service
          uri: lb://consignado-simulation-service
          predicates:
            - Path=/api/v1/simulations/**
            
        - id: loan-service
          uri: lb://consignado-loan-service
          predicates:
            - Path=/api/v1/loans/**, /api/v1/contracts/**
            
        - id: payment-service
          uri: lb://consignado-payment-service
          predicates:
            - Path=/api/v1/payments/**, /api/v1/installments/**
```

```java
// gateway/config/SecurityConfig.java
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                // Endpoints públicos
                .pathMatchers("/actuator/health").permitAll()
                .pathMatchers("/api/v1/simulations").permitAll() // Simulação pública
                
                // Endpoints autenticados
                .pathMatchers("/api/v1/customers/**").hasAnyRole("USER", "CORBAN", "ADMIN")
                .pathMatchers("/api/v1/loans/**").hasAnyRole("USER", "CORBAN", "ADMIN")
                .pathMatchers("/api/v1/contracts/**").hasAnyRole("USER", "CORBAN", "ADMIN")
                
                // Endpoints administrativos
                .pathMatchers("/api/v1/admin/**").hasRole("ADMIN")
                
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
            )
            .build();
    }
}
```

#### Dia 3: Keycloak Setup

```yaml
# docker-compose.yml
services:
  keycloak:
    image: quay.io/keycloak/keycloak:23.0
    ports:
      - "8080:8080"
    environment:
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
    command: start-dev
```

```java
// Configuração do Resource Server
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/realms/consignado-hub
          jwk-set-uri: http://localhost:8080/realms/consignado-hub/protocol/openid-connect/certs
```

**Realm Configuration (Keycloak Admin):**

1. Criar Realm: `consignado-hub`
2. Criar Clients:
   - `consignado-api` (confidential)
   - `consignado-web` (public)
   - `consignado-corban` (confidential)
3. Criar Roles:
   - `ROLE_USER` (cliente final)
   - `ROLE_CORBAN` (correspondente bancário)
   - `ROLE_INTERNAL` (funcionário banco)
   - `ROLE_ADMIN` (administrador)

#### Dia 4: Multi-tenant por Canal

```java
// gateway/filter/ChannelHeaderFilter.java
@Component
public class ChannelHeaderFilter implements GlobalFilter, Ordered {
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
            .map(context -> {
                Jwt jwt = (Jwt) context.getAuthentication().getPrincipal();
                String clientId = jwt.getClaimAsString("azp"); // Client ID do Keycloak
                
                Channel channel = mapClientToChannel(clientId);
                
                ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("X-Channel", channel.name())
                    .build();
                
                return exchange.mutate().request(request).build();
            })
            .flatMap(chain::filter);
    }
    
    private Channel mapClientToChannel(String clientId) {
        return switch (clientId) {
            case "consignado-web" -> Channel.DIGITAL;
            case "consignado-corban" -> Channel.CORBAN;
            case "consignado-backoffice" -> Channel.INTERNAL;
            default -> Channel.DIGITAL;
        };
    }
    
    @Override
    public int getOrder() {
        return -1;
    }
}
```

#### Dia 5: Rate Limiting

```java
// gateway/config/RateLimiterConfig.java
@Configuration
public class RateLimiterConfig {
    
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> ReactiveSecurityContextHolder.getContext()
            .map(context -> context.getAuthentication().getName())
            .defaultIfEmpty("anonymous");
    }
    
    @Bean
    public RedisRateLimiter redisRateLimiter() {
        // 100 requests por segundo, burst de 200
        return new RedisRateLimiter(100, 200);
    }
}
```

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: customer-service
          uri: lb://consignado-customer-service
          predicates:
            - Path=/api/v1/customers/**
          filters:
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 100
                redis-rate-limiter.burstCapacity: 200
                key-resolver: "#{@userKeyResolver}"
```

### ✅ Critérios de Sucesso Sprint 6

- [ ] Gateway roteando para serviços
- [ ] Keycloak configurado com realm
- [ ] Autenticação OAuth2/JWT funcionando
- [ ] Roles e permissões por canal
- [ ] Rate limiting configurado

---

## 🎯 Sprint 7: DevOps + Observabilidade

### Objetivo
Configurar CI/CD, observabilidade e DevSecOps.

### 📖 Estude Primeiro

| Tópico | Arquivo | Seções |
|--------|---------|--------|
| Docker | `MODULO_08_DEVOPS.md` | 8.1 |
| CI/CD | `MODULO_08_DEVOPS.md` | 8.2 |
| Observabilidade | `MODULO_08_DEVOPS.md` | 8.3 |
| DevSecOps | `MODULO_08_DEVOPS.md` | 8.5 |

### 🛠️ Implemente

#### Dia 1: Dockerfile Otimizado

```dockerfile
# Dockerfile (multi-stage build)
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src/ src/
RUN ./mvnw package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -q --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Dia 2: Docker Compose Completo

```yaml
# docker-compose.yml
version: '3.8'

services:
  # Infraestrutura
  postgres:
    image: postgres:15-alpine
    environment:
      POSTGRES_USER: consignado
      POSTGRES_PASSWORD: consignado
      POSTGRES_DB: consignado_db
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U consignado"]
      interval: 10s
      timeout: 5s
      retries: 5

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
    ports:
      - "2181:2181"

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

  keycloak:
    image: quay.io/keycloak/keycloak:23.0
    ports:
      - "8080:8080"
    environment:
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
    command: start-dev

  # Observabilidade
  prometheus:
    image: prom/prometheus:v2.48.0
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml

  grafana:
    image: grafana/grafana:10.2.2
    ports:
      - "3000:3000"
    environment:
      GF_SECURITY_ADMIN_PASSWORD: admin

  jaeger:
    image: jaegertracing/all-in-one:1.52
    ports:
      - "16686:16686"
      - "4317:4317"

volumes:
  postgres_data:
```

#### Dia 3: GitHub Actions CI/CD

```yaml
# .github/workflows/ci.yml
name: CI/CD Pipeline

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
      - uses: actions/checkout@v4
      
      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
          cache: maven
      
      - name: Build with Maven
        run: mvn -B clean verify
      
      - name: Run Tests
        run: mvn test
        
      - name: Upload Coverage to Codecov
        uses: codecov/codecov-action@v3
        with:
          file: target/site/jacoco/jacoco.xml

  security:
    runs-on: ubuntu-latest
    needs: build
    
    steps:
      - uses: actions/checkout@v4
      
      - name: OWASP Dependency Check
        uses: dependency-check/Dependency-Check_Action@main
        with:
          project: 'consignado-hub'
          path: '.'
          format: 'HTML'
      
      - name: SonarCloud Scan
        uses: SonarSource/sonarcloud-github-action@master
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}

  docker:
    runs-on: ubuntu-latest
    needs: [build, security]
    if: github.ref == 'refs/heads/main'
    
    steps:
      - uses: actions/checkout@v4
      
      - name: Login to Docker Hub
        uses: docker/login-action@v3
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}
      
      - name: Build and Push Customer Service
        uses: docker/build-push-action@v5
        with:
          context: ./consignado-customer-service
          push: true
          tags: ${{ secrets.DOCKER_USERNAME }}/consignado-customer-service:${{ github.sha }}
```

#### Dia 4: Prometheus + Grafana

```yaml
# prometheus.yml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'consignado-services'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets:
        - 'customer-service:8081'
        - 'simulation-service:8082'
        - 'loan-service:8083'
        - 'payment-service:8084'
        - 'gateway:8080'
```

```java
// Métricas customizadas
@Component
@RequiredArgsConstructor
public class ConsignadoMetrics {
    
    private final MeterRegistry meterRegistry;
    
    private Counter proposalsCreated;
    private Counter proposalsApproved;
    private Counter proposalsRejected;
    private Timer averbationDuration;
    
    @PostConstruct
    public void init() {
        proposalsCreated = Counter.builder("consignado.proposals.created")
            .description("Total de propostas criadas")
            .register(meterRegistry);
        
        proposalsApproved = Counter.builder("consignado.proposals.approved")
            .description("Total de propostas aprovadas")
            .register(meterRegistry);
        
        proposalsRejected = Counter.builder("consignado.proposals.rejected")
            .description("Total de propostas rejeitadas")
            .register(meterRegistry);
        
        averbationDuration = Timer.builder("consignado.averbation.duration")
            .description("Tempo de averbação")
            .register(meterRegistry);
    }
    
    public void incrementProposalsCreated(Channel channel) {
        proposalsCreated.increment(Tags.of("channel", channel.name()));
    }
    
    public Timer.Sample startAverbationTimer() {
        return Timer.start(meterRegistry);
    }
}
```

#### Dia 5: Distributed Tracing com Jaeger

```java
// application.yml
management:
  tracing:
    sampling:
      probability: 1.0
  otlp:
    tracing:
      endpoint: http://jaeger:4317
```

```xml
<!-- pom.xml -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-otel</artifactId>
</dependency>
<dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-exporter-otlp</artifactId>
</dependency>
```

### ✅ Critérios de Sucesso Sprint 7

- [ ] Dockerfile otimizado multi-stage
- [ ] Docker Compose completo
- [ ] GitHub Actions CI/CD
- [ ] SonarCloud integrado
- [ ] OWASP Dependency Check
- [ ] Prometheus coletando métricas
- [ ] Grafana com dashboards
- [ ] Jaeger com tracing distribuído

---

## 🎯 Sprint 8: ☸️ Kubernetes

### Objetivo
Deploy do sistema em Kubernetes com Helm charts.

### 📖 Estude Primeiro

| Tópico | Arquivo | Seções |
|--------|---------|--------|
| K8s Conceitos | `MODULO_10_KUBERNETES.md` | 10.1, 10.2 |
| Deployments | `MODULO_10_KUBERNETES.md` | 10.3 |
| Services | `MODULO_10_KUBERNETES.md` | 10.4 |
| Helm | `MODULO_10_KUBERNETES.md` | 10.6 |

### 🛠️ Implemente

#### Dia 1-2: Manifests Kubernetes

```yaml
# k8s/customer-service/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: customer-service
  labels:
    app: customer-service
spec:
  replicas: 2
  selector:
    matchLabels:
      app: customer-service
  template:
    metadata:
      labels:
        app: customer-service
    spec:
      containers:
        - name: customer-service
          image: consignado-hub/customer-service:latest
          ports:
            - containerPort: 8081
          env:
            - name: SPRING_PROFILES_ACTIVE
              value: kubernetes
            - name: SPRING_DATASOURCE_URL
              valueFrom:
                secretKeyRef:
                  name: db-credentials
                  key: url
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
              port: 8081
            initialDelaySeconds: 30
            periodSeconds: 10
          livenessProbe:
            httpGet:
              path: /actuator/health/liveness
              port: 8081
            initialDelaySeconds: 60
            periodSeconds: 30
---
apiVersion: v1
kind: Service
metadata:
  name: customer-service
spec:
  selector:
    app: customer-service
  ports:
    - port: 8081
      targetPort: 8081
  type: ClusterIP
```

```yaml
# k8s/customer-service/hpa.yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: customer-service-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: customer-service
  minReplicas: 2
  maxReplicas: 10
  metrics:
    - type: Resource
      resource:
        name: cpu
        target:
          type: Utilization
          averageUtilization: 70
```

#### Dia 3: ConfigMaps e Secrets

```yaml
# k8s/configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: consignado-config
data:
  KAFKA_BOOTSTRAP_SERVERS: kafka:9092
  REDIS_HOST: redis
  REDIS_PORT: "6379"
  KEYCLOAK_URL: http://keycloak:8080

---
# k8s/secrets.yaml (em produção, usar External Secrets)
apiVersion: v1
kind: Secret
metadata:
  name: db-credentials
type: Opaque
stringData:
  url: jdbc:postgresql://postgres:5432/consignado_db
  username: consignado
  password: consignado
```

#### Dia 4: Helm Chart

```yaml
# helm/consignado-hub/Chart.yaml
apiVersion: v2
name: consignado-hub
description: Sistema de Crédito Consignado INSS
type: application
version: 1.0.0
appVersion: "1.0.0"

dependencies:
  - name: postgresql
    version: "14.0.0"
    repository: https://charts.bitnami.com/bitnami
  - name: redis
    version: "18.0.0"
    repository: https://charts.bitnami.com/bitnami
  - name: kafka
    version: "26.0.0"
    repository: https://charts.bitnami.com/bitnami
```

```yaml
# helm/consignado-hub/values.yaml
global:
  namespace: consignado

services:
  customer:
    replicas: 2
    image:
      repository: consignado-hub/customer-service
      tag: latest
    resources:
      requests:
        memory: 512Mi
        cpu: 250m
      limits:
        memory: 1Gi
        cpu: 500m
  
  simulation:
    replicas: 2
    image:
      repository: consignado-hub/simulation-service
      tag: latest
  
  loan:
    replicas: 3
    image:
      repository: consignado-hub/loan-service
      tag: latest
  
  payment:
    replicas: 2
    image:
      repository: consignado-hub/payment-service
      tag: latest
  
  gateway:
    replicas: 2
    image:
      repository: consignado-hub/gateway
      tag: latest

ingress:
  enabled: true
  className: nginx
  hosts:
    - host: api.consignado.local
      paths:
        - path: /
          pathType: Prefix

postgresql:
  auth:
    postgresPassword: consignado
    database: consignado_db
  primary:
    persistence:
      size: 10Gi

redis:
  auth:
    enabled: false
  master:
    persistence:
      size: 1Gi

kafka:
  replicaCount: 1
```

```yaml
# helm/consignado-hub/templates/deployment.yaml
{{- range $name, $service := .Values.services }}
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ $name }}-service
  namespace: {{ $.Values.global.namespace }}
spec:
  replicas: {{ $service.replicas }}
  selector:
    matchLabels:
      app: {{ $name }}-service
  template:
    metadata:
      labels:
        app: {{ $name }}-service
    spec:
      containers:
        - name: {{ $name }}-service
          image: "{{ $service.image.repository }}:{{ $service.image.tag }}"
          ports:
            - containerPort: 8080
          envFrom:
            - configMapRef:
                name: consignado-config
            - secretRef:
                name: db-credentials
          resources:
            {{- toYaml $service.resources | nindent 12 }}
{{- end }}
```

#### Dia 5: Deploy Local com Kind

```bash
# Criar cluster local
kind create cluster --name consignado-hub --config kind-config.yaml

# Instalar ingress controller
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml

# Instalar via Helm
helm install consignado ./helm/consignado-hub -n consignado --create-namespace

# Verificar pods
kubectl get pods -n consignado

# Port forward para teste
kubectl port-forward svc/gateway 8080:8080 -n consignado
```

### ✅ Critérios de Sucesso Sprint 8

- [ ] Manifests para todos os serviços
- [ ] Probes de health configurados
- [ ] HPA para auto-scaling
- [ ] Helm chart completo
- [ ] Deploy funcionando no Kind

---

## 🎯 Sprint 9: ☁️ AWS + LocalStack

### Objetivo
Simular ambiente AWS localmente e preparar para deploy real.

### 📖 Estude Primeiro

| Tópico | Arquivo | Seções |
|--------|---------|--------|
| AWS Conceitos | `MODULO_09_AWS_CLOUD.md` | 9.1 |
| RDS | `MODULO_09_AWS_CLOUD.md` | 9.3 |
| SQS/SNS | `MODULO_09_AWS_CLOUD.md` | 9.4 |
| S3 | `MODULO_09_AWS_CLOUD.md` | 9.5 |
| LocalStack | `MODULO_09_AWS_CLOUD.md` | 9.7 |

### 🛠️ Implemente

#### Dia 1: LocalStack Setup

```yaml
# docker-compose-localstack.yml
version: '3.8'

services:
  localstack:
    image: localstack/localstack:3.0
    ports:
      - "4566:4566"
      - "4510-4559:4510-4559"
    environment:
      SERVICES: s3,sqs,sns,secretsmanager,rds
      DEBUG: 1
      DOCKER_HOST: unix:///var/run/docker.sock
    volumes:
      - ./init-aws.sh:/etc/localstack/init/ready.d/init-aws.sh
      - localstack_data:/var/lib/localstack
      - /var/run/docker.sock:/var/run/docker.sock

volumes:
  localstack_data:
```

```bash
# init-aws.sh
#!/bin/bash

# Criar bucket S3 para documentos
awslocal s3 mb s3://consignado-documents

# Criar filas SQS
awslocal sqs create-queue --queue-name averbation-requests
awslocal sqs create-queue --queue-name averbation-responses
awslocal sqs create-queue --queue-name notifications

# Criar tópicos SNS
awslocal sns create-topic --name contract-events

# Criar secrets
awslocal secretsmanager create-secret \
  --name consignado/database \
  --secret-string '{"username":"consignado","password":"consignado"}'

echo "AWS resources created successfully!"
```

#### Dia 2: Integração com S3

```java
// adapter/out/s3/DocumentStorageAdapter.java
@Component
@RequiredArgsConstructor
public class DocumentStorageAdapter implements DocumentStorage {
    
    private final S3Client s3Client;
    
    @Value("${aws.s3.bucket}")
    private String bucket;
    
    @Override
    public String upload(String key, byte[] content, String contentType) {
        PutObjectRequest request = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(contentType)
            .build();
        
        s3Client.putObject(request, RequestBody.fromBytes(content));
        
        return String.format("s3://%s/%s", bucket, key);
    }
    
    @Override
    public byte[] download(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build();
        
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request);
        return response.readAllBytes();
    }
    
    @Override
    public String generatePresignedUrl(String key, Duration expiration) {
        S3Presigner presigner = S3Presigner.create();
        
        GetObjectRequest getRequest = GetObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build();
        
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(expiration)
            .getObjectRequest(getRequest)
            .build();
        
        return presigner.presignGetObject(presignRequest).url().toString();
    }
}
```

#### Dia 3: SQS como alternativa ao Kafka

```java
// adapter/out/sqs/SqsEventPublisher.java
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "messaging.provider", havingValue = "sqs")
public class SqsEventPublisher implements EventPublisher {
    
    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    
    @Value("${aws.sqs.queue-url.averbation}")
    private String averbationQueueUrl;
    
    @Override
    public void publish(DomainEvent event) {
        try {
            String messageBody = objectMapper.writeValueAsString(event);
            
            SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(getQueueUrl(event))
                .messageBody(messageBody)
                .messageGroupId(event.getAggregateId()) // FIFO
                .messageDeduplicationId(event.getEventId())
                .build();
            
            sqsClient.sendMessage(request);
            
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar evento", e);
        }
    }
    
    private String getQueueUrl(DomainEvent event) {
        if (event instanceof AverbationRequestedEvent) {
            return averbationQueueUrl;
        }
        // Outros eventos...
        return averbationQueueUrl;
    }
}
```

#### Dia 4: Secrets Manager

```java
// config/AwsSecretsConfig.java
@Configuration
@ConditionalOnProperty(name = "aws.secrets.enabled", havingValue = "true")
public class AwsSecretsConfig {
    
    @Bean
    public SecretsManagerClient secretsManagerClient() {
        return SecretsManagerClient.builder()
            .endpointOverride(URI.create("http://localhost:4566"))
            .region(Region.US_EAST_1)
            .build();
    }
    
    @Bean
    public DatabaseCredentials databaseCredentials(SecretsManagerClient client) {
        GetSecretValueRequest request = GetSecretValueRequest.builder()
            .secretId("consignado/database")
            .build();
        
        GetSecretValueResponse response = client.getSecretValue(request);
        String secretString = response.secretString();
        
        return new ObjectMapper().readValue(secretString, DatabaseCredentials.class);
    }
}

public record DatabaseCredentials(String username, String password) {}
```

#### Dia 5: Configuração para ambiente real AWS

```yaml
# application-aws.yml
cloud:
  aws:
    region:
      static: us-east-1
    credentials:
      instance-profile: true

aws:
  s3:
    bucket: ${S3_BUCKET:consignado-documents-prod}
  sqs:
    queue-url:
      averbation: ${SQS_AVERBATION_URL}
      notifications: ${SQS_NOTIFICATIONS_URL}
  secrets:
    enabled: true

spring:
  datasource:
    url: jdbc:postgresql://${RDS_ENDPOINT}:5432/consignado_db
```

### ✅ Critérios de Sucesso Sprint 9

- [ ] LocalStack configurado
- [ ] Upload/Download S3 funcionando
- [ ] SQS enviando/recebendo mensagens
- [ ] Secrets Manager integrado
- [ ] Configuração para ambiente real

---

## 🎯 Sprint 10: 🏗️ Terraform

### Objetivo
Infraestrutura como Código para deploy na AWS.

### 📖 Estude Primeiro

| Tópico | Arquivo | Seções |
|--------|---------|--------|
| IaC Conceitos | `MODULO_11_TERRAFORM.md` | 11.1 |
| HCL | `MODULO_11_TERRAFORM.md` | 11.2 |
| Modules | `MODULO_11_TERRAFORM.md` | 11.3 |
| State | `MODULO_11_TERRAFORM.md` | 11.4 |

### 🛠️ Implemente

#### Dia 1-2: Estrutura e VPC

```hcl
# terraform/main.tf
terraform {
  required_version = ">= 1.6.0"
  
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
  
  backend "s3" {
    bucket         = "consignado-terraform-state"
    key            = "prod/terraform.tfstate"
    region         = "us-east-1"
    encrypt        = true
    dynamodb_table = "terraform-locks"
  }
}

provider "aws" {
  region = var.aws_region
}

# VPC
module "vpc" {
  source = "./modules/vpc"
  
  environment     = var.environment
  vpc_cidr        = var.vpc_cidr
  azs             = var.availability_zones
  private_subnets = var.private_subnets
  public_subnets  = var.public_subnets
}
```

```hcl
# terraform/modules/vpc/main.tf
resource "aws_vpc" "main" {
  cidr_block           = var.vpc_cidr
  enable_dns_hostnames = true
  enable_dns_support   = true
  
  tags = {
    Name        = "${var.environment}-vpc"
    Environment = var.environment
  }
}

resource "aws_subnet" "private" {
  count             = length(var.private_subnets)
  vpc_id            = aws_vpc.main.id
  cidr_block        = var.private_subnets[count.index]
  availability_zone = var.azs[count.index % length(var.azs)]
  
  tags = {
    Name = "${var.environment}-private-${count.index + 1}"
    Type = "private"
  }
}

resource "aws_subnet" "public" {
  count                   = length(var.public_subnets)
  vpc_id                  = aws_vpc.main.id
  cidr_block              = var.public_subnets[count.index]
  availability_zone       = var.azs[count.index % length(var.azs)]
  map_public_ip_on_launch = true
  
  tags = {
    Name = "${var.environment}-public-${count.index + 1}"
    Type = "public"
  }
}
```

#### Dia 3: RDS PostgreSQL

```hcl
# terraform/modules/rds/main.tf
resource "aws_db_subnet_group" "main" {
  name       = "${var.environment}-db-subnet"
  subnet_ids = var.private_subnet_ids
}

resource "aws_security_group" "rds" {
  name        = "${var.environment}-rds-sg"
  description = "Security group for RDS"
  vpc_id      = var.vpc_id
  
  ingress {
    from_port       = 5432
    to_port         = 5432
    protocol        = "tcp"
    security_groups = [var.app_security_group_id]
  }
}

resource "aws_db_instance" "main" {
  identifier     = "${var.environment}-consignado-db"
  engine         = "postgres"
  engine_version = "15.4"
  instance_class = var.db_instance_class
  
  allocated_storage     = var.allocated_storage
  max_allocated_storage = var.max_allocated_storage
  storage_type          = "gp3"
  storage_encrypted     = true
  
  db_name  = "consignado_db"
  username = var.db_username
  password = random_password.db_password.result
  
  db_subnet_group_name   = aws_db_subnet_group.main.name
  vpc_security_group_ids = [aws_security_group.rds.id]
  
  multi_az               = var.environment == "prod"
  backup_retention_period = 7
  deletion_protection     = var.environment == "prod"
  
  tags = {
    Environment = var.environment
  }
}

resource "random_password" "db_password" {
  length  = 32
  special = true
}

resource "aws_secretsmanager_secret" "db_credentials" {
  name = "${var.environment}/consignado/database"
}

resource "aws_secretsmanager_secret_version" "db_credentials" {
  secret_id = aws_secretsmanager_secret.db_credentials.id
  secret_string = jsonencode({
    username = aws_db_instance.main.username
    password = random_password.db_password.result
    host     = aws_db_instance.main.address
    port     = aws_db_instance.main.port
    dbname   = aws_db_instance.main.db_name
  })
}
```

#### Dia 4: EKS Cluster

```hcl
# terraform/modules/eks/main.tf
module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "~> 19.0"
  
  cluster_name    = "${var.environment}-consignado-eks"
  cluster_version = "1.28"
  
  vpc_id     = var.vpc_id
  subnet_ids = var.private_subnet_ids
  
  cluster_endpoint_public_access = true
  
  eks_managed_node_groups = {
    general = {
      desired_size = 2
      min_size     = 2
      max_size     = 10
      
      instance_types = ["t3.medium"]
      capacity_type  = "ON_DEMAND"
      
      labels = {
        Environment = var.environment
        NodeGroup   = "general"
      }
    }
  }
  
  tags = {
    Environment = var.environment
  }
}

# IAM Role for Service Accounts (IRSA)
module "irsa_s3" {
  source  = "terraform-aws-modules/iam/aws//modules/iam-role-for-service-accounts-eks"
  version = "~> 5.0"
  
  role_name = "${var.environment}-consignado-s3-role"
  
  oidc_providers = {
    main = {
      provider_arn               = module.eks.oidc_provider_arn
      namespace_service_accounts = ["consignado:consignado-api"]
    }
  }
  
  role_policy_arns = {
    s3 = aws_iam_policy.s3_access.arn
  }
}
```

#### Dia 5: Outputs e Deploy

```hcl
# terraform/outputs.tf
output "vpc_id" {
  value = module.vpc.vpc_id
}

output "rds_endpoint" {
  value     = module.rds.endpoint
  sensitive = true
}

output "eks_cluster_name" {
  value = module.eks.cluster_name
}

output "eks_cluster_endpoint" {
  value = module.eks.cluster_endpoint
}
```

```bash
# Deploy
cd terraform

# Inicializar
terraform init

# Planejar
terraform plan -var-file=environments/prod.tfvars

# Aplicar
terraform apply -var-file=environments/prod.tfvars

# Configurar kubectl
aws eks update-kubeconfig --name prod-consignado-eks --region us-east-1
```

### ✅ Critérios de Sucesso Sprint 10

- [ ] VPC e subnets criadas
- [ ] RDS PostgreSQL provisionado
- [ ] Secrets Manager com credenciais
- [ ] EKS cluster funcionando
- [ ] IRSA configurado
- [ ] State remoto no S3

---

## 🎯 Sprint 11: 🚀 Integração Final

### Objetivo
Testes E2E, performance e documentação final.

### 🛠️ Implemente

#### Dia 1: Testes E2E

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class E2EConsignadoFlowTest {
    
    @Container
    static DockerComposeContainer<?> environment = 
        new DockerComposeContainer<>(new File("docker-compose-test.yml"))
            .withExposedService("postgres", 5432)
            .withExposedService("kafka", 9092)
            .withExposedService("redis", 6379);
    
    @Autowired
    TestRestTemplate restTemplate;
    
    @Test
    @DisplayName("Fluxo completo: Cadastro → Simulação → Proposta → Averbação → Contrato")
    void fluxoCompleto() {
        // 1. Cadastrar cliente
        CustomerResponse customer = cadastrarCliente();
        assertThat(customer.status()).isEqualTo("PENDING_BENEFIT");
        
        // 2. Vincular benefício
        vincularBeneficio(customer.id());
        
        // 3. Simular empréstimo
        SimulationResponse simulation = simular(customer.id());
        assertThat(simulation.installmentValue()).isLessThan(new BigDecimal("700")); // Margem
        
        // 4. Criar proposta
        ProposalResponse proposal = criarProposta(simulation.simulationId());
        
        // 5. Aguardar análise de crédito (async)
        await().atMost(30, TimeUnit.SECONDS)
            .until(() -> getProposalStatus(proposal.id()), is("APPROVED"));
        
        // 6. Aguardar averbação (async)
        await().atMost(60, TimeUnit.SECONDS)
            .until(() -> getContractStatus(proposal.id()), is("ACTIVE"));
        
        // 7. Verificar contrato ativo
        ContractResponse contract = getContract(proposal.id());
        assertThat(contract.averbationCode()).isNotNull();
        assertThat(contract.installments()).hasSize(simulation.termMonths());
    }
}
```

#### Dia 2: Load Testing com Gatling

```scala
// gatling/SimulationLoadTest.scala
class SimulationLoadTest extends Simulation {
  
  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")
  
  val simulationScenario = scenario("Simulação de Empréstimo")
    .exec(
      http("Simular")
        .post("/api/v1/simulations")
        .body(StringBody("""
          {
            "benefitNumber": "1234567890",
            "requestedAmount": 10000,
            "termMonths": 48
          }
        """))
        .check(status.is(201))
        .check(jsonPath("$.installmentValue").saveAs("installment"))
    )
    .pause(1)
  
  setUp(
    simulationScenario.inject(
      rampUsers(100).during(30.seconds),
      constantUsersPerSec(50).during(60.seconds)
    )
  ).protocols(httpProtocol)
   .assertions(
     global.responseTime.max.lt(3000),
     global.successfulRequests.percent.gt(95)
   )
}
```

#### Dia 3: Documentação API (OpenAPI)

```java
// config/OpenApiConfig.java
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI consignadoOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("ConsignadoHub API")
                .description("API do Sistema de Crédito Consignado INSS")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Time Consignado")
                    .email("consignado@finbank.com.br"))
            )
            .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"))
            .components(new Components()
                .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                )
            );
    }
}
```

#### Dia 4: README Final e Demo

```markdown
# ConsignadoHub - Demo

## Executar Localmente

```bash
# 1. Subir infraestrutura
docker-compose up -d

# 2. Aguardar serviços
./wait-for-it.sh

# 3. Criar cliente
curl -X POST http://localhost:8080/api/v1/customers \
  -H "Content-Type: application/json" \
  -d '{"fullName": "Maria Silva", "cpf": "40453683886", "birthDate": "1960-05-15", "channel": "DIGITAL"}'

# 4. Simular empréstimo
curl -X POST http://localhost:8080/api/v1/simulations \
  -H "Content-Type: application/json" \
  -d '{"benefitNumber": "1234567890", "requestedAmount": 10000, "termMonths": 48}'
```

## Acessar Interfaces

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **Grafana:** http://localhost:3000 (admin/admin)
- **Jaeger:** http://localhost:16686
- **Keycloak:** http://localhost:8080/admin
```

### ✅ Critérios de Sucesso Sprint 11

- [ ] Testes E2E passando
- [ ] Load test > 95% sucesso
- [ ] Documentação OpenAPI completa
- [ ] README com instruções
- [ ] Demo funcionando

---

## 🎓 Conclusão

### O que você aprendeu

- ✅ **Java 21** com Records, Sealed Classes, Pattern Matching
- ✅ **Spring Boot 3.5** com arquitetura hexagonal
- ✅ **DDD** com Value Objects, Entities, Aggregates
- ✅ **Event-Driven** com Kafka
- ✅ **Resiliência** com Resilience4j
- ✅ **OAuth2/OIDC** com Keycloak
- ✅ **DevOps** com Docker, GitHub Actions
- ✅ **Cloud** com AWS (LocalStack)
- ✅ **IaC** com Terraform
- ✅ **Kubernetes** com Helm

### Próximos passos

1. Fazer deploy real na AWS
2. Adicionar FGTS (Fase 2)
3. Implementar portabilidade completa
4. Machine Learning para score

---

> **Parabéns! 🎉** Você completou o ConsignadoHub!
