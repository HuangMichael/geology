package com.yhb.utils.oneKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * Created by huangbin on 2017/9/30.
 */

@Service
public abstract class OneKeyDataService implements OneKeyData {

    protected Log log = LogFactory.getLog(this.getClass());

    @Override
    public void oneKeyImport() {
        log.info("oneKeyImport------------------" + this.getClass().getSimpleName());
    }

    @Override
    public void oneKeyExport() {
        log.info("oneKeyExport------------------" + this.getClass().getSimpleName());
    }
}
