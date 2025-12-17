package org.tedros.core.setting.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public record TReportPropertie(String organizationName, byte[] reportLogotype) implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(reportLogotype);
		result = prime * result + Objects.hash(organizationName);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TReportPropertie)) {
			return false;
		}
		TReportPropertie other = (TReportPropertie) obj;
		return Objects.equals(organizationName, other.organizationName)
				&& Arrays.equals(reportLogotype, other.reportLogotype);
	}

	@Override
	public String toString() {
		return String.format("TReportPropertie [organizationName=%s, reportLogotype=%s]", organizationName,
				Arrays.toString(reportLogotype));
	}

}
