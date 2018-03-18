# Tedros
API JavaFX for desktop app development.

Este projeto foi desenvolvido para ajudar no desenvolvimento de aplicações desktop com JavaFX.
Seu objetivo é disponibilizar aos desenvolvedores uma conjunto completo de componentes em uma 
estrutura pré-definida que facilite seu entendimento e sua responsabilidade. 

Para facilitar o desenvolvimento de telas CRUD e outros tipos de telas comportamentais foi criado
apartir do pattern MVP (Model View Presenter) outros componentes que auxiliam na estruturação, formatação 
e nos comportamentos dos diferentes tipos de campos que uma tela pode conter, tudo baseado em anotações. 

Em breve detalharei melhor todos os patterns utilizados os componentes e como customiza-los.

No momento so adicionarei como configurar o tedros para execução, porem fiquem a vontade para debugar e ver como
funciona.

Abaixo alguns prints:

![Tela de login](https://github.com/DavisGordon/Tedros/blob/master/img/login.png)

![Tela Principal](https://github.com/DavisGordon/Tedros/blob/master/img/tela_principal.png)

![Tela de customização](https://github.com/DavisGordon/Tedros/blob/master/img/tela_configuracap.png)

Configuração:

Para esta versão do tedros é preciso:
- JDK 1.7
- Maven 3.x
- Lib jfxrt versão 1.7.0_80 (encontra-se na pasta zips)
- H2 (Banco de dados) (encontra-se na pasta zips)
- Tomee (Servidor de aplicação) (encontra-se na pasta zips)
- windows (não foi testado no linux e MAC)

1) Faça o clone/download do projeto
2) Na pasta zips:
	a) Descompacte o arquivo usr.zip na raiz do file system. Este arquivo contem o banco de dados h2 ja configurado
	b) Descompacte o arquivo apache-tomee-webprofile-1.7.2.zip em qualquer lugar de sua preferencia.
	c) execute o comando maven abaixo para colocar a lib jfxrt-1.7.0_80.jar no repositorio local do mavem:
	mvn install:install-file -Dfile=jfxrt-1.7.0_80.jar -DgroupId=com.oracle -DartifactId=jfxrt -Dversion=1.7.0_80 -Dpackaging=jar
3) Importe o projeto maven tedrosbox para dentro da IDE, sugiro o eclipse.
4) Altere as properties abaixo nos arquivos pom.xml com o local onde se encontra o jdk e a pasta webapp do tomee acima informado.
	<java.home>C:\Program Files\Java\jdk1.7.0_51</java.home>
	<tomee.webapp>C:\Desenv\Servidores\apache-tomee-webprofile-1.7.2\webapps</tomee.webapp>
	(obs: estas propriedades estão replicadas em alguns arquivos pom.xml, sugiro fazer um search para identificar onde elas foram declaradas, 
	vou corrigir futuramente, esse é o tipico problema usual do copy and paste)
5) Adicione o servidor Tomee na IDE, no eclipse use a opção Apache Tomcat 7.
6) Com o botão direito do mouse sobre o projeto tedrosbox selecione a opção: Run as >Maven build, e execute o Goals: clean install
7) Inicialize o servidor configurado acima pela IDE
8) Com o botão direito do mouse sobre o projeto tedros-global-brasil-ejb-ear selecione a opção: Run as >Maven build, e execute o Goals: tomee:deploy
9) Com o botão direito do mouse sobre o projeto tedros-core-ejb-ear selecione a opção: Run as >Maven build, e execute o Goals: tomee:deploy
10) Abra o arquivo Tedros.java que contem o metodo main.
11) Execute o arquivo Tedros.java como java application.

Se tudo tiver sido configurado de forma correta o Tedros irá descompactar a pasta TedrosBox na pasta do seu usuario com as configurações de layout 
necessarias e logo em seguida será apresentada a tela de login.

Utilize o super usuario "owner". 
A senha pode ser qualquer caracter alphanumerico nesta versão snapshot.

Qualquer problema ou duvida não exite em me contactar pelo e-mail davis.dun@gmail.com

Boa diversão! :]