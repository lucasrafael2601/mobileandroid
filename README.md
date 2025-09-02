# 📱 Curso de Desenvolvimento Mobile Android (Ementa 5 Meses)

Ementa enxuta e focada no que o mercado mais exige hoje para Android nativo: **Kotlin**, **arquitetura limpa (MVVM)**, **UI clássica XML + ViewBinding**, **persistência (Room / DataStore)**, **networking (Retrofit + Coroutines)**, **injeção de dependências (Hilt)**, **testes**, **boas práticas de publicação**. Jetpack Compose é citado como caminho futuro, mas não é foco (remoção para manter curva de aprendizado realista em 5 meses).

---

## 🎯 Objetivo Geral
Formar o aluno para entregar um app Android profissional usando a stack estável mais difundida: Activities/Fragments + XML, MVVM + Repository, Room, Retrofit, Hilt, Coroutines/Flow, testes fundamentais e pipeline de release.

## 🗓️ Organização Temporal
- Duração: ~20 semanas (5 meses)
- Ritmo sugerido: 4h a 6h de estudo/prática guiada por semana + projeto incremental
- Entregáveis: mini‑projetos modulares evoluindo para um **App Final** (Lista + Detalhe + Cache Offline + Login Simples)

---

## 🧩 Módulos

### Módulo 1 - Fundamentos Kotlin + Fundamentos Android Clássico

- Kotlin essencial: tipos, null safety, data classes, coleções, funções de extensão, lambdas
- Estrutura de projeto Android (Gradle moderno, namespaces, build variants básicos)
- Ciclo de vida Activity / Fragment
- Layouts XML, ConstraintLayout, Material Components (botões, text fields, theming mínimo)
- ViewBinding vs findViewById (adotar ViewBinding)
- Recursos: strings, dimens, drawables, temas, estilos, cores (Material 3 em XML)

Entrega parcial: Tela estática multi-Fragment navegando com BottomNavigation ou Toolbar simples.

### Módulo 2 - UI Dinâmica + Listas + Navegação

- RecyclerView: Adapter, ViewHolder, ListAdapter + DiffUtil
- Padrões de estado de tela (Loading, Empty, Error, Content)
- Navigation Component (gráfico, safe args, back stack)
- Comunicação Fragment ↔ ViewModel (scoped ViewModel)
- Imagens: Coil (ou Glide) básico
- Acessibilidade inicial (contentDescription, touch targets)

Entrega parcial: Lista paginada simples (mock) + detalhe, busca local e estados de tela.

### Módulo 3 - Arquitetura e Dados Locais

- MVVM + Repository + separação de camadas
- Coroutines: launch, async, scopes, exception handling
- Flow vs LiveData (usar Flow como primário)
- Room: entities, DAO, migrations básicas
- DataStore (Preferences) para configurações leves
- Estratégias Offline First (cache + fonte remota) e mapeamento de modelos (DTO ↔ Entity ↔ UI Model)
- Injeção de dependências com Hilt (modules, scopes)

Entrega parcial: Persistir itens da lista (Room) + filtro + preferências de usuário salvas em DataStore.

### Módulo 4 - Networking e Integrações

- Retrofit + OkHttp (interceptors, logging, timeouts)
- Serialização: Moshi ou Gson (optar por Moshi)
- Tratamento de erros (HTTP, timeout, parsing) + Result wrapper
- Paginação manual básica (ou Paging 3 como extensão se houver tempo)
- Autenticação simples (token bearer ou Firebase Auth opcional)
- Upload / download de imagens (multipart) básico
- Estratégia de sincronização (merge remoto/local)

Entrega parcial: App utilizando API pública (ex: TMDB ou GitHub) com cache Room + fallback offline.

### Módulo 5 - Qualidade, Release e Projeto Final

- Medição de performance (Layout Inspector, Memory, Network) breve
- Segurança básica: Proguard/R8, evitar credenciais hardcoded, Network Security Config
- Empacotamento e assinatura (keystore), Play Console (simulado)
- Automação inicial: GitHub Actions build + lint + testes (pipeline simples)

Entrega final: App consolidado (lista + detalhe + login simples + cache offline + testes de unidade principais + pipeline CI build).

---

## 🛠️ Stack de Tecnologias
| Tecnologia | Uso Principal |
|------------|---------------|
| Kotlin | Linguagem |
| XML + ViewBinding | Camada de UI clássica |
| Material Components | Estilos e componentes visuais |
| RecyclerView / ListAdapter | Listas performáticas |
| Navigation Component | Navegação declarativa entre telas |
| Coroutines + Flow | Concorrência e reatividade |
| Room | Persistência local estruturada |
| DataStore | Armazenamento leve de preferências |
| Retrofit + OkHttp + Moshi | Consumo de APIs REST |
| Hilt | Injeção de dependências |
| Coil | Carregamento de imagens |
| JUnit / Mockito / Espresso | Testes |
| Git + GitHub | Versionamento e colaboração |


---

## 📦 Estrutura (sugerida para projetos de exemplo)
```
app/
 ┣ data/        (datasources remotos, locais, dtos, repos)
 ┣ domain/      (models de negócio, use cases - opcional se simplificar)
 ┣ ui/          (activities, fragments, adapters, viewmodels)
 ┣ di/          (módulos Hilt)
 ┣ core/        (utils, Result wrappers, extensions)
 ┗ build.gradle.kts
```

---

## 🧪 Critérios de Conclusão
- App final roda offline com cache coerente
- Tratamento de erros visível ao usuário
- Fluxo de login ou equivalente simples (mock ou real)
- Pipeline automatizado (build + testes) configurado
- Documentação curta de setup no README do projeto final

---

Opcional / Extensões: Jetpack Compose, Paging 3, WorkManager, Firebase (Auth, Firestore), Crashlytics, Analytics, Compose Multiplatform.