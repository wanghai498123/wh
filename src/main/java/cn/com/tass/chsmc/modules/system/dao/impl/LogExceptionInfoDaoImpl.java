package cn.com.tass.chsmc.modules.system.dao.impl;



import org.springframework.stereotype.Component;
import cn.com.tass.chsmc.modules.common.dao.impl.GenericDaoImpl;
import cn.com.tass.chsmc.modules.system.dao.LogExceptionInfoDao;
import cn.com.tass.chsmc.modules.system.model.LogExceptionInfo;



@Component("logExceptionInfoDao")
public class LogExceptionInfoDaoImpl extends GenericDaoImpl<LogExceptionInfo> implements LogExceptionInfoDao {

}
