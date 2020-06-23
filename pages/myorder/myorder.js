// pages/myorder/myorder.js
import {
  returnMyOrderData
} from '../../service/order.js'
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    titles: [
      "全部",
      "代付款",
      "待收货",
      "已完成",
      "已取消"
    ],
    all: [],
    ofPay: [],
    wait: [],
    off: [],
    motion: [],
    scrolleLeft: "one",
    cpnsH: {},
    index: 0
  },

  //======================事件处理=========================
  _returnMyOrderData() {
    returnMyOrderData().then(res => {
      console.log(res)
      let obj = res.data.body
      if (res.data.code == 1) {
        this.setData({
          all: obj.all,
          ofPay: obj.ofPay,
          wait: obj.wait,
          motion: obj.motion
        })
      } else if (res.data.code == 0) {
        wx.hideLoading()
        console.log("没有任何订单")
      } else if (res == -1) {
        app.clean()
      }
    }).finally(res => {
      wx.hideLoading()
    })
  },

  handleTabClcik(e) {
    let sign;
    // this.data.index = e.detail.index
    switch (e.detail.title) {
      case "全部":
        sign = "one"
        break
      case "代付款":
        sign = "two"
        break
      case "待收货":
        sign = "three"
        break
      case "已完成":
        sign = "four"
        break
      case "已取消":
        sign = "five"
        break
    }
    this.setData({
      scrolleLeft: sign
    })
  },

  //监听滚动
  handleScrolle(e) {
    let x = e.detail.scrollLeft
    let _index = this.data.index
    let cpnsH = this.data.cpnsH
    if( x<=cpnsH.one) {
      _index = 0
    }
    if(x<= cpnsH.two && x>=cpnsH.one) {
      _index = 1
    }
    if(x<=cpnsH.three && x >= cpnsH.two) {
      _index = 2
    }
    if(x <= cpnsH.four && x >= cpnsH.three) {
      _index = 3
    }
    if (x == cpnsH.four || x>= (cpnsH.four - 20)) {
      _index = 4
    }
    console.log(_index)
    if(_index != this.data.index) {
      this.data.index = _index
      this.selectComponent("#s-controller").setCurrentIndex(_index)
    }
  },

  //获取组件的高度
  getComponentsHeight() {
    wx.createSelectorQuery().select("#one").boundingClientRect((rect) => {
      let cpsH = rect.width
      let obj = {}

      obj.one = cpsH
      obj.two = cpsH * 2
      obj.three = cpsH * 3
      obj.four = cpsH * 4
      obj.five = cpsH * 5

      this.setData({
        cpnsH: obj
      })
    }).exec()
  },
  //========================/事件处理==============================
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this._returnMyOrderData()
    this.getComponentsHeight()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
   
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})