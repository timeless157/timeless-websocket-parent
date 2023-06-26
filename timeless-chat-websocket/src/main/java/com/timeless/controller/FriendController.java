package com.timeless.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.timeless.domain.ResponseResult;
import com.timeless.domain.entity.ChatUser;
import com.timeless.domain.entity.Friend;
import com.timeless.enums.AppHttpCodeEnum;
import com.timeless.exception.SystemException;
import com.timeless.service.ChatUserService;
import com.timeless.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * @author timeless
 * @create 2023-06-23 0:13
 */
@RestController
@RequestMapping("/FriendController")
public class FriendController {

    @Autowired
    private FriendService friendService;

    /**
     * @Description: 申请好友
     * @Date: 2023/6/24 0:11
     * @Author: timeless
     */
    @PostMapping("/applyForFriends")
    public ResponseResult applyForFriends(@RequestBody Friend friend) {
        boolean saveOrUpdate = friendService.saveOrUpdate(friend, Wrappers.<Friend>lambdaQuery()
                .eq(Friend::getFromUsername, friend.getFromUsername())
                .eq(Friend::getToUsername, friend.getToUsername()));
        if (!saveOrUpdate) {
            throw new SystemException(AppHttpCodeEnum.APPLY_FAIL);
        }
        return ResponseResult.okResult().setMsg("申请成功");
    }

}
