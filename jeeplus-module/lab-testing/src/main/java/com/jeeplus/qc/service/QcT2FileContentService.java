/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.qc.domain.QcT2FileContent;
import com.jeeplus.qc.mapper.QcT2FileContentMapper;

/**
 * 文件内容Service
 * @author Lewis
 * @version 2022-01-04
 */
@Service
@Transactional
public class QcT2FileContentService extends ServiceImpl<QcT2FileContentMapper, QcT2FileContent> {
    
}
