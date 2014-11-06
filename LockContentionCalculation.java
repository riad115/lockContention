package com.LockContention.Test;

import java.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map.Entry;
public class LockContentionCalculation {
/**
* @param
*/
		public static HashMap<Long, Integer> lockMap = new HashMap<Long, Integer>();
		public static HashMap<Long, Integer> unlockMap = new HashMap<Long, Integer>();
		public static List<Long> locks = new ArrayList<Long>();
		public static List<Long> unlocks = new ArrayList<Long>();
		public static List<Long> waitTime = new ArrayList<Long>();
		public static int lockContention=0;
		private static final int NTHREADS = 4;
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			lockMap.put(1415250536890029L, 1);
			lockMap.put(1415250536890097L, 2);
			lockMap.put(1415250536890112L, 3);
			lockMap.put(1415250536890074L, 4);
			unlockMap.put(1415250536890179L, 1);
			unlockMap.put(1415250536890311L, 2);
			unlockMap.put(1415250536890378L, 3);
			unlockMap.put(1415250536890450L, 4);
			unlocks = Collections.list(Collections.enumeration(unlockMap.keySet()));
			Collections.sort(unlocks);
			for(int i = 0; i < unlocks.size(); i++) {   
			    //System.out.println(unlocks.get(i));
			} 
			
			for(int i = 0; i < unlocks.size(); i++) {   
			    locks.add(returnLockElement(unlocks.get(i)));
				//System.out.println(locks.size());
			}
			
			for(int i = 0; i < unlocks.size(); i++) {   
			    waitTime.add(0L);
				
			} 
			
			for(int i = 0; i < unlocks.size(); i++) {   
				for(int j = i+1; j < locks.size(); j++){
					
					if(j!=i){
						//if((locks.get(j) < unlocks.get(i)) && (unlocks.get(j) > locks.get(i))){
						if(locks.get(j) < unlocks.get(i)){	
							lockContention++;
							Long val = unlocks.get(i) - locks.get(j);
							//System.out.println("value: " + (Long) val);
							if(val > waitTime.get(j)){
								waitTime.set(j, val);
							}
						}
						
					}
					else {
						break;
					}
					
				}
				
			    
			} 
			
			System.out.println("Lock contention " + lockContention);
			
			for(int i = 0; i < waitTime.size(); i++) {   
			    //locks.add(returnLockElement(unlocks.get(i)));
				System.out.println("Thread waittime" +waitTime.get(i));
			}
			
			System.out.println(waitTime.size());
			
		}
		
		
		public static Long returnLockElement(Long value){
			
			Integer x = unlockMap.get(value);
			Long y=0L ;
			for(Entry<Long, Integer> entry: lockMap.entrySet()){
	            if(x.equals(entry.getValue())){
	                y = entry.getKey();
	                break; //breaking because its one to one map
	            }
	        }


			return y;
		}
		
}
