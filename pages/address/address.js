// pages/address/address.js
import {
    addressList
  } from '../../service/address.js'
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    addressList: []
  },

//=======================事件处理===============================

//获取地址
  _addressList() {
    addressList().then( res => {
      if(res.data.code == 1) {
        wx.hideLoading()

        this.setData({
          addressList: res.data.body == null ? [] : res.data.body
        })

      } 
      else if(res.data.code == -1) {
        app.clean()
      }
    })
  },

//========================/事件处理===================================

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this._addressList()
  },

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