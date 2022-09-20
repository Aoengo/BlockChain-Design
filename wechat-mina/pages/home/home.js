// pages/home/home.js
import {AxiosURL,User} from "../../javascript/data.js"
import {infoAlert} from "../../javascript/tool.js"
// import {Component} from "../../javascript/hfdialog.js"
Page({

  pagesIndex:{
    signin:0,
    signup:1,
    none:2,
  },
  /**
   * 页面的初始数据
   */
  options: {
    addGlobalClass: true
  },
  data: {
    userID:null,
    userPassword:null,

    newUser:{
      userID:null,
      userPassword:null,
      confirmPassword:null,
    },
    screenBtn: [{
      text: '确定',
      type: 'primary'
    },],
    showModalStatus: false,
    title:null,
    content:null,
    pageState:0,
  },
  
  buttontap(e) {
    let _this = this
    _this.setData({
      showModalStatus: false
    })
    // console.log(e)
  },
  // 获取输入框参数
  newUserID:function(input_value){
    this.data.newUser.userID = input_value.detail.value
  },
  newUserPassword:function(input_value){
    this.data.newUser.userPassword= input_value.detail.value
  },
  newConfirmPassword:function(input_value){
    this.data.newUser.confirmPassword = input_value.detail.value
  },
  getUserID:function(input_value){
    this.data.userID = input_value.detail.value
  },
  getPassword:function(input_value){
    this.data.userPassword = input_value.detail.value
  },
  // 刷新数据，等于刷新页面
  flashData:function(which){
    let _this = this
    _this.setData({
      pageState:which
    })
  },
  //弹窗相关函数
  showMask:function(title,text) {
    let _this = this
    _this.setData({
      showModalStatus: true,
      title:title,
      content:text
    })
  },
  // 用户API登录
  signIn:function(){
    let _this = this
    let  data = {username:_this.data.userID,password:_this.data.userPassword};
    wx.request({  
      url: AxiosURL + "/sign_in",  
      data,  
      method:'POST',  
      success: function (res) {  
        if(res.data === true && User.state === true){
          console.log("login success")
          User.ID = _this.data.userID
          User.password = _this.data.userPassword
          User.state = false
          _this.showMask("登录成功","")
          _this.flashData(_this.pagesIndex.none);
        }
        else if (res.data === true && User.state === false){
          _this.showMask("登录失败","您已登录")
        }
        else if(res.data === false){
          _this.showMask("登录失败","请重试")
        }
      }   
    })   
  },
  signUp:function(){
    let _this = this
    _this.flashData(_this.pagesIndex.signup);
  },

  register:function(){
    let _this = this
    if(_this.data.newUser.userPassword !== _this.data.newUser.confirmPassword){
      _this.showMask("密码错误","请修改密码后重试")
    }else {
      let  data = {username:_this.data.newUser.userID,password:_this.data.newUser.userPassword};
      //发送 POST 请求
      wx.request({
        method: "post",
        url: AxiosURL + "/sign_up",
        data,
        success: function(res){
        if(res.data === true){
          console.log("register success")
          _this.showMask("注册成功","")
          User.ID = _this.data.newUser.userID
          User.password = _this.data.newUser.userPassword
          User.state = true
          _this.flashData(_this.pagesIndex.signin);
        }
        else{
          _this.showMask("注册失败","账户已经存在")
        }
      }
    });
    }
  },
  goback:function(){
    let _this = this
    _this.flashData(_this.pagesIndex.signin);
  },

  checkState:function(){
    let _this = this
    if(User.state === true && _this.data.pageState !== _this.pagesIndex.signin){
      _this.showMask("请先登录","")
      _this.flashData(_this.pagesIndex.signin)
    }
   
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
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
    this.checkState()
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