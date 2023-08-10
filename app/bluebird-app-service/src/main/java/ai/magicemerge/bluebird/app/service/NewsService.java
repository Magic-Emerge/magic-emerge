package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.dal.model.News;
import ai.magicemerge.bluebird.app.service.dto.AppNewsDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 应用咨询 服务类
 * </p>
 *
 * @author GygesM
 * @since 2023-07-02
 */
public interface NewsService extends IService<News> {

	List<News> getNews(AppNewsDto appNewsDto);

}
