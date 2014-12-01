package com.LockContention.Test;

import java.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
public class LockContentionCalculation {
/**
* @param
*/
		public static HashMap<String, Long> lockMap = new HashMap<String, Long>();
		public static HashMap<String, Long> unlockMap = new HashMap<String, Long>();
		public static HashMap<Long, Integer> threadIdLock = new HashMap<Long, Integer>();
		public static HashMap<Long, Integer> threadIdunLock = new HashMap<Long, Integer>();
		public static HashMap<Long, Long> waitTimeThread = new HashMap<Long, Long>();
		public static List<Long> locks = new ArrayList<Long>();
		public static List<Long> unlocks = new ArrayList<Long>();
		public static List<Long> waitTime = new ArrayList<Long>();
		public static int lockContention=0;
		//private static final int NTHREADS = 4;
		public static int threadIdCount = 0;
		public static int threadSize =0;
		
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			String inputFileName = args[0];
			System.out.println("Input file: " + inputFileName);
			readInputFile(inputFileName);
			
			/*int threadCount = 0;
			threadCount = returnThreadIdCountLock(1585104640L);
			lockMap.put(Integer.toString(threadCount)+Long.toString(1585104640L), 1415250536890029L);
			threadCount = returnThreadIdCountLock(1589307136L);
			lockMap.put(Integer.toString(threadCount)+Long.toString(1589307136L), 1415250536890097L);
			threadCount = returnThreadIdCountLock(1591408384L);
			lockMap.put(Integer.toString(threadCount)+Long.toString(1591408384L), 1415250536890112L);
			threadCount = returnThreadIdCountLock(1587205888L);
			lockMap.put(Integer.toString(threadCount)+Long.toString(1587205888L), 1415250536890074L);
			threadCount = returnThreadIdCountUnlock(1585104640L);
			unlockMap.put(Integer.toString(threadCount)+Long.toString(1585104640L), 1415250536890179L);
			threadCount = returnThreadIdCountUnlock(1589307136L);
			unlockMap.put(Integer.toString(threadCount)+Long.toString(1589307136L), 1415250536890311L);
			threadCount = returnThreadIdCountUnlock(1591408384L);
			unlockMap.put(Integer.toString(threadCount)+Long.toString(1591408384L), 1415250536890378L);
			threadCount = returnThreadIdCountUnlock(1587205888L);
			unlockMap.put(Integer.toString(threadCount)+Long.toString(1587205888L), 1415250536890450L);*/
			
			HashMap<String, Long> unlockSortedMap = sortByValue(unlockMap);
			//System.out.println(lockMap.keySet());
			//System.out.println(lockMap.values());
			//System.out.println(unlockMap.keySet());
			//System.out.println(unlockSortedMap.values());
			
			
			//unlocks = Collections.list(Collections.enumeration(unlockMap.values()));
			//Collections.sort(unlocks);
			unlocks = Collections.list(Collections.enumeration(unlockSortedMap.values()));
			for(int i = 0; i < unlocks.size(); i++) {   
			    //System.out.println(unlocks.get(i));
			} 
			
			
			for(Entry<String, Long > entry: unlockSortedMap.entrySet()){
	            
	                String x = entry.getKey();
	                //Long y = lockMap.get(x);
	                locks.add(lockMap.get(x));
	                //break; //breaking because its one to one map
	            
	            
	        }
			
			for(int i = 0; i < unlocks.size(); i++) {   
			    //locks.add(returnLockElement(unlocks.get(i)));
				//System.out.println(unlocks.get(i)+" "+locks.get(i));
			}
			
			for(int i = 0; i < unlocks.size(); i++) {   
			    waitTime.add(0L);
				
			} 
			
			for(int i = 0; i < unlocks.size(); i++) { 
				waittimeCalculation(unlocks.get(i), waitTime.get(i));
				//System.out.println("value: " + waitTime.get(i));
				//waitTime.set(i, 0L);
				for(int j = i+1; j < locks.size(); j++){
					
					if(j!=i){
						//if((locks.get(j) < unlocks.get(i)) && (unlocks.get(j) > locks.get(i))){
						if(locks.get(j) < unlocks.get(i)){	
							lockContention++;
							Long val = unlocks.get(i) - locks.get(j);
							//System.out.println("value: " + (Long) val);
							//waittimeCalculation(unlocks.get(j), val);
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
			System.out.println("Avg. Lock contention " + ((double)lockContention/(double)unlocks.size()));
			System.out.println("Normalized: " + ((double)lockContention/(double)unlocks.size())/(double) waitTimeThread.size());
			printWaitTime();
			//for(int i = 0; i < waitTime.size(); i++) {   
			    //locks.add(returnLockElement(unlocks.get(i)));
				//System.out.println("Thread waittime" +waitTime.get(i));
			//}
			
			//System.out.println(waitTime.size());
			
		}
		
		
		public static Long returnLockElement(Long value){
			
			String x = "";
			Long y=0L ;
			for(Entry<String, Long > entry: unlockMap.entrySet()){
	            if(value.equals(entry.getValue())){
	                x = entry.getKey();
	                break; //breaking because its one to one map
	            }
	            
	        }
			
			y = lockMap.get(x);

			//System.out.println(value +" " +y);
			return y;
		}
		
public static Integer returnThreadIdCountLock(Long value){
			
			Integer x=0;
			Long y=0L ;
			if(threadIdLock.containsKey(value)){
				x= threadIdLock.get(value) + 1;
				threadIdLock.put(value, x);
				return x;
				
			}
			
			else{
				threadIdLock.put(value, 1);
				return 1;
			}
		}

public static Integer returnThreadIdCountUnlock(Long value){
	
	Integer x=0;
	Long y=0L ;
	if(threadIdunLock.containsKey(value)){
		x= threadIdunLock.get(value) + 1;
		threadIdunLock.put(value, x);
		return x;
		
	}
	
	else{
		threadIdunLock.put(value, 1);
		return 1;
	}
}


public static void waittimeCalculation(Long unlockTime, Long waitVal){
	
	String x = "";
	
	for(Entry<String, Long > entry: unlockMap.entrySet()){
        if(unlockTime.equals(entry.getValue())){
            x = entry.getKey();
            break; //breaking because its one to one map
        }
    }
	
	Long threadEntry =Long.parseLong(x.substring(x.length()-threadSize, x.length()));
	//System.out.println(threadEntry);

	if(waitTimeThread.containsKey(threadEntry)){
		Long y = waitTimeThread.get(threadEntry) + waitVal;
		waitTimeThread.put(threadEntry, y);
		
	}
	
	else{
		waitTimeThread.put(threadEntry, waitVal);
	}
	
	
}


public static void printWaitTime(){
	
	//System.out.println("Thread: "+waitTimeThread.keySet()+" waititme:"+waitTimeThread.values() );
	//System.out.println(waitTimeThread.values());
	//iterating over keys only
	int value;
	long maxWaitTime=0;
	long maxContThread=0;
	for (Long key : waitTimeThread.keySet()) {
	    value=threadIdunLock.get(key);
		System.out.println("Thread: " + key +" Avg. waiting time: "+ ((double) waitTimeThread.get(key)/(double) value));
	}
	
	
	for (Map.Entry<Long, Long> entry : waitTimeThread.entrySet()) {
		if(entry.getValue() > maxWaitTime){
			maxWaitTime = entry.getValue();
			maxContThread = entry.getKey();
		}
	    
	}
	
	System.out.println("Max Contened thread: " + maxContThread);
    System.out.println("Max waiting time: " + maxWaitTime);
}


public static HashMap sortByValue(HashMap unsortMap) {	 
	List list = new LinkedList(unsortMap.entrySet());
 
	Collections.sort(list, new Comparator() {
		public int compare(Object o1, Object o2) {
			return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
		}
	});
 
	HashMap sortedMap = new LinkedHashMap();
	for (Iterator it = list.iterator(); it.hasNext();) {
		Map.Entry entry = (Map.Entry) it.next();
		sortedMap.put(entry.getKey(), entry.getValue());
	}
	return sortedMap;
}


public static void readInputFile(String FileName){
    
    String line = null;
    int threadLockCount=0;
    int threadunLockCount=0;
    List<String> list = new ArrayList<String>();
    try {
        BufferedReader reader = new BufferedReader(new FileReader(FileName));
        while((line = reader.readLine()) != null){
        	if ( line.trim().length() == 0 ) {  
        	    continue;  // Skip blank lines  
        	  }
        	String[] vals = line.split("\\s+");
        	//for(int i=0;i<vals.length;i++){
        		//System.out.println(vals[i]);
        	//}
        	threadSize = vals[3].length();
        	
        	if(vals[4].equalsIgnoreCase("lock")){
        		threadLockCount = returnThreadIdCountLock(Long.parseLong(vals[3]));
    			lockMap.put(Integer.toString(threadLockCount)+vals[3], (long)Double.parseDouble(vals[1]));
        	}
        	
        	if(vals[4].equalsIgnoreCase("unlock")){
        		threadunLockCount = returnThreadIdCountUnlock(Long.parseLong(vals[3]));
    			unlockMap.put(Integer.toString(threadunLockCount)+vals[3], (long)Double.parseDouble(vals[1]));
        	}
        	
        }
        
        reader.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    
}






}
