package com.yhb.service.base;

import java.util.List;

/**
 * Created by huangbin on 2017/5/5 0005.
 */
public interface CrudService {


    Object save(Object object);

    void delete(List idList);

    Object findById(Long id);

    List findAll();


}
