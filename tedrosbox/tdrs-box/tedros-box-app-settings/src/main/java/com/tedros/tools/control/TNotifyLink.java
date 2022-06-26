/**
 * 
 */
package com.tedros.tools.control;

import com.tedros.core.TLanguage;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.module.TObjectRepository;
import com.tedros.core.notify.model.TNotify;
import com.tedros.fxapi.builder.TGenericBuilder;
import com.tedros.tools.module.notify.TNotifyModule;
import com.tedros.tools.module.notify.model.TNotifyMV;

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
	private TObjectRepository repo = new TObjectRepository();
	
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
