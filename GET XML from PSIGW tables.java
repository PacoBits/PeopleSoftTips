import java.io.UnsupportedEncodingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class MessageDownloader {
  public MessageDownloader() {
    super();
  }

  public static void main(String[] args) throws SQLException,
                                                DataFormatException,
                                                UnsupportedEncodingException {
    Connection conn =
      DriverManager.getConnection("jdbc:oracle:thin:@SERVER:1521:BBDD_SID",
                                  "SYSADM", "PASSWORD");

    Statement stmt = conn.createStatement();
   /* ResultSet rs =
      stmt.executeQuery("SELECT IBTRANSACTIONID, IB_SEGMENTINDEX, SEGMENTNO, SUBSEGMENTNO, UNCOMPMIMEDATALEN, MIMEDATALONG\n" +
        "  FROM PSAPMSGPUBDATA\n" +
        //" WHERE IBTRANSACTIONID = 'GUID-GOES-HERE'\n" +
        " ORDER BY IBTRANSACTIONID, IB_SEGMENTINDEX, SEGMENTNO, SUBSEGMENTNO, DATASEQNO\n");
    
    String id = "";

    while (rs.next()) {
      if(!id.equals(rs.getString("IBTRANSACTIONID"))) {
        id = rs.getString("IBTRANSACTIONID");
        System.out.println("----------------------------------------------------");
        System.out.println("******  " + id + "  ******");
      }
      String segment = rs.getInt("IB_SEGMENTINDEX") + "-" +
                     rs.getInt("SEGMENTNO") + "-" +
                     rs.getInt("SUBSEGMENTNO");*/
    
    
    ResultSet rs =
    	      stmt.executeQuery("select aa.GUID,aa.IB_OPERATIONNAME,aa.VERSIONNAME,b.UNCOMPDATALEN,b.PUBDATALONG  FROM PSIBLOGHDR aa, PSIBLOGDATA b where AA.IB_OPERATIONNAME='OPERATION' and aa.guid=b.guid and rownum<3 \n");
    	    
    	    String id = "";

    	    while (rs.next()) {
    	      if(!id.equals(rs.getString("GUID"))) {
    	        id = rs.getString("GUID");
    	        System.out.println("----------------------------------------------------");
    	        System.out.println("******  " + id + "  ******");
    	      }
    	      System.out.println("******  segment  ******");
    	     // String segment = rs.getInt("GUID") + "-" +  rs.getInt("IB_OPERATIONNAME") + "-" +	                     rs.getInt("VERSIONNAME");
    	    
      Inflater inflater = new Inflater();
      
      byte[] result = new byte[rs.getInt("UNCOMPDATALEN")];
      
      inflater.setInput(rs.getBytes("PUBDATALONG"));
      
      int length = inflater.inflate(result);
      System.out.println("******  result  ******");
      
     // System.out.println("Segment: " + segment);
      System.out.println(new String(result, 0, length, "UTF-8"));
      System.out.println();
      System.out.println("--");
      System.out.println();
      
      inflater.end();
    }
  }
}