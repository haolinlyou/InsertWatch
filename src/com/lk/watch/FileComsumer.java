package com.lk.watch;

import java.io.File;
import java.util.Set;

import com.lk.watch.util.QueueUtil;

public class FileComsumer extends Thread{
	
	public FileComsumer() {};
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		
		while (true) {
			//System.out.println("InsertManger.userDeque.size() is " +InsertManger.userDeque.size());
			if (QueueUtil.getBlockingDeque().size() > 0) {
				
				Set<String> fileNames = null;
				try {
					fileNames = (Set<String>) QueueUtil.getUserDeque();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (fileNames != null) {
					for (String fileName: fileNames) {
						
						System.out.println("get file:"+fileName);
						delete(fileName);
						
					}
				}
				
			}
			
		}
	}
	
	private void delete(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			file.delete();
			System.out.println("delete file  "+fileName);
		}
	}

	
	
}
