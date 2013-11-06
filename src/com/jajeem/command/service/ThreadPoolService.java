package com.jajeem.command.service;

import java.util.ArrayList;

public class ThreadPoolService {
	static ArrayList<Thread> pool = new ArrayList<>();
	public ThreadPoolService(){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				PoolStatusChecker();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void InsertThread(Runnable r){
		Thread t = new Thread(r);
		t.start();
		pool.add(t);
	}
	
	public void PoolStatusChecker(){
		for (int i = 0; i < pool.size(); i++) {
			if(!pool.get(i).isAlive())
				pool.remove(i);
		}
	}
}
