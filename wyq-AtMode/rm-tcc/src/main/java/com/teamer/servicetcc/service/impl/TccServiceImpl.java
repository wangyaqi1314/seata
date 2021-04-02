package com.teamer.servicetcc.service.impl;

import com.teamer.servicetcc.dao.TccDAO;
import com.teamer.servicetcc.service.TccService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class TccServiceImpl implements  TccService {

    @Autowired
    TccDAO tccDAO;

    /**
     * tcc服务t（try）方法
     * 根据实际业务场景选择实际业务执行逻辑或者资源预留逻辑
     * @param params - name
     * @return String
     */
    @Override
    public String insert(Map<String, String> params) {
        log.info("xid = " + RootContext.getXID());
        tccDAO.insert(params);
        //放开以下注解抛出异常
//        throw new RuntimeException("服务tcc测试回滚");
        return "success";
    }

    /**
     * tcc服务 confirm方法
     * 若一阶段采用资源预留，在二阶段确认时要提交预留的资源
     *
     * @param context 上下文
     * @return boolean
     */
    @Override
    public boolean commitTcc(BusinessActionContext context) {
        String xid = context.getXid();
        String actionOneResult = ResultHolder.getActionOneResult(xid);
        if(!StringUtils.isBlank(actionOneResult)){
            System.out.println("当前XID:"+xid+"已执行过！");
        }
        log.info("xid = " + xid + "提交成功");
        //.....
        ResultHolder.setActionOneResult("xid","T");
        return true;
    }

    /**
     * tcc 服务 cancel方法
     *
     * @param context 上下文
     * @return boolean
     */
    @Override
    public boolean cancel(BusinessActionContext context) {
        String xid = context.getXid();
        //允许空回滚
        if (StringUtils.isBlank(xid)){
            System.out.println("允许空回滚");
            return true;
        }
        //幂等性 判断是否执行过
        String actionOneResult = ResultHolder.getActionOneResult(xid);
        if(!StringUtils.isBlank(actionOneResult)){
            System.out.println("当前XID:"+xid+"已执行过！");
        }
        System.out.println("please manually rollback this data:" + context.getActionContext("params"));
        Map<String, String> params = (Map<String, String>)context.getActionContext("params");
        String name = params.get("name");
        tccDAO.delete(name);
        System.out.println("delete data name :" + name+" success!");
        ResultHolder.setActionOneResult("xid","F");
        return true;
    }
}
