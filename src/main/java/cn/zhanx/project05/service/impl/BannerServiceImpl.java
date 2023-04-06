package cn.zhanx.project05.service.impl;

import cn.zhanx.project05.domain.Banner;
import cn.zhanx.project05.mapper.BannerMapper;
import cn.zhanx.project05.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

}
