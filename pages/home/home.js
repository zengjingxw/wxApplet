// pages/home/home.js
import {
  getMutiData,
  getProduct,
} from '../../service/home.js'
import {
  types,
  BACK_TOP_POSITION
} from '../../service/constant.js'
Page({
  /**
   * 页面的初始数据
   */
  data: {
    titles: ['流行', '新款', '销量'],
    banners: [],
    recommend: [],
    goods: {
      'pop': {
        page: 0,
        lists: []
      },
      'new': {
        page: 0,
        lists: []
      },
      'sell': {
        page: 0,
        lists: []
      }
    },
    currentType: 'pop',
    isShowBack : false,
    isShowControl: false,
    showScrollTop: 0,
    tabControlTop: 0
  },
  handleTabClcik(e) {
    console.log(e)
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

    this._getMutiData()

    this._getProduct(types[0])
    this._getProduct(types[1])
    this._getProduct(types[2])
  },

  //------------发送网络请求-----------------
  _getMutiData() {
    getMutiData().then(res => {
      wx.hideLoading()
      this.setData({
        banners: res.data.data.banner.list,
        recommend: res.data.data.recommend.list
      })
    })
  },

  _getProduct(type) {
    const page = (this.data.goods[type].page + 1) * 1

    const itemList = `goods.${type}.lists`
    const itemPage = `goods.${type}.page`
    getProduct(type, page).then(res => {
      wx.hideLoading()
      const list = res.data.data.list
      const delegate = this.data.goods[type].lists.concat(list)
      this.setData({
        [itemList]: delegate,
        [itemPage]: page
      })
    })
  },
  //---------------------事件监听----------------
  handleTabClcik(e) {
    const index = e.detail.index
    this.setData({
      currentType: types[index]
    })

    this.selectComponent("#s-controller").setCurrentIndex(index)
    this.selectComponent("#s-controller1").setCurrentIndex(index)
  },

  loadImg() {
    wx.createSelectorQuery().select("#s-controller1").boundingClientRect( (rect) => {
      this.setData({
        tabControlTop: rect.top
      })
    }).exec()
  },

  onBackTop() {
    // wx.pageScrollTo({
    //   scrollTop: 0,
    //   duration: 0
    // })
    this.setData({
      showScrollTop: 0
    })
  },

  scrollPosition(e) {
    const position = e.detail.scrollTop
    if(this.data.isShowBack != ( position >= BACK_TOP_POSITION)) {
      this.setData({
        isShowBack: position >= BACK_TOP_POSITION
      })
    }
    if(this.isShowControl != (position >=this.data.tabControlTop)) {
      this.setData({
        isShowControl: position >= this.data.tabControlTop
      })
    }
  },

  loadMore() {
    this._getProduct(this.data.currentType)
  },
  //----------------------/事件监听----------------

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