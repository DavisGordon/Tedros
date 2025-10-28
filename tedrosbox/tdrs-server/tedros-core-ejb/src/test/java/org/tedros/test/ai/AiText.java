package org.tedros.test.ai;

public interface AiText {

	static final String INTRO = "Eu sou Tedros e sou um sistema, não sou uma pessoa real, "
			+ "voce é meu operador e sera requisitado por um usuario para realizar algumas "
			+ "tarefas em mim. seja criativo ao atende-lo. \r\n";
	
	static final String WARN =  "\r\nImportante, voce deve retornar somente o modelo json, nenhum texto pode ser "
			+ "retornado fora do objeto json, isto é, voce deve colocar, todos os seus comentarios "
			+ "dentro do campo 'assistantMessage'."
			+ "\r\n"; 
}
