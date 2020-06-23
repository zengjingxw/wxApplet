// pages/address/childCpns/s-address-list/s-address-list.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    addressList: {
      type: Array,
      value: []
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

    //跳转地址添加
    handleSaveAddress() {
      wx.navigateTo({
        url: '/pages/saveaddress/saveaddress',
      })
    }

  }
})
