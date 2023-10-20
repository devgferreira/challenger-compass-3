#  Challenger compass 3 - Sistema de votos.

---

## Concepção das APIs
A concepção das APIs adotou os preceitos fundamentais da Clean Architecture, aliados à filosofia do clean code.

O cerne deste projeto reside na integração dos microservices e como eles vão se comunicar entre si. De forma simplificada, a estrutura do projeto é construída em torno da API ms-voto, estabelecendo uma comunicação precisa com as outras APIs.

Tal abordagem possibilita que o funcionário vote em uma proposta.


## Ferramentas usadas
RabbitMQ: Sistema de mensageria.

Docker: Sistema de contenerização.

Java 17: Linguagem de programação.

Recomendo fortemente que você tenha essas ferramentas instaladas.


## Como iniciar as APIs
Você precisa ter instalado as ferramentas mencionadas no tópico anterior. 

Uso de uma IDE: Vs Code, Eclipse, Intellij, ou um console caso for usar o Docker.

Caso você vá testar localmente, verifique se os perfis do application.yml estão apontando para o LOCAL. Se for usar o Docker, aponte para o DOCKER.

OBS: Crie as filas de mensageria no RabbitMQ: emitir-resultado-votacao e emitir-proposta-resultado-votacao.

Essas ferramentas e instruções irão ajudá-lo a configurar e executar as APIs de forma eficiente.


# Estrutura do Projeto
## Apresentação
Controlador: Controladores de API, responsáveis por receber solicitações e enviar respostas.

## Aplicação
DTOs: Objetos de Transferência de Dados usados para passar dados entre camadas.

Interfaces: Contratos para os serviços.

Serviços: Contém lógica de negócios de alto nível e chama métodos do repositório.

## Domínio
Entidade/Modelo: Entidades de domínio.

Interfaces: Contratos para os Repositórios.

Enums: Enumerações usadas em entidades e/ou regras de negócio.

## Infra
Configuração: Configuração de dependência do projeto.

Cliente: Configuração dos controladores do FeingClient.

Constantes: Regras que os valores não podem ser mutáveis.

Exceções: Configuração de exceção e seu manipulador.

Mqueue: Configuração rabbit para o envio das mensagens.

# Microservices

## Eureka-server
A API eureka-server é um registrador de outras APIs.


## Ms-cloud-geteway
A API ms-cloud-geteway é um centralizador de acesso, todas as APIs que se registrarem na API eureka-server terão a mesma porta por conta da API ms-cloud-geteway.


## Ms-funcionario
A API ms-funcionario foi feita para a criação e busca de funcionários, e consiste com um controlador: FuncionarioController.

FuncionarioController
O FuncionarioController tem dois end-points.

criarFuncionario: Esse end-point é responsável por criar um novo funcionário e persistir o mesmo no banco de dados.
buscarFuncionarioPorCpf: Esse end-point é responsável por buscar um funcionário pelo seu CPF no banco de dados.


## Ms-proposta
A API ms-proposta foi feita para a criação e busca de propostas, e consiste com um controlador: PropostaController.

PropostaController
O PropostaController tem três end-points.

criarProposta: Esse end-point é responsável por criar uma nova proposta e persistir a mesma no banco de dados.
buscarPropostaPorId: Esse end-point é responsável por buscar uma proposta pelo seu ID no banco de dados.
buscarTodasAsPropostas: Esse end-point é responsável por buscar todas as propostas.


## Ms-validacao-voto
A API ms-validacao-voto foi feita para validar se o funcionário pode votar naquela proposta verificando os setores de ambos, e consiste com um controlador: ValidacaoVotoController.

ValidacaoVotoController
O ValidacaoVotoController tem um end-point.

validarVoto: Esse end-point é responsável por validar se o funcionário pode votar na proposta.


## Ms-voto
A API ms-voto foi feita para contabilizar o voto do funcionário na proposta, buscar os funcionários na API ms-funcionario, as propostas na API ms-proposta, validar na API ms-validacao-voto, manda mensagem via RabbitMQ que a votação foi encerrada com seu resultado, e consiste com um controlador: VotoController.

VotoController
O VotoController tem um end-point.

votar: Esse end-point é responsável por buscar o funcionário e proposta em seus respectivos MS e persistir o voto do funcionário no banco de dados.


## Uso de Dockerfiles e docker-compose.yaml
Comandos para usar:

- docker-compose build: Para atualizar as imagens caso faça uma alteração.

- docker-compose up: Para baixar e atualizar os contêineres

Para executar os contêineres Dockerfile:
- Cada projeto tem seu próprio Dockerfile contendo instruções para construir as imagens.

docker-compose.yml:
- Este arquivo define como cada contêiner deve agir enquanto estiver rodando com o docker. Ele é essencial para a orquestração dos contêineres, garantindo que todos os serviços funcionem em harmonia.

Docker é uma ferramenta poderosa que ajuda a simplificar o processo de desenvolvimento, permitindo que você empacote suas aplicações com todas as suas dependências em um contêiner isolado. Isso facilita o compartilhamento e a implantação de sua aplicação, independentemente do ambiente de hospedagem.


## Uso do RabbitMQ
Filas usadas
emitir-resultado-votacao e emitir-proposta-resultado-votacao

emitir-proposta-resultado-votacao
- Essa fila é responsável por salvar a entidade proposta junto com o resultado da votação na API ms-voto: Aprovado ou Reprovado, e enviá-lo para persistir no banco de dados da API ms-proposta.

emitir-resultado-votacao
- Essa fila é responsável por mandar uma mensagem para a API ms-funcionario com o resultado da votação na API ms-voto. Exemplo: “O resultado da votação foi: Aprovado para a proposta: Aumentar o salário dos funcionários do ID: 1”.

O RabbitMQ é um broker de mensagens open source amplamente utilizado que suporta vários protocolos de mensagens. Ele permite que as aplicações se comuniquem entre si de forma assíncrona, melhorando a performance e a escalabilidade. É uma ferramenta essencial em arquiteturas baseadas em microserviços, permitindo que os serviços troquem informações de forma eficiente e confiável.

---
