package com.anshare.project.core;

import com.anshare.project.model.SqlModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

/**
 * 定制版MyBatis Mapper插件接口，如需其他接口参考官方文档自行添加。
 */
public interface Mapper<T>
        extends
        BaseMapper<T>,
        ConditionMapper<T>,
        IdsMapper<T>,
        InsertListMapper<T> {

    //List<T> list(@Param("sql") String sql);

    List<T> resumeList(@Param("id")String id);

    List<T> findListByIds(@Param("ids") String ids);

    List<T> listPlus(SqlModel sqlModel);
}
