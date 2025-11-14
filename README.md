# README - PeripheralCatalog

## Integrantes da Equipe

-   Yasmin Faraj
-   Felipe Uemura

## Sobre o Projeto

O PeripheralCatalog é um aplicativo Android desenvolvido em Kotlin, utilizando
arquitetura MVVM, Jetpack Compose para a interface e Room para
armazenamento local. O aplicativo permite cadastrar, listar, consultar e
excluir periféricos, com integração opcional a uma API via Retrofit.

## Estrutura do Projeto

-   MVVM (Model-View-ViewModel)
-   Room Database para persistência
-   Retrofit para consumo de API externa
-   Jetpack Compose para UI
-   Repository centralizando fonte de dados

## Instruções para Execução

1.  Abra o projeto no Android Studio.
2.  Aguarde o Gradle sincronizar.
3.  Execute em um dispositivo físico ou emulador.
4.  O banco de dados é criado automaticamente pelo Room.

## Endpoints da API

O projeto utiliza uma requisição simples: - GET /peripherals\
Retorna uma lista de periféricos contendo id, name, brand e type.

## Diagrama de Navegação

MainActivity\
→ Lista de Periféricos\
→ Adicionar Periférico\
→ Detalhes do Periférico

## Estrutura do Banco de Dados (Room)

Tabela: PeripheralEntity\
Campos: - id (Primary Key) - name - brand - type

## Funcionalidades

-   Listar periféricos
-   Adicionar periféricos
-   Excluir periféricos
-   Persistência local
-   Consumo de API
-   Interface em Compose

## Trabalho em Equipe e Contribuições

### Yasmin Faraj

-   Camada ViewModel
-   Interface com Compose
-   Integração com o Repository
-   Documentação

### Felipe Uemura

-   Configuração do Room Database
-   Criação das entidades e DAO
-   Estruturação do Retrofit
-   Modelos e DTOs
-   Ajustes na arquitetura