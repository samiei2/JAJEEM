package com.jajeem.util.Threading;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
	private ExecutorService pool;
	private static ExecutorService tempRunPools = Executors.newCachedThreadPool();
	private ExecutorService singleRun = Executors.newSingleThreadExecutor();
	private static HashMap<String, ThreadManager> privatePools = new HashMap<>();
	
	private ThreadManager() {
		
	}
	
	private ThreadManager(String poolName) {
		privatePools.put(poolName, this);
		pool = Executors.newFixedThreadPool(10);
	}

	private ThreadManager(String poolName, int poolSize) {
		privatePools.put(poolName, this);
		pool = Executors.newFixedThreadPool(poolSize);
	}

	public static ThreadManager getInstance(){
		if(privatePools.containsKey("Default"))
			return privatePools.get("Default");
		else
			return new ThreadManager("Default");
	}
	
	public static ThreadManager getInstance(String poolName) {
		if(privatePools.containsKey(poolName))
			return privatePools.get(poolName);
		else
			return new ThreadManager(poolName);
	}
	
	public void run(Runnable command){
		pool.execute(command);
	}
	
	public void runTemp(Runnable command){
		tempRunPools.execute(command);
	}
	
	public void runSingle(Runnable command){
		singleRun.execute(command);
	}
}
