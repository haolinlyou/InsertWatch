package com.fiberhome.insert;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class InsertService extends Thread{
	
	public InsertService() {};
	
	
	
	@Override
	public void run() {
		
		
		System.out.println("thread "+this.getId()+" start !");
		while (true) {
			//System.out.println("InsertManger.userDeque.size() is " +InsertManger.userDeque.size());
			if (!InsertManger.userDeque.isEmpty()) {
				
				Set<String> fileNames = InsertManger.getUserDeque();
				
				if (fileNames != null) {
					for (String fileName: fileNames) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("thread "+this.getId()+" : Current file name is "+fileName);
						delete(fileName);
						
					}
				}
				
			}
			
		}
	}
	
	private void delete(String fileName) {
		File file = new File("f:\\tmp\\"+fileName);
		if (file.exists()) {
			file.delete();
			System.out.println("delete file  "+fileName);
		}
	}

	
	
}
