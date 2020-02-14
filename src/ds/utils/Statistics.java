package ds.utils;

import java.util.List;

public class Statistics {
    public int sum (List<Integer> a){
        if (a.size() > 0) {
            int sum = 0;

            for (Integer i : a) {
                sum += i;
            }
            return sum;
        }
        return 0;
    }

    public int sum2 (List<Long> a){
        if (a.size() > 0) {
            int sum = 0;

            for (Long i : a) {
                sum += i;
            }
            return sum;
        }
        return 0;
    }
    public double mean (List<Integer> a){
        int sum = sum(a);
        double mean = 0;
        mean = sum / (a.size() * 1.0);
        return mean;
    }

    public double mean2 (List<Long> a){
        int sum = sum2(a);
        double mean = 0;
        mean = sum / (a.size() * 1.0);
        return mean;
    }

    public double sd (List<Integer> a){
        int sum = 0;
        double mean = mean(a);

        for (Integer i : a)
            sum += Math.pow((i - mean), 2);
        return Math.sqrt( sum / ( a.size() - 1 ) ); // sample
    }

    public double sd2 (List<Long> a){
        int sum = 0;
        double mean = mean2(a);

        for (Long i : a)
            sum += Math.pow((i - mean), 2);
        return Math.sqrt( sum / ( a.size() - 1 ) ); // sample
    }
}
