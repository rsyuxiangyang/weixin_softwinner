package app.guangda.repository.dao;

import app.guangda.repository.model.GdSubscribeUser;
import app.guangda.repository.model.GdSubscribeUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GdSubscribeUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gd_subscribe_user
     *
     * @mbggenerated Sat Jan 09 21:57:21 CST 2016
     */
    int countByExample(GdSubscribeUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gd_subscribe_user
     *
     * @mbggenerated Sat Jan 09 21:57:21 CST 2016
     */
    int deleteByExample(GdSubscribeUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gd_subscribe_user
     *
     * @mbggenerated Sat Jan 09 21:57:21 CST 2016
     */
    int deleteByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gd_subscribe_user
     *
     * @mbggenerated Sat Jan 09 21:57:21 CST 2016
     */
    int insert(GdSubscribeUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gd_subscribe_user
     *
     * @mbggenerated Sat Jan 09 21:57:21 CST 2016
     */
    int insertSelective(GdSubscribeUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gd_subscribe_user
     *
     * @mbggenerated Sat Jan 09 21:57:21 CST 2016
     */
    List<GdSubscribeUser> selectByExample(GdSubscribeUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gd_subscribe_user
     *
     * @mbggenerated Sat Jan 09 21:57:21 CST 2016
     */
    GdSubscribeUser selectByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gd_subscribe_user
     *
     * @mbggenerated Sat Jan 09 21:57:21 CST 2016
     */
    int updateByExampleSelective(@Param("record") GdSubscribeUser record, @Param("example") GdSubscribeUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gd_subscribe_user
     *
     * @mbggenerated Sat Jan 09 21:57:21 CST 2016
     */
    int updateByExample(@Param("record") GdSubscribeUser record, @Param("example") GdSubscribeUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gd_subscribe_user
     *
     * @mbggenerated Sat Jan 09 21:57:21 CST 2016
     */
    int updateByPrimaryKeySelective(GdSubscribeUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gd_subscribe_user
     *
     * @mbggenerated Sat Jan 09 21:57:21 CST 2016
     */
    int updateByPrimaryKey(GdSubscribeUser record);
}