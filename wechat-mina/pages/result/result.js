// pages/result/result.js
import {AxiosURL,User} from "../../javascript/data.js"
Page({

  pagesIndex:{
    unLog:0,
    proposal:1,
    voteDetail:2,
  },
  /**
   * 页面的初始数据
   */
  data: {
    ProposalInfo: [],
    voteWho:null,
    tableData: [
      {
        rank: '',
        name: '',
        poll: '',
      }
    ],
    pageState:0,

    screenBtn: [{
      text: '确定',
      type: 'primary'
    },],
    showModalStatus: false,
    title:null,
    content:null,
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
  goback:function(){
    let _this = this
    _this.flashData(_this.pagesIndex.proposal);
  },

  _delete(value){
    let _this = this
    let  data = {id:User.ID,title:value.currentTarget.id};
    // console.log(data)
    //发送 POST 请求
    wx.request({
      method: "post",
      url: AxiosURL + "/user_space/vote_result_delete",
      data,
      success: (res) => {
      console.log(res.data)
      if(res.data === 0xfffe){
        _this.showMask("删除成功","");
      }else {
        _this.showMask("删除失败","");
      }
    }
    })
    _this.flashData(_this.pagesIndex.proposal);
  },
  
  voteDetail:function(value){
    let _this = this
    _this.flashData(_this.pagesIndex.voteDetail)
    _this.displayDetail(value)
    // console.log(_this)
    _this.currentTitle = value.currentTarget.id
    // console.log(value.currentTarget.id)
  },

  display:function(){
    let _this = this
    let  data = {id:User.ID};
    // console.log(data)
    //发送 POST 请求
    wx.request({
      method: "post",
      url: AxiosURL + "/user_space/vote_result",
      data,
      success: (res) => {
      let newtable = []
      // console.log(res.data)
      for(let i = 0; i<res.data.length;i++){
        let table={}
        table.voteTitle= res.data[i].title;
        table.voteBallot= res.data[i].ballot;
        if(res.data[i].state === -0xfffc){
          table.voteState="正常"
        }else{
          table.voteState="异常"
        }
        newtable.splice(i,1,table)
      }
      _this.setData({
        ProposalInfo:newtable
      })
      console.log(_this.data.ProposalInfo)
    }
    })
  },
  displayDetail:function(value){
    let _this = this
    let  data = {id:User.ID,title:value.currentTarget.id};
    // console.log("value:",data.title)
    wx.request({
      method: "post",
      url: AxiosURL + "/user_space/vote_result_check",
      data,
    success: (res) => {
      let newtable = []
      // console.log(res)
      for(let i = 0; i<res.data.proposal.length;i++){
        let table={}
        table.rank= res.data.proposal[i].rank+1;
        table.name= res.data.proposal[i].ballot;
        table.poll= res.data.proposal[i].vote_count;
        newtable.splice(i,1,table)
      }
      _this.setData({
        tableData:newtable,
        voteWho:res.data.voteWho
      })
      console.log(_this.data)
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