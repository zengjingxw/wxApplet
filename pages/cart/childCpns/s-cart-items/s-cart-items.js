// pages/cart/childCpns/s-cart-items/s-cart-items.js
const appData = getApp()
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    goods: {
      type: Object,
      value: {}
    },
    index: {
      type: Number
    },
    showCheck: {
      type: Boolean,
      value: true
    }
  },

  externalClasses: ['order-prod-item'],

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    onCheckClick(e) {
      this.triggerEvent("onCheckClick", { 'id': this.data.goods.id, 'index': e.currentTarget.dataset.index}, {})
    }
  }
})
