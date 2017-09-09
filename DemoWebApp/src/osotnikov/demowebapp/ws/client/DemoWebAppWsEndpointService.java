
package osotnikov.demowebapp.ws.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "DemoWebAppWsEndpointService", targetNamespace = "http://ws.demowebapp.osotnikov/", wsdlLocation = "http://localhost:8001/demoWebAppWs/DemoWebAppWsEndpointService?WSDL")
public class DemoWebAppWsEndpointService
    extends Service
{

    private final static URL DEMOWEBAPPWSENDPOINTSERVICE_WSDL_LOCATION;
    private final static WebServiceException DEMOWEBAPPWSENDPOINTSERVICE_EXCEPTION;
    private final static QName DEMOWEBAPPWSENDPOINTSERVICE_QNAME = new QName("http://ws.demowebapp.osotnikov/", "DemoWebAppWsEndpointService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8001/demoWebAppWs/DemoWebAppWsEndpointService?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        DEMOWEBAPPWSENDPOINTSERVICE_WSDL_LOCATION = url;
        DEMOWEBAPPWSENDPOINTSERVICE_EXCEPTION = e;
    }

    public DemoWebAppWsEndpointService() {
        super(__getWsdlLocation(), DEMOWEBAPPWSENDPOINTSERVICE_QNAME);
    }

    public DemoWebAppWsEndpointService(WebServiceFeature... features) {
        super(__getWsdlLocation(), DEMOWEBAPPWSENDPOINTSERVICE_QNAME, features);
    }

    public DemoWebAppWsEndpointService(URL wsdlLocation) {
        super(wsdlLocation, DEMOWEBAPPWSENDPOINTSERVICE_QNAME);
    }

    public DemoWebAppWsEndpointService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, DEMOWEBAPPWSENDPOINTSERVICE_QNAME, features);
    }

    public DemoWebAppWsEndpointService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DemoWebAppWsEndpointService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns DemoWebAppWsEndpoint
     */
    @WebEndpoint(name = "DemoWebAppWsEndpointPort")
    public DemoWebAppWsEndpoint getDemoWebAppWsEndpointPort() {
        return super.getPort(new QName("http://ws.demowebapp.osotnikov/", "DemoWebAppWsEndpointPort"), DemoWebAppWsEndpoint.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns DemoWebAppWsEndpoint
     */
    @WebEndpoint(name = "DemoWebAppWsEndpointPort")
    public DemoWebAppWsEndpoint getDemoWebAppWsEndpointPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.demowebapp.osotnikov/", "DemoWebAppWsEndpointPort"), DemoWebAppWsEndpoint.class, features);
    }

    private static URL __getWsdlLocation() {
        if (DEMOWEBAPPWSENDPOINTSERVICE_EXCEPTION!= null) {
            throw DEMOWEBAPPWSENDPOINTSERVICE_EXCEPTION;
        }
        return DEMOWEBAPPWSENDPOINTSERVICE_WSDL_LOCATION;
    }

}
