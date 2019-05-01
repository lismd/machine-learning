package myapriori;
      
import java.io.IOException;      
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;      
     
import org.apache.poi.hssf.usermodel.HSSFCell;      
import org.apache.poi.hssf.usermodel.HSSFRow;      
import org.apache.poi.hssf.usermodel.HSSFSheet;      
import org.apache.poi.hssf.usermodel.HSSFWorkbook;      
import org.apache.poi.poifs.filesystem.POIFSFileSystem;      
     
/**    
 * ��ȡExcel���Ĺ�����        
 */     
public class ExcelReader {      
    private POIFSFileSystem fs;      
    private HSSFWorkbook wb;      
    private HSSFSheet sheet;      
    private HSSFRow row;      
    /**    
     * ��ȡExcel����ͷ������    
     * @param InputStream    
     * @return String[] ��ͷ���ݵ�����    
     *     
     */     
    public String[] readExcelTitle(InputStream is) {      
        try {      
            fs = new POIFSFileSystem(is);      
            wb = new HSSFWorkbook(fs);      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
        sheet = wb.getSheetAt(0);    
        row = sheet.getRow(0);      
        //����������colNum������ı�colNum=18(���ܹ���18�У�      
        int colNum = row.getPhysicalNumberOfCells();      
        String[] title = new String[colNum];      
        for (int i=0; i<colNum; i++) {      
            title[i] = getStringCellValue(row.getCell(i)); 
        }      
        return title;      
    }      
          
    /**    
     * ��ȡExcel��������    
     * @param InputStream    
     * @return List<NData> ������Ԫ���������ݵ�java��������    
     */     
    public List<NData>  readExcelContent(InputStream is) {  
    	ArrayList<NData> datas = new ArrayList<NData>();// ���庯�����         
        try {      
            fs = new POIFSFileSystem(is);      
            wb = new HSSFWorkbook(fs);      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
        sheet = wb.getSheetAt(0);      
        //�õ�������rowNum      
        int rowNum = sheet.getLastRowNum();      
        row = sheet.getRow(0);      
        //int colNum = row.getPhysicalNumberOfCells();    ����ı�colNum=18(���ܹ���18�У�  
        //��������Ӧ�ôӵڶ��п�ʼ,��һ��Ϊ��ͷ�ı���      
        for (int i = 1; i <=rowNum; i++) {      
            row = sheet.getRow(i);
            NData d=new NData();
            d.setA(row.getCell(0).getStringCellValue());
            d.setB(row.getCell(1).getStringCellValue());
            d.setC(row.getCell(2).getStringCellValue());
            d.setD(row.getCell(3).getStringCellValue());
            d.setE(row.getCell(4).getStringCellValue());
            d.setF(row.getCell(5).getStringCellValue());
            d.setG(row.getCell(6).getStringCellValue());
            d.setH(row.getCell(7).getStringCellValue());
            d.setI(row.getCell(8).getStringCellValue());
            d.setJ(row.getCell(9).getStringCellValue());
            d.setK(row.getCell(10).getStringCellValue());
            d.setL(row.getCell(11).getStringCellValue());
            d.setM(row.getCell(12).getStringCellValue());
            d.setN(row.getCell(13).getStringCellValue());
            d.setO(row.getCell(14).getStringCellValue());
            d.setP(row.getCell(15).getStringCellValue());
            d.setQ(row.getCell(16).getStringCellValue());
            d.setIndex((int) row.getCell(17).getNumericCellValue());
            datas.add(d);
        }      
        return datas;      
    }      
          
    /**    
     * ��ȡ��Ԫ����������Ϊ�ַ������͵�����    
     * @param cell Excel��Ԫ��    
     * @return String ��Ԫ����������    
     */     
    private String getStringCellValue(HSSFCell cell) {      
        String strCell = "";      
        switch (cell.getCellType()) {      
        case HSSFCell.CELL_TYPE_STRING:      
            strCell = cell.getStringCellValue();      
            break;      
        case HSSFCell.CELL_TYPE_NUMERIC:      
            strCell = String.valueOf(cell.getNumericCellValue());  
            break;      
        case HSSFCell.CELL_TYPE_BOOLEAN:      
            strCell = String.valueOf(cell.getBooleanCellValue());      
            break;      
        case HSSFCell.CELL_TYPE_BLANK:      
            strCell = "";      
            break;      
        default:      
            strCell = "";      
            break;      
        }      
        if (strCell.equals("") || strCell == null) {      
            return "";      
        }        
        return strCell;      
    }      
        
}   
