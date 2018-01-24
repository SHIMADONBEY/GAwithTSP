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
     * @param filename �t�@�C���p�X
     * @return �s�s��
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
     * @param l �s�s��
     * @param t �v������
     * @param problemToSearch �o�H
     * @param filename �o�̓t�@�C����
     * @return 1
     * @throws Exception Exception
     */
    public static int writeData(double l, long t, Tour problemToSearch, String filename)
            throws Exception {

        /* �s�s�f�[�^�̏o�� */
        try {
            Map map = problemToSearch.getMap();
            // �������݃t�@�C���w��
            PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

            // �������݂��s�Ȃ�
            for (int i = 0; i < map.getCityCount(); i++) {
                fout.println(map.getCity(problemToSearch.getRoute(i)).x
                        + "," + map.getCity(problemToSearch.getRoute(i)).y);
            }
            fout.println(map.getCity(problemToSearch.getRoute(0)).x
                    + "," + map.getCity(problemToSearch.getRoute(0)).y);

            /* ����H�̏o�� */
            // for(int i = 0; i < problemToSearch.getCityCount(); i++){
            // fout.println(problemToSearch.getRoute(i));
            // }

            fout.println("\nExecution Time =" + t + "ms");
            fout.println("Length=" + l);

            // �������݃t�@�C�����N���[�Y����
            fout.close();
        } catch (Exception e) {
            System.err.println("�������݃G���[: " + e);
            System.exit(1); // �G���[�Ȃ炱���ŏI������
        }

        return 1;
    }

}
