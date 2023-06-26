<template>
  <div>
    <h1 style="text-align: center">登录</h1>
    <router-link class="register" to="/register">点我注册</router-link>
    <br />
    <div class="input-container">
      <div class="row">
        <el-input
          type="text"
          placeholder="用户名"
          v-model="username"
        ></el-input>
      </div>
      <div class="row">
        <el-input
          type="password"
          placeholder="密码"
          @keyup.enter.native="loginEnterFun"
          v-model="password"
        ></el-input>
      </div>
    </div>

    <div class="button-container">
      <el-button type="primary" @click="login">登录</el-button>
    </div>
  </div>
</template>

<script>
import { userLogin } from "@/api";
export default {
  name: "Login",
  data() {
    return {
      username: "",
      password: "",
    };
  },

  methods: {
    loginEnterFun: function (e) {
      var keyCode = window.event ? e.keyCode : e.which;
      // console.log('回车登录',keyCode,e);
      if (keyCode == 13) {
        this.login();
      }
    },

    login: function () {
      //用户登录
      userLogin(this.username, this.password)
        .then((response) => {
          // 将用户名存储在sessionStorage中
          sessionStorage.setItem("username", this.username);
          this.$router.push({ path: "/room" });
        })
        .catch((error) => {
          // console.log(error);
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
  width: 200px; /* 调整输入框的长度 */
  margin-bottom: 10px; /* 调整行之间的间距 */
}
</style>