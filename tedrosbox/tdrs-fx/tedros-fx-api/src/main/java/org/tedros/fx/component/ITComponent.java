package org.tedros.fx.component;

import org.tedros.api.descriptor.ITComponentDescriptor;

public interface ITComponent {
	void tInitializeComponent(ITComponentDescriptor descriptor);
	void tStopComponent();
}
