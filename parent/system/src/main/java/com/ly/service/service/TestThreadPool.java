package com.ly.service.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPool {

	private ThreadPoolExecutor tpe = new ThreadPoolExecutor(10, 100, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
	
	public void hh() {
		Future<String> aa = tpe.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				Thread.sleep(10000);
				return "hello";
			}
		
		});
		
		try {
			String result = aa.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		tpe.execute(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("hello world");
				return;
			}
		});
		
		new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
}
