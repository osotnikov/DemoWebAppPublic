package osotnikov.demowebapp.services.user_management.session;

import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DeclareRoles({"ADMINISTRATOR", "USER"})
//@Stateless(name="pojoEjb")
public class Pojo {
	@PersistenceContext(unitName="web_demo_pu")
    private EntityManager em;
	
	@EJB(beanName="UserManagementService")
	UserManagementService userManagementBean;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void func(){
		if(em == null){
			System.out.println("em is null");
		}else{
			System.out.println("em is: " + em.toString());
		}
		
		if(userManagementBean == null){
			System.out.println("userManagementBean is null");
		}else{
			System.out.println("userManagementBean is: " + userManagementBean.toString());
		}
		
	}
}
