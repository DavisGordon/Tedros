/**
 * 
 */
package org.tedros.test.ai;

import java.util.List;

import org.tedros.core.ai.model.completion.chat.TChatMessage;
import org.tedros.core.ai.model.completion.chat.TChatRequest;
import org.tedros.core.ai.model.completion.chat.TChatResult;
import org.tedros.core.ai.model.completion.chat.TChatRole;
import org.tedros.core.controller.TProfileController;
import org.tedros.core.security.model.TProfile;
import org.tedros.ejb.controller.ICountryController;
import org.tedros.location.model.Country;
import org.tedros.test.ai.helper.JsonHelper;
import org.tedros.test.ai.helper.ServiceHelper;
import org.tedros.test.ai.model.CountryJson;
import org.tedros.test.ai.model.EntryTypeJson;
import org.tedros.test.ai.model.ProfileJson;
import org.tedros.test.serv.OpenAiServ;

/**
 * @author Davis Gordon
 *
 */
public class CountryTest {

	public static void test() {
		String detail = "Use o modelo json abaixo para realizar uma tarefa de analise:\r\n ";
				//+ "respeitando as seguintes regras: \r\n"
				//+ "- Campo name deve conter no maximo 4 caracteres. \r\n"
				//+ "- Campo description deve conter no maximo 15 caracteresm \r\n";
		String custom = "O json abaixo contem dados de todos os paises do mundo\r\n";
		
		List<Country> l = ServiceHelper.listAll(ICountryController.JNDI_NAME, Country.class);
		CountryJson obj = new CountryJson();
		obj.setModel("insert");
		obj.setData(l);
		String json = JsonHelper.write(obj);
		
		String userPrompt =  "Retorne os paises europeus";
		
		TChatMessage s = new TChatMessage(TChatRole.SYSTEM, AiText.INTRO+detail+custom+json);
		TChatMessage m = new TChatMessage(TChatRole.USER, userPrompt+AiText.WARN);
		TChatRequest req = new TChatRequest();
		req.addMessage(s);
		req.addMessage(m);
		req.setTemperature(0.0D);
		req.setMaxTokens(4096);
		TChatResult r = OpenAiServ.chat(req);
		r.getChoices().forEach(c->{
			System.out.println(c.getMessage().getContent());
			System.out.println("-----");
			CountryJson t = JsonHelper.read(c.getMessage().getContent(), CountryJson.class);
			t.getData().size();
		});
	}
}
