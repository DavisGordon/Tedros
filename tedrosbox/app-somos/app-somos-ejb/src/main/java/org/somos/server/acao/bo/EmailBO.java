/**
 * 
 */
package org.somos.server.acao.bo;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.somos.model.Acao;
import org.somos.model.Contato;
import org.somos.model.Pessoa;
import org.somos.model.TipoAjuda;
import org.somos.model.Voluntario;
import org.somos.server.exception.EmailBusinessException;
import org.somos.server.pessoa.bo.PessoaBO;
import org.somos.server.producer.Item;

import com.tedros.util.TDateUtil;
import com.tedros.util.TFileUtil;
import com.tedros.util.TSMTPUtil;
import com.tedros.util.TSentEmailException;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EmailBO {
	
	private static final String PROJECT_NAME = "Social Movement";
	private static final String PROJECT = "["+PROJECT_NAME+"]";

	@Inject
	private PessoaBO pessBO;
	
	@Inject
	@Named("host")
	private Item<String> host;
	
	@Inject
	@Named("emailSmtpHost")
	private Item<String> smtpHost;

	@Inject
	@Named("emailSmtpPort")
	private Item<String> smtpPort;

	@Inject
	@Named("emailSmtpSocketPort")
	private Item<String> socketPort;
	
	@Inject
	@Named("emailUser")
	private Item<String> emailAccount;
	
	@Inject
	@Named("emailPass")
	private Item<String> passAccount;
	
	@Inject
	@Named("emailTemplatePath")
	private Item<String> templPath;
	
	private TSMTPUtil util;
	
	@PostConstruct
	public void init(){
		util = TSMTPUtil.getInstance(smtpHost.getValue(), socketPort.getValue(), "javax.net.ssl.SSLSocketFactory", 
				"true", smtpPort.getValue(), emailAccount.getValue(), passAccount.getValue());
	}
	
	
	public void enviar(boolean debug, String to, String subject, String content, boolean html) throws TSentEmailException{
		util.sent(debug, emailAccount.getValue(), to, subject, content, html);
	}
	
	public void enviarEmailAjudaCampanha(String titulo, String nome, String contato,
			String valor, String periodo, String forma) throws EmailBusinessException, TSentEmailException {
		String content = "Uma inscrição em uma das campanhas acaba de ser realizado pelo site. "
				+ "<br>Campanha: "+titulo
				+ "<br>Nome: "+(nome!=null?nome:"")
				+ "<br>Contato: "+(contato!=null?contato:"")
				+ "<br>Valor: "+(valor!=null?valor:"")
				+ "<br>Periodo: "+(periodo!=null?periodo:"")
				+ "<br>Forma de ajuda: "+(forma!=null?forma:"");
		
		String to = pessBO.getEnderecoEstrategicoEmail();
		if(to.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		util.sent(false, emailAccount.getValue(), to, PROJECT+" Inscrição em campanha realizado pelo site", content, true);
		
	}
	
	public void enviarEmailCancelarAjudaCampanha(String titulo, String nome, String contato) throws EmailBusinessException, TSentEmailException {
		String content = "Um cancelamento de ajuda em uma das campanhas acaba de ser realizado pelo site. "
				+ "<br>Campanha: "+titulo
				+ "<br>Nome: "+(nome!=null?nome:"")
				+ "<br>Contato: "+(contato!=null?contato:"");
		
		String to = pessBO.getEnderecoEstrategicoEmail();
		if(to.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		util.sent(false, emailAccount.getValue(), to, PROJECT+" Cancelamento de ajuda em campanha realizado pelo site", content, true);
		
	}
	
	public void enviarEmailPropostaAjuda(String empresa, String nome, String contato,
			String tipoAjuda, String desc, String endereco) throws EmailBusinessException, TSentEmailException {
		String content = "Um contato de um possivel parceiro acaba de ser realizado pelo site. "
				+ "<br>Empresa: "+(empresa!=null?empresa:"")
				+ "<br>Nome: "+(nome!=null?nome:"")
				+ "<br>Contato: "+(contato!=null?contato:"")
				+ "<br>Tipo Ajuda: "+(tipoAjuda!=null?tipoAjuda:"")
				+ "<br>Gostaria de ajudar: "+(desc!=null?desc:"")
				+ "<br>Endereço: "+(endereco!=null?endereco:"");
		
		String to = pessBO.getEnderecoEstrategicoEmail();
		if(to.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		util.sent(false, emailAccount.getValue(), to, PROJECT+" Contato de possivel parceiro realizado pelo site", content, true);
		
	}
	
	public void enviarEmailBoasVindas(Pessoa p) throws TSentEmailException, EmailBusinessException{
		String content = gerarMsgBoasVindas(p.getNome());
		
		//Envia email de boas vindas
		util.sent(false, emailAccount.getValue(), p.getLoginName(), "Bem vindo a ONG "+PROJECT_NAME, content, true);
				
	}
	
	public void enviarEmailNewPass(Pessoa p, String key) throws TSentEmailException{
		
		String content = "Olá "+p.getNome()+", para definir uma nova senha clique <a href=\""+host+"defpass.html?k="+key+"\">aqui</a>";
		
		util.sent(false, emailAccount.getValue(), p.getLoginName(), PROJECT+" Definir nova senha", content, true);
				
	}
	
	public void enviarEmailAcoesProgramadasParaDecisao(List<Acao> lst) throws TSentEmailException, EmailBusinessException{
		
		List<Pessoa> pess = pessBO.getListaEstrategicoEmail();
		if(pess==null || pess.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		Calendar c = Calendar.getInstance();
		for(Pessoa p : pess) {
			String to = p.getEmail();
			if(to==null ||  (p.getStatus()!=null && p.getStatus().equals("DESATIVADO")))
				continue;
			
			String content = "As seguintes ações encontram-se programadas para muito em breve e aguardam uma decisão: <br>";
			for(Acao a : lst) {
				content += "Ação "+a.getTitulo()
				+ " programada para "+TDateUtil.getFormatedDate(a.getData(), TDateUtil.DDMMYYYY_HHMM)
				+ "<br>Mudar status para: ";
				if(c.after(a.getData())) {
					content += "<br><a href=\""+host+"api/sm/acao/"+a.getId()+"/prog/2/"+to+"\">Cancelada</a> ou "
							+ "<a href=\""+host+"api/sm/acao/"+a.getId()+"/prog/3/"+to+"\">Executada</a> ";
				}else {
					content += "<br><a href=\""+host+"api/sm/acao/"+a.getId()+"/prog/1/"+to+"\">Agendada</a> ou "
							+ "<a href=\""+host+"api/sm/acao/"+a.getId()+"/prog/2/"+to+"\">Cancelada</a> ";
				}
				content += "<hr>";
			}
			content += "Alterar todas para <a href=\""+host+"api/sm/acao/prog/1/"+to+"\">Agendada</a> ou "
					+ "todas para <a href=\""+host+"api/sm/acao/prog/2/"+to+"\">Cancelada</a> com exceção das "
					+ "que foram alteradas isoladamente.";
		
 
			util.sent(false, emailAccount.getValue(), to, PROJECT+" Avaliar ações com status programadas.", content, true);
		}
	}
	
	public void enviarEmailNovoVoluntario(Pessoa p) throws TSentEmailException, EmailBusinessException{
		
		String cel = "";
		for(Contato c : p.getContatos())
			if(c.getTipo().equals("2"))
				cel = c.getDescricao();
		
		
		String content = "Um novo cadastro acaba de ser realizado pelo site. "
				+ "<br>Nome: "+p.getNome() 
				+ "<br>Email: "+p.getLoginName()
				+ "<br>Cel/Tel: "+cel;
		
		String to = pessBO.getEnderecoEstrategicoEmail();
		if(to.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		util.sent(false, emailAccount.getValue(), to, PROJECT+" Novo cadastro de voluntário realizado pelo site", content, true);
				
	}
	
	public void enviarEmailParticiparAcao(Voluntario v, String termo) throws TSentEmailException, EmailBusinessException{
		
		Pessoa p = v.getPessoa();
		
		String cel = "";
		for(Contato c : p.getContatos())
			if(c.getTipo().equals("2"))
				cel = c.getDescricao();
		
		String tajs = "<ul>";
		for(TipoAjuda taj : v.getTiposAjuda())
			tajs += "<li>"+taj.getDescricao()+"</li>";
		tajs += "</ul>";
		
		
		String content = p.getNome()+" quer participar na ação "+v.getAcao().getTitulo()+" agendado para o dia "
				+ formataDataHora(v.getAcao().getData())+"."
				+ "<br>Email: "+p.getLoginName()
				+ "<br>Cel/Tel: "+cel
				+ "<br>Tipo de ajuda:"+tajs
				+ (termo!=null ? "<hr><br>Termo de adesão:<br><br>" + termo : "");
		
		String to = pessBO.getEnderecoEstrategicoEmail();
		if(to.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		util.sent(false, emailAccount.getValue(), to, PROJECT+" Voluntário para ação "+v.getAcao().getTitulo(), content, true);
				
	}
	
	public void enviarEmailSairAcao(Pessoa p, Acao a) throws TSentEmailException, EmailBusinessException{
		
		String cel = "";
		for(Contato c : p.getContatos())
			if(c.getTipo().equals("2"))
				cel = c.getDescricao();
		
		String content = p.getNome()+" saiu da ação "+a.getTitulo()+" agendado para o dia "
				+ formataDataHora(a.getData())+"."
				+ "<br>Email: "+p.getLoginName()
				+ "<br>Cel/Tel: "+cel;
		
		String to = pessBO.getEnderecoEstrategicoEmail();
		if(to.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		util.sent(false, emailAccount.getValue(), to, PROJECT+" Voluntário saiu da ação "+a.getTitulo(), content, true);
				
	}

	private String gerarMsgBoasVindas(String nome) throws EmailBusinessException {
		
		//File htmlTemplate = new File("C:/usr/covidsemfome/email_boasvindas.html");
		File htmlTemplate = new File(templPath.getValue());
		
		if(!htmlTemplate.isFile())
			throw new EmailBusinessException("TEMPLATE DE EMAIL NAO ENCONTRADO") ;
		
		String msg = TFileUtil.readFile(htmlTemplate);
		msg = msg.replaceAll("#NOME#", nome);
		msg = msg.replaceAll("#HOST#", host.getValue());
		
		return msg;
	}
	
	private String formataDataHora(Date data){
		String pattern = "dd/MM/yyyy 'às' HH:mm";
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(data);
	}
	
	
}
