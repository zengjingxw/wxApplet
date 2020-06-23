// pages/detail/childCpns/s-detail-bottom/s-detail-bottom.js
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

  },

  /**
   * 组件的方法列表
   */
  methods: {
    onAddCart() {
      this.triggerEvent('addcart', {}, {})
    },
    toPay() {
      this.triggerEvent('toPay', {}, {})
    }
  }
})
