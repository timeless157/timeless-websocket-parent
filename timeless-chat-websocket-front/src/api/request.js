import axios from 'axios';
import nprogress from 'nprogress';
import "nprogress/nprogress.css";
import { Notification, MessageBox, Message } from 'element-ui'


const requests = axios.create({
  baseURL: "/api",
  timeout: 5000,
});


requests.interceptors.request.use((config) => {
  nprogress.start();
  return config;
});


// 响应拦截器
requests.interceptors.response.use(res => {
  nprogress.done();
  // 未设置状态码则默认成功状态
  const code = res.data.code || 200
  // console.log(res);
  if (code !== 1000 && code !== 200) {
    // 显示错误消息
    Message.error({
      message: res.data.msg, // 错误信息
      duration: 3000, // 持续时间（毫秒）
    });
    throw new Error(res.data.msg); // 抛出错误
  }
  return res.data;
},
  error => {
    // console.log('err' + error);
    return Promise.reject(error);
  }
)

export default requests;

