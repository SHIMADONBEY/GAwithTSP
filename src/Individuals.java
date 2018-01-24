/**
 * 個体群クラス
 * 
 * @author b113048
 */
public class Individuals {
	/* 個体数 */
	private int popCount;
	/* 交叉確率 */
	private double crossRate;
	/* 個体群 */
	private Chromosome[] indivs;
	/* エリート個体 */
	private Chromosome elite;
	/* エリート個体が更新されたか */
	private boolean updated;

	/**
	 * コンストラクタ
	 * 
	 * @param l 遺伝子長
	 * @param p 個体数
	 * @param c 交叉率
	 * @param m 変異率
	 */
	public Individuals(int l, int p, double c, double m) {
		popCount = p;
		crossRate = c;
		indivs = new Chromosome[popCount];
		elite = new Chromosome(l, m);
		updated = false;

		for (int i = 0; i < popCount; i++) {
			indivs[i] = new Chromosome(l, m);
		}
	}

	public int getPopCount() {
		return popCount;
	}

	public void setPopCount(int popCount) {
		this.popCount = popCount;
	}

	public Chromosome getChromosome(int i) {
		return indivs[i];
	}

	public void setChromosome(int i, Chromosome chr) {
		indivs[i].setGene(chr.getGene());
		indivs[i].setFitness(chr.getFitness());
	}

	public Chromosome getElite() {
		return elite;
	}

	public void setElite(Chromosome elite) {
		this.elite.setGene(elite.getGene());
		this.elite.setFitness(elite.getFitness());
	}

	public boolean hasUpdated() {
		return updated;
	}

	/* 親の選択リスト */
	/**
	 * @return 交配ペア配列
	 */
	public int[] getParentList() {
		int[] x = new int[popCount];

		int i;
		int k;
		int swp;

		for (i = 0; i < popCount; i++) {
			x[i] = i;
		}

		for (i = 0; i < popCount; i++) {
			k = (int) (Math.random() * (popCount - i)) + i;
			swp = x[i];
			x[i] = x[k];
			x[k] = swp;
		}

		return x;
	}

	/**
	 * @return 適応度の平均値
	 */
	public double getAverageFitness() {
		double total = 0;
		for (int i = 0; i < popCount; i++) {
			total += getChromosome(i).getFitness();
		}
		return total / (double) popCount;
	}

	/**
	 * 枝交換交叉を行う (https://www.jstage.jst.go.jp/article/sicetr1965/31/5/31_5_598/_pdf)
	 * 
	 * @param id1 親個体ID1
	 * @param id2 親個体ID2
	 */
	public void crossover(int id1, int id2) {
		int length; // 遺伝子長
		if (indivs[id1].getLength() != indivs[id2].getLength()) {
			System.err.println("交叉エラー");
			return;
		}

		if (Math.random() >= crossRate) {
			return;
		}

		// パス表現
		int[] gene1;
		int[] gene2;
		length = indivs[id1].getLength();

		/* マクロ遺伝子の生成 */
		gene1 = indivs[id1].getGene();
		gene2 = indivs[id2].getGene();

		int[][] parent1 = new int[length][2];
		int[][] parent2 = new int[length][2];

		int i_1, i_2;
		int j_1, j_2;
		int p_1, p_2;
		int q_1, q_2;

		int j, k;

		int swap;

		for (j = 0; j < length; j++) {
			parent1[j][0] = indivs[id1].getGene(j);
			parent2[j][0] = indivs[id2].getGene(j);

			parent1[j][1] = indivs[id1].getGene((j + 1) % length);
			parent2[j][1] = indivs[id2].getGene((j + 1) % length);
		}

		i_1 = (int) Math.random() * length;
		i_2 = 0;
		while ((parent2[i_2][0] != parent1[i_1][0])) {
			i_2++;
		}

		while (true) {
			j_2 = 0;
			while ((parent2[j_2][0] != parent1[i_1][1])) {
				j_2++;
			}

			j_1 = 0;
			while ((parent1[j_1][0] != parent2[i_2][1])) {
				j_1++;
			}

			for (k = 0; k < 2; k++) {
				swap = parent1[i_1][k];
				parent1[i_1][k] = parent2[i_2][k];
				parent2[i_2][k] = swap;
			}

			if (parent1[i_1][1] == parent2[i_2][1]) {
				break;
			}

			p_1 = i_1 + 1;
			q_1 = j_1 - 1;

			if (j_1 < i_1) {
				q_1 += length;
			}
			while (p_1 <= q_1) {
				swap = parent1[q_1 % length][1];
				parent1[q_1 % length][1] = parent1[p_1 % length][0];
				parent1[p_1 % length][0] = swap;

				if (p_1 == q_1) {
					break;
				}
				swap = parent1[p_1 % length][1];
				parent1[p_1 % length][1] = parent1[q_1 % length][0];
				parent1[q_1 % length][0] = swap;

				p_1++;
				q_1--;
			}

			p_2 = i_2 + 1;
			q_2 = j_2 - 1;

			if (j_2 < i_2) {
				q_2 += length;
			}

			while (p_2 <= q_2) {
				swap = parent2[q_2 % length][1];
				parent2[q_2 % length][1] = parent2[p_2 % length][0];
				parent2[p_2 % length][0] = swap;

				if (p_2 == q_2) {
					break;
				}
				swap = parent2[p_2 % length][1];
				parent2[p_2 % length][1] = parent2[q_2 % length][0];
				parent2[q_2 % length][0] = swap;

				p_2++;
				q_2--;
			}

			i_1 = j_1;
			i_2 = j_2;
		}

		for (k = 0; k < length; k++) {
			gene1[k] = parent1[k][0];
			gene2[k] = parent2[k][0];
		}

		indivs[id1].setGene(gene1);
		indivs[id2].setGene(gene2);

		gene1 = null;
		gene2 = null;

		parent1 = null;
		parent2 = null;
	}

	/**
	 * 次世代への生き残り
	 * 
	 * @param obj 継承候補個体群
	 */
	public void survive(Individuals obj) {
		int objPopCount = obj.getPopCount(); // 生き残り候補数
		int i;

		int[] ranking = new int[objPopCount]; // ID列
		double[] eval = new double[objPopCount]; // 評価列

		for (i = 0; i < objPopCount; i++) {
			ranking[i] = i;
			eval[i] = obj.getChromosome(i).getFitness();
		}

		/* 適合度の高い順にID列を並べ替え */
		Sort.sortValue(ranking, eval);

		/* 上位を生き残らせる */
		for (i = 0; i < popCount; i++) {
			setChromosome(i, obj.getChromosome(ranking[i]));
		}

		/* エリート戦略 */
		if (obj.getElite().getFitness() < eval[ranking[0]]) {
			/* エリート更新 */
			updated = true;
			setElite(obj.getChromosome(ranking[0]));
		} else {
			/* エリート再生 */
			setElite(obj.getElite());
			setChromosome(popCount - 1, obj.getElite());
		}
	}

	/**
	 * @param m 地図データ
	 */
	public void evaluation(Map m) {
		for (int i = 0; i < popCount; i++) {
			indivs[i].enforceGene(m);
			indivs[i].calcFitness(m);
		}
	}

	/**
	 * まとめて突然変異処理
	 */
	public void mutate(int itr) {
		for (int i = 0; i < popCount; i++) {
			indivs[i].mutate(itr);
		}
	}

	/**
	 * @param obj 継承候補個体群
	 */
	public void putChromosomes(Individuals obj) {
		int n_obj = obj.getPopCount();
		double[] sigma = new double[n_obj];
		double multiplier = (popCount) / (obj.getPopCount() * obj.getAverageFitness());

		int i;
		int j;
		int k;

		j = 0;
		for (i = 0; i < n_obj; i++) {
			sigma[i] = obj.getChromosome(i).getFitness() * multiplier;
			for (k = 0; k < (int) sigma[i]; k++) {
				setChromosome(j, obj.getChromosome(i));
				j++;
			}
			sigma[i] -= (int) sigma[i];
		}

		for (i = 1; i < n_obj; i++) {
			sigma[i] += sigma[i - 1];
		}

		for (i = j; i < popCount; i++) {
			double u = Math.random() * sigma[n_obj - 1];

			k = 0;
			while (u >= sigma[k]) {
				k++;
			}

			setChromosome(i, obj.getChromosome(k));
		}
	}

}
