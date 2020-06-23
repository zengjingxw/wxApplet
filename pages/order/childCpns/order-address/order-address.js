// pages/order/childCpns/order-address/order-address.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    orderAddress: {
      type: Object,
      value: {}
    }
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    handleToAddressLsit() {
      wx.navigateTo({
        url: '/pages/address/address',
      })
    }
  }
})
