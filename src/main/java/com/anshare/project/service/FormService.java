package com.anshare.project.service;
import com.anshare.project.model.Form;
import com.anshare.project.core.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by Anshare on 2018/11/01.
 */
public interface FormService extends Service<Form> {
    List<Map<String,Object>> getTables();

}
