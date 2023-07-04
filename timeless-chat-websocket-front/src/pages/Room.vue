<template>
  <div>
    <h3 style="text-align: center">当前用户：{{ this.username }}</h3>
    <h3 style="text-align: center">在线人数：{{ this.userNumber }}</h3>
<!--    <h3 style="text-align: center">在线用户：-->
<!--      <div v-for="user in usernameOnlineList" :key="user">{{ user }}</div>-->
<!--    </h3>-->
    <div class="container">
      <div class="left">
        <h2 style="text-align: center">用户列表</h2>
        <ul>
          <li
              v-for="user in userList"
              :key="user.id"
              :class="{ selected: user.selected }"
              title="点击选择用户聊天"
          >
            <div class="user-info">
              <span @click="selectUser(user)">
                {{ user.toUsername }}
              </span>
              <!-- <div class="button-container">
                <el-button
                  v-if="user.isFriend === 0"
                  type="primary"
                  size="mini"
                  @click="sendFriendRequest(user)"
                >
                  申请加好友
                </el-button>
                <el-button
                  v-if="user.isFriend === 1"
                  type="success"
                  @click="sendMessage(user)"
                >
                  好友
                </el-button>
                <el-button v-if="user.isFriend === 2" type="danger" disabled>
                  申请中
                </el-button>
              </div> -->
            </div>
          </li>
        </ul>
      </div>
      <div class="middle">
        <h2 style="text-align: center">群聊列表</h2>
        <h2 style="text-align: center; color: rgb(57, 29, 216)">
          功能开发中......
        </h2>
        <ul>
          <!-- <li v-for="user in groupList" :key="user.id">{{ groupList.groupName }}</li> -->
        </ul>
      </div>
      <div class="right">
        <div v-if="selectedUser">
          <h2 style="text-align: center">
            正在与{{ selectedUser.toUsername }}聊天
          </h2>
        </div>
        <div v-if="selectedUser">
          <ul>
            <li
                v-for="message in messageList[username + selectedUser.toUsername]"
                :key="message"
            >
              {{ message }}
            </li>
          </ul>
        </div>
        <div v-if="selectedUser">
          <div class="message-input">
            <el-input
                v-model="selectedUserMessage.message"
                placeholder="请输入内容"
                @keyup.enter.native="sendMsg"
            ></el-input>
            <div class="button-container">
              <el-button type="primary" @click="sendMsg">发送消息</el-button>
              <el-button type="danger" @click="deleteAllMsgs"
              >删除所有消息
              </el-button
              >
            </div>
          </div>
        </div>
      </div>
    </div>
    <div>
      <h1 class="bottom" style="text-align: center">好友申请</h1>

      <h2 style="text-align: center; color: rgb(57, 29, 216)">
        功能开发中......
      </h2>
    </div>
  </div>
</template>

<script>
import {getAllUsers, listPrivateMessages, deleteAllMsg} from "@/api";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import {Message} from "element-ui";

export default {
  name: "Room",
  data() {
    return {
      userList: [],
      groupList: [],
      selectedUser: null,
      message: "",
      stompClient: null,
      messageList: {}, // 使用对象来存储每个用户的聊天记录
      username: "",
      usernameOnlineList: [],
      userNumber: 1,
      selectedUserMessage: {
        user: null,
        message: "",
      },
    };
  },
  methods: {
    listAllUsers() {
      getAllUsers(this.username).then((response) => {
        this.userNumber = ++response.data.userNumber;
        // this.usernameOnlineList = response.data.usernameOnlineList;
        this.userList = response.data.friends.filter(
            (user) => user.toUsername !== this.username
        );
      });
    },
    selectUser(user) {
      // if (user.isFriend === 0 || user.isFriend === 2) {
      //   // 不是好友，不能聊天
      //   Message.error({
      //     message: "非好友，不能聊天", // 错误信息
      //     duration: 3000, // 持续时间（毫秒）
      //   });
      //   return;
      // }
      if (!this.messageList[this.username + user.toUsername]) {
        console.log(2222222)
        this.$set(this.messageList, this.username + user.toUsername, []);
      }

      // 展示数据库中存在的信息，也就是聊天记录
      listPrivateMessages(this.username, user.toUsername).then((response) => {
        this.$set(this.messageList, this.username + user.toUsername, response.data);
      });

      this.selectedUser = user;
      this.selectedUserMessage.user = user;
      this.selectedUserMessage.message = ""; // 清空输入框内容
      this.userList.forEach((u) => {
        u.selected = false;
      });
      user.selected = true;
    },
    sendMsg() {
      if (
          this.stompClient !== null &&
          this.selectedUserMessage.message !== ""
      ) {
        this.stompClient.send(
            "/ClientToServer/privateChat",
            {},
            JSON.stringify({
              fromUsername: this.username,
              message: this.selectedUserMessage.message,
              toUsername: this.selectedUserMessage.user.toUsername,
            })
        );
        // if (!this.messageList[this.selectedUserMessage.user.fromUsername]) {
        //   this.$set(
        //     this.messageList,
        //     this.selectedUserMessage.user.fromUsername,
        //     []
        //   );
        // }
        this.messageList[this.username + this.selectedUserMessage.user.toUsername].push(
            this.username + " 发送：" + this.selectedUserMessage.message
        );

        this.selectedUserMessage.message = ""; // 清空输入框内容
      } else {
        Message.info("请输入消息");
      }
    },
    deleteAllMsgs() {
      if (this.messageList[this.username + this.selectedUserMessage.user.toUsername] == "") {
        Message.error("当前没有聊天记录");
        return;
      }
      deleteAllMsg(this.username, this.selectedUser.toUsername).then(
          (response) => {
            this.messageList[this.username + this.selectedUserMessage.user.toUsername] = [];
            Message.success("删除成功");
          }
      );
    },
    connect() {
      const socket = new SockJS("http://你的虚拟机ip:8082/ws/timeless");
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect({}, (frame) => {
        console.log("Connected: " + frame);
        this.stompClient.subscribe(
            "/topic/ServerToClient.private." + this.username,
            (result) => {
              this.showContent(
                  JSON.parse(result.body).message,
                  JSON.parse(result.body).fromUsername,
                  JSON.parse(result.body).toUsername,
              )
            });
        this.stompClient.subscribe("/topic/ServerToClient.deleteMsg", (result) => {
          const res = JSON.parse(result.body);
          this.messageList[res.toUsername + res.fromUsername] = [];
          // if (this.selectedUser !== null) {
          //   this.messageList[this.username + this.selectedUser.toUsername] = [];
          // }
        });
        this.stompClient.subscribe("/topic/ServerToClient.showUserNumber", (result) => {
          this.userNumber = result.body;
        });
      });
    },
    disconnect() {
      if (this.stompClient !== null) {
        this.stompClient.disconnect();
      }
      console.log("Disconnected");
    },
    showContent(body, from, to) {
      // 处理接收到的消息
      // 示例代码，根据实际需求进行修改
      // console.log(this.selectedUser);
      if (!this.messageList[to + from]) {
        this.$set(this.messageList, to + from, []); // 初始化选定用户的聊天记录数组
      }
      this.messageList[to + from].push(body); // 将接收到的消息添加到选定用户的聊天记录数组

      // console.log(body);
    },
  },
  created() {
  },
  mounted() {
    // 从sessionStorage中获取用户名
    this.username = sessionStorage.getItem("username");
    this.connect();
    this.listAllUsers();
    // console.log(this.username);
  },
  beforeDestroy() {
    this.disconnect();
  },
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  margin: 10px;
}

.left,
.middle,
.right {
  flex: 0.5;
  margin: 5px;
  padding: 10px;
  background-color: lightgray;
}

.right {
  flex: 2;
}

.bottom {
  margin-top: 20px;
  text-align: center;
}

li {
  cursor: pointer;
  transition: color 0.3s ease;
}

li:hover {
  color: blue;
}

li.selected {
  color: blue;
  font-weight: bold;
}

.send-button {
  display: flex;
  justify-content: flex-end;
}

.message-input {
  display: flex;
  align-items: center;
}

.button-container {
  margin-left: 10px; /* 调整间距大小 */
}

.message-container {
  display: flex;
  justify-content: flex-end;
}

.button-container {
  display: flex;
  justify-content: flex-end;
}

.user-info {
  display: flex;
  align-items: center;
}

.button-container {
  margin-left: auto;
}
</style>