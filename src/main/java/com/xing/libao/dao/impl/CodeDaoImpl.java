package com.xing.libao.dao.impl;

import com.xing.libao.bean.Code;
import com.xing.libao.dao.CodeDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2015/3/13 0013.
 */
public class CodeDaoImpl implements CodeDao {
    @Override
    public int saveCode(Code code) {
        return sqlSession.insert(getClass().getName() + ".saveCode",code);
    }

    @Override
    public Integer countByCode(String liCode) {
        return (Integer)sqlSession.selectOne(getClass().getName() + ".countByCode",liCode);
    }

    public SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }
}
