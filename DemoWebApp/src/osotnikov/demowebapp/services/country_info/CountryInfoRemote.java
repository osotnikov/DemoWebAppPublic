package osotnikov.demowebapp.services.country_info;

import javax.ejb.Remote;

@Remote
public interface CountryInfoRemote extends CountryInfoCommon{

	// Simple method to check if the remote EJB is reachable.
	public String performReachabilityCheck();
	
}
