package com.teamer.serviceat.service.impl;

import com.teamer.serviceat.dao.AtDAO;
import com.teamer.serviceat.service.AtService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class AtServiceImpl implements AtService {

    @Autowired
    AtDAO atDAO;

    @Override
    public String insert(Map<String, String> params) {
        log.info("------------------> xid = " + RootContext.getXID());
        atDAO.insert(params);

        atDAO.insert(params);
        return "success";
    }
}
