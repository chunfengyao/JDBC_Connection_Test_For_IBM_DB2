import java.sql.*;
import java.util.Scanner;
import java.io.Console;


interface JDBCInterface
{
	String JDBC_DRIVER = "com.ibm.db2.jcc.DB2Driver";
	int port = 60000;
	String ip = "127.0.0.1";
	String dbname = "SAMPLE";
	void connection();
	void TestDriver();
}

interface UserInfo
{
	String Username = "db2inst1";
	String Userpasswd = "db2inst1";
	void Userconn();
}

class db2conn implements JDBCInterface,UserInfo
{
	int port;
	String ip;
	String dbname;
	String Username;
	String Userpasswd;
	public void TestDriver()
	{
		try
		{
			Class.forName (JDBC_DRIVER);
		}
		catch (Exception e)
		{
			System.out.println("\t无法加载该驱动:com.ibm.db2.jcc.DB2Driver\n\t请检查您的JDK版本或手动导入对应驱动包!!!");
			System.exit(0);
		}
	}
	public void connection()
	{
		System.out.println("该测试程序仅适用于DB2数据库。\n请按顺序依次输入如下内容，并按回车键确认：\n1、数据库的可ping通的IP地址");
		System.out.println("2、数据库JDBC访问端口");
		System.out.println("3、待连接的数据库库名 (可通过如下命令查询：db2 list database directory) ");
		Scanner sc = new Scanner(System.in);
		System.out.print("IP地址：");
		this.ip = sc.next();
		System.out.print("JDBC访问端口：");
		this.port = sc.nextInt();
		System.out.print("数据库库名：");
		this.dbname = sc.next();
	}
	public void Userconn()
	{
		System.out.println("\n请按顺序依次输入如下内容的连接用户信息，并按回车键确认：");
		System.out.println("1、监控用户用户名");
		System.out.println("1、监控用户的密码 (无回显)!!!!!!");
		Scanner sc = new Scanner(System.in);
		System.out.print("用户名：");
		this.Username = sc.next();
		Console console = System.console();
		System.out.print("密码 (无回显)：");
		Userpasswd = new String(console.readPassword());
	}
	public String GetUrl()
	{
		return ("jdbc:db2://"+ip+":"+port+"/"+dbname);
	}
	public String GetUsername()
	{
		return Username;
	}
	public void connect()
	{
		Connection conn = null;
		Statement stmt = null;
		System.out.println("\t正在通过JDBC连接至目标数据库......"+"\n\tJDBC驱动名称: "+JDBC_DRIVER);
		System.out.println("\t该JDBC连接所请求的URL为："+GetUrl());
		System.out.println("\t执行该JDBC连接的用户为："+GetUsername());
			try
			{
				conn = DriverManager.getConnection (GetUrl(),Username,Userpasswd);
				System.out.println("\t\t.........\n\t\t.........\n\t\t.........\n\t已成功通过JDBC连接到目标数据库......");
			}
			catch (SQLException e)
			{
				System.out.println("\n\tERROR！！！:未能成功通过JDBC连接到目标数据库！！！");
				System.out.println("\n\t以下是错误代码：\n\n\n"+e.getErrorCode( ));
				System.out.println("--------------------");
				System.out.println("\t以下是详细的错误信息：\n\n\n"+e.getMessage());
				System.out.println("--------------------");
				e.printStackTrace( );
			}
			finally 
			{
				if (conn != null) 
				try 
				{
					conn.close();
					System.out.println("\t\t.........\n\t\t.........\n\t\t.........\n\t已成功关闭测试过程中连接到目标数据库所使用的资源......");
				} 
				catch (SQLException e)
				{
					System.out.println("\n未能成功关闭测试过程中连接到目标数据库所使用的资源！！！\n以下是尝试关闭资源时的错误详细信息\n");
					e.printStackTrace();
					System.exit(0);
				}
			}
	}
}
final class JDBC_Connection_Test_For_DB2
{
	public static void main(String[] args)
	{
		JDBCInterface conn = new db2conn();
		conn.TestDriver();
		conn.connection();
		db2conn conn1=(db2conn)conn;
		conn1.Userconn();
		conn1.connect();
	}
}
