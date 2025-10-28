
# Tedros

O Tedros é um poderoso framework de desenvolvimento de aplicativos para o sistema Tedros. Com uma ampla gama de funcionalidades e tecnologias avançadas, o Tedros permite criar aplicativos robustos, escaláveis, internacionalizados, ricas e inteligentes de forma declarativa e rápida, sem muitas complicações. Foque no business core a ser desenvolvido o Tedros foi desenhado para gerenciar e integrar os aplicativos entre si e com a inteligencia artificial e visando este cenário foi desenvolvido alguns aplicativos base para dar suporte que podem ser customizados e integrados de acordo com a necessidade do cliente.


**Declarativo**
```java
@TSelectionModalPresenter(allowsMultipleSelections = true, 
	page = @TPage(modelView=ChatUserMV.class, 
		serviceName = IChatUserController.JNDI_NAME, query = 
		@TQuery(entity=ChatUser.class)),
	tableView = @TTableView(columns = { 
		@TTableColumn(text = TUsualKey.NAME, cellValue="name")}))
public class ChatUserMV extends TEntityModelView<ChatUser> {

	@TLabel(text=TUsualKey.NAME)
	@TTextField
	private SimpleStringProperty name;
	
	private SimpleStringProperty profiles;
	
	public ChatUserMV(ChatUser entity) {
		super(entity);
		super.formatToString("%s", name);
	}
}
```

Uma das características mais impressionantes do Tedros é o seu chat com a inteligência artificial chamado Teros. É possível criar funções que dão acesso à inteligência artificial, gerando inúmeras possibilidades de interação.

1. Fazer perguntas: Você pode fazer perguntas para obter informações ou esclarecer dúvidas sobre diversos assuntos.

2. Obter respostas: O Teros fornecerá respostas relevantes e úteis para as suas perguntas, com base no conhecimento e nas capacidades da inteligência artificial.

3. Solicitar criação de imagens: É possível pedir ao Teros para criar imagens específicas de acordo com as suas necessidades.

4. Explorar funcionalidades: O Teros pode fornecer informações sobre as funcionalidades disponíveis no sistema Tedros e como utilizá-las.

5. Obter suporte técnico: O Teros pode ajudar a solucionar problemas técnicos e fornecer orientações passo a passo para resolver questões relacionadas ao sistema.

6. Realizar tarefas automatizadas: O Teros pode executar tarefas automatizadas, como realizar transações, enviar notificações ou executar ações específicas no sistema.

Essas são apenas algumas das ações que você pode realizar no sistema Tedros através do chat com a inteligência artificial Teros. O Teros está pronto para ajudar e fornecer suporte em diversas áreas, tornando a interação com o sistema mais eficiente e intuitiva.

Se o usuário tiver alguma dúvida específica ou precisar de ajuda com alguma funcionalidade em particular, basta fazer a pergunta ao Teros e ele estará pronto para ajudar!

**Crie uma função e pronto**
```java
/**
 * This function provides data on product prices 
 * to artificial intelligence
 * 
 * @author Davis Gordon
 *
 */
public class ListProductPriceAiFunction extends TFunction<Empty> {

	public ListProductPriceAiFunction() {
		super("list_products_price", "Lists all products price", Empty.class, 
			v->{
				ServiceLocator loc = ServiceLocator.getInstance();
				try {
					IProductPriceController serv = loc.lookup(IProductPriceController.JNDI_NAME);
					TResult<List<ProductPrice>> res = serv
						.listAll(TedrosContext.getLoggedUser().getAccessToken(), ProductPrice.class);
					
					if(res.getState().equals(TState.SUCCESS) && !res.getValue().isEmpty()) {
						List<Price> lst = new ArrayList<>();
						res.getValue().forEach(p-> lst.add(new Price(p)));
						return new Response("The products price list", lst);
					}
				} catch (NamingException e) {
					e.printStackTrace();
				}finally {
					loc.close();
				}
				
				return new Response("No data found");
		});
	}
}
```

Abaixo segue lista de funcionalidades disponibilizadas pelos aplicativos base e que podem ser integrados e customizados.

1. Ferramentas de Temas:
   - Escolher um tema para o Tedros
   - Alterar as cores dos painéis, textos, botões e formulários
   - Alterar a imagem de fundo

2. Preferências:
   - Gerenciar as propriedades principais do sistema
   - Gerenciar os tipos de Mime dos arquivos

3. Usuários:
   - Carregar todas as políticas de autorização
   - Gerenciar perfis de usuário
   - Gerenciar usuários do sistema

4. Notificações:
   - Gerenciar notificações por e-mail (Agendadas, Enviadas ou na Fila)

5. Teros (Inteligência Artificial):
   - Descrever uma imagem para ser criada pela inteligência artificial
   - Fazer perguntas para a inteligência artificial
   - Conversar com a inteligência artificial

6. Produtos e Inventário:
   - Gerenciar o catálogo de produtos
   - Criar relatórios de produtos
   - Configurar o estoque de produtos
   - Registrar entradas e saídas de produtos no estoque
   - Gerenciar tipos de entrada e saída de produtos
   - Criar relatórios de inventário

7. Suporte:
   - Gerenciar documentos
   - Gerenciar tipos de documentos
   - Gerenciar estados de documentos
   - Gerenciar locais (lugares, áreas administrativas, cidades e países)
   - Gerenciar pessoas físicas e jurídicas
   - Gerenciar tipos de pessoas físicas e jurídicas
   - Gerenciar situações de pessoas físicas e jurídicas
   - Gerenciar membros e tipos de membros
   - Gerenciar situações de membros
   - Gerenciar filantropos e tipos de filantropos
   - Gerenciar situações de filantropos
   - Gerenciar voluntários e tipos de voluntários
   - Gerenciar situações de voluntários
   - Gerenciar clientes e tipos de clientes
   - Gerenciar situações de clientes
   - Gerenciar categorias de pessoas

[Acesse a documentação na Wiki para obter mais informações sobre como configurar o ambiente de desenvolvimento e explorar todas as funcionalidades disponíveis.](https://github.com/Tedros-Box/tedros-apps/wiki) 

**Log in**
![Chatgpt](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/tedrosbox.png)

**Tela pricipal com menu lateral aberto**
![Chatgpt](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/menu.png)

**Teros**
![Chatgpt](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/teros3.png)

**Exemplo de uma view simples com pesquisa e paginacão**
![Chatgpt](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/producprice.png)

**Exemplo de relatório gerado, o logotipo pode ser alterado nas preferencias**
![Chatgpt](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/pf_rel2.png)


# Use Case

O sistema Tedros esta em plena utilização pela ONG [Social Movement](http://www.somossocial.org.br)

Esta ONG começou durante a pandemia do Covid-19 que resultou no aumento da fome,  por conta do  lock-down, em muitas pessoas vulneráveis e moradores de rua que dependiam das doações de comida de restaurantes e de pessoas comuns, foi então que um grupo de amigos começaram a preparar refeições em casa e distribui-las nas ruas. A iniciativa deu certo e eles cresceram em voluntários, doações e refeições distribuídas. Com este crescimento veio a necessidade de seguir um processo de trabalho que os ajudassem no gerenciamento das ações, doações, entrada e saída de produtos em estoque, recrutamento e controle dos voluntários, mailing, termos de adesão e das informações do web site. Com o Tedros foi desenvolvida uma solução completa que atendesse a todas as necessidades do então projeto chamado Covid Sem Fome e de forma rápida e gradual a solução foi desenvolvida sem muito esforço, ouve varias iterações e mudanças nos aplicativos e no sistema foram 27 versões no total, mas todas foram realizadas de forma rápida e sem muitas complicações. Quando o projeto foi registrado oficialmente como uma ONG e com um novo nome, processo e web site a refatoração dos aplicativos e do web site foi realizado sem muito esforço e complicação e hoje no momento em que escrevo isto **todo o sistema  encontra-se funcional e estável sem intervenções a +1,5 anos**.

O web site da ONG esta integrado aos serviços na camada  de negócios dos aplicativos do  sistema Tedros que é utilizado pelos diversos colaboradores da ONG, cada um com o perfil e permissões adequados a sua atribuição. 

Segue alguns prints do Tedros e aplicativos feitos para a ONG.

Tela do aplicativo da ONG:
![Tela 1](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/somos1.png)

![Tela 3](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/somos3.png)

![Tela 4](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/somos4.png)

![Tela 5](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/somos5.png)

![Tela 6](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/somos6.png)


# Contato
Email: tedrosbox@gmail.com
[LinkedIn](https://www.linkedin.com/in/davis-gordon-dun/)
