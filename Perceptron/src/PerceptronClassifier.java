import java.util.Random;

/**
 * Created by kasun on 3/20/17.
 */
public class PerceptronClassifier {

    private double BIAS_VALUE = 1.0;
    private double thresholdVal;
    private double[] weightVec;

    /**
     * Train the Perceptron Classifier
     *
     * @param input        - input features
     * @param output       - labels
     * @param learningRate
     * @param iterations
     * @param thresholdVal
     */

    public void train(double[][] input, int[] output, double learningRate, int iterations, double thresholdVal) {

        this.thresholdVal = thresholdVal;
        int inputSize = input[0].length + 1;
        int dataSize = input.length;
        double[][] inputVec = new double[dataSize][inputSize];
        this.weightVec = new double[inputSize];
        Random rand = new Random();

        //Initialize the weight vector
        for (int i = 0; i < weightVec.length; i++) {
            this.weightVec[i] = rand.nextDouble();

        }
        // Adding bias value to end of the input array
        for (int i = 0; i < dataSize; i++) {
            System.arraycopy(input[i], 0, inputVec[i], 0, inputSize - 1);
            inputVec[i][inputSize - 1] = BIAS_VALUE;
        }

        //Update the weights by minimizing the error
        for (int k = 0; k < iterations; k++) {
            double globalError = 0;
            for (int i = 0; i < dataSize; i++) {
                double desOutput = classifyData(inputVec[i]);
                double localError = output[i] - desOutput;
                globalError += localError;

                for (int j = 0; j < inputSize; j++) {
                    this.weightVec[j] += learningRate * localError * inputVec[i][j];
                }
            }
            if (globalError == 0) {
                System.out.println("error minimized");
                break;
            }

        }

    }

    /**
     * Classify the given input
     *
     * @param inputVec - input to be classified
     * @return label of the input
     */
    public int classifyData(double[] inputVec) {
        double dout = 0;
        for (int i = 0; i < inputVec.length; i++)
            dout += weightVec[i] * inputVec[i];

        return dout > thresholdVal ? 1 : 0;
    }

    /**
     * Evaluate the classifier accuracy
     *
     * @param input  - testing features
     * @param output - testing labels
     */
    public void testClassifier(double[][] input, int[] output) {
        double accurateResultCount = 0;
        for (int i = 0; i < input.length; i++) {
            int result = classifyData(input[i]);
            if (result == output[i])
                accurateResultCount++;
        }
        double accuracy = accurateResultCount * 100 / (input.length);
        System.out.println("Overall Testing accuracy is: " + accuracy + "%");
    }
}
