package com.timeless.domain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (Friend)表实体类
 *
 * @author makejava
 * @since 2023-06-23 21:44:11
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("timeless_friend")
public class Friend {

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 主动加的人的用户名    
    private String fromUsername;

    // 被加的人的用户名    
    private String toUsername;

    // 是否是好友（0：否  1：是  2: 申请中）
    private Integer isFriend;

}
