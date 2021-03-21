package com.xing.libao.init;

import com.xing.libao.dao.TitleDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.BitSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/7/6
 * Time: 11:38
 */
@Component
public class MidSet implements InitializingBean {
    @Autowired
    TitleDao titleDao;
    private static final MidSet single = new MidSet();
    //DEFAULT_SIZE为2的29次方，即此处的左移28位
    private static final int DEFAULT_SIZE = 2 << 27;

    private BitSet bitSets = new BitSet(DEFAULT_SIZE);

    public static MidSet getInstance() {
        return single;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Init mid set");
        List<Long> mids = titleDao.listAllMid();
        for (Long mid : mids) {
            setSet(mid);
        }
    }

    public void setSet(long mid) {
        bitSets.set(toIndex(mid), true);
    }

    public boolean hasIn(long mid) {
        return bitSets.get(toIndex(mid));
    }

    private int toIndex(long in) {
        return (int)(in % Integer.MAX_VALUE);
    }
}
