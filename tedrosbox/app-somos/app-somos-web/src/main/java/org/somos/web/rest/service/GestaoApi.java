package org.somos.web.rest.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.somos.ejb.controller.IAcaoController;
import org.somos.ejb.controller.ICozinhaController;
import org.somos.ejb.controller.IEntradaController;
import org.somos.ejb.controller.IEstoqueConfigController;
import org.somos.ejb.controller.IEstoqueController;
import org.somos.ejb.controller.IPessoaController;
import org.somos.ejb.controller.IProdutoController;
import org.somos.ejb.controller.ISaidaController;
import org.somos.model.Acao;
import org.somos.model.Cozinha;
import org.somos.model.Entrada;
import org.somos.model.EntradaItem;
import org.somos.model.Estoque;
import org.somos.model.EstoqueConfig;
import org.somos.model.EstoqueItem;
import org.somos.model.Pessoa;
import org.somos.model.Produto;
import org.somos.model.Saida;
import org.somos.model.SaidaItem;
import org.somos.web.rest.model.EstocavelItemModel;
import org.somos.web.rest.model.EstocavelModel;
import org.somos.web.rest.model.EstoqueConfigModel;
import org.somos.web.rest.model.EstoqueItemModel;
import org.somos.web.rest.model.EstoqueModel;
import org.somos.web.rest.model.IdNomeModel;
import org.somos.web.rest.model.PessoaModel;
import org.somos.web.rest.model.ProdutoModel;
import org.somos.web.rest.model.RestModel;
import org.somos.web.rest.util.ApiUtils;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
@Path("/tdrs")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class GestaoApi extends LoggedUserBaseApi{
	
	private static final String ACCESS_DENIED = "Você não possui permissão para executar esta operação";

	@EJB
	private IProdutoController prodServ;
	
	@EJB
	private ICozinhaController cozServ;
	
	@EJB
	private IPessoaController pessServ;
	
	@EJB
	private IAcaoController aServ;
	
	@EJB
	private IEntradaController inServ;
	
	@EJB
	private ISaidaController outServ;
	
	@EJB
	private IEstoqueConfigController scServ;
	
	@EJB
	private IEstoqueController stServ;
	
	@POST
	@Path("/prod/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public RestModel<ProdutoModel> saveProd(@FormParam("codigo") String  codigo, 
			@FormParam("nome") String  nome,
			@FormParam("marca") String  marca,
			@FormParam("descricao") String  descricao,
			@FormParam("medida") String  medida,
			@FormParam("unidadeMedida") String  unidadeMedida,
			@FormParam("id") Long  id
			){
	
		try{
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
			 
			Produto p = id==null 
					? new Produto()
							: findProdById(id);
			p.setCodigo(codigo);
			p.setNome(nome);
			p.setMarca(marca);
			p.setDescricao(descricao);
			p.setUnidadeMedida(unidadeMedida);
			p.setMedida(medida);
			
			TResult<Produto> res = prodServ.save(appBean.getToken(), p);
			if(res.getResult().equals(EnumResult.SUCESS)) {
				ProdutoModel m = convert(res.getValue());
				return new RestModel<>(m, "200", "OK");
			}else {
				return new RestModel<>(null, "404", res.getMessage());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	
	@GET
	@Path("/prods")
	public RestModel<List<ProdutoModel>> getProds(){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			
			List<ProdutoModel> lst = listAllProds();
			return new RestModel<>(lst, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}


	/**
	 * @return
	 */
	private List<ProdutoModel> listAllProds() {
		TResult<List<Produto>> res = prodServ.listAll(appBean.getToken(), Produto.class);
		List<ProdutoModel> lst = new ArrayList<>();
		List<Produto> l = res.getValue();
		if(l!=null) 
			for(Produto p : l)
				lst.add(convert(p));
		lst.sort(new Comparator<ProdutoModel>() {
			@Override
			public int compare(ProdutoModel o1, ProdutoModel o2) {
				return o1.getNome().compareToIgnoreCase(o2.getNome());
			}
			
		});
		return lst;
	}


	/**
	 * @param p
	 * @return
	 */
	private ProdutoModel convert(Produto p) {
		return new ProdutoModel(p.getId(), p.getCodigo(), p.getNome(), 
				p.getMarca(), p.getDescricao(), p.getUnidadeMedida(), p.getMedida());
	}
	
	@GET
	@Path("/prod/{id}")
	public RestModel<ProdutoModel> getProd(@PathParam("id") Long id){
				
		try {
			Produto ex = findProdById(id);
			return new RestModel<>(convert(ex), "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}


	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Produto findProdById(Long id) throws RuntimeException {
		try {
			Produto ex = new Produto();
			ex.setId(id);
			TResult<Produto> res = prodServ.findById(this.appBean.getToken(), ex);
			ex = res.getValue();
			return ex;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	@DELETE
	@Path("/prod/del/{id}")
	public RestModel<List<ProdutoModel>> delProd(@PathParam("id") Long id){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			
			Produto p = findProdById(id);
			TResult res = prodServ.remove(appBean.getToken(), p);
			
			if(res.getResult().equals(EnumResult.SUCESS)) {
				List<ProdutoModel> lst = listAllProds();
				return new RestModel<>(lst, "200", "OK");
			}else {
				return new RestModel<>(null, "404", res.getMessage());
			}
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	@GET
	@Path("/coz")
	public RestModel<List<IdNomeModel>> getCozinhas(){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			
			TResult<List<Cozinha>> res = cozServ.listAll(appBean.getToken(), Cozinha.class);
			
			List<IdNomeModel> lst = new ArrayList<>();
			
			if(res.getValue()!=null)
				for(Cozinha e : res.getValue())
					lst.add(new IdNomeModel(e.getId(), e.getNome()));
			
			return new RestModel<>(lst, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/pess/search/{name}")
	public RestModel<List<IdNomeModel>> searchPess(@PathParam("name") String name){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			
			TResult<List<Pessoa>> res = pessServ.pesquisar(appBean.getToken(), name, null, null, null, null); 
			
			List<IdNomeModel> lst = new ArrayList<>();
			
			if(res.getValue()!=null)
				for(Pessoa e : res.getValue())
					lst.add(new IdNomeModel(e.getId(), e.getNome()));
			
			lst.sort(new Comparator<IdNomeModel>() {

				@Override
				public int compare(IdNomeModel o1, IdNomeModel o2) {
					return o1.getNome().compareToIgnoreCase(o2.getNome());
				}
				
			});
			
			return new RestModel<>(lst, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	
	@GET
	@Path("/prod/search/{name}")
	public RestModel<List<IdNomeModel>> searchProd(@PathParam("name") String name){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			TResult<List<Produto>> res = prodServ.pesquisar(appBean.getToken(), null, name, null, null, null, "nome", null); 
			
			List<IdNomeModel> lst = new ArrayList<>();
			
			if(res.getValue()!=null)
				for(Produto e : res.getValue())
					lst.add(new IdNomeModel(e.getId(), e.getNome()));
			
			return new RestModel<>(lst, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	
	@GET
	@Path("/filterIn/{cozId}/{begin}/{end}")
	public RestModel<List<EstocavelModel>> filterIn(@PathParam("cozId") Long cozId, @PathParam("begin") String begin, @PathParam("end") String end){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			
			Date dti = !"x".equals(begin) ? ApiUtils.convertToDate(begin) : null;
			Date dtf = !"x".equals(end) ? ApiUtils.convertToDate(end) : null;
			
			Cozinha coz = cozId>0 ? new Cozinha() : null;
			if(coz!=null)
				coz.setId(cozId);
			
			TResult<List<Entrada>> res = inServ.pesquisar(appBean.getToken(), coz, dti, dtf, null, "data", null);
			List<EstocavelModel> lst = new ArrayList<>();
			
			if(res.getValue()!=null)
				for(Entrada e : res.getValue()) {
					EstocavelModel m = convert(e);
					lst.add(m);
				}
			
			return new RestModel<>(lst, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}


	/**
	 * @param e
	 * @return
	 */
	private EstocavelModel convert(Entrada e) {
		List<EstocavelItemModel> itens = new ArrayList<>();
		for(EntradaItem ei : e.getItens()) {
			itens.add( new EstocavelItemModel(ei.getId(), new IdNomeModel(ei.getProduto().getId(), ei.getProduto().getNome()), 
					ei.getQuantidade(), ei.getValorUnitario(), ei.getUnidadeMedida()));
		}
		IdNomeModel doador =  e.getDoador()!=null 
				? new IdNomeModel(e.getDoador().getId(), e.getDoador().getNome())
						: null;
		EstocavelModel m = new EstocavelModel(e.getId(), new IdNomeModel(e.getCozinha().getId(), e.getCozinha().getNome()), 
				e.getData(), e.getTipo(),doador, itens);
		return m;
	}

	@GET
	@Path("/in/{id}")
	public RestModel<EstocavelModel> getIn(@PathParam("id") Long id){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			Entrada ex = findInById(id);
			return new RestModel<>(convert(ex), "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	
	@DELETE
	@Path("/in/del/{id}")
	public RestModel<String> delIn(@PathParam("id") Long id){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
			
			Entrada e = findInById(id);
			TResult res = inServ.remove(appBean.getToken(), e);
			if(res.getResult().equals(EnumResult.SUCESS)) {
				return new RestModel<>(null, "200", "OK");
			}else {
				return new RestModel<>(null, "404", res.getMessage());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/in/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public RestModel<EstocavelModel> saveIn(EstocavelModel  model){
	
		try{
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			final Entrada ex = model.getId()==null 
					? new Entrada() 
							: this.findInById(model.getId());
					
			ex.setData(ApiUtils.convertToDateTime(model.getData()));
			if(ex.isNew() 
					|| (!ex.isNew() 
							&& !ex.getCozinha().getId().equals(model.getCozinha().getId()))) {
				ex.setCozinha(this.findCozById(model.getCozinha().getId()));
			}
			ex.setTipo(model.getTipo());
			if(model.getTipo().equals("Doação")) {
				ex.setDoador(this.findPessoaById(model.getDoador().getId()));
			}else {
				ex.setDoador(null);
			}
			if(ex.isNew()) {
				List<EntradaItem> l = new ArrayList<>();
				for(EstocavelItemModel i : model.getItens()) {
					EntradaItem ei = new EntradaItem();
					Produto p = this.findProdById(i.getProduto().getId());
					ei.setProduto(p);
					ei.setQuantidade(i.getQuantidade());
					ei.setValorUnitario(i.getValorUnitario());
					ei.setUnidadeMedida(i.getUnidadeMedida());
					l.add(ei);
				}
				ex.setItens(l);
			}else{
				ex.getItens().removeIf(ei ->{
					for(EstocavelItemModel i : model.getItens()) {
						if(i.getId()!=null && ei.getId().equals(i.getId()))
							return false;
					}
					return true;
				}); 
				
				ex.getItens().forEach(ei->{
					for(EstocavelItemModel i : model.getItens()) {
						if(i.getId()!=null && ei.getId().equals(i.getId())) {
							if(!ei.getProduto().getId().equals(i.getProduto().getId()))
								ei.setProduto(findProdById(i.getProduto().getId()));
							ei.setQuantidade(i.getQuantidade());
							ei.setValorUnitario(i.getValorUnitario());
							ei.setUnidadeMedida(i.getUnidadeMedida());
						}
					}
				});
				
				model.getItens().forEach(i->{
					if(i.getId()==null) {
						EntradaItem ei = new EntradaItem();
						Produto p = this.findProdById(i.getProduto().getId());
						ei.setProduto(p);
						ei.setQuantidade(i.getQuantidade());
						ei.setValorUnitario(i.getValorUnitario());
						ei.setUnidadeMedida(i.getUnidadeMedida());
						ex.getItens().add(ei);
					}
				});
			}
			
			TResult<Entrada> res = inServ.save(appBean.getToken(), ex);
			
			if(res.getResult().equals(EnumResult.SUCESS)) {
				Entrada in = res.getValue();
				return new RestModel<>(convert(in), "200", "OK");
			}else {
				return new RestModel<>(null, "404",res.getMessage());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private Entrada findInById(Long id) throws Exception {
		Entrada ex = new Entrada();
		ex.setId(id);
		TResult<Entrada> res = inServ.findById(appBean.getToken(), ex);
		ex = res.getValue();
		return ex;
	}
	
	//acao
	
	@GET
	@Path("/filterAcao/{begin}")
	public RestModel<List<IdNomeModel>> filterAcao(@PathParam("begin") String begin){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			Date dti = !"x".equals(begin) ? ApiUtils.convertToDate(begin) : null;
			
			TResult<List<Acao>> res = aServ.pesquisar(appBean.getToken(), null, null, dti, null, null, "data", null);
			List<IdNomeModel> lst = new ArrayList<>();
			
			if(res.getValue()!=null)
				for(Acao e : res.getValue()) {
					IdNomeModel m = convert(e);
					lst.add(m);
				}
			
			return new RestModel<>(lst, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	
	// saida
	

	@GET
	@Path("/filterOut/{cozId}/{begin}/{end}")
	public RestModel<List<EstocavelModel>> filterOut(@PathParam("cozId") Long cozId, @PathParam("begin") String begin, @PathParam("end") String end){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			Date dti = !"x".equals(begin) ? ApiUtils.convertToDate(begin) : null;
			Date dtf = !"x".equals(end) ? ApiUtils.convertToDate(end) : null;
			
			Cozinha coz = cozId>0 ? new Cozinha() : null;
			if(coz!=null)
				coz.setId(cozId);
			
			TResult<List<Saida>> res = outServ.pesquisar(appBean.getToken(), coz, dti, dtf, null, "data", null);
			List<EstocavelModel> lst = new ArrayList<>();
			
			if(res.getValue()!=null)
				for(Saida e : res.getValue()) {
					EstocavelModel m = convert(e);
					lst.add(m);
				}
			
			return new RestModel<>(lst, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}


	/**
	 * @param e
	 * @return
	 */
	private EstocavelModel convert(Saida e) {
		List<EstocavelItemModel> itens = new ArrayList<>();
		for(SaidaItem ei : e.getItens()) {
			itens.add( new EstocavelItemModel(ei.getId(), new IdNomeModel(ei.getProduto().getId(), ei.getProduto().getNome()), 
					ei.getQuantidade()));
		}
		Acao a = e.getAcao();
		IdNomeModel acao = convert(a);
		EstocavelModel m = new EstocavelModel(e.getId(), new IdNomeModel(e.getCozinha().getId(), e.getCozinha().getNome()), 
				e.getData(), acao, e.getObservacao(), itens);
		return m;
	}


	/**
	 * @param a
	 * @return
	 */
	private IdNomeModel convert(Acao a) {
		IdNomeModel acao =  a!=null 
				? new IdNomeModel(a.getId(), a.getTitulo() + " em "
		+ ApiUtils.formatDateHourToView(a.getData()) + " ("+a.getStatus()+")")
						: null;
		return acao;
	}

	@GET
	@Path("/out/{id}")
	public RestModel<EstocavelModel> getOut(@PathParam("id") Long id){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
			
			Saida ex = findOutById(id);
			return new RestModel<>(convert(ex), "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}

	@DELETE
	@Path("/out/del/{id}")
	public RestModel<String> delOut(@PathParam("id") Long id){
				
		try {
			 if(isAccessDenied())
					return new RestModel<>(null, "404", ACCESS_DENIED);
				
			Saida e = findOutById(id);
			TResult res = outServ.remove(appBean.getToken(), e);
			if(res.getResult().equals(EnumResult.SUCESS)) {
				return new RestModel<>(null, "200", "OK");
			}else {
				return new RestModel<>(null, "404", res.getMessage());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/out/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public RestModel<EstocavelModel> saveOut(EstocavelModel  model){
	
		try{
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			final Saida ex = model.getId()==null 
					? new Saida() 
							: this.findOutById(model.getId());
					
			ex.setData(ApiUtils.convertToDateTime(model.getData()));
			if(ex.isNew() 
					|| (!ex.isNew() 
							&& !ex.getCozinha().getId().equals(model.getCozinha().getId()))) {
				ex.setCozinha(this.findCozById(model.getCozinha().getId()));
			}
			ex.setObservacao(model.getObservacao());
			ex.setAcao(this.findAcaoById(model.getAcao().getId()));
			
			if(ex.isNew()) {
				List<SaidaItem> l = new ArrayList<>();
				for(EstocavelItemModel i : model.getItens()) {
					SaidaItem ei = new SaidaItem();
					Produto p = this.findProdById(i.getProduto().getId());
					ei.setProduto(p);
					ei.setQuantidade(i.getQuantidade());
					l.add(ei);
				}
				ex.setItens(l);
			}else{
				ex.getItens().removeIf(ei ->{
					for(EstocavelItemModel i : model.getItens()) {
						if(i.getId()!=null && ei.getId().equals(i.getId()))
							return false;
					}
					return true;
				}); 
				
				ex.getItens().forEach(ei->{
					for(EstocavelItemModel i : model.getItens()) {
						if(i.getId()!=null && ei.getId().equals(i.getId())) {
							if(!ei.getProduto().getId().equals(i.getProduto().getId()))
								ei.setProduto(findProdById(i.getProduto().getId()));
							ei.setQuantidade(i.getQuantidade());
						}
					}
				});
				
				model.getItens().forEach(i->{
					if(i.getId()==null) {
						SaidaItem ei = new SaidaItem();
						Produto p = this.findProdById(i.getProduto().getId());
						ei.setProduto(p);
						ei.setQuantidade(i.getQuantidade());
						ex.getItens().add(ei);
					}
				});
			}
			
			TResult<Saida> res = outServ.save(appBean.getToken(), ex);
			
			if(res.getResult().equals(EnumResult.SUCESS)) {
				Saida in = res.getValue();
				return new RestModel<>(convert(in), "200", "OK");
			}else {
				return new RestModel<>(null, "404",res.getMessage());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private Saida findOutById(Long id) throws Exception {
		Saida ex = new Saida();
		ex.setId(id);
		TResult<Saida> res = outServ.findById(appBean.getToken(), ex);
		ex = res.getValue();
		return ex;
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private Acao findAcaoById(Long id) throws Exception {
		Acao ex = new Acao();
		ex.setId(id);
		TResult<Acao> res = aServ.findById(appBean.getToken(), ex);
		ex = res.getValue();
		return ex;
	}
	

	@GET
	@Path("/filterSC/{cozId}")
	public RestModel<List<EstoqueConfigModel>> filterSc(@PathParam("cozId") Long cozId){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
			
			Cozinha coz = cozId>0 ? new Cozinha() : null;
			if(coz!=null)
				coz.setId(cozId);
			
			TResult<List<EstoqueConfig>> res = scServ.pesquisar(appBean.getToken(), coz);
			List<EstoqueConfigModel> lst = new ArrayList<>();
			
			if(res.getValue()!=null)
				for(EstoqueConfig e : res.getValue()) {
					EstoqueConfigModel m = convert(e);
					lst.add(m);
				}
			
			return new RestModel<>(lst, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}


	private EstoqueConfigModel convert(EstoqueConfig e) {
		IdNomeModel coz = new IdNomeModel(e.getCozinha().getId(), e.getCozinha().getNome());
		ProdutoModel p = new ProdutoModel(e.getProduto().getId(), e.getProduto().getCodigo(), e.getProduto().getNome(), null, null, null, null);
		return new EstoqueConfigModel(e.getId(), coz, p, e.getQtdMinima(), e.getQtdInicial());
	}

	@GET
	@Path("/sc/{id}")
	public RestModel<EstoqueConfigModel> getSc(@PathParam("id") Long id){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
			
			EstoqueConfig ex = findScById(id);
			return new RestModel<>(convert(ex), "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}

	@DELETE
	@Path("/sc/del/{id}")
	public RestModel<String> delSc(@PathParam("id") Long id){
				
		try {
			 if(isAccessDenied())
					return new RestModel<>(null, "404", ACCESS_DENIED);
				
			EstoqueConfig e = findScById(id);
			TResult res = scServ.remove(appBean.getToken(), e);
			if(res.getResult().equals(EnumResult.SUCESS)) {
				return new RestModel<>(null, "200", "OK");
			}else {
				return new RestModel<>(null, "404", res.getMessage());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/sc/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public RestModel<EstoqueConfigModel> saveSc(EstoqueConfigModel  model){
	
		try{
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			final EstoqueConfig ex = model.getId()==null 
					? new EstoqueConfig() 
							: this.findScById(model.getId());
					
			ex.setCozinha(this.findCozById(model.getCozinha().getId()));
			ex.setProduto(this.findProdById(model.getProduto().getId()));
			ex.setQtdInicial(model.getQtdInicial());
			ex.setQtdMinima(model.getQtdMinima());
			
			TResult<EstoqueConfig> res = scServ.save(appBean.getToken(), ex);
			
			if(res.getResult().equals(EnumResult.SUCESS)) {
				EstoqueConfig in = res.getValue();
				return new RestModel<>(convert(in), "200", "OK");
			}else {
				return new RestModel<>(null, "404",res.getMessage());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private EstoqueConfig findScById(Long id) throws Exception {
		EstoqueConfig ex = new EstoqueConfig();
		ex.setId(id);
		TResult<EstoqueConfig> res = scServ.findById(appBean.getToken(), ex);
		ex = res.getValue();
		return ex;
	}
	
	// estoqueModel
	

	@GET
	@Path("/filterST/{cozId}/{begin}/{end}")
	public RestModel<List<EstoqueModel>> filterSt(@PathParam("cozId") Long cozId, @PathParam("begin") String begin, @PathParam("end") String end){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			Date dti = !"x".equals(begin) ? ApiUtils.convertToDate(begin) : null;
			Date dtf = !"x".equals(end) ? ApiUtils.convertToDate(end) : null;
			
			Cozinha coz = cozId>0 ? new Cozinha() : null;
			if(coz!=null)
				coz.setId(cozId);
			
			TResult<List<Estoque>> res = stServ.pesquisar(appBean.getToken(), null, coz, dti, dtf, null, "data", "desc");
			List<EstoqueModel> lst = new ArrayList<>();
			
			if(res.getValue()!=null)
				for(Estoque e : res.getValue()) {
					EstoqueModel m = convert(e);
					lst.add(m);
				}
			
			return new RestModel<>(lst, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}

	/**
	 * @param e
	 * @return
	 */
	private EstoqueModel convert(Estoque e) {
		List<EstoqueItemModel> itens = new ArrayList<>();
		for(EstoqueItem ei : e.getItens()) {
			itens.add( new EstoqueItemModel(ei.getId(), 
					new ProdutoModel(ei.getProduto().getId(), ei.getProduto().getCodigo(), ei.getProduto().getNome()), 
					ei.getQtdMinima(), ei.getQtdInicial(), ei.getQtdCalculado(), ei.getQtdAjuste()));
		}
		String o;
		if(e.getEntradaRef()!=null) {
			Entrada in = e.getEntradaRef();
			o = "Entrada de produtos em "+ApiUtils.formatDateHourToView(in.getData())
			+" do tipo "+in.getTipo()+(in.getDoador()==null?"":" realizado por "+in.getDoador().getNome());
		}else {
			Saida out = e.getSaidaRef();
			o = "Saída de produtos em "+ApiUtils.formatDateHourToView(out.getData())
					+ " registrada para a ação "+out.getAcao().getTitulo()+" realizada no dia "+ApiUtils.formatDateHourToView(out.getAcao().getData());
			
		
		}
		
		EstoqueModel m = new EstoqueModel(e.getId(), new IdNomeModel(e.getCozinha().getId(), e.getCozinha().getNome()), o, 
				e.getData(), e.getObservacao(), itens);
		return m;
	}

	@GET
	@Path("/st/{id}")
	public RestModel<EstoqueModel> getSt(@PathParam("id") Long id){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
			
			Estoque ex = findStById(id);
			return new RestModel<>(convert(ex), "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/st/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public RestModel<EstoqueModel> saveSt(EstoqueModel  model){
	
		try{
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			final Estoque ex =  this.findStById(model.getId());
			ex.setObservacao(model.getObservacao());
			model.getItens().forEach(i->{
				if(i.getId()!=null) {
					for(EstoqueItem ei : ex.getItens()) {
						if( ei.getId().equals(i.getId())) {
							ei.setQtdAjuste(i.getQtdAjuste());
						}
					}
				}
			});
			
			TResult<Estoque> res = stServ.save(appBean.getToken(), ex);
			
			if(res.getResult().equals(EnumResult.SUCESS)) {
				Estoque st = res.getValue();
				return new RestModel<>(convert(st), "200", "OK");
			}else {
				return new RestModel<>(null, "404",res.getMessage());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private Estoque findStById(Long id) throws Exception {
		Estoque ex = new Estoque();
		ex.setId(id);
		TResult<Estoque> res = stServ.findById(appBean.getToken(), ex);
		ex = res.getValue();
		return ex;
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private Cozinha findCozById(Long id) throws Exception {
		Cozinha ex = new Cozinha();
		ex.setId(id);
		TResult<Cozinha> res = cozServ.findById(appBean.getToken(), ex);
		ex = res.getValue();
		return ex;
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private Pessoa findPessoaById(Long id) throws Exception {
		Pessoa ex = new Pessoa();
		ex.setId(id);
		TResult<Pessoa> res = pessServ.findById(appBean.getToken(), ex);
		ex = res.getValue();
		return ex;
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/filterPess/{nome}/{status}/{begin}/{end}")
	public RestModel<List<PessoaModel>> filterPess(@PathParam("nome") String nome, 
			@PathParam("status") String status, 
			@PathParam("begin") String begin, 
			@PathParam("end") String end){
				
		try {
			if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
			
			Date dti = !"x".equals(begin) ? ApiUtils.convertToDate(begin) : null;
			Date dtf = !"x".equals(end) ? ApiUtils.convertToDate(end) : null;
				
			nome = "list_all".equals(nome) ? null : nome;
			status = "list_all".equals(status) ? null : status;
			 
			TResult<List<Pessoa>> res = pessServ.pesquisar(appBean.getToken(), nome, status, dti, dtf);
			List<PessoaModel> lst = new ArrayList<>();
			
			if(res.getValue()!=null)
				for(Pessoa e : res.getValue()) {
					lst.add(convert(e));
				}
			
			return new RestModel<>(lst, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}


	private PessoaModel convert(Pessoa e) {
		return new PessoaModel(e);
	}

	@GET
	@Path("/pess/{id}")
	public RestModel<PessoaModel> getPess(@PathParam("id") Long id){
				
		try {
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
			
			Pessoa ex = findPessoaById(id);
			return new RestModel<>(convert(ex), "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/pess/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public RestModel<PessoaModel> savePess(PessoaModel  model){
	
		try{
			 if(isAccessDenied())
				return new RestModel<>(null, "404", ACCESS_DENIED);
				
			final Pessoa ex = model.getId()==null 
					? new Pessoa() 
							: this.findPessoaById(model.getId());
					
			ex.setStatusVoluntario(model.getStatus());
			ex.setObservacao(model.getObservacao());
			
			TResult<Pessoa> res = pessServ.save(appBean.getToken(), ex);
			
			if(res.getResult().equals(EnumResult.SUCESS)) {
				Pessoa p = res.getValue();
				return new RestModel<>(convert(p), "200", "OK");
			}else {
				return new RestModel<>(null, "404",res.getMessage());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
}
