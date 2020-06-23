// pages/profile/childCpns/s-header/s-header.js
const app = getApp()
import {
  smileRequest
} from '../../../../service/smile.js'

Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    userData: {},
    userShowSign: 0
  },

  /**
   * 组件的方法列表
   */
  methods: {

    getUserInfo: function(e) {
      const _this = this
      if (e.detail.errMsg.indexOf("ok") != -1) {
        wx.login({
          success(res) {
            if (res.code) {
              smileRequest({
                url: "login.wx?code=" + res.code
              }).then(res => {
                if (res.statusCode == 200) {
                  wx.hideLoading()
                  wx.setStorageSync("sessionId", res.data.sessionId)
                  wx.setStorageSync("token", res.data.token)
                  _this.setData({
                    userData: e.detail.userInfo,
                    userShowSign: 1
                  })

                  //登录成功初始化购物车
                  app.globalSyncCart()
                }
              })
            }
          }
        })
      }
    }

  },

  lifetimes: {
    attached: function () {
      if(wx.getStorageSync("token")) {
        this.setData({
          userData: app.globalData.userInfo,
          userShowSign: 1
        })
      } else {
        this.setData({
          userShowSign: 0
        })
      }

    },
    detached: function () {
      
    },
  },


})