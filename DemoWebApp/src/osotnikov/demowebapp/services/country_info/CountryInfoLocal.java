package osotnikov.demowebapp.services.country_info;

import javax.ejb.Local;

@Local
public interface CountryInfoLocal extends CountryInfoCommon{
	
	// Nothing to add just yet. I guess local EJB interfaces could be used to access
	// sensitive data that should only be accessible by the applications that run in
	// the same JVM where this EJB is deployed...
	
}
