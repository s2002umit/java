package com.cisco.apicem.group.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class ResourceGroup {
	
	private long instanceId;
	
    protected ResourceGroupScopeRule resourceGroupScopeRule = null;
	
	public ResourceGroupScopeRule getResourceGroupScopeRule() {
        return resourceGroupScopeRule;
    }

    public void setResourceGroupScopeRule(ResourceGroupScopeRule resourceGroupScopeRule) {
        this.resourceGroupScopeRule = resourceGroupScopeRule;
    }

    private String name = null;
	
	private Set<GroupType> groupTypeList = new LinkedHashSet();

	public long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(long instanceId) {
		this.instanceId = instanceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<GroupType> getGroupTypeList() {
		return groupTypeList;
	}

	public void setGroupTypeList(Set<GroupType> groupTypeList) {
		this.groupTypeList = groupTypeList;
	}
	
	
}
