package com.tiutiu.prodiff.mapper;

import com.tiutiu.prodiff.entity.PdProj;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface PdProjMapper {
    @Select("select * from pdproj where `pdproj_name` = #{pdProjName}")
    PdProj getPdProjByName(String pdProjName);
}
