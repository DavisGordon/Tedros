package org.tedros.fx.form.group;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.fx.form.ITForm;

public class TFormItem {
	
	private String id;
	private String buttonTitle;
	private ITForm form;
		
	public TFormItem() {
	}
	
	public TFormItem(String buttonTitle, ITForm form) {
		this.buttonTitle = buttonTitle;
		this.form = form;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
	
		if(obj==null)
			return false;
		
		TFormItem item = (TFormItem) obj;
		
		/*if(this.form!=null && item.getForm()!=null)
			return this.form.hashCode() == item.getForm().hashCode();*/
		
		if(this.id!=null && item.getId()!=null && this.buttonTitle!=null && item.getButtonTitle()!=null)
			return this.id.equals(item.getId()) && this.buttonTitle.equals(item.getButtonTitle());
		if(this.id!=null && item.getId()!=null)
			return this.id.equals(item.getId()); 
		if(this.buttonTitle!=null && item.getButtonTitle()!=null)
			return this.buttonTitle.equals(item.getButtonTitle());
		
		return false;
				 
	}
	
	public final String getButtonTitle() {
		return buttonTitle;
	}
	public final void setButtonTitle(String buttonTitle) {
		this.buttonTitle = buttonTitle;
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public ITForm getForm() {
		return form;
	}

	public void setForm(ITForm form) {
		this.form = form;
	}
	
}
