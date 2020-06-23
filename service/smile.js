import { baseURLTWO } from "./constant.js"

export function smileRequest(options) {
  
  return new Promise( (resovle, reject) => {
    if (options.showLoading == undefined? true : options.showLoading) {
      wx.showLoading({
        title: '加载中'
      })
    }
    wx.request({
      url: baseURLTWO + options.url,
      method: options.method || 'get',
      data: options.data || {},
      header: { "Cookie": "JSESSIONID=" + (wx.getStorageSync("sessionId")), "token": wx.getStorageSync("token"),"sessionId":wx.getStorageSync("sessionId") },
      success: resovle,
      fail: reject
    })
  })
}