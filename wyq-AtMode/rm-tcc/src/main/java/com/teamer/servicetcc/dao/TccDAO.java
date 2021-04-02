package com.teamer.servicetcc.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Mapper
@Component
public interface TccDAO {

    @Insert("insert into service_tcc (bName) value (#{name})")
    int insert(Map<String, String> map);

    @Delete("delete from service_tcc where bName= #{name}")
    int delete(String name);
}
