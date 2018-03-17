/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package com.tedros.fxapi.util;

/**
 * <pre>
 * TMaskUtil - Utilidades para aplicação de mascara em {@link String} 
 * 
 * A = Qualquer letra de A a Z
 * # = Qualquer caracter, letra ou simbolo
 * 9 = Somente numeros
 * 
 * Exemplo:
 *  
 * 999.999.999-99<br> 99.999.999/9999-99<br> AAA-9999<br> (99) 9999-99999<br> [###] A999
 * </pre>
 * */ 
public final class TMaskUtil {
	
	/**
	 * <pre>
	 * String applyMask(String text, String mask)
	 * 
	 * Aplica a mascara informada na String passada como parametro.
	 * 
	 * A = Qualquer letra de A a Z
	 * # = Qualquer caracter, letra ou simbolo
	 * 9 = Somente numeros
	 * 
	 * Exemplo:
	 *  
	 * 999.999.999-99<br> 99.999.999/9999-99<br> AAA-9999<br> (99) 9999-99999<br> [###] A999
	 * </pre>
	 * 
	 * @param text - Texto a ser aplicada a mascara
	 * @param mask - mascara a ser aplicada
	 * @return {@link String} -Texto com a mascara aplicada
	 * 
	 * @author Davis Gordon
	 * */ 
	public static String applyMask(String text, String mask) {
		
		if(text==null)
			return null;
		
		String textMasked = "";
		String letter;
		char[] arr = mask.toCharArray();
		text = removeMask(text, arr);
		
		int y = 0;
		for (int x=0; x<arr.length; x++) {
			String ch = String.valueOf(arr[x]);
			
			if(text.length()<=y)
				continue;
			
			if(!(ch.equals("#") || ch.equals("9") || ch.equals("A"))){
				 textMasked +=  ch;
				 continue;
		    }
			
			letter = text.substring(y,y+1);
			y++;
			
			if(!isCharMaskValid(ch, letter))
				continue;
			textMasked += letter;
		}
		return textMasked;
	}
	
	/**
	 * Verifica se o caracter da mascara permite a entrada da letra informada.
	 * 
	 * @param ch - Respectivo caracter da mascara
	 * @param letter - Letra a ser validada
	 * @return {@link Boolean}
	 * 
	 * @author Davis Gordon
	 * */
	public static  boolean isCharMaskValid(String ch, String letter){
		if(ch!=null && ch.equals("9") && !letter.matches("[0-9]"))
			return false;
        if(ch!=null && ch.equals("A") && (!letter.matches("[a-z]") && !letter.matches("[A-Z]") ))
			return false;
        return true;
	}
	
	/**
	 * Recupera os caracteres da mascara entre as posições informadas
	 * 
	 *  @param start
	 *  @param end
	 *  @param mask
	 *  @return {@link String}
	 *  
	 *  @author Davis Gordon
	 * */
	public static  String getMaskChar(int start, int end, String mask){
		int s = (start==0) ? 0 : start;
		int e = (end==0) ? end+1 : end+1;
		if(s>mask.length())
			return null;
		String ch = mask.substring(s, e > mask.length() ? mask.length() : e);
		return ch;
	}

	/**
	 * Remove os caracters da mascara da String
	 * 
	 * @param text - texto com a mascara aplicada
	 * @param arr - Array com os caracteres da mascara
	 * @return {@link String} - texto sem os caracteres da mascara
	 * 
	 * @author Davis Gordon
	 * */
	public static String removeMask(String text, char[] arr) {
		for (char c : arr) {
			String l =String.valueOf(c);
			if(!(l.equals("#") || l.equals("9") || l.equals("A"))){
				if(l.equals(".")) l = "\\.";
				if(l.equals("?")) l = "\\?";
				if(l.equals("*")) l = "\\*";
				if(l.equals("(")) l = "\\(";
				if(l.equals(")")) l = "\\)";
				if(l.equals("{")) l = "\\{";
				if(l.equals("}")) l = "\\}";
				if(l.equals("|")) l = "\\|";
				if(l.equals("\\"))l = "\\\\";
				text = text.replaceAll(l, "");
			}
		}
		return text;
	}

}
