
package osotnikov.demowebapp.ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the osotnikov.demowebapp.ws.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SendObjectMessage_QNAME = new QName("http://ws.demowebapp.osotnikov/", "sendObjectMessage");
    private final static QName _SendObjectMessageResponse_QNAME = new QName("http://ws.demowebapp.osotnikov/", "sendObjectMessageResponse");
    private final static QName _GetSystemInfoResponse_QNAME = new QName("http://ws.demowebapp.osotnikov/", "getSystemInfoResponse");
    private final static QName _GetSystemInfo_QNAME = new QName("http://ws.demowebapp.osotnikov/", "getSystemInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: osotnikov.demowebapp.ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendObjectMessage }
     * 
     */
    public SendObjectMessage createSendObjectMessage() {
        return new SendObjectMessage();
    }

    /**
     * Create an instance of {@link SendObjectMessageResponse }
     * 
     */
    public SendObjectMessageResponse createSendObjectMessageResponse() {
        return new SendObjectMessageResponse();
    }

    /**
     * Create an instance of {@link GetSystemInfoResponse }
     * 
     */
    public GetSystemInfoResponse createGetSystemInfoResponse() {
        return new GetSystemInfoResponse();
    }

    /**
     * Create an instance of {@link GetSystemInfo }
     * 
     */
    public GetSystemInfo createGetSystemInfo() {
        return new GetSystemInfo();
    }

    /**
     * Create an instance of {@link TextMessage }
     * 
     */
    public TextMessage createTextMessage() {
        return new TextMessage();
    }

    /**
     * Create an instance of {@link MessageToDemoWebAppWS }
     * 
     */
    public MessageToDemoWebAppWS createMessageToDemoWebAppWS() {
        return new MessageToDemoWebAppWS();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendObjectMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.demowebapp.osotnikov/", name = "sendObjectMessage")
    public JAXBElement<SendObjectMessage> createSendObjectMessage(SendObjectMessage value) {
        return new JAXBElement<SendObjectMessage>(_SendObjectMessage_QNAME, SendObjectMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendObjectMessageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.demowebapp.osotnikov/", name = "sendObjectMessageResponse")
    public JAXBElement<SendObjectMessageResponse> createSendObjectMessageResponse(SendObjectMessageResponse value) {
        return new JAXBElement<SendObjectMessageResponse>(_SendObjectMessageResponse_QNAME, SendObjectMessageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSystemInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.demowebapp.osotnikov/", name = "getSystemInfoResponse")
    public JAXBElement<GetSystemInfoResponse> createGetSystemInfoResponse(GetSystemInfoResponse value) {
        return new JAXBElement<GetSystemInfoResponse>(_GetSystemInfoResponse_QNAME, GetSystemInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSystemInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.demowebapp.osotnikov/", name = "getSystemInfo")
    public JAXBElement<GetSystemInfo> createGetSystemInfo(GetSystemInfo value) {
        return new JAXBElement<GetSystemInfo>(_GetSystemInfo_QNAME, GetSystemInfo.class, null, value);
    }

}
