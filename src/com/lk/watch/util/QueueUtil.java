package com.lk.watch.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 阻塞队列实现类
 * @author lk
 *
 */
public class QueueUtil {
	
	
	/**
	 * 阻塞队列
	 */
	public static LinkedBlockingDeque<Collection<?>> userDeque = new LinkedBlockingDeque<Collection<?>>();
	
	
	public static Collection<?> getUserDeque() throws InterruptedException {
		
		return userDeque.take();
	}
	
	
	@SuppressWarnings("unchecked")
	public static <E> void  putUserDeque(Collection<?> elements, int batchNum) throws InstantiationException, IllegalAccessException {
		
		Iterator<E> iterator = (Iterator<E>) elements.iterator();
		
		Collection<E> userBatchDeque = elements.getClass().newInstance();
		
		while (iterator.hasNext()) {
			userBatchDeque.add(iterator.next());
			
			if (userBatchDeque.size() == batchNum) {
				userDeque.add(userBatchDeque);
				userBatchDeque = elements.getClass().newInstance();
			}
		}
		
		if (userBatchDeque.size() > 0) {
			userDeque.add(userBatchDeque);
		}
		
		
		
	}
	
	public static void  putUserDeque(Collection<?> elements) {
		
		userDeque.add(elements);
		
	}
	
	public static LinkedBlockingDeque<Collection<?>> getBlockingDeque() {
		
		return userDeque;
	}
	
	
	

}
