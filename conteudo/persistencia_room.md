# Aula Completa: Persistência de Dados no Android com Room (Versão Detalhada e Comentada)

## Introdução

O Room é uma camada de abstração sobre o SQLite que:
- Reduz código repetitivo (boilerplate).
- Fornece verificação de consultas em tempo de compilação.
- Integra-se nativamente com LiveData / Flow / Rx (observabilidade).
- Ajuda a manter um código organizado e seguro.

---

## 1. Conceitos Básicos

### O que é o Room?
- Parte do Android Jetpack.
- Gera implementações de DAOs automaticamente.
- Valida SQL em compile-time (evita muitos erros em runtime).
- Oferece suporte a migrations e conversores de tipos.

### Componentes Principais
- Entity: Classe que mapeia uma tabela.
- DAO (Data Access Object): Interface com métodos (consultas, inserts etc.).
- Database: Classe abstrata que expõe os DAOs e configura o Room.

Fluxo básico: Entity -> DAO -> Database -> Uso na camada de dados / ViewModel.

---

## 2. Configuração Inicial

### Adicionando Dependências (Versões Centralizadas)

Arquivo: gradle/libs.versions.toml
```toml
[versions]
room = "2.6.1"

[libraries]
room-runtime   = { module = "androidx.room:room-runtime", version.ref = "room" }
room-compiler  = { module = "androidx.room:room-compiler", version.ref = "room" }
room-ktx       = { module = "androidx.room:room-ktx", version.ref = "room" }
```

build.gradle (Module):
```gradle
// Biblioteca principal em tempo de execução
implementation(libs.room.runtime)

// Extensões (coroutines, etc.)
implementation(libs.room.ktx)

// Processador de anotações (Java/KAPT)
kapt(libs.room.compiler)
```

Observações:
- Se usar Kotlin + KSP: substituir kapt por ksp(libs.room.compiler).
- Certifique-se de ter o plugin kapt ou ksp configurado.

---

## 3. Exemplo Básico

### 3.1. Criando uma Entity

Objetivo: criar a tabela user com colunas id, first_name e last_name.

Arquivo: model/User.java
```java
// Importações necessárias das anotações do Room
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

// @Entity indica que esta classe representa uma tabela no banco.
// Se quiser nome customizado: @Entity(tableName = "user")
@Entity
public class User {

    // @PrimaryKey define a chave primária.
    // autoGenerate = true faz o Room gerar valores automaticamente (INTEGER PRIMARY KEY AUTOINCREMENT).
    @PrimaryKey(autoGenerate = true)
    public int uid;

    // @ColumnInfo permite definir o nome real da coluna.
    // Útil para manter Java naming diferente de SQL naming.
    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    // Construtor usado ao criar a instância antes de inserir.
    public User(String firstName, String lastName) {
        this.firstName = firstName; // Atribui valor ao campo firstName
        this.lastName  = lastName;  // Atribui valor ao campo lastName
    }

    // (Opcional) Getters/Setters podem ser adicionados para encapsulamento.
    // (Opcional) Override de toString() para debug.
}
```

Boas práticas:
- Manter campos simples (tipos primitivos e tipos suportados).
- Para tipos complexos, usar TypeConverter.

---

### 3.2. Criando um DAO

DAO expõe operações de banco (CRUD + consultas personalizadas).  
Arquivo: data/UserDao.java
```java
// Importações essenciais
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;
import androidx.room.OnConflictStrategy;

import java.util.List;

// @Dao marca a interface para geração de implementação pelo Room.
@Dao
public interface UserDao {

    // @Insert gera SQL de inserção.
    // onConflict = REPLACE substitui registro com mesma PK (cuidado com perda de dados).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    // @Query permite consultas SQL personalizadas.
    // "SELECT * FROM user" retorna todas as linhas da tabela user.
    @Query("SELECT * FROM user")
    List<User> getAll();

    // Remove o registro correspondente ao objeto passado (usa a PK).
    @Delete
    void delete(User user);

    // Atualiza os campos baseados na PK do objeto.
    @Update
    void update(User user);

    // Exemplo adicional: buscar por nome
    @Query("SELECT * FROM user WHERE first_name LIKE :name LIMIT 1")
    User findByFirstName(String name);
}
```

Notas:
- Para operações pesadas, usar threads (Executors) ou coroutines (em Kotlin).
- Em Kotlin, prefira marcar métodos com suspend.

---

### 3.3. Criando o Database

Conecta entidades e DAOs.  
Arquivo: data/AppDatabase.java
```java
// Imports essenciais
import androidx.room.Database;
import androidx.room.RoomDatabase;

// @Database lista entidades e controla versão.
// version é usada para migrations.
// exportSchema = true (padrão) gera esquema para versionamento (configure pasta schemas).
@Database(entities = {User.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    // Método abstrato que o Room implementa em tempo de compilação.
    public abstract UserDao userDao();
}
```

Boas práticas:
- Criar singleton do banco (evita múltiplas instâncias pesadas).
- Gerenciar migrações ao alterar a versão.

---

### 3.4. Inicializando e Usando (Exemplo Prático)

Exemplo mínimo (Java) em, por exemplo, uma Activity (NÃO fazer em main thread na prática):

```java
// Imports básicos
import androidx.room.Room;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // Referência para o banco
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main); // Layout opcional

        // Cria (ou abre) o banco.
        // databaseBuilder(context, classe, nome_do_arquivo.db)
        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "app_database.db"
        )
        // .fallbackToDestructiveMigration() // (Evite em produção, apaga dados)
        .build();

        // Executa operações fora da UI thread
        Executors.newSingleThreadExecutor().execute(() -> {
            // Obtém DAO
            UserDao userDao = db.userDao();

            // Cria usuário
            User user = new User("Maria", "Silva");

            // Insere
            userDao.insert(user);

            // Consulta tudo
            List<User> all = userDao.getAll();

            // Exemplo de log (substituir por uso real na UI via Handler / LiveData)
            for (User u : all) {
                System.out.println("User: " + u.firstName + " " + u.lastName);
            }
        });
    }
}
```

Observações:
- Nunca realizar I/O em main thread (causa ANR).
- Preferir ViewModel + LiveData / Flow para atualizar a UI.

---

## 4. Recursos Intermediários

### 4.1. Consultas Personalizadas
```java
// Busca um usuário pelo primeiro nome (LIKE permite padrões, ex: "Ma%").
@Query("SELECT * FROM user WHERE first_name LIKE :name LIMIT 1")
User findByName(String name);
```

### 4.2. Atualização de Dados
```java
// Atualiza todas as colunas persistidas, casando pela PK (uid).
@Update
void update(User user);
```

### 4.3. Observando Dados com LiveData
```java
// Retorna LiveData que emite a lista a cada mudança na tabela 'user'.
@Query("SELECT * FROM user")
LiveData<List<User>> getAllLive();
```

Benefícios:
- Atualização automática da UI.
- Integração com ciclo de vida (evita leaks).

---

## 5. Recursos Avançados

### 5.1. Relacionamentos (One-to-Many)

Arquivo: model/Book.java
```java
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Book {

    @PrimaryKey(autoGenerate = true)
    public int bookId;        // Identificador do livro

    public int userOwnerId;   // FK referenciando User.uid (não há constraint automática por padrão)

    public String title;      // Título do livro
}
```

Classe de relação:
```java
import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;

public class UserWithBooks {

    // @Embedded insere campos do User no objeto resultante.
    @Embedded
    public User user;

    // @Relation vincula user.uid -> Book.userOwnerId
    @Relation(
        parentColumn = "uid",
        entityColumn = "userOwnerId"
    )
    public List<Book> books;
}
```

DAO com transação:
```java
@Dao
public interface UserWithBooksDao {

    // @Transaction garante consistência ao montar o objeto composto.
    @Transaction
    @Query("SELECT * FROM user")
    List<UserWithBooks> getUsersWithBooks();
}
```

### 5.2. Migrations

Quando alterar schema (ex: adicionar coluna), incremente version.

```java
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.annotation.NonNull;

static final Migration MIGRATION_1_2 = new Migration(1, 2) {
    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        // Adiciona coluna age (INTEGER) à tabela user sem apagar dados existentes
        database.execSQL("ALTER TABLE user ADD COLUMN age INTEGER");
    }
};
```

Uso na criação:
```java
Room.databaseBuilder(context, AppDatabase.class, "app_database.db")
    .addMigrations(MIGRATION_1_2)
    .build();
```

### 5.3. TypeConverters (Tipos Personalizados)

Arquivo: util/Converters.java
```java
import androidx.room.TypeConverter;
import java.util.Date;

public class Converters {

    // Converte de Long (no banco) para Date (na app)
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    // Converte de Date (objeto) para Long (epoch millis) ao salvar
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
```

Aplicação no Database:
```java
import androidx.room.TypeConverters;

@Database(entities = {User.class, Book.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract UserWithBooksDao userWithBooksDao();
}
```

---

## 6. Boas Práticas

- Sempre usar background thread (Executors, coroutines).
- Preferir LiveData / Flow para reatividade.
- Planejar migrations antes de publicar versão em produção.
- Separar pacotes: model/, data/, util/, db/.
- Evitar SELECT * em listas muito grandes (paginação: Paging 3).
- Usar índices (@Index) em colunas muito consultadas.
- Validar dados antes de inserir (camada de domínio).

Exemplo de índice:
```java
@Entity(indices = {@Index(value = {"first_name"})})
public class User { ... }
```

---

## 7. Referências

- Documentação Oficial Room: https://developer.android.com/training/data-storage/room
- Guia Jetpack: https://developer.android.com/jetpack
- Migrations: https://developer.android.com/training/data-storage/room/migrating-db
- TypeConverters: https://developer.android.com/training/data-storage/room/referencing-data

---

## 8. Exercício Prático

1. Criar app de cadastro de usuários (nome / sobrenome / idade opcional).
2. Implementar CRUD completo (insert / list / update / delete).
3. Adicionar livros relacionados ao usuário (One-to-Many).
4. Exibir lista reativa com LiveData.
5. Adicionar campo Date de criação usando TypeConverter.
6. Criar MIGRATION para adicionar nova coluna (ex: email).
7. Implementar busca por prefixo do first_name.

Desafio extra:
- Implementar paginação (Paging 3).
- Medir performance (Trace / Systrace).
- Adicionar testes instrumentados de DAO.

---

## Conclusão

O Room fornece uma interface robusta, segura e escalável para persistência local. Com Entities bem definidas, DAOs claros e boas práticas (migrations, reatividade e separação de camadas), sua aplicação torna-se mais confiável e fácil de manter. Pratique incrementando gradualmente: básico -> consultas -> relações -> migrations -> otimização.

Fim.
