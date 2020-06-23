// pages/profile/childCpns/s-list-view/s-list-view.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    infos: {
      type: Array
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
    handleMyOrder() {
      if(wx.getStorageSync("token")) {
        wx.navigateTo({
          url: '/pages/myorder/myorder',
        })
      } else {
        wx.showModal({
          title: '提示',
          content: '请先登录',
        })
      }
    }
  }
})
