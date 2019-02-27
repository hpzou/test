package com.anshare.project.service.impl;

import com.anshare.project.core.AbstractService;
import com.anshare.project.dao.AdCodelistMapper;
import com.anshare.project.model.AdCodelist;
import com.anshare.project.service.AdCodelistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by Anshare on 2018/11/01.
 */
@Service
@Transactional
public class AdCodelistServiceImpl extends AbstractService<AdCodelist> implements AdCodelistService {
    @Resource
    private AdCodelistMapper adCodelistMapper;

}
