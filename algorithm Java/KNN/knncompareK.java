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

public class knncompareK {

	public static void main(String[] args) {
		/**
		 * ��Բ�ͬ�Ĳ���k ����ѵ��������KNNģ�ͼ��������ݼ����쳣���ݣ�����У�����ĸ�����ָ��浽Excel�������� �������£�
		 * 
		 * @param int
		 *            k ��Ϊ�趨�Ĳ���k���������趨��kֵС��200(��Ĺ�������������ݻᶼ�������ģ�
		 * @return ������浽Excel��������
		 */
		///////////////////////////////////////////////////////////////////////
		// ��Ϊ�趨����k
		// ����ѭ���ı�kֵ�Ӷ��ﵽ���õļ��Ч��
		///////////////////////////////////////////////////////////////////////
		List<List<Double>> datas = new ArrayList<List<Double>>();// ����ѵ�����ݼ�
		// ��Excel���ж�����Ϊ��������ݼ�����ExcelReader�ࣩ
		MyExcelReader excelReader = new MyExcelReader();
		try {
			// ��ȡExcel�����������
			InputStream is = new FileInputStream("F://5folder//KNN//xunlian05.xls");
			datas = excelReader.readExcelContent(is);
			int n = datas.size();
			System.out.println("�ѻ��Excel��������:" + n + "��ѵ������");
		} catch (FileNotFoundException e) {
			System.out.println("δ�ҵ�ָ��·����ѵ�������ļ�!");
			e.printStackTrace();
		}
		////////////////////////////////////////////////////////////////////////////////////////////////
		List<List<Double>> testDatas = new ArrayList<List<Double>>();// ����������ݼ�
		try {
			// ��ȡExcel�����������
			InputStream istest = new FileInputStream("F://5folder////KNN//ceshi05.xls");
			testDatas = excelReader.readExcelContentTest(istest);
			int ntest = testDatas.size();
			System.out.println("�ѻ��Excel��������:" + ntest + "����������");
		} catch (FileNotFoundException e) {
			System.out.println("δ�ҵ�ָ��·���Ĳ��������ļ�!");
			e.printStackTrace();
		}
		////////////////////////////////////////////////////////////////////////////////////////////////
		List<Integer> normalIndicats = new ArrayList<Integer>();
		try {
			// ��ȡExcel�����������
			InputStream istest = new FileInputStream("F://5folder////KNN//ceshi05.xls");
			normalIndicats = excelReader.readExcelIndicat(istest);
			int ntest = testDatas.size();
			System.out.println("�ѻ��Excel��������:" + ntest + "�����ݵ�����ָʾ��ʶ");
		} catch (FileNotFoundException e) {
			System.out.println("δ�ҵ�ָ��·���Ĳ��������ļ�!");
			e.printStackTrace();
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////
		// ������������Excel���ı�ͷ
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
		cell.setCellValue("k");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("ʵ���������");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("ʵ���������");
		cell.setCellStyle(style);
		cell = row.createCell(3);
		cell.setCellValue("ʵ��������");
		cell.setCellStyle(style);
		cell = row.createCell(4);
		cell.setCellValue("ʵ��������");
		cell.setCellStyle(style);
		cell = row.createCell(5);
		cell.setCellValue("������");
		cell.setCellStyle(style);
		cell = row.createCell(6);
		cell.setCellValue("������");
		cell.setCellStyle(style);
		cell = row.createCell(7);
		cell.setCellValue("׼ȷ��");
		cell.setCellStyle(style);
		cell = row.createCell(8);
		cell.setCellValue("�Զ���ָ��");
		cell.setCellStyle(style);
		cell = row.createCell(9);
		cell.setCellValue("ָ��f1");
		cell.setCellStyle(style);
		///////////////////////////////////////////////////////////////////////////////////////////////////
		// ���ò���k�ķ�ΧΪk=10:5:160,k��ȡֵ����30=(155-10)/5+1��
		int ksize = 30;
		double[] A = new double[ksize];
		// ����һ��float���͵�2ά���飬ʵ���������������
		double[] B = new double[ksize];
		// ����һ��float���͵�2ά���飬ʵ������������쳣
		double[] C = new double[ksize];
		// ����һ��float���͵�2ά���飬ʵ���쳣���������
		double[] D = new double[ksize];
		// ����һ��float���͵�2ά���飬ʵ���쳣������쳣
		double[] mingzhong = new double[ksize];
		// ����һ��float���͵�2ά����,������
		double[] fugai = new double[ksize];
		// ����һ��float���͵�2ά���飬������
		double[] accury = new double[ksize];
		// ����һ��float���͵�2ά���飬����׼ȷ��
		double[] zonghe = new double[ksize];
		// ����һ��float���͵�2ά���飬�Ҷ����һ��ָ��
		double[] f1 = new double[ksize];
		// ����һ��float���͵�2ά���飬����׼ȷ��
		//////////////////////////////////////////////////////////////////////////////////////////
		KNN knn = new KNN();
		int k = 10;// ����k��ֵ����Ϊ�趨�ģ���Ϊk��ֵС�ڵ���500
		while (k <= 155) {
			int p = k / 5 - 2;
			for (int i = 0; i < testDatas.size(); i++) {
				List<Double> test = testDatas.get(i);//��õ�i����������
				int detectIndicat=Math.round(Float.parseFloat((knn.knn(datas, test, k))));//�ò������ݵļ�������쳣��ʶ
				int normalIndicat=normalIndicats.get(i);//�ò������ݵ�ʵ�������쳣��ʶ
				//////////////////////////////////////////////////////////////////////////////////////////////
	    		///////////////////////////////////////////////////////////////////////////////////
	    		//��ģ�ͽ������۸�ģ����������
	    			if (normalIndicat==1&&detectIndicat==1)
	    				A[p]++;
	    			else if(normalIndicat==1&&detectIndicat==0)
	    				B[p]++;
	    			else if(normalIndicat==0&&detectIndicat==1)
	    				C[p]++;
	    			else
	    				D[p]++;
			}
			//////////////////////////////////////////////////////////////////////////////////////////////
				mingzhong[p] = D[p] / (1.0 * (B[p] + D[p]));
				fugai[p] = D[p] / (1.0 * (C[p] + D[p]));
				accury[p]= (A[p] + D[p]) / (1.0 * (A[p] + B[p] + C[p] + D[p]));
				zonghe[p] = 0.4 * mingzhong[p]+ 0.6 * fugai[p];
				f1[p]=2*mingzhong[p]*fugai[p]/(mingzhong[p]+fugai[p]);
				////////////////////////////////////////////////////////////////////////////////////
				// ���岽������д����ʵ������
				row = sheet.createRow((int) (p ) + 1);
				// ������Ԫ�񣬲�����ֵ
				row.createCell(0).setCellValue(k);
				row.createCell(1).setCellValue(A[p]);
				row.createCell(2).setCellValue(B[p]);
				row.createCell(3).setCellValue(C[p]);
				row.createCell(4).setCellValue(D[p]);
				row.createCell(5).setCellValue(mingzhong[p]);
				row.createCell(6).setCellValue(fugai[p]);
				row.createCell(7).setCellValue(accury[p]);
				row.createCell(8).setCellValue(zonghe[p]);
				row.createCell(9).setCellValue(f1[p]);
			k += 5;
		}

		// �����������ļ��浽ָ��λ��
		try {
			FileOutputStream fout = new FileOutputStream("F://5folder//KNN//KNNAssessResult05.xls");
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("��ͬ����k��Ӧ���۽���Ѿ��洢��Excel���F://5folder//KNN//KNNAssessResult05.xls��");
	}
}
