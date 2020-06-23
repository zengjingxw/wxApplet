// pages/checkstand/checkstand.js
import {
   updOrder
  } from '../../service/order.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    payData: [
      {
        "name": "微信支付",
        "icon": "/assets/images/common/whcatpay.png",
        "signData": "wx"
      },
      {
        "name": "QQ支付",
        "icon": "/assets/images/common/qqpay.png",
        "signData": "qq"
      },
      {
        "name": "支付宝支付",
        "icon": "/assets/images/common/alipaypng.png",
        "signData": "alipay"
      }
    ],
    price: 1000,
    barData: {
      "way": "微信支付",
      "price" : 1000
    },
    orderId: ''
  },

//=====================事件处理==============================
  handleBarSwicth(e) {
    let obj = {}
    obj.price = this.data.price
    if(e.detail.check == "qq") {
      obj.way ="QQ支付"
    } else if (e.detail.check == "wx") {
      obj.way = "微信支付"
    } else if (e.detail.check == "alipay") {
      obj.way = "支付宝支付"
    }
    this.setData({
      barData: obj
    })
  },

  handleOrder() {
    updOrder({
      sign: "fk",
      orderId: this.data.orderId
    }).then(res => {
      if(res.data.code == 1) {
        wx.hideLoading()
        wx.navigateTo({
          url: '/pages/myorder/myorder',
        })
      }
    })
  },

//=====================/事件处理================================
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const obj = JSON.parse(options.obj)
    console.log(obj)
    let signData = {}
    this.data.orderId =  obj.id
    signData.price = obj.totalPrice
    signData.way="微信支付"
    this.setData({
      price: obj.totalPrice,
      barData: signData
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})