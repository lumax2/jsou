package jsoup;

public class SingleTonDemo {
	
	


	
	
}
//1 饿汉单例高并发条件下 线程安全的 单例模式
class Singleton{
	private static Singleton a=new Singleton();
	private Singleton(){};
	public static Singleton getInstance(){
		return a;
	}
}

//2懒汉单例模式
class Singleton1{
	private static Singleton1 b =null;
	private Singleton1(){};
	public static Singleton1 getInstance(){
		if(b==null){
			b=new Singleton1();
		}
		return b;
	}
}

//3通过静态内部类 实现懒汉单例模式 高并发条件
class Singleton2{
	
	private Singleton2(){};
	private static class inner{
		static Singleton2 getInstance(){
			return new Singleton2();
		}
	}
	public Singleton2 getInstance(){
		return inner.getInstance();
	}
	
}
//4双锁 懒汉 单例模式
class Singleton3{
	private static Singleton3 c=null;
	private Singleton3(){};
	public static Singleton3 getInstance(){
		if(c==null){
			synchronized(Singleton3.class){
				if(c==null){
					c=new Singleton3();
				}
			}
		}
		return c;
	}
}









