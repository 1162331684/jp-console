/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.access.service.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import com.jeeplus.utils.excel.annotation.ExcelField;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Exception AccessDTO
 * @author Young
 * @version 2022-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExceptionListDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * comments
     */
	private String comments;
	/**
     * commments_times
     */
	private String commmentsTimes;
	/**
     * assignee
     */
	@Query(tableColumn = "a.assignee")
	private String assignee;
	@Query(tableColumn = ".userName")
	private String userName;
	/**
     * status
     */
	@Query(tableColumn = "a.status")
	private String status;
	/**
     * file_path
     */
	private String filePath;
	
	private String applicationNo;
	
	private String processInstanceId;
	/**
     * bom_id
     */
	private String bomId;

	/**
     * garment_QA_comments
     */
	private String garmentQaComments;
	/**
     * garment_QA_comment_time
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date garmentQaCommentTime;
	/**
     * material_QAs
     */
	private String materialQas;
	/**
     * material_QSs_time
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date materialQssTime;
	/**
     * mer_team
     */
	private String merTeam;
	/**
     * mer_team_time
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date merTeamTime;
	/**
     * meeting_record
     */
	private String meetingRecord;
	/**
     * meeting_record_time
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date meetingRecordTime;
	
	@Query(tableColumn = "b.working_no", javaField = "workingNo", type = QueryType.LIKE)
	private String workingNo;		// Working No
	
	private String brand;

	private String productName;		// Product Name

	private String devRegion;		// Dev Region

	private String developer;		// Developer

	private String directDevelopment;		// Direct Development

	private String styleStatus;		// Style Status

	private String styleBrStatus;		// Style BR Status

	private String colorwayName;		// Colorway Name
	@Query(tableColumn = "b.article", javaField = "article", type = QueryType.LIKE)

	private String article;		// Article

	private String earliestBuyReady;		// Earliest Buy Ready

	private String colorwayStatus;		// Colorway Status
	@Query(tableColumn = "b.fty_code", javaField = "ftyCode", type = QueryType.LIKE)
	private String ftyCode;		// Fty Code

	private String ftyName;		// Fty Name

	private String loForFty;		// Lo For Fty
	private String ftyPriority;		// Fty Priority
	@Query(tableColumn = "b.ref", javaField = "ref", type = QueryType.LIKE)
	private String ref;		// Ref

	private String typename;		// Typename

	private String material;		// Material

	private String groupCode;		// Group Code

	private String shortName;		// Short Name

	private String coo;		// COO

	private String lo;		// LO

	private String suppRef;		// Supp Ref

	private String partNo;		// Part No

	private String partName;		// Part Name

	private String sustainability;		// sustainability

	private String level;		// Level

	private String yield;		// Yield

	private String suppUom;		// Supp UOM

	//    info

	@Query(tableColumn = "c.lab_dip_status", javaField = "labDipStatus", type = QueryType.EQ)
	private String labDipStatus;		// LabDip Status

	private String labDipStatusDate;		// LabDip Status Date

	private String labDipRemark;		// Remark (LabDip)

	private String materialRemarks;		// Material Remarks

	private String materialDeveloper;		// Developer (Material)

	private String mtlSuppLifecycleState;		// Mtl-Supp Lifecycle State

	private String managementModel;		// Management Model

	private String developerLocation;		// Developer Location

	private String materialOrigGroup;		// Orig Group (Material)

	private String materialDescription;		// Description (Material)

	private String color;		// Color

	private String mainMaterial;		// Main Material

	private String fiberText;		// Fiber Text

	private String fiberTextPosition;		// Fiber Text Position

	private String layerComp;		// Layer 1 Comp

	private String supplierLeadTime;		// Lead Time (Days) (MaterialSupplier)

	private String loMerch;		// LO Merch

	private String materialDevType;		// Dev. Type (Material)

	private String materialTypename;		// Typename (Material)

	private String businessUnit;		// Business Unit (RMA)

	private String layerConst;		// Layer 1 Const

	private String firstColorway;		// 1st Colorway

	private String flexBomLinkid;		// FlexBOMLink ID

	private String sourceSeasonLeadTime;		// Lead Time (Days) (Source2SeasonLink)

	private String season;		// Season

	private String usRainwearStatus;		// US Rainwear Status

	private String productSpecialty;		// Product Specialty

	private String globalMktgFc;		// Mktg FC (Global)
	
	private String bomRemarks;		// bom_remarks

	private String firstSeason;		// First Season

	private String minQtyColor;		// Min Qty/Color

	private String minQtyOrder;		// Min Qty/Order

	private String supplierWidth;		// Width (MaterialSupplier)

	private String widthUom;		// Width Uo
	
	private List<String> ids;

}
