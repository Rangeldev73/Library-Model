# 📚 Library Management System (CLI)

<p align="center">
  <a href="#-english-version">English Version</a> •
  <a href="#-versão-em-português">Versão em Português</a>
</p>

---

## 🇺🇸 English Version

### 📌 Project Overview

A robust, console-based (CLI) Library Management System built natively in Java.
Designed with a strict focus on Object-Oriented Programming (OOP) pillars, SOLID
principles, and decoupled System Design, this application uses no external frameworks
to demonstrate pure core software engineering, data structure handling, and
relational data synchronization.

The software dynamically manages the complete lifecycle (CRUD) of books and users
while maintaining real-time relational integrity during loan and return transactions.

---

### 🏗️ Architecture & System Design

#### 1. Separation of Concerns (SoC) & UI Isolation

The system architecture strictly separates the presentation layer from business logic:

- **Presentation Layer (`Main.java`):** Acts purely as the console interface. It handles
  menu rendering, input capture via `Scanner`, string sanitization, and terminal
  exception handling (such as `NumberFormatException`).

- **Business Logic Layer (`LibraryManager.java`):** The functional core of the application.
  It contains zero console outputs or input prompts. It processes rules, state mutations,
  and data queries in memory, making the business engine easily portable to a Web/GUI
  environment or API controllers in the future.

#### 2. Dependency Injection (DI) & Decoupling

To eliminate rigid coupling, the `LibraryManager` engine does not instantiate its own
internal storage collections. Instead, it receives pre-populated lists reactively via
its constructor:

```java
public LibraryManager(List<Book> books, List<User> users, List<Loan> loans) {
    this.books = books;
    this.users = users;
    this.loans = loans;
}
```

This decouples domain logic from physical infrastructure, allowing mock implementations
to be easily injected for unit testing.

#### 3. Defensive Programming & Edge Case Handling

- **Relational Deletion Blocks:** A book or user cannot be removed if they have an active
  loan record, preventing data corruption or orphan references.
- **Double State Validation:** Book deletion guards against edge cases by checking both
  reference presence and matching against the explicit `BookStatus.AVAILABLE` enumerator.
- **Defensive Copying:** Query methods (`getBooks()`, `getUsers()`) return shallow copies
  (`new ArrayList<>(...)`) to prevent unintended direct state mutation from outside the
  manager class.

#### 4. Automated File Persistence (CSV over Flat Files)

Storage operations are fully abstracted inside `FileService.java`. The system serializes
state dynamically using semicolon (`;`) delimiters. Data synchronization is triggered
across all three flat files (`books.txt`, `users.txt`, `loans.txt`) immediately after
every in-memory update.

---

### 🗂️ Relational Data Schema

The flat files act as relational database tables, generated automatically on the first
write operation:

| File        | Content                                              |
|-------------|------------------------------------------------------|
| `books.txt` | ISBN, title, author, and availability status         |
| `users.txt` | User name and unique incremental ID                  |
| `loans.txt` | Relational bridge: Book ISBN mapped to User ID       |

---

### 📁 Project Structure

```
Library-Model/
├── Book.java
├── BookStatus.java
├── FileService.java
├── LibraryManager.java
├── Loan.java
├── Main.java
├── User.java
└── README.md
```

---

### 🚀 Execution Guide

All source files are located together in the root directory. Compile and run the
application directly from your terminal:

```bash
# Compile all source files
javac *.java

# Run the application
java Main
```

---

## 🇧🇷 Versão em Português

### 📌 Visão Geral do Projeto

Um sistema robusto de gestão de biblioteca baseado em console (CLI), desenvolvido de
forma nativa em Java. Projetado com foco rigoroso nos pilares da Programação Orientada
a Objetos (POO), princípios SOLID e em um Design de Sistema desacoplado, esta aplicação
abdica de frameworks externos para demonstrar fundamentos puros de engenharia de
software, manipulação de estruturas de dados e integridade relacional.

O software gerencia de forma dinâmica o ciclo de vida completo (CRUD) de livros e
usuários, garantindo consistência transacional em tempo real nos fluxos de empréstimo
e devolução.

---

### 🏗️ Arquitetura e Design do Sistema

#### 1. Separação de Responsabilidades e Isolamento de Interface

A arquitetura do sistema divide estritamente a camada de apresentação das regras de
domínio:

- **Camada de Apresentação (`Main.java`):** Atua unicamente como a interface de console.
  É responsável pela renderização dos menus, captura de dados via teclado, sanitização
  de entradas e tratamento de exceções de terminal (como entradas não numéricas).

- **Camada de Regras de Negócio (`LibraryManager.java`):** O núcleo funcional da
  aplicação. Não possui nenhuma instrução de impressão ou captura de dados direta.
  Processa validações, mutações de estado e buscas em memória, tornando o motor de
  regras totalmente portátil para interfaces gráficas ou controladores de sistema
  futuros sem alterações no núcleo.

#### 2. Injeção de Dependência e Desacoplamento

Para mitigar o acoplamento rígido, o motor do `LibraryManager` não instancia suas
próprias listas internas. Ele recebe suas coleções populadas de forma reativa através
do construtor:

```java
public LibraryManager(List<Book> books, List<User> users, List<Loan> loans) {
    this.books = books;
    this.users = users;
    this.loans = loans;
}
```

Isso isola a lógica de domínio da infraestrutura física, permitindo injetar coleções
simuladas para testes unitários isolados.

#### 3. Programação Defensiva e Casos de Borda

- **Bloqueio de Remoção Condicional:** Um livro ou usuário não pode ser excluído do
  sistema se houver um vínculo de empréstimo ativo, impedindo a quebra de consistência
  e a criação de registros órfãos.
- **Validação Dupla de Estado:** O método de remoção de livros valida tanto a existência
  do objeto quanto se seu enumerador de estado está explicitamente definido como
  disponível antes de prosseguir.
- **Cópias Defensivas:** Os métodos de consulta retornam clones superficiais das
  coleções internas para proteger o estado estrutural contra mutações externas
  indesejadas.

#### 4. Persistência de Arquivos Automatizada

O armazenamento físico é completamente abstraído na classe `FileService.java`. O sistema
serializa os dados dinamicamente no formato de texto delimitado por ponto e vírgula.
A sincronização é acionada atualizando os três arquivos simultaneamente logo após cada
alteração em memória.

---

### 🗂️ Esquema Relacional de Dados

Os arquivos de texto plano atuam como tabelas relacionais, gerados automaticamente na
primeira gravação:

| Arquivo     | Conteúdo                                                         |
|-------------|------------------------------------------------------------------|
| `books.txt` | ISBN, título, autor e estado de disponibilidade do livro         |
| `users.txt` | Nome do usuário e identificador único incremental                |
| `loans.txt` | Registro de empréstimo: ISBN do livro vinculado ao ID do usuário |

---

### 📁 Estrutura do Projeto

```
Library-Model/
├── Book.java
├── BookStatus.java
├── FileService.java
├── LibraryManager.java
├── Loan.java
├── Main.java
├── User.java
└── README.md
```

---

### 🚀 Guia de Execução

Como todas as classes estão localizadas juntas na pasta raiz do projeto, compile e
execute a aplicação diretamente pelo terminal:

```bash
# Compilar todos os arquivos fonte
javac *.java

# Executar a aplicação
java Main
```

---

<p align="center">
  Desenvolvido como projeto prático de portfólio acadêmico em Ciência da Computação.
</p>