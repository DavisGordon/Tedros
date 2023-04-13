/**
 * 
 */
package org.tedros.test.ai;

import org.tedros.core.ai.model.completion.chat.TChatMessage;
import org.tedros.core.ai.model.completion.chat.TChatRequest;
import org.tedros.core.ai.model.completion.chat.TChatResult;
import org.tedros.core.ai.model.completion.chat.TChatRole;
import org.tedros.stock.entity.EntryType;
import org.tedros.test.ai.helper.JsonHelper;
import org.tedros.test.ai.model.EntryTypeJson;
import org.tedros.test.serv.OpenAiServ;

/**
 * @author Davis Gordon
 *
 */
public class Insert {

	public static void test() {
		String detail = "Use o modelo json abaixo para realizar uma tarefa de cadastro:\r\n ";
				//+ "respeitando as seguintes regras: \r\n"
				//+ "- Campo name deve conter no maximo 4 caracteres. \r\n"
				//+ "- Campo description deve conter no maximo 15 caracteresm \r\n";

		EntryTypeJson obj = new EntryTypeJson();
		obj.setModel("insert");
		obj.addData(new EntryType());
		String json = JsonHelper.write(obj);
		
		String userPrompt =  "cadastre todas as formas possiveis de um produto entrar no estoque de uma empresa";
		
		TChatMessage s = new TChatMessage(TChatRole.SYSTEM, AiText.INTRO+detail+json);
		TChatMessage m = new TChatMessage(TChatRole.USER, userPrompt+AiText.WARN);
		TChatRequest req = new TChatRequest();
		req.addMessage(s);
		req.addMessage(m);
		req.setTemperature(0.0D);
		TChatResult r = OpenAiServ.chat(req);
		r.getChoices().forEach(c->{
			System.out.println(c.getMessage().getContent());
			System.out.println("-----");
			EntryTypeJson t = JsonHelper.read(c.getMessage().getContent(), EntryTypeJson.class);
			t.getData().size();
		});
	}
}
