# 📋 Backlog de Melhorias Técnicas - ConsignadoHub

> Documento consolidando feedbacks de Code Review e melhorias identificadas.

---

## 🔴 Sprint 1.5: Polimento (Pós Sprint 1)

### 1️⃣ GlobalExceptionHandler

**Problema:** Erros de domínio retornam 500 com stack trace exposto.

**Solução:**
- Criar `@RestControllerAdvice` em `adapter/in/web/`
- Mapear `DomainException` → HTTP 422
- Mapear `ClienteNaoEncontradoException` → HTTP 404
- Retornar JSON limpo: `{ "code": "CPF_INVALIDO", "message": "..." }`

| Status | Prioridade | Esforço |
|--------|------------|---------|
| Pendente | Alta | 1h |

---

### 2️⃣ Logs e Observabilidade

**Problema:** Código "silencioso" - difícil debugar em produção.

**Solução:**
- Adicionar `@Slf4j` nos Services
- Logar início/fim de operações
- Mascarar dados sensíveis (CPF: `***.***.***-25`)
- Métricas básicas com Micrometer

| Status | Prioridade | Esforço |
|--------|------------|---------|
| Pendente | Alta | 30min |

---

### 3️⃣ Testes de Integração (@DataJpaTest)

**Problema:** Não testamos se JPA realmente persiste corretamente.

**Solução:**
- Criar `ClienteRepositoryIntegrationTest`
- Testar save/find/exists
- Usar Testcontainers ou H2

| Status | Prioridade | Esforço |
|--------|------------|---------|
| Pendente | Média | 1h |

---

## 🟡 Sprint 2+: Evoluções de Domínio

### 4️⃣ DadosBancarios (Conta Destino)

**Problema:** Não temos onde enviar o dinheiro do empréstimo.

**Solução - VO:**
```java
public record DadosBancarios(
    String banco,    // Código COMPE (341=Itaú, 001=BB)
    String agencia,
    String conta,
    String digito,
    TipoConta tipo   // CORRENTE, POUPANCA
) {}
```

**Adicionar ao Cliente:**
```java
private List<DadosBancarios> contasBancarias;
```

| Status | Sprint | Esforço |
|--------|--------|---------|
| Backlog | 2 ou 3 | 2h |

---

### 5️⃣ ChavePix (Alternativa moderna)

**Solução:**
```java
public record ChavePix(
    TipoChavePix tipo,  // CPF, EMAIL, TELEFONE, ALEATORIA
    String chave
) {}
```

| Status | Sprint | Esforço |
|--------|--------|---------|
| Backlog | 2 ou 3 | 1h |

---

### 6️⃣ TipoVinculo (Correntista vs Não-Correntista)

**Contexto:** Bancos têm sistemas legados (EQ3 no Itaú) para correntistas.

**Solução:**
```java
public enum TipoVinculo {
    CORRENTISTA,      // Dados vêm do Core Banking (Mock)
    NAO_CORRENTISTA   // CustomerService é o dono do dado
}
```

**Impacto:**
- Correntista: crédito direto na conta (Book Transfer)
- Não-Correntista: TED/PIX para banco externo

| Status | Sprint | Esforço |
|--------|--------|---------|
| Backlog | 5 (Payment) | 2h |

---

## 📊 Resumo

| Melhoria | Sprint | Prioridade |
|----------|--------|------------|
| GlobalExceptionHandler | 1.5 | 🔴 Alta |
| Logs Slf4j | 1.5 | 🔴 Alta |
| Testes @DataJpaTest | 1.5 | 🟡 Média |
| DadosBancarios VO | 2/3 | 🟡 Média |
| ChavePix VO | 2/3 | 🟡 Média |
| TipoVinculo Enum | 5 | 🟢 Baixa |

---

## 💡 Argumento de Entrevista

> "No meu projeto, tratei o CustomerService como sistema de Originação, 
> capturando dados de correntistas e não-correntistas. Para o desembolso, 
> implementei DadosBancarios VO. Se for On-Us (correntista), transferência 
> interna. Se Off-Us, orquestra TED ou PIX validando titularidade."
