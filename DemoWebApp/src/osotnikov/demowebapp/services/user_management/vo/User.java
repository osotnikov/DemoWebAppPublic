package osotnikov.demowebapp.services.user_management.vo;
  
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
 
@Entity
@Table(name="USERS")
@Cacheable(false)
public class User implements Serializable {
          
    @Id
    @Column(nullable=false, length=1024)
    private String email;
  
    @Column(nullable=false)
    private String firstName;
      
    @Column(nullable=false)
    private String lastName;
      
    /**
     * A sha512 is 512 bits long -- as its name indicates. If you are using an hexadecimal representation, 
     * each digit codes for 4 bits ; so you need 512 : 4 = 128 characters to represent 512 bits -- plus you
     * need another 7 for the "{SHA-1}" prefix, thus you need a varchar(135), or a char(135), as the 
     * length is always the same, not varying at all.
     */
    @Column(nullable=false, length=135) //sha-512 + hex
    private String password;
    
    // @Transient means non persistent.
    @Transient
    private String confirmPasswordValue;
      
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable=false)
    private Date registeredOn;
          
    @ElementCollection(targetClass = GroupEnum.class)
    @CollectionTable(name = "USERS_GROUPS", 
                    joinColumns       = @JoinColumn(name = "email", nullable=false), 
                    uniqueConstraints = { @UniqueConstraint(columnNames={"email","groupname"}) } )
    @Enumerated(EnumType.STRING)
    @Column(name="groupname", length=64, nullable=false)
    private List<GroupEnum> groups;
     
    public User(){
    
    }
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String getEmail() {
        return email;
    }
  
    public void setEmail(String email) {
        this.email = email;
    }
  
    /**
     * @return the password in SHA512 HEX representation
     */
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public Date getRegisteredOn() {
        return registeredOn;
    }
 
    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }
 
    public List<GroupEnum> getGroups() {
        return groups;
    }
 
    public void setGroups(List<GroupEnum> groups) {
        this.groups = groups;
    }
    
    public String getConfirmPasswordValue() {
		return confirmPasswordValue;
	}

	public void setConfirmPasswordValue(String confirmPasswordValue) {
		this.confirmPasswordValue = confirmPasswordValue;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", password=" + password
				+ ", confirmPasswordValue=" + confirmPasswordValue
				+ ", registeredOn=" + registeredOn + ", groups=" + groups + "]";
	}

	
}