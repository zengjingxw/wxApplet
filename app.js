//app.js
import {
  checkToken,
  syncCart
} from './service/user.js'
App({
  onLaunch: function() {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })

    this.checkToken()


  },

  //添加购物车
  addToCart(obj) {
    const oldInfo = this.globalData.cartList.find(item => item.id === obj.id)
    if (oldInfo) {
      oldInfo.count += 1
    } else {
      obj.count = 1
      obj.checked = true
      this.globalData.cartList.push(obj)
    }
    if(!wx.getStorageInfoSync("token")) { 
      wx.setStorageSync("cart", this.globalData.cartList)
    }

    if (wx.getStorageSync("token")) {
      setTimeout(() => {
        this.globalSyncCart()
      }, 1000)
    }

  },

  //定期全局数据
  globalData: {
    cartList: wx.getStorageSync("cart") || [],
    userInfo: {}
  },

  //=============================================登录验证===================================================


  //token验证
  checkToken() {
    checkToken().then(res => {
      if (res.data == 1) {
        this.globalSyncCart()
        // 获取用户信息
        wx.getSetting({
          success: res => {
            if (res.authSetting['scope.userInfo']) {

              wx.getUserInfo({
                success: res => {
                  // 可以将 res 发送给后台解码出 unionId
                  this.globalData.userInfo = res.userInfo

                  // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                  // 所以此处加入 callback 以防止这种情况
                  if (this.userInfoReadyCallback) {
                    this.userInfoReadyCallback(res)
                  }
                }
              })
            }
          }
        })
      }
      //token失效获取不正确进行本机数据清除
      if (res.data == -1 || res.data == 40001) {
        wx.hideLoading()
        this.clean()
      }
    })
  },

  //同步cart
  globalSyncCart() {
    // let body = wx.getStorageSync("cart")
    let body = this.globalData.cartList
    syncCart(body).then(res => {
      if (res == -1) {
        this.clean()
      } else {
        if (res.data.body != null) {
          this.globalData.cartList = res.data.body.lists
          wx.removeStorageSync("cart")
        }
      }
    })
  },

  //本地更新cart

  updCart() {
   const signs =  this.globalData.cartList.filter( item => !item.checked)
   this.globalData.cartList = signs
   console.log("全局数据跟新了")
    console.log(this.globalData.cartList)
  },

  //清楚本地数据
  clean() {
    wx.hideLoading()
    wx.removeStorageSync("sessionId")
    wx.removeStorageSync("token")
  }
})