package com.anshare.project.service;
import com.anshare.project.model.Affix;
import com.anshare.project.core.Service;
import com.anshare.project.model.SqlModel;

import java.util.List;


/**
 * Created by Anshare on 2018/10/05.
 */
public interface AffixService extends Service<Affix> {
    List<Affix> list(SqlModel sqlModel);//获取所有，包含分页
}
