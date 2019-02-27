package com.anshare.project.dao;

import com.anshare.project.core.Mapper;
import com.anshare.project.model.Affix;
import com.anshare.project.model.SqlModel;

import java.util.List;

public interface AffixMapper extends Mapper<Affix> {
    List<Affix> list(SqlModel sqlModel);
}