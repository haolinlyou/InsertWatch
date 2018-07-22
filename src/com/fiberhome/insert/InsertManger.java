package com.fiberhome.insert;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class InsertManger {
	
	public static LinkedBlockingDeque<Set<String>> userDeque = new LinkedBlockingDeque<Set<String>>();
	
	public ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
	
	
	public void startService(int threadNum) {
		fixedThreadPool.execute(new InsertWatch());
		
		for (int i=0; i<threadNum; i++) {
			fixedThreadPool.execute(new InsertService());
		}
		

	}
	
	
	public static Set<String> getUserDeque() {
		
		return userDeque.poll();
	}
	
	
	
	public static void  putUserDeque(Set<String> fileNames) {
		
		Set<String> tempUserInfo = new HashSet();
		
		for (String fileName: fileNames) {
			tempUserInfo.add(fileName);
		}
		System.out.println("put file "+fileNames);
		userDeque.add(tempUserInfo);
		System.out.println("usrDeque size is  "+userDeque.size());
	}
	
	
	public static void main(String[] args) {
		
		
		InsertManger manager = new InsertManger();
		manager.startService(4);
		
	}
	

}
