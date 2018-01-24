
public class Tour {
    private int[] route;
    private Map map;
    private int cityCount;

    /**
     * @param n 都市数
     */
    public Tour(int n) {
        // TODO 自動生成されたコンストラクター・スタブ
        cityCount = n;
        route = new int[n];
        map = new Map(n);
    }

    /**
     * @param m 地図データ
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
     * @param route 経路配列
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
     * @param map 地図データ
     */
    public void setMap(Map map) {
        for (int i = 0; i < cityCount; i++) {
            this.map.setCity(i, map.getCity(i).x, map.getCity(i).y);
        }
    }

    /**
     * 経路を生成する
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
     * @param a 都市番号A
     * @param b 都市番号B
     * @return AB間のユークリッド距離（1/1000単位）
     */
    public double getDistance(int a, int b) {
        Point cityA = map.getCity(a);
        Point cityB = map.getCity(b);

        return (double) ((int) (Math.sqrt(Math.pow(cityA.x - cityB.x, 2)
                + Math.pow(cityA.y - cityB.y, 2)) * 1000 + 0.5) / 1000.0);
    }

    /**
     * @return 全経路のユークリッド距離総和
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
