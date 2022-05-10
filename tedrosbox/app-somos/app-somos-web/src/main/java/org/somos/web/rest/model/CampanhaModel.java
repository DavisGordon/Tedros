/**
 * 
 */
package org.somos.web.rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.somos.model.Campanha;
import org.somos.model.FormaAjuda;
import org.somos.model.Periodo;
import org.somos.web.rest.util.ApiUtils;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement
public class CampanhaModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6746802472398575127L;

	private Long id;
	
	private String titulo;
	
	private String desc;
	
	private List<ValorAjudaModel> valores;
	
	private String meta;
	
	private String angariado;
	
	private String dataFim;
	
	private Long image;

	private List<String> periodos;
	
	private List<FormaAjudaModel> formas;
	
	private Long assIdForma;
	
	private String valor;
	
	private String periodo;
	
	private String associado;
	
	private DetalheAjudaModel detalheAjuda;
	
	public CampanhaModel() {
	}
	
	public CampanhaModel(Campanha e) {
		this.id = e.getId();
		this.titulo =e.getTitulo();
		this.desc = e.getDesc();
		if(e.getValores()!=null) {
			this.valores = new ArrayList<>();
			 e.getValores().stream().sorted().forEach(v->{
				 valores.add(new ValorAjudaModel(v));
			 });
			 valores.sort(null);
		}
		this.meta = e.getMeta();
		this.angariado = e.getAngariado();
		if(e.getDataFim()!=null)
			this.dataFim = ApiUtils.formatDateToView(e.getDataFim());
		if(e.getImage()!=null && e.getImage().getId()!=null)
			this.image = e.getImage().getId();
		if(e.getPeriodos()!=null && !e.getPeriodos().isEmpty()) {
			this.periodos = new ArrayList<>();
			for(Periodo v : e.getPeriodos())
				this.periodos.add(v.getNome());
		}
		if(e.getFormasAjuda()!=null && !e.getFormasAjuda().isEmpty()) {
			this.formas = new ArrayList<>();
			for(FormaAjuda v : e.getFormasAjuda())
				this.formas.add(new FormaAjudaModel(v.getId(), v.getTipo(), v.getDetalhe(), v.getTercerizado()));
		}
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the valores
	 */
	public List<ValorAjudaModel> getValores() {
		return valores;
	}

	/**
	 * @param valores the valores to set
	 */
	public void setValores(List<ValorAjudaModel> valores) {
		this.valores = valores;
	}

	/**
	 * @return the meta
	 */
	public String getMeta() {
		return meta;
	}

	/**
	 * @param meta the meta to set
	 */
	public void setMeta(String meta) {
		this.meta = meta;
	}

	/**
	 * @return the angariado
	 */
	public String getAngariado() {
		return angariado;
	}

	/**
	 * @param angariado the angariado to set
	 */
	public void setAngariado(String angariado) {
		this.angariado = angariado;
	}

	/**
	 * @return the dataFim
	 */
	public String getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return the image
	 */
	public Long getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Long image) {
		this.image = image;
	}

	/**
	 * @return the periodos
	 */
	public List<String> getPeriodos() {
		return periodos;
	}

	/**
	 * @param periodos the periodos to set
	 */
	public void setPeriodos(List<String> periodos) {
		this.periodos = periodos;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the formas
	 */
	public List<FormaAjudaModel> getFormas() {
		return formas;
	}

	/**
	 * @param formas the formas to set
	 */
	public void setFormas(List<FormaAjudaModel> formas) {
		this.formas = formas;
	}

	/**
	 * @return the assIdForma
	 */
	public Long getAssIdForma() {
		return assIdForma;
	}

	/**
	 * @param assIdForma the assIdForma to set
	 */
	public void setAssIdForma(Long assIdForma) {
		this.assIdForma = assIdForma;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the periodo
	 */
	public String getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	/**
	 * @return the associado
	 */
	public String getAssociado() {
		return associado;
	}

	/**
	 * @param associado the associado to set
	 */
	public void setAssociado(String associado) {
		this.associado = associado;
	}

	/**
	 * @return the detalheAjuda
	 */
	public DetalheAjudaModel getDetalheAjuda() {
		return detalheAjuda;
	}

	/**
	 * @param detalheAjuda the detalheAjuda to set
	 */
	public void setDetalheAjuda(DetalheAjudaModel detalheAjuda) {
		this.detalheAjuda = detalheAjuda;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
