import { smileRequest } from './smile.js'

export function checkToken() {
  return smileRequest({
    url: 'checkUserToken.wx?sessionId=' + wx.getStorageSync("sessionId") + "&token=" + wx.getStorageSync("token")
  })
}

export function syncCart(body) {
  return smileRequest({
    showLoading: false,
    url: '/syncCart.wx',
    method: "POST",
    data: body
  })
}