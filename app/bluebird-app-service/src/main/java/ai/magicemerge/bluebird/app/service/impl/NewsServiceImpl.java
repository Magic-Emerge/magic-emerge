package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.dal.model.News;
import ai.magicemerge.bluebird.app.dal.mapper.NewsMapper;
import ai.magicemerge.bluebird.app.service.NewsService;
import ai.magicemerge.bluebird.app.service.common.utils.AuthUtils;
import ai.magicemerge.bluebird.app.service.dto.AppNewsDto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 应用咨询 服务实现类
 * </p>
 *
 * @author GygesM
 * @since 2023-07-02
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

	@Autowired
	private NewsMapper newsMapper;

	@Override
	public List<News> getNews(AppNewsDto appNewsDto) {

		LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<News>()
				.orderByDesc(News::getNewsTime);
		if (AuthUtils.isUser()) {
			queryWrapper.eq(News::getIsPublic, appNewsDto.getIsPublic());
		}
		if (StringUtils.isNotBlank(appNewsDto.getTitle())) {
			queryWrapper.like(News::getTitle, appNewsDto.getTitle());
		}

		return newsMapper.selectList(queryWrapper);
	}
}
