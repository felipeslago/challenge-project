## Campaign Api e Group Member Api
Projeto de implementação das APIs de cadastro de Campanhas e cadastro de Sócio Torcedor

Estratégia utilizada para a criação das APIs:
 1. Spring Boot Tomcat Embedded
 2. H2 Database para persistência dos dados em arquivo.
 3. Foi utilizado a criação de uma configuração assíncrona para o cadastro e atualização das campanhas, garantindo que as datas das campanhas sejam alteradas sem concorrência e sem manter a requisição esperando.
 4. Foi criado um serviço de WebHooks que pode ser acionado para enviar atualizações de campanhas

* Dentro da pasta artifacts, existe um arquivo com todas as chamadas às APIs, para importação no Postman

## Stream
Projeto de implementação do uso de Streams para encontrar vogais

## Deadlock
Um Deadlock ocorre quando dois ou mais threads ficam bloqueados, um aguardando o outro.
Quando threads executam paralelamente e o objeto acessado não é thread-safe, se faz necessário o uso de métodos ou declarações synchronized.
Porém, isso poderá gerar um Deadlock, se em algum ponto do código os threads ficarem aguardando outros threads executar.
Uma maneira de evitar um Deadlock seria lockar (sincronizar) partes pequenas do código, seguindo uma ordem definida.
Em outras palavras, ao invés de sincronizar o método inteiro, podemos sincronizar apenas a parte do código que faz o acesso direto ao objeto.

## Stream x ParallelStreams
A diferença entre os dois métodos é o acesso paralelo dos itens.
O método stream retorna uma stream de acesso sequencial, ou seja, cada elemento é acessado e processado sequencialmente.
Já o método parallelStream retorna uma stream de acesso paralelo, em que diferentes elementos podem ser acessados simultaneamente, independente da ordem, criando assim várias threads.
Devido a criação de threads e acesso simultâneo, o método parallelStream deve ser usado com cautela, garantindo que o objeto acessado seja thread-safe.
Além disso, deve-se tomar cuidado ao utilizá-lo em ambientes multi-thread, visto que serão criadas mais threads, enfileirando processamentos.
O uso de parallelStream, desde que respeite as regras citadas acima, deve ser utilizado para:
 1. O processamento de muitos itens (desde que a ordem de processamento não seja importante)
 2. Quando o processamento sequencial (método stream) apresentar problemas de performance
