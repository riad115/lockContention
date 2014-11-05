package com.LockContention.Test;
import java.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

public class LockContentionCalculation {

	/**
	 * @param 
	 */
	public static HashMap<Double, Integer> lockMap = new HashMap<Double, Integer>();
	public static HashMap<Double, Integer> unlockMap = new HashMap<Double, Integer>();
	public static List<Double> locks = new ArrayList<Double>();
	public static List<Double> unlocks = new ArrayList<Double>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		lockMap.put(1414690415796600.00, 1);
		lockMap.put(1414690415796571.00, 2);
		lockMap.put(1414690415796604.00, 3);
		lockMap.put(1414690415796690.00, 4);
		
		
		unlockMap.put(1414690415796983.00, 1);
		unlockMap.put(1414690415796604.00, 2);
		unlockMap.put(1414690415796604.00, 3);
		unlockMap.put(1414690415796604.00, 4);
		unlocks = Collections.list(Collections.enumeration(unlockMap.keySet()));
		Collections.sort(unlocks);
		
	}

}
