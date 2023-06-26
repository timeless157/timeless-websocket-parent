package com.timeless.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timeless.domain.entity.ChatUser;
import com.timeless.mapper.ChatUserMapper;
import com.timeless.service.ChatUserService;
import org.springframework.stereotype.Service;

/**
 * (ChatUser)表服务实现类
 *
 * @author makejava
 * @since 2023-06-23 00:09:31
 */
@Service("chatUserService")
public class ChatUserServiceImpl extends ServiceImpl<ChatUserMapper, ChatUser> implements ChatUserService {
}
