package jsoup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class TestConnection {
	
	
//	@Test	//传统jdbc直接访问，查询
	public void jdbcFind() throws SQLException{
		//从spring容器中获取DataSource
		String conf = "spring/applicationContext.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(conf);
		//1.获取数据源对象
		DataSource ds = (DataSource)context.getBean("dataSource");
		//2.获取链接
		Connection cn = ds.getConnection();
		//3.执行SQL语句
		String sql = "select * from spidder";
		//4.statement
		PreparedStatement ps = cn.prepareStatement(sql);
		//5.执行sql语句
		ResultSet rs = ps.executeQuery();
		//6.遍历打印每条结果
		while(rs.next()){
			System.out.println("name:"+rs.getString(1));
			System.out.println("title:"+rs.getInt("title"));
		}
		//7.释放对象
		try{
			if(rs!=null){
				rs.close();
			}
		}catch(Exception e){
			
		}finally{
			rs.close();
		}
		ps.close();
		cn.close();
	}
}
