// Paige Weber, L22812475
// Times and evaluates the following implementation of ThreeSum
// for (int i = 0; i < n; i++)
//   for (int j = 0; j < n; j++)
//      for (int k = 0; k < n; k++)
//         if (i < j && j < k)
//          if (a[i] + a[j] + a[k] == 0)
//               count++

import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class NaiveThreeSum
{
    public static void main(String[] args)
    {
        //user input for max, min, how many
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the maximum integer: ");
        int max = scanner.nextInt();
        System.out.println("Enter the minimum integer: ");
        int min = scanner.nextInt();
        System.out.println("Enter how many integers: ");
        int howMany = scanner.nextInt();

        //create an array of integers 
        int[] smallArray = new int[howMany];
        
        Random random = new Random();
        for(int i = 0; i < howMany; i++)
        {
            int randomVal = random.nextInt((max - min) + 1) + min;
            smallArray[i] = randomVal;
        }
    
        //sort from negative to postive
        Arrays.sort(smallArray);

        //make array double the size and sort
        int[] bigArray = new int[howMany * 2];

        for(int i = 0; i < howMany * 2; i++)
        {
            int randomVal2 = random.nextInt((max - min) + 1) + min;
            bigArray[i] = randomVal2;
        }

        Arrays.sort(bigArray);

        //run through and time
        final long startTime1 = System.currentTimeMillis();

        ThreeSum(smallArray, howMany);

        final long endTime1 = System.currentTimeMillis();
        final long finalTime1 = endTime1 - startTime1;
        System.out.println("Small array execution time: " + finalTime1 + " milliseconds");

        final long startTime2 = System.currentTimeMillis();

        ThreeSum(bigArray, (howMany * 2));

        final long endTime2 = System.currentTimeMillis();
        final long finalTime2 = endTime2 - startTime2;
        System.out.println("Big array execution time: " + finalTime2 + " milliseconds");

        //divide 2n/n to find the integer that tells you how much longer it takes (should be 8ish --- need n to be close to 200)
        System.out.println("The big array was " + (finalTime2/finalTime1) + " times slower than the smaller array.");
    }

        public static int ThreeSum(int[] a, int n)
        {
            int count = 0;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    for (int k = 0; k < n; k++)
                        if (i < j && j < k)
                            if (a[i] + a[j] + a[k] == 0)
                                count++;
            return count;
        }
    
}




