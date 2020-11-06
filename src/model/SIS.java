package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import bean.EnrollmentBean;
import bean.StudentBean;
import dao.EnrollementDao;
import dao.StudentDAO;

/*
 * attributes that hold the instance of 
 * 	studentDao and EnrollmentDao
 */
public class SIS {
	
	private StudentDAO students;
	private EnrollementDao enrollment;
	private int creditTaken;
	private String nPrefix;
	
	/* ctr 
	 * intialize the instance of the classes
	 */
	public SIS() throws ClassNotFoundException{
		this.students = new StudentDAO();
		this.enrollment = new EnrollementDao();
	}
	
	// methods 
	public Map<String, StudentBean> retriveStudent(String namePrefix, String credit_taken) throws Exception {
		checkInput(namePrefix, credit_taken);
		
		this.creditTaken = Integer.parseInt(credit_taken);
		this.nPrefix = namePrefix;
		return this.students.retrieve(nPrefix, creditTaken);
	}
	
	// helper method that checks the input validities 
	private void checkInput(String namePrefix, String credit_taken) {
		try {
			int cTaken = Integer.parseInt(credit_taken);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			String qy = "select * from students where surname like ? and credit_taken >= ?";
			Connection cn = this.students.getDS().getConnection();
			PreparedStatement iv = cn.prepareStatement(qy);
			iv.setString(1, namePrefix);
			iv.setInt(2, Integer.parseInt(credit_taken)); 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	public Map<String, EnrollmentBean> retriveEnrollment() throws Exception {
		return this.enrollment.retrieve(nPrefix, creditTaken);
	}

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		SIS sis = new SIS();
		System.out.println("SIS");
		System.out.println("students = " + sis.students);
		System.out.println("enrollment =" + sis.enrollment);
		System.out.println("nPrefix = " + sis.nPrefix);
		System.out.println("creditTaken =" + sis.creditTaken);
		
		
	}

}
