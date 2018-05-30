package com.stopcar.system.tools;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.mywq.util.LabelValueBean;

import com.stopcar.system.db.DBUtils;

public class Tools {
	/**
	 * 构造函数，构造器，构造子，如果函数是静态的 这里的构造子就用做私有的
	 */
	private Tools() {};
	//******************BEGIN MD5加密********************
	
		public static final String initPwd="27d17aee7b69911d6946014e337932db";
		
		
		/**
		 * md5加密
		 * @param pwd
		 * @return
		 * @throws Exception
		 */
		public static String getMd5Code(Object pwd)throws Exception
		{
			//获取md5第一次加密的密文
			String md5pwd1=Tools.MD5Encode(pwd.toString());
			//System.out.println("一次密文:"+md5pwd1);
			//编写混淆明文
			String pwd2=md5pwd1+"朱龙㈢㈩Ⅶ昊以させαωηιゅほ無つくぉ法為ㄍㄐㄛㄜνμβㄝㄆㄓㄚ有法朱かねむめ龍昊"+md5pwd1;
			//二次混淆加密
			String md5pwd2=Tools.MD5Encode(pwd2);
			//System.out.println("二次密文:"+md5pwd2);
			return md5pwd2;
		}
		
		
		 private final static String[] hexDigits = {
		     "0", "1", "2", "3", "4", "5", "6", "7",
		     "8", "9", "a", "b", "c", "d", "e", "f"};

		  /**
		   * 转换字节数组为16进制字串
		   * @param b 字节数组
		   * @return 16进制字串
		   */
		  private static String byteArrayToHexString(byte[] b)
		  {
		      StringBuffer resultSb = new StringBuffer();
		      for (int i = 0; i < b.length; i++)
		      {
		         resultSb.append(byteToHexString(b[i]));
		      }
		      return resultSb.toString();
		  }
		  /**
		   * 转换字节为16进制字符串
		   * @param b byte
		   * @return String
		   */
		  private static String byteToHexString(byte b)
		  {
		      int n = b;
		      if (n < 0)
		         {
		    	  n = 256 + n;
		         }
		      int d1 = n / 16;
		      int d2 = n % 16;
		      return hexDigits[d1] + hexDigits[d2];
		  }
		  /**
		   * 得到MD5的秘文密码
		   * @param origin String
		   * @throws Exception
		   * @return String
		   */
		  public static String MD5Encode(String origin) throws Exception
		  {
		       String resultString = null;
		       try
		       {
		           resultString=new String(origin);
		           MessageDigest md = MessageDigest.getInstance("MD5");
		           resultString=byteArrayToHexString(md.digest(resultString.getBytes()));
		           return resultString;
		       }
		       catch (Exception ex)
		       {
		          throw ex;
		       }
		  }
		//******************END  MD5******************
		
	
	private final static int MATCH_SCALE=2;         //四舍五入默认小数位数
	/**
	 * 以下四个方法为精度转换方法
	 * @param dol double
	 * @param scale int
	 * @return String
	 */
	public static double ObjToDouble(Object dol, int scale)
	{
		  return Tools.ObjectToBigDecimal(dol, scale).doubleValue();
	}
	public static double ObjToDouble(Object dol)
	{
	   return Tools.ObjToDouble(dol, MATCH_SCALE);	
	}
	
	public static String DoubleToStr(double dol, int scale)
	{
	    return Tools.ObjectToBigDecimal(dol, scale).toString();
	}
	public static String DoubleToStr(double dol)
	{
	    return Tools.DoubleToStr(dol, MATCH_SCALE);
	}

	public static double DoubleToDouble(double dol, int scale)
	{
	    return Tools.ObjectToBigDecimal(dol, scale).doubleValue();
	}
	public static double DoubleToDouble(double dol)
	{
	    return Tools.DoubleToDouble(dol,  MATCH_SCALE);
	}

	public static double StrToDouble(String dol, int scale)
	{
	    return Tools.ObjectToBigDecimal(dol, scale).doubleValue();
	}
	public static double StrToDouble(String dol)
	{
	    return Tools.StrToDouble(dol, MATCH_SCALE);
	}
	
    public static String StrToStr(String dol, int scale)
	{
	   return Tools.ObjectToBigDecimal(dol, scale).toString();
	}
	public static String StrToStr(String dol)
	{
	  return Tools.StrToStr(dol,MATCH_SCALE);
	}

	/**
	 * 四舍五入基础方法
	 * @param dol    ---  数值
	 * @param scale  ---  小数位数(精度)
	 * @return
	 */
	private static BigDecimal ObjectToBigDecimal(Object dol,int scale)
	{
		//定义货币类型变量
		BigDecimal decimal=null;
		//判断dol如果是空或null,返回0
		if(dol==null || dol.equals(""))
		{
			return new BigDecimal(0);
		}
		//基于dol完成BigDecimal的实例化
		decimal = new BigDecimal(dol.toString());
		//按照精度四舍五入
		decimal = decimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return decimal;
	}

	/**
	 * 获取主键流水号
	 * @param sname
	 * @return
	 * @throws Exception
	 */
	public static int getSequenceForPk(final String sname)throws Exception
	{
		//定义JDBC接口
		PreparedStatement pstm1=null;   //读取序列当前值
		PreparedStatement pstm2=null;   //更新序列
		ResultSet rs=null;
		try
		{
			//读取序列当前值
			StringBuilder sql1=new StringBuilder()
			.append("select a.svalue")
			.append("  from sequence a")
			.append(" where a.sname=?")	
			;
			pstm1=DBUtils.preparedStatement(sql1.toString());
			pstm1.setObject(1, sname);
			rs=pstm1.executeQuery();
			
			//定义序列返回值
			int currVal=0;
			
			StringBuilder sql2=new StringBuilder();
			
			//判断是否存在序列当前值
			if(rs.next())
			{
				currVal=rs.getInt(1);
				
				//更新序列
				sql2.append("update sequence")
				    .append("  set svalue=?")
				    .append(" where sname=?")
				;
			}
			else
			{
				sql2.append("insert into sequence(svalue,sname,sdate)")
				    .append("            values(?,?,current_date)");		
			}	
			pstm2=DBUtils.preparedStatement(sql2.toString());
			pstm2.setObject(1, ++currVal);
			pstm2.setObject(2, sname);
			pstm2.executeUpdate();
			
			return currVal;
			
		}
		finally
		{
			DBUtils.close(rs);
			DBUtils.close(pstm1);
			DBUtils.close(pstm2);
		}
	}
	/**
	 * 获取序列流水号
	 * @param sname
	 * @return
	 * @throws Exception
	 */
	public static String getSequence(String sname)throws Exception
	{
		//1、定义JDBC接口
		PreparedStatement pstm1 = null; //用于读取当前序列
		PreparedStatement pstm2 = null; //更新序列当前值
		ResultSet rs = null;
		try
		{
			//定义SQL，读取序列当前值
			StringBuilder sql = new StringBuilder()
			.append("select a.svalue") 
			.append("  from sequence a")
			.append(" where a.sname=?")
			.append("   and a.sdate=current_date")		
			;
			//3、编译sql语句
			pstm1=DBUtils.preparedStatement(sql.toString());
			pstm1.setObject(1, sname);
			//4.执行SQL语句
			rs = pstm1.executeQuery();
			//5.定义序列当前值得变量
			int current_val = 0;
			//定义更新序列的sql语句
			StringBuilder sql2 = new StringBuilder();
			//判断当前序列是否存在当前值
			if(rs.next())
			{
				//更新该序列的当前值
				current_val=rs.getInt(1);
				//定义更新语句
				sql2.append("update sequence")
					.append("   set svalue=?")
					.append(" where sdate=current_date")
					.append("   and sname=?")		
				;
			}
			else
			{
				//录入序列数据,并且保证当前值是1
				//定义SQL语句,完成新序列的录入
				sql2.append("insert into sequence(svalue,sname,sdate)")
				.append("            values(?,?,current_date)")		
				;
			}
			pstm2=DBUtils.preparedStatement(sql2.toString());
			pstm2.setObject(1, ++current_val);
			pstm2.setObject(2, sname);
			pstm2.executeUpdate();
			
			
			//定义补位码的基础码
			String baseCode="0000";
			//计算基础码中补位码的截取开始下标
			int beginIndex=String.valueOf(current_val).length();
			//获取补位码
			String bcode=baseCode.substring(beginIndex);
			 return null;			
				
		}
		finally
		{
			DBUtils.close(rs);
			DBUtils.close(pstm1);
			DBUtils.close(pstm2);
		}
	}
	/**
	 * 获取下拉列表
	 * @param fname
	 * @return
	 * @throws Exception
	 */
	public static List<LabelValueBean> getOptions(String fname)throws Exception
	{
		//1.定义JDBC接口
		PreparedStatement pstm=null;
		ResultSet rs=null;
		try
		{
			//3.定义SQL语句
			StringBuilder sql=new StringBuilder()
			.append("select fvalue,fcode") 
			.append("  from syscode") 
			.append(" where fname=?")		
			;
			//4.编译SQL语句
			pstm=DBUtils.preparedStatement(sql.toString());
			pstm.setObject(1, fname);
			
			//5.执行SQL
			rs=pstm.executeQuery();
			//6.定义装载查询结果的List
			List<LabelValueBean> opts=new ArrayList<>();
			//7.定义LabelValueBean 变量,装载当前行数据
			LabelValueBean bean=null;
			//8.循环解析rs的数据
			while(rs.next())
			{
				//9.实例化LabelValueBean
				bean=new LabelValueBean(rs.getString(1),rs.getString(2));
				//10.放入List
				opts.add(bean);
			}
			return opts;
		}
		finally
		{
			DBUtils.close(rs);
			DBUtils.close(pstm);
		}
	}
	/**
	 * 转换数组
	 * @param val
	 * @return
	 */
	public static String[] caseArray(Object val)
	{
		if(val==null||val.equals(""))
		{
			return new String[] {};//这里是如果Object是个空的或者是空的字符，就输出一个字符串数组，这里定义的方式是匿名的
		}
		if(val instanceof java.lang.String[])//这里是判断val是否是String[]
		{
			return (String[])val;
		}
		else
		{
			return new String[] {val.toString()};
		}
	}
	public static String jojinArray(Object val)
	{
		//空值拦截
		if(val==null||val.equals(""))
		{
			return "";
		}
		if(val instanceof java.lang.String[])
		{
			//将接收到的对象转换成字符串
			String tem[] = (String[])val;
			//计算数组长度
			int size=tem.length;
			//创建字符串容器，填充数组首元素
			StringBuilder text = new StringBuilder(tem[0]);
			//将其余的元素拼接成字符串
			for(int i=1;i<size;i++)
			{
				text.append(",").append(tem[i]);
			}
			//返回
			return text.toString();
		}
		else
		{
			return val.toString();
		}
	}
	/**
	 * 将字符串转换成字符串数组
	 * @param result
	 * @return
	 */
	public static String[] StringToArray(String result)
	{
		String[] string = result.split("#");
		return string;
	}

}
			
