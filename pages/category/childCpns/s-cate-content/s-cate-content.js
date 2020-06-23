// pages/category/childCpns/s-cate-content/s-cate-content.js
import {
  types
} from '../../../../service/constant.js'
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    subcategories:{
      type: Array,
      value: []
    },
    categoryDetail: {
      type: Object,
      value: {}
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    title: ['综合', '新品', '销量'],
    currentType: 'pop',
  },

  /**
   * 组件的方法列表
   */
  methods: {
    handleTabClcik(e) {
      const type = types[e.detail.index]
      this.setData({
        currentType: type
      })
    }
  }
})
