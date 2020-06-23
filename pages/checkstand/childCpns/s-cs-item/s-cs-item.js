// pages/checkstand/childCpns/s-cs-payitem/s-cs-payitem.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    payWay: {
      type: Object,
      value: {}
    },
    check: {
      type: String,
      value: ''
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
    handleIcon(e) {
      this.triggerEvent("handlePaySwicth", {
        "check": e.target.dataset.sign
      }, {})
    },
  }
})