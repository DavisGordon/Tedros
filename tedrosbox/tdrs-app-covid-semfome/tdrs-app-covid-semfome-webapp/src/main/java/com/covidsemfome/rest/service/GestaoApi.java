package com.covidsemfome.rest.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.covidsemfome.ejb.controller.ICozinhaController;
import com.covidsemfome.ejb.controller.IEntradaController;
import com.covidsemfome.ejb.controller.IPessoaController;
import com.covidsemfome.ejb.controller.IProdutoController;
import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.Entrada;
import com.covidsemfome.model.EntradaItem;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Produto;
import com.covidsemfome.rest.model.EstocavelItemModel;
import com.covidsemfome.rest.model.EstocavelModel;
import com.covidsemfome.rest.model.IdNomeModel;
import com.covidsemfome.rest.model.ProdutoModel;
import com.covidsemfome.rest.model.RestModel;
import com.covidsemfome.rest.util.ApiUtils;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

import br.com.covidsemfome.bean.AppBean;
import br.com.covidsemfome.bean.CovidUserBean;
import br.com.covidsemfome.producer.Item;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
@Path("/tdrs")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class GestaoApi {
	
	@Inject
	@Named("errorMsg")
	private Item<String> error;
	
	@Inject @Any
	private CovidUserBean covidUserBean;
	
	@Inject
	private AppBean appBean;

	@EJB
	private IProdutoController prodServ;
	
	@EJB
	private ICozinhaController cozServ;
	
	@EJB
	private IPessoaController pessServ;
	
	@EJB
	private IEntradaController inServ;
	
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
			//Pessoa p = covidUserBean.getUser().getPessoa();
			
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
	
	
}
