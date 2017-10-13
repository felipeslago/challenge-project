## Build da aplicação
`gradle clean build`

## Testes da aplicação
`gradle test`

## Executar a aplicação
A aplicação irá executar na porta 9001, conforme definido no application.properties

`gradle bootRun`

## Endpoints
`GET: /campaign`

`GET: /campaign/{CAMPAIGN_ID}`

`POST: /campaign
 BODY:
     {
        "name":string,
        "startDate":date(YYYY-MM-DD),
        "endDate":date(YYYY-MM-DD),
        "heartTeamId":long
     }
`

`PUT: /campaign
 BODY:
     {
        "id":long,
        "name":string,
        "startDate":date(YYYY-MM-DD),
        "endDate":date(YYYY-MM-DD),
        "heartTeamId":long
     }
`

`DELETE: /campaign/{CAMPAIGN_ID}`

`GET: /team`

`DELETE: /team/{TEAM_ID}`