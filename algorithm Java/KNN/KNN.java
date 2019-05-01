package KNN;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

//KNN�㷨������ 
public class KNN {
	/** * �������ȼ����еıȽϺ���������Խ�����ȼ�Խ�� */
	private Comparator<KNNNode> comparator = new Comparator<KNNNode>() {
		public int compare(KNNNode o1, KNNNode o2) {
			if (o1.getDistance() >= o2.getDistance()) {
				return -1;
			} else {
				return 1;
			}
		}
	};

	/** * �������Ԫ����ѵ��Ԫ��֮ǰ�ľ��� * @param d1 ����Ԫ��* @param d2 ѵ��Ԫ�� * @return ����ֵ */

	public static double calDistance(List<Double> d1, List<Double> d2) {
		double distance = 0.00;
		for (int i = 0; i < d1.size(); i++) {
			distance += (d1.get(i) - d2.get(i)) * (d1.get(i) - d2.get(i));
		}
		return distance;
	}

	/**
	 * * ִ��KNN�㷨����ȡ����Ԫ������ * @param datas ѵ�����ݼ� * @param testData ����Ԫ�� * @param
	 * k �趨��Kֵ * @return ����Ԫ������
	 */

	public String knn(List<List<Double>> datas, List<Double> testData, int k) {
		PriorityQueue<KNNNode> pq = new PriorityQueue<KNNNode>(k, comparator);
		for (int i = 0; i < k; i++) {
			List<Double> currData = datas.get(i);//��ȡdatas�ĵ�index�����ݷ���currData��
			String c = currData.get(currData.size() - 1).toString();//�������������쳣��ʶ��ȡ������String������
			KNNNode node = new KNNNode(i, calDistance(testData, currData), c);//��i�����ݸ���������testData��ȵľ���
			pq.add(node);
		}
		for (int i = k; i < datas.size(); i++) {
			List<Double> t = datas.get(i);//��ȡdatas�ĵ�i�����ݷ���currData��
			double distance = calDistance(testData, t);//�����i�����ݸ���������testData��ȵľ���
			//distance��k�����������ıȽ�
			KNNNode top = pq.peek();
			if (top.getDistance() > distance) {
				pq.remove();
				pq.add(new KNNNode(i, distance, t.get(t.size() - 1).toString()));
			}
		}
		return getMostClass(pq);
	}

	/** * ��ȡ���õ���k�������Ԫ��Ķ����� * @param pq �洢k���������Ԫ������ȼ�����* @return ����������� */

	private String getMostClass(PriorityQueue<KNNNode> pq) {
		Map<String, Integer> classCount = new HashMap<String, Integer>();
		int pqsize = pq.size();
		for (int i = 0; i < pqsize; i++) {
			KNNNode node = pq.remove();
			String c = node.getC();//��������쳣ָʾ��ʶ
			if (classCount.containsKey(c)) {
				classCount.put(c, classCount.get(c) + 1);//��Ӧ��ʾ�ļ�����1
			} else {
				classCount.put(c, 1);
			}
		}
		int maxIndex = -1;
		int maxCount = 0;
		//�Ƚ��������쳣�ļ������ĸ���ͷ�����Ӧָʾ
		Object[] classes = classCount.keySet().toArray();
		for (int i = 0; i < classes.length; i++) {
			if (classCount.get(classes[i]) > maxCount) {
				maxIndex = i;
				maxCount = classCount.get(classes[i]);
			}
		}
		return classes[maxIndex].toString();
	}
}
