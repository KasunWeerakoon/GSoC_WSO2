import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kasun on 3/23/17.
 */
public class PerceptronTest {

    /**
     * Generate the data set
     *
     * @param size
     * @param lenght
     * @return - data array
     */
    public static double[][] createDataset(int size, int lenght) {
        double[][] dataArr = new double[size][lenght];
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < size / 2; i++) {
            for (int j = 0; j < lenght; j++) {
                dataArr[i][j] = random.nextInt(1, 10 + 1);
            }
        }
        for (int i = size / 2; i < size; i++) {
            for (int j = 0; j < lenght; j++) {
                dataArr[i][j] = random.nextInt(30, 40 + 1);
            }
        }
        return dataArr;
    }

    /**
     * Create the labels for generated data set
     *
     * @return - output array
     */
    static int[] createOutout(int size) {
        int[] output = new int[size];
        for (int i = 0; i < size; i++) {
            if (i < size / 2) {
                output[i] = 0;
            } else {
                output[i] = 1;
            }
        }
        return output;
    }

    public static void main(String[] args) {
        PerceptronClassifier perceptron = new PerceptronClassifier();
        double[][] trainingData = createDataset(100, 2);
        int[] trainingDataLabels = createOutout(100);

        //Train the Perceptron Classifier
        perceptron.train(trainingData, trainingDataLabels, .001, 500, 0);

        //Create Testing Data
        double[][] testingData = createDataset(20, 2);
        int[] testingDataLabels = createOutout(20);

        //Test the Perceptron Classifier
        perceptron.testClassifier(testingData, testingDataLabels);
    }
}
