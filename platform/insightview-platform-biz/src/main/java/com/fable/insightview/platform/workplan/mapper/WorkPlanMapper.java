package com.fable.insightview.platform.workplan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.workplan.entity.WorkPlan;

public interface WorkPlanMapper {
	
	List<WorkPlan> selectWorkPlans(Page<WorkPlan> page);

	void insertWorkPlan(WorkPlan workPlan);
	
	int deleteWorkPlanById(@Param("id")Integer id);
	
	int updateWorkPlan(WorkPlan workPlan);
	
	WorkPlan selectWorkPlanById(@Param("id")Integer id);
	
	List<WorkPlan> selectWorkPlansByUserId(@Param("userId")Integer userId);
}
