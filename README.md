# Reserva-de-salas
Sobre as questões no final do teste técnico:
1. Quais foram as principais decisões técnicas adotadas durante o
desenvolvimento?
Serão explicadas mais abaixo durante a documentção do sistema. 
2. Se tivesse mais tempo, o que você implementaria ou melhoraria?
   Estudaria e implementaria os outros pontos:
   Testes automatizados
   Logs
   Códigos HTTP apropriados
   Autenticação
   Cache  
3. Você utilizou alguma ferramenta de IA durante o desenvolvimento?
   Sim, tanto o chatgpt tanto o gemini, o sistema conta com um frontend feito em boa parte por ia.
   Mas o maior uso foi para retirada de dúvidas com relação a sintaxe e algumas estruturas.
<br>
<br>
Documentação
<br>
Sistema de reservas de sala
<br>
1 -Objetivo: montar um sistema para gerenciar reservas de salas. 
<br>
<br>
2-Regras:
  ->Não são permitidos duas reservas da mesma sala em horários conflitantes, porém é possivel criar uma reserva logo após o termino da outra
  como por exemplo: reserva 1; 16:00 às 16:30 | reserva 2; 16:30 às 17:00 
  Ficou desta forma pois não faz sentido bloquear os inícios, já que uma das formas burlar seria colocar um minuto a mais, nisto o sistema precisaria
  de uma regra como "reuniões só podem ser marcadas 30 minutos depois da anterior" ou algo do gênero, essa abordagem não foi considerada necessária 
  para este sistema.
  ->O horário final deve ser posterior ao horário inicial.
  -> A data de marcação de reserva não pode ser anterior a data atual, no entanto para corrigir eventuais falhas, é possivel marcar para um horário 
  anterior ao atual.
  ->A sala informada deve existir. As salas são inseridas via moc, basta acessar o endpoint 
  ->Campos obrigatórios são validados.
  
3 - Tecnologias utilizadas: 
    - Java 25
    - Spring Boot
    - Spring Data JPA
    - Hibernate
    - Maven
    - H2 Database
    - Swagger/OpenAPI

4 - Arquitetura 
   Model -> DTO -> DAO -> Service -> Controller 
   A aplicação segue o padrão em camadas:

  Controller:
  Responsável por receber as requisições HTTP.

  Service:
  Contém as regras de negócio e validações.

  DAO:
  Responsável pela comunicação com o banco através do Spring Data JPA.

  DTO:
  Responsável pela transferência dos dados entre as camadas.
  
  Model
  Espelho do banco

5 - Como executar 
    Prefereicialmente com o Intelej, baixar ou clonar o projeto e executar pela ide. O banco só sera acessivel durante a execução do código. Para facilitar a navegação, há um frontend que deve ser executado junto com o liveserver, mas ele não cobre todos os endpoints.
    
6 -Começando pelo banco de dados:
  Foi escolhido um banco em memória para evitar a necessidade de instalações adicionais. 
  Vantagem: Não é necessário instalar o banco, ele já esta incluso nas bibliotecas do spring. 
            Não é necessário rodar uma query para criar o banco e as tabelas, o próprio  hibernate faz isso.
  Desvantagem: A cada uso é necessário reinserir os dados, já que o banco é deletado e recriado a cada novo teste.

  O banco escolhido foi o H2 no lugar do Apache Derb pelo simples fato de ser mais fácil à configuração do H2
  <img width="582" height="491" alt="image" src="https://github.com/user-attachments/assets/70f50c91-c002-4d5c-a1af-f5560612896a" />
  <br>
  Para acessar o H2, é necessário que a aplicação esteja rodando e acessar esta página : http://localhost:8080/h2
  Estas são as configuração para acessar, provavelmente não sera necessário alterar esta configuração. 
  <br>
  Driver Class:	 org.h2.Driver
  <br>
  JDBC URL:	 jdbc:h2:mem:salas
  <br>
  User Name:	sa
  <br>
  Password:
  <br>
  Ao clicar em conectar, deve ficar desta forma: 
 <img width="1918" height="992" alt="image" src="https://github.com/user-attachments/assets/03f79003-2424-44cf-bfd4-81017599af4b" />
 Clicando em reserva ou sala, o h2 gera uma query para fazer uma busca ampla dentro da tabela. Evite clicar em mais de uma tabela 
 antes de apagar a query anterior.

 7 - Agora os endpoints: 
 <img width="1858" height="986" alt="image" src="https://github.com/user-attachments/assets/d575e6b5-a22e-42b0-8441-6000327b2988" />
 O spring traz consigo o toncat, um servidor embutido que roda nativamente na porta 8080, estou usando o swagger para expor os endpoints de uma forma
 mais clara. 
 A rota do swagger é esta: localhost:8080/swagger-ui/index.html
 Antes de usar os outros endpoints, deve primeiro executar a inserção das salas nesta rota, não é necessário passar nenhum parâmetro, apenas executar. 
 <img width="1858" height="351" alt="image" src="https://github.com/user-attachments/assets/36de395a-ec48-4c51-aba8-3b2ac2568b49" />
 
 Para facilitar os testes na api, há uma rota que insere várias reservas, ela só pode ser usada depois de inserir as salas. 
 <img width="1851" height="716" alt="image" src="https://github.com/user-attachments/assets/53e5b4bb-bdfc-474d-9899-066c442ed5ba" />
 Para cadastrar uma reserva, acesse o endpoint
 <br>
 Insira os dados no formato json, não é necessário passar o idReserva, o banco cria este campo automaticamente e mesmo que a id seja passada, 
 ela será ignorada pelo sistema. Os dados são:
 • responsável
 • idsala(1 , 2 ou 3)
 • data(deve esta no formato dd-MM-yyyy)
 • horário inicial(deve esta no formato HH:MM)
 • horário final(deve esta no formato HH:MM)
 • observação (opcional)
 <img width="1851" height="716" alt="image" src="https://github.com/user-attachments/assets/74e065d6-3169-4574-918b-c609cfb46a65" />
 
 <br>
 Nesta rota é possivel buscar uma reserva passando uma id, este é um exemplo de retorno: 
 
  "idReserva": 1,
  "responsavel": "string",
  "data": "21-07-2026",
  "inicio": "10:00",
  "fim": "12:00",
  "observacao": "string",
  "sala": {
    "idSala": 1,
    "nome": "Sala A",
    "capacidade": 6
  
<br>
 <img width="1851" height="716" alt="image" src="https://github.com/user-attachments/assets/5c5ebdad-8352-4249-afe5-f23364e3987d" />
 <br>
 Para listar as reservas, acesso o seguinte endpoint, não é necessário passar parâmetros: 
 <br>
 <img width="1851" height="716" alt="image" src="https://github.com/user-attachments/assets/96d076e0-cc90-4aab-8258-79726d828376" />
exemplo de retorno:

  {
    "idReserva": 1,
    "responsavel": "string",
    "data": "21-07-2026",
    "inicio": "10:00",
    "fim": "12:00",
    "observacao": "string",
    "sala": {
      "idSala": 1,
      "nome": "Sala A",
      "capacidade": 6
    }
  },
  {
    "idReserva": 2,
    "responsavel": "Carlos Mendes",
    "data": "22-09-2026",
    "inicio": "09:00",
    "fim": "10:30",
    "observacao": "Reunião de alinhamento semanal",
    "sala": {
      "idSala": 1,
      "nome": "Sala A",
      "capacidade": 6
    }
  },
  {
    "idReserva": 3,
    "responsavel": "Carlos Mendes de Sá",
    "data": "18-12-2026",
    "inicio": "11:00",
    "fim": "12:30",
    "observacao": "Reunião de alinhamento semanal 2ª temporada",
    "sala": {
      "idSala": 1,
      "nome": "Sala A",
      "capacidade": 6
    }
  }, (...)
  Para atualizar uma reserva, acesse este endpoint
  <img width="1851" height="716" alt="image" src="https://github.com/user-attachments/assets/1865679b-62e3-46e9-9aba-4f100253adba" />
  É necessário passar estes dados: 
  {
  "idReserva": 0,
  "responsavel": "string",
  "data": "2026-07-21",
  "inicio": "string",
  "fim": "string",
  "observacao": "string",
  "idSala": 0
  }
  o idReserva deve existir e o idSala também, a data não deve ser anteior a atual e o horário não deve conflitar com outra reunião. 
  Para deletar, passe a id da reserva nesta rota
  <img width="1851" height="716" alt="image" src="https://github.com/user-attachments/assets/afcadaf4-d89a-4c35-8945-50e4af8c073c" />

  8 - Sobre os outros endpoints
  A paginação pode ser feita neste endpoint passando o numero da página e quantidade que cada página deve carregar
  <img width="1851" height="716" alt="image" src="https://github.com/user-attachments/assets/4b583d50-ea7e-4403-af56-1e557cfb3cda" />

  Neste é possivel passar uma data e o sistema lista todas as reservas, a data deve ser passada neste formato: dd-MM-yyyy(12-02-2027)
  <img width="1851" height="716" alt="image" src="https://github.com/user-attachments/assets/2911b104-9cee-47a8-a3b5-90cfaec1558f" />

  Nestas 2, é possivel filtrar pelo id da sala, ambas trazem o mesmo resultado, a diferença é o foco, em reserva o retorno são as reservas e sala
  na sala o retorno é a sala e as reservas 
  <img width="1872" height="897" alt="image" src="https://github.com/user-attachments/assets/3633e9da-4954-4be2-b18c-e6dca26ddd43" />

  Nesta rota retorna uma lista ordenada pelas salas e pela data, da sala 1 até a sala 3, e da data mais antiga até a mais atual
  <img width="1872" height="897" alt="image" src="https://github.com/user-attachments/assets/12337980-3a06-440b-be3a-1675ca6facb4" />

  Por fim, nesta o retorno são as salas e suas respectivas reservas na ordem que foram inseidas
  <img width="1872" height="897" alt="image" src="https://github.com/user-attachments/assets/e282c5c9-19e7-47a5-9390-517e446ce810" />






 

 
 

 





      



