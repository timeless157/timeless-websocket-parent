package com.timeless.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.timeless.constants.Constants;
import com.timeless.domain.ResponseResult;
import com.timeless.domain.entity.ChatUser;
import com.timeless.domain.entity.Friend;
import com.timeless.domain.vo.GetAllUsersVo;
import com.timeless.enums.AppHttpCodeEnum;
import com.timeless.exception.SystemException;
import com.timeless.listener.WebSocketEventListener;
import com.timeless.service.ChatUserService;
import com.timeless.service.FriendService;
import org.apache.ibatis.reflection.property.PropertyCopier;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author timeless
 * @create 2023-06-23 0:13
 */
@RestController
@RequestMapping("/ChatUserController")
public class ChatUserController {

    @Autowired
    private ChatUserService chatUserService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    private static List<String> usernameOnlineList = new ArrayList<>();

    /**
     * @Description: 获得所有的用户
     * @Date: 2023/6/23 3:59
     * @Author: timeless
     */
    @GetMapping("/getAllUsers")
    public ResponseResult<GetAllUsersVo> getAllUsers(@RequestParam("username") String username) {
        GetAllUsersVo getAllUsersVo = new GetAllUsersVo();

        ChatUser chatUser = chatUserService.getOne(Wrappers.<ChatUser>lambdaQuery().eq(ChatUser::getUsername, username));

        List<ChatUser> list = chatUserService.list();
        List<Friend> friends = list.stream()
                .map(user -> {
                    Friend one = friendService.getOne(Wrappers.<Friend>lambdaQuery().eq(Friend::getToUsername, username).eq(Friend::getFromUsername, user.getUsername()));
                    Friend friend = new Friend();
                    friend.setToUsername(user.getUsername());
                    friend.setFromUsername(username);
                    friend.setIsFriend(one == null ? Constants.FRIEND_NO : one.getIsFriend());
                    return friend;
                })
                .collect(Collectors.toList());

        getAllUsersVo.setChatUser(chatUser);
        getAllUsersVo.setFriends(friends);
        getAllUsersVo.setUserNumber(WebSocketEventListener.userNumber);
//        getAllUsersVo.setUsernameOnlineList(usernameOnlineList);
//        rabbitTemplate.convertAndSend("testExchange" , "test.demo" , username);

        return ResponseResult.okResult(getAllUsersVo);

    }

    /**
     * @Description: 注册
     * @Date: 2023/6/23 3:59
     * @Author: timeless
     */
    @PostMapping("/register")
    public ResponseResult register(@RequestBody @Valid ChatUser chatUser) {
        long count = chatUserService.count(Wrappers.<ChatUser>lambdaQuery().eq(ChatUser::getUsername, chatUser.getUsername()));
        if (count > 0) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_HAS_BEEN_REGISTERED);
        }
        boolean save = chatUserService.save(chatUser);
        if (!save) {
            throw new SystemException(AppHttpCodeEnum.REGISTER_FAIL);
        }
        return ResponseResult.okResult(chatUser);
    }

    /**
     * @Description: 登录
     * @Date: 2023/6/23 3:59
     * @Author: timeless
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody ChatUser chatUser) {
        ChatUser user = chatUserService.getOne(Wrappers.<ChatUser>lambdaQuery().eq(ChatUser::getUsername, chatUser.getUsername()));
        if (Objects.isNull(user)) {
            throw new SystemException(AppHttpCodeEnum.USER_NOT_EXIST);
        }
        String password = user.getPassword();
        String userPassword = chatUser.getPassword();
        if (!password.equals(userPassword)) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_ERROR);
        }

//        usernameOnlineList.add(chatUser.getUsername());

        return ResponseResult.okResult(user);
    }


}
