package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.StudentBean;

/*
 * this class is reponsible for communicating with the DBMS 
 */

public class StudentDAO {
	
	private DataSource ds;
	
	// ctr
	public StudentDAO() throws ClassNotFoundException{ 
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
			} 
		}
	
	// method
	public Map<String, StudentBean> retrieve(String namePrefix, int credit_taken) throws SQLException {
		
		String query = "select * from students where surname like '%" + namePrefix +"%'and credit_taken >= " + credit_taken;
		
		Map<String, StudentBean> rv = new HashMap<String, StudentBean>();
		
		Connection con = this.ds.getConnection(); 
		PreparedStatement p = con.prepareStatement(query); 
		ResultSet r = p.executeQuery();
		
		while (r.next()){
			String name = r.getString("GIVENNAME") + ", " + r.getString("SURNAME");
			String sid = (r.getString("SID")).trim();
			String creditGrad = r.getString("CREDIT_GRADUATE");
			String creditTaken = r.getString("CREDIT_TAKEN");
			
			// to get student bean info
			StudentBean sb = new StudentBean();					
			sb.setName(name);
			sb.setSid(sid);
			sb.setCredit_taken(Integer.parseInt(creditTaken));	// method call from studentBean
			sb.setCredit_grad(Integer.parseInt(creditGrad));
			
			rv.put(sid, sb);
		}
		r.close(); 
		p.close(); 
		con.close();
		return rv;
		}

	public DataSource getDS() {
		return this.ds;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("student dao");
	}

}
