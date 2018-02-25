package com.test;

public class C{
	public static void main(String[] args) {
		C1 c = new C1();
		c.start();
	}
}
 class C1 extends Thread {
	
	int i = 0;	
	public void run() {
		for(int i=1;i>0;i--) {
			System.out.println("2线程");
		}
		
	}
}
