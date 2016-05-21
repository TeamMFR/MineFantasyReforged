package minefantasy.mf2.api.helpers;

public class Functions 
{
	/**
	 * f(x) = cos(xPi/(f/2))*(m/2) + (m/2) + c
	 * Create a wave output from c to m and back between x=f
	 * @param x the running variable
	 * @param f frequency: where it outputs between c and m
	 * @param m magnitude: the max point at f/2
	 * @param c constant: the min point at f==0
	 */
	public static int getIntervalWave1_i(int x, int f, int m, int c)
	{
		double fx = Math.cos(x*Math.PI/ (f/2)) * ((m-c)/2) + ((m-c)/2) + c;
		return (int)fx;
	}
	/**
	 * f(x) = cos(xPi/(f/2))*(m/2) + (m/2) + c
	 * Create a wave output from c to m and back between x=f
	 * @param x the running variable
	 * @param f frequency: where it outputs between c and m
	 * @param m magnitude: the max point at f/2
	 * @param c constant: the min point at f==0
	 */
	public static double getIntervalWave1_d(double x, double f, double m, double c)
	{
		double fx = Math.cos(x*Math.PI/ (f/2)) * ((m-c)/2) + ((m-c)/2) + c;
		return fx;
	}
}
