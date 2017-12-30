package jsoup;

import org.junit.Test;

public class TestThread extends Thread{

	public void run(){
		System.out.println("In run");
		yield();
		System.out.println("Leaving run");
	}
	
	public static void main(String[] args) {
		(new TestThread()).start();
		
	}
	@Test
	public void t(){
		int a='a';
		System.out.println(a);
	}
}
