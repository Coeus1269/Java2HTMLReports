package Java2HTMLReports;

import java.util.LinkedHashMap;
import StringUtils.StringUtils;


public class Java2HTMLReportBuilder 
{
	// This builder will automatically build the order by from the columns in the selections and append any specified order by to it.
	// Table formatting can be Managed with css, i.e.; table, th, td {border: 1px solid black; border-collapse: collapse; width: 80%; text-align: center; table-layout: fixed;	}
	// Row Hover - tr:hover {background-color: #f5f5f5;}
	// Striped Tables - tr:nth-child(even) {background-color: #f2f2f2;}
	// set position of table caption - caption {  caption-side: bottom; }
	// table header specific - th {  padding-top: 12px; padding-bottom: 12px; text-align: left; background-color: #4CAF50; color: white;	}
	
	// Font attributes can be Managed with css; font, size, color etc  each section is named
	// Section names:		Style Sheet Example p.ReportHeader { color: blue; font-family: impact; font-size: 300%; background-color:lightblue; border: 5px solid yellow; width: 80%;}
	//		p.ReportTitle		
	//		p.ReportHeader	
	//		p.ReportFooter
	
	// TODO: auto calculate data table width % based on total size of biggest data elements in each column
	// TODO: calculate pages for printing
	

//	public class ReportAtrributes
//		{
		public String Title_str = "Report Main Title";
		public Integer TitleFontSize_int = 12;
		public String TitleAlign_str = "center"; 		
		public String TitleFontSize_str = "<font size=" + TitleFontSize_int + ">";
		
		public String Header_str = "Header";
		public Integer HeaderFontSize_int = 6;
		public String HeaderAlign_str = "left"; 		
		public String HeaderFontSize_str = "<font size=" + HeaderFontSize_int + ">";
		
		public String Footer_str = "Footer";
		public Integer FooterFontSize_int = 4;
		public String FooterAlign_str = "right"; 		
		public String FooterFontSize_str = "<font size=" + FooterFontSize_int + ">";
		
		// public String DataTableAlign_str = "center";			 Always centered
		public String DataTableRowAlign_str = "center";
		public String DataTableName_str = "wg_DataTable";
		public String DataTableWidthPercent_str = "80";
		public Integer DataTableBorder_int = 1;
		public String DataTableBorder_str = "border='" + DataTableBorder_int + "';";
		public String DataTableCaption_str = "";
		
//		
//		}
		
	
//	private ReportAtrributes Report_obj = new ReportAtrributes();
//	private ReportAtrributes Page_obj = new ReportAtrributes();

	
	private LinkedHashMap<String, Integer> Sections_lhm = new LinkedHashMap<String, Integer>();	
																		// Format, Insert order specific: Key = Column name of section to split on, 
																		//		Value = boolean value for weather to include sums at end of section section
																		// subsequent sections will split within the previous one.
																		// TODO: how to store columns for sums apply functions to
	private String OrderBy_str = "";
	private String UserOrderBy_str = "weight_int, height_int";			// format= ( column, column, etc ) - no leading or ending commas no parentheses
	
	private LinkedHashMap<String, String> DataTableColumns_lhm = new LinkedHashMap<String, String>();	
																		// This is the list in order of columns to display in the data table
																		// Format, Insert order specific: Key = Data Column name, Value = Table Header Name to display
																		
	private String TableHeader_str = "";

	public static void main(String[] args) 
		{
		// Test console display
		Java2HTMLReportBuilder StaticIsStupid = new Java2HTMLReportBuilder();
		
		StaticIsStupid.Sections_lhm.put("name_str", 0);
		StaticIsStupid.Sections_lhm.put("age_int", 1);
		StaticIsStupid.Sections_lhm.put("gender_str", 0);
		
		// System.out.println(StaticIsStupid.BuildOrderBy());
		
		StaticIsStupid.DataTableColumns_lhm.put("name_str", "Name");
		StaticIsStupid.DataTableColumns_lhm.put("age_int", "Age");
		StaticIsStupid.DataTableColumns_lhm.put("gender_str", "Gender");
			
		StaticIsStupid.BuildTableHeader();
		
		StaticIsStupid.HTMLFormatedReport();
	
		}
	

	
	public String HTMLFormatedReport()
		{
		String tempDataTableCaption_str = "";
		if(StringUtils.StringNotEmptyAndNotNull(DataTableCaption_str))
			tempDataTableCaption_str = "<caption>" + DataTableCaption_str  + " </caption>";
		
		
		System.out.println("<p class='ReportTitle'>" + Title_str + "</p>");
		System.out.println("<p class='ReportHeader'>" +  Header_str + "</p>");
		System.out.println("<div style='overflow-x:auto;'>");
		System.out.println("<table >\n" + tempDataTableCaption_str + 
				"<tbody>\n" + 
				"<tr style=\"text-align: center;\">\n" + 
				TableHeader_str +
//				"	<th>Name</th>\n" + 
//				
//				"	<th>Age</th>\n" + 
//				"	<th>Gender</th>\n" + 
				"</tr>\n" + 
				"<tr style=\"text-align: center;\">\n" + 
				"	<td>Bob</td>\n" + 
				"	<td>23</td>\n" + 
				"	<td>M</td>\n" + 
				"</tr>\n" + 
				"<tr style=\"text-align: center;\">\n" + 
				"	<td>Kim</td>\n" + 
				"	<td>31</td>\n" + 
				"	<td>F</td>\n" + 
				"</tr>\n" + 
				"</tbody>\n" + 
				"</table> <br>");
		System.out.println("</div>");
		System.out.println("<p class='ReportFooter'>"  +  Footer_str + "</p>");
		
		
		return "UGH";
		}
	
	public String BuildOrderBy()
		{
		OrderBy_str = " order by";
		// System.out.println("Order By Columns ");
		
		for( String key : Sections_lhm.keySet() )	
			{
		    // System.out.println(" " + key + " - Value: " + Sections_lhm.get(key));
		    OrderBy_str += " " + key + ",";
			}
		
		if(StringUtils.StringNotEmptyAndNotNull(UserOrderBy_str))
			OrderBy_str = OrderBy_str + UserOrderBy_str;
		else
			OrderBy_str = OrderBy_str.substring(0, OrderBy_str.length() -1);
		return OrderBy_str;
			
		}
	
	public String BuildTableHeader()
	{	
	// Build the inner table header "<th>Key</th>"
	if(DataTableColumns_lhm.size() > 0)
		{
		for( String key : DataTableColumns_lhm.keySet() )	
			{
		    // System.out.println(" " + key + " - Value: " + );
			TableHeader_str += " <th>" + DataTableColumns_lhm.get(key) + "</th>";
			}
		}
	
	return TableHeader_str;
		
	}

}
