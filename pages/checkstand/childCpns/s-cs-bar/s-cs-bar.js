// pages/checkstand/childCpns/s-cs-bar/s-cs-bar.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    data: {
      type: Object,
      value: {
        way: "微信支付",
        price: 1000
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    pview: false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    handlePay() {
      this.triggerEvent("handleOrder", {}, {})
    }
  }
})
