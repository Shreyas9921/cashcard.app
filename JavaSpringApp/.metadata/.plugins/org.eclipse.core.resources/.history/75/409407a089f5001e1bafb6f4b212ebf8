package shreyas.main;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {
	
	//DP Approach with Memoization map to store already computed fibonacci numbers
	private static Map<Integer, Long> memoMap = new HashMap<>();
	
	public static long fibonacciDP(int n) {
		if(n <= 1)
			return n;
		
        // Check if the Fibonacci number is already computed
		if(memoMap.containsKey(n)) {
			return memoMap.get(n);
		}
		
        // Compute Fibonacci number using recursion and memoization
		long fib = fibonacciDP(n-1) + fibonacciDP(n-2);
		
        // Store the computed Fibonacci number in the memoization map
		memoMap.put(n, fib);
		
		return fib;
	}
	
	
	//DP approach using Array
	public static long[] fibonacciArray(int n) {
		long[] fibArr = new long[n+1];
		fibArr[0] = 0;
		fibArr[1] = 1;
		
		for(int i = 2; i <= n; i++) {
			fibArr[i] = fibArr[i-1] + fibArr[i-2];
		}
		
		return fibArr;
	}

}
