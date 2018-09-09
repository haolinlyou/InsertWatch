package com.lk.watch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lk.watch.util.QueueUtil;

/**
*
* @author lk
* @date 2018Äê9ÔÂ9ÈÕ
*/

public class FileWatchDemo {
	
	
	public static void main(String[] args) {
		String watchDir = args[0];
		int threadNum = Integer.valueOf(args[1]);
		int batchNum = Integer.valueOf(args[2]);
		
		
		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
		        .setNameFormat("demo-pool-%d").build();
		ExecutorService servicePool = new ThreadPoolExecutor(10, 200,
			        0L, TimeUnit.MILLISECONDS,
			        new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
		
		
		
		servicePool.submit(new FileWatcher(watchDir,batchNum));
		
		for (int i=0; i<threadNum; i++) {
			servicePool.submit(new FileComsumer());
		}
		servicePool.submit(new FileWriter(watchDir));
		 
	}

}
