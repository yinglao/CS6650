package com.example.stepcount.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.stepcount.model.*;


public class StepDataDao {

  protected ConnectionManager connectionManager;

  private static StepDataDao instance = null;

  protected StepDataDao() {
    connectionManager = new ConnectionManager();
  }

  public static StepDataDao getInstance() {
    if (instance == null) {
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
      if (connection != null) {
        System.out.println("close!");
        connection.close();
      }
      if (insertStmt != null) {
        insertStmt.close();
      }

    }
  }

  public int getStepDataByUserID(int userId) throws SQLException {
    int stepCount = 0;
    String selectStepData =
        "select UserID, RecordDate, sum(StepCount) as Step " +
            "from StepData " +
            "where UserID = ? " +
            "group by UserID, RecordDate " +
            "order by RecordDate DESC " +
            "limit 1;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectStepData);
      selectStmt.setInt(1, userId);
      results = selectStmt.executeQuery();
      if (results.next()) {
        stepCount = results.getInt("Step");

      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        System.out.println("close!");
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return stepCount;
  }


  /**
   * Get the all the stepData for a user and a day.
   */
  public int getStepDataByUserIDAndRecordDate(int userId, int RecordDate) throws SQLException {
    int totalStep = 0;
    String selectStepData =
        "SELECT SUM(StepCount) " +
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
      if (results.next()) {
        totalStep = results.getInt("sum(StepCount)");

      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        System.out.println("close!");
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return totalStep;
  }


  /**
   * Get the all the stepData for a user and a day.
   */
  public ArrayList<Integer> getStepDataByUserIDAndRangeOfDays(int userId, int startDay, int numDays)
      throws SQLException {
    ArrayList<Integer> stepCounts = new ArrayList<>();
    int totalSteps = 0;
    String selectStepData =
        "select RecordDate, Step " +
            "from " +
            "(select userID, RecordDate, sum(StepCount) as Step " +
            "from StepData " +
            "group by userID, RecordDate) as dayData " +
            "where UserID = ? and RecordDate >= ? and RecordDate < ? " +
            "order by RecordDate;";

    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectStepData);
      selectStmt.setInt(1, userId);
      selectStmt.setInt(2, startDay);
      selectStmt.setInt(3, numDays);
      results = selectStmt.executeQuery();
      while (results.next()) {
        int stepCount = results.getInt("Step");
        stepCounts.add(stepCount);
        totalSteps += stepCount;
      }
      stepCounts.add(totalSteps);
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        System.out.println("close!");
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return stepCounts;
  }
}
