// pages/order/order.js
import {
  toOrder,
  orderHandler
} from '../../service/order.js'
const app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    orderList: [],
    tatolPrice: 0,
    defaultAddress: {},
    sign: '',
    payData: {}
  },

  //===============事件处理=====================
  goToOrder() {
    toOrder().then(res => {
      if (res.data.code == 1) {
        wx.hideLoading();
        if (this.data.sign == "one") {
          let _a = this.data.orderList
          _a.push(this.data.payData)
          this.setData({
            orderList: _a,
            defaultAddress: res.data.body.address
          })
        } else {
          this.setData({
            orderList: res.data.body.prodList,
            defaultAddress: res.data.body.address
          })
        }
        this.comPrice()
        this.checkAd()
      } else if (res.data.code == -1) {
        app.clean()
      }
    })
  },

  //计算价格

  comPrice() {
    const ofPrice = this.data.orderList.reduce((preValue, item) => {
      return (preValue * 1) + ((item.price * item.count) * 1)
    }, 0)
    this.setData({
      tatolPrice: ofPrice
    })
  },

  //提交订单

  handleSubOrder() {
    if (this.data.defaultAddress.phone == null) {
      wx.showModal({
        title: '提示',
        content: '请选择地址',
        success(res) {
          if (res.confirm) {
            wx.navigateTo({
              url: '/pages/address/address',
            })
          }
        }
      })
      return
    } 

    let address = this.data.defaultAddress
    let body =[]
    let sign
    if (wx.getStorageSync("sign")) {
      let payData = wx.getStorageSync("payData")
      body.push(payData)
      sign = "one"
    } else {
      console.log("测试")
      body = this.data.orderList
      sign = "much"
    }
    orderHandler({
      body: body,
      address: address,
      sign: sign,
    }).then(res => {
      if(res.data.code == 1) {
        wx.hideLoading()
        if(res.data.message == "much") {
          app.updCart()
        }
        console.log(res)
        const obj = JSON.stringify(res.data.body)
        wx.navigateTo({
          url: '/pages/checkstand/checkstand?obj=' + obj,
        })
        // wx.showModal({
        //   title: '提示',
        //   content: '直接付款完成',
        //   success(res) {
        //     if(res.confirm) {
        //       wx.switchTab({
        //         url: '/pages/cart/cart',
        //       })
        //     }
        //     if(res.cancel) {
        //       wx.switchTab({
        //         url: '/pages/cart/cart',
        //       })
        //     }
        //   }
        // })
      }
      else if(res.data.code == -1) {
        app.clean()
      }
    })
    
  },

  checkAd() {
    if (wx.getStorageSync("tmpAddress")) {
      this.setData({
        defaultAddress: wx.getStorageSync("tmpAddress")
      })
    }
    wx.removeStorageSync("tmpAddress")
  },

  //判断是否直接购买还是购物车跳转
  judeg(options) {

    if (options.sign == "one") {
      wx.setStorageSync("sign", options.sign)
      wx.setStorageSync("payData", JSON.parse(options.obj))
    }

    if (options.sign == "cart") {
      wx.removeStorageSync("sign")
      wx.removeStorageSync("payData")
    }

    if (wx.getStorageSync("sign")) {
      this.setData({
        sign: wx.getStorageSync("sign"),
        payData: wx.getStorageSync("payData")
      })
    }
  },
  //==================/事件处理=============================
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

    this.judeg(options)

    this.goToOrder()

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

})