package myapriori;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dealString {
	public static void main(String[] args) {
		String tran="A1"+";"+"B1"+";";
		String t="A1;B1;";
		System.out.println(tran);
		System.out.println(t);
		System.out.println(tran==t);
		///////////////////////////////////////////////////////////////////////
		List<NData> datas = new ArrayList<NData>();// ��Excel����ж�ȡ������
		// ��Excel���ж�����Ϊ��������ݼ�����ExcelReader�ࣩ
		try {
			// ��ȡExcel�����������
			ExcelReader excelReader = new ExcelReader();
			InputStream is = new FileInputStream("F://associativeAnalysis//b5datas.xls");
			datas = excelReader.readExcelContent(is);
			int n = datas.size();
			System.out.println(n + "������");
			System.out.println("�ѻ��Excel��������:");
		} catch (FileNotFoundException e) {
			System.out.println("δ�ҵ�ָ��·�����ļ�!");
			e.printStackTrace();
		}
		//////////////////////////////////////////////////////////////////////////////
		List<String> S = new ArrayList<String>();
		////////////////////////////////////////////////////////////////////////////////////////////////
		// �ò��轫��Excel�ж�ȡ�����������е�����ת��Ϊ�ض����ַ�����ʽ���δ����transList������
		for (int i = 0; i < 10; i++) {
			String temp = datas.get(i).getA() + ";" + datas.get(i).getB() + ";" + datas.get(i).getC() + ";"
					+ datas.get(i).getD() + ";" + datas.get(i).getE() + ";" + datas.get(i).getF() + ";"
					+ datas.get(i).getG() + ";" + datas.get(i).getH() + ";" + datas.get(i).getI() + ";"
					+ datas.get(i).getJ() + ";" + datas.get(i).getK() + ";" + datas.get(i).getL() + ";"
					+ datas.get(i).getM() + ";" + datas.get(i).getN() + ";" + datas.get(i).getO() + ";"
					+ datas.get(i).getP() + ";" + datas.get(i).getQ() + ";";
			
			System.out.println(temp);
			S.add(temp);
		}
		System.out.println("////////////////////////////////////////////////////////");
		for (int i = 0; i < 10; i++) {
			System.out.println(S.get(i));
		}
		//////////////////////////////////////////////////////////////////////////////////////////////
		String b="M1;A1;B1;P2;";
		//b.indexOf(ch)
		System.out.println(b.contains("A"));
		String[] items = b.split(";");
		for (String item : items) {
			System.out.println(item);
		}
		Arrays.sort(items);
		System.out.println("�����:");
		for (String item : items) {
			System.out.println(item);
		}
		String c="";
		for (String item : items) {
			c+=item+";";
		}
		System.out.println(c);
		System.out.println(c.length());
		if(!(c=="A1;B1;M1;P2;")){
			System.out.println("good:true");
		}
		////////////////////////////////////////////////////////////////////////
		StringBuffer r=new StringBuffer();
		for (String item : items) {
			r.append(item+";");
		}
		System.out.println(r);
		if(r.toString()=="A1;B1;M1;P2;"){
			System.out.println("r:true");
		}
		///////////////////////////////////////////////////////////////////
		String rule="M1;A1;B1;=>P2;";
		String str="";
		String[] myitems = rule.split("=>");
		for (String item : myitems) {
			str+=item;
			System.out.println(str);
		}
		System.out.println(str);
	}
}
