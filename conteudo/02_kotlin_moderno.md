# Introdução ao Android Moderno e ao Kotlin

O desenvolvimento Android evoluiu significativamente nos últimos anos, adotando práticas modernas, arquitetura robusta e linguagens mais expressivas. O Kotlin tornou-se a linguagem oficial recomendada pelo Google para o desenvolvimento Android, trazendo mais segurança, concisão e produtividade.

## Android Moderno

O Android moderno incentiva o uso de componentes como **Jetpack**, arquitetura MVVM, navegação baseada em componentes, e integração facilitada com serviços em nuvem. Ferramentas como o Android Studio, o sistema de build Gradle e o uso de bibliotecas modernas tornam o desenvolvimento mais eficiente e seguro.

## Kotlin: Sintaxe e Possibilidades

Kotlin é uma linguagem concisa, segura e interoperável com Java. Veja alguns exemplos de sua sintaxe e recursos:

### Declaração de Variáveis

```kotlin
val nome: String = "Maria" // Imutável
var idade: Int = 25        // Mutável
```

### Funções

```kotlin
fun saudacao(nome: String): String {
    return "Olá, $nome!"
}
```

### Função de Expressão Única

```kotlin
fun dobro(x: Int) = x * 2
```

### Classes e Propriedades

```kotlin
class Pessoa(val nome: String, var idade: Int)

val pessoa = Pessoa("João", 30)
```

### Null Safety

```kotlin
var email: String? = null
email = "exemplo@email.com"
println(email?.length) // Safe call
```

### Coleções e Lambdas

```kotlin
val numeros = listOf(1, 2, 3, 4)
val pares = numeros.filter { it % 2 == 0 }
```

### Data Classes

```kotlin
data class Usuario(val id: Int, val nome: String)
```

### Objetos Singleton

```kotlin
object Configuracao {
    val versao = "1.0"
}
```

## Exercícios Práticos

1. **Crie uma função Kotlin que receba dois números inteiros e retorne o maior deles.**
2. **Implemente uma `data class` chamada `Produto` com propriedades `nome` (String) e `preco` (Double).**
3. **Dada uma lista de nomes, use uma expressão lambda para filtrar apenas os nomes que começam com a letra "A".**
