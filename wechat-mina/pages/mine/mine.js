// pages/mine/mine.js
import {AxiosURL,User,UserDate} from "../../javascript/data.js"
Page({

  pagesIndex:{
    unLog:0,
    main:1,
    ownProposal:2,
    ownProposalDetail:3,
    newProposal:4,
  },
  /** postman
   * 页面的初始数据
   */
  data: {
    ID:null,
    ProposalInfo:[],
    BallotInfo:[],

    VoteInfo:[],
    new_name:null,
    ProposalTitle:null,
    
    time: null,
    multiArray: [UserDate.years, UserDate.months, UserDate.days, UserDate.hours],
    multiIndex: [0, 0, 0, 0],
    choose_year: null,

    pageState:0,

    screenBtn: [{
      text: '确定',
      type: 'primary'
    },],
    showModalStatus: false,
    title:null,
    content:null,
  },
  
  buttontap(where) {
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
    _this.flashData(_this.pagesIndex.main);
  },
  getID:function(){
    let _this = this
    _this.setData({
      ID:User.ID
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
      _this.getID()
      _this.flashData(_this.pagesIndex.main)
      // _this.display()
    }
  },
  logOut:function(){
    let _this = this
    User.ID = null
    User.password = null
    User.state = true
    _this.showMask("注销成功","")
  },
  ownProposal:function(){
    let _this = this
    _this.flashData(_this.pagesIndex.ownProposal)
    _this.getProposalInfo()
  },
  newProposalTitle:function(input_value){
    this.data.ProposalTitle = input_value.detail.value
  },
  newBallotName:function(input_value){
    let _this = this
    if(input_value.detail.value !== null){
      _this.data.new_name = input_value.detail.value
    }
  },
  addBallotName:function(){
    let _this = this
    let table = {}
    if(!_this.data.VoteInfo.some(item => {return item.name === _this.data.new_name}) && _this.data.new_name!==null){
      table.name = _this.data.new_name
      _this.data.VoteInfo.push(table)
      _this.setData({
        new_name:null,
        VoteInfo:_this.data.VoteInfo
      })
    }
  },
  saveProposal:function(){
    let _this = this
    _this.sendFullInfo()
  },
  sendFullInfo(){
    let _this = this
    let data = {ID:User.ID,Title:_this.data.ProposalTitle,VoteInfo:_this.data.VoteInfo,Time:_this.data.time};
      //发送 POST 请求
      wx.request({
        method: "post",
        url: AxiosURL + "/user_space/vote_create",
        data,
        success: function (res) {  
          console.log(res)
        if(res.data === true){
          _this.showMask("保存信息成功","")
          _this.setData({
            VoteInfo:[]
          })
        }else{
          _this.showMask("信息保存失败","标题可能已经存在")
        }
        // console.log(res)
      }
      })
  },
  newProposal:function(){
    let _this = this
    _this.flashData(_this.pagesIndex.newProposal)
  },

  getProposalInfo:function(){
    let _this = this
    let  data = {ID:User.ID};
    wx.request({
      method: "post",
      url: AxiosURL + "/user_space/vote_info",
      data,
      // headers:
      // {
      //   'Content-Type': 'application/json'
      // },
      success: function (res) {  
        let newtable = []
        for(let i = 0;i<res.data.length;i++){
          let table = {}
          table.voteTitle= res.data[i].title;
          table.voterNums= res.data[i].nums;
          newtable.splice(i,1,table)
        }
        _this.setData({
          ProposalInfo:newtable
        })
        console.log(_this.data.ProposalInfo)
      }
    })
  },

  voteDetail:function(value){
    let _this = this
    _this.flashData(_this.pagesIndex.ownProposalDetail)
    _this.displayDetail(value)
    _this.currentTitle = value.currentTarget.id
    // console.log(value.currentTarget.id)
  },
  displayDetail:function(value){
    let _this = this
    let  data = {title:value.currentTarget.id};
    wx.request({
      method: "post",
      url: AxiosURL + "/user_space/vote_detail_info",
      data,
      success: function (res) {  
      let newtable = []
      // _this.proposalTitle = CheckParameter.Title;
      for(let i = 0;i<res.data.length;i++){
        let table = {}
        table.ballotRank= res.data[i].rank;
        table.voteBallot= res.data[i].title;
        table.ticketsNums= res.data[i].vote_count;
        newtable.splice(i,1,table)
      }
      _this.setData({
        BallotInfo:newtable
      })
      console.log(_this.data.BallotInfo)
    }
    })
  },
  _delete:function(){
    let _this = this
    let  data = {title:_this.currentTitle,id:User.ID};
    wx.request({
      method: "post",
      url: AxiosURL + "/user_space/vote_reset",
      data,
      success: function (res) {  
      console.log(res)
      if(res.data === 1){
        _this.showMask("删除成功","")
      }
    }
    })
  },
  
  //获取时间日期
  bindMultiPickerChange: function(e) {
    // console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      multiIndex: e.detail.value
    })
    const index = this.data.multiIndex;
    const year = this.data.multiArray[0][index[0]];
    const month = this.data.multiArray[1][index[1]];
    const day = this.data.multiArray[2][index[2]];
    const hour = this.data.multiArray[3][index[3]];
    this.setData({
      time: year + '-' + month + '-' + day + '-' + hour 
    })
  },
  //监听picker的滚动事件
  bindMultiPickerColumnChange: function(e) {
    //获取年份
    if (e.detail.column == 0) {
      let choose_year = this.data.multiArray[e.detail.column][e.detail.value];
      console.log(choose_year);
      this.setData({
        choose_year
      })
    }
    if (e.detail.column == 1) {
      let num = parseInt(this.data.multiArray[e.detail.column][e.detail.value]);
      let temp = [];
      if (num == 1 || num == 3 || num == 5 || num == 7 || num == 8 || num == 10 || num == 12) { //判断31天的月份
        for (let i = 1; i <= 31; i++) {
          if (i < 10) {
            i = "0" + i;
          }
          temp.push("" + i);
        }
        this.setData({
          ['multiArray[2]']: temp
        });
      } else if (num == 4 || num == 6 || num == 9 || num == 11) { //判断30天的月份
        for (let i = 1; i <= 30; i++) {
          if (i < 10) {
            i = "0" + i;
          }
          temp.push("" + i);
        }
        this.setData({
          ['multiArray[2]']: temp
        });
      } else if (num == 2) { //判断2月份天数
        let year = parseInt(this.data.choose_year);
        console.log(year);
        if (((year % 400 == 0) || (year % 100 != 0)) && (year % 4 == 0)) {
          for (let i = 1; i <= 29; i++) {
            if (i < 10) {
              i = "0" + i;
            }
            temp.push("" + i);
          }
          this.setData({
            ['multiArray[2]']: temp
          });
        } else {
          for (let i = 1; i <= 28; i++) {
            if (i < 10) {
              i = "0" + i;
            }
            temp.push("" + i);
          }
          this.setData({
            ['multiArray[2]']: temp
          });
        }
      }
      console.log(this.data.multiArray[2]);
    }
    var data = {
      multiArray: this.data.multiArray,
      multiIndex: this.data.multiIndex
    };
    data.multiIndex[e.detail.column] = e.detail.value;
    this.setData(data);
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