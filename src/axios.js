import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8088', // 后端服务的基础URL
  timeout: 30000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json', // 设置默认 Content-Type
  },
});

// 请求拦截器
instance.interceptors.request.use(config => {
  // 添加token
  const token = localStorage.getItem('token');
  console.log("token:"+token)
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, error => {
  
  return Promise.reject(error);
});

// 响应拦截器
instance.interceptors.response.use(response => {
  
  return response;
}, error => {
    // 打印响应错误，便于调试
    console.error('Response Error:', error.response || error);
  
    // 处理具体的错误信息（比如 token 过期）
    if (error.response && error.response.status === 401) {
      // 根据需要处理 Token 过期或无效的情况
      console.error('Token 无效或已过期');
    }
  return Promise.reject(error);
});

export default instance;