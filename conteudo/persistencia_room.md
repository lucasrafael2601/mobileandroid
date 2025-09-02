# Aula Completa: Persistência de Dados no Android com Room

## Introdução

O Room é uma biblioteca de persistência de dados do Android que fornece uma camada de abstração sobre o SQLite, facilitando o acesso e manipulação de dados locais de forma segura e eficiente.

---

## 1. Conceitos Básicos

### O que é o Room?

- **Room** é parte do Android Jetpack.
- Facilita o uso do SQLite com menos código boilerplate.
- Garante segurança de tipos em tempo de compilação.

### Componentes Principais

- **Entity:** Representa uma tabela no banco de dados.
- **DAO (Data Access Object):** Define métodos para acessar o banco.
- **Database:** Classe principal que conecta tudo.

---

## 2. Configuração Inicial

### Adicionando Dependências

No `build.gradle` (Module):

```toml
# No arquivo gradle/libs.versions.toml, adicione:
[versions]
room = "2.6.1"

[libraries]
room-runtime = { module = "androidx.room:room-runtime", version.ref = "room" }
room-compiler = { module = "androidx.room:room-compiler", version.ref = "room" }
```

```gradle
// No build.gradle (Module), use:
implementation(libs.room.runtime)
kapt(libs.room.compiler)
```

---

## 3. Exemplo Básico
### 3.1. Criando uma Entity

A camada **Entity** representa uma tabela no banco de dados. Cada campo da classe corresponde a uma coluna da tabela. É nela que você define os dados que serão armazenados.

**Sugestão de caminho:**  
Crie um arquivo chamado `User.java` na pasta `model` do seu projeto.

```java
// model/User.java
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    // Construtor de exemplo
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
```

> **Dica:** Sempre utilize nomes claros para os campos e anotações para personalizar nomes de colunas, se necessário.

---

### 3.2. Criando um DAO

O DAO (Data Access Object) define os métodos para acessar o banco de dados.

```java
```java
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Delete
    void delete(User user);

    @Update
    void update(User user);
}
```
```

---

### 3.3. Criando o Database

A classe Database conecta as entidades e os DAOs.

```java
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
```

---

### 3.4. Exemplo prático: Usando tudo junto

Veja como utilizar as classes criadas para inserir e consultar usuários no banco de dados:

```java
// Inicialize o banco de dados
AppDatabase db = Room.databaseBuilder(getApplicationContext(),
        AppDatabase.class, "database-name").build();

// Obtenha o DAO
UserDao userDao = db.userDao();

// Insira um novo usuário
User user = new User("Maria", "Silva");
userDao.insert(user);

// Consulte todos os usuários
List<User> users = userDao.getAll();
```

> **Nota:** Operações de banco devem ser feitas em background (Thread/AsyncTask/Coroutines).


> **Nota:** Operações de banco devem ser feitas em background (Thread/AsyncTask/Coroutines).

---

## 4. Recursos Intermediários

### 4.1. Consultas Personalizadas

```java
@Query("SELECT * FROM user WHERE first_name LIKE :name LIMIT 1")
User findByName(String name);
```

### 4.2. Atualização de Dados

```java
@Update
void update(User user);
```

### 4.3. Observando Dados com LiveData

```java
@Query("SELECT * FROM user")
LiveData<List<User>> getAllLive();
```

---

## 5. Recursos Avançados

### 5.1. Relacionamentos (One-to-Many)

```java
@Entity
public class Book {
    @PrimaryKey
    public int bookId;
    public int userOwnerId;
}

public class UserWithBooks {
    @Embedded public User user;
    @Relation(
        parentColumn = "uid",
        entityColumn = "userOwnerId"
    )
    public List<Book> books;
}

@Dao
public interface UserDao {
    @Transaction
    @Query("SELECT * FROM user")
    List<UserWithBooks> getUsersWithBooks();
}
```

### 5.2. Migrations

```java
static final Migration MIGRATION_1_2 = new Migration(1, 2) {
    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE user ADD COLUMN age INTEGER");
    }
};
```

### 5.3. Tipos Personalizados (TypeConverters)

```java
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

@Database(entities = {User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase { ... }
```

---

## 6. Boas Práticas

- Sempre use operações de banco em background.
- Prefira LiveData/Flow para observar dados.
- Use migrations para atualizar o schema sem perder dados.
- Separe DAOs por entidade.

---

## 7. Referências

- [Documentação Oficial do Room](https://developer.android.com/training/data-storage/room)
- [Guia do Android Jetpack](https://developer.android.com/jetpack)

---

## 8. Exercício Prático

1. Crie um app simples de cadastro de usuários.
2. Implemente inserção, listagem, atualização e remoção.
3. Adicione uma tabela relacionada (ex: livros do usuário).
4. Implemente observação com LiveData.

---

## Conclusão

O Room simplifica a persistência de dados no Android, tornando o código mais seguro, eficiente e fácil de manter. Pratique os exemplos e explore os recursos avançados para dominar a biblioteca!
