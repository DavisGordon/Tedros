# Tedros
## API JavaFX para desenvolvimento de aplicativos desktop.

Tedros é um framework para desenvolvimento de aplicativos desktop em JavaFX, standalone ou clinte servidor.
Sua arquitetura foi projetada visando maior produtividade no desenvolvimento de um aplicativo.

Por exemplo, para se criar a tela CRUD abaixo com listagem de uma entidade simples:

![Tela Editar Cozinha](https://github.com/DavisGordon/Tedros/blob/master/img/ex_simpleview.png)

São necessarias apenas 7 classes com pouquissimo codigo, são elas:

**No backend**
|Classe  |Descrição |
|:---       |:---      |
|Cozinha.java| A entidade de persistencia|
|ICozinhaController.java|Interface de acesso remoto ao serviço EJB|
|CozinhaController.java|Implementação da interface|

**No frontend**
|Classe  |Descrição |
|:---       |:---      |
|CozinhaModelView.java|Aqui é definido atravez de anotações java toda informação necessaria sobre a tela a ser construida para editar a entidade.|
|CozinhaModule.java|Aqui declaramos um modulo para a tela. Um modulo pode conter uma ou mais telas. |
|CozinhaIconImageView.java|Opcional, representa a imagem a ser usada como icone do modulo.|
|AppStart.java|E por ultimo, declaramos o modulo no aplicativo.|

Abaixo alguns prints:

![Tela de login](https://github.com/DavisGordon/Tedros/blob/master/img/login.png)

![Tela Principal](https://github.com/DavisGordon/Tedros/blob/master/img/tela_principal.png)

![Tela de customização](https://github.com/DavisGordon/Tedros/blob/master/img/tela_configuracap.png)

**Configuração:**

Requisitos:
- JDK 1.8
- Maven
- Lib jfxrt versão 1.8.0_261 (encontra-se na pasta zips)
- H2 Database (https://www.h2database.com/html/download.html) ou outro banco de dados.

1. Faça o clone/download do projeto

2. Na pasta zips:
- Faça o download do arquivo jfxrt-1.8.0_261.jar e execute o comando maven abaixo para adiciona-lo no repositorio local: 
- `mvn install:install-file -Dfile=jfxrt-1.8.0_261.jar -DgroupId=com.oracle -DartifactId=jfxrt -Dversion= 1.8.0_261 -Dpackaging=jar`

3. Faça o download do banco de dados, execute o arquivo bin\h2.bat uma tela para conexão sera aberta no browser.
4. Importe o projeto maven tedrosbox.
5. Altere a propriedade abaixo no arquivo pom.xml com o local onde se encontra o jdk .

`<java.home>C:\Program Files\Java\jdk1.8.0_102\bin</java.home>`

5
5. Com o botão direito do mouse sobre o projeto tedrosbox selecione a opção: `Run as >Maven build`, e execute no campo Goals: `clean install`

6. Com o botão direito do mouse sobre o projeto server-application selecione a opção: `Run as >Maven build`, e execute no campo Goals: `cargo:run`

7. Abra e execute o arquivo com.tedros.Main.java como java application.

Se tudo tiver sido configurado de forma correta o Tedros devera criar a pasta .tedros na pasta do seu usuario com as configurações de layout necessarias e em seguida será apresentada a tela de login.

Utilize o super usuario **owner**. 
A senha é **xxx**.

## Entendendo a estrutura do projeto:

1. tedrosbox 
   - tdrs-box 
     - app-tedros-settings
     - tedros-box
     - tedros-box-app-base
     - tedros-core
   - tdrs-fx
     - tedros-fx-component
   - tdrs-global-brasil
     - app-global-brasil
     - tedros-global-brasil-ejb
     - tedros-global-brasil-ejb-client
     - tedros-global-brasil-ejb-ear
     - tedros-global-brasil-model
   - tdrs-miscellaneous
     - tedros-util
   - tdrs-server
     - tedros-core-ejb
     - tedros-core-ejb-client
     - tedros-core-ejb-ear
     - tedros-core-model
     - tedros-ejb-model-base
     - tedros-ejb-service-base
     - tedros-global-model
     
|  Projeto  |Descrição |
|:---       |:---      |
|tedrosbox  |Projeto pai|
|tdrs-box   |Modulo do gerenciador de aplicativos|
|app-tedros-settings|Aplicativo para customização do tedros|
|tedros-box|Projeto pricipal com a classe main Tedros.java|
|tedros-box-app-base|API base para desenvolvimento de um novo aplicativo|
|tedros-core|Projeto core gerenciador do contexto|
|tdrs-fx|Modulo com as APIs JAVAFX customizadas|
|tedros-fx-component|Projeto com as APIs JAVAFX customizadas|
|tdrs-global-brasil|Modulo de exemplo de uma aplicação com backend em EJB.|
|app-global-brasil|Projeto com a camada de visão JAVAFX da aplicação|
|tedros-global-brasil-ejb|Projeto ejb com a camada de serviço, negocio e persistencia da aplicação.|
|tedros-global-brasil-ejb-client|Projeto com as interfaces de serviços a serem utilizadas pela camada de visão|
|tedros-global-brasil-ejb-ear|Projeto ear para ser usado no deploy do servido de aplicações|
|tedros-global-brasil-model|Projeto com as entidades jpa do projeto|
|tdrs-miscellaneous|Modulo de utilitarios|
|tedros-util|Projeto com as classes utilitarias|
|tdrs-server|Modulo base de backend|
|tedros-core-ejb|Projeto com a camada de serviço, negocio e persistencia do tedros-core|
|tedros-core-ejb-client|Projeto com as interfaces de serviços a serem utilizadas pelo tedros-core|
|tedros-core-ejb-ear|Projeto ear para ser usado no deploy do servido de aplicações|
|tedros-core-model|Projeto com as entidades jpa do tedros-core|
|tedros-ejb-model-base|Projeto base a ser usado para implementação de novas entidades jpa.|
|tedros-ejb-service-base|Projeto base a ser usado para implementação de novos serviços|
|tedros-global-model|Projeto com as entidades globais/comuns que podem ser usadas em qualquer projeto.|

## Para desenvolver um novo aplicativo vamos usar o modulo tdrs-global-brasil como exemplo:

Neste primeiro momento não vou entrar em muitos detalhes vou apenas detalhar os pacotes de referencia, prometo melhorar a documentação.

1. **Entendendo os pacotes da camada de visão do projeto app-global-brasil:**

|  Pacote  |Descrição |
|:---       |:---     |
|com.tedros.global.brasil.module.pessoa|pacote com a classe que herda de TModule responsavel por inicializar a view|
|com.tedros.global.brasil.module.pessoa.form|pacote com os forms customizados (não esta sendo usado nesta implementação é so um exemplo)|
|com.tedros.global.brasil.module.pessoa.icon|pacote com os classes que irão exibir os icones da aplicação no menu do tedros box|
|com.tedros.global.brasil.module.pessoa.model|pacote com os model a serem usados para geração dos formularios nas views|
|com.tedros.global.brasil.module.pessoa.process|pacote com os processos a serem usados para comunicação com o backend|
|com.tedros.global.brasil.module.pessoa.table|pacote com os componentes customizados a serem usadas nas TableViews (não esta sendo usado nesta implementação)|
|com.tedros.global.brasil.module.pessoa.trigger|pacote com as triggers a serem executadas em alguns campos da tela|
|com.tedros.global.brasil.module.pessoa.validator|pacote com validadores|
|com.tedros.global.brasil.start|pacote com a classe AppStart.java com a configuração dos modulos e menus a serem apresentados na inicialização do tedros-box.|

2. **Por onde começar:**

Antes dos passos abaixo crie um modulo parecido com o modulo tdrs-global-brasil porem com nomes diferentes.
Obs. Não é preciso criar a camada de backend neste momento.

- Monte uma entidade que implemente ITModel ou herde de TEntity, coloque seus atributos, exemplo classe Pessoa.java, mas nesse momento não precisa ser uma entidade jpa então não se preocupe em colocar as anotações jpa.
- Monte sua classe model view exemplo PessoaModelView.java, esta classe deve conter os atributos da classe que criou acima (os mesmos nomes), com a diferença do tipo delas compare a classe Pessoa.java com a classe PessoaModelView.java. A classe PessoaModelView.class servirá de modelo para geração dos formulariose e para servir de bind entre os componetes de tela javafx, gerados pelas anotações, com os atributos da entidade.

- Monte o seu modulo para tal de uma olhada na classe CadastroDePessoaModule.java

- Monte a sua classe de inicialização, de uma olhada na classe AppStart.java

- Altere o pom.xml do projeto tedros-box e adicione o projeto criado como dependencia.

- Execute o tedros.

PS. Tem muita coisa para documentar ainda por isso vão debugando e questionando, fiquem a vontade.

Qualquer problema ou duvida não exite em me contactar pelo e-mail davis.dun@gmail.com

Boa diversão! :]
