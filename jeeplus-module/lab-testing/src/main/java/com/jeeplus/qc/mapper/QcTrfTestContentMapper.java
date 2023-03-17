/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.qc.service.dto.QcTrfTestContentDTO;
import com.jeeplus.qc.domain.QcTrfTestContent;

/**
 * 测试项条件配置表MAPPER接口
 * @author max teng
 * @version 2022-11-01
 */
public interface QcTrfTestContentMapper extends BaseMapper<QcTrfTestContent> {

    /**
     * 根据id获取测试项条件配置表
     * @param id
     * @return
     */
    QcTrfTestContentDTO findById(String id);

    /**
     * 获取测试项条件配置表列表
     *
     * @param QcTrfTestCodeId
     * @return
     */
    List <QcTrfTestContentDTO> findList(String QcTrfTestCodeId);

}
