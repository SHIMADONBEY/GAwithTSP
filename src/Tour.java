
public class Tour {
    private int[] route;
    private Map map;
    private int cityCount;

    /**
     * @param n �s�s��
     */
    public Tour(int n) {
        // TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
        cityCount = n;
        route = new int[n];
        map = new Map(n);
    }

    /**
     * @param m �n�}�f�[�^
     */
    public Tour(Map m) {
        cityCount = m.getCityCount();
        route = new int[cityCount];
        map = new Map(cityCount);
        setMap(m);
    }

    public int[] getRoute() {
        return route;
    }

    public int getRoute(int p) {
        return route[p];
    }

    /**
     * @param route �o�H�z��
     */
    public void setRoute(int[] route) {
        for (int i = 0; i < cityCount; i++) {
            this.route[i] = route[i];
        }
    }

    public Map getMap() {
        return map;
    }

    /**
     * @param map �n�}�f�[�^
     */
    public void setMap(Map map) {
        for (int i = 0; i < cityCount; i++) {
            this.map.setCity(i, map.getCity(i).x, map.getCity(i).y);
        }
    }

    /**
     * �o�H�𐶐�����
     */
    public void makeTour() {
        int i, j;
        int swp;
        int cityCount = map.getCityCount();

        for (i = 0; i < cityCount; i++) {
            route[i] = i;
        }

        for (i = 0; i < cityCount; i++) {
            j = (int) (Math.random() * (cityCount - i)) + i;
            swp = route[i];
            route[i] = route[j];
            route[j] = swp;
        }
    }

    /**
     * @param a �s�s�ԍ�A
     * @param b �s�s�ԍ�B
     * @return AB�Ԃ̃��[�N���b�h�����i1/1000�P�ʁj
     */
    public double getDistance(int a, int b) {
        Point cityA = map.getCity(a);
        Point cityB = map.getCity(b);

        return (double) ((int) (Math.sqrt(Math.pow(cityA.x - cityB.x, 2)
                + Math.pow(cityA.y - cityB.y, 2)) * 1000 + 0.5) / 1000.0);
    }

    /**
     * @return �S�o�H�̃��[�N���b�h�������a
     */
    public double getDistance() {
        long total = 0;
        int cityCount = map.getCityCount();

        for (int i = 0; i < map.getCityCount(); i++) {
            total += getDistance(route[i], route[(i + 1) % cityCount]) * 1000 + 0.5;
        }
        return (double) total / 1000.0;
    }
}
