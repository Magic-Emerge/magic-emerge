package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.dal.model.Search;
import ai.magicemerge.bluebird.app.dal.mapper.SearchMapper;
import ai.magicemerge.bluebird.app.service.SearchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GygesM
 * @since 2023-06-07
 */
@Service
public class SearchServiceImpl extends ServiceImpl<SearchMapper, Search> implements SearchService {

}
