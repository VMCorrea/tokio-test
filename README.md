# API Customer Service

Este projeto é um teste de programação.

O programa consiste em uma API REST que executa todas as operações CRUD para objetos do tipo Customer, utilizando os principais métodos HTTP (GET, POST, PUT, DELETE).

# Documentação

Segue neste documento README.md, o manual de requisições para utilizar esta API.

Para a documentação do código, foi utilizado a funcionalidade do javadoc. Os arquivos referentes a documentação do javadoc se encontram na pasta /doc do projeto.

# Tecnologias

O projeto construído via Maven utilizando-se do Spring Boot, e diversos frameworks da família Spring como Spring MVC, Spring Data, Spring Security e as ferramentas que já vem atreladas aos mesmos.

Foi utilizada a JDK 8 como versão do Java para o projeto.

# Instalação
O jar do projeto pode ser encontrado aqui

Para o projeto funcionar basta abrir a linha de comando e executar o jar baixado:
			
	java -jar caminho\do\arquivo\api.jar
# Uso
## 1. Servidor
Ao executar o arquivo JAR, o Spring Boot irá iniciar e um servidor local será levantado no endereço http://localhost:8080/, a partir daí será possível executar os HTTP Requests, através do curl, do navegador ou de programas como POSTMAN.
## 2. JSON
A API consome e produz arquivos do tipo JSON. O formato para as requisições de inserção de dados de Customer (POST e PUT) é o seguinte: 
		
		{
		    "name": "Nome",
		    "email": "nome@email.com",
		    "adresses": [
		        {
		            "cep": "00000000"
		        },
		        {
		            "cep": "11111111"
		        }
		    ]
		}
## 3. Autenticação
O projeto utiliza um sistema simples de autorização do Spring. Para consumir qualquer endpoint, é necessário logar com as credências
* Usuário: admin
* Senha: 1234

Ou adicionar no header "Authorization: Basic YWRtaW46MTIzNA==".
* "YWRtaW46MTIzNA==" é o código em base64 que representa "admin:1234"
## 4. Requisições

#### 4.1 GET Request (Único)
Requisição do tipo GET, que busca um customer no banco de dados.

    curl --request GET --url http://localhost:8080/customers/{id} -H “Authorization: Basic YWRtaW46MTIzNA==”
O customer será buscado através de seu id.
#### 4.2 GET Request (Listagem)

Requisição do tipo GET, que lista os customers.
A lista contém um sistema de paginação com a possibilidade de escolher a quantidade de elementos por página e de ordenação onde é possível escolher ordenar por um dos campos de customer.

    curl --request GET --url http://localhost:8080/customers?page=1&size=5&sortBy=id -H “Authorization: Basic YWRtaW46MTIzNA==”

Valores padrões dos parâmetros:
* page = 1
* size = 5
* sortBy = id

#### 4.3 POST Request
Requisição do tipo POST, que insere um customer no banco de dados.

    curl --request POST --url http://localhost:8080/autores -H “Authorization: Basic YWRtaW46MTIzNA==”

Junto da requisição, é necessário enviar um arquivo do tipo JSON.
A inserção deve seguir as seguintes regras para funcionar:
* O ID não precisa ser enviado.
* Nome não pode ser nulo.
* Email não pode ser nulo.
* Deve haver pelo menos um endereço.

#### 4.4 PUT Request
Requisição do tipo PUT, para atualizar um customer no banco de dados.

    curl --request PUT --url http://localhost:8080/autores/{id} -H “Authorization: Basic YWRtaW46MTIzNA==”

A requisição deve enviar um arquivo JSON com o customer que será atualizado. As mesmas regras do POST se aplicam aqui.

A identificação do customer é feita pelo id enviado na url.

#### 4.5 DELETE Request
Requisição do tipo DELETE, que apaga um customer no banco de dados.

    curl --request DELETE --url http://localhost:8080/autores/{id} -H “Authorization: Basic YWRtaW46MTIzNA==”
Para deletar, é utilizado o id do customer.