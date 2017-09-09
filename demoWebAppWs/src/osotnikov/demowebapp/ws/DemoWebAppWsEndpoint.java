package osotnikov.demowebapp.ws;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.jws.WebService;

import osotnikov.demowebapp.ws.vo.MessageToDemoWebAppWS;
//import org.eclipse.persistence.jpa.PersistenceProvider;

@WebService
public class DemoWebAppWsEndpoint {

    public String getSystemInfo(){
    	
    	ByteArrayOutputStream bArrOS = new ByteArrayOutputStream();
    	PrintStream ps = null;
    	try{
    		 ps = new PrintStream(bArrOS, true, "UTF_16");
    	}catch(UnsupportedEncodingException e){
    		e.printStackTrace();
    	}
    	
    	Map<String, String> env = System.getenv();
    	
        for (String envName : env.keySet()) {
        	ps.format("%s=%s%n", envName,env.get(envName));
        }
        
        String sysInfo = new String(bArrOS.toByteArray(),java.nio.charset.StandardCharsets.UTF_16);
        
        System.out.println("Will send the following message: " + sysInfo);
        
        return sysInfo;
    }
    
    public void sendObjectMessage(MessageToDemoWebAppWS demoMsg){
    	
    	System.out.println("Received the following message: " + demoMsg.toString());
    	
    }
    
    public String anotherAddition(){return "lalal";}
}