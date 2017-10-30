package minefantasy.mf2.api.helpers;

public class Functions {
    /**
     * f(x) = cos(xPi/(f/2))*(m/2) + (m/2) + c Create a wave output from c to m and
     * back between x=f
     *
     * @param x the running variable
     * @param p period: time between waves (in 20ths of seconds)
     * @param a amplitude: the max point at f/2
     * @param c constant: the min point at f==0
     */
    public static int getIntervalWave1_i(int x, int p, int a, int c) {
        double fx = Math.cos(x * Math.PI / (p / 2)) * ((a - c) / 2) + ((a - c) / 2) + c;
        return (int) fx;
    }

    /**
     * f(x) = cos(xPi/(f/2))*(m/2) + (m/2) + c Create a wave output from c to m and
     * back between x=f
     *
     * @param x the running variable
     * @param p period: time between waves (in 20ths of seconds)
     * @param a amplitude: the max point at f/2
     * @param c constant: the min point at f==0
     */
    public static double getIntervalWave1_d(double x, double p, double a, double c) {
        double fx = Math.cos(x * Math.PI / (p / 2)) * ((a - c) / 2) + ((a - c) / 2) + c;
        return fx;
    }
}
