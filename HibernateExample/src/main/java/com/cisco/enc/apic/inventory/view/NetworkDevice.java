//package com.cisco.enc.apic.inventory.view;
//
//import java.util.Date;
//import java.util.Map;
//
//import com.cisco.xmp.model.framework.base.InstanceImpl;
//import com.cisco.xmp.model.framework.metadata.EntityMetadata;
//import com.cisco.xmp.model.framework.metadata.PropertyMetadata;
//
//public class NetworkDevice  {
//    /**
//     * 
//     */
//    private static final long serialVersionUID = 1L;
//    public static final String AP_MANAGER_INTERFACE_IP = "apManagerInterfaceIp";
//    public static final String BOOT_DATE_TIME = "bootDateTime";
//    public static final String COLLECTION_STATUS = "collectionStatus";
//    public static final String FAMILY = "family";
//    public static final String HOSTNAME = "hostname";
//    public static final String INTERFACE_COUNT = "interfaceCount";
//    public static final String INVENTORY_STATUS_DETAIL = "inventoryStatusDetail";
//    public static final String LAST_UPDATE_TIME = "lastUpdateTime";
//    public static final String LAST_UPDATED = "lastUpdated";
//    public static final String LINE_CARD_COUNT = "lineCardCount";
//    public static final String LINE_CARD_ID = "lineCardId";
//    public static final String LOCATION = "location";
//    public static final String LOCATION_NAME = "locationName";
//    public static final String MAC_ADDRESS = "macAddress";
//    public static final String MANAGEMENT_IP_ADDRESS = "managementIpAddress";
//    public static final String MEMORY_SIZE = "memorySize";
//    public static final String PLATFORM_ID = "platformId";
//    public static final String REACHABILITY_FAILURE_REASON = "reachabilityFailureReason";
//    public static final String REACHABILITY_STATUS = "reachabilityStatus";
//    public static final String ROLE = "role";
//    public static final String ROLE_SOURCE = "roleSource";
//    public static final String SERIAL_NUMBER = "serialNumber";
//    public static final String SERIES = "series";
//    public static final String SNMP_CONTACT = "snmpContact";
//    public static final String SNMP_LOCATION = "snmpLocation";
//    public static final String SOFTWARE_VERSION = "softwareVersion";
//    public static final String TAG_COUNT = "tagCount";
//    public static final String TUNNEL_UDP_PORT = "tunnelUdpPort";
//    public static final String TYPE = "type";
//    public static final String UP_TIME = "upTime";
//    private String apManagerInterfaceIp = null;
//
//    public String getApManagerInterfaceIp() {
//        return apManagerInterfaceIp;
//    }
//
//    public void setApManagerInterfaceIp(String apManagerInterfaceIp) {
//        /* 215 */this.apManagerInterfaceIp = apManagerInterfaceIp;
//    }
//
//    /* 219 */private String bootDateTime = null;
//
//    public String getBootDateTime() {
//        /* 227 */return bootDateTime;
//    }
//
//    public void setBootDateTime(String bootDateTime) {
//        /* 235 */this.bootDateTime = bootDateTime;
//    }
//
//    /* 239 */private String collectionStatus = null;
//
//    public String getCollectionStatus() {
//        /* 250 */return collectionStatus;
//    }
//
//    public void setCollectionStatus(String collectionStatus) {
//        /* 261 */this.collectionStatus = collectionStatus;
//    }
//
//    /* 265 */private String family = null;
//
//    public String getFamily() {
//        /* 274 */return family;
//    }
//
//    public void setFamily(String family) {
//        /* 283 */this.family = family;
//    }
//
//    /* 287 */private String hostname = null;
//
//    public String getHostname() {
//        /* 295 */return hostname;
//    }
//
//    public void setHostname(String hostname) {
//        /* 303 */this.hostname = hostname;
//    }
//
//    /* 307 */private String interfaceCount = null;
//
//    public String getInterfaceCount() {
//        /* 315 */return interfaceCount;
//    }
//
//    public void setInterfaceCount(String interfaceCount) {
//        /* 323 */this.interfaceCount = interfaceCount;
//    }
//
//    /* 327 */private String inventoryStatusDetail = null;
//
//    public String getInventoryStatusDetail() {
//        /* 335 */return inventoryStatusDetail;
//    }
//
//    public void setInventoryStatusDetail(String inventoryStatusDetail) {
//        /* 343 */this.inventoryStatusDetail = inventoryStatusDetail;
//    }
//
//    /* 347 */private Date lastUpdateTime = null;
//
//    public Date getLastUpdateTime() {
//        /* 354 */return lastUpdateTime;
//    }
//
//    public void setLastUpdateTime(Date lastUpdateTime) {
//        /* 362 */this.lastUpdateTime = lastUpdateTime;
//    }
//
//    /* 366 */private String lastUpdated = null;
//
//    public String getLastUpdated() {
//        /* 374 */return lastUpdated;
//    }
//
//    public void setLastUpdated(String lastUpdated) {
//        /* 382 */this.lastUpdated = lastUpdated;
//    }
//
//    /* 386 */private String lineCardCount = null;
//
//    public String getLineCardCount() {
//        /* 394 */return lineCardCount;
//    }
//
//    public void setLineCardCount(String lineCardCount) {
//        /* 402 */this.lineCardCount = lineCardCount;
//    }
//
//    /* 406 */private String lineCardId = null;
//
//    public String getLineCardId() {
//        /* 414 */return lineCardId;
//    }
//
//    public void setLineCardId(String lineCardId) {
//        /* 422 */this.lineCardId = lineCardId;
//    }
//
//    /* 426 */private String location = null;
//
//    public String getLocation() {
//        /* 434 */return location;
//    }
//
//    public void setLocation(String location) {
//        /* 442 */this.location = location;
//    }
//
//    /* 446 */private String locationName = null;
//
//    public String getLocationName() {
//        /* 454 */return locationName;
//    }
//
//    public void setLocationName(String locationName) {
//        /* 462 */this.locationName = locationName;
//    }
//
//    /* 466 */private String macAddress = null;
//
//    public String getMacAddress() {
//        /* 474 */return macAddress;
//    }
//
//    public void setMacAddress(String macAddress) {
//        /* 482 */this.macAddress = macAddress;
//    }
//
//    /* 486 */private String managementIpAddress = null;
//
//    public String getManagementIpAddress() {
//        /* 494 */return managementIpAddress;
//    }
//
//    public void setManagementIpAddress(String managementIpAddress) {
//        /* 502 */this.managementIpAddress = managementIpAddress;
//    }
//
//    /* 506 */private String memorySize = null;
//
//    public String getMemorySize() {
//        /* 514 */return memorySize;
//    }
//
//    public void setMemorySize(String memorySize) {
//        /* 522 */this.memorySize = memorySize;
//    }
//
//    /* 526 */private String platformId = null;
//
//    public String getPlatformId() {
//        /* 534 */return platformId;
//    }
//
//    public void setPlatformId(String platformId) {
//        /* 542 */this.platformId = platformId;
//    }
//
//    /* 546 */private String reachabilityFailureReason = null;
//
//    public String getReachabilityFailureReason() {
//        /* 554 */return reachabilityFailureReason;
//    }
//
//    public void setReachabilityFailureReason(String reachabilityFailureReason) {
//        /* 562 */this.reachabilityFailureReason = reachabilityFailureReason;
//    }
//
//    /* 566 */private String reachabilityStatus = null;
//
//    public String getReachabilityStatus() {
//        /* 574 */return reachabilityStatus;
//    }
//
//    public void setReachabilityStatus(String reachabilityStatus) {
//        /* 582 */this.reachabilityStatus = reachabilityStatus;
//    }
//
//    /* 586 */private String role = null;
//
//    public String getRole() {
//        /* 595 */return role;
//    }
//
//    public void setRole(String role) {
//        /* 604 */this.role = role;
//    }
//
//    /* 608 */private String roleSource = null;
//
//    public String getRoleSource() {
//        /* 616 */return roleSource;
//    }
//
//    public void setRoleSource(String roleSource) {
//        /* 624 */this.roleSource = roleSource;
//    }
//
//    /* 628 */private String serialNumber = null;
//
//    public String getSerialNumber() {
//        /* 636 */return serialNumber;
//    }
//
//    public void setSerialNumber(String serialNumber) {
//        /* 644 */this.serialNumber = serialNumber;
//    }
//
//    /* 648 */private String series = null;
//
//    public String getSeries() {
//        /* 656 */return series;
//    }
//
//    public void setSeries(String series) {
//        /* 664 */this.series = series;
//    }
//
//    /* 668 */private String snmpContact = null;
//
//    public String getSnmpContact() {
//        /* 676 */return snmpContact;
//    }
//
//    public void setSnmpContact(String snmpContact) {
//        /* 684 */this.snmpContact = snmpContact;
//    }
//
//    /* 688 */private String snmpLocation = null;
//
//    public String getSnmpLocation() {
//        /* 696 */return snmpLocation;
//    }
//
//    public void setSnmpLocation(String snmpLocation) {
//        /* 704 */this.snmpLocation = snmpLocation;
//    }
//
//    /* 708 */private String softwareVersion = null;
//
//    public String getSoftwareVersion() {
//        /* 716 */return softwareVersion;
//    }
//
//    public void setSoftwareVersion(String softwareVersion) {
//        this.softwareVersion = softwareVersion;
//    }
//
//    private String tagCount = null;
//
//    public String getTagCount() {
//        return tagCount;
//    }
//
//    public void setTagCount(String tagCount) {
//        this.tagCount = tagCount;
//    }
//
//    private String tunnelUdpPort = null;
//
//    public String getTunnelUdpPort() {
//        return tunnelUdpPort;
//    }
//
//    public void setTunnelUdpPort(String tunnelUdpPort) {
//        this.tunnelUdpPort = tunnelUdpPort;
//    }
//
//    private String type = null;
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    private String upTime = null;
//
//    public String getUpTime() {
//        return upTime;
//    }
//
//    public void setUpTime(String upTime) {
//        this.upTime = upTime;
//    }
//
//    @Override
//    public String getComponentName() {
//        return "NetworkDevice";
//    }
//
//    @Override
//    public Class<? extends InstanceImpl> getEntityClass() {
//        return NetworkDevice.class;
//    }
//
//    @Override
//    public EntityMetadata getEntityMetadata() {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public Map<String, PropertyMetadata> getPropertyMetadataMap() {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//}
