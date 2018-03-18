package com.yk.platform.common.util.poi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 导出excel工具类
 * <p>Description: </p>
 * @date 2015年11月30日
 * @author yangkai 
 * @version 1.0
 * <p>Company:bbk</p>
 * <p>Copyright:Copyright(c)2015</p>
 */
public class ExportUtil {
	
	private  Workbook workbook = null;  
	private  Sheet sheet = null;
	private  Row row = null;  
	private  Cell cell=null;  
	private String exportPath=null;  
	private CellStyle datecellStyle =null;   
	private CellStyle doublecellStyle =null;  
	private CellStyle fontAlignStyle=null;   
	private CellStyle titleStyle=null; 
	private DataFormat format =null;  
	private int totalCell;  
//	private static String NUMBER_FORMAT = "#,###.######"; 
	private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"; 
	private Drawing p=null;
	private CreationHelper helper=null;
	private RichTextString text=null;
	private  Comment comment=null;
	
	private static Logger logger = Logger.getLogger(ExportUtil.class.getName());

    /**
	 * 
	 * @param exportPath  导出路径
	 * @param totalCell  总列数
	 */
	public ExportUtil(String exportPath,int totalCell) {
		if(exportPath.toLowerCase().endsWith("xls")){
			this.workbook=new HSSFWorkbook();
		}else{
			this.workbook=new XSSFWorkbook();
		}
		this.totalCell=totalCell; 
		this.exportPath=exportPath; 
		this.sheet = this.workbook.createSheet();
		this.helper=this.workbook.getCreationHelper(); 
		this.format = this.workbook.createDataFormat(); 
		this.datecellStyle=this.workbook.createCellStyle(); 
		this.datecellStyle.setDataFormat(format.getFormat(DATE_FORMAT)); 
		this.datecellStyle.setAlignment(CellStyle.ALIGN_CENTER);  
		this.datecellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); 
		this.doublecellStyle=this.workbook.createCellStyle();  
		this.doublecellStyle.setAlignment(CellStyle.ALIGN_RIGHT);  
		this.doublecellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); 
		this.fontAlignStyle=workbook.createCellStyle();
		this.fontAlignStyle.setAlignment(CellStyle.ALIGN_CENTER);  
		this.fontAlignStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); 
		this.titleStyle=workbook.createCellStyle();
		this.titleStyle.setAlignment(CellStyle.ALIGN_CENTER);  
		this.titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); 
		this.titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		this.titleStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		
		for(int i=0;i<this.totalCell;i++){ 
			this.sheet.setColumnWidth(i, 6000);
		}
	}
	
	/**
	 * 构造方法
	 * @param exportPath 导出路径
	 * @param totalCell 总列数
	 * @param widthMap 列号-宽度映射
	 */
	public ExportUtil(String exportPath,int totalCell,Map<Integer, Integer> widthMap) {
		if(exportPath.toLowerCase().endsWith("xls")){
			this.workbook=new HSSFWorkbook();
		}else{
			this.workbook=new XSSFWorkbook();
		}
		this.totalCell=totalCell; 
		this.exportPath=exportPath; 
		this.sheet = this.workbook.createSheet();
		this.helper=this.workbook.getCreationHelper(); 
		this.format = this.workbook.createDataFormat(); 
		this.datecellStyle=this.workbook.createCellStyle(); 
		this.datecellStyle.setDataFormat(format.getFormat(DATE_FORMAT)); 
		this.datecellStyle.setAlignment(CellStyle.ALIGN_CENTER);  
		this.datecellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); 
		this.doublecellStyle=this.workbook.createCellStyle();  
		this.doublecellStyle.setAlignment(CellStyle.ALIGN_RIGHT);  
		this.doublecellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); 
		this.fontAlignStyle=workbook.createCellStyle();
		this.fontAlignStyle.setAlignment(CellStyle.ALIGN_CENTER);  
		this.fontAlignStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); 
		this.titleStyle=workbook.createCellStyle();
		this.titleStyle.setAlignment(CellStyle.ALIGN_CENTER);  
		this.titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); 
		this.titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		this.titleStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		
		for(int i=0;i<this.totalCell;i++){ 
			this.sheet.setColumnWidth(i, widthMap.get(i));
		}
	}
	
	public Row createRow(int index) {
		 this.row = this.sheet.createRow(index);
		 return this.row;
	}
	
	
	public Comment createComment(String context){
		this.p= this.sheet.createDrawingPatriarch();
	    this.comment = this.p.createCellComment(this.p.createAnchor(0,0,0,0,(short)3,3,(short)5,6));
	    this.text= this.helper.createRichTextString(context);
	    this.comment.setString(text);
		this.comment.setAuthor("lizhiyong");
		return comment;
	}
	
	public void setTitle(int index, String templetePath,String templ_id,String value){
		this.cell= this.row.createCell(index);
	    this.cell.setCellType(Cell.CELL_TYPE_STRING);
	    this.cell.setCellValue(value);
	    String commentText=FieldsReader.getLevel2AttributeByName(templetePath, templ_id,value, "desc");
	    if(commentText!=null&&!commentText.trim().equals("")){//如果没有描述则不添加批注
	    	Comment c= createComment(commentText);
	  	    this.cell.setCellComment(c);
	    }
	}
	
	public void setCell(int index, int value) {
		this.cell = this.row.createCell((short) index);
		this.cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		this.cell.setCellValue(value);
		this.cell.setCellStyle(this.fontAlignStyle);
	}
	
	public void setCell(int index, String value){
		this.cell= this.row.createCell(index);
		this.cell.setCellType(Cell.CELL_TYPE_STRING);
		this.cell.setCellValue(value);
		this.cell.setCellStyle(this.fontAlignStyle);
	}
	
	public void setCell(int index, Character value) {
		this.cell = this.row.createCell((short) index);
		this.cell.setCellType(Cell.CELL_TYPE_STRING);
		this.cell.setCellValue(value);
		this.cell.setCellStyle(this.fontAlignStyle);
	}
	
	public void setCell(int index, boolean value){
		this.cell= this.row.createCell(index);
		this.cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
		this.cell.setCellValue(value);
		this.cell.setCellStyle(this.fontAlignStyle);
	}
	
	public void setCell2(int index) {
        this.cell = this.row.createCell((short) index);
        this.cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        this.cell.setCellStyle(this.fontAlignStyle);
    }
	
	public void setCell(int index, double value) {
		this.cell = this.row.createCell((short) index);
		this.cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		this.cell.setCellValue(value);
		this.cell.setCellStyle(this.doublecellStyle);
	}
	
	public void setCell(int index, float value) {
		this.cell = this.row.createCell((short) index);
		this.cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		this.cell.setCellValue(value);
		this.cell.setCellStyle(this.doublecellStyle);
	}
	
	public void setCell(int index, Long value) {
		this.cell = this.row.createCell((short) index);
		this.cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		this.cell.setCellValue(value);
		this.cell.setCellStyle(this.fontAlignStyle);
	}
	
	public void setCell(int index,Date value) {
		this.cell = this.row.createCell((short) index);
		this.cell.setCellValue(value);
		this.cell.setCellStyle(this.datecellStyle);  
	}
	
	/**
     * 创建标题
     * @Description createHead
     * @param headString   
     * @param firstRow   
     * @param lastRow    
     * @param firstCol   
     * @param lastCol    
     * @return
     */
    public  boolean createHead(String headString,int firstRow,int lastRow,int firstCol,int lastCol) {
        boolean result=false;
        try {
            this.row=this.sheet.createRow(firstRow);
            this.cell = this.row.createCell(0);
            this.row.setHeight((short) 400);
            setCell(0, headString); 
            CellRangeAddress cellRangeAddress=new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
            sheet.addMergedRegion(cellRangeAddress);
            CellStyle cellStyle=workbook.createCellStyle();
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);  
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); 
            Font font = workbook.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setFontName("宋体");
            font.setFontHeight((short) 300);
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
            result=true;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return result;
    }

	 
	public void setSheetName(int index,String name){
	    workbook.setSheetName(index, name);
	}
	
	public boolean export(){
		boolean result=false;
		OutputStream fos=null;
		try {
		    fos=new FileOutputStream(this.exportPath);
			workbook.write(fos);
			fos.flush();
			result=true;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}finally{
			try {
				if(fos!=null){
					fos.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return result;
	}
    
	/**
	 * 
	 * @Description doExport 导出excel
	 * @param templetePath  
	 * @param templ_name     
	 * @param exportPath     
	 * @param list          
	 */
	public static void doExport(String templetePath,String templ_id, String exportPath, List<?> list){
		int newRown; 
		String titles=FieldsReader.getTitles(templetePath, templ_id);  //获得列名
        String[] colNames=titles.split(",");
        Map<String, String> map=FieldsReader.getTempleteMap(templetePath, templ_id);  //获得列名-属性映射对象
	    int totalCell=colNames.length; 
        int totalRow=0;
        if(list!=null){
        	totalRow=list.size();
        }
        
        Map<Integer, Integer> widthMap=FieldsReader.getWidthMap(templetePath, templ_id);
        ExportUtil writerUtil=new ExportUtil(exportPath,totalCell,widthMap);
        String sheetName=FieldsReader.getLevel1AttributeByName(templetePath, templ_id, "name");
        writerUtil.setSheetName(0, sheetName); 
        newRown= writerUtil.sheet.getPhysicalNumberOfRows();  
        writerUtil.createRow(newRown); 
        writerUtil.sheet.createFreezePane( 0, 1, 0, 1 ); //冻结第一行
        for(int cellN=0;cellN<totalCell;cellN++){  //设置列名，批注
            writerUtil.setTitle(cellN, templetePath,templ_id,colNames[cellN]);
        }
        Object obj=null;      
        Object value=null;   
        String colName=null; 
        String fieldName=null;  
        newRown= writerUtil.sheet.getPhysicalNumberOfRows();
        for(int i=0;i<totalRow;i++){  
            obj=list.get(i);
            writerUtil.createRow(i+newRown); 
            for(int j=0;j<totalCell;j++){ 
                 colName=colNames[j]; 
                 fieldName= map.get(colName); 
                 value= FieldsReader.getFiledValue(obj, fieldName); 
                 if(value==null){
                	 logger.error(fieldName +"'s value is null");
                 }else{
                	 if(value instanceof String){
                         writerUtil.setCell(j, (String)value);
                     }else if(value instanceof Integer){
                         writerUtil.setCell(j, (Integer)value);
                     }else if(value instanceof Double){
                         writerUtil.setCell(j, (Double)value);
                     }else if(value instanceof Date){
                         writerUtil.setCell(j, (Date)value);
                     }else if(value instanceof Boolean){
                         writerUtil.setCell(j, (Boolean)value);
                     }else if(value instanceof Long){
                         writerUtil.setCell(j, (Long)value);
                     }else if(value instanceof Float){
                         writerUtil.setCell(j, (Float)value);
                     }else if(value instanceof BigDecimal){
                         writerUtil.setCell(j, new BigDecimal(value.toString()).doubleValue());
                     }
                     else{
                     	logger.error(value +"is not supported type");
                     } 
                 }
            }
        }
        writerUtil.export();
	}
}
