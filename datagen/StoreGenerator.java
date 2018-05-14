import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StoreGenerator {

	static final double INF = 1e50;
	static final int POINT_ITERATIONS = 100;

	static double xMin;
	static double yMin;
	static double xMax;
	static double yMax;

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("Usage: StoreGenerator count range name-file");
			System.out.println("range represents a rectangle in the form x1,y1-x2,y2.");
			System.out.println("name-file contains a list of vendor names.");
			return;
		}

		int count = Integer.parseInt(args[0]);
		String[] coords = args[1].split("-");
		String[] pair1 = coords[0].split(",");
		String[] pair2 = coords[1].split(",");
		xMin = Long.parseLong(pair1[0]);
		yMin = Long.parseLong(pair1[1]);
		xMax = Long.parseLong(pair2[0]);
		yMax = Long.parseLong(pair2[1]);
		if (xMin > xMax) {
			double temp = xMin;
			xMin = xMax;
			xMax = temp;
		}
		if (yMin > yMax) {
			double temp = yMin;
			yMin = yMax;
			yMax = temp;
		}
		List<String> vendors = read(args[2]);

		List<Point> points = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			addPoint(points);
		}

		int id = 0;
		for (Point p : points) {
			System.out.printf("%d\t%s\t%s\n", id++, rand(vendors), p);
		}
	}

	static List<String> read(String filename) throws Exception{
		return Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
	}

	static void addPoint(List<Point> points) {
		if (points.isEmpty()) {
			points.add(randPoint());
			return;
		}

		// Try to add a point as far as possible from existing points.
		Point[] candidates = new Point[POINT_ITERATIONS];
		for (int i = 0; i < POINT_ITERATIONS; i++) {
			candidates[i] = randPoint();
		}

		double bestScore = 0;
		Point bestPoint = null;
		for (int i = 0; i < candidates.length; i++) {
			double minDist2 = INF;
			for (Point p : points) {
				minDist2 = Math.min(minDist2, p.dist2(candidates[i]));
			}
			if (minDist2 > bestScore) {
				bestScore = minDist2;
				bestPoint = candidates[i];
			}
		}
		points.add(bestPoint);
	}

	static Point randPoint() {
		double x = xMin + Math.random() * (xMax - xMin);
		double y = yMin + Math.random() * (yMax - yMin);
		return new Point(x, y);
	}

	static <T> T rand(List<T> list) {
		int i = (int) (Math.random() * list.size());
		return list.get(i);
	}

	static class Point {
		double x;
		double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double dist2(Point other) {
			double dx = x - other.x;
			double dy = y - other.y;
			return dx * dx + dy * dy;
		}

		public String toString() {
			return String.format("%.4f,%.4f", x, y);
		}
	}
}