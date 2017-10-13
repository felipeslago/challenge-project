## Build da aplicação
`gradle clean build`

## Testes da aplicação
`gradle test`

## Executar a aplicação
A aplicação irá executar na porta 9002, conforme definido no application.properties

`gradle bootRun`

## Endpoints
`GET: /member`

`GET: /member/{MEMBER_ID}`

`POST: /member
 BODY:
    {
        "name":string,
        "email":string,
        "dateOfBirth":date(YYYY-MM-DD),
        "heartTeam":long
    }
`
