package com.poc.pro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.poc.pro.model.MDFile;

@Mapper
public interface MDFileDao {

	@Select("select * from files_table where id=#{id}")
	public MDFile getFile(int id);

	@Insert("insert into files_table(name,filepath) values(#{name},#{path})")
	public int saveFile(MDFile filename);

	@Delete("delete ")
	public void DeleteFile(MDFile file, int id);

	@Select("SELECT * FROM files_table")
	public List<MDFile> getAllFiles();
}
