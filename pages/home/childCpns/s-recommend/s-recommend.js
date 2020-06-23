// pages/home/childCpns/s-recommend/s-recommend.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    recommendLists: {
      type: Array,
      vaue: []
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    isLoadImg: false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    imageLoad() {
      if(!this.data.isLoadImg) {
        this.data.isLoadImg = true
        this.triggerEvent("loadImg")
      }
    },

  }
})
