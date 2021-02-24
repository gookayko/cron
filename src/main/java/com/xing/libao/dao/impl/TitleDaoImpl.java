package com.xing.libao.dao.impl;

import com.xing.libao.bean.Title;
import com.xing.libao.dao.TitleDao;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/6/8
 * Time: 16:23
 */
public class TitleDaoImpl implements TitleDao {
    @Override
    public int getBtMd5(String md5) {
        return (Integer) sqlSession.selectOne(getClass().getName() + ".getBtMd5", md5);
    }

    @Override
    public int saveTitle(Title title) {
        return sqlSession.insert(getClass().getName() + ".saveTitle", title);
    }

    public SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }
}
