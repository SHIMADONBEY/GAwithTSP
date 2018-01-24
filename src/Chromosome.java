/**
 * 染色体クラス
 * 
 * @author b113048
 */
public class Chromosome {
	private double mutationRate; // 突然変異率
	private double fitness; // 適合度
	private int[] gene; // 遺伝子配列
	private int length; // 遺伝子長

	/**
	 * コンストラクタ．
	 * 
	 * @param l 遺伝子長
	 * @param p_m 突然変異率
	 */
	public Chromosome(int l, double p_m) {
		length = l;
		gene = new int[length];
		mutationRate = p_m;
		fitness = 0.0;
	}

	public double getMutationRate() {
		return mutationRate;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int[] getGene() {
		return gene;
	}

	public int getGene(int locus) {
		return gene[locus];
	}

	/**
	 * @param gene 遺伝子配列
	 */
	public void setGene(int[] gene) {
		for (int i = 0; i < length; i++) {
			this.gene[i] = gene[i];
		}
	}

	public int getLength() {
		return length;
	}

	/**
	 * 適合度計算 (power law scaling)．
	 * 
	 * @param m 地図データ
	 */
	public void calcFitness(Map m) {
		Tour t = new Tour(length);
		t.setMap(m);
		t.setRoute(gene);
		this.fitness = Math.pow(length / t.getDistance(), 3);
		t = null;
	}

	/**
	 * 突然変異 (逆位)
	 * 
	 * @param itr 繰り返し回数
	 */
	public void mutate(int itr) {
		int posA;
		int posB; // 位置
		int swp; // スワップ変数

		// 突然変異を行うか判定
		if (Math.random() >= mutationRate) {
			return;
		}

		for (int i = 0; i < itr; i++) {
			posA = (int) (Math.random() * length);
			do {
				posB = (int) (Math.random() * length);
			} while (posA == posB);

			while (posA < posB) {
				swp = gene[posA];
				gene[posA] = gene[posB];
				gene[posB] = swp;

				posA++;
				posB--;
			}
		}
	}

	/**
	 * 2opt法による遺伝子の矯正
	 * 
	 * @param m 地図データ
	 */
	public void enforceGene(Map m) {
		// 都市番号
		int a;
		int b;
		int c;
		int d;

		// 都市A，Cの遺伝子座
		int idxA = -1;
		int idxC = -1;
		double lengthABCD;
		double lengthACBD; // 長さAB+CDと長さAC+DB
		double deltaLength; // 改善量
		double maxImprove; // 最大改善量
		int swap; // スワップ変数
		int i;
		int j; // インクリメント

		Tour r = new Tour(length);

		r.setRoute(gene);
		r.setMap(m);

		do {
			maxImprove = 0.0;

			for (i = 0; i < length - 1; i++) {
				a = gene[i];
				b = gene[(i + 1) % length];

				for (j = i + 2; j < length; j++) {
					c = gene[j];
					d = gene[(j + 1) % length];

					lengthABCD = r.getDistance(a, b) + r.getDistance(c, d);
					lengthACBD = r.getDistance(a, c) + r.getDistance(b, d);

					deltaLength = lengthACBD - lengthABCD;

					if (-deltaLength > maxImprove) {
						// 都市番号を更新する
						idxA = i;
						idxC = j;
						maxImprove = -deltaLength;
					}
				}
			}

			// 改善できるものがあれば経路の入れ替えを行う
			if (maxImprove > 0) {
				idxA++; // 入れ替えるのはAの次の都市から

				while (idxA < idxC) {
					swap = gene[idxA];
					gene[idxA] = gene[idxC];
					gene[idxC] = swap;

					idxA++;
					idxC--;
				}
			}
		} while (maxImprove > 0);
	}

}
