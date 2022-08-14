package org.tedros.server.info;

import java.util.Properties;

/**
 * Abstração da Interface {@link ITAppInfo}
 * */
public abstract class TAppInfo implements ITAppInfo {

	private String moduleName;
	private String partnerId;
	private String partnerAlias;
	private String moduleShortDescription;
	private String moduleSchema;
	
	
	
	public void readInfoProperties(Properties info){
		moduleName = (String) info.get("module_name");
		partnerId = (String) info.get("partner_id");
		partnerAlias = (String) info.get("partner_alias");
		moduleShortDescription = (String) info.get("module_short_description");
		moduleSchema = (String) info.get("module_schema");
	}
		
	public String getModuleSchema() {
		return moduleSchema;
	}
	
	@Override
	public String getModuleName() {
		return this.moduleName;
	}
	
	@Override
	public String getPartnerId() {
		return this.partnerId;
	}
	
	public String getPartnerAlias() {
		return partnerAlias;
	}

	public String getModuleShortDescription() {
		return moduleShortDescription;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(null==getPartnerId() || null==obj)
			return false;
		return getPartnerId().equals(((TAppInfo)obj).getPartnerId());
	}
	
	@Override
	public String toString() {
		if(null==getPartnerId() || null==getModuleName())
			return "";
		return getPartnerId()+"-"+getModuleName();
	}
	
}
