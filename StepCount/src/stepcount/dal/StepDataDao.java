package stepcount.dal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import stepcount.model.*;


public class StepDataDao {

	protected ConnectionManager connectionManager;

	private static StepDataDao instance = null;
	protected StepDataDao() {
		connectionManager = new ConnectionManager();
	}
	public static StepDataDao getInstance() {
		if(instance == null) {
			instance = new StepDataDao();
		}
		return instance;
	}

	public StepData create(StepData stepData) throws SQLException {
		String insertStepData =
			"INSERT INTO StepData(UserId, RecordDate, TimeInterval, StepCount) " +
			"VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			// Food has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertStepData,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, stepData.getUserId());
			// Note: for the sake of simplicity, just set Picture to null for now.
			insertStmt.setInt(2, stepData.getRecordDate());
			insertStmt.setInt(3, stepData.getTimeInterval());
			insertStmt.setInt(4, stepData.getStepCount());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			return stepData;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			
		}
	}


	/**
	 * Get the all the stepData for a user and a day.
	 */
	public List<StepData> getStepDataByUserIDAndRecordDate(int userId, int RecordDate) throws SQLException {
		List<StepData> stepDataList = new ArrayList<StepData>();
		String selectStepData =
			"SELECT * " +
			"FROM StepData " +
			"WHERE UserId=? " +
			"AND RecordDate=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectStepData);
			selectStmt.setInt(1, userId);
			selectStmt.setInt(2, RecordDate);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int resultUserId = results.getInt("UserId");
				int resultRecordDate = results.getInt("RecordDate");
				int timeInterval = results.getInt("TimeInterval");
				int stepCount = results.getInt("StepCount");

				StepData stepData = new StepData(resultUserId, resultRecordDate, timeInterval, stepCount);
				stepDataList.add(stepData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return stepDataList;
	}	
	
}
