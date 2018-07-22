package com.fiberhome.insert;


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

public class InsertWatch implements Runnable {
	
	
		
		@Override
		public void run() {
			WatchService watcher = null;
			try {
				watcher = FileSystems.getDefault().newWatchService();
				Paths.get("f:\\tmp").register(watcher, 
						StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_DELETE,
						StandardWatchEventKinds.ENTRY_MODIFY);
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
					e.printStackTrace();
				}
				if (key != null) {
					
					List<WatchEvent<?>> events = key.pollEvents(); //获取所有得事件
			       
					for (WatchEvent event : events){
			        	
			        	WatchEvent.Kind<?> kind = event.kind();
			        	
			        	if (kind == StandardWatchEventKinds.OVERFLOW){
			              //当前磁盘不可用
			        		continue;
			            }
			        	
			            WatchEvent<Path> ev = event;
			            Path path = ev.context();
			            if(kind == StandardWatchEventKinds.ENTRY_CREATE){
			              System.out.println("create " + path.getFileName());
			              fileNames.add(path.getFileName().toString());
			            }else if(kind == StandardWatchEventKinds.ENTRY_MODIFY){
			              System.out.println("modify " + path.getFileName());
			              fileNames.add(path.getFileName().toString());
			              //filenames.add(path.getFileName().toString());
			            }else if(kind == StandardWatchEventKinds.ENTRY_DELETE){
			              System.out.println("delete " + path.getFileName());
			            }
			          }
					
					InsertManger.putUserDeque(fileNames);
			          
			   
			          if(!key.reset()){ 
			            //已经关闭了进程
			            System.out.println("exit watch server");
			            break;
			          }
			        }
			      

				}
			
		}
		
}