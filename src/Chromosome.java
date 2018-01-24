/**
 * ���F�̃N���X
 * 
 * @author b113048
 */
public class Chromosome {
	private double mutationRate; // �ˑR�ψٗ�
	private double fitness; // �K���x
	private int[] gene; // ��`�q�z��
	private int length; // ��`�q��

	/**
	 * �R���X�g���N�^�D
	 * 
	 * @param l ��`�q��
	 * @param p_m �ˑR�ψٗ�
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
	 * @param gene ��`�q�z��
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
	 * �K���x�v�Z (power law scaling)�D
	 * 
	 * @param m �n�}�f�[�^
	 */
	public void calcFitness(Map m) {
		Tour t = new Tour(length);
		t.setMap(m);
		t.setRoute(gene);
		this.fitness = Math.pow(length / t.getDistance(), 3);
		t = null;
	}

	/**
	 * �ˑR�ψ� (�t��)
	 * 
	 * @param itr �J��Ԃ���
	 */
	public void mutate(int itr) {
		int posA;
		int posB; // �ʒu
		int swp; // �X���b�v�ϐ�

		// �ˑR�ψق��s��������
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
	 * 2opt�@�ɂ���`�q�̋���
	 * 
	 * @param m �n�}�f�[�^
	 */
	public void enforceGene(Map m) {
		// �s�s�ԍ�
		int a;
		int b;
		int c;
		int d;

		// �s�sA�CC�̈�`�q��
		int idxA = -1;
		int idxC = -1;
		double lengthABCD;
		double lengthACBD; // ����AB+CD�ƒ���AC+DB
		double deltaLength; // ���P��
		double maxImprove; // �ő���P��
		int swap; // �X���b�v�ϐ�
		int i;
		int j; // �C���N�������g

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
						// �s�s�ԍ����X�V����
						idxA = i;
						idxC = j;
						maxImprove = -deltaLength;
					}
				}
			}

			// ���P�ł�����̂�����Όo�H�̓���ւ����s��
			if (maxImprove > 0) {
				idxA++; // ����ւ���̂�A�̎��̓s�s����

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
