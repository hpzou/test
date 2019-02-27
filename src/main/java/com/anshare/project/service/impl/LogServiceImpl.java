package com.anshare.project.service.impl;

import com.anshare.project.core.AbstractService;
import com.anshare.project.dao.LogMapper;
import com.anshare.project.model.Log;
import com.anshare.project.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Anshare on 2018/12/04.
 */
@Service
@Transactional
public class LogServiceImpl extends AbstractService<Log> implements LogService {
    @Resource
    private LogMapper logMapper;

}
