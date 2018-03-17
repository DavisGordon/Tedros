package com.tedros.app.config.impl;

import com.tedros.app.config.ITAppPageClass;

public class TAppPageClass implements ITAppPageClass {

	@SuppressWarnings("rawtypes")
	private Class clazz;
	
	@SuppressWarnings("rawtypes")
	public TAppPageClass(Class moduleClass) {
		
		if(null==moduleClass)
			throw new IllegalArgumentException("Classe precisa ser diferente de null");
		
		if(!moduleClass.getName().contains("modules."))
			throw new IllegalArgumentException("O pacote da classe deve conter um diretorio 'modules'. Ex: com.minha.empresa.modules.nomeDoModulo.NomeDaClasse.class");
		
		final String[] arr = moduleClass.getName().split("modules.");
		
		if(arr.length>2)
			throw new IllegalArgumentException("O pacote da classe deve conter somente um diretorio 'modules'. Ex: com.minha.empresa.modules.nomeDoModulo.NomeDaClasse.class");
		
		if(!arr[1].contains("."))
			throw new IllegalArgumentException("O pacote deve conter o diretorio com o nome do modulo logo apos o diretorio 'modules'  'modules'. Ex: com.minha.empresa.modules.nomeDoModulo.NomeDaClasse.class");
		
		this.clazz = moduleClass;

	}

	@Override
	public String getFormatedModulePath() {
		
		if(null==clazz)
			throw new NullPointerException("Nenhuma classe foi definida");
		
		return clazz.getName().replaceAll("\\.", "/").concat(".class");
	}
	
}
