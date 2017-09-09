package osotnikov.demowebapp.services.fibonacci.entity;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import osotnikov.demowebapp.services.fibonacci.constants.FibCompStep;
import osotnikov.demowebapp.services.user_management.vo.User;

@Entity
@Table(name="FIB_COMP_HISTORY")
@Cacheable(false)
public class FibCompHistory {

	private long id;
	private int termInd;
	private int result;
	private FibCompBill fibCompBill;
	private User user;
	private FibCompStep compStep;
	private Date recordCreationTime;
	private Date compStartTime;
	private Date compEndTime;
	private String jmsMsgId;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID", nullable=false)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BILL_ID", referencedColumnName="ID", nullable=true)
	public FibCompBill getFibCompBill() {
		return fibCompBill;
	}
	public void setFibCompBill(FibCompBill fibCompBill) {
		this.fibCompBill = fibCompBill;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USERNAME", referencedColumnName="EMAIL", nullable=true)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="COMP_STEP", nullable=true)
	public FibCompStep getCompStep() {
		return compStep;
	}
	public void setCompStep(FibCompStep compStep) {
		this.compStep = compStep;
	}
	
	@Column(name="RECORD_CREATION_TIME", nullable=true)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Date getRecordCreationTime() {
		return recordCreationTime;
	}
	public void setRecordCreationTime(Date recordCreationTime) {
		this.recordCreationTime = recordCreationTime;
	}
	
	@Column(name="COMP_START_TIME", nullable=true)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Date getCompStartTime() {
		return compStartTime;
	}
	public void setCompStartTime(Date compStartTime) {
		this.compStartTime = compStartTime;
	}
	
	@Column(name="COMP_END_TIME", nullable=true)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Date getCompEndTime() {
		return compEndTime;
	}
	public void setCompEndTime(Date compEndTime) {
		this.compEndTime = compEndTime;
	}
	
	@Column(name="JMS_MSG_ID", nullable=true)
	public String getJmsMsgId() {
		return jmsMsgId;
	}
	public void setJmsMsgId(String jmsMsgId) {
		this.jmsMsgId = jmsMsgId;
	}
	
	@Column(name="TERM_IND", nullable=true)
	public int getTermInd() {
		return termInd;
	}
	public void setTermInd(int termInd) {
		this.termInd = termInd;
	}
	
	@Column(name="RESULT", nullable=true)
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	
}
