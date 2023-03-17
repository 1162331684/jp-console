/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.bom.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * BomEntity
 * @author Young
 * @version 2022-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("exception_bom")
public class ExceptionBom extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
     * Working No
     */
	private String workingNo;
	/**
     * Brand
     */
	private String brand;
	/**
     * Sports Cat
     */
	private String sportsCat;
	/**
     * Product Name
     */
	private String productName;
	/**
     * Dev Region
     */
	private String devRegion;
	/**
     * Developer
     */
	private String developer;
	/**
     * Direct Development
     */
	private String directDevelopment;
	/**
     * Style Status
     */
	private String styleStatus;
	/**
     * Style BR Status
     */
	private String styleBrStatus;
	/**
     * Colorway Name
     */
	private String colorwayName;
	/**
     * Article
     */
	private String article;
	/**
     * Earliest Buy Ready
     */
	private String earliestBuyReady;
	/**
     * Colorway Status
     */
	private String colorwayStatus;
	/**
     * Fty Code
     */
	private String ftyCode;
	/**
     * Fty Name
     */
	private String ftyName;
	/**
     * Lo For Fty
     */
	private String loForFty;
	/**
     * Fty Priority
     */
	private String ftyPriority;
	/**
     * Ref
     */
	private String ref;
	/**
     * Typename
     */
	private String typename;
	/**
     * Material
     */
	private String material;
	/**
     * Group Code
     */
	private String groupCode;
	/**
     * Short Name
     */
	private String shortName;
	/**
     * COO
     */
	private String coo;
	/**
     * LO
     */
	private String lo;
	/**
     * Supp Ref
     */
	private String suppRef;
	/**
     * Part No
     */
	private String partNo;
	/**
     * Part Name
     */
	private String partName;
	/**
     * sustainability
     */
	private String sustainability;
	/**
     * Level
     */
	private String level;
	/**
     * Yield
     */
	private String yield;
	/**
     * Supp UOM
     */
	private String suppUom;
	public ExceptionBom() {

	}
	public ExceptionBom(String id) {
		super(id);
	}
}
