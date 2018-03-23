

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TESTPOC {

	public static void main(String[] args) {
	    
	    List<String> test1=new ArrayList<String>();
	    
	    for(int i=0;i<10000;i++){
	        test1.add(""+i);
	    }
		List list=test1.subList(5, 10);
		System.err.println("=====size==" + list.size());
		Map<String, String> test = new HashMap<String, String>();
		test.put(Long.valueOf(1l).toString(), "test");

		System.err.println("=======" + test.get("1"));

		String asss = "{\"tunnelIntfId\":20,\"key\":201,\"nhrpNetworkId\":201,\"protectionProfile\":\"DMVPN-PROFILE-TRANSPORT-3\",\"vrf\":{\"instanceId\":0,\"vrfName\":\"IWAN-TRANSPORT-3\",\"rdValue\":\"65511:101\"},\"bandwidth\":100000,\"clusterSupported\":false,\"delay\":\"\",\"eigrpEnabled\":false,\"eigrpNexthopEnabled\":false,\"eigrpasnumber\":0,\"encrPolicy\":[],\"hubdeviceEnabled\":false,\"hubspoketopologyEnabled\":false,\"ikePolicies\":[],\"intfList\":[],\"mode\":\"multipoint\",\"mtu\":1400,\"nhrpAuth\":\"cisco123\",\"nhrpHoldTime\":600,\"nhsfallbacktime\":-1,\"otherProtEnabled\":false,\"ripEnabled\":false,\"routingEnabled\":false,\"sourceIntf\":\"GigabitEthernet2\",\"splitHorizon\":false,\"tcpmss\":1360,\"nhsserverlist\":[{\"clusterId\":\"0\",\"maxConn\":\"1\",\"fallbackTime\":\"-1\",\"nhshopserverlist\":[{\"hubIPAdrress\":\"103.0.0.129\",\"greIPAddress\":\"90.90.90.1\",\"priority\":\"1\",\"id\":\"0\"},{\"hubIPAdrress\":\"103.0.0.131\",\"greIPAddress\":\"90.90.90.2\",\"priority\":\"2\",\"id\":\"0\"}]}],\"isakmpProfiles\":[],\"isakmpProfileNames\":[],\"transformSets\":[],\"eigrpSettings\":{\"tunnelIntfId\":[\"20\"],\"asNumber\":400,\"routingProcessId\":\"IWAN-EIGRP\",\"isStub\":true,\"leakMap\":{\"routeMapName\":\"STUB-LEAK-ALL\",\"routeMapEntries\":[{\"entryAction\":\"PERMIT\",\"seqNumber\":100,\"entryDescription\":\"Leak all routes\",\"allSetActions\":[],\"allMatches\":[]}]},\"networks\":[{\"ipAddress\":\"103.0.0.128\",\"ipMask\":\"255.255.255.128\"},{\"ipAddress\":\"5.5.5.5\",\"ipMask\":\"255.255.255.255\"}],\"afInterfaces\":[{\"intfName\":\"default\",\"isPassive\":true},{\"intfName\":\"GigabitEthernet1.99\",\"isPassive\":false,\"isSpiltHorizonEnabled\":true,\"isNextHopSelfEnabled\":true,\"authMode\":\"md5\",\"authKeychain\":{\"keyChainName\":\"LAN-KEY\",\"allKeys\":[{\"keyId\":1,\"keyString\":\"c1sco123\",\"keyEncr\":0}]}},{\"intfName\":\"Tunnel20\",\"isSpiltHorizonEnabled\":true,\"isNextHopSelfEnabled\":true,\"holdTime\":60,\"helloInterval\":20,\"isPassive\":false,\"authMode\":\"md5\",\"authKeychain\":{\"keyChainName\":\"WAN-KEY\",\"allKeys\":[{\"keyId\":1,\"keyString\":\"c1sco123\",\"keyEncr\":0}]}}],\"distributeLists\":[{\"isInList\":false,\"routeMap\":{\"routeMapName\":\"ROUTE-LIST\",\"routeMapEntries\":[{\"entryAction\":\"DENY\",\"seqNumber\":10,\"entryDescription\":\"Block readvertisement of learned WAN routes\",\"allMatches\":[{\"matchTagValue\":\"300\"},{\"matchTagValue\":\"301\"},{\"matchTagValue\":\"401\"},{\"matchTagValue\":\"400\"},{\"matchTagValue\":\"501\"},{\"matchTagValue\":\"500\"}]},{\"entryAction\":\"PERMIT\",\"seqNumber\":100,\"entryDescription\":\"Advertise all other routes\"}]}},{\"isInList\":true,\"routeMap\":{\"routeMapName\":\"DMVPN3-BR-IN\",\"routeMapEntries\":[{\"entryAction\":\"PERMIT\",\"seqNumber\":10,\"allMatches\":[{\"matchTagValue\":\"300\"},{\"matchTagValue\":\"301\"}]},{\"entryAction\":\"PERMIT\",\"seqNumber\":20,\"allSetActions\":[{\"setTagValue\":\"300\"}]},{\"entryAction\":\"PERMIT\",\"seqNumber\":5,\"entryDescription\":\"\",\"allMatches\":[{\"matchTagValue\":\"301\"}], \"allSetActions\":[{\"setMetricValue\":\"+10000\"}]}]}}]},\"intfAddress\":\"103.0.0.130\",\"intfAddressMask\":\"255.255.255.128\"}";
	}
		

}
