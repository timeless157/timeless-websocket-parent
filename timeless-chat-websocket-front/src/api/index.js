import requests from "./request";

export function getAllUsers(username) {
    return requests({
        url: '/ChatUserController/getAllUsers',
        method: 'get',
        params: {
            username: username
        },
    })
}

// 登录
export function userLogin(username, password) {
    return requests({
        url: '/ChatUserController/login',
        method: 'post',
        data: { 'username': username, 'password': password }
    })
}

// 注册
export function userRegister(username, nickname, password) {
    return requests({
        url: '/ChatUserController/register',
        method: 'post',
        data: { 'username': username, 'nickname': nickname, 'password': password }
    })
}

// 得到私聊消息
export function listPrivateMessages(fromUsername, toUsername) {
    return requests({
        url: `/PrivateMessageController/listPrivateMessages/${fromUsername}/${toUsername}`,
        method: 'get',
    })
}

// 删除所有私聊消息
export function deleteAllMsg(fromUsername, toUsername) {
    return requests({
        url: `/PrivateMessageController/deleteAllMsg/${fromUsername}/${toUsername}`,
        method: 'delete',
    })
}
