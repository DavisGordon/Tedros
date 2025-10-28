package org.tedros.fx.process;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.tedros.app.process.ITTask;

import javafx.concurrent.Task;

/**<pre>
 * A custom Task impl
 * </pre>
 * 
 * @author Davis Gordon
 * 
 * */
public abstract class TTaskImpl<V> extends Task<V> implements ITTask<V> {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:");
	
	abstract public String getServiceNameInfo();
	
	@Override
	public boolean cancel(boolean arg0) {
		updateMessage(sdf.format(Calendar.getInstance().getTime()) + " " + getServiceNameInfo() + " sendo cancelado...\n"  );
		boolean flag = super.cancel(arg0);
		updateMessage(sdf.format(Calendar.getInstance().getTime()) + " " + getServiceNameInfo() + " cancelado!\n"  );
		return flag;
	}
	
	@Override
	protected void done() {
		updateMessage(sdf.format(Calendar.getInstance().getTime()) + " " + getServiceNameInfo() + " pronto!\n"  );
		super.done();
	}
	
	@Override
	protected void succeeded() {
		updateMessage(sdf.format(Calendar.getInstance().getTime()) + " " + getServiceNameInfo() + " executado com sucesso!\n"  );
		super.succeeded();
	}
	
	@Override
	protected void failed() {
		updateMessage(sdf.format(Calendar.getInstance().getTime()) + " " + getServiceNameInfo() + " falhou!\n");
		super.failed();
	}
	
	@Override
	protected void running() {
		updateMessage(sdf.format(Calendar.getInstance().getTime()) + " " + getServiceNameInfo() + " executado...\n"  );
		super.running();
	}
	
	@Override
	protected void scheduled() {
		updateMessage(sdf.format(Calendar.getInstance().getTime()) + " " + getServiceNameInfo() + " agendado!\n"  );
		super.scheduled();
	}

}
