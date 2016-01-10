package app.guangda.repository.dao.nonauto;

import app.guangda.repository.model.nonauto.GdDeptShow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NonAutoGdDeptMapper {
    @Select("select max(dept_scene_id) from gd_dept")
    String selectMaxSceneId();

    @Select(" SELECT  pkid,  " +
            "   dept_code as deptCode,  " +
            "   dept_name as deptName,  " +
            "   dept_address as deptAddress,  " +
            "   dept_type as deptType,  " +
            "   dept_phone as deptPhone,  " +
            "   dept_qrcode as deptQrcode,  " +
            "   (select count(*) from gd_subscribe_log where dept_code =CONCAT(\"qrscene_\",t.dept_scene_id) and  subscribe_type='1'  and create_time between #{strStartDate} and #{strEndDate}) dayNew ,  " +
            "   (select count(*) from gd_subscribe_log where dept_code =CONCAT(\"qrscene_\",t.dept_scene_id) and subscribe_type='0' and create_time between #{strStartDate} and #{strEndDate}) dayCancle,  " +
            "   (select count(*) from gd_subscribe_user where dept_code =CONCAT(\"qrscene_\",t.dept_scene_id) and subscribe_flag='1') total  " +
            " FROM `gd_dept`  t;")
    List<GdDeptShow> qryDeptSubscribeInfo(@Param("strStartDate") String strStartDate,@Param("strEndDate") String strEndDate);

}