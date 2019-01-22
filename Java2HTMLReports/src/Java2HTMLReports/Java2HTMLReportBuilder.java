package Java2HTMLReports;

import java.util.LinkedHashMap;
import StringUtils.StringUtils;


public class Java2HTMLReportBuilder 
{
	// This builder will automatically build the 'order by' from the columns in the Sections_lhm and append any specified order by (OrderBy_str) to it.
	// Only columns defined in DataTableColumns_lhm will display
	// A section label (title, header, footer, etc.) of length 0 (zero) will not display
	// A divider of length 0 (zero) will not display
	
	// Table formatting can be Managed with css
	//			i.e.; table, th, td {border: 1px solid black; border-collapse: collapse; width: 80%; text-align: center; table-layout: fixed;	}
	// 			Row Hover - tr:hover {background-color: #f5f5f5;}
	//			Header color th {background-color: #FF6666;}
	// 			Striped Tables - tr:nth-child(odd) {background-color: #f2f2f2;}
	// 			set position of table caption - caption {  caption-side: bottom; }
	// 			table header specific - th {  padding-top: 12px; padding-bottom: 12px; text-align: left; background-color: #4CAF50; color: white;	}
	
	// Font attributes and formatting can be Managed with css; font, size, color, etc.  each section is named
	// Section names:		Style Sheet Example: p.ReportHeader { color: blue; font-family: impact; font-size: 300%; background-color:lightblue; border: 5px solid yellow; width: 80%;}
	//		p.ReportTitle		
	//		p.ReportHeader	
	//		p.ReportFooter	
	//		p.ReportPageNumber
	
	// example CSS - red theme
//	<style>
//		p.ReportTitle  { color: red; font-family: impact; font-size: 200%; background-color: grey; border: 2px solid red; width: 60%; text-align: right;}
//    	p.ReportHeader {font-size: 150%; text-align: center; text-shadow: 7px 6px 2px #FFCCCC;}
//	    p.ReportHeaderDivider {  text-align: center;}
//		table {width: 70%; margin-left: auto; margin-right: auto; }
//	    table, th, td {border: 1px solid black; border-collapse: collapse; cellpadding="5"; text-align: center; }
//	    th {background-color: #FF6666;}
//	    tr:nth-child(odd) {background-color: #f2f2f2;}
//	    caption {  caption-side: bottom;  font-size: 80%;}
//
//	    p.ReportFooter {font-size: 75%; text-align: center;}
//	    p.ReportPageNumber {font-size: 75%; text-align: right;}
//	</style>

	
	// TODO: auto calculate data table width % based on total size of biggest data elements in each column
	// TODO: calculate pages for printing page numbers
	

		private String Title_str = "Report Main Title &nbsp;"; 
		private int TitleDividerLineLength = 80;
		private String TitleDividerCharacter = "_";
		
		private String Header_str = "This is the Header for report info";
		private int HeaderDividerLineLength = 80;
		private String HeaderDividerCharacter = "_";
		
		private String Footer_str = "Footer"; 
		private int FooterDividerLineLength = 60;
		private String FooterDividerCharacter = ".";
		
		private String PageNumber_str = "1";
		
		private String DataTableCaption_str = "Table 1.1 Caption";
	
		// to be removed after testing
//		private Integer DataTableBorder_int = 1;
//		private String DataTableBorder_str = "border='" + DataTableBorder_int + "';";
//		private String DataTableRowAlign_str = "center";
//		private String DataTableName_str = "wg_DataTable";		
	
	
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
		
		System.out.println(StaticIsStupid.HTMLFormatedReport());
	
		}
	
	private String DividerLength(int Value_int, String DividerCharacter_str)
	{
		int Counter_int = 0;
		String TempString_str = "";
		
		while(Counter_int < Value_int)
			{
			TempString_str = TempString_str + DividerCharacter_str;
			Counter_int++;
			}
		return TempString_str;
		
	}
	
	public String HTMLFormatedReport()
		{
		// currently just a test output until all the methods are completed
		
		String tempDataTableCaption_str = "";
		String tempHTML_str = "";
		
		if(StringUtils.StringNotEmptyAndNotNull(DataTableCaption_str))
			tempDataTableCaption_str = "<caption>" + DataTableCaption_str  + " </caption>";
		
		if(Title_str.length()>0)
			tempHTML_str = tempHTML_str + "<p class='ReportTitle'>" + Title_str + "</p>\n";
		
		if(TitleDividerLineLength > 0)
			tempHTML_str = tempHTML_str + "<p class='ReportHeaderDivider'>" + DividerLength(TitleDividerLineLength, TitleDividerCharacter) + "</p>\n";

		if(Header_str.length() > 0)
			tempHTML_str = tempHTML_str + "<p class='ReportHeader'>" +  Header_str + "</p>\n" ;
		
		if(HeaderDividerLineLength > 0)
			tempHTML_str = tempHTML_str + "<p class='ReportHeaderDivider'>" + DividerLength(HeaderDividerLineLength, HeaderDividerCharacter) + "</p>\n";

		tempHTML_str = tempHTML_str + "<div style='overflow-x:auto;'>\n";
		tempHTML_str = tempHTML_str + "<table >\n"  + 
				"<tbody>\n" + 
				"<tr>\n" + 
				TableHeader_str +
//				"	<th>Name</th>\n" + 				
//				"	<th>Age</th>\n" + 
//				"	<th>Gender</th>\n" + 
				"</tr>\n" + 
				"<tr>\n" + 
				"	<td>Bob</td> " + 
				"	<td>23</td> " + 
				"	<td>M</td> " + 
				"</tr>\n" + 
				"<tr>\n" + 
				"	<td>Kim</td> " + 
				"	<td>31</td> " + 
				"	<td>F</td> " + 
				"</tr>\n" + 
				
				"<tr>\n" + 
				"	<td style=\"text-align: right;\">Average</td>\n" + 
				"	<td>27</td>\n" + 
				"	<td>&nbsp;</td>\n" + 
				"</tr>\n" + 

				"</tbody>\n" + tempDataTableCaption_str + 
				"</table>";
		tempHTML_str = tempHTML_str + "</div>\n";
		
		if(FooterDividerLineLength > 0)
			tempHTML_str = tempHTML_str + "<p class='ReportHeaderDivider'>" + DividerLength(FooterDividerLineLength, FooterDividerCharacter) + "</p>\n";

		if(Footer_str.length() > 0)
			tempHTML_str = tempHTML_str + "<p class='ReportFooter'>"  +  Footer_str + "</p>\n";
		
		tempHTML_str = tempHTML_str + "<p class='ReportPageNumber'>  Page Number: " + PageNumber_str + "</p>\n";
	
		return tempHTML_str;
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
