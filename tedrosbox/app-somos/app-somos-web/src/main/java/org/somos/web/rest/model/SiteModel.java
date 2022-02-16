package org.somos.web.rest.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.somos.model.SiteAbout;
import org.somos.model.SiteContato;
import org.somos.model.SiteDoacao;
import org.somos.model.SiteDoacaoTransferencia;
import org.somos.model.SiteEquipe;
import org.somos.model.SiteGaleria;
import org.somos.model.SiteMidiaSocial;
import org.somos.model.SiteNoticia;
import org.somos.model.SiteParceria;
import org.somos.model.SitePontoColeta;
import org.somos.model.SiteSMDoacao;
import org.somos.model.SiteTermo;
import org.somos.model.SiteVideo;
import org.somos.model.SiteVoluntario;

@XmlRootElement(name="site")
public class SiteModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 142693441190203087L;

	@XmlAttribute
	private String descricao;
	
	@XmlAttribute
	private Long image;
	
	@XmlAttribute
	private String link;
	
	@XmlAttribute
	private String valor;
	
	@XmlAttribute
	private String nome;
	
	@XmlAttribute
	private String cargo;
	
	@XmlAttribute
	private String telefone;
	
	@XmlAttribute
	private String whatsapp;
	
	@XmlAttribute
	private String email;
	@XmlAttribute
	private List<SiteModel> itens1;
	@XmlAttribute
	private List<SiteModel> itens2;
	
	public SiteModel(SiteTermo e) {
		this.descricao = e.getDescricao();
	}
	
	public SiteModel(SiteAbout e) {
		this.descricao = e.getDescricao();
		this.image = e.getImage()!=null ? e.getImage().getId() : null;
	}
	
	public SiteModel(SiteParceria e) {
		this.descricao = e.getDescricao();
		this.image = e.getImage()!=null ? e.getImage().getId() : null;
	}
	
	public SiteModel(SiteVoluntario e) {
		this.descricao = e.getDescricao();
		this.image = e.getImage()!=null ? e.getImage().getId() : null;
	}
	
	public SiteModel(SiteDoacaoTransferencia e) {
		this.descricao = e.getDescricao();
		this.image = e.getImage()!=null ? e.getImage().getId() : null;
	}
	
	public SiteModel(SiteSMDoacao e) {
		this.descricao = e.getDescricao();
		this.image = e.getImage()!=null ? e.getImage().getId() : null;
		if(e.getPontosColeta()!=null) {
			this.itens1 = new ArrayList<>();
			e.getPontosColeta().sort(new Comparator<SitePontoColeta>() {
				@Override
				public int compare(SitePontoColeta o1, SitePontoColeta o2) {
					return o1.getOrdem()!=null && o2.getOrdem()!=null 
							? o1.getOrdem().compareTo(o2.getOrdem())
									: 0;
				}
			});
			e.getPontosColeta().forEach(i->{
				SiteModel m = new SiteModel();
				m.setDescricao(i.getDescricao());
				itens1.add(m);
			});
		}
		if(e.getTransferencia()!=null) {
			this.itens2 = new ArrayList<>();
			SiteModel m = new SiteModel(e.getTransferencia());
			this.itens2.add(m);
			
		}
	}
	
	public SiteModel(SiteContato e) {
		this.nome = e.getNome();
		this.cargo = e.getCargo();
		this.telefone = e.getTelefone();
		if(e.getWhatsapp()!=null)
			this.whatsapp = e.getWhatsapp().toString();
		this.email = e.getEmail();
	}
	
	public SiteModel(SiteNoticia e) {
		this.descricao = e.getDescricao();
		this.link = e.getLink();
	}
	
	public SiteModel(SiteVideo e) {
		this.descricao = e.getDescricao();
		this.link = e.getLink();
	}

	public SiteModel(SiteDoacao e) {
		this.descricao = e.getDescricao();
		this.link = e.getLink();
		if(e.getValor()!=null) {
			DecimalFormat df = new DecimalFormat("####.00");
			this.valor = "R$ " + df.format(e.getValor());
		}
	}
	

	public SiteModel() {
		
	}

	public SiteModel(SiteMidiaSocial e) {
		this.nome = e.getNome();
		this.link = e.getLink();
	}

	public SiteModel(SiteEquipe e) {
		this.nome=e.getNome();
		this.cargo = e.getFuncao();
		this.descricao = e.getDescricao();
		this.image = e.getImage()!=null ? e.getImage().getId() :  null;
	}

	public SiteModel(SiteGaleria e) {
		this.descricao = e.getTitulo();
		this.image = e.getImage()!=null ? e.getImage().getId() :  null;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
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
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the whatsapp
	 */
	public String getWhatsapp() {
		return whatsapp;
	}

	/**
	 * @param whatsapp the whatsapp to set
	 */
	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the itens1
	 */
	public List<SiteModel> getItens1() {
		return itens1;
	}

	/**
	 * @param itens1 the itens1 to set
	 */
	public void setItens1(List<SiteModel> itens1) {
		this.itens1 = itens1;
	}

	/**
	 * @return the itens2
	 */
	public List<SiteModel> getItens2() {
		return itens2;
	}

	/**
	 * @param itens2 the itens2 to set
	 */
	public void setItens2(List<SiteModel> itens2) {
		this.itens2 = itens2;
	}
}
