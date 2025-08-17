# 🛒 Supermercado CLI

Projeto acadêmico desenvolvido para aprendizado sobre **Firebase** em conjunto com **Java**.  
A aplicação simula um **sistema de supermercado via linha de comando (CLI)**, com funcionalidades como:

- Cadastro de usuários
- Login com Firebase Authentication
- Listagem de produtos
- Adição ao carrinho
- Visualização do carrinho
- Finalização da compra

---

## 🚀 Tecnologias utilizadas

- **Java 24+**
- **Maven** (gerenciador de dependências)
- **Firebase Authentication**
- **Firebase Admin SDK**
- **SLF4J + Logback** (para logging)

---

## 📦 Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado:

- [Java 24+](https://www.oracle.com/java/technologies/downloads/)  
- [Apache Maven 3.9+](https://maven.apache.org/download.cgi)  
- Conta no [Firebase Console](https://console.firebase.google.com/) com um projeto configurado.  

---

## 🔑 Configuração das variáveis de ambiente

O projeto utiliza duas variáveis de ambiente para se conectar ao Firebase:

1. **`FIREBASE_WEB_API_KEY`**  
   - Usada para autenticação com Firebase Authentication.  
   - Você encontra em:  
     **Firebase Console > Configurações do projeto > Geral > Seus apps > Configuração da Web (API Key).**

2. **`GOOGLE_APPLICATION_CREDENTIALS`**  
   - Caminho para o arquivo JSON de credenciais do Firebase Admin SDK.  
   - Você pode gerar esse arquivo em:  
     **Firebase Console > Configurações do projeto > Contas de serviço > Gerar nova chave privada.**

### Exemplos de configuração

#### Linux / Mac
```bash
export FIREBASE_WEB_API_KEY="sua_api_key_aqui"
export GOOGLE_APPLICATION_CREDENTIALS="/caminho/para/credenciais.json"
````

#### Windows (PowerShell)

```powershell
setx FIREBASE_WEB_API_KEY "sua_api_key_aqui"
setx GOOGLE_APPLICATION_CREDENTIALS "C:\caminho\para\credenciais.json"
```

⚠️ **Importante:** Depois de definir, reinicie o terminal ou o IntelliJ para que as variáveis sejam carregadas.

---

## ▶️ Como rodar o projeto

### 1. Via Maven (linha de comando)

Dentro da pasta do projeto, rode:

```bash
mvn clean compile exec:java
```

Isso irá compilar e executar o programa, abrindo o menu interativo no console.

---

### 2. Via IntelliJ IDEA

1. Abra o projeto no IntelliJ.
2. Vá em **Run > Edit Configurations...**
3. Clique em **+** e selecione **Maven**.
4. Em *Command line*, adicione:

   ```
   exec:java
   ```
5. Em **Environment variables**, configure:

   ```
   FIREBASE_WEB_API_KEY=sua_api_key_aqui;
   GOOGLE_APPLICATION_CREDENTIALS=C:\caminho\para\credenciais.json
   ```
6. Salve e clique em **Run ▶️**.

---

## 📖 Exemplo de uso

Ao rodar, você verá o menu:

```
=== Supermercado (CLI) ===
1) Cadastrar  
2) Login  
3) Listar produtos  
4) Adicionar ao carrinho  
5) Ver carrinho  
6) Finalizar  
0) Sair
```

Basta escolher a opção desejada e seguir as instruções.

---

## ⚠️ Avisos

* Durante a execução, podem aparecer **warnings do SLF4J** ou **sun.misc.Unsafe**.

  * Esses avisos vêm de bibliotecas externas (Guice / Firebase).
  * Não afetam a execução do projeto.
* Se quiser ver os logs completos do Firebase, adicione o provider de logging (já configurado no `pom.xml` com Logback).

---

## 👨‍🎓 Objetivo acadêmico

Este projeto foi desenvolvido como parte da disciplina de **Programação Orientada a Objetos II**, com o intuito de praticar:

* Integração de projetos Java com serviços externos (Firebase).
* Uso de variáveis de ambiente para configuração segura.
* Boas práticas de organização com Maven.
* Implementação de um sistema de **linha de comando interativo**.
