# Projeto: Acervo de Álbuns Musicais da Década de 90

Turma 01 - Programação Orientada a Objetos 

Membros:
Cleysla Maria Santos Ferreira,
José Gabriel Silva Santana e
Maria Aparecida Nascimento dos Santos

---

## Motivação

A década de 90 foi um período em que a música se transformou em espelho e motor de um mundo em mutação. Foi uma era de fronteiras borradas, em que o mainstream e o alternativo travaram um diálogo intenso, gerando um dos períodos mais férteis da cultura contemporânea.

O rock alternativo floresceu com uma força inédita: das guitarras grunge que ecoavam desencanto e inquietude às paisagens sonoras etéreas do shoegaze, havia um sentimento de urgência, de que cada acorde carregava uma verdade sobre a juventude da época. Paralelamente, o britpop transformou a experiência britânica em hinos de orgulho nacional e cotidiano urbano, enquanto, do outro lado do Atlântico, bandas indie reinventavam seu som ao se conectar com influências distintas.

Mas os anos 90 não viveram apenas de guitarras. Foi também a década em que a música eletrônica encontrou nova linguagem: o trip hop emergiu como trilha sonora das cidades modernas, misturando batidas densas e atmosferas sombrias que pareciam condensar tanto o desencanto quanto a sensualidade de um novo tempo futurista. O techno e o house saíram dos clubes subterrâneos para conquistar multidões, desenhando um otimismo crescente, a sensação de que o futuro estava logo ali, pulsando na pista de dança.

Este acervo não busca apenas listar os álbuns mais lembrados, mas também capturar a essência plural dos anos 90: uma década em que o pop e o alternativo coexistiam em igual intensidade, dialogando e se transformando mutuamente. Entre a ousadia eletrônica de "Mezzanine", a vulnerabilidade íntima de "Verde Anil Amarelo Cor de Rosa e Carvão", a catarse coletiva de "Nevermind" e a espiritualidade etérea de "Ágætis byrjun", temos um retrato da diversidade radical que marcou a música da época. É justamente nesse ecletismo, onde o mainstream e o underground, a pista de dança e o quarto solitário, a fúria e a contemplação se entrelaçam, que reside a beleza dos anos 90. Uma era que ainda hoje soa viva, fresca e necessária, como se os anos 90 nunca tivessem terminado, apenas se transformado em ecos que continuam a reverberar em nossa cultura.

## Descrição 

O projeto desenvolvido tem como tema um acervo de álbuns musicais da década de 1990. O sistema permite que diferentes usuários interajam com o acervo por meio de uma aplicação web, podendo criar uma conta, realizar login, pesquisar álbuns, visualizar detalhes e organizar seus favoritos. A ideia é simular o funcionamento de uma pequena aplicação de catálogo musical, em que os dados são persistidos e mantidos mesmo após o fechamento da aplicação.

A aplicação foi construída utilizando a linguagem Java 17 e o framework Spring Boot, organizando a estrutura em camadas seguindo o padrão arquitetural MVC (Model–View–Controller). Além disso, foi utilizado o Thymeleaf como motor de templates, o que possibilita a integração direta entre os dados vindos do backend em Java e as páginas HTML apresentadas ao usuário.

## Funcionalidades

O sistema oferece as seguintes funcionalidades principais:

- Cadastro de usuários: qualquer pessoa pode criar uma conta informando nome, email e senha.

- Autenticação (login): usuários já cadastrados podem entrar no sistema validando email e senha.

- Pesquisa de álbuns: o acervo pode ser consultado através de filtros como título, gênero musical e artista.

- Favoritos: cada usuário pode adicionar ou remover álbuns da sua lista de favoritos, além de visualizar todos os que já foram salvos.

- Exibição de informações: a aplicação mostra para o usuário detalhes de cada álbum, como título, artista, ano de lançamento, gênero,uma curta descrição.

## Podrões e conceitos utilizados

### Persistência dos dados

Neste desenvolvimento, foi adotada a persistência ocorre através de arquivos de texto (.txt), que cumprem o papel de banco de dados. Dessa forma, existem dois arquivos principais dentro do projeto:

- usuarios.txt: responsável por armazenar os registros dos usuários. Cada linha do arquivo corresponde a um usuário, contendo dados como id, nome, email, senha e os códigos dos álbuns favoritados.

- albuns.txt: armazena as informações de cada álbum, incluindo código, título, artista, ano, gênero, link e descrição.

Essa escolha exigiu a criação de repositórios próprios (UsuarioRepository e AlbumRepository), que contêm os métodos para ler e gravar dados diretamente nos arquivos de texto. Assim, operações como salvar um novo usuário, atualizar favoritos ou listar os álbuns cadastrados são feitas manipulando os arquivos .txt. Essa abordagem funciona como uma simulação de banco de dados, garantindo a persistência dos dados sem a necessidade de configurar um sistema relacional.

### Arquitetura do sistema

A estrutura segue o padrão MVC:

- Model: contém as classes principais do domínio, como Usuario, Album e a classe abstrata Midia. Essas classes representam os dados e suas regras básicas de funcionamento e o AlbumRepository e UsuarioRepository, que são responsáveis pelo acesso aos dados, realizando operações de leitura e escrita nos arquivos .txt. 

- Service: concentra a lógica de negócio da aplicação. Por exemplo, ao favoritar um álbum, o service valida se o álbum e o usuário existem, atualiza os favoritos e solicita que o repositório persista essa modificação.

- Controller: responsável por mapear as rotas da aplicação, conectar a camada de serviço com as páginas HTML e retornar as informações processadas para a interface.

- View: camada visual desenvolvida em HTML e CSS, integrada ao backend com Thymeleaf, que permite exibir informações dinâmicas vindas do Java diretamente nas páginas.

### Conceitos de OO
- Encapsulamento: As classes Album e Usuario possui atributos privados e métodos públicos de acesso. Isso garante que os dados só sejam manipulados de maneira controlada.
  
- Abstração: O sistema foi dividido em entidades principais, sendo Album e Usuario, e suas responsabilidades foram separadas em classes de repositório, serviço e controle. Desse modo, detalhes de persintência ficam escondidos do restante do sistema.
  
- Herança e polimorfismo: No projeto existe a classe abstrata Midia, que define atributos e métodos comuns a diferentes mídias. A classe Album herda de Midia, reutilizando código e organizando melhor o sistema. Isso também permite o uso de polimorfismo, já que podemos manipular objetos de Album como Midia, tratando diferentes mídias de forma genérica.

### Testes com JUnit

No projeto, o JUnit 5 foi utilizado para criar testes unitários que verificam o funcionamento correto dos serviços AlbumService e UsuarioService sem depender do banco de dados real. Cada teste é independente, usando @BeforeEach para configurar o ambiente antes de cada execução.

O Mockito foi usado para criar mocks dos repositórios AlbumRepository e UsuarioRepository e simular seus comportamentos. Com when(...).thenReturn(...) definimos os retornos esperados e, com verify(...), garantimos que métodos importantes foram chamados.

No AlbumService, os testes verificam funcionalidades como listar álbuns, buscar por código e filtrar por gênero ou artista. Já no UsuarioService, os testes verificam operações relacionadas a usuários, como login, favoritar/desfavoritar álbuns e listar favoritos. Essa abordagem permite testar o comportamento das classes de serviço de forma rápida, confiável e isolada, garantindo que mudanças no código não quebrem funcionalidades essenciais.

### Padrão de projeto

No projeto, os componentes anotados com @Service e @Repository do Spring Boot são tratados como Singletons por padrão. Isso significa que o Spring cria e gerencia uma única instância de cada componente durante toda a execução da aplicação. Dessa forma, por exemplo, o AlbumRepository é sempre o mesmo objeto em todos os serviços que o utilizam, garantindo consistência no acesso e manipulação dos dados. Assim, o Spring permite aplicar o padrão de projeto Singleton de forma prática e transparente, sem a necessidade de implementação manual.
