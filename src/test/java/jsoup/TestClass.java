package jsoup;

public class TestClass {
	
	public void myMethod(Object obj){
		System.out.println("MyObject");
	}
	public void myMethod(String str){
		System.out.println("MyString");
	}
	

	public static void main(String[] args) {
		TestClass t=new TestClass();
		t.myMethod(null);
	}
}
