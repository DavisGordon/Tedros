# Tedros
|[Português](https://github.com/DavisGordon/Tedros/blob/master/README-pt.md)|

Tedros is a powerful application development framework for the Tedros system. With a wide range of features and advanced technologies, Tedros allows you to create robust, scalable, internationalized, rich and intelligent applications declaratively and quickly, without many complications. Focus on the business core to be developed, Tedros was designed to manage and integrate applications with each other and with artificial intelligence and aiming at this scenario, some base applications were developed to support that can be customized and integrated according to the customer's needs.

**Declaratively**
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

One of the most impressive features of Tedros is its chat with the artificial intelligence called Teros. It is possible to create functions that give access to artificial intelligence, generating countless possibilities for interaction.

1. Ask questions: You can ask questions to obtain information or clarify doubts about various subjects.

2. Get answers: Teros will provide relevant and useful answers to your questions based on knowledge and capabilities of artificial intelligence.

3. Request creation of images: You can ask Teros to create specific images according to your needs.

4. Explore features: Teros can provide you with information about features available in the Tedros system and how to use them.

5. Get technical support: Teros can help troubleshoot technical issues and provide step-by-step guidance to resolve system-related issues.

6. Perform automated tasks: Teros can perform automated tasks such as performing transactions, sending notifications or performing specific actions on the system.

These are just some of the actions you can perform on the Tedros system through chat with the Teros artificial intelligence. Teros is ready to help and provide support in several areas, making interaction with the system more efficient and intuitive.

If the user has a specific question or needs help with a particular feature, just ask Teros the question and he's ready to help!

**Create a function and that's it**
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

Below is a list of features provided by base applications that can be integrated and customized.

1. Theme Tools:
    - Choose a theme for Tedros
    - Change the colors of panels, texts, buttons and forms
    - Change the background image

2. Preferences:
    - Manage key system properties
    - Manage Mime types of files

3. Users:
    - Load all authorization policies
    - Manage user profiles
    - Manage system users

4. Notifications:
    - Manage email notifications (Scheduled, Sent or Queued)

5. Teros (Artificial Intelligence):
    - Describe an image to be created by artificial intelligence
    - Ask questions to artificial intelligence
    - Chat with artificial intelligence

6. Products and Inventory:
    - Manage the product catalog
    - Create product reports
    - Configure product inventory
    - Record entries and exits of products in stock
    - Manage input and output types of products
    - Create inventory reports

7. Support:
    - Manage documents
    - Manage document types
    - Manage document states
    - Manage locations (places, administrative areas, cities and countries)
    - Manage individuals and legal entities
    - Manage types of natural and legal persons
    - Manage situations of individuals and legal entities
    - Manage members and member types
    - Manage member statuses
    - Manage philanthropists and types of philanthropists
    - Manage philanthropist situations
    - Manage volunteers and types of volunteers
    - Manage volunteer situations
    - Manage customers and customer types
    - Manage customer situations
    - Manage categories of people

[Go to the documentation on the Wiki for more information on setting up the development environment and exploring all available functionality.](https://github.com/DavisGordon/Tedros/wiki)

**Login**
![Chatgpt](https://github.com/DavisGordon/Tedros/blob/master/printscreen/tedrosbox.png)

**Main screen with open side menu**
![Chatgpt](https://github.com/DavisGordon/Tedros/blob/master/printscreen/menu.png)

**Teros**
![Chatgpt](https://github.com/DavisGordon/Tedros/blob/master/printscreen/teros3.png)

**Example of a simple view with search and pagination**
![Chatgpt](https://github.com/DavisGordon/Tedros/blob/master/printscreen/producprice.png)

**Example of generated report, logo can be changed in preferences**
![Chatgpt](https://github.com/DavisGordon/Tedros/blob/master/printscreen/pf_rel2.png)


# use case

The Tedros system is in full use by the NGO [Social Movement](http://www.somossocial.org.br)

This NGO started during the coronavirus pandemic Covid-19 that resulted in increased hunger, due to the lockdown, in many vulnerable people and homeless people who depended on food donations from restaurants and ordinary people, it was then that a group of friends started to prepare meals in home and distribute them on the streets. The initiative worked and they grew in volunteers, donations and distributed meals. With this growth came the need to follow a work process that would help them manage actions, donations, entry and exit of products in stock, recruitment and control of volunteers, mailing, terms of membership and information on the web site. With Tedros, a complete solution was developed that met all the needs of the then project called Covid Sem Fome and quickly and gradually the solution was developed without much effort, there were several iterations and changes in the applications and in the system, there were 27 versions in total , but they were all done quickly and without too many complications. When the project was officially registered as an NGO and with a new name, process and website the refactoring of the applications and the website was carried out without much effort and complication and today as I write this **the entire system is in functional and stable without interventions for +1.5 years**.

The NGO's website is integrated with the services at the business layer of the Tedros system applications, which are used by the various NGO employees, each with the appropriate profile and permissions for their role.

Here are some prints of Tedros and applications made for the NGO.

NGO app screen:
![Screen 1](https://github.com/DavisGordon/Tedros/blob/master/printscreen/somos1.png)

![Screen 3](https://github.com/DavisGordon/Tedros/blob/master/printscreen/somos3.png)

![Screen 4](https://github.com/DavisGordon/Tedros/blob/master/printscreen/somos4.png)

![Screen 5](https://github.com/DavisGordon/Tedros/blob/master/printscreen/somos5.png)

![Screen 6](https://github.com/DavisGordon/Tedros/blob/master/printscreen/somos6.png)


# Contact:
Email: tedrosbox@gmail.com
[LinkedIn](https://www.linkedin.com/in/davis-gordon-dun/)
