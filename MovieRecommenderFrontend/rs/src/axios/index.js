import axios from 'axios'
import qs from 'qs'

axios.defaults.timeout = 20000
axios.defaults.baseURL = 'http://127.0.0.1:8082/'
axios.defaults.withCredentials = true

// 添加请求拦截器
axios.interceptors.request.use(function(config) {
	// 在发送请求之前做些什么
	if (config.method === "post") {
		//config.transformRequest = [JSONtoString]
		config.data = qs.stringify(config.data)
	}
	return config
}, function(error) {
	// 对请求错误做些什么
	return Promise.reject(error)
})

axios.interceptors.response.use(function(response) {

	if (response.data.status === 0) {
		return Promise.resolve(response.data)
	} else {
		return Promise.reject(response.data)
	}
}, function(error) {
	// 对响应错误做点什么
	return Promise.reject(error)
})

export default {
	send: function(url, type, msg) {
		if (type.toLowerCase() === 'get') {
			url +=`?${JSONtoParams(msg)}`;
		}
		console.log(msg)
		return axios[type.toLowerCase()](url, msg)
	}
}

function JSONtoString(data) {
	data.token = localStorage.getItem("token");
	return JSON.stringify(data);
}

function JSONtoParams(data = {}) {
	let back = [];
	data.token = localStorage.getItem("token");
	for (let item in data) {
		back.push(`${item}=${data[item]}`)
	}
	return back.join('&');
}
