# Introdução ao Android

O Android é um sistema operacional móvel desenvolvido pelo Google, amplamente utilizado em smartphones e tablets. Ele oferece uma plataforma aberta para desenvolvedores criarem aplicativos inovadores, utilizando a linguagem de programação Kotlin ou Java.

## Desenvolvimento no Android Studio

O Android Studio é o ambiente de desenvolvimento oficial para criar aplicativos Android. Ele fornece ferramentas integradas para design de interface, depuração, testes e publicação de apps.

### Principais recursos do Android Studio:
- Editor de código inteligente
- Emulador de dispositivos Android
- Gerenciamento de dependências
- Ferramentas de análise de desempenho

## Prática: Criando um Hello World

Vamos criar um aplicativo simples que exibe "Hello World" na tela.

### Passos:

1. **Abra o Android Studio** e crie um novo projeto.
2. Escolha a opção **Empty Activity**.
3. No arquivo `activity_main.xml`, substitua o conteúdo por:

    ```xml
    <TextView
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         android:text="Hello World!"
         android:layout_gravity="center"/>
    ```

4. Execute o aplicativo no emulador ou em um dispositivo físico.

Pronto! Você criou seu primeiro app Android com uma mensagem de "Hello World".