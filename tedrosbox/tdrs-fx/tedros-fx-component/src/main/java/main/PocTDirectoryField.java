package main;

import java.io.File;

import com.tedros.fxapi.control.TDirectoryField;

public class PocTDirectoryField {
	
	private AppMain main;
	
	public PocTDirectoryField(AppMain main) {
		this.main = main;
		
		TDirectoryField field = new TDirectoryField(main.stage);
		//field.settShowFilePath(false);
		field.settFile(new File(System.getProperty("user.home")));
		
		main.prateleira.getChildren().add(field);
		
		
		
	}

}
