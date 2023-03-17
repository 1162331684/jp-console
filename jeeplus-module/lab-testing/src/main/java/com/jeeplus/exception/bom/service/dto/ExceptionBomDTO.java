/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.bom.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import com.jeeplus.utils.excel.annotation.ExcelField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * BomDTO
 * @author Young
 * @version 2022-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExceptionBomDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Query(tableColumn = "a.brand", javaField = "brand", type = QueryType.EQ)
	@ExcelField(title="Brand", align=2, sort=1)
	private String brand;		// Brand
	@ExcelField(title="Sports Cat", align=2, sort=2)
	private String sportsCat;		// Sports Cat
	@Query(tableColumn = "a.working_no", javaField = "workingNo", type = QueryType.EQ)
	@ExcelField(title="Working No", align=2, sort=3)
	private String workingNo;		// Working No
	@ExcelField(title="Product Name", align=2, sort=4)
	private String productName;		// Product Name
	@ExcelField(title="Dev Region", align=2, sort=5)
	private String devRegion;		// Dev Region
	@ExcelField(title="Developer", align=2, sort=6)
	private String developer;		// Developer
	@ExcelField(title="Direct Development", align=2, sort=7)
	private String directDevelopment;		// Direct Development
	@ExcelField(title="Style Status", align=2, sort=8)
	private String styleStatus;		// Style Status
	@ExcelField(title="Style BR Status", align=2, sort=9)
	private String styleBrStatus;		// Style BR Status
	@ExcelField(title="Colorway Name", align=2, sort=10)
	private String colorwayName;		// Colorway Name
	@Query(tableColumn = "a.article", javaField = "article", type = QueryType.EQ)
	@ExcelField(title="Article", align=2, sort=11)
	private String article;		// Article
	@ExcelField(title="Earliest Buy Ready", align=2, sort=12)
	private String earliestBuyReady;		// Earliest Buy Ready
	@ExcelField(title="Colorway Status", align=2, sort=13)
	private String colorwayStatus;		// Colorway Status
	@Query(tableColumn = "a.fty_code", javaField = "ftyCode", type = QueryType.EQ)
	@ExcelField(title="Fty Code", align=2, sort=14)
	private String ftyCode;		// Fty Code
	@ExcelField(title="Fty Name", align=2, sort=15)
	private String ftyName;		// Fty Name
	@ExcelField(title="Lo For Fty", align=2, sort=16)
	private String loForFty;		// Lo For Fty
	@ExcelField(title="Fty Priority", align=2, sort=17)
	private String ftyPriority;		// Fty Priority
	@Query(tableColumn = "a.ref", javaField = "ref", type = QueryType.LIKE)
	@ExcelField(title="Ref", align=2, sort=18)
	private String ref;		// Ref
	@ExcelField(title="Typename", align=2, sort=19)
	private String typename;		// Typename
	@ExcelField(title="Material", align=2, sort=20)
	private String material;		// Material
	@ExcelField(title="Group Code", align=2, sort=21)
	private String groupCode;		// Group Code
	@ExcelField(title="Short Name", align=2, sort=22)
	private String shortName;		// Short Name
	@ExcelField(title="COO", align=2, sort=23)
	private String coo;		// COO
	@ExcelField(title="LO", align=2, sort=24)
	private String lo;		// LO
	@ExcelField(title="Supp Ref", align=2, sort=25)
	private String suppRef;		// Supp Ref
	@ExcelField(title="Part No", align=2, sort=26)
	private String partNo;		// Part No
	@ExcelField(title="Part Name", align=2, sort=27)
	private String partName;		// Part Name
	@ExcelField(title="sustainability", align=2, sort=28)
	private String sustainability;		// sustainability
	@ExcelField(title="Level", align=2, sort=29)
	private String level;		// Level
	@ExcelField(title="Yield", align=2, sort=30)
	private String yield;		// Yield
	@ExcelField(title="Supp UOM", align=2, sort=31)
	private String suppUom;		// Supp UOM

	//    info
	private String bomId;		// bom_id
	@Query(tableColumn = "b.lab_dip_status", javaField = "labDipStatus", type = QueryType.EQ)
	@ExcelField(title="LabDip Status", align=2, sort=32)
	private String labDipStatus;		// LabDip Status
	@ExcelField(title="LabDip Status Date", align=2, sort=33)
	private String labDipStatusDate;		// LabDip Status Date
	@ExcelField(title="Remark (LabDip)", align=2, sort=34)
	private String labDipRemark;		// Remark (LabDip)
	@ExcelField(title="Material Remarks", align=2, sort=35)
	private String materialRemarks;		// Material Remarks
	@ExcelField(title="Developer (Material)", align=2, sort=36)
	private String materialDeveloper;		// Developer (Material)
	@Query(tableColumn = "b.mtl_supp_lifecycle_state", javaField = "mtlSuppLifecycleState", type = QueryType.EQ)
	@ExcelField(title="Mtl-Supp Lifecycle State", align=2, sort=37)
	private String mtlSuppLifecycleState;		// Mtl-Supp Lifecycle State
	@ExcelField(title="Management Model", align=2, sort=38)
	private String managementModel;		// Management Model
	@ExcelField(title="Developer Location", align=2, sort=39)
	private String developerLocation;		// Developer Location
	@ExcelField(title="Orig Group (Material)", align=2, sort=40)
	private String materialOrigGroup;		// Orig Group (Material)
	@ExcelField(title="Description (Material)", align=2, sort=41)
	private String materialDescription;		// Description (Material)
	@ExcelField(title="Color", align=2, sort=42)
	private String color;		// Color
	@ExcelField(title="Main Material", align=2, sort=43)
	private String mainMaterial;		// Main Material
	@ExcelField(title="Fiber Text", align=2, sort=44)
	private String fiberText;		// Fiber Text
	@ExcelField(title="Fiber Text Position", align=2, sort=45)
	private String fiberTextPosition;		// Fiber Text Position
	@ExcelField(title="Layer 1 Comp", align=2, sort=46)
	private String layerComp;		// Layer 1 Comp
	@ExcelField(title="Lead Time (Days) (MaterialSupplier)", align=2, sort=47)
	private String supplierLeadTime;		// Lead Time (Days) (MaterialSupplier)
	@ExcelField(title="LO Merch", align=2, sort=48)
	private String loMerch;		// LO Merch
	@ExcelField(title="Dev. Type (Material)", align=2, sort=49)
	private String materialDevType;		// Dev. Type (Material)
	@ExcelField(title="Typename (Material)", align=2, sort=50)
	private String materialTypename;		// Typename (Material)
	@ExcelField(title="Business Unit (RMA)", align=2, sort=51)
	private String businessUnit;		// Business Unit (RMA)
	@ExcelField(title="Layer 1 Const", align=2, sort=52)
	private String layerConst;		// Layer 1 Const
	@ExcelField(title="1st Colorway", align=2, sort=53)
	private String firstColorway;		// 1st Colorway
	@ExcelField(title="FlexBOMLink ID", align=2, sort=54)
	private String flexBomLinkid;		// FlexBOMLink ID
	@ExcelField(title="Lead Time (Days) (Source2SeasonLink)", align=2, sort=55)
	private String sourceSeasonLeadTime;		// Lead Time (Days) (Source2SeasonLink)
	@ExcelField(title="Season", align=2, sort=56)
	private String season;		// Season
	@ExcelField(title="US Rainwear Status", align=2, sort=57)
	private String usRainwearStatus;		// US Rainwear Status
	@ExcelField(title="Product Specialty", align=2, sort=58)
	private String productSpecialty;		// Product Specialty
	@ExcelField(title="Mktg FC (Global)", align=2, sort=59)
	private String globalMktgFc;		// Mktg FC (Global)
	@ExcelField(title="bom_remarks", align=2, sort=60)
	private String bomRemarks;		// bom_remarks
	@ExcelField(title="First Season", align=2, sort=61)
	private String firstSeason;		// First Season
	@ExcelField(title="Min Qty/Color", align=2, sort=62)
	private String minQtyColor;		// Min Qty/Color
	@ExcelField(title="Min Qty/Order", align=2, sort=63)
	private String minQtyOrder;		// Min Qty/Order
	@ExcelField(title="Width (MaterialSupplier)", align=2, sort=64)
	private String supplierWidth;		// Width (MaterialSupplier)
	@ExcelField(title="Width UoM", align=2, sort=65)
	private String widthUom;		// Width UoM
	@ExcelField(title="Supp. Ref.Name", align=2, sort=66)
	private String suppRefName;		// Supp. Ref.Name
	@ExcelField(title="Brand Partner", align=2, sort=67)
	private String brandPartner;		// Brand Partner
	@ExcelField(title="Leadtime Remark", align=2, sort=68)

//    Excel 文件中未存在的字段
	private String leadtimeRemark;		// Leadtime Remark
	@ExcelField(title="LCS Remark", align=2, sort=70)
	private String lcsRemark;		// LCS Remark
	@ExcelField(title="ts_comments", align=2, sort=71)
	private String tsComments;		// ts_comments
	@ExcelField(title="ts_commments_times", align=2, sort=72)
	private String tsCommmentsTimes;		// ts_commments_times
	@ExcelField(title="garment_qa_comments", align=2, sort=73)
	private String garmentQaComments;		// garment_qa_comments
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="garment_qa_comment_time", align=2, sort=74)
	private Date garmentQaCommentTime;		// garment_qa_comment_time
	@ExcelField(title="material_qas", align=2, sort=75)
	private String materialQas;		// material_qas
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="material_qss_time", align=2, sort=76)
	private Date materialQssTime;		// material_qss_time
	@ExcelField(title="mer_team", align=2, sort=77)
	private String merTeam;		// mer_team
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="mer_team_time", align=2, sort=78)
	private Date merTeamTime;		// mer_team_time
	@ExcelField(title="meeting_record", align=2, sort=79)
	private String meetingRecord;		// meeting_record
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="meeting_record_time", align=2, sort=80)
	private Date meetingRecordTime;		// meeting_record_time

	private String prTaskStatus;

	private String labTestingTaskStatus;
	private String userName;
	
	private String labCount;
	
	private List<String> ids;
	
	private String source;

}
