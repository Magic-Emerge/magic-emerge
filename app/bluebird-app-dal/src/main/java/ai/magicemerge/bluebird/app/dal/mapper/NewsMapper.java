package ai.magicemerge.bluebird.app.dal.mapper;

import ai.magicemerge.bluebird.app.dal.model.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 应用咨询 Mapper 接口
 * </p>
 *
 * @author GygesM
 * @since 2023-07-02
 */
@Mapper
public interface NewsMapper extends BaseMapper<News> {

}
