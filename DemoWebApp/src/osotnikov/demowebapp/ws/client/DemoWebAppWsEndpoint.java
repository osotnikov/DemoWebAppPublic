
package osotnikov.demowebapp.ws.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "DemoWebAppWsEndpoint", targetNamespace = "http://ws.demowebapp.osotnikov/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface DemoWebAppWsEndpoint {


    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "sendObjectMessage", targetNamespace = "http://ws.demowebapp.osotnikov/", className = "osotnikov.demowebapp.ws.client.SendObjectMessage")
    @ResponseWrapper(localName = "sendObjectMessageResponse", targetNamespace = "http://ws.demowebapp.osotnikov/", className = "osotnikov.demowebapp.ws.client.SendObjectMessageResponse")
    @Action(input = "http://ws.demowebapp.osotnikov/DemoWebAppWsEndpoint/sendObjectMessageRequest", output = "http://ws.demowebapp.osotnikov/DemoWebAppWsEndpoint/sendObjectMessageResponse")
    public void sendObjectMessage(
        @WebParam(name = "arg0", targetNamespace = "")
        MessageToDemoWebAppWS arg0);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSystemInfo", targetNamespace = "http://ws.demowebapp.osotnikov/", className = "osotnikov.demowebapp.ws.client.GetSystemInfo")
    @ResponseWrapper(localName = "getSystemInfoResponse", targetNamespace = "http://ws.demowebapp.osotnikov/", className = "osotnikov.demowebapp.ws.client.GetSystemInfoResponse")
    @Action(input = "http://ws.demowebapp.osotnikov/DemoWebAppWsEndpoint/getSystemInfoRequest", output = "http://ws.demowebapp.osotnikov/DemoWebAppWsEndpoint/getSystemInfoResponse")
    public String getSystemInfo();

}
