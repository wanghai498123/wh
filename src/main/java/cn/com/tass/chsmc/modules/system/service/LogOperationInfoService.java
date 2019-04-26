package cn.com.tass.chsmc.modules.system.service;



import org.springframework.transaction.annotation.Transactional;
import cn.com.tass.chsmc.modules.common.service.GenericService;
import cn.com.tass.chsmc.modules.system.model.LogOperationInfo;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

@Transactional
public interface LogOperationInfoService extends GenericService<LogOperationInfo>{
	Page<LogOperationInfo> listLogOperationInfo(DtPageable dtPageable) throws Exception;
	
}

