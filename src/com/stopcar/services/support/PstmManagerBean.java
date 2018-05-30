package com.stopcar.services.support;

import java.sql.PreparedStatement;

import com.stopcar.system.db.DBUtils;



final class PstmManagerBean 
{
	private PreparedStatement pstm = null;
	private boolean isBatch = false;
	/**
	 * 
	 * @param pstm   ------需要通过事务执行的语句对象
	 * @param isBatch  ----更新类别true---executeBatch；flash---executeUpdate
	 */
	public PstmManagerBean(PreparedStatement pstm,boolean isBatch)
	{
		this.pstm = pstm;
		this.isBatch = isBatch;
	}
	/**
	 * 执行语句对象
	 * @param pstm
	 * @param isBatch
	 */
	public void executePreparedStatement()throws Exception
	{
		if(this.isBatch)
		{
			this.pstm.executeBatch();
		}
		else
		{
			this.pstm.executeUpdate();
		}
	}
	public void close()
	{
		DBUtils.close(this.pstm);
	}

}
