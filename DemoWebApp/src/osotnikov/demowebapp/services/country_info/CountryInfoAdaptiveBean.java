package osotnikov.demowebapp.services.country_info;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.InitialContext;

import osotnikov.web.utils.WebUtils;

//@Stateless(name="CountryInfoAdaptiveBean")
//@EJB(name = "CountryInfoWsDataBean", beanInterface = CountryInfoLocal.class)
//@EJB(name = "CountryInfoLocalDataBean", beanInterface = CountryInfoLocal.class)
public class CountryInfoAdaptiveBean implements CountryInfoLocal, CountryInfoRemote{
	
	@Inject
	private WebUtils webUtils;
	
	@Resource 
	// Yet another way to get the ejb context instead of using JNDI...
	private SessionContext sessionEJBContext;
	
	@Override
	public String getStringWithAllTheCountries() throws Exception{
		
		boolean canMakeRemoteCalls = false;
		
		// CHECK IF THE COUNTRY INFO WS IS REACHABLE ... If it is use it, otherwise use the local implementation.
		canMakeRemoteCalls = false;//webUtils.hostAvailabilityCheck("WEB_DEMO_CONSTANTS.COUNTRY_INFO_WS_WSDL_HOST", 80);

		InitialContext ic = new InitialContext();
	    //SessionContext sessionEJBContext = (SessionContext) ic.lookup("java:comp/EJBContext");
	    
	    CountryInfoCommon countryInfoBean = null;
		
		if(canMakeRemoteCalls){
			//ic.lookup("java:global/_auto_generated_ear_/DemoWebApp/CountryInfoWsDataBean");
			//ic.lookup("java:app/DemoWebApp/CountryInfoWsDataBean");//
			countryInfoBean = (CountryInfoCommon)ic.lookup("java:module/CountryInfoWsDataBean");
			System.out.println("Got my countryInfoBean by looking up CountryInfoWsDataBean!");
		}else{
			countryInfoBean = (CountryInfoCommon)ic.lookup("java:module/CountryInfoLocalDataBean");//ic.lookup("java:global/_auto_generated_ear_/DemoWebApp/CountryInfoLocalDataBean");//(CountryInfoCommon)sessionEJBContext.lookup("java:comp/env/CountryInfoLocalDataBean");
			System.out.println("Got my countryInfoBean by looking up CountryInfoLocalDataBean!");
		}
		
		return countryInfoBean.getStringWithAllTheCountries();
	}

	@Override
	public String performReachabilityCheck() {
		// TODO Auto-generated method stub
		return "Hello, you have successfully reached the remote instance of the country info bean." + 
				" The bean type is CountryInfoAdaptiveBean.";
	}
	
}
