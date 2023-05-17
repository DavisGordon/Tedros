/**
 * 
 */
package org.tedros.test.ai;

import org.tedros.core.ai.model.completion.chat.TChatMessage;
import org.tedros.core.ai.model.completion.chat.TChatRequest;
import org.tedros.core.ai.model.completion.chat.TChatResult;
import org.tedros.core.ai.model.completion.chat.TChatRole;
import org.tedros.core.controller.TProfileController;
import org.tedros.core.security.model.TProfile;
import org.tedros.test.ai.helper.JsonHelper;
import org.tedros.test.ai.helper.ServiceHelper;
import org.tedros.test.ai.model.ProfileJson;
import org.tedros.test.serv.OpenAiServ;

/**
 * @author Davis Gordon
 *
 */
public class CreateProfileTest {

	public static void test() {
		String detail = "Use o modelo json abaixo para realizar uma tarefa de cadastro:\r\n ";
				//+ "respeitando as seguintes regras: \r\n"
				//+ "- Campo name deve conter no maximo 4 caracteres. \r\n"
				//+ "- Campo description deve conter no maximo 15 caracteresm \r\n";
		String custom = "O json abaixo é referente ao perfil 'Master' e ele contem todas "
				+ "as autorizacões sobre os aplicativos, modulos e telas do sistema.\r\n";
		TProfile p = new TProfile();
		p.setName("Master");
		p = ServiceHelper.find(TProfileController.JNDI_NAME, p);
		
		
		ProfileJson obj = new ProfileJson();
		obj.setModel("insert");
		obj.addData(p);
		String json = JsonHelper.write(obj);
		
		String userPrompt =  "Cadastre um perfil com permissões apenas para cadastro de produtos.";
		
		TChatMessage s = new TChatMessage(TChatRole.SYSTEM, AiText.INTRO+detail+custom+json);
		TChatMessage m = new TChatMessage(TChatRole.USER, userPrompt+AiText.WARN);
		TChatRequest req = new TChatRequest();
		req.addMessage(s);
		req.addMessage(m);
		req.setTemperature(0.0D);
		TChatResult r = OpenAiServ.chat(req);
		r.getChoices().forEach(c->{
			System.out.println(c.getMessage().getContent());
			System.out.println("-----");
			ProfileJson t = JsonHelper.read(c.getMessage().getContent(), ProfileJson.class);
			t.getData().size();
		});
	}
}
