/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.access.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Exception AccessEntity
 * @author Young
 * @version 2022-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("exception_list")
public class ExceptionList extends BaseEntity {

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
	private String assignee;
	/**
     * status
     */
	private String status;
	
	private String applicationNo;
	
	private String processInstanceId;
	
	/**
     * file_path
     */
	private String filePath;
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
