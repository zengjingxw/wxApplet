// pages/detail/detail.js
import
  { 
    getDetaill,
    getRecommends,
    GoodsBaseInfo,
    ShopInfo,
    ParamInfo
  } from '../../service/detail.js'
import {
  BACK_TOP_POSITION
} from '../../service/constant.js'

const appData = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    goodsID: '',
    detaBanner: [],
    baseGoodsInfro: {},
    shopInfo: {},
    detailInfo: {},
    paramInfo: {},
    commnet: {},
    detaRecommentd: {},
    isShowBack: false,
    showScrollTop: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    this._getGoods(options.goodsId)
    // this._getGoods(this.data.goodsID)

    this._getDetaill()

    this._getRecommends()


  },

//---------------网络请求-------------------
  _getGoods(goodId) {
    this.setData({
      goodsID: goodId
    })
  },

  _getDetaill() {
    getDetaill(this.data.goodsID).then( res => {
      wx.hideLoading()
      const detaData = res.data.result

      const topImg = detaData.itemInfo.topImages

      const baseGoodsInfo = new GoodsBaseInfo(detaData.itemInfo, detaData.columns, detaData.shopInfo.services)

      const shopInfo = new ShopInfo(detaData.shopInfo);

      const detailInfo = detaData.detailInfo;

      const paramInfo = new ParamInfo(detaData.itemParams.info, detaData.itemParams.rule)

      let commentInfo = {}
      if(detaData.rate && detaData.rate.cRate > 0) {
        commentInfo = detaData.rate.list[0]
      }

      this.setData({
        detaBanner: topImg,
        baseGoodsInfro: baseGoodsInfo,
        shopInfo: shopInfo,
        detailInfo: detailInfo,
        paramInfo: paramInfo,
        commnet: commentInfo,
      })
    })
  },

  _getRecommends() {
    getRecommends().then(res => {
      wx.hideLoading()
      this.setData({
        detaRecommentd: res.data.data.list
      })
    })
  },
//---------------/-------------------

//-------------------事件监听------------------
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
    if (this.data.isShowBack != (position >= BACK_TOP_POSITION)) {
      this.setData({
        isShowBack: position >= BACK_TOP_POSITION
      })
    }
  },

  //添加购物车
  addcart() {
    const obj = {}
    obj.id = this.data.goodsID
    obj.imageURL = this.data.detaBanner[0]
    obj.title = this.data.baseGoodsInfro.title
    obj.desc = this.data.baseGoodsInfro.desc
    obj.price = this.data.baseGoodsInfro.realPrice

    appData.addToCart(obj)

    wx.showToast({
      title: '添加成功'
    })
  },
  //直接购买
  handlePay() {
    if(!wx.getStorageSync("token")) {
      wx.showModal({
        title: '提示',
        content: '请先登录',
      })
      return
    }
    const obj = {}
    obj.id = this.data.goodsID
    obj.imageURL = this.data.detaBanner[0]
    obj.title = this.data.baseGoodsInfro.title
    obj.desc = this.data.baseGoodsInfro.desc
    obj.price = this.data.baseGoodsInfro.realPrice
    obj.count = 1
    const paramObj = JSON.stringify(obj)
    wx.navigateTo({
      url: '/pages/order/order?obj=' + paramObj +"&sign=" + "one",
    })
  },
//--------------/-------------------
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})