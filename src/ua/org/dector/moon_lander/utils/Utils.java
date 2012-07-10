package ua.org.dector.moon_lander.utils;

/**
 * @author dector (dector9@gmail.com)
 */
public class Utils {
    public static int toPowerOfTwo(int number) {
        double log = Math.log(number) / Math.log(2);

        if (log == (int)log) {
            return number;
        } else {
            return (int)Math.pow(2, (int)log + 1);
        }
    }
}
