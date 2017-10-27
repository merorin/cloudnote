package merorin.cloud.cloudnote.base;

import com.alibaba.fastjson.JSON;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description: 基本的spring测试类
 *
 * @author guobin On date 2017/10/27.
 * @version 1.0
 * @since jdk 1.8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:ctx-base-test.xml" })
@Ignore("This is a base test, no need proceeding.")
public abstract class BaseTest {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 准备测试类所用的数据
     * @param path 文件路径
     * @param clazz 类
     * @param <T> 操作对象的类
     * @return 此次插入的数据
     * @throws Exception 抛出异常
     */
    protected <T> List<T> prepareDbData(String path ,Class<T> clazz) throws Exception {

        return Optional.ofNullable(path).map(pathUrl -> {

            List<T> dataList = null;

            try {
                String url = this.getClass().getResource(pathUrl).getPath();
                Stream<String> stringStream = Files.lines(Paths.get(url), Charset.defaultCharset());
                String dataStr = stringStream.filter( str -> str != null && !str.isEmpty()).collect(Collectors.joining());

                dataList = JSON.parseArray(dataStr, clazz);
                dataList.forEach(this.mongoTemplate::insert);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return dataList;
        }).orElse(null);
    }

    /**
     * 对插入的数据进行回滚
     * @param ids 需要删除的id列表
     * @param clazz 操作对象的类
     * @param <T> 操作对象的类
     */
    protected <T> void rollback(List<String> ids, Class<T> clazz) {
        Optional.ofNullable(ids).ifPresent(list -> list.forEach(id -> {
            Query query = new Query(Criteria.where("_id").is(id));
            this.mongoTemplate.findAndRemove(query, clazz);
        }));
    }
}
