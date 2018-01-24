public class Map {
    protected int cityCount;
    protected Point[] cities;

    public Map(int n) {
        cityCount = n;
        cities = new Point[cityCount];
    }

    public void setCity(int i, double x, double y) {
        cities[i] = new Point(x, y);
    }

    public Point getCity(int i) {
        return cities[i];
    }

    public Point[] getCities() {
        return cities;
    }

    public int getCityCount() {
        return cityCount;
    }
}
