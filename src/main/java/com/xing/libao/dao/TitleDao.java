package com.xing.libao.dao;

import com.xing.libao.bean.Title;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/6/8
 * Time: 16:17
 */
public interface TitleDao {
    int getBtMd5(String md5);
    int saveTitle(Title title);
}
