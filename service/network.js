import { baseURL } from "./constant.js"
export default function request(options) {
  return new Promise((resovle, reject) => {
    wx.showLoading({
      title: '加载中'
    })
    wx.request({
      url: baseURL+ options.url,
      method: options.method || 'get',
      data: options.data || {},
      success: resovle,
      fail: reject
    })
  })
}
