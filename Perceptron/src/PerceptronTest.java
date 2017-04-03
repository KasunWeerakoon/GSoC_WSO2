import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Created by kasun on 3/23/17.
 */
public class PerceptronTest {

    public static void main(String[] args) {
        List<double[]> dataset = new ArrayList<double[]>();
        int iterationCount = 500;
        double istartTime, istopTime, ielapsedTime;
        DecimalFormat formatter;
        int truePrediction = 0;
        int falsePrediction = 0;

        try {
            String absolutePath = new File("src/Data/DataSet.txt").getAbsolutePath();
            BufferedReader br = new BufferedReader(new FileReader(absolutePath));
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                String[] ar = sCurrentLine.split(",");
                if (ar[ar.length - 1].equals("Iris-virginica")) {
                    ar[ar.length - 1] = "1";
                } else {
                    ar[ar.length - 1] = "0";
                }
                double[] doubleValues = Arrays.stream(ar)
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                dataset.add(doubleValues);
            }
        } catch (IOException e) {
            System.out.println(e.fillInStackTrace());
        }


        // Shuffle the data set for higher accuracy
        Collections.shuffle(dataset);

        Perceptron perceptron = new Perceptron();
        /**
         * Train the model by whole data set and calculate the accuracy by training set
         */
        //Iterate the same data set
        istartTime = System.currentTimeMillis();
        for (int i = 0; i < iterationCount; i++) {

//           double startTime = System.currentTimeMillis();

            for (double[] d : dataset) {
                double[] features = new double[d.length - 1];
                System.arraycopy(d, 0, features, 0, features.length);

                //Train the perceptron by sending tuple at a time
                perceptron.fit(features, d[d.length - 1]);
            }
//            double stopTime = System.currentTimeMillis();
//            double elapsedTime = stopTime - startTime;
//            DecimalFormat formatter = new DecimalFormat("#0.0000000000000000000000");
//            System.out.println("Time taken to run through the dataset "+formatter.format( elapsedTime ));
        }
        istopTime = System.currentTimeMillis();
        ielapsedTime = istopTime - istartTime;
        formatter = new DecimalFormat("#0.00000");
        System.out.println("Time taken to run through the dataset " + iterationCount + " time " + formatter.format(ielapsedTime));
        truePrediction = 0;
        falsePrediction = 0;

        for (double[] d : dataset) {
            double[] features = new double[d.length - 1];


            System.arraycopy(d, 0, features, 0, features.length);
            int predict = perceptron.predict(features);
            if (predict == d[d.length - 1]) {
                truePrediction++;
            } else {
                falsePrediction++;
            }
        }
        System.out.println("Calculate the accuracy by training set \n Accuracy " + truePrediction * 100 / (truePrediction + falsePrediction) + "%");

        /**
         * Train the model using 70% of the data set and validate the model using remaining 30% of data set
         */

        //Reset the perceptron to train new model
        perceptron.resetPerceptron();

        List<double[]> trainingSet = dataset.subList(0, ((int) (dataset.size() * 0.7)));
        List<double[]> validationSet = dataset.subList(((int) (dataset.size() * 0.7)), dataset.size());

        istartTime = System.currentTimeMillis();
        for (int i = 0; i < iterationCount; i++) {
            for (double[] d : trainingSet) {
                double[] features = new double[d.length - 1];
                System.arraycopy(d, 0, features, 0, features.length);

                //Train the perceptron by sending tuple at a time
                perceptron.fit(features, d[d.length - 1]);
            }
        }
        istopTime = System.currentTimeMillis();
        ielapsedTime = istopTime - istartTime;
        formatter = new DecimalFormat("#0.00000");
        System.out.println("Time taken to run through the dataset " + iterationCount + " time " + formatter.format(ielapsedTime));
        truePrediction = 0;
        falsePrediction = 0;

        for (double[] d : validationSet) {
            double[] features = new double[d.length - 1];


            System.arraycopy(d, 0, features, 0, features.length);
            int predict = perceptron.predict(features);
            if (predict == d[d.length - 1]) {
                truePrediction++;
            } else {
                falsePrediction++;
            }
        }
        System.out.println("Validate the model using 30% of data set \n Accuracy " + truePrediction * 100 / (truePrediction + falsePrediction) + "%");

    }
}
