# Introdução ao Android Moderno e ao Kotlin

O desenvolvimento Android evoluiu significativamente nos últimos anos, adotando práticas modernas, arquitetura robusta e linguagens mais expressivas. O Kotlin tornou-se a linguagem oficial recomendada pelo Google para o desenvolvimento Android, trazendo mais segurança, concisão e produtividade.

## Android Moderno

O Android moderno incentiva o uso de componentes como **Jetpack**, arquitetura MVVM, navegação baseada em componentes e integração facilitada com serviços em nuvem. Ferramentas como o Android Studio, o sistema de build Gradle e o uso de bibliotecas modernas tornam o desenvolvimento mais eficiente e seguro.

### Principais Componentes do Android Moderno

- **Jetpack**: Conjunto de bibliotecas que facilitam o desenvolvimento, como LiveData, ViewModel, Room, Navigation, WorkManager, entre outros.
- **Arquitetura MVVM**: Separação clara entre UI (View), lógica de apresentação (ViewModel) e dados (Model).
- **Navigation Component**: Gerenciamento de navegação entre telas de forma segura e desacoplada.
- **Data Binding**: Ligação direta entre componentes de UI e dados, reduzindo código boilerplate.
- **Dependency Injection**: Uso de frameworks como Hilt ou Dagger para injeção de dependências.

## Kotlin: Sintaxe e Possibilidades

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

### Funções de Extensão

```kotlin
fun String.reverter(): String = this.reversed()

val texto = "Kotlin"
println(texto.reverter()) // "niltok"
```

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

### Null Safety

```kotlin
var email: String? = null
email = "exemplo@email.com"
println(email?.length) // Safe call

// Operador Elvis
val tamanho = email?.length ?: 0
```

### Coleções e Lambdas

```kotlin
val numeros = listOf(1, 2, 3, 4, 5)
val pares = numeros.filter { it % 2 == 0 }
val dobrados = numeros.map { it * 2 }
val soma = numeros.reduce { acc, i -> acc + i }
```

### Data Classes

```kotlin
data class Usuario(val id: Int, val nome: String)

val usuario1 = Usuario(1, "Ana")
val usuario2 = usuario1.copy(nome = "Carlos")
```

### Objetos Singleton

```kotlin
object Configuracao {
    val versao = "1.0"
    fun exibirVersao() = println("Versão: $versao")
}
Configuracao.exibirVersao()
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
        println(obj.length) // Smart cast para String
    }
}
```

### Desestruturação

```kotlin
val (id, nome) = usuario1
println("ID: $id, Nome: $nome")
```

## Exercícios Práticos

1. **Crie uma função Kotlin que receba dois números inteiros e retorne o maior deles.**
2. **Implemente uma `data class` chamada `Produto` com propriedades `nome` (String) e `preco` (Double).**
3. **Dada uma lista de nomes, use uma expressão lambda para filtrar apenas os nomes que começam com a letra "A".**
4. **Crie uma função de extensão para a classe String que retorna a string invertida.**
5. **Implemente uma sealed class chamada `ResultadoOperacao` com os estados `Sucesso` (com valor Int) e `Falha` (com mensagem String).**
