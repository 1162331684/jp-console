/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.bom.info.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Bom InfoEntity
 * @author Young
 * @version 2022-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("exception_bom_info")
public class ExceptionBomInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**
     * bom_id
     */
	private String bomId;
	/**
     * LabDip Status
     */
	private String labDipStatus;
	/**
     * LabDip Status Date
     */
	private String labDipStatusDate;
	/**
     * Remark (LabDip)
     */
	private String labDipRemark;
	/**
     * Material Remarks
     */
	private String materialRemarks;
	/**
     * Developer (Material)
     */
	private String materialDeveloper;
	/**
     * Mtl-Supp Lifecycle State
     */
	private String mtlSuppLifecycleState;
	/**
     * Management Model
     */
	private String managementModel;
	/**
     * Developer Location
     */
	private String developerLocation;
	/**
     * Orig Group (Material)
     */
	private String materialOrigGroup;
	/**
     * Description (Material)
     */
	private String materialDescription;
	/**
     * Color
     */
	private String color;
	/**
     * Main Material
     */
	private String mainMaterial;
	/**
     * Fiber Text
     */
	private String fiberText;
	/**
     * Fiber Text Position
     */
	private String fiberTextPosition;
	/**
     * Layer 1 Comp
     */
	private String layerComp;
	/**
     * Lead Time (Days) (MaterialSupplier)
     */
	private String supplierLeadTime;
	/**
     * LO Merch
     */
	private String loMerch;
	/**
     * Dev. Type (Material)
     */
	private String materialDevType;
	/**
     * Typename (Material)
     */
	private String materialTypename;
	/**
     * Business Unit (RMA)
     */
	private String businessUnit;
	/**
     * Layer 1 Const
     */
	private String layerConst;
	/**
     * 1st Colorway
     */
	private String firstColorway;
	/**
     * FlexBOMLink ID
     */
	private String flexBomLinkid;
	/**
     * Lead Time (Days) (Source2SeasonLink)
     */
	private String sourceSeasonLeadTime;
	/**
     * Season
     */
	private String season;
	/**
     * US Rainwear Status
     */
	private String usRainwearStatus;
	/**
     * Product Specialty
     */
	private String productSpecialty;
	/**
     * Mktg FC (Global)
     */
	private String globalMktgFc;
	/**
     * bom_remarks
     */
	private String bomRemarks;
	/**
     * First Season
     */
	private String firstSeason;
	/**
     * Min Qty/Color
     */
	private String minQtyColor;
	/**
     * Min Qty/Order
     */
	private String minQtyOrder;
	/**
     * Width (MaterialSupplier)
     */
	private String supplierWidth;
	/**
     * Width UoM
     */
	private String widthUom;
	/**
     * Supp. Ref.Name
     */
	private String suppRefName;
	/**
     * Brand Partner
     */
	private String brandPartner;
	/**
     * Leadtime Remark
     */
	private String leadtimeRemark;
	/**
     * LCS Remark
     */
	private String lcsRemark;
	/**
     * ts_comments
     */
	private String tsComments;
	/**
     * ts_commments_times
     */
	private String tsCommmentsTimes;
	/**
     * garment_QA_comments
     */
	private String garmentQaComments;
	/**
     * garment_QA_comment_time
     */
	private Date garmentQaCommentTime;
	/**
     * material_QAs
     */
	private String materialQas;
	/**
     * material_QSs_time
     */
	private Date materialQssTime;
	/**
     * mer_team
     */
	private String merTeam;
	/**
     * mer_team_time
     */
	private Date merTeamTime;
	/**
     * meeting_record
     */
	private String meetingRecord;
	/**
     * meeting_record_time
     */
	private Date meetingRecordTime;

}
