import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kasun on 4/3/17.
 */
public class Perceptron {
    private double learningRate;
    private int featureSize;
    private double[] weightVec;
    private double thresholdVal = 0;
    private boolean initialPerceptron = true;



    public Perceptron() {
        //set default learning rate
        this(0.00001);
    }

    public Perceptron(double learningRate) {
        this.learningRate = learningRate;
    }

    /**
     * Train the Perceptron model
     * @param features
     * @param lable
     */

    public void fit(double[] features, double lable) {
        if (this.initialPerceptron) {
            this.featureSize = features.length;
            initializeWeights();
            this.initialPerceptron = false;
        }
        int predictedValue = this.predict(features);
        double delta = lable - predictedValue;
        for (int i = 0; i < weightVec.length - 1; i++)
            weightVec[i] += learningRate * delta * features[i];

        //update the weight related to bias term
        weightVec[weightVec.length - 1] = learningRate * delta;
    }

    /**
     * Initialize weight vector and add bias term at the end
     */
    private void initializeWeights() {
        this.weightVec = new double[featureSize + 1];
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < weightVec.length; i++)
            this.weightVec[i] = random.nextDouble(0, 1);
    }

    /**
     * Predict the label for given feature set
     * @param features
     * @return predicted value
     */
    public int predict(double[] features) {
        double prediction = 0;
        for (int i = 0; i < features.length; i++)
            prediction += features[i] * weightVec[i];

        //adding bias value to prediction
        prediction += weightVec[weightVec.length - 1];
        return prediction > thresholdVal ? 1 : 0;
    }

    public void resetPerceptron(){
        this.weightVec = null;
        this.initialPerceptron = true;
        learningRate = 0.0;
        featureSize = 0;
    }

}
