package jsoup;

import org.junit.Test;

public class TestInstanceof {

	public static void main(String[] args) {
		
		Person p =new Person();
		Teacher t =new Teacher();
		Student s =new Student();
		
		System.out.println();
		if(t instanceof Person){
			//s =(Student)t;
		}
	}
}
class Person{
	
	public void example(){};
	public int example(int m,float f){
		return m;
		
	};
	
}
class Teacher extends Person{
	@Test
	public void t(){
		int a='a';
		System.out.println(a);
	}
}
class Student extends Person{
	
}