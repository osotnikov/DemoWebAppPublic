package osotnikov.demowebapp.services.country_info;

import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;
import javax.ejb.EJB;

//@Stateless(name="CountryInfoWsDataBean")
public class CountryInfoWsDataBean implements CountryInfoLocal {
	
	//@WebServiceRef(wsdlLocation = WEB_DEMO_CONSTANTS.COUNTRY_INFO_WS_WSDL_URL)
    //static Country countryService;
	
	@Override
	public String getStringWithAllTheCountries() {
		
		// The service.
		// Country country = new Country(); // This is valid but better use the injected service.
		
		// The port of the service (nothing to do with an http port).
		//CountrySoap countrySoapPort = countryService.getCountrySoap();
		String allCountriesStr = "";
		//allCountriesStr = countrySoapPort.getCountries();
	    //System.out.println("Got all countries: " + allCountriesStr);
		
		return allCountriesStr;
		
	}
	
}
