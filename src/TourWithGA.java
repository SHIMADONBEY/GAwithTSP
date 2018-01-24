public class TourWithGA extends Tour {
	private int numOfSamples;
	private int numOfParents;
	private double p_c;
	private double p_m;
	private int t_Max;

	public TourWithGA(int n) {
		super(n);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public TourWithGA(Map m) {
		super(m);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * @param n1 個体数
	 * @param n2 生成する子個体数
	 * @param itr 最大世代数
	 * @param r1 交差確率
	 * @param r2 突然変異確率
	 * @return 最適経路
	 */
	public int methodOfGA(int n1, int n2, int itr, double r1, double r2) {
		final int MUTATION_ITERATION = 5;
		int i;
		int[] pairList = new int[numOfParents];

		if (n1 < 1 || n1 % 2 == 1) {
			System.err.println("個体数は2以上の偶数です！");
			return 1;
		}

		numOfSamples = n1;

		if (n2 % 2 == 1) {
			System.err.println("個体数は偶数です！");
			return 1;
		} else if (numOfSamples > n2) {
			numOfParents = numOfSamples;
		} else {
			numOfParents = n2;
		}

		if (itr < 0) {
			System.err.println("最大世代数は0以上です！");
			return 1;
		}
		t_Max = itr;

		if (r1 < 0) {
			p_c = 0;
		} else if (r1 > 1) {
			p_c = 1;
		} else {
			p_c = r1;
		}

		if (r2 < 0) {
			p_m = 0;
		} else if (r2 > 1) {
			p_m = 1;
		} else {
			p_m = r2;
		}

		int length = super.getMap().getCityCount();

		Individuals children;
		Individuals parents;

		children = new Individuals(length, numOfSamples, p_c, p_m);

		Tour buf = new Tour(length);
		buf.setMap(super.getMap());
		buf.makeTour();
		Chromosome chr = new Chromosome(length, p_m);
		chr.setGene(buf.getRoute());
		chr.enforceGene(super.getMap());
		chr.calcFitness(super.getMap());
		children.setElite(chr);

		for (i = 0; i < numOfSamples; i++) {
			chr = new Chromosome(length, p_m);
			buf = new Tour(length);
			buf.setMap(super.getMap());
			buf.makeTour();
			chr.setGene(buf.getRoute());
			children.setChromosome(i, chr);
		}

		int t = 0;
		super.setRoute(children.getElite().getGene());
		System.out.printf("%5d\t %.3f\n", t, super.getDistance());
		while (t < t_Max) {
			parents = new Individuals(length, numOfParents, p_c, p_m);
			parents.setElite(children.getElite());

			parents.putChromosomes(children);
			pairList = parents.getParentList();

			for (i = 0; i < numOfParents; i += 2) {
				parents.crossover(pairList[i], pairList[i + 1]);
			}

			parents.mutate(MUTATION_ITERATION);
			parents.evaluation(super.getMap());

			t++;

			children = new Individuals(length, numOfSamples, p_c, p_m);

			children.survive(parents);

			if (children.hasUpdated()) {
				super.setRoute(children.getElite().getGene());
			}

			System.out.printf("%5d\t %.3f\n", t, super.getDistance());
		}

		return 0;
	}

}
