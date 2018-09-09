package com.lk.watch;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class FileWriter implements Runnable {
	
	

    private String path = "f:\\tmp\\";
    
    public FileWriter(String path) {
		super();
		this.path = path;
	}
    
    

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		int index = 0;
    	 while(true) {
        	try {
				String fileName = this.path+index+".txt";
				FileUtils.write(new File(fileName), "1234");
				System.out.println("create file:"+fileName);
        	} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	index++;
    	 }
    }
	
	public static void main(String[] args) {
		Thread t = new Thread(new FileWriter("f:\\tmp\\"));
		t.start();
	}
      

}
