package ai.magicemerge.bluebird.app.service.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WorkspaceDto implements Serializable {
	private static final long serialVersionUID = 5673544810970583764L;


	/**
	 * 自增ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 空间名称
	 */
	private String name;
	/**
	 * 是否启用
	 */
	private Boolean isValid;

	/**
	 * 描述
	 */
	private String remark;
	private Date createAt;

	/**
	 * 空间管理者id
	 */
	@Schema(description = "空间管理者id")
	private String managerId;

	@Schema(description = "空间管理者名称")
	private String managerName;


}
