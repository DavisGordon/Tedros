package com.tedros.test;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import com.tedros.ejb.base.entity.TEntity;

public class TCProfile extends TEntity {

	private static final long serialVersionUID = -5789123411402469912L;
	
	private Long id;
	private String name;
	private String description;
	private String lastName;
	private String surName;
	private String endereco;
	private String senha;
	private Boolean ativar;
	private Double quantidade;
	private Integer campoInteger;
	private BigInteger campoBigInteger;
	private BigDecimal campoBigDecimal;
	private TCProfile perfil;
	private Date data;	
	private Set<TCProfile> profiles;
	private String sexo;
	private Double campoSlider;
	private Double campoNumberSpin;
	
	
	public TCProfile() {
		
	}
	
	public TCProfile(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public TCProfile(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public TCProfile(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Boolean getAtivar() {
		return ativar;
	}

	public void setAtivar(Boolean ativar) {
		this.ativar = ativar;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public TCProfile getPerfil() {
		return perfil;
	}

	public void setPerfil(TCProfile perfil) {
		this.perfil = perfil;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		String str = "";
		try {
			
			Method[] metodos = this.getClass().getDeclaredMethods();
			for (Method method : metodos) {
				
				if(method.getName().indexOf("Profiles")!=-1 || method.getName().indexOf("Perfil")!=-1){
					continue;
				}
				
				if(method.getName().indexOf("get")!=-1){
					str += method.invoke(this)+"; ";
				}
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(str);
		
		return (getName()!=null)? getName() : "";
		//return str;
	}

	public Set<TCProfile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<TCProfile> profiles) {
		this.profiles = profiles;
	}

	public Integer getCampoInteger() {
		return campoInteger;
	}

	public void setCampoInteger(Integer campoInteger) {
		this.campoInteger = campoInteger;
	}

	public BigInteger getCampoBigInteger() {
		return campoBigInteger;
	}

	public void setCampoBigInteger(BigInteger campoBigInteger) {
		this.campoBigInteger = campoBigInteger;
	}

	public BigDecimal getCampoBigDecimal() {
		return campoBigDecimal;
	}

	public void setCampoBigDecimal(BigDecimal campoBigDecimal) {
		this.campoBigDecimal = campoBigDecimal;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Double getCampoSlider() {
		return campoSlider;
	}

	public void setCampoSlider(Double campoSlider) {
		this.campoSlider = campoSlider;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Double getCampoNumberSpin() {
		return campoNumberSpin;
	}

	public void setCampoNumberSpin(Double campoNumberSpin) {
		this.campoNumberSpin = campoNumberSpin;
	}
	
	
}
