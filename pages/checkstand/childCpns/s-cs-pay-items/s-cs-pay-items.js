// pages/checkstand/childCpns/s-cs-payitems/s-cs-payitems.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    payDataList: {
      type: Array,
      value: []
    }

  },

  /**
   * 组件的初始数据
   */
  data: {
    check: 'wx'
  },

  /**
   * 组件的方法列表
   */
  methods: {
    handlePaySwicth(e) {
      this.setData({
        check: e.detail.check
      })

      this.triggerEvent("handleBarSwicth", {
        "check": e.detail.check
      }, {})

    }
  }
})
