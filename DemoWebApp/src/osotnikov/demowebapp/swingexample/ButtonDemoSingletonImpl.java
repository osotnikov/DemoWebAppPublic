package osotnikov.demowebapp.swingexample;

import javax.annotation.PostConstruct;
//import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

//@Singleton
//@Startup
public class ButtonDemoSingletonImpl implements ButtonDemoSingleton {

	@PostConstruct
	private void init(){
	    
		System.out.println("ButtonDemoSingleton STARTED");
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ButtonJPanel.createAndShowGUI(); 
            }
        });
		
	}
	
}
