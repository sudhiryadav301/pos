package generic;

import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;

import io.restassured.path.json.JsonPath;

public class Util {
	public static String getProperty(String path,String key)
	{
		String value="";
		try 
		{
			Properties p=new Properties();
			p.load( new FileInputStream(path));
			value=p.getProperty(key);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return value;
	}
	public static String getTimeStamp()
	{
		 LocalDateTime currentDateTime = LocalDateTime.now();
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
	     String formattedDateTime = currentDateTime.format(formatter);
	     return formattedDateTime;
	}
	
	public static String getLocatorDetails(By by)
	{
		String msg="";
		try 
		{
			String FQCN = by.getClass().getCanonicalName();
			String locator=FQCN.split("By")[2];
			Field[] allF = by.getClass().getDeclaredFields();
			allF[0].setAccessible(true);
			String locatorValue = allF[0].get(by).toString();
			msg=locator+" as "+locatorValue;
		}
		catch (Exception e)
		{
		
		}
		return msg;
	}
	
	public static String[][] getDataFromCSVtoArray(String path)
	{
		String[][] data=null;
		
		try {
				FileReader f=new FileReader(path);
				CSVParser csvParser = CSVParser.parse(f,CSVFormat.DEFAULT);
				List<CSVRecord> allRecords = csvParser.getRecords();
				
				int rowCount=allRecords.size();
				int colCount=allRecords.get(0).size();
				
				data=new String[rowCount-1][colCount];
				
				for(int i=1;i<rowCount;i++)
				{
					CSVRecord record = allRecords.get(i);
					
					for(int j=0;j<colCount;j++)
					{
						String v = record.get(j);
						data[i-1][j]=v;
					}
		
				}
				csvParser.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static Iterator<String[]> getDataFromCSVtoIterator(String path)
	{
		ArrayList<String[]> data=new ArrayList<String[]>();
		
		try {
				FileReader f=new FileReader(path);
				CSVParser csvParser = CSVParser.parse(f,CSVFormat.DEFAULT);
				List<CSVRecord> allRecords = csvParser.getRecords();
				
				int rowCount=allRecords.size();
				int colCount=allRecords.get(0).size();

				for(int i=1;i<rowCount;i++)
				{
					CSVRecord record = allRecords.get(i);
					String[] a=new String[colCount];
					for(int j=0;j<colCount;j++)
					{
						String v = record.get(j);
						a[j]=v;
					}
		
					data.add(a);
				}
				csvParser.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Iterator<String[]> iData = data.iterator();
		return iData;
	}
	
	public static Iterator<String[]>  getDataFromJSONtoIterator(String path)
	{
		ArrayList<String[]> data=new ArrayList<String[]>();
		
		try
		{
		JsonPath json=new JsonPath(new FileInputStream(path));
		Map<String,List<String>> map=json.get();
		Set<String> allKeys = map.keySet();
		
		
		for(String key:allKeys)
		{
			List<String> list = map.get(key);
			int size=list.size();
			String[] s=new String[size];
			for(int i=0;i<size;i++)
			{
				String v=list.get(i);
				s[i]=v;
			}
			data.add(s);
		}
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		Iterator<String[]> iData = data.iterator();
		return iData;
	}
	
	public static String[][] getDataFromJSONtoArray(String path)
	{
		String[][] data=null;
		try
		{
		JsonPath json=new JsonPath(new FileInputStream(path));
		LinkedHashMap<String,ArrayList<String>> map=json.get();
		
		int rowCount=map.size();	
		data=new String[rowCount][];
		
		Set<String> allKeys = map.keySet();
		
		int i=0;
		
		for(String key:allKeys) 
		{
			ArrayList<String> value =map.get(key);
			int columnCount=value.size();
			data[i]=new String[columnCount];
			
			for(int j=0;j<columnCount;j++)
			{
				String v = value.get(j);
				data[i][j]=v;
			}
			
			i++;
		}
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static Iterator<String[]> getDataFromDBtoIterator(String dbURL,String un,String pw,String query)
	{
		ArrayList<String[]> data=new ArrayList<String[]>();
		try {
				Connection connection = DriverManager.getConnection(dbURL,un,pw);
				
				ResultSet resultSet = connection.createStatement().executeQuery(query);
				int colCount=resultSet.getMetaData().getColumnCount();
				
				while(resultSet.next())
				{
					String[] rec=new String[colCount];
					
					for(int i=1;i<=colCount;i++)
					{
						String v=resultSet.getString(i);
						rec[i-1]=v;
					}
		
					data.add(rec);
				}
				connection.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return data.iterator();
	}
	
}
