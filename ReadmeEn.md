# Tedros
## JavaFX API for desktop app development.

This project was started in 2013 to participate in the biggest startup event in Brazil that year, Desafio Brasil 2013, sponsored by FGV (Fundação Getulio Vargas), among the more than 5000 projects we were among the best reaching the semi-final.

Its idea is to help in the development of desktop applications with JavaFX, providing developers with a complete set of components in a pre-defined structure that facilitates development.

To facilitate the development of CRUD screens and other types of behavioral screens, a set of components was created to help structure what should be displayed in the view using annotations.

Soon I will detail all the components and how to customize them.

At the moment only the how-to configuration of the tedros for execution has been added, feel free to debug and see how it works.

Below some prints:

![Login screen](https://github.com/DavisGordon/Tedros/blob/master/img/login.png)

![Main Screen](https://github.com/DavisGordon/Tedros/blob/master/img/tela_principal.png)

![Customization screen](https://github.com/DavisGordon/Tedros/blob/master/img/tela_configuracap.png)

**Configuration:**

For this version of tedros you need:
- JDK 1.7
- Maven 3.x
- Lib jfxrt version 1.7.0_80 (found in the zips folder)
- H2 (Database) (found in the zips folder)
- Tomee (Application server) (found in the zips folder)
- windows (not tested on linux and MAC)

1. Clone / download the project

2. In the zips folder:
- Unzip the usr.zip file at the root of the file system. This file contains the h2 database already configured
- Unzip the apache-tomee-webprofile-1.7.2.zip file anywhere you like and run the maven command below to place the lib jfxrt-1.7.0_80.jar in the local repository of the mavem:
- `mvn install: install-file -Dfile = jfxrt-1.7.0_80.jar -DgroupId = com.oracle -DartifactId = jfxrt -Dversion = 1.7.0_80 -Dpackaging = jar`

3. Import the maven tedrosbox project into the IDE, I suggest eclipse.

4. Change the properties below in the pom.xml files with the location of the jdk and the tomee webapp folder above.

`<java.home>C:\Program Files\Java\jdk1.7.0_51</java.home>`

`<tomee.webapp>C:\Develop\Servers\apache-tomee-webprofile-1.7.2\webapps</tomee.webapp>`

(note: these properties are replicated in some pom.xml files, I suggest doing a search to identify where they were declared, I will correct them in the future, this is the typical usual copy and paste problem)

5. Add the Tomee server to the IDE, in eclipse use the Apache Tomcat 7 option.

6. With the right mouse button on the tedrosbox project select the option: `Run as> Maven build`, and execute the goal` clean install` in the Goals field.

7. Boot the server configured above by the IDE

8. With the right mouse button on the tedros-global-brasil-ejb-ear project select the option: `Run as> Maven build`, and execute the goal` tomee:deploy` in the Goals field.

9. With the right mouse button on the tedros-core-ejb-ear project select the option: `Run as> Maven build`, and run in the Goals field the goal` tomee:deploy`

10. Open the Tedros.java file that contains the main method.

11. Run the Tedros.java file as a java application.

If everything has been configured correctly, Tedros will unzip the folder TedrosBox in your user's folder with the necessary layout settings and then the login screen will be displayed.

Use the super user **owner**.
The password can be any alphanumeric character in this snapshot version.

## Understanding the project structure:

1. tedrosbox
   - tdrs-box
     - app-tedros-settings
     - tedros-box
     - tedros-box-app-base
     - tedros-core
   - tdrs-fx
     - tedros-fx-component
   - tdrs-global-brasil
     - app-global-brazil
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
     
| Project |Description |
|:---       |:---      |
| tedrosbox | Parent project |
| tdrs-box | Application manager module |
| app-tedros-settings | Application for customizing tedros |
| tedros-box | Main project with the main class Tedros.java |
| tedros-box-app-base | Base API for developing a new application |
| tedros-core | Core project context manager |
| tdrs-fx | Module with customized JAVAFX APIs |
| tedros-fx-component | Design with customized JAVAFX APIs |
| tdrs-global-brasil | Example module of an application with backend in EJB. |
| app-global-brasil | Project with the application's JAVAFX view layer |
| tedros-global-brasil-ejb | Ejb project with the service, business and application persistence layer. |
| tedros-global-brasil-ejb-client | Project with the service interfaces to be used by the vision layer |
| tedros-global-brasil-ejb-ear | Ear project to be used in deploying the application server |
| tedros-global-brasil-model | Project with the project's jpa entities |
| tdrs-miscellaneous | Utility module |
| tedros-util | Project with utilitarian classes |
| tdrs-server | Base backend module |
| tedros-core-ejb | Project with the service, business and persistence layer of tedros-core |
| tedros-core-ejb-client | Project with the service interfaces to be used by tedros-core |
| tedros-core-ejb-ear | Ear project to be used in deploying the application server |
| tedros-core-model | Project with tedros-core jpa entities |
| tedros-ejb-model-base | Base project to be used for the implementation of new jpa entities. |
| tedros-ejb-service-base | Base project to be used to implement new services |
| tedros-global-model | Project with global / common entities that can be used in any project. |

## To develop a new application we will use the module tdrs-global-brasil as an example:

In this first moment I will not go into many details, I will only detail the reference packages, I promise to improve the documentation.

1. **Understanding the app-global-brasil project vision layer packages:**

| Package | Description |
|:---       |:---     |
|com.tedros.global.brasil.module.pessoa|package with the class that inherits from TModule responsible for initializing the view|
|com.tedros.global.brasil.module.pessoa.form|package with custom forms (not being used in this implementation is just an example)|
|com.tedros.global.brasil.module.pessoa.icon|package with classes that will display application icons in the tedros box menu|
|com.tedros.global.brasil.module.pessoa.model|package with models to be used to generate forms in views|
|com.tedros.global.brasil.module.pessoa.process|package with the processes to be used for communication with the backend|
|com.tedros.global.brasil.module.pessoa.table|package with customized components to be used in TableViews (not being used in this implementation)|
|com.tedros.global.brasil.module.pessoa.trigger|package with triggers to be executed in some fields of the screen|
|com.tedros.global.brasil.module.pessoa.validator|package with validators|
|com.tedros.global.brasil.start|package with the AppStart.java class with the configuration of the modules and menus to be presented when tedros-box starts.|

2. **Where to start:**

Before the steps below create a module similar to the tdrs-global-brasil module but with different names.
Note It is not necessary to create the backend layer at this time.

- Set up an entity that implements ITModel or inherits from TEntity, put its attributes, example Pessoa.java class, but at this point it doesn't have to be a jpa entity so don't worry about putting jpa annotations.
- Build your model view class example PessoaModelView.java, this class must contain the attributes of the class you created above (the same names), with the difference of their type compare the Pessoa.java class with the PessoaModelView.java class. The PessoaModelView.class class will serve as a model for the generation of forms and to serve as a bind between the javafx screen components, generated by the annotations, with the entity's attributes.

- Assemble your module for such a look at the class CadastroDePessoaModule.java

- Build your startup class, take a look at the AppStart.java class

- Change the pom.xml of the tedros-box project and add the project created as a dependency.

- Run the tedros.

PS. There is a lot to document yet so go debugging and questioning, feel free.

Any problems or doubts do not hesitate to contact me by e-mail davis.dun@gmail.com

Have fun! :]
