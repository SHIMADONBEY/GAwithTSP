public class Map {
	protected int cityCount;
	protected Point[] cities;
	private double[][] edge;

	public Map(int n) {
		cityCount = n;
		cities = new Point[cityCount];
		edge = new double[n][n];
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

	public double[][] getEdge() {
		return this.edge;
	}

	public double getEdge(int a, int b) {
		return this.edge[a][b];
	}

	public void setEdge(double[][] edge) {
		for (int i = 0; i < cityCount; i++) {
			for (int j = 0; j < cityCount; j++) {
				this.edge[i][j] = edge[i][j];
			}
		}
	}

	public void calcEdge() {
		for (int i = 0; i < cityCount; i++) {
			int j;
			edge[i][i] = 0;
			for (j = 0; j < i; j++) {
				edge[i][j] = edge[j][i];
			}
			for (j = i + 1; j < cityCount; j++) {
				edge[i][j] = Math.sqrt(Math.pow(cities[i].x - cities[j].x, 2)
						+ Math.pow(cities[i].y - cities[j].y, 2));
			}
		}
	}
}
