# Relato de Implementação

### Sumário

1. [Objetivo](#objetivo)
2. [Instalação dos softwares necessários](#softwares)
3. [Passo-a-Passo para o desenvolvimento](#passos)
4. [Resultados Alcançados](#resultados)
5. [Licença](#licenca)

<div id='objetivo'/> 

### 1. Objetivo

O objetivo deste projeto é desenvolver uma API GraphQL simples usando o framework Spring para gerenciar informações sobre um jogo específico. A API permite realizar operações como adicionar, atualizar, excluir e consultar jogos, bem como recuperar detalhes sobre os desenvolvedores associados a cada jogo. A motivação não é criar uma API abrangente, porém o foco está em fornecer uma interface flexível e eficiente para manipular dados relacionados a jogos por meio de consultas GraphQL.

<div id='softwares'/> 

### 2. Instalação dos softwares necessários
  #### 2.1. Como configurar o ambiente de execução do projeto
  
  - Instale o [JDK 11](https://www.oracle.com/java/technologies/downloads/) ou superior
  
  - Instale o [VS Code](https://code.visualstudio.com/)
    - Instale essas Extensões no VS Code:
      
      - [Pack de Extensão Java VS Code](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
      - [Pack de Extensão Spring Boot VS Code](https://marketplace.visualstudio.com/items?itemName=vmware.vscode-boot-dev-pack)
        
  #### 2.2. Para executar o projeto
  
  - Clone o repositório `git clone https://github.com/Tomaz5556/GraphQLMiniProjetoWEB.git`
  
  - Abra a pasta do projeto `GraphQLMiniProjetoWEB`

  ![abrir-pasta](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/abrir-pasta.png)

  - Execute o arquivo `ProjectGraphqlApplication.java` para iniciar a Aplicação Spring.

  ![executar-projeto](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/executar-projeto.png)

  - Depois disso, acesse [http://localhost:8080/graphiql](http://localhost:8080/graphiql) no seu navegador.

  - Execute alguma consulta, por exemplo, buscar o nome do desenvolver do jogo `GTA`.
    ```
    query {
      gameByName(name: "GTA") {
        developer {
          name
        }
      }
    }
    ```
  - Você deverá ver uma resposta dessa maneira.

  ![executar-consulta](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/executar-consulta.png)

<div id='passos'/> 

### 3. Passo-a-Passo para o desenvolvimento
  #### 3.1. Começando com Spring Initializr
  
  - Inicializando o projeto:
    - Navegando até [https://start.spring.io/](https://start.spring.io/)
    - Escolhendo `Maven`.
    - Clicando em `Dependencies` e selecionando `Spring for GraphQL` e `Spring Web`. Assim:
    
    ![spring-initializr](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/spring-initializr.png)
    
    - Clicando em `Generate`.
    - Baixando o arquivo ZIP resultante, que é um arquivo de um aplicativo GraphQL configurado para o projeto.
      
  #### 3.2. Criando o Schema

  - Adicionando um novo arquivo `schema.graphqls` à pasta `src/main/resources/graphql` com o seguinte conteúdo:

    ![criando-schema](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/criando-schema.png)

    ```
    type Mutation {
        addGame(id: ID!, name: String!, developerId: ID!): Game!
        updateGame(id: ID!, name: String!, developerId: ID!): Game!
        deleteGame(id: ID!): Game!
    }
    
    type Query {
        gameById(id: ID!): Game
        gameByName(name: String!): Game
        allGames: [Game!]!
    }
    
    type Game {
        id: ID!
        name: String!
        developer: Developer!
    }
    
    type Developer {
        id: ID!
        name: String!
    }
    ```
  
  - Este esquema define as operações que podem ser realizadas em um jogo e seu desenvolvedor. Aqui estão os detalhes:
    - **Mutation**: Este tipo define três operações que podem ser realizadas em um jogo:
        - `addGame`: Permite adicionar um novo jogo.
        - `updateGame`: Permite atualizar um jogo existente.
        - `deleteGame`: Permite excluir um jogo existente.
    
    - **Query**: Este tipo define três consultas que podem ser realizadas:
        - `gameById`: Retorna os detalhes de um jogo específico com base no id fornecido.
        - `gameByName`: Retorna os detalhes de um jogo específico com base no nome fornecido.
        - `allGames`: Retorna uma lista de todos os jogos.
    
    - **Game**: Este tipo define a estrutura de um jogo com os campos `id`, `name` e `developer`.
    
    - **Developer**: Este tipo define a estrutura de um desenvolvedor com os campos `id` e `name`.
    
  #### 3.3. Criando as Classes
  ##### 3.3.1. Game e Developer

  - Criando as classes no pacote principal da aplicação, ao lado de ProjectGraphqlApplication.

    ![criando-gamedeveloper](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/criando-gamedeveloper.png)
  
  - `Game.java`

    ```
    package com.web.graphql;
    
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    
    public class Game {
    
        private String id;
        private String name;
        private String developerId;
    
        private static List<Game> games = new ArrayList<>(Arrays.asList(
                new Game("game-1", "GTA", "developer-1"),
                new Game("game-2", "Far Cry", "developer-2"),
                new Game("game-3", "The Witcher", "developer-3")));
    
        public Game(String id, String name, String developerId) {
            this.id = id;
            this.name = name;
            this.developerId = developerId;
        }
    
        public String getId() {
            return id;
        }
    
        public String getName() {
            return name;
        }
    
        public String getDeveloperId() {
            return developerId;
        }
    
        public static Game getById(String id) {
            return games.stream()
                    .filter(game -> game.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        }
    
        public static Game getByName(String name) {
            return games.stream()
                    .filter(game -> game.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        }
    
        public static List<Game> getAllGames() {
            return games;
        }
    
        public static Game addGame(String id, String name, String developerId) {
            Game newGame = new Game(id, name, developerId);
            games.add(newGame);
            return newGame;
        }
    
        public static Game updateGame(String id, String name, String developerId) {
            Game game = getById(id);
            if (game != null) {
                game.name = name;
                game.developerId = developerId;
            }
            return game;
        }
    
        public static Game deleteGame(String id) {
            Game game = getById(id);
            if (game != null) {
                games.remove(game);
            }
            return game;
        }
    }
    ```
    
  - `Developer.java`
    
    ```
    package com.web.graphql;

    import java.util.Arrays;
    import java.util.List;
    
    public record Developer (String id, String name) {
    
        private static List<Developer> developers = Arrays.asList(
                new Developer("developer-1", "Rockstar"),
                new Developer("developer-2", "Ubisoft"),
                new Developer("developer-3", "CD Projekt")
        );
    
        public static Developer getById(String id) {
            return developers.stream()
    				.filter(developer -> developer.id().equals(id))
    				.findFirst()
    				.orElse(null);
        }
    }
    ```
  ##### 3.3.2. GameController

  - Spring for GraphQL fornece um modelo de programação baseado em anotações. Com métodos anotados pelo controlador, podemos declarar como buscar os dados para campos específicos do GraphQL.
  - Adicionando `GameController` no pacote principal do aplicativo, ao lado de `Game` e `Developer`

    ![criando-gamecontroller](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/criando-gamecontroller.png)
  
  - `GameController.java`
    
    ```
    package com.web.graphql;
    
    import java.util.List;
    
    import org.springframework.graphql.data.method.annotation.Argument;
    import org.springframework.graphql.data.method.annotation.MutationMapping;
    import org.springframework.graphql.data.method.annotation.QueryMapping;
    import org.springframework.graphql.data.method.annotation.SchemaMapping;
    import org.springframework.stereotype.Controller;
    
    @Controller
    public class GameController {
        @QueryMapping
        public Game gameById(@Argument String id) {
            Game game = Game.getById(id);
            return game;
        }
    
        @QueryMapping
        public Game gameByName(@Argument String name) {
            return Game.getByName(name);
        }
    
        @QueryMapping
        public List<Game> allGames() {
            return Game.getAllGames();
        }
    
        @MutationMapping
        public Game addGame(@Argument String id, @Argument String name, @Argument String developerId) {
            return Game.addGame(id, name, developerId);
        }
    
        @MutationMapping
        public Game updateGame(@Argument String id, @Argument String name, @Argument String developerId) {
            return Game.updateGame(id, name, developerId);
        }
    
        @MutationMapping
        public Game deleteGame(@Argument String id) {
            return Game.deleteGame(id);
        }
    
        @SchemaMapping
        public Developer developer(Game game) {
            return Developer.getById(game.getDeveloperId());
        }
    }
    ```

  - A classe `GameController` é um controlador que define como buscar e manipular dados de `Game` e `Developer` conforme definido no esquema GraphQL.
  - `@QueryMapping` é uma anotação composta que atua como um atalho para o tipo `Query`, que representa as operações de leitura da API. Por exemplo, o método `gameById` mapeia o argumento `id` para o parâmetro `id` do método e retorna um objeto `Game` com esse `id`. O método `gameByName` faz o mesmo, mas com o argumento `name`. O método `allGames` retorna uma lista de todos os objetos `Game`.
  - `@MutationMapping` é uma anotação composta que atua como um atalho para o tipo `Mutation`, que representa as operações de escrita da API. Por exemplo, o método `addGame` recebe três argumentos: `id`, `name` e `developerId`, e retorna um novo objeto `Game` criado com esses valores. O método `updateGame` faz o mesmo, mas atualiza um objeto `Game` existente. O método `deleteGame` recebe um argumento `id` e retorna o objeto `Game` excluído com esse `id`.
  - `@SchemaMapping` mapeia um método manipulador para um campo no esquema GraphQL e o declara como o `DataFetcher` para esse campo. Por exemplo, o método `Developer` mapeia o campo `developer` do tipo `Game` para o método de manipulador, que recebe um objeto `Game` como fonte e retorna um objeto `Developer` associado a ele. O tipo `Developer` é outro tipo definido no esquema GraphQL, que representa o desenvolvedor de um jogo.
  - `@Argument` vincula um argumento GraphQL nomeado a um parâmetro de método. Por exemplo, o método `gameById` usa a anotação `@Argument` para vincular o argumento `id` do campo `gameById` do tipo `Query` ao parâmetro `id` do método. A anotação `@Argument` pode ser omitida se o nome do argumento e o nome do parâmetro forem iguais.
  
  #### 3.4. Habilitando o GraphiQL Playground
  - GraphiQL é uma interface visual útil para escrever e executar consultas e muito mais.
  - Adicionando esta configuração ao arquivo `application.properties` para ativar o GraphiQL.

    ```
    spring.graphql.graphiql.enabled=true
    ```
    
    ![editando-properties](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/editando-properties.png)

  #### 3.5. Executando o Projeto
  
  - Executando o arquivo `ProjectGraphqlApplication.java` para iniciar a Aplicação Spring.

  ![executar-projeto](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/executar-projeto.png)

  - Depois disso, acessando [http://localhost:8080/graphiql](http://localhost:8080/graphiql) no navegador.

  <div id='resultados'/> 
  
  ### 4. Resultados Alcançados
  
  #### 4.1 Mutation

  ##### 4.1.1 Adicionar um Jogo

  ![add-game](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/add-game.png)
  
  ##### 4.1.2 Atualizar um Jogo

  ![update-game](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/update-game.png)
  
  ##### 4.1.3 Excluir um Jogo

  ![delete-game](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/delete-game.png)

  #### 4.2 Query
  
  ##### 4.2.1 Buscar Jogo por ID

  ![game-id](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/game-id.png)
  
  ##### 4.2.2 Buscar Jogo por Nome

  ![game-name](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/game-name.png)
  
  ##### 4.2.3 Buscar Todos os Jogos

  ![all-games](https://github.com/Tomaz5556/GraphQLMiniProjetoWEB/blob/master/images-tutorial/all-games.png)

  <div id='licenca'/> 

  ### 5. Licença
  
  Esse projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE.md) para mais detalhes.
