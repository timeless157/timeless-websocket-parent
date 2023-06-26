<template>
  <div>
    <h1 style="text-align: center">注册</h1>
    <router-link class="register" to="/login">点我登录</router-link>
    <br />
    <div class="input-container">
      <div class="row">
        <el-input
          type="text"
          placeholder="用户名(只包含0-9和小写a-z的字符串，且是5-10位)"
          v-model="username"
        ></el-input>
      </div>
      <div class="row">
        <el-input
          type="text"
          placeholder="昵称(1-10位)"
          v-model="nickname"
        ></el-input>
      </div>
      <div class="row">
        <el-input
          type="password"
          placeholder="密码(1-10位)"
          v-model="password"
        ></el-input>
      </div>
      <div class="row">
        <el-input
          type="password"
          placeholder="确认密码(1-10位)"
          @keyup.enter.native="register"
          v-model="passwordConfirm"
        ></el-input>
      </div>
    </div>

    <div class="button-container">
      <el-button type="primary" @click="register">注册</el-button>
    </div>
  </div>
</template>

<script>
import { userRegister } from "@/api";
import { Message } from "element-ui";
export default {
  name: "Register",
  data() {
    return {
      username: "",
      nickname: "",
      password: "",
      passwordConfirm: "",
    };
  },

  methods: {
    register() {
      if (this.password !== this.passwordConfirm) {
        Message.error("两次密码不一致");
        return;
      }
      userRegister(this.username, this.nickname, this.password)
        .then((response) => {
          Message.success("注册成功");
          this.$router.push("/login"); // 跳转到登录界面
        })
        .catch((error) => {
          //
        });
    },
  },

  created() {},
};
</script>

<style>
.register {
  display: flex;
  justify-content: center;
  align-items: center;
}

.button-container {
  display: flex;
  justify-content: center;
}

.input-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.row {
  width: 380px; /* 调整输入框的长度 */
  margin-bottom: 10px; /* 调整行之间的间距 */
}
</style>