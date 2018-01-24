/**
 * �̌Q�N���X
 * 
 * @author b113048
 */
public class Individuals {
	/* �̐� */
	private int popCount;
	/* �����m�� */
	private double crossRate;
	/* �̌Q */
	private Chromosome[] indivs;
	/* �G���[�g�� */
	private Chromosome elite;
	/* �G���[�g�̂��X�V���ꂽ�� */
	private boolean updated;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param l ��`�q��
	 * @param p �̐�
	 * @param c ������
	 * @param m �ψٗ�
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

	/* �e�̑I�����X�g */
	/**
	 * @return ��z�y�A�z��
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
	 * @return �K���x�̕��ϒl
	 */
	public double getAverageFitness() {
		double total = 0;
		for (int i = 0; i < popCount; i++) {
			total += getChromosome(i).getFitness();
		}
		return total / (double) popCount;
	}

	/**
	 * �}�����������s�� (https://www.jstage.jst.go.jp/article/sicetr1965/31/5/31_5_598/_pdf)
	 * 
	 * @param id1 �e��ID1
	 * @param id2 �e��ID2
	 */
	public void crossover(int id1, int id2) {
		int length; // ��`�q��
		if (indivs[id1].getLength() != indivs[id2].getLength()) {
			System.err.println("�����G���[");
			return;
		}

		if (Math.random() >= crossRate) {
			return;
		}

		// �p�X�\��
		int[] gene1;
		int[] gene2;
		length = indivs[id1].getLength();

		/* �}�N����`�q�̐��� */
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
	 * ������ւ̐����c��
	 * 
	 * @param obj �p�����̌Q
	 */
	public void survive(Individuals obj) {
		int objPopCount = obj.getPopCount(); // �����c���␔
		int i;

		int[] ranking = new int[objPopCount]; // ID��
		double[] eval = new double[objPopCount]; // �]����

		for (i = 0; i < objPopCount; i++) {
			ranking[i] = i;
			eval[i] = obj.getChromosome(i).getFitness();
		}

		/* �K���x�̍�������ID�����בւ� */
		Sort.sortValue(ranking, eval);

		/* ��ʂ𐶂��c�点�� */
		for (i = 0; i < popCount; i++) {
			setChromosome(i, obj.getChromosome(ranking[i]));
		}

		/* �G���[�g�헪 */
		if (obj.getElite().getFitness() < eval[ranking[0]]) {
			/* �G���[�g�X�V */
			updated = true;
			setElite(obj.getChromosome(ranking[0]));
		} else {
			/* �G���[�g�Đ� */
			setElite(obj.getElite());
			setChromosome(popCount - 1, obj.getElite());
		}
	}

	/**
	 * @param m �n�}�f�[�^
	 */
	public void evaluation(Map m) {
		for (int i = 0; i < popCount; i++) {
			indivs[i].enforceGene(m);
			indivs[i].calcFitness(m);
		}
	}

	/**
	 * �܂Ƃ߂ēˑR�ψُ���
	 */
	public void mutate(int itr) {
		for (int i = 0; i < popCount; i++) {
			indivs[i].mutate(itr);
		}
	}

	/**
	 * @param obj �p�����̌Q
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
