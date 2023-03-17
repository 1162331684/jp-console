/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.app.domain;

import java.io.Serializable;
import java.util.List;

import com.jeeplus.qc.domain.QcTrfItemSpecimen;
import com.jeeplus.qc.domain.QcTrfItemSpecimenTesting;
import com.jeeplus.qc.service.dto.QcTrfItemSpecimenTestingDTO;

import lombok.Data;
/**
 * TRFEntity
 * @author Lewis
 * @version 2021-11-18
 */
@Data
public class QcAppTrfData implements Serializable  {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<QcTrfItemSpecimenTesting> qcTrfItemSpecimenTestings ;
	
	private List<QcTrfItemSpecimen> qcTrfItemSpecimens ;

}
