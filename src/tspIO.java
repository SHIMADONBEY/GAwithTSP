import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class tspIO {

    private static int cityNum;

    private static Point[] cityCoordinate;

    public static int getCitynum() {
        return cityNum;
    }

    public static Point getCityCoordinate(int i) {
        return cityCoordinate[i];
    }

    /**
     * @param filename ファイルパス
     * @return 都市数
     * @throws Exception ArrayIndexOutOfBoundsException
     */
    public static int readData(String filename) throws Exception {

        FileInputStream in = null;

        try {
            File inputFile = new File(filename);
            in = new FileInputStream(inputFile);
        } catch (Exception e) {
            System.err.println("Unable to open data file: " + filename + "\n" + e);
            return 0;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;

        int i = 0;

        try {
            while ((line = br.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                if (tokenizer.countTokens() == 1) {
                    cityNum = Integer.parseInt(tokenizer.nextToken());
                    cityCoordinate = new Point[cityNum];

                } else if (cityNum > i) {
                    cityCoordinate[i] = new Point(Double.parseDouble(tokenizer.nextToken()),
                            Double.parseDouble(tokenizer.nextToken()));
                    i++;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        } finally {
            br.close();
        }
        return 1;
    }

    /**
     * @param l 都市数
     * @param t 計測時間
     * @param problemToSearch 経路
     * @param filename 出力ファイル名
     * @return 1
     * @throws Exception Exception
     */
    public static int writeData(double l, long t, Tour problemToSearch, String filename)
            throws Exception {

        /* 都市データの出力 */
        try {
            Map map = problemToSearch.getMap();
            // 書き込みファイル指定
            PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

            // 書き込みを行なう
            for (int i = 0; i < map.getCityCount(); i++) {
                fout.println(map.getCity(problemToSearch.getRoute(i)).x
                        + "," + map.getCity(problemToSearch.getRoute(i)).y);
            }
            fout.println(map.getCity(problemToSearch.getRoute(0)).x
                    + "," + map.getCity(problemToSearch.getRoute(0)).y);

            /* 巡回路の出力 */
            // for(int i = 0; i < problemToSearch.getCityCount(); i++){
            // fout.println(problemToSearch.getRoute(i));
            // }

            fout.println("\nExecution Time =" + t + "ms");
            fout.println("Length=" + l);

            // 書き込みファイルをクローズする
            fout.close();
        } catch (Exception e) {
            System.err.println("書き込みエラー: " + e);
            System.exit(1); // エラーならここで終了する
        }

        return 1;
    }

}
