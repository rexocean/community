package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.AlphaDaoHibernateImpl;
import com.nowcoder.community.dao.AlphaDaoMyBatisImpl;
import com.nowcoder.community.dao.AlphaDaoTestImpl;
import com.nowcoder.community.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
//测试时以主程序作为配置类
@ContextConfiguration(classes = CommunityApplication.class)
public class CommunityApplicationTests implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void testApplicationContext() {
        System.out.println(applicationContext);

        //依赖的是接口，实现类变了，接口不用动，但是只会挑选一个，当重复加@Primary注解，实现只加载这一个
        //降低耦合，使得实现类和接口不发生耦合
        AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);

        AlphaDao alphaDao1 = applicationContext.getBean(AlphaDaoMyBatisImpl.class);
        AlphaDao alphaDao2 = applicationContext.getBean(AlphaDaoHibernateImpl.class);
        AlphaDao alphaDao3 = applicationContext.getBean(AlphaDaoTestImpl.class);


        System.out.println(alphaDao.select());
        System.out.println(alphaDao1.select());
        System.out.println(alphaDao2.select());
        System.out.println(alphaDao3.select());

        alphaDao3 = applicationContext.getBean("alphaHibernate", AlphaDao.class);
        System.out.println(alphaDao3.select());

    }

    @Test
    public void testBeanManagement() {
        AlphaService alphaService = applicationContext.getBean(AlphaService.class);
        System.out.println(alphaService);

        alphaService = applicationContext.getBean(AlphaService.class);
        System.out.println(alphaService);
    }

    @Test
    public void testBeanConfig() {
        SimpleDateFormat simpleDateFormat =
                applicationContext.getBean(SimpleDateFormat.class);
        System.out.println(simpleDateFormat.format(new Date()));

    }

    //需要注入其他的实现类，再加一个注解Qualifier(name)
    @Autowired
    @Qualifier("alphaHibernate")
    private AlphaDao alphaDao;

    @Autowired
    private AlphaService alphaService;

    @Autowired
    private SimpleDateFormat simpleDateFormat;

    @Test
    public void testDi() {
        System.out.println(alphaDao);
        System.out.println(alphaService);
        System.out.println(simpleDateFormat);
    }
}
