package com.timeless.domain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.*;

/**
 * (ChatUser)表实体类
 *
 * @author makejava
 * @since 2023-06-23 00:09:29
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("timeless_chat_user")
public class ChatUser {

    @TableId(type = IdType.AUTO)
    private Long id;

    //用户名
    @Pattern(regexp = "^[0-9a-z]{5,10}$", message = "用户名只包含0-9和小写a-z的字符串，且是5-10位")
    private String username;

    //用户昵称
    @Size(min = 1, max = 10 , message = "昵称必须在1-10位之间")
    private String nickname;

    //用户密码
    @Size(min = 1, max = 10)
    private String password;

    //用户头像
    private String avatar;

    //用户状态(在线、离线)    
    private String userState;
}
