package com.lk.watch;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lk.watch.util.QueueUtil;

public class FileWatcher implements Runnable {
	
	
	private String watchDir = "/data/lk";
	private int batchNum = 5;
	
	
		
	public FileWatcher(String watchDir, int batchNum) {
		super();
		this.watchDir = watchDir;
		this.batchNum = batchNum;
	}

	@Override
	public void run() {
		WatchService watcher = null;
		try {
			watcher = FileSystems.getDefault().newWatchService();
			
			Paths.get(watchDir).register(watcher, 
					StandardWatchEventKinds.ENTRY_CREATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Set<String> fileNames = new HashSet();
		while (true) {
			
			WatchKey key = null;
			try {
				key = watcher.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("key9");
				e.printStackTrace();
			}
			if (key != null) {
				
				List<WatchEvent<?>> events = key.pollEvents(); //获取所有得事件
		       
				for (WatchEvent event : events){
		        	
		        	WatchEvent.Kind<?> kind = event.kind();
		        	
		        	if (kind == StandardWatchEventKinds.OVERFLOW){
		              //当前磁盘不可用
		        		
		        		System.out.println("overflow ");
		        		continue;
		            }
		            WatchEvent<Path> ev = event;
		            Path path = ev.context();
		            
		            if(kind == StandardWatchEventKinds.ENTRY_CREATE) {
		            	
		            	System.out.println("create " + watchDir+path.getFileName());
		            	fileNames.add(watchDir+path.getFileName().toString());
						
						if (fileNames.size() == batchNum) {
							QueueUtil.putUserDeque(fileNames);
							fileNames = new HashSet<>();
						}
		            }
				}
				
			}
			
			if(!key.reset()){ 
	            //已经关闭了进程
	            System.out.println("exit watch server");
	            break;
	            
			}
		}
		
	}
	
	
		
}