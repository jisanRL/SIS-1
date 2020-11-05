package bean;

import java.util.ArrayList;
import java.util.List;

/*
 * this class is a simple data strucutre 
 * holds information about one course 
 * 
 * @attr 
 * 		cid , 		course ID 
 * 		students, 	list of studentIDs who are enrolled in the course 
 * 		credit, 	credit taken
 * 
 */
public class EnrollmentBean {
	
	private String cid;
	private String sid;
	private List<String> students = new ArrayList<String>();
	private String credits;
	
	//ctr
	public EnrollmentBean() {
		
	}
	
	// getters and setters 
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public List<String> getStudents() {
		return students;
	}

	public void setStudents(List<String> students) {
		this.students = students;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}
	
	public static void main(String[] args) {
		System.out.println("enrollmentbean");
	}
}
