package myapriori;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Apriori {

	// private final static int SUPPORT = 806; // ֧�ֶ���ֵ(��Ӧ֧�ֶ�20%��
	private final static int SUPPORT = 604; // ֧�ֶ���ֵ(��Ӧ֧�ֶ�15%��
	//private final static int SUPPORT = 403; // ֧�ֶ���ֵ(��Ӧ֧�ֶ�10%��
	private final static double CONFIDENCE = 0.65; // ���Ŷ���ֵ
	private final static double totalnumber = 4028; // ��������

	private final static String ITEM_SPLIT = ";"; // ��֮��ķָ���
	private final static String CON = "=>"; // ��֮��ķָ���

	private final static List<String> transList = new ArrayList<String>(); // ���н���(��Ӧ�������ݿ�D��

	static {// ��ʼ�����׼�¼
		///////////////////////////////////////////////////////////////////////
		List<NData> datas = new ArrayList<NData>();// ��Excel����ж�ȡ������
		// ��Excel���ж�����Ϊ��������ݼ�����ExcelReader�ࣩ
		try {
			// ��ȡExcel�����������
			ExcelReader excelReader = new ExcelReader();
			InputStream is = new FileInputStream("F://associativeAnalysis//b5datanew.xls");
			datas = excelReader.readExcelContent(is);
			int n = datas.size();
			System.out.println(n + "������");
			System.out.println("�ѻ��Excel��������:");
		} catch (FileNotFoundException e) {
			System.out.println("δ�ҵ�ָ��·�����ļ�!");
			e.printStackTrace();
		}
		////////////////////////////////////////////////////////////////////////////////////////////////
		// �ò��轫��Excel�ж�ȡ�����������е�����ת��Ϊ�ض����ַ�����ʽ���δ����transList������
		for (int i = 0; i < datas.size(); i++) {
			String tran = datas.get(i).getA() + ";" + datas.get(i).getB() + ";" + datas.get(i).getC() + ";"
					+ datas.get(i).getD() + ";" + datas.get(i).getE() + ";" + datas.get(i).getF() + ";"
					+ datas.get(i).getG() + ";" + datas.get(i).getH() + ";" + datas.get(i).getI() + ";"
					+ datas.get(i).getJ() + ";" + datas.get(i).getK() + ";" + datas.get(i).getL() + ";"
					+ datas.get(i).getM() + ";" + datas.get(i).getN() + ";" + datas.get(i).getO() + ";"
					+ datas.get(i).getP() + ";" + datas.get(i).getQ() + ";";
			transList.add(tran);
		}

	}

	// �õ����е�Ƶ����
	public Map<String, Integer> getFC() {
		Map<String, Integer> frequentCollectionMap = new HashMap<String, Integer>();// ���е�Ƶ����

		frequentCollectionMap.putAll(getItem1FC());// �� 1-Ƶ��������Ƶ����Map

		Map<String, Integer> itemkFcMap = new HashMap<String, Integer>();// ����
																			// k-Ƶ����
		itemkFcMap.putAll(getItem1FC());// �� 1-Ƶ�������� k-Ƶ����Map
		while (itemkFcMap != null && itemkFcMap.size() != 0) {
			Map<String, Integer> candidateCollection = getCandidateCollection(itemkFcMap);// ��k-1Ƶ��������k-��ѡ��
			Set<String> ccKeySet = candidateCollection.keySet();// ���k-��ѡ�������е�key������

			// �Ժ�ѡ��������ۼӼ���
			for (String trans : transList) {
				for (String candidate : ccKeySet) {
					boolean flag = true;// �����жϽ������Ƿ���ָú�ѡ�������֣�������1
					String[] candidateItems = candidate.split(ITEM_SPLIT);// ȥ���ָ���ITEM_SPLIT�����õ��ַ�������
					for (String candidateItem : candidateItems) {
						if (trans.indexOf(candidateItem + ITEM_SPLIT) == -1) {// ���ж�candidateItem�Ƿ���trans������
							flag = false;
							break;
						}
					}
					if (flag) {
						Integer count = candidateCollection.get(candidate);
						candidateCollection.put(candidate, count + 1);// ��ʾ��count��������candidate
					}
				}
			}

			// �Ӻ�ѡ�����ҵ�����֧�ֶȵ�Ƶ������
			itemkFcMap.clear();
			for (String candidate : ccKeySet) {
				Integer count = candidateCollection.get(candidate);
				if (count >= SUPPORT) {
					itemkFcMap.put(candidate, count);
				}
			}

			// ��������ɵ�kƵ����������Ƶ����
			frequentCollectionMap.putAll(itemkFcMap);

		}

		return frequentCollectionMap;
	}

	// ��k-1Ƶ�����õ�k��ѡ��
	private Map<String, Integer> getCandidateCollection(Map<String, Integer> itemkFcMap) {
		Map<String, Integer> candidateCollection = new HashMap<String, Integer>();// ����k��ѡ��
		Set<String> itemkSet1 = itemkFcMap.keySet();// �õ�k-1Ƶ����������key
		Set<String> itemkSet2 = itemkFcMap.keySet();// �õ�k-1Ƶ����������key

		for (String itemk1 : itemkSet1) {
			for (String itemk2 : itemkSet2) {
				// ��������
				String[] tmp1 = itemk1.split(ITEM_SPLIT);// ȥ���ָ���ITEM_SPLIT�����õ��ַ�������
				String[] tmp2 = itemk2.split(ITEM_SPLIT);// ȥ���ָ���ITEM_SPLIT�����õ��ַ�������

				String c = "";// �����ѡ�� kƵ����
				if (tmp1.length == 1) {
					if (tmp1[0].compareTo(tmp2[0]) < 0) {
						c = tmp1[0] + ITEM_SPLIT + tmp2[0] + ITEM_SPLIT;
					}
				} else {
					boolean flag = true;
					for (int i = 0; i < tmp1.length - 1; i++) {
						if (!tmp1[i].equals(tmp2[i])) {
							flag = false;
							break;
						}
					}
					if (flag && (tmp1[tmp1.length - 1].compareTo(tmp2[tmp2.length - 1]) < 0)) {// ����ʱ���ظ���
						c = itemk1 + tmp2[tmp2.length - 1] + ITEM_SPLIT;// ע�⣺�õ���c��key����"2;3;5;"
					}
				}

				// ���м�֦:�ж�c�Ƿ�Ϊ��Ƶ����ѡ
				boolean hasInfrequentSubSet = false;// �����Ƶ����ѡָʾ
				if (!c.equals("")) {
					String[] tmpC = c.split(ITEM_SPLIT);// �ַ���cȥ���ָ���ITEM_SPLIT�����õ��ַ�������tmpC
					for (int i = 0; i < tmpC.length; i++) {
						String subC = "";
						for (int j = 0; j < tmpC.length; j++) {
							if (i != j) {
								subC = subC + tmpC[j] + ITEM_SPLIT;
							}
						}
						if (itemkFcMap.get(subC) == null) {// �ж�subC�Ƿ���k-1Ƶ����itemkFcMap�У����û�ڣ���cΪ��Ƶ����
							hasInfrequentSubSet = true;
							break;
						}
					}
				} else {
					hasInfrequentSubSet = true;
				}

				if (!hasInfrequentSubSet) {
					candidateCollection.put(c, 0);// ���cΪƵ�������������kƵ������
				}
			}
		}

		return candidateCollection;
	}

	// �õ�Ƶ��1���map�������Ӧ�� 1-Ƶ������������
	private Map<String, Integer> getItem1FC() {
		Map<String, Integer> sItem1FcMap = new HashMap<String, Integer>();
		Map<String, Integer> rItem1FcMap = new HashMap<String, Integer>();// Ƶ��1�

		for (String trans : transList) {
			String[] items = trans.split(ITEM_SPLIT);// �ַ���transȥ���ָ���ITEM_SPLIT�����õ��ַ�������
														// ��2;4;�����2��4����String��������
			for (String item : items) {
				Integer count = sItem1FcMap.get(item + ITEM_SPLIT);// ���keyΪitem
																	// +
																	// ITEM_SPLIT��Ӧ��ֵ
				if (count == null) {
					sItem1FcMap.put(item + ITEM_SPLIT, 1);// ��keyΪitem +
															// ITEM_SPLIT��Ӧ��ֵ��Ϊ1
				} else {
					sItem1FcMap.put(item + ITEM_SPLIT, count + 1);// ��keyΪitem +
																	// ITEM_SPLIT��ֵΪcount+1
				}
			}
		}

		Set<String> keySet = sItem1FcMap.keySet();
		for (String key : keySet) {
			Integer count = sItem1FcMap.get(key);
			if (count >= SUPPORT) {
				rItem1FcMap.put(key, count);
			}
		}
		return rItem1FcMap;
	}

	// ��Ƶ���������������
	// ����ÿ��Ƶ���l�����������зǿ����Ӽ���
	public Map<String, Double> getRelationRules(Map<String, Integer> frequentCollectionMap) {
		Map<String, Double> relationRules = new HashMap<String, Double>();
		Set<String> keySet = frequentCollectionMap.keySet();
		for (String key : keySet) {
			double countAll = frequentCollectionMap.get(key);// ��ü�ֵΪkey��Ƶ����ĸ���
			String[] keyItems = key.split(ITEM_SPLIT);// �ַ���keyȥ���ָ���ITEM_SPLIT�����õ��ַ�������
			if (keyItems.length > 1) {
				List<String> source = new ArrayList<String>();
				Collections.addAll(source, keyItems);// ��keyItems����ַ������β��뵽source�ַ���������
				List<List<String>> result = new ArrayList<List<String>>();

				buildSubSet(source, result);// ���source�����зǿ��Ӽ�����result��
				// ����{1��2��5��}�ķǿ����Ӽ���{1��2��}��{5��}��{1��5��}��{2��}��{2��5��}��{1��}��6��
				// List<String> source
				// ���㣺source[0]="1;",source[1]="2;",source[2]="5;"

				for (List<String> itemList : result) {
					if (itemList.size() < source.size()) {// ֻ�������Ӽ�
						List<String> otherList = new ArrayList<String>();
						for (String sourceItem : source) {
							if (!itemList.contains(sourceItem)) {
								otherList.add(sourceItem);
							}
						} // ������˵�����itemList��{1��2��}����ôotherList����{5��}
						String reasonStr = "";// ǰ��
						String resultStr = "";// ���
						for (String item : itemList) {
							reasonStr = reasonStr + item + ITEM_SPLIT;
						}
						for (String item : otherList) {
							resultStr = resultStr + item + ITEM_SPLIT;
						}

						double countReason = frequentCollectionMap.get(reasonStr);
						double itemConfidence = countAll / countReason;// �������Ŷ�

						if (itemConfidence >= CONFIDENCE ) {
							String rule = reasonStr + CON + resultStr;
							relationRules.put(rule, itemConfidence);
						}
					}
				}
			}
		}

		return relationRules;
	}

	// ���source�����зǿ��Ӽ�����result��
	private void buildSubSet(List<String> sourceSet, List<List<String>> result) {
		// ����һ��Ԫ��ʱ���ݹ���ֹ����ʱ�ǿ��Ӽ���Ϊ����������ֱ����ӵ�result��
		if (sourceSet.size() == 1) {
			List<String> set = new ArrayList<String>();
			set.add(sourceSet.get(0));// ���������sourceSetֻ��һ��Ԫ��
			result.add(set);
		} else if (sourceSet.size() > 1) {
			// ����n��Ԫ��ʱ���ݹ����ǰn-1���Ӽ�������result��
			buildSubSet(sourceSet.subList(0, sourceSet.size() - 1), result);// ��sourceSet�г������һ��λ�õ�����ת�����ͺ����result
			int size = result.size();// �����ʱresult�ĳ��ȣ����ں����׷�ӵ�n��Ԫ��ʱ����
			// �ѵ�n��Ԫ�ؼ��뵽������
			List<String> single = new ArrayList<String>();
			single.add(sourceSet.get(sourceSet.size() - 1));// ��sourceSet�����һ��λ�õ����ݷŵ�single��
			result.add(single);// ��sourceSet�г������һ��λ�õ�����ת��ΪList<String>���ͼ���result������
			// �ڱ���ǰ���n-1�Ӽ�������£��ѵ�n��Ԫ�طֱ�ӵ�ǰn���Ӽ��У������µļ����뵽result��;
			// Ϊ����ԭ��n-1���Ӽ���������Ҫ�ȶ�����и���
			List<String> clone;
			for (int i = 0; i < size; i++) {
				clone = new ArrayList<String>();
				for (String str : result.get(i)) {
					clone.add(str);
				}
				clone.add(sourceSet.get(sourceSet.size() - 1));

				result.add(clone);
			}
		}
	}

	// ��rule�ַ��� H3;E2;M3;=>B1; ת��Ϊ B1;E2;M3;H3; ����ʽ
	// ����Ҫ��ȡ���ַ�����Ȼ���䰴���ֵ�˳������������
	public static String changeStr(String rule) {
		String[] strs = rule.split(CON);
		String rulestr = "";
		for (String str : strs) {
			rulestr += str;
		}

		String[] items = rulestr.split(ITEM_SPLIT);
		Arrays.sort(items);
		String c = "";
		for (String item : items) {
			c += item + ";";
		}
		return c;
	}

	public static void main(String[] args) {
		System.out.println("///////////////////////////////////////////////////////");
		Apriori apriori = new Apriori();
		Map<String, Integer> frequentCollectionMap = apriori.getFC();
		System.out.println("----------------Ƶ����" + "----------------");
		Set<String> fcKeySet = frequentCollectionMap.keySet();
		for (String fcKey : fcKeySet) {
			System.out.println(fcKey + "  :  " + frequentCollectionMap.get(fcKey));
		}
		Map<String, Double> relationRulesMap = apriori.getRelationRules(frequentCollectionMap);
		System.out.println("----------------��������" + relationRulesMap.size() + "��" + "----------------");
		///////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////
		// ���õ��Ĺ����������Excel�����
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
		cell.setCellValue("��������");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("֧�ֶ�����");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("֧�ֶ�");
		cell.setCellStyle(style);
		cell = row.createCell(3);
		cell.setCellValue("���Ŷ�");
		cell.setCellStyle(style);
		cell = row.createCell(4);
		cell.setCellValue("������");
		cell.setCellStyle(style);
		cell = row.createCell(5);
		cell.setCellValue("������");
		cell.setCellStyle(style);
		////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		// ���岽��д����ʵ������
		Set<String> rrKeySet = relationRulesMap.keySet();
		int i = 0;
		for (String rrKey : rrKeySet) {
			System.out.println(rrKey + "  :  " + relationRulesMap.get(rrKey));// ��ӡ��������������Ŷ�
			row = sheet.createRow((int) i + 1);
			// ������Ԫ�񣬲�����ֵ
			row.createCell(0).setCellValue(rrKey);
			// �� H3;E2;M3;=>B1; ���͵��ַ���ת��Ϊ B1;E2;M3;H3;����
			// ����Ҫ��ȡ���ַ�����Ȼ���䰴���ֵ�˳������������
			row.createCell(1).setCellValue(frequentCollectionMap.get(changeStr(rrKey)));// ֧�ֶ�������ʾ
			row.createCell(2).setCellValue(1.0 * frequentCollectionMap.get(changeStr(rrKey)) / totalnumber);// ֧�ֶ�
			row.createCell(3).setCellValue(relationRulesMap.get(rrKey));// ���Ŷ�
			// ����������Lift
			String[] strs = rrKey.split(CON);
			String conseq = strs[1];
			row.createCell(4)
					.setCellValue(relationRulesMap.get(rrKey) * totalnumber / frequentCollectionMap.get(conseq));// ֧�ֶ�
			row.createCell(5).setCellValue(conseq);// ������
			i++;
		}
		////////////////////////////////////////////////////////////////////////////////////////////////
		// �����������ļ��浽ָ��λ��
		try {
			FileOutputStream fout = new FileOutputStream("F:/associativeAnalysis/ruleResults.xls");
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("������Ѿ��洢��Excel���F:/associativeAnalysis/ruleResults.xls��");
	}
}
