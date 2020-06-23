import { smileRequest } from './smile.js'

export function toOrder() {
  return smileRequest({
    url: 'toOrder.wx?sessionId=' + wx.getStorageSync("sessionId") + "&token=" + wx.getStorageSync("token")
  })
}

export function orderHandler(options) {
  return smileRequest({
    url: 'orderHandle.wx',
    data: {
      body: options.body,
      address: options.address,
      sign: options.sign
    },
    method: "POST"
  })
}

export function updOrder(options) {
  return smileRequest({
    url: 'updOrder.wx?sign=' + options.sign + "&orderId=" + options.orderId,
  })
}

export function returnMyOrderData() {
  return smileRequest({
    url: 'returnMyOrderData.wx'
    })
}