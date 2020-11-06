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

import bean.EnrollmentBean;
import bean.StudentBean;

/*
 * retrieves data from enrollment table
 */

public class EnrollementDao {
	
	private DataSource ds;
	
	// ctr
		public EnrollementDao() throws ClassNotFoundException{ 
			try {
				ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
			} catch (NamingException e) {
				e.printStackTrace();
				} 
			}

	// method
	public Map<String, EnrollmentBean> retrieve(String cidPrefix, int credit_taken) throws SQLException {

		String query = "select * from enrollment where cid like '%" + cidPrefix + "%'and credit_taken >= "
				+ credit_taken;

		Map<String, EnrollmentBean> rv = new HashMap<String, EnrollmentBean>();

		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();

		while (r.next()) {
			String name = r.getString("GIVENNAME") + ", " + r.getString("SURNAME");
			String sid = (r.getString("CID")).trim();
//			String creditGrad = r.getString("CREDIT_GRADUATE");
//			String creditTaken = r.getString("CREDIT_TAKEN");

			// to get enrollment bean info
			EnrollmentBean eb = new EnrollmentBean();
			
			rv.put(sid, eb);
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Enrollement Dao");
	}

}
