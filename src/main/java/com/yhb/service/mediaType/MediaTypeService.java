package com.yhb.service.mediaType;

import com.yhb.dao.mediaType.MediaTypeRepository;
import com.yhb.domain.mediaType.MediaType;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 媒体类型业务类
 */
@Service
public class MediaTypeService extends BaseService {

    @Autowired
    MediaTypeRepository mediaTypeRepository;

    /**
     * @param mediaType
     * @return 保存媒体类型
     */
    public MediaType save(MediaType mediaType) {
        return mediaTypeRepository.save(mediaType);
    }


}
