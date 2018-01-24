
public class tspSample {
	private static TourWithGA solver;

	/**
	 * @param args
	 * @throws Exception Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		/* �����̃`�F�b�N */
		if (args.length < 1) {
			System.err.println("Please input the name of data file!\n");
			System.exit(1);
		}

		Map target;
		// �e�L�X�g�f�[�^�̓ǂݍ���
		int status = tspIO.readData(args[0]);

		if (status <= 0) {
			return;
		}

		target = new Map(tspIO.getCitynum());

		for (int i = 0; i < tspIO.getCitynum(); i++) {
			/* �œK����@�p�Ƀ}�b�v���`�� */
			target.setCity(i, tspIO.getCityCoordinate(i).x, tspIO.getCityCoordinate(i).y);
		}

		/* ���̓f�[�^�̕\�� */
		System.out.println("city_num= " + target.getCityCount());
		for (Point dt : target.getCities()) {
			System.out.printf("%g\t %g\n", dt.x, dt.y);
		}

		solver = new TourWithGA(target);

		long time1, time2;

		System.out.println("start...");
		time1 = System.currentTimeMillis(); // ���ݎ���1

		/* ��`�I�A���S���Y���Ōo�H�𐶐�(�̐�, ��������q�̐�, �ő吢�㐔, �����m��, �ˑR�ψيm��) */
		solver.methodOfGA(100, 150, 300, 0.8, 0.1);

		/* ����H���̌v�Z */
		double length = solver.getDistance();
		System.out.println(length);

		System.out.println("stop.");
		time2 = System.currentTimeMillis(); // ���ݎ���2
		System.out.println("�������ԁF" + (time2 - time1) + "/ms");

		status = tspIO.writeData(length, (time2 - time1), solver, "result.csv");
	}

}
