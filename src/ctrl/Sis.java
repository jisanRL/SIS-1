package ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.StudentBean;
import dao.EnrollementDao;
import dao.StudentDAO;
import model.SIS;

/**
 * Servlet implementation class Test
 * front controller 
 */
@WebServlet({"/Sis", "/Sis/*", "/sis", "/sis/*"})
public class Sis extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SIS sisModel;
	private StudentDAO sd;
	private EnrollementDao ed;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sis() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /*
     * init method 
     */
    public void init() throws ServletException {
		try {
			sisModel = new SIS();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getServletContext().setAttribute("sis", sisModel);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		List<StudentBean> res = new ArrayList<>();
		Map<String, StudentBean> tr = new HashMap<String, StudentBean>();
		
		int ep = 0;
		int numberofresults = 0;
		
		String reportButton = request.getParameter("report");		// button clicked
		
		// check if button is clicked 
		if (reportButton != null) {
			String prefix = request.getParameter("prefix");					// name
			String creditTaken = request.getParameter("creditTaken");		// miniumum credit taken
			
			if (!emptyNullChecker(prefix) && !emptyNullChecker(creditTaken)) {			// check this part 
				try {
					sisModel = (SIS) getServletContext().getAttribute("sis");
					tr = sisModel.retriveStudent(prefix, creditTaken);
					
					// check through the hashmap and get the data in studentBean
					for (String studentBean : tr.keySet()) {
						res.add(tr.get(studentBean));
					}
					
					numberofresults = tr.size();
					request.setAttribute("numberofresults", numberofresults);
					request.setAttribute("resultMap", res.toArray());
				
				} catch (Exception e) {
					// TODO: handle exception
					ep = 1;
					request.setAttribute("errorValue", ep);
				}
			} else {
				// if any fields are empty 
				ep = 1;
				request.setAttribute("errorValue", ep);
			}
		}
		request.getRequestDispatcher("/Form.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private boolean emptyNullChecker(String input) {
		if (input.isEmpty() || input == null) {
			return true;
		}
		return false;
	}

}
