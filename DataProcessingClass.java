package bankinApp;
import java.sql.*;
import java.util.Vector;


public class DataProcessingClass {
	
	public static void printResultSet(ResultSet rs) throws SQLException
	{
		if (!rs.isBeforeFirst() ) {    
		    System.out.println("No entries found");
		} 
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
		int space=1;
		while(rs.next())
		{
			if(space==1)
			{
				for(int title=1;title<=columnCount;title++)
				{
					if(rsmd.getColumnType(title)==4)
					{
						System.out.printf("%15s",rsmd.getColumnName(title));
					}
					else if(rsmd.getColumnType(title)==12)
					{
						System.out.printf("%20s",rsmd.getColumnName(title));
					}
					
					else
					{
						System.out.printf("%20s",rsmd.getColumnName(title));
					}
				}
				
			}

			System.out.println();
			for(int column=1;column<=columnCount;column++)
			{
				if(rsmd.getColumnType(column)==4)
				{
					System.out.printf("%15s",rs.getInt(column));
				}
				else if(rsmd.getColumnType(column)==12)
				{
					System.out.printf("%20s",rs.getString(column));
				}
				
				else
				{
					System.out.printf("%20s",rs.getString(column));
				}
			}
			System.out.println();
			space+=1;
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");

	}
	

}
