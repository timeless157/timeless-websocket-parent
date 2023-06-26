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
 * (PrivateMessage)表实体类
 *
 * @author makejava
 * @since 2023-06-24 17:41:54
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("timeless_private_message")
public class PrivateMessage  {

    @TableId(type = IdType.AUTO)
    private Long id;
    
    // 发消息者    
    private String fromUsername;
    
    // 接收消息者
    private String toUsername;
    
    // 消息内容    
    private String message;
    
}
