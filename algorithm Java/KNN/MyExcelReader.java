//�ܹ���ȡxunlian.xls��ceshi.xls
package KNN;
     
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
public class MyExcelReader {      
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
        //����������colNum������ı�colNum=9(���ܹ���9�У�      
        int colNum = row.getPhysicalNumberOfCells();      
        String[] title = new String[colNum];      
        for (int i=0; i<colNum; i++) {      
            title[i] = getStringCellValue(row.getCell(i)); 
        }      
        return title;      
    }      
          
    /**    
     * ��ȡѵ����������    
     * @param InputStream    
     * @return List<List<Double>> ������Ԫ���������ݵ�java��������    
     */     
    public List<List<Double>>  readExcelContent(InputStream is) {  
    	List<List<Double>> datas = new ArrayList<List<Double>>();      
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
        //int colNum = row.getPhysicalNumberOfCells();    ����ı�colNum=9(���ܹ���9�У�  
        //��������Ӧ�ôӵڶ��п�ʼ,��һ��Ϊ��ͷ�ı���      
        for (int i = 1; i <=rowNum; i++) {      
            row = sheet.getRow(i);
            List<Double> d = new ArrayList<Double>(); 
            d.add(row.getCell(1).getNumericCellValue());
            d.add(row.getCell(2).getNumericCellValue());
            d.add(row.getCell(3).getNumericCellValue());
            d.add(row.getCell(4).getNumericCellValue());
            d.add(row.getCell(5).getNumericCellValue());
            d.add(row.getCell(6).getNumericCellValue());
            d.add(row.getCell(7).getNumericCellValue());
            d.add(row.getCell(8).getNumericCellValue());//
            datas.add(d);
        }      
        return datas;      
    }  
    
    /**    
     * ��ȡ������������    
     * @param InputStream    
     * @return List<List<Double>> ������Ԫ���������ݵ�java��������    
     */ 
    public List<List<Double>>  readExcelContentTest(InputStream is) {  
    	List<List<Double>> testdatas = new ArrayList<List<Double>>();      
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
        //int colNum = row.getPhysicalNumberOfCells();    ����ı�colNum=9(���ܹ���9�У�  
        //��������Ӧ�ôӵڶ��п�ʼ,��һ��Ϊ��ͷ�ı���      
        for (int i = 1; i <=rowNum; i++) {      
            row = sheet.getRow(i);
            List<Double> d = new ArrayList<Double>(); 
            d.add(row.getCell(1).getNumericCellValue());
            d.add(row.getCell(2).getNumericCellValue());
            d.add(row.getCell(3).getNumericCellValue());
            d.add(row.getCell(4).getNumericCellValue());
            d.add(row.getCell(5).getNumericCellValue());
            d.add(row.getCell(6).getNumericCellValue());
            d.add(row.getCell(7).getNumericCellValue());
            testdatas.add(d);
        }      
        return testdatas;      
    }
    
    /**    
     * ��ȡExcel��������    
     * @param InputStream    
     * @return List<Integer> ��������һ�е�Ԫ���������ݵ�java��������    
     */     
    public List<Integer>  readExcelIndicat(InputStream is) {  
    	List<Integer> normalIndicat = new ArrayList<Integer>();      
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
        //int colNum = row.getPhysicalNumberOfCells();    ����ı�colNum=9(���ܹ���9�У�  
        //��������Ӧ�ôӵڶ��п�ʼ,��һ��Ϊ��ͷ�ı���      
        for (int i = 1; i <=rowNum; i++) {      
            row = sheet.getRow(i);
            normalIndicat.add((int) (row.getCell(8).getNumericCellValue()));
        }      
        return normalIndicat;      
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
