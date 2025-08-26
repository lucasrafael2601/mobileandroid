# Kotlin Moderno para Android

O desenvolvimento Android evoluiu significativamente nos últimos anos, adotando práticas modernas, arquitetura robusta e linguagens mais expressivas. O Kotlin tornou-se a linguagem oficial recomendada pelo Google para o desenvolvimento Android, trazendo mais segurança, concisão e produtividade.

---

## 1. Android Moderno

O Android moderno incentiva o uso de componentes como **Jetpack**, arquitetura MVVM, navegação baseada em componentes e integração facilitada com serviços em nuvem. Ferramentas como o Android Studio, o sistema de build Gradle e o uso de bibliotecas modernas tornam o desenvolvimento mais eficiente e seguro.

### Principais Componentes

- **Jetpack**: Conjunto de bibliotecas que facilitam o desenvolvimento, como LiveData, ViewModel, Room, Navigation, WorkManager, entre outros.
- **Arquitetura MVVM**: Separação clara entre UI (View), lógica de apresentação (ViewModel) e dados (Model).
- **Navigation Component**: Gerenciamento de navegação entre telas de forma segura e desacoplada.
- **Data Binding**: Ligação direta entre componentes de UI e dados, reduzindo código boilerplate.
- **Dependency Injection**: Uso de frameworks como Hilt ou Dagger para injeção de dependências.

---

## 2. Sintaxe Básica do Kotlin

Kotlin é uma linguagem concisa, segura e interoperável com Java. Veja exemplos de sua sintaxe e recursos:

### Declaração de Variáveis

```kotlin
val nome: String = "Maria" // Imutável
var idade: Int = 25        // Mutável

// Inferência de tipo
val ativo = true
var saldo = 100.50
```

### Funções

```kotlin
fun saudacao(nome: String): String {
    return "Olá, $nome!"
}

// Parâmetros com valor padrão
fun soma(a: Int, b: Int = 10): Int = a + b
```

### Função de Expressão Única

```kotlin
fun dobro(x: Int) = x * 2
```

---

## 3. Null Safety

No **Android com Kotlin**, o **Null Safety** é um dos maiores diferenciais em relação a Java, evitando o famoso **NullPointerException (NPE)**.

### O que é Null Safety?

É o sistema de tipos do Kotlin que obriga você a tratar valores que podem ser nulos de forma explícita, evitando muitos erros em tempo de execução.

### Como funciona?

#### Variáveis não-nulas (default)

```kotlin
var nome: String = "Maria"
// nome = null // ❌ Erro de compilação
```

#### Variáveis anuláveis (`?`)

```kotlin
var nome: String? = "Maria"
nome = null // ✅ permitido
```

#### Maneiras de acessar variáveis anuláveis

- **Safe call `?.`**:

    ```kotlin
    val tamanho = nome?.length
    ```

- **Elvis operator `?:`**:

    ```kotlin
    val tamanho = nome?.length ?: 0
    ```

- **Not-null assertion `!!`**:

    ```kotlin
    val tamanho = nome!!.length // ⚠️ perigo: pode crashar
    ```

- **Checagem explícita**:

    ```kotlin
    if (nome != null) {
        println("Nome: ${nome.length}")
    } else {
        println("Nome vazio")
    }
    ```

#### Exemplos práticos no Android

- **findViewById**:

    ```kotlin
    val textView: TextView? = findViewById(R.id.meuTexto)
    textView?.text = "Olá"
    ```

- **Intents e Extras**:

    ```kotlin
    val nome = intent.getStringExtra("NOME")
    val mensagem = nome ?: "Usuário desconhecido"
    ```

- **Banco de dados / APIs**: O compilador já força a tratar `null`.

---

## 4. Classes, Data Classes e Objetos

### Classes e Propriedades

```kotlin
class Pessoa(val nome: String, var idade: Int) {
    fun aniversario() {
        idade++
    }
}

val pessoa = Pessoa("João", 30)
pessoa.aniversario()
```

### Data Classes

Uma **data class** é uma classe usada principalmente para armazenar dados. O compilador gera automaticamente métodos como `equals`, `hashCode`, `toString`, `copy` e desestruturação.

```kotlin
data class Usuario(val id: Int, val nome: String)

val usuario1 = Usuario(1, "Ana")
val usuario2 = usuario1.copy(nome = "Carlos")
```

#### Exemplo completo

```kotlin
data class Pessoa(val nome: String, var idade: Int)

fun main() {
    val p1 = Pessoa("João", 30)
    val p2 = Pessoa("João", 30)
    val p3 = Pessoa("Maria", 25)

    println(p1) // Pessoa(nome=João, idade=30)
    println(p1 == p2) // true
    println(p1 == p3) // false

    val p4 = p1.copy(idade = 31)
    println(p4) // Pessoa(nome=João, idade=31)

    val (nome, idade) = p3
    println("$nome tem $idade anos")
}
```

### Objetos Singleton

```kotlin
object Configuracao {
    val versao = "1.0"
    fun exibirVersao() = println("Versão: $versao")
}
Configuracao.exibirVersao()
```

---

## 5. Coleções e Lambdas

Kotlin oferece interfaces imutáveis e mutáveis:

- `List`, `Set`, `Map` — somente leitura.
- `MutableList`, `MutableSet`, `MutableMap` — permitem modificação.

### Exemplos

```kotlin
val listaImutavel = listOf(1, 2, 3)
val listaMutavel = mutableListOf(1, 2, 3)
val conjunto = setOf("a", "b", "a")
val mapa = mapOf("a" to 1, "b" to 2)
```

### Lambdas: sintaxe

```kotlin
val soma = { a: Int, b: Int -> a + b }
listOf(1,2,3).map { it * 2 }
```

### Operações comuns

```kotlin
data class Pessoa(val nome: String, val idade: Int)

val pessoas = listOf(
    Pessoa("Ana", 25),
    Pessoa("Carlos", 30),
    Pessoa("Bia", 22)
)

val nomes = pessoas.map { it.nome }
val adultos = pessoas.filter { it.idade >= 21 }
val somaIdades = pessoas.sumOf { it.idade }
```

### Sequences — avaliação *lazy*

```kotlin
val grandes = (1..1_000_000).asSequence()
    .map { it * 2 }
    .filter { it % 3 == 0 }
    .take(5)
    .toList()
```

### Scope Functions

- `let`, `also`, `apply`, `run`, `with`

```kotlin
val pessoa = Pessoa("João", 30).apply {
    println("Criando ${this.nome}")
}

val idade = pessoa.let {
    println("fazendo algo com ${it.nome}")
    it.idade
}
```

---

## 6. Outros Recursos Kotlin

### Funções de Extensão

```kotlin
fun String.reverter(): String = this.reversed()
val texto = "Kotlin"
println(texto.reverter()) // "niltok"
```

### Sealed Classes

```kotlin
sealed class Resultado
data class Sucesso(val dados: String): Resultado()
data class Erro(val mensagem: String): Resultado()

fun processar(resultado: Resultado) {
    when (resultado) {
        is Sucesso -> println("Dados: ${resultado.dados}")
        is Erro -> println("Erro: ${resultado.mensagem}")
    }
}
```

### Smart Casts

```kotlin
fun imprimirTamanho(obj: Any) {
    if (obj is String) {
        println(obj.length)
    }
}
```

### Desestruturação

```kotlin
val (id, nome) = usuario1
println("ID: $id, Nome: $nome")
```

---

## 7. Exercícios Práticos

1. Crie uma função Kotlin que receba três números inteiros e retorne o maior deles. Em seguida, utilize essa função para encontrar o maior valor em uma lista de inteiros.

2. Implemente uma `data class` chamada `Produto` com as propriedades `nome` (String), `preco` (Double) e `categoria` (String). Adicione um método na classe para aplicar um desconto percentual ao preço.

3. Dada uma lista de nomes, utilize uma expressão lambda para filtrar apenas os nomes que começam com a letra "A" (maiúscula ou minúscula) e que tenham mais de 3 caracteres. Em seguida, transforme todos os nomes filtrados para letras maiúsculas.

4. Crie uma função de extensão para a classe String que retorna a string invertida e, adicionalmente, substitui todas as vogais por asteriscos (`*`).

5. Implemente uma sealed class chamada `ResultadoOperacao` com os estados `Sucesso` (com valor Int), `Falha` (com mensagem String) e `Pendente`. Crie uma função que receba um `ResultadoOperacao` e imprima uma mensagem apropriada para cada caso, utilizando `when`.

---

## 8. Cheatsheet Rápido

```text
map { }         // transforma
filter { }      // filtra
flatMap { }     // transforma e achatamento
forEach { }     // itera (side-effects)
mapIndexed { i, v -> } // transforma com índice
groupBy { }     // agrupa em Map<chave, List<T>>
associateBy { } // cria Map com chave derivada do elemento
partition { }   // retorna Pair<List<T>,List<T>> (true/false)
fold(init){acc,v->}  // acumula, define init
reduce{acc,v->}      // acumula, sem init
sumOf { }       // soma propriedade
asSequence()    // lazy pipeline
windowed(n)     // janelas deslizantes
chunked(n)      // blocos fixos
```

---
