package com.timeless.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timeless.domain.entity.PrivateMessage;
import com.timeless.mapper.PrivateMessageMapper;
import com.timeless.service.PrivateMessageService;
import org.springframework.stereotype.Service;
/**
 * (PrivateMessage)表服务实现类
 *
 * @author makejava
 * @since 2023-06-24 17:41:55
 */
@Service("privateMessageService")
public class PrivateMessageServiceImpl extends ServiceImpl<PrivateMessageMapper, PrivateMessage> implements PrivateMessageService {
}
