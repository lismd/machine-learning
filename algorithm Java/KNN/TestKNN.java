package KNN;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


// KNN�㷨������ 


public class TestKNN {

	/** * ����ִ����� * @param args */

	public static void main(String[] args) {
		int k=30;//����k
		///////////////////////////////////////////////////////////////////////
		List<List<Double>> datas = new ArrayList<List<Double>>();//����ѵ�����ݼ�
		//��Excel���ж�����Ϊ��������ݼ�����ExcelReader�ࣩ
		MyExcelReader excelReader = new MyExcelReader();
        try {           
            //��ȡExcel�����������    
            InputStream is = new FileInputStream("F://KNN//xunlian.xls");
            datas= excelReader.readExcelContent(is);
            int n=datas.size();
            System.out.println("�ѻ��Excel��������:"+n+"��ѵ������");           
        } catch (FileNotFoundException e) {      
            System.out.println("δ�ҵ�ָ��·����ѵ�������ļ�!");      
            e.printStackTrace();      
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////
        List<List<Double>> testDatas = new ArrayList<List<Double>>();//����������ݼ�
        try {           
            //��ȡExcel�����������    
            InputStream istest = new FileInputStream("F://KNN//ceshi.xls");
            testDatas= excelReader.readExcelContentTest(istest);
            int ntest=testDatas.size();
            System.out.println("�ѻ��Excel��������:"+ntest+"����������");           
        } catch (FileNotFoundException e) {      
            System.out.println("δ�ҵ�ָ��·���Ĳ��������ļ�!");      
            e.printStackTrace();      
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////
        List<Integer> normalIndicats = new ArrayList<Integer>();
        try {           
            //��ȡExcel�����������    
            InputStream istest = new FileInputStream("F://KNN//ceshi.xls");
            normalIndicats= excelReader.readExcelIndicat(istest);
            int ntest=testDatas.size();
            System.out.println("�ѻ��Excel��������:"+ntest+"�����ݵ�����ָʾ��ʶ");           
        } catch (FileNotFoundException e) {      
            System.out.println("δ�ҵ�ָ��·���Ĳ��������ļ�!");      
            e.printStackTrace();      
        }
        ///////////////////////////////////////////////////////////////////////////////////////////
		//��ģ������ʱ��Ҫ����A,B,C,D�ĸ�ģ��
		int A,B,C,D;
		A=0;
		B=0;
		C=0;
		D=0;
        ///////////////////////////////////////////////////////////////////////////////////////////
        //��ģ�ͶԼ�����ݵļ��������Excel�����
            // ��һ��������һ��webbook����Ӧһ��Excel�ļ�
     		HSSFWorkbook wb = new HSSFWorkbook();
     		// �ڶ�������webbook�����һ��sheet,��ӦExcel�ļ��е�sheet
     		HSSFSheet sheet = wb.createSheet("sheet1");
     		// ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������short
     		HSSFRow row = sheet.createRow((int) 0);
     		// ���Ĳ���������Ԫ�񣬲�����ֵ��ͷ ���ñ�ͷ����
     		HSSFCellStyle style = wb.createCellStyle();
     		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����һ�����и�ʽ

     		HSSFCell cell = row.createCell(0);
     		cell.setCellValue("Index");
     		cell.setCellStyle(style);
     		cell = row.createCell(1);
     		cell.setCellValue("ʵ�������쳣��ʶ");
     		cell.setCellStyle(style);
     		cell = row.createCell(2);
     		cell.setCellValue("��������쳣��ʶ");
     		cell.setCellStyle(style);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        KNN knn = new KNN();
        int index=606;//��һ���������ݵ�index
		for (int i = 0; i < testDatas.size(); i++) {
			List<Double> test = testDatas.get(i);//��õ�i����������
			System.out.println("����Ԫ��: ");
			for (int j = 0; j < test.size(); j++) {
				System.out.print(test.get(j) + " ");
			}
			System.out.print("���Ϊ: ");
			int detectIndicat=Math.round(Float.parseFloat((knn.knn(datas, test, k))));//�ò������ݵļ�������쳣��ʶ
			System.out.println(detectIndicat);
			////////////////////////////////////////////////////////////////////////////////////////////
			// ���岽��д���ÿ�е�����
			row = sheet.createRow((int) i + 1);
			//������Ԫ�񣬲�����ֵ
			row.createCell(0).setCellValue(index);
			int normalIndicat=normalIndicats.get(i);
			row.createCell(1).setCellValue(normalIndicat);
			row.createCell(2).setCellValue(detectIndicat);
			index++;
			//////////////////////////////////////////////////////////////////////////////////////////////
    		///////////////////////////////////////////////////////////////////////////////////
    		//��ģ�ͽ������۸�ģ����������
    			if (normalIndicat==1&&detectIndicat==1)
    				A++;
    			else if(normalIndicat==1&&detectIndicat==0)
    				B++;
    			else if(normalIndicat==0&&detectIndicat==1)
    				C++;
    			else
    				D++;
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////
		// �����������ļ��浽ָ��λ��
		try {
			FileOutputStream fout = new FileOutputStream("F://KNN//KNNDetectResult.xls");
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("������Ѿ��洢��Excel���F://KNN//KNNDetectResult.xls��");
		/////////////////////////////////////////////////////////////////////////////////////////////////
		//ģ������ָ�����
		System.out.println("ʵ�������������"+A+"��");
		System.out.println("ʵ����������쳣"+B+"��");
		System.out.println("ʵ���쳣�������"+C+"��");
		System.out.println("ʵ���쳣����쳣"+D+"��");
		double mingzhong=D/(1.0*(B+D));
		System.out.println("������Ϊ"+mingzhong);
		double fugai=D/(1.0*(C+D));
		System.out.println("������Ϊ"+fugai);
		double accury=(A+D)/(1.0*(A+B+C+D));
		System.out.println("����׼ȷ��Ϊ"+accury);
	}
}
