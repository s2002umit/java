package com.cisco.apicem.group.model;

public class ResourceGroupScopeRule {
    
    private long instanceId;
    public long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(long instanceId) {
        this.instanceId = instanceId;
    }

    public ResourceGroup getResourceGroup() {
        return resourceGroup;
    }

    public void setResourceGroup(ResourceGroup resourceGroup) {
        this.resourceGroup = resourceGroup;
    }

    public static final String GROUP_TYPE = "groupType";
    public static final String INHERIT = "inherit";
    public static final String MEMBER_TYPE = "memberType";
    public static final String RULES = "rules";
    public static final String RESOURCE_GROUP = "resourceGroup";
    private GroupType groupType = null;
    private boolean inherit = false;
    private String memberType = null;
    private String rules = null;
    protected ResourceGroup resourceGroup = null;
  
    public GroupType getGroupType() {
        return this.groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public boolean getInherit() {
        return this.inherit;
    }

    public void setInherit(boolean inherit) {
        this.inherit = inherit;
    }

    public String getMemberType() {
        return this.memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getRules() {
        return this.rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }


    protected void setHiddenResourceGroup(ResourceGroup resourceGroup) {
        this.resourceGroup = resourceGroup;
    }

    public String getComponentName() {
        return "ResourceGroupScopeRule";
    }


}
