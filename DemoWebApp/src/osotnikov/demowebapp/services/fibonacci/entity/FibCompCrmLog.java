package osotnikov.demowebapp.services.fibonacci.entity;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="FIB_COMP_CRM_LOG")
@Cacheable(false)
public class FibCompCrmLog{
	
	private long id;
	private long billingId;
	private String username;
	private String firstAndLastname;
	private int termInd;
	private int result;
	private Date startTime;
	private Date endTime;
	private long duration;
	private long cost;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID", nullable=false)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="TERM_IND", nullable=false)
	public int getTermInd() {
		return termInd;
	}
	public void setTermInd(int termInd) {
		this.termInd = termInd;
	}
	
	@Column(name="RESULT", nullable=false)
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	@Column(name="START_TIME", nullable=false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Column(name="END_TIME", nullable=false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/** @return The time the computation took in milliseconds. Derived properties are not supported
	 *  by JPA that is why these methods use a field. */
	@Column(name="DURATION", nullable=false)
	public long getDuration(){
		return duration;
	}
	public void setDuration(long duration){
		this.duration = duration;
	}
	
	@Column(name="COST", nullable=false)
	public long getCost(){
		return cost;
	}
	public void setCost(long cost){
		this.cost = cost;
	}
	
	@Column(name="USERNAME", nullable=false, length=1024)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name="BILL_ID", nullable=false)
	public long getBillingId() {
		return billingId;
	}
	public void setBillingId(long billingId) {
		this.billingId = billingId;
	}
	@Column(name="FIRST_AND_LAST_NAME", nullable=true, length=1024)
	public String getFirstAndLastname() {
		return firstAndLastname;
	}
	public void setFirstAndLastname(String firstAndLastname) {
		this.firstAndLastname = firstAndLastname;
	}
	
}
