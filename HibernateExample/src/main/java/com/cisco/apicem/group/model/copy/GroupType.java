package com.cisco.apicem.group.model.copy;

import javax.xml.bind.annotation.XmlEnumValue;

public enum GroupType {

	@XmlEnumValue("RBAC") RBAC(0), @XmlEnumValue("POLICY") POLICY(1), @XmlEnumValue("SITE") SITE(
			2), @XmlEnumValue("DEVICE_TYPE") DEVICE_TYPE(3);

	public static final int RBAC_VALUE = 0;
	public static final int POLICY_VALUE = 1;
	public static final int SITE_VALUE = 2;
	public static final int DEVICE_TYPE_VALUE = 3;
	public static final String RBAC_NAME = "RBAC";
	public static final String POLICY_NAME = "POLICY";
	public static final String SITE_NAME = "SITE";
	public static final String DEVICE_TYPE_NAME = "DEVICE_TYPE";
	private int value;

	private GroupType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static GroupType getEnum(int value) {
		switch (value) {
		case 0:
			return RBAC;
		case 1:
			return POLICY;
		case 2:
			return SITE;
		case 3:
			return DEVICE_TYPE;
		default:
			throw new IllegalArgumentException("Unknown value type: " + value);
		}
	}

}
