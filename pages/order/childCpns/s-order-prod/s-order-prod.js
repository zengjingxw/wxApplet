// pages/order/childCpns/s-order-prod/s-order-prod.js
import {
    formatTime
  } from "../../../../utils/util.js"
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    orderList: {
      type: Array,
      vlaue: []
    },
    method: {
      type: String,
      value: "顺丰快递"
    },
    ofDateinfo: {
      type: Date,
      value: new Date()
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    dateinfo: ""
  },

  /**
   * 组件的方法列表
   */
  methods: {

  },

  lifetimes: {
    attached: function () {
      const fromtDate = formatTime(new Date())
      this.setData({
        dateinfo: fromtDate
      })
    },
    detached: function () {

    },
  }, 
})
