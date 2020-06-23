// pages/order/childCpns/s-order-bootom-bar/s-order-bootom-bar.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    tatolPrice: {
      type: Number,
      value: 0
    },
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
    
    handleClickSub() {
      this.triggerEvent("orderSub", {}, {})
    }
  }
})
