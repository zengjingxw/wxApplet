// pages/saveaddress/saveaddress.js
import {
  saveAddress,
  deleteAddress
} from '../../service/address.js'
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    region: ['--', '--', '--'],
    defaultAddressSelect: 0,
    checkBox: false,
    edit: {},
    editSign: false
  },

  //=================事件处理========================

  bindRegionChange(e) {
    this.setData({
      region: e.detail.value
    })
  },

  //保存地址
  saveHandleFrom(e) {
    let body = e.detail.value

    if (body.consignee == "") {
      this.hint('提示', '请输入收货人')
      return
    }

    if (body.phone == "") {
      this.hint('提示', '请输入电话号码')
      return
    }

    if (!(/^1[3456789]\d{9}$/.test(body.phone))) {
      this.hint('提示', '手机号码有误')
      return
    }

    if (this.data.region[0] == "--") {
      this.hint('提示', "请选择区域信息")
      return
    }

    if (body.detailedAddress == "") {
      this.hint('提示', "请输入详细地址")
      return
    }


    body.regional = this.data.region[0] + this.data.region[1] + this.data.region[2]
    body.editSign = this.data.region
    body.defaultAddress = this.data.defaultAddressSelect
    body.id = this.data.edit.id?this.data.edit.id:"sign"

    this._saveAddress(body,"dd");
  },
  //提示信息
  hint(title, content) {
    wx.showModal({
      title: title,
      content: content,
    })
  },

  //设置默认地址
  defaultChange(e) {
    if (e.detail.value) {
      this.data.defaultAddressSelect = 1
    } else {
      this.data.defaultAddressSelect = 0
    }
  },

  _saveAddress(body, sign) {
    saveAddress(body).then(res => {
      if (res.data.code == 1 && res.statusCode == 200) {
        wx.hideLoading()
        wx.setStorageSync("tmpAddress", res.data.body)
        if (sign == "noOrder") {

        } else {
          wx.navigateTo({
            url: '/pages/order/order',
          })
        }
      } else if (res == -1) {
        app.clean()
      }
    })
  },

  //地址删除
  handleDeleteAddress() {
    if(this.data.edit.id) {
      deleteAddress(this.data.edit.id).then(res => {
        if(res.data.code == 1) {
          wx.navigateTo({
            url: '/pages/address/address',
          })
        } else if(res.data.code == -1) {
          app.clean()
        }
      })
    }
  },


  //=================/事件处理======================
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if(options.sign == "edit") {
      let obj = JSON.parse(options.obj)
      console.log(obj)

      let tempData = {}
      tempData.id = obj.id
      tempData.phone = obj.phone
      tempData.consignee = obj.consignee
      tempData.detailedAddress = obj.detailedAddress
      
      console.log(tempData)
      this.setData({
        region: obj.editSign,
        checkBox: obj.defaultAddress == 0?false:true,
        edit: tempData,
        editSign: true
      })
    }
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