package com.anshare.project.service.impl;

import com.anshare.project.core.AbstractService;
import com.anshare.project.dao.DynamictablesMapper;
import com.anshare.project.model.Dynamictables;
import com.anshare.project.service.DynamictablesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Anshare on 2018/11/06.
 */
@Service
@Transactional
public class DynamictablesServiceImpl extends AbstractService<Dynamictables> implements DynamictablesService {
    @Resource
    private DynamictablesMapper dynamictablesMapper;

}
