/**
 * 
 */
package org.tedros.tools.control;

import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.notify.model.TNotify;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.builder.TGenericBuilder;
import org.tedros.tools.module.notify.TNotifyModule;
import org.tedros.tools.module.notify.model.TNotifyMV;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.control.Hyperlink;

/**
 * Open a notify modal 
 * 
 * @author Davis Gordon
 *
 */
public class TNotifyLink extends Hyperlink {
	
	private TGenericBuilder<TNotify> builder;
	private TRepository repo = new TRepository();
	
	public TNotifyLink(TGenericBuilder<TNotify> builder) {
		this.builder = builder;
		super.setText(TLanguage.getInstance().getString("#{label.notify.link}"));
		EventHandler<ActionEvent> ev = e -> {
			TNotify m = this.builder.build();
			TedrosAppManager.getInstance().loadInModule(TNotifyModule.class, new TNotifyMV(m));
		};
		repo.add("ev", ev);
		setOnAction(new WeakEventHandler<>(ev));
	}
}
