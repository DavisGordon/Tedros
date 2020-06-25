package com.tedros.global.brasil.ejb.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;

import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.global.brasil.model.Documento;
import com.tedros.global.brasil.model.Pessoa;

public class AppMain {

	@SuppressWarnings({"unused", "unchecked", "deprecation"})
	public static void main(String[] args) {
		
		Properties p = new Properties();
		//initLocal(p);
		initRemote(p);
		InitialContext ctx;
		
		try {
			
			ctx = new InitialContext(p);
			
			@SuppressWarnings("rawtypes")
			ITEjbService myBean = (ITEjbService<?>) ctx.lookup("TPessoaServiceRemote");
			
			Pessoa pe = new Pessoa();
			
			Documento d = new Documento();
			
			d.setDataEmissao(new Date(2017,01,04));
			
			TResult<List<Pessoa>> l = myBean.findAll(pe);
			
			System.out.println(l.getResult());
			
			
			
			
			
			//TApplicationService myBean = (TApplicationService) ctx.lookup("TPessoaServiceRemote");
			//gerarApps(myBean);
			
			//TProfileService pBean = (TProfileService) ctx.lookup("ProfileServiceRemote");
			//gerarProfiles(myBean, pBean);
			//TResult<List<TCProfile>> lst = pBean.listAll(new TCProfile());
			
			//TPessoaService tPessoaService = (TPessoaService) ctx.lookup("TPessoaServiceRemote");
			
			
			
			
			/*
			Endereco end = new Endereco("GO","Goiania","27250238","1");
			Documento documento = new Documento("12333333","1");
			Contato contato = new Contato("61 88855555", "3");
			
			Endereco end2 = new Endereco("DF","Brasilia","11111","1");
			Documento documento2 = new Documento("111111","1");
			Contato contato2 = new Contato("61 11111111", "3");
			
			Endereco end3 = new Endereco("RJ","Rio de Janeiro","2222222","1");
			Documento documento3 = new Documento("222222","1");
			Contato contato3 = new Contato("61 2222", "3");
			
			Pessoa pessoa = new Pessoa("Samantha Dun", "1");
			
			contato.setPessoa(pessoa);
			documento.setPessoa(pessoa);
			
			contato2.setPessoa(pessoa);
			documento2.setPessoa(pessoa);
			
			contato3.setPessoa(pessoa);
			documento3.setPessoa(pessoa);
			
			
			pessoa.getEnderecos().addAll(Arrays.asList(end, end2, end3));
			pessoa.getContatos().addAll(Arrays.asList(contato,contato2, contato3));
			pessoa.getDocumentos().addAll(Arrays.asList(documento, documento2, documento3));
			
			System.out.println("### >> Salvando pessoa:\n\n");
			System.out.println("Antes:\n"+pessoa);
			TResult<Pessoa> rSave = tPessoaService.save(pessoa);
			System.out.println("\n\n@@@ >> Resultado: "+ rSave.getResult().name());
			
			if(rSave.getValue()!=null){
				System.out.println("Depois:\n"+rSave.getValue());
				
				System.out.println("\n\n******************************************************************\n\n");
				System.out.println("### >> Recuperando pessoa salva com id ("+rSave.getValue().getId()+"):\n\n");
				TResult<Pessoa> r2 = tPessoaService.find(rSave.getValue());
				Pessoa p2 = r2.getValue();
				
				System.out.println("\n\n@@@ >> Resultado: "+ r2.getResult().name());
				System.out.println(p2);
				
				System.out.println("\n\n******************************************************************\n\n");
				System.out.println("### >> Alterando pessoa:");
				System.out.println("Antes:\n"+p2);
				
				Endereco end4 = new Endereco("AA","AAAAA","AAAAAA","1");
				Documento documento4 = new Documento("AAAAA","1");
				Contato contato4 = new Contato("AAAAA", "3");
				
				documento4.setPessoa(p2);
				contato4.setPessoa(p2);
				
				//Object o = null;
				
				//for(Endereco x : p2.getEnderecos()){o = x; break;}
				p2.getEnderecos().clear();
				
				//for(Documento x : p2.getDocumentos()){o = x; break;}
				p2.getDocumentos().clear();
				
				//for(Contato x : p2.getContatos()){o = x; break;}
				p2.getContatos().clear();
								
				p2.getEnderecos().add(end4);
				p2.getDocumentos().add(documento4);
				p2.getContatos().add(contato4);
				
				System.out.println("Depois:\n"+p2);
				
				TResult<Pessoa> r3 = tPessoaService.save(p2);
				System.out.println("\n\n@@@ >> Resultado: "+ r3.getResult().name());
				System.out.println("Depois:\n"+r3.getValue());
			}
			
			
			System.out.println("\n\n******************************************************************\n\n");
			System.out.println("### >> Listando Geral:\n");
			TResult<List<Pessoa>> r = tPessoaService.listAll(Pessoa.class);
			for(Pessoa pe : r.getValue()){
				System.out.println(pe);
			}
			*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	private static void initRemote(Properties p) {
		
		
		String url = "http://{0}:8080/tomee/ejb";
		String ip = "127.0.0.1";
		
		String serviceURL = MessageFormat.format(url, ip);
		
		p.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
		p.put("java.naming.provider.url", serviceURL);
	}


	@SuppressWarnings("unused")
	private static void initLocal(Properties p) {
		p.put("java.naming.factory.initial", "org.apache.openejb.client.LocalInitialContextFactory");
		
		p.put("tedrosDataSource", "new://Resource?type=DataSource");
		p.put("tedrosDataSource.UserName", "tdrs");
		p.put("tedrosDataSource.Password", "xpto");
		p.put("tedrosDataSource.JdbcDriver", "org.h2.Driver");
		p.put("tedrosDataSource.JdbcUrl", "jdbc:h2:file:~/.tedros/data/db;");
		p.put("tedrosDataSource.JtaManaged", "true");
		
		// change some logging
		p.put("log4j.category.OpenEJB.options ", " debug");
		p.put("log4j.category.OpenEJB.startup ", " debug");
		p.put("log4j.category.OpenEJB.startup.config ", " debug");
		
		// set some openejb flags
		p.put("openejb.jndiname.format ", " {ejbName}/{interfaceClass}");
		p.put("openejb.descriptors.output ", " true");
		p.put("openejb.validation.output.level ", " verbose");
	}

	/*private static void gerarProfiles(TApplicationService myBean, TProfileService pBean) {
		@SuppressWarnings("unchecked")
		TResult<List<TCApplication>> result =  myBean.listAll(new TCApplication());
		List<TCApplication> list = result.getValue();
		
		for (TCApplication tcApplication : list) {
			List<TCApplicationModule> modules = tcApplication.getModules();
			TCProfile profile = new TCProfile("Administrador", "bla bla bla");
			profile.setAutorizations(new ArrayList<TCModuleAutorization>());
			for (TCApplicationModule module : modules) {
				profile.getAutorizations().add(new TCModuleAutorization(profile, module));
			}
			pBean.save(profile);
		}
	}
*/
	/*O@SuppressWarnings("unused")
	private static void gerarApps(TApplicationService myBean) {
		TCApplication app = new TCApplication();
		app.setName("Painel de Controle");
		app.setDateInstall(new Date());
		app.setLicence("licence");
		app.setVersion("1.0.0");
		app.setOwnerId(2L);
		app.setOwnerName("davis");
		app.setTedrosId(1L);
		app.setStatus(22);
		
		TCApplicationModule module1 = new TCApplicationModule();
		module1.setName("Customizar");
		module1.setApplication(app);
		TCApplicationModule module2 = new TCApplicationModule();
		module2.setName("Controle de Acesso");
		module2.setApplication(app);
		
		app.setModules(Arrays.asList(module1, module2));
		
		TCApplicationConfig config = new TCApplicationConfig();
		config.setDataLocation("remote");
		config.setApplication(app);
		
		app.setConfig(config);
		
		myBean.save(app);
		
		TCApplication app2 = new TCApplication();
		app2.setName("Dados B�sicos");
		app2.setDateInstall(new Date());
		app2.setLicence("licence");
		app2.setVersion("1.0.0");
		app2.setOwnerId(2L);
		app2.setOwnerName("davis");
		app2.setTedrosId(1L);
		app2.setStatus(22);
		
		TCApplicationModule module3 = new TCApplicationModule();
		module3.setName("Cadastro de Pessoa");
		module3.setApplication(app2);
		TCApplicationModule module4 = new TCApplicationModule();
		module4.setName("Controle de Acesso");
		module4.setApplication(app2);
		
		app2.setModules(Arrays.asList(module3));
		
		TCApplicationConfig config2 = new TCApplicationConfig();
		config2.setDataLocation("remote");
		config2.setApplication(app2);
		
		app2.setConfig(config2);
		
		myBean.save(app2);
	}
*/	
}
