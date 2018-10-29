package stepcount.servlet;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import stepcount.dal.*;
import stepcount.model.*;


/**
 * Root resource (exposed at "/" path)
 */
@Path("/")
public class Resource {
    private static final StepDataDao stepDataDao = StepDataDao.getInstance();
    
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "APPLICATION_JSON" media type.
     *
     * @return SkierData object that will be returned as a APPLICATION_JSON response.
     */
    @Path("current/{userId}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public int getDatabyUserID(@PathParam("userId") int userId) throws SQLException {
        return stepDataDao.getStepDataByUserID(userId);
    }
    
    
    @GET
    @Path("single/{userId}/{day}")
    public Response getDataByUserIDAndRecordDate(@PathParam("userId") String userId, @PathParam("day") String day) throws SQLException {
        Integer res =  stepDataDao.getStepDataByUserIDAndRecordDate(Integer.parseInt(userId), Integer.parseInt(day));
      
        return Response.status(200).entity("getUserById is called, id : " + res.toString()).build();
    }
    
    @Path("range/{userId}/{startDay}/{numDays}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public ArrayList<Integer> getDataByUserIDAndRangeOfDays(@PathParam("userId") int userId, 
    		@PathParam("startDay") int startDay,
    		@PathParam("numDays") int numDays) throws SQLException {
        return stepDataDao.getStepDataByUserIDAndRangeOfDays(userId, startDay, numDays);
    }

    @Path("{userId}/{day}/{timeInterval}/{stepCount}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postData(@PathParam("userId") int userId,
    		@PathParam("day") int day,
    		@PathParam("timeInterval") int timeInterval,
    		@PathParam("stepCount") int stepCount) throws SQLException {
    	StepData stepData = new StepData(userId, day, timeInterval, stepCount);
        stepDataDao.create(stepData);
    }

   
}
