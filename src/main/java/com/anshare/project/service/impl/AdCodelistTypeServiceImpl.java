package com.anshare.project.service.impl;

import com.anshare.project.core.AbstractService;
import com.anshare.project.dao.AdCodelistTypeMapper;
import com.anshare.project.model.AdCodelistType;
import com.anshare.project.service.AdCodelistTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Anshare on 2018/11/22.
 */
@Service
@Transactional
public class AdCodelistTypeServiceImpl extends AbstractService<AdCodelistType> implements AdCodelistTypeService {
    @Resource
    private AdCodelistTypeMapper adCodelistTypeMapper;

}
