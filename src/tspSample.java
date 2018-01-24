
public class tspSample {
	private static TourWithGA solver;

	/**
	 * @param args
	 * @throws Exception Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		/* 引数のチェック */
		if (args.length < 1) {
			System.err.println("Please input the name of data file!\n");
			System.exit(1);
		}

		Map target;
		// テキストデータの読み込み
		int status = tspIO.readData(args[0]);

		if (status <= 0) {
			return;
		}

		target = new Map(tspIO.getCitynum());

		for (int i = 0; i < tspIO.getCitynum(); i++) {
			/* 最適化手法用にマップを形成 */
			target.setCity(i, tspIO.getCityCoordinate(i).x, tspIO.getCityCoordinate(i).y);
		}

		/* 入力データの表示 */
		System.out.println("city_num= " + target.getCityCount());
		for (Point dt : target.getCities()) {
			System.out.printf("%g\t %g\n", dt.x, dt.y);
		}

		solver = new TourWithGA(target);

		long time1, time2;

		System.out.println("start...");
		time1 = System.currentTimeMillis(); // 現在時刻1

		/* 遺伝的アルゴリズムで経路を生成(個体数, 生成する子の数, 最大世代数, 交差確率, 突然変異確率) */
		solver.methodOfGA(100, 150, 300, 0.8, 0.1);

		/* 巡回路長の計算 */
		double length = solver.getDistance();
		System.out.println(length);

		System.out.println("stop.");
		time2 = System.currentTimeMillis(); // 現在時刻2
		System.out.println("処理時間：" + (time2 - time1) + "/ms");

		status = tspIO.writeData(length, (time2 - time1), solver, "result.csv");
	}

}
