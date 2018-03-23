package com.cisco.xmp.group.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
    private Long instanceId;
    private String instanceName;
    private String description;

    private boolean limitGroupMembership;
    private String appGroupType;
    private String groupNameHierarchy;

    private List<Group> superGroups = new ArrayList();
    private List<Group> subGroups = new ArrayList();

    private static final long ALL_ONES_BIT_MASK = 4294967295L;
    private static final int BIT_SHIFT_32 = 32;

    public Long getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceName() {
        return this.instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLimitGroupMembership() {
        return this.limitGroupMembership;
    }

    public void setLimitGroupMembership(boolean limitGroupMembership) {
        this.limitGroupMembership = limitGroupMembership;
    }

    public String getAppGroupType() {
        return this.appGroupType;
    }

    public void setAppGroupType(String appGroupType) {
        this.appGroupType = appGroupType;
    }

    public void setGroupNameHierarchy(String groupNameHierarchy) {
        this.groupNameHierarchy = groupNameHierarchy;
    }

    public String getGroupNameHierarchy() {
        return this.groupNameHierarchy;
    }

    public List<Group> getSuperGroups() {
        if (this.superGroups == null) {
            this.superGroups = new ArrayList();
        }
        return this.superGroups;
    }

    public void setSuperGroups(List<Group> superGroups) {
        this.superGroups = superGroups;
    }

    public List<Group> getSubGroups() {
        if (this.subGroups == null) {
            this.subGroups = new ArrayList();
        }
        return this.subGroups;
    }

    public void setSubGroups(List<Group> subGroups) {
        this.subGroups = subGroups;
    }

    public void addSubGroup(Group subGroup) {
        subGroup.superGroups.add(this);
        this.subGroups.add(subGroup);
    }

    public void addSuperGroup(Group superGroup) {
        superGroup.subGroups.add(this);
        this.superGroups.add(superGroup);
    }

    public void deleteSubGroup(Group subGroup) {
        subGroup.superGroups.remove(this);
        this.subGroups.remove(subGroup);
    }

    public void deleteSuperGroup(Group superGroup) {
        superGroup.subGroups.remove(this);
        this.superGroups.remove(superGroup);
    }

    public boolean equals(Object o) {
        if ((o == null) || (!(o instanceof Group)) || (o.getClass() != getClass())) {
            return false;
        }
        Group o1 = (Group) o;
        if ((o1.getInstanceId().longValue() == 0L) || (getInstanceId().longValue() == 0L)) {
            return super.equals(o);
        }
        return o1.getInstanceId().equals(getInstanceId());
    }

    public int hashCode() {
        return getInstanceId().longValue() == 0L ? super.hashCode()
                : (int) (getInstanceId().longValue() & 0xFFFFFFFF) | (int) (getInstanceId().longValue() >>> 32);
    }

}