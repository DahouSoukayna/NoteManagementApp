package com.gsnotes.utils.export;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelExporter {
	
	static final int CING_X=12;
	static final int CING_Y=8;
	static final int CPREP_X=10;
	static final int CPREP_Y=7;
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	
	private String[] headerColNames;
	private String[] headerData;
	private String[] columnNames;
	private String[][] data;
	private double[] currentCoefs;
	private String sheetName = "";

	
	public ExcelExporter(String[] columnNames, String[][] data, String sheetName) {
		this.headerColNames = null;
		this.headerData = null;
		this.columnNames = columnNames;
		this.data = data;
		this.currentCoefs = null;
		this.sheetName = sheetName;
		workbook = new XSSFWorkbook();

	}
	
	public ExcelExporter(String[] headerColNames,String[] headerData, String[] columnNames, String[][] data, double[] currentCoefs, String sheetName) {
		this.headerColNames = headerColNames;
		this.headerData = headerData;
		this.columnNames = columnNames;
		this.data = data;
		this.currentCoefs = currentCoefs;
		this.sheetName = sheetName;
		workbook = new XSSFWorkbook();

	}
	
	
	

	private void writeHeaderLine() {
		sheet = workbook.createSheet(sheetName);

		Row row0 = sheet.createRow(0);
		Row row1 = sheet.createRow(1);
		Row row2 = sheet.createRow(2);
		Row row3 = sheet.createRow(3);

		//style du 1er header
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());  
        style.setFillPattern(FillPatternType.BIG_SPOTS);
		
		//style of normal data
		CellStyle style2 = workbook.createCellStyle();
		XSSFFont font2 = workbook.createFont();
		font2.setFontHeight(14);
		style2.setFont(font2);
				
				
		//header 1
		int m = 0; //numero col pour row1
		if(headerColNames != null) {
			//k: pour parcourir les tableaux de données headerColNames, headerData || j: pour numero de colonne 
			for(int j=0,k=0; j<headerColNames.length*2; j=j+2,k++) {
				if(j<6) {
					createCell(row0, j, headerColNames[k], style); //remplir row0 par headerName et ses données: 3 premiers cases de chaque tableau(headercol, headerData) 
//																	j=0,k=0:: col0-head0 / col1-data0
//																	j=2;k=1  col2-head1 / col3-data1
//																	j=4;k=2; col4-head2 / col5-data2
//																	j=6;k=3; col0-head3 / col1 
					createCell(row0,j+1, headerData[k],style2);
					//k=j-1;
				}else {
					createCell(row1, m, headerColNames[k], style);
					createCell(row1,(m+1), headerData[k],style2);
					m+=2;
					
				}
				
			}
		}
		
		
		//header 2
		int i = 0;
		for (String it : columnNames) {
			createCell(row3, (i++), it, style);
		}

	}
	
	
	
	
	
//methide de creation de Cell
	private Cell createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
		
		return cell;
	}
	
	
	
//methode pour data
	private void writeDataLines() {
		int rowCount = 4;//4 premiers sont reserves pour header

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		
		
		//embarquer les formulas dans les colonnes de la Moyenne et la validation
		
		int moyCell = data[0].length -1;              //get index of moyenne column
		int valdCell = data[0].length ;					//index of validation column
		String debColName= getColumnName(5);	 	 //get columnName: G,F,E,.. change selon nbr des elements pour index=5 apres data of student
		String endColName= getColumnName(moyCell-1); //chaque module pour les utiliser dans la formule de calcul
		Cell cell;											//to set formula into specific cell
		String moyFormula="", valdFormula="";
		for (int i = 0; i < data.length; i++) {
			Row row = sheet.createRow(rowCount++);  //pour chaque ligne des donnees create a row in excel file
			int columnCount = 0;
			for (int j = 0; j < data[i].length; j++) {        
				cell = createCell(row, columnCount++, data[i][j], style);  //rempli data qu'on entre au constructeur
				
				
				//si on arrive à la colonne de la moyenne 
				if(columnCount == moyCell) {
					CellReference cellReference = new CellReference(cell);//utiliser pour savoir cell col/row exactement 
					String thisR = cellReference.getCellRefParts()[1]; 
					//prapare formula
					//String coef = String.valueOf(currentCoefs[0]).replace(".", ",");
					
					//on initialise la formule par cell of element 1 info
					moyFormula = "SUM(PRODUCT("+debColName + thisR + "," + currentCoefs[0]+")"; //debcol index 5
					//k=6:index of element2 col  || c: pour parcourir les currentCoefs of elements in the same order of elments
					int c=1;
					for(int k=6; k<moyCell && c<currentCoefs.length; k++) {
						String colName = getColumnName(k);
						//coef = String.valueOf(currentCoefs[c]).replace(".", ",");
						moyFormula += ",PRODUCT("+colName + thisR + "," + currentCoefs[c] +")";
						c++;
					}
					moyFormula += ")"; //fermeture de SUM
					
					
	                cell.setCellFormula(moyFormula);
	                
				}else if(columnCount == valdCell) {
					CellReference cellReference = new CellReference(cell);
					String thisR = cellReference.getCellRefParts()[1]; 
					//prepare validation formula
					//valdFormula ="IF("+getColumnName(moyCell)+thisR+">="+CING_X+",\"V\",\"R\")" ;
					if(headerData[4].equals("Normale") && headerData[5] != "CP1" && headerData[5]!= "CP2") { //
						valdFormula ="IF("+getColumnName(moyCell)+thisR+">="+CING_X+",\"V\",\"R\")" ;
					}else if(headerData[4].equals("Rattrapage") && headerData[5] != "CP1" && headerData[5]!= "CP2") {
						valdFormula ="IF("+getColumnName(moyCell)+thisR+">="+CING_X+",\"V\",\"NV\")" ;
					}else if(headerData[4].equals("Normale") && (headerData[5] == "CP1" || headerData[5]== "CP2")) {
						valdFormula ="IF("+getColumnName(moyCell)+thisR+">="+CPREP_X+",\"V\",\"R\")" ;
					}else if(headerData[4].equals("Rattrapage") && (headerData[5] == "CP1" || headerData[5]== "CP2")) {
						valdFormula ="IF("+getColumnName(moyCell)+thisR+">="+CPREP_X+",\"V\",\"NV\")" ;
					}
					
	                cell.setCellFormula(valdFormula);
	               // =SUM(INDEX(data,0,COLUMNS(data)-(n-1)):INDEX(data,0,COLUMNS(data)))
				}
			}
		}

	}
	
	

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}
	
	
	
	private void dataStyle() {
		
	}
	
	
	
	// Function to convert a given number to an Excel column
    private String getColumnName(int n)
    {
        // initialize output string as empty
        StringBuilder result = new StringBuilder();
 
        while (n > 0)
        {
            // find the index of the next letter and concatenate the letter
            // to the solution
 
            // here index 0 corresponds to `A`, and 25 corresponds to `Z`
            int index = (n - 1) % 26;
            result.append((char)(index + 'A'));
            n = (n - 1) / 26;
        }
 
        return result.reverse().toString();
    }
	
	
}
