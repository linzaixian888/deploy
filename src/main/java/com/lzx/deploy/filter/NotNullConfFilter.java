package com.lzx.deploy.filter;

public abstract class NotNullConfFilter extends CheckConfFilter{
	@Override
	public Result validate(String confName, Object value) throws Exception {
		if(value==null){
			return Result.stop;
		}
		return null;
	}

}
