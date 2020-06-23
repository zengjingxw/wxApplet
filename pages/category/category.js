// pages/category/category.js

import {
  getCategory,
  getSubcategory,
  getCategoryDetail
} from '../../service/category.js'
import {
  types
} from '../../service/constant.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    categories: [],
    categoryData: {},
    currentIndex: 0,
  },


  // -------------网络请求------------
  _getCategory() {
    getCategory().then(res => {
      wx.hideLoading()
      const categories = res.data.data.category.list.filter(item => item.title !="内衣")
      const categoryData = {}
      for (let i = 0; i < categories.length; i++) {
        categoryData[i] = {
          subcategories: [],
          categoryDetail: {
            'pop': [],
            'new': [],
            'sell': []
          }
        }
      }
      this.setData({
        categories: categories,
        categoryData: categoryData
      })

      this._getSubcategory(0)

      this._getCategoryDetail(0)
    })
  },

  _getSubcategory(index) {
    const maitKey = this.data.categories[index].maitKey
    getSubcategory(maitKey).then(res => {
      wx.hideLoading()
      const tempCategoryData = this.data.categoryData
      tempCategoryData[index].subcategories = res.data.data.list
      this.setData({
        categoryData: tempCategoryData
      })
    })
  },

  _getCategoryDetail(index) {
    const miniWallkey = this.data.categories[index].miniWallkey

    this._getRealCategoryDetail(index, miniWallkey, types[0])
    this._getRealCategoryDetail(index, miniWallkey, types[1])
    this._getRealCategoryDetail(index, miniWallkey, types[2])
  },

  _getRealCategoryDetail(index, miniWallkey, type) {
    getCategoryDetail(miniWallkey, type).then(res => {
      wx.hideLoading()
      const categoryData = this.data.categoryData;
      categoryData[index].categoryDetail[type] = res
      this.setData({
        categoryData: categoryData
      })
    })
  },
  //-------------/--------------------- 

  //----------------事件监听------------
  menuItemClick(e) {
    const index = e.detail.index
    this.setData({
      currentIndex: index
    })
    this._getSubcategory(index)

    this._getCategoryDetail(index)
  },
  //--------------/----------------
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this._getCategory()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})