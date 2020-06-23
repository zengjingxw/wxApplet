// components/s-tab-control/s-tab-control.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    titles: {
      type: Array,
      value: []
    }
  },
  externalClasses: ['snas-css'],
  /**
   * 组件的初始数据
   */
  data: {
    currentIndex: 0
  },

  /**
   * 组件的方法列表
   */
  methods: {
    handleTabClick(e) {
      this.setData({
        currentIndex: e.currentTarget.dataset.index
      })
      this.triggerEvent("handleTabClcik", {
        index: this.data.currentIndex,
        title: this.data.titles[this.data.currentIndex]
      })
    },
    setCurrentIndex(index) {
      this.setData({
        currentIndex: index
      })
    }
  }
})