package osotnikov.demowebapp.services.user_management.session;
 
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import osotnikov.demowebapp.services.user_management.vo.GroupEnum;
import osotnikov.demowebapp.services.user_management.vo.User;
import osotnikov.utils.StringUtils;
 
@Stateless(name="UserManagementService")
public class UserManagementService {
	
	@PersistenceContext(unitName="web_demo_pu")
    private EntityManager em;
	
	@Inject
	StringUtils stringUtils;
	
	private User registeringUser;
	
	public User fetchUser(String email){
		User user = em.find(User.class, email); // DAO layer exceptions should be runtime and handled 
        // somewhere all together because you can't really recover from them.
		// Entities become detached when the transaction ends.
		return user;
	}
 
    /** @return null if the user does not exist or log in failed, the user otherwise... */
    public User loginAndFetchUser(String email, String password, HttpServletRequest req) {
    	
    	email = stringUtils.getTrimmedOrEmpty(email);
    	
    	User user = fetchUser(email);
        System.out.println("UserManagementService.loginAndFetchUser: "
        	+ "successfully retrieved User Profile from DB: " + user);
    	
        // Only login if not already logged in...
        if(user != null && req.getUserPrincipal() == null){
            try {
                req.login(email, password);// HttpServletRequest.login means that we'll 
                // have getUserPrincipal returning something after that ...
                System.out.println("UserManagementService.loginAndFetchUser: successfully "
                	+ "logged in " + email);
            } catch (ServletException e) {
            	user = null;
                e.printStackTrace();
            }
        }else if (user == null){
        	System.out.println("UserManagementService.loginAndFetchUser: No user found for "
        		+ "email: "+email);
        }else /*if (user != null && req.getUserPrincipal() != null)*/{
        	System.out.println("UserManagementService.loginAndFetchUser: Skip logged because "
        		+ "already logged in: "+email);
        }
        
        return user;
    }
 
    /** @return true if the logout succeeded, false if it failed. */
    public boolean logout(HttpServletRequest req) {
 
    	try {
            req.logout();
            req.getSession().invalidate();
            return true;
        } catch (ServletException e) {
            e.printStackTrace();
            System.out.println("UserManagementService.loginAndFetchUser: Logout failed on backend");
        }
        
        return false;
    }
    
    /** @return The User object that was passed with possibly the whitespace of its fields trimmed 
     * or null if the User object that was passed had invalid data. */
    public void validateAndTweakUser() {
    	// VALIDATE AND TWEAK REGISTRATION DATA
    	
    	// Check for empty fields.
        if(stringUtils.isEmpty(registeringUser.getEmail()) || 
           stringUtils.isEmpty(registeringUser.getFirstName()) ||
           stringUtils.isEmpty(registeringUser.getLastName()) ||
           stringUtils.isEmpty(registeringUser.getPassword()) ||
           stringUtils.isEmpty(registeringUser.getConfirmPasswordValue())){
        	
           registeringUser = null;
        }else if ( ! registeringUser.getConfirmPasswordValue().equals(registeringUser.getPassword())) {
        	registeringUser = null;
        }else{
        	// Remove whitespace.
            registeringUser.setEmail(registeringUser.getEmail().trim());
            registeringUser.setFirstName(registeringUser.getFirstName().trim());
            registeringUser.setLastName(registeringUser.getLastName().trim());
            
            System.out.println("UserManagementService.validateAndTweakUser, user is valid: "
                + registeringUser.toString());
        }
        
    }
    
    /** @return The User object that was passed with possibly the whitespace of its fields 
     * trimmed or null if the User object that was passed had invalid data. */
    public User validateTweakAndRegisterUser(User registeringUser) {
 
    	// VALIDATE AND TWEAK REGISTRATION DATA
    	this.registeringUser = registeringUser;
    	
    	validateAndTweakUser();
    	
    	if(this.registeringUser == null){
    		return null; // The user data is invalid. Registration failed.
    	}
    	
    	// Register the user with all the groups.
        List<GroupEnum> groups = new ArrayList<GroupEnum>();
        groups.add(GroupEnum.ADMINISTRATOR);
        groups.add(GroupEnum.USER);
        groups.add(GroupEnum.DEFAULT);
        registeringUser.setGroups(groups);
        
        registeringUser.setRegisteredOn(new java.util.Date());
        
        try{
	        String encPass = "{SHA-1}" + new sun.misc.BASE64Encoder()
            .encode(java.security.MessageDigest.getInstance("SHA1")
            .digest(registeringUser.getPassword().getBytes()));
	        registeringUser.setPassword(encPass);

        	System.out.println("Successfully encrypted the password of the user with SHA1 to: "
        		+ registeringUser.getPassword());
        }catch(Exception e){
        	e.printStackTrace();
        	throw new RuntimeException();
        }
        
        // This could cause a runtime exception, i.e. in case the user already exists.
        em.persist(registeringUser);
        System.out.println("UserManagementService.validateTweakAndRegisterUser: Successfully "
        		+ "registered new user: '" + registeringUser);
 
        return registeringUser;
    }
}