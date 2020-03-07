# Ping
Fica pingando um IP na rede

#
#### Pendências
- Implementar retorno do ping em pacote (Tudo de uma vez)
- Implementar o controle da variavel 'acessivel' vinda no corpo da requisição
- Impletantar o ping de um ip e receber via get
- Implementar controle de segurança no redebimento dos dados do usuario para não fazer DDoS na rede
- Fazer Controle de ping do mesmo ip e porta

#
### Possíveis regras de negócio
- Os serviços da API serão autenticados ou publicos?
- A autenticação será realizada em qual ambiente?
- O Endereço a ser notificado será autenticado?
- Caso a API seja publica será necessario fazer controle de acesso por IP do usuario (Não enviar varias requisições ao mesmo tempo)
- Será necessário implementar uma API de loop no contexto da aplicação, para o usuário não precisar ficar enviando post para a API?

#
### Exemplo de envio de dados

>http://localhost:8080/ping
>
```
{
	"endpoint": "http://localhost:8080/ping/notificacao",
	"retornoIndividual": false,
	"sites": [
		{ "endereco": "192.168.1.1",  "acessivel": false },
		{ "endereco": "www.google.com", "porta": 80, "acessivel": true },
		{ "endereco": "www.enderecoComErro.com.br", "acessivel": true },
		{ "endereco": "127.0.0.1", "acessivel": false },
		{ "endereco": "127.0.0.1", "porta": 8080 },
		{ "endereco": "10.0.3.1", "porta": "", "acessivel": false }
	]
}
```

|Atributo          |Definição| Tipo|
|------------------|---------|-----|
|endpoint          |Endereço (POST) a ser notificado após a verificação do ping | String |
|retornoIndividual | Tipo do retorno (Individual ou em Pacote)                  | Boolean |
|sites| Lista com os item a serem verificados| Array|
|sites.endereco|Endereço a ser verificado| String|
|sites.porta|(Opcional) Por a ser verificada|Number|
|sites.acessivel| (Opcinal) Notifica o endpoint somente se verificação atribuida for diferente | boolean|

#
### Exemplo de recebimento de dados
Dados enviados via (POST) ao endpoint em uma solicitação por retorno individual
```
{
    "endereco": "www.google.com",
    "porta": "80",
    "acessivel": true
}
```

|Atributo          |Definição| Tipo|
|------------------|---------|-----|
|endereco          |Endereço verificado| String |
|porta |Porta verificada, caso seja nulo o valor padrão de retorno sera 0| Number |
|acessivel |estado do endereco verificado| boolean |
