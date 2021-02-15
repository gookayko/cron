package com.xing.libao.dao;


import com.xing.libao.bean.Code;

/**
 * Created by Administrator on 2015/3/13 0013.
 */
public interface CodeDao {
    public int saveCode(Code code);

    public Integer countByCode(String liCode);
}
