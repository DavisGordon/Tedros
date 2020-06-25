/**
 * 
 */
package com.covidsemfome.module.pessoa.icon;

import com.tedros.core.image.TImageView;


/**
 * @author Davis Gordon
 *
 */
public class PessoaIconImageView extends TImageView {
	
	public PessoaIconImageView() {
		
	}
	
	public static void main(String[] args) {
		new PessoaIconImageView();
	}

	@Override
	public String getImagePathName() {
		return "com/covidsemfome/module/pessoa/icon/icon.png";
	}

}
