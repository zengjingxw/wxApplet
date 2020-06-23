// pages/category/childCpns/s-cate-menu/s-cate-menu.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    cataMenuList: {
      type: Array,
      value: []
    }
  },

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
    menuClick(e) {
      this.setData({
        currentIndex: e.currentTarget.dataset.index
      })
      this.triggerEvent("menuItemClick", { 'index': e.currentTarget.dataset.index})
    }
  }
})
