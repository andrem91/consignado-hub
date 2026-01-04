# 📖 Módulo 1 - Parte 1: Fundamentos Java Básicos

> Dominar os fundamentos é essencial antes de avançar. Este módulo cobre tipos, strings, controle de fluxo, classes e exceções.

---

## 📚 1.1 Tipos Primitivos

### Tipos Numéricos

```java
// Inteiros
byte b = 127;           // 8 bits (-128 a 127)
short s = 32767;        // 16 bits
int i = 2_147_483_647;  // 32 bits - MAIS USADO
long l = 9_223_372_036_854_775_807L; // 64 bits - precisa do L

// Decimais
float f = 3.14f;        // 32 bits - precisa do f
double d = 3.14159265;  // 64 bits - MAIS USADO

// Dica: Use underscores para legibilidade
int milhao = 1_000_000;
```

### Outros Primitivos

```java
// Booleano
boolean ativo = true;   // true ou false

// Caractere
char letra = 'A';       // 16 bits Unicode
char unicode = '\u0041'; // 'A' em Unicode
```

### Valores Padrão (campos de classe)

| Tipo | Valor Padrão |
|------|-------------|
| byte, short, int, long | 0 |
| float, double | 0.0 |
| boolean | false |
| char | '\u0000' |
| Object/referências | null |

---

## 📚 1.2 Wrappers e Autoboxing

### Classes Wrapper

```java
// Primitivo → Wrapper (Boxing)
int primitivo = 42;
Integer wrapper = Integer.valueOf(primitivo);

// Wrapper → Primitivo (Unboxing)
int novoPrimitivo = wrapper.intValue();

// Autoboxing (automático desde Java 5)
Integer auto = 42;      // Boxing automático
int unbox = auto;       // Unboxing automático
```

### Tabela de Wrappers

| Primitivo | Wrapper |
|-----------|---------|
| byte | Byte |
| short | Short |
| int | **Integer** |
| long | Long |
| float | Float |
| double | Double |
| boolean | Boolean |
| char | Character |

### ⚠️ Cuidado com null!

```java
Integer valor = null;
int primitivo = valor;  // NullPointerException!

// ✅ Sempre verifique
if (valor != null) {
    int primitivo = valor;
}
```

### Integer Cache (-128 a 127)

```java
Integer a = 127;
Integer b = 127;
System.out.println(a == b);  // true (mesmo objeto do cache)

Integer c = 128;
Integer d = 128;
System.out.println(c == d);  // false (objetos diferentes!)
System.out.println(c.equals(d)); // true ✅ USE SEMPRE equals()
```

---

## 📚 1.3 BigDecimal (Valores Monetários)

### Por que usar BigDecimal?

```java
// ❌ PROBLEMA com double
double a = 0.1;
double b = 0.2;
System.out.println(a + b);  // 0.30000000000000004 !!!

// ✅ SOLUÇÃO: BigDecimal
BigDecimal x = new BigDecimal("0.1");
BigDecimal y = new BigDecimal("0.2");
System.out.println(x.add(y)); // 0.3 ✅
```

### Operações

```java
BigDecimal valor = new BigDecimal("1000.50");

// Operações retornam NOVO objeto (imutável!)
BigDecimal soma = valor.add(new BigDecimal("100"));
BigDecimal subtracao = valor.subtract(new BigDecimal("50"));
BigDecimal multiplicacao = valor.multiply(new BigDecimal("2"));
BigDecimal divisao = valor.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);

// Comparação - NÃO use equals()!
BigDecimal a = new BigDecimal("1.0");
BigDecimal b = new BigDecimal("1.00");
System.out.println(a.equals(b));     // false (escala diferente!)
System.out.println(a.compareTo(b) == 0); // true ✅ USE compareTo
```

### Arredondamento

```java
BigDecimal valor = new BigDecimal("10.5678");

// Arredondar para 2 casas
valor.setScale(2, RoundingMode.HALF_UP);    // 10.57
valor.setScale(2, RoundingMode.DOWN);       // 10.56
valor.setScale(2, RoundingMode.CEILING);    // 10.57
valor.setScale(2, RoundingMode.FLOOR);      // 10.56
```

---

## 📚 1.4 Strings

### Criação e Imutabilidade

```java
// Strings são IMUTÁVEIS
String s1 = "Hello";           // String pool
String s2 = new String("Hello"); // Novo objeto no heap

// Comparação
s1 == s2;           // false (referências diferentes)
s1.equals(s2);      // true ✅ USE SEMPRE equals()

// String Pool
String a = "Java";
String b = "Java";
a == b;  // true (mesmo objeto no pool)
```

### Métodos Comuns

```java
String texto = "  Hello World  ";

// Transformação (retorna NOVA string)
texto.toLowerCase();        // "  hello world  "
texto.toUpperCase();        // "  HELLO WORLD  "
texto.trim();               // "Hello World"
texto.strip();              // "Hello World" (Java 11+ remove Unicode whitespace)
texto.replace("l", "L");    // "  HeLLo WorLd  "

// Verificação
texto.isEmpty();            // false
texto.isBlank();            // false (Java 11+)
texto.contains("World");    // true
texto.startsWith("  H");    // true
texto.endsWith("  ");       // true

// Extração
texto.length();             // 15
texto.charAt(2);            // 'H'
texto.substring(2, 7);      // "Hello"
texto.indexOf("o");         // 4
texto.lastIndexOf("o");     // 9

// Divisão
String csv = "a,b,c";
String[] parts = csv.split(","); // ["a", "b", "c"]
```

### StringBuilder (Mutável)

```java
// ❌ Ineficiente para concatenação em loop
String result = "";
for (int i = 0; i < 1000; i++) {
    result += i;  // Cria novo objeto a cada iteração!
}

// ✅ Use StringBuilder
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);
}
String result = sb.toString();
```

### Text Blocks (Java 15+)

```java
// ❌ Antes
String json = "{\n" +
              "  \"nome\": \"João\",\n" +
              "  \"idade\": 30\n" +
              "}";

// ✅ Text Block
String json = """
    {
      "nome": "João",
      "idade": 30
    }
    """;
```

---

## 📚 1.5 Arrays

### Declaração e Inicialização

```java
// Declaração
int[] numeros;
String[] nomes;

// Inicialização com tamanho
numeros = new int[5];  // [0, 0, 0, 0, 0]

// Inicialização com valores
int[] valores = {1, 2, 3, 4, 5};
String[] frutas = {"Maçã", "Banana", "Laranja"};

// Acesso
int primeiro = valores[0];  // 1
valores[2] = 10;            // Modifica

// Tamanho
int tamanho = valores.length;  // 5 (propriedade, não método!)
```

### Arrays Multidimensionais

```java
// Matriz 3x3
int[][] matriz = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

// Acesso
int valor = matriz[1][2];  // 6 (linha 1, coluna 2)

// Iteração
for (int[] linha : matriz) {
    for (int num : linha) {
        System.out.print(num + " ");
    }
    System.out.println();
}
```

### Arrays Utility Class

```java
import java.util.Arrays;

int[] arr = {3, 1, 4, 1, 5};

Arrays.sort(arr);              // Ordena: [1, 1, 3, 4, 5]
Arrays.fill(arr, 0);           // Preenche: [0, 0, 0, 0, 0]
int[] copia = Arrays.copyOf(arr, 10);  // Copia com novo tamanho
boolean iguais = Arrays.equals(arr1, arr2);
String texto = Arrays.toString(arr);   // "[0, 0, 0, 0, 0]"
int indice = Arrays.binarySearch(arr, 4); // Requer array ordenado
```

---

## 📚 1.6 Estruturas de Controle

### if-else

```java
int idade = 18;

if (idade >= 18) {
    System.out.println("Maior de idade");
} else if (idade >= 16) {
    System.out.println("Pode votar");
} else {
    System.out.println("Menor de idade");
}

// Operador ternário
String status = idade >= 18 ? "Adulto" : "Menor";
```

### switch (Clássico)

```java
int dia = 1;
String nomeDia;

switch (dia) {
    case 1:
        nomeDia = "Domingo";
        break;
    case 2:
        nomeDia = "Segunda";
        break;
    default:
        nomeDia = "Inválido";
}
```

### switch Expression (Java 14+)

```java
// ✅ Moderno - retorna valor, sem break
String nomeDia = switch (dia) {
    case 1 -> "Domingo";
    case 2 -> "Segunda";
    case 3, 4, 5 -> "Meio da semana";
    default -> "Inválido";
};

// Com bloco
String resultado = switch (opcao) {
    case 1 -> {
        log("Opção 1 selecionada");
        yield "Primeiro";  // yield para retornar valor
    }
    case 2 -> "Segundo";
    default -> "Outro";
};
```

### Loops

```java
// for tradicional
for (int i = 0; i < 10; i++) {
    System.out.println(i);
}

// for-each (enhanced for)
String[] nomes = {"Ana", "Bruno", "Carlos"};
for (String nome : nomes) {
    System.out.println(nome);
}

// while
int contador = 0;
while (contador < 5) {
    System.out.println(contador++);
}

// do-while (executa pelo menos uma vez)
do {
    System.out.println("Executou!");
} while (false);

// break e continue
for (int i = 0; i < 10; i++) {
    if (i == 3) continue;  // Pula para próxima iteração
    if (i == 7) break;     // Sai do loop
    System.out.println(i); // 0, 1, 2, 4, 5, 6
}
```

---

## 📚 1.7 Classes e Objetos

### Estrutura de uma Classe

```java
public class Pessoa {
    // Atributos (estado)
    private String nome;
    private int idade;
    private static int contador = 0; // Pertence à classe
    
    // Constante
    public static final int IDADE_MINIMA = 0;
    
    // Construtor padrão
    public Pessoa() {
        contador++;
    }
    
    // Construtor com parâmetros
    public Pessoa(String nome, int idade) {
        this(); // Chama construtor padrão
        this.nome = nome;
        this.idade = idade;
    }
    
    // Métodos (comportamento)
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    // Método estático
    public static int getContador() {
        return contador;
    }
    
    @Override
    public String toString() {
        return "Pessoa{nome='" + nome + "', idade=" + idade + "}";
    }
}
```

### Instanciação

```java
// Criar objeto
Pessoa p1 = new Pessoa();
Pessoa p2 = new Pessoa("João", 30);

// Acessar membros
p1.setNome("Maria");
String nome = p2.getNome();

// Acessar membros estáticos
int total = Pessoa.getContador();  // Via classe (preferível)
int total = p1.getContador();      // Via objeto (funciona, mas evite)
```

### Modificadores de Acesso

| Modificador | Classe | Pacote | Subclasse | Mundo |
|-------------|--------|--------|-----------|-------|
| public | ✅ | ✅ | ✅ | ✅ |
| protected | ✅ | ✅ | ✅ | ❌ |
| (default) | ✅ | ✅ | ❌ | ❌ |
| private | ✅ | ❌ | ❌ | ❌ |

---

## 📚 1.8 Exceções

### Hierarquia

```
Throwable
├── Error (NÃO trate - problemas graves da JVM)
│   ├── OutOfMemoryError
│   └── StackOverflowError
│
└── Exception
    ├── RuntimeException (Unchecked - NÃO precisa declarar)
    │   ├── NullPointerException
    │   ├── IllegalArgumentException
    │   ├── IndexOutOfBoundsException
    │   └── ArithmeticException
    │
    └── Checked Exceptions (PRECISA declarar ou tratar)
        ├── IOException
        ├── SQLException
        └── FileNotFoundException
```

### try-catch-finally

```java
try {
    // Código que pode lançar exceção
    int resultado = 10 / 0;
} catch (ArithmeticException e) {
    // Trata a exceção
    System.out.println("Erro: " + e.getMessage());
} catch (Exception e) {
    // Captura outras exceções (mais genérico por último)
    e.printStackTrace();
} finally {
    // SEMPRE executa (mesmo com return no try/catch)
    System.out.println("Finalizando");
}
```

### try-with-resources (Java 7+)

```java
// ✅ Fecha automaticamente recursos que implementam AutoCloseable
try (
    FileReader reader = new FileReader("arquivo.txt");
    BufferedReader br = new BufferedReader(reader)
) {
    String linha = br.readLine();
} catch (IOException e) {
    e.printStackTrace();
}
// reader e br são fechados automaticamente aqui
```

### Lançando Exceções

```java
public void validarIdade(int idade) {
    if (idade < 0) {
        throw new IllegalArgumentException("Idade não pode ser negativa");
    }
}

// Checked exception - precisa declarar
public void lerArquivo() throws IOException {
    throw new IOException("Arquivo não encontrado");
}
```

### Exceção Customizada

```java
public class SaldoInsuficienteException extends RuntimeException {
    private final BigDecimal saldoAtual;
    private final BigDecimal valorSolicitado;
    
    public SaldoInsuficienteException(BigDecimal saldo, BigDecimal valor) {
        super("Saldo insuficiente: disponível " + saldo + ", solicitado " + valor);
        this.saldoAtual = saldo;
        this.valorSolicitado = valor;
    }
    
    public BigDecimal getSaldoAtual() { return saldoAtual; }
    public BigDecimal getValorSolicitado() { return valorSolicitado; }
}
```

---

## 📚 1.9 Date/Time API (Java 8+)

### Classes Principais

```java
import java.time.*;

// Data (sem hora)
LocalDate hoje = LocalDate.now();
LocalDate nascimento = LocalDate.of(1990, 5, 15);

// Hora (sem data)
LocalTime agora = LocalTime.now();
LocalTime horario = LocalTime.of(14, 30, 0);

// Data e Hora
LocalDateTime dataHora = LocalDateTime.now();
LocalDateTime especifico = LocalDateTime.of(2024, 1, 15, 10, 30);

// Com fuso horário
ZonedDateTime comFuso = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

// Apenas fuso
ZoneId saopaulo = ZoneId.of("America/Sao_Paulo");
ZoneOffset offset = ZoneOffset.of("-03:00");
```

### Operações

```java
LocalDate hoje = LocalDate.now();

// Adicionar/Subtrair (imutável - retorna novo objeto)
LocalDate amanha = hoje.plusDays(1);
LocalDate mesPassado = hoje.minusMonths(1);
LocalDate anoQueVem = hoje.plusYears(1);

// Comparação
boolean antes = hoje.isBefore(amanha);   // true
boolean depois = hoje.isAfter(amanha);   // false
boolean igual = hoje.isEqual(hoje);      // true

// Diferença
Period periodo = Period.between(nascimento, hoje);
int anos = periodo.getYears();
long dias = ChronoUnit.DAYS.between(nascimento, hoje);
```

### Formatação

```java
import java.time.format.DateTimeFormatter;

LocalDateTime agora = LocalDateTime.now();

// Formatadores pré-definidos
String iso = agora.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

// Formatador customizado
DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
String formatado = agora.format(formatoBR);  // "15/01/2024 10:30:00"

// Parse (String → Data)
LocalDate data = LocalDate.parse("15/01/2024", 
    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
```

---

## 📝 Exercícios Práticos

1. **Calculadora de Idade:** Receba data de nascimento e retorne idade exata
2. **Validador de CPF:** Implemente algoritmo de validação
3. **Formatador de Moeda:** Converta BigDecimal para "R$ 1.234,56"
4. **Parser de CSV:** Leia linha e retorne array de valores

---

## 🎯 Perguntas de Entrevista

1. Qual a diferença entre `==` e `equals()`?
2. Por que usar BigDecimal para dinheiro?
3. String é imutável. Por quê?
4. Diferença entre checked e unchecked exception?
5. O que é autoboxing?
6. Como funciona o String Pool?
7. Quando usar StringBuilder?
8. try-with-resources vs finally?

---

> **Próximo:** [Módulo 1 - Parte 2: Fundamentos Avançados](MODULO_01_PARTE_2_FUNDAMENTOS_AVANCADOS.md)
