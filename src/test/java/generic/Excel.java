package generic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excel {
	public static int getRowCount(String path,String sheet)
	{
		int rc=0;
		try 
		{
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			rc=wb.getSheet(sheet).getLastRowNum();
			wb.close();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		return rc;
	}
	public static int getColumnCount(String path,String sheet,int r)
	{
		int cc=0;
		try 
		{
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			cc=wb.getSheet(sheet).getRow(r).getLastCellNum();
			wb.close();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		return cc;
	}
	public static String getCellData(String path,String sheet,int r,int c)
	{
		String v="";
		try 
		{
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			v=wb.getSheet(sheet).getRow(r).getCell(c).toString();
			wb.close();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		return v;
	}
	public static void writeDataToCell(String path,String sheet,int r,int c,String value)
	{
		try {	
				Workbook wb = WorkbookFactory.create(new FileInputStream(path));
				try 
				{
					wb.getSheet(sheet).getRow(r).createCell(c).setCellValue(value);
				}
				catch (NullPointerException e) 
				{
					wb.getSheet(sheet).createRow(r).createCell(c).setCellValue(value);
				}
				wb.write(new FileOutputStream(path));
				wb.close();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		
	}	
	public static void writeDataToCell(String path,String sheet,int r,int c,String value,boolean makeItRed)
	{
		try {	
				Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			       CellStyle style = wb.createCellStyle();
			       Font font = wb.createFont();
			       if(makeItRed)
			       {
			       font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
			       }
			       else
			       {
			    	   font.setColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());   
			       }
			       style.setFont(font);
				try 
				{
					Cell cell = wb.getSheet(sheet).getRow(r).createCell(c);
					cell.setCellValue(value);
					cell.setCellStyle(style);
				}
				catch (NullPointerException e) 
				{
					Cell cell = wb.getSheet(sheet).createRow(r).createCell(c);
					cell.setCellValue(value);
					cell.setCellStyle(style);
				}
				wb.write(new FileOutputStream(path));
				wb.close();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		
	}	
	public static ArrayList<String[]> getSheetDataToList(String path,String sheet)
	{

		ArrayList<String[]> list=new ArrayList<String[]>();
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			int rc=wb.getSheet(sheet).getLastRowNum();
			for(int i=1;i<=rc;i++)
			{
				try {
						int cc=wb.getSheet(sheet).getRow(i).getLastCellNum();
						String row[]=new String[cc];
						for(int j=0;j<cc;j++)
						{
							String v="";
							try 
							{
							  v=wb.getSheet(sheet).getRow(i).getCell(j).toString();
							}
							catch (NullPointerException e) {// blank cell
								System.err.println(e.getMessage());
							}
							row[j]=v;
						}
						list.add(row);
				}
				catch (NullPointerException e) {//blank row
					System.err.println(e.getMessage());
				}	
			}
			wb.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return list;
	}
	public static LinkedHashMap<String,String> getDataToMap(String path,String sheet,int row)
	{
		
		LinkedHashMap<String,String> map=new LinkedHashMap<String,String>();
		try 
		{
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			int cc =wb.getSheet(sheet).getRow(row).getLastCellNum();
			for(int i=0;i<cc;i++)
			{
				String key=wb.getSheet(sheet).getRow(0).getCell(i).toString();
				String value=wb.getSheet(sheet).getRow(row).getCell(i).toString();
				map.put(key, value);
			}
			wb.close();
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage());
		}
		return map;	
	}
	
}