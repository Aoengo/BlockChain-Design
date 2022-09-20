// pages/vote/vote.js
import {AxiosURL,User} from "../../javascript/data.js"

Page({

  /**
   * 页面的初始数据
   */
  currentTitle:null,
  pagesIndex:{
    unLog:0,
    proposal:1,
    voteDetail:2,
  },

  data: {
    ballotList: [],
    tableData: [],
    type:'',
    screenBtn: [{
      text: '确定',
      type: 'primary'
    },],
    showModalStatus: false,
    title:null,
    content:null,
    pageState:0,
    voteState:null,
  },
  buttontap() {
    let _this = this
    _this.setData({
      showModalStatus: false
    })
  },
  showMask:function(title,text) {
    let _this = this
    _this.setData({
      showModalStatus: true,
      title:title,
      content:text
    })
  },
  voteDetail:function(value){
    let _this = this
    _this.flashData(_this.pagesIndex.voteDetail)
    _this.displayTitle(value)
    _this.currentTitle = value.currentTarget.id
    console.log(value.currentTarget.id)
  },
  goback:function(){
    let _this = this
    _this.flashData(_this.pagesIndex.proposal);
  },
  display:function() {
    let _this = this
    wx.request({
      method: "post",
      url: AxiosURL + "/user_space/vote_proposals",
      success: (res) => {
        let newtable = []
        for(let i = 0;i < res.data.value1.length;i++){
          let table = {}
          table.title = res.data.value1[i]
          table.time = _this.dateProcess(res.data.value2[i])
          newtable.splice(i,1,table)
        }
        _this.setData({
          tableData:newtable
        })
        console.log(res)
      },
      fail: (res) => {},
      complete: (res) => {},
    })
  },

  displayTitle:function(value) {
    let _this = this
    let  data = {title:value.currentTarget.id};
    // console.log(data)
    //发送 POST 请求
    wx.request({
      method: "post",
      url: AxiosURL + "/user_space/vote",
      data,
      success: (res) => {
      let newtable = []
      console.log(res)
      for(let i = 0; i< res.data.length;i++){
        let table = {}
        table.name = res.data[i]
        newtable.splice(i,1,table)
      }
      _this.setData({
        ballotList:newtable
      })
      _this.getVoteState()
    }
  })
  },

  getVoteState:function() {
    let _this = this
    let  data = {title:_this.currentTitle,id:User.ID};
    // console.log(data)
    //发送 POST 请求
    wx.request({
      method: "post",
      url: AxiosURL + "/user_space/user_vote_state",
      data,
      success: (res) => {
      console.log(res.data)
      if(res.data === true){
        _this.setData({
          voteState:true
        })
      }else{
        _this.setData({
          voteState:false
        })
      }
    }
    })
  },

  vote:function(value) {
    let _this = this
    console.log(value)
    let  data = {title:_this.currentTitle,name:value.currentTarget.id,id:User.ID};
    // console.log(data)
    //发送 POST 请求
    wx.request({
      method: "post",
      url: AxiosURL + "/user_space/vote_chose",
      data,
      success: (res) => {
      console.log(res.data)
      if(res.data !== 0xffff){
        _this.showMask("投票成功","")
      }else{
        _this.showMask("投票失败","每个项目每人只能投一次，同时您可能已经超时")
      }
    }
    })
  },
  flashData:function(which){
    let _this = this
    _this.setData({
      pageState:which
    })
  },
  checkState:function(){
    let _this = this
    if(User.state === true){
      _this.showMask("请先登录","")
      _this.flashData(_this.pagesIndex.unLog)
    }
    else{
      _this.flashData(_this.pagesIndex.proposal)
      _this.display()
    }
  },
  dateProcess:function(value){
    let date = new Date(parseInt(value));
    // console.log(date)
    let year = date.getFullYear();
    let month= date.getMonth()+1;
    month = month < 10 ? ('0' + month) : month;
    let day  = date.getDate();
    day = day < 10 ? ('0' + day) : day;
    var hours = date.getHours();
    hours = hours < 10 ? ('0' + hours) : hours;
    let date_res = year+"-"+month+"-"+day+"-"+hours;
    return date_res;
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