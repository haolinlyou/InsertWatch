
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class Write_File implements Runnable {
	
	

        FileOutputStream out = null;
        FileOutputStream outSTr = null;
        BufferedOutputStream Buff = null;
        FileWriter fw = null;
        String path = "f:\\tmp\\";

        int count = 1000;//写文件行数
        int index = 0;
        
        
        @Override
		public void run() {
        	 while(true) {
            	try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
             	try {
                     
                     //经过测试：ufferedOutputStream执行耗时:1,1，1 毫秒
                     outSTr = new FileOutputStream(new File(path+index*10+".txt"));
                     Buff = new BufferedOutputStream(outSTr);
                     long begin0 = System.currentTimeMillis();
                     for (int i = 0; i < count; i++) {
                         Buff.write("测试java 文件操作\r\n".getBytes());
                     }
                     Buff.flush();
                     Buff.close();
                     long end0 = System.currentTimeMillis();
                     System.out.println("BufferedOutputStream执行耗时:" + (end0 - begin0) + " 毫秒");
                     index += 1;
                     if (index > 100) {
                    	 index = 0;
                     }

                 } catch (Exception e) {
                     e.printStackTrace();
                 } finally {
                     try {
                       
                         Buff.close();
                         outSTr.close();
                         
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }

            	
            }

        	
        }
        
        public static void main(String[] args) {
			Thread write = new Thread(new Write_File());
			
			write.start();
		}
       
      

}
