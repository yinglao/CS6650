package stepcount.servlet;
import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import stepcount.dal.*;
import stepcount.model.*;

@WebServlet("/post")
public class AddStepData extends HttpServlet{


    protected StepDataDao stepDataDao;

    @Override
    public void init() throws ServletException {
    	stepDataDao = StepDataDao.getInstance();
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Create the food.
        int userId = Integer.parseInt(req.getParameter("userid"));
        int recordDate = Integer.parseInt(req.getParameter("recorddate"));
        int timeInterval = Integer.parseInt(req.getParameter("timeinterval"));
        int stepCount = Integer.parseInt(req.getParameter("stepcount"));

         
        try {
        	StepData stepData = new StepData (userId, recordDate, timeInterval, stepCount);
        	stepData = stepDataDao.create(stepData);
        	messages.put("success", "Successfully created " + userId);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        
        req.getRequestDispatcher("/AddFood.jsp").forward(req, resp);
    }
}