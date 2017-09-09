package osotnikov.demowebapp.services.country_info;

import javax.ejb.Stateless;

//@Stateless(name="CountryInfoLocalDataBean")
public class CountryInfoLocalDataBean implements CountryInfoLocal{

	@Override
	public String getStringWithAllTheCountries() {
		
		// Pretend I got the list of countries from a local file ...
		
		return "WEB_DEMO_CONSTANTS.ALL_THE_COUNTRIES_STRING";
		
	}

}
