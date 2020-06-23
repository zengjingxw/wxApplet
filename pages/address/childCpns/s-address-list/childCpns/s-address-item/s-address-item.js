// pages/address/childCpns/s-address-list/childCpns/s-address-item/s-address-item.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    isDefaultSelect: {
      type: Boolean,
      value: false
    },
    addressItem: {
      type: Object,
      value: {}
    }
  },
  externalClasses: ['list-address-item'],
  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    //选择对应的地址
    handleSelectCurrent() {
      wx.setStorageSync("tmpAddress", this.properties.addressItem)
      wx.navigateTo({
        url: '/pages/order/order',
      })
    },

    //编辑地址
    handleToAddressEdit() {
      console.log("点击了地址编辑")
      wx.navigateTo({
        url: '/pages/saveaddress/saveaddress?obj=' + JSON.stringify(this.properties.addressItem) + "&sign=" + "edit",
      })
    }

  }
})
