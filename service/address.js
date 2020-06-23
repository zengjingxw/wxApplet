import { smileRequest } from './smile.js'

export function addressList() {
  return smileRequest({
    url: 'addressList.wx?sessionId=' + wx.getStorageSync("sessionId") + "&token=" + wx.getStorageSync("token")
  })
}

export function saveAddress(body) {
  return smileRequest({
    url: 'saveAddress.wx',
    method: "POST",
    data: body
  })
}

export function deleteAddress(addressId) {
  return smileRequest({
    url: 'deleteAddress.wx?addressId=' + addressId
  })
}