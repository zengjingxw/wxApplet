// pages/cart/cart.js

const appData = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    cartList: [],
    isSelectAll: true,
    totalPrice: 0,
    totalCounter: 0
  },
  //-------------事件监听-----------------
  //结算
  toCom() {
    if (wx.getStorageSync("token")) {
      if (!this.data.cartList.filter(item => item.checked).length) {
        wx.showModal({
          title: '提示',
          content: '至少选择一个',
        })
        return
      }
      wx.navigateTo({
        url: '/pages/order/order?sign=' + "cart",
      })
    } else {
      wx.showModal({
        title: '提示',
        content: '请先登录',
        success(res) {
          if(res.confirm) {
            console.log('用户点击确定')
          } else if(res.cancel) {
            console.log('用户点击取消')
          }
        }
      })
    }

  },

  //全选和全不选
  onSelectAll() {
    if (this.data.isSelectAll) {
      appData.globalData.cartList.filter(item => item.checked).forEach(item => {
        item.checked = false
      })
    } else {
      appData.globalData.cartList.filter(item => !item.checked).forEach(item => {
        item.checked = true
      })
    }
    this.getCartList()
    this.anti()
  },

  //点击选中或者不选中
  onCheckClick(e) {
    const id = e.detail.id
    const index = e.detail.index
    const delegate = appData.globalData.cartList.find(item => item.id == id)
    delegate.checked = !delegate.checked;
    this.setData({
      [`cartList[${index}]`]: delegate
    })
    wx.setStorageSync("cart", this.data.cartList)
    // this.getCartList()
    this.getTotalPrice()

    this.anti()

  },

  anti() {
    if (this.timeOut) {
      clearTimeout(this.timeOut)
    }
    this.timeOut = setTimeout(() => {
      appData.globalSyncCart()
    }, 1000)
  },
  //---------------/----------------------
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.getCartList()
  },

  //判断
  getCartList() {
    if (appData.globalData.cartList.length != 0) {
      this.getTotalPrice()
      this.setData({
        cartList: appData.globalData.cartList,
      })
    } else {
      this.setData({
        cartList: [],
        isSelectAll: false
      })
    }
  },

  //计算总价格
  getTotalPrice() {
    const toTalPrice = appData.globalData.cartList.filter(item => item.checked).reduce((preValue, item) => {
      return (preValue * 1) + ((item.price * item.count) * 1)
    }, 0)
    if (appData.globalData.cartList.length != 0) {
      this.setData({
        totalPrice: toTalPrice,
        isSelectAll: !(appData.globalData.cartList.filter(item => !item.checked).length),
        totalCounter: appData.globalData.cartList.filter(item => item.checked).length
      })
    }
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
    wx.setNavigationBarTitle({
      title: `购物车(${appData.globalData.cartList.length})`,
    })
    this.getCartList()
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