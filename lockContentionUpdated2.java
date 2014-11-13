package com.LockContention.Test;

import java.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map.Entry;
public class LockContentionCalculation {
/**
* @param
*/
		public static HashMap<Long, String> lockMap = new HashMap<Long, String>();
		public static HashMap<Long, String> unlockMap = new HashMap<Long, String>();
		public static HashMap<Long, Integer> threadIdLock = new HashMap<Long, Integer>();
		public static HashMap<Long, Integer> threadIdunLock = new HashMap<Long, Integer>();
		public static List<Long> locks = new ArrayList<Long>();
		public static List<Long> unlocks = new ArrayList<Long>();
		public static List<Long> waitTime = new ArrayList<Long>();
		public static int lockContention=0;
		private static final int NTHREADS = 4;
		public static int threadIdCount = 0;
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			String inputFileName = args[0];
			System.out.println("Input file: " + inputFileName);
			readInputFile(inputFileName);
			
			/*int threadCount = 0;
			threadCount = returnThreadIdCountLock(1585104640L);
			lockMap.put(1415250536890029L, Integer.toString(threadCount)+Long.toString(1585104640L));
			threadCount = returnThreadIdCountLock(1589307136L);
			lockMap.put(1415250536890097L, Integer.toString(threadCount)+Long.toString(1589307136L));
			threadCount = returnThreadIdCountLock(1591408384L);
			lockMap.put(1415250536890112L, Integer.toString(threadCount)+Long.toString(1591408384L));
			threadCount = returnThreadIdCountLock(1587205888L);
			lockMap.put(1415250536890074L, Integer.toString(threadCount)+Long.toString(1587205888L));
			threadCount = returnThreadIdCountUnlock(1585104640L);
			unlockMap.put(1415250536890179L, Integer.toString(threadCount)+Long.toString(1585104640L));
			threadCount = returnThreadIdCountUnlock(1589307136L);
			unlockMap.put(1415250536890311L, Integer.toString(threadCount)+Long.toString(1589307136L));
			threadCount = returnThreadIdCountUnlock(1591408384L);
			unlockMap.put(1415250536890378L, Integer.toString(threadCount)+Long.toString(1591408384L));
			threadCount = returnThreadIdCountUnlock(1587205888L);
			unlockMap.put(1415250536890450L, Integer.toString(threadCount)+Long.toString(1587205888L));
			*/
			//System.out.println(lockMap.keySet());
			//System.out.println(lockMap.values());
			//System.out.println(unlockMap.keySet());
			//System.out.println(unlockMap.values());
			
			
			unlocks = Collections.list(Collections.enumeration(unlockMap.keySet()));
			Collections.sort(unlocks);
			for(int i = 0; i < unlocks.size(); i++) {   
			    //System.out.println(unlocks.get(i));
			} 
			
			for(int i = 0; i < unlocks.size(); i++) {   
			    locks.add(returnLockElement(unlocks.get(i)));
				//System.out.println(unlocks.get(i)+" "+locks.get(i));
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
				//System.out.println("Thread waittime" +waitTime.get(i));
			}
			
			//System.out.println(waitTime.size());
			
		}
		
		
		public static Long returnLockElement(Long value){
			
			String x = unlockMap.get(value);
			Long y=0L ;
			for(Entry<Long, String> entry: lockMap.entrySet()){
	            if(x.equalsIgnoreCase(entry.getValue())){
	                y = entry.getKey();
	                break; //breaking because its one to one map
	            }
	            
	        }

			System.out.println(value +" " +y);
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
        	
        	if(vals[4].equalsIgnoreCase("lock")){
        		threadLockCount = returnThreadIdCountLock(Long.parseLong(vals[3]));
    			lockMap.put((long)Double.parseDouble(vals[1]), Integer.toString(threadLockCount)+vals[3]);
        	}
        	
        	if(vals[4].equalsIgnoreCase("unlock")){
        		threadunLockCount = returnThreadIdCountUnlock(Long.parseLong(vals[3]));
    			unlockMap.put((long)Double.parseDouble(vals[1]), Integer.toString(threadunLockCount)+vals[3]);
        	}
        	
        }
        
        reader.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    
}



}
