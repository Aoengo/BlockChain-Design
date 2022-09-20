<template>
<el-row>
  <el-col :span="24"><div class="content-position">
    <div class="text_style">
      创建投票项目
    </div>
    <table class="table-position" style="" >
      <tr>
        <td style="width: 2px;"></td>
        <td style="width: 197px;">请输入投票项目标题:</td>
        <td>
          <el-input style="width: 480px" v-model="title" size="mini" placeholder="Input the Vote Title..."></el-input>
        </td>
      </tr>
    </table>
    <table class="table-position" style="" >
      <tr>
        <td style="width: 2px;"></td>
        <td style="width: 197px;">请输入截止时间:</td>
        <td>
          <div class="block">
            <span class="demonstration"></span>
            <el-date-picker
                v-model="deadlineDate"
                type="date"
                size="mini"
                placeholder="选择日期">
            </el-date-picker>
          </div>
        </td>
        <td>
          <el-time-select
              v-model="deadlineTime"
              size="mini"
              :picker-options="{
                start: '00:00',
                step: '01:00',
                end: '23:00'
              }"
              placeholder="选择时间">
          </el-time-select>
        </td>
      </tr>
    </table>
      <el-table
        :data="VoteInfo"
        style="width: 100%"
        max-height="250"
        :header-cell-style="{
            background: '#96CDCD',
            color: '#333',
            fontWeight: '600',
            fontSize: '14px',
        }">
      <el-table-column
          fixed
          prop="name"
          label="项目"
          width="200">
      </el-table-column>
      <el-table-column
          fixed="right">
        <template slot="header" slot-scope="{}">
          <el-input class="input-style"
              v-model="new_name"
              size="mini"
              placeholder="输添加参选内容"/>
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          width="80">
        <template slot="header" slot-scope="{}">
          <el-button
              size="mini"
              @click.native.prevent="createRow(new_name)">New</el-button>
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          width="80">
        <template slot="header" slot-scope="{}">
          <el-button
              size="mini"
              type="success"
              @click.native.prevent="saveRow()">Save</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div></el-col>
</el-row>
</template>

<script>
// import {Create} from "../javascript/createVote";
import {AxiosURL, MRoute, User} from "@/javascript/data";

export default {
  name: "CreateVote",
  data(){
    return {
      VoteInfo:[],
      new_name:'',
      title:null,
      deadlineDate:null,
      deadlineTime:null
    }
  },
  methods: {
    checkTable(){
      let _this = this
      if(_this.VoteInfo[0].name == null){
        _this.VoteInfo.pop()
      }
    },
    createRow(new_name) {
      let _this = this
      let table = {}
      table.name = new_name
      //true 有 false 无
      if(!_this.VoteInfo.some(item => {return item.name === new_name}) && new_name !== ''){
        _this.new_name = ''
        _this.VoteInfo.push(table);
      }
      console.log(table)
      console.log(_this.VoteInfo)
    },
    saveRow(){
      let _this = this
      let time = _this.dateProcess(_this.deadlineDate,_this.deadlineTime)
      let data = {ID:User.ID,Title:_this.title,VoteInfo:_this.VoteInfo,Time:time};
      // console.log(time)
      this.axios({
        method: "post",
        url: AxiosURL + "/user_space/vote_create",
        data,
        // headers:
        // {
        //  'Content-Type': 'application/json'
        // }
      }).then((res)=>{
        if(res.data === true){
          _this.infoAlert('','信息保存成功')
          _this.$router.push(MRoute.user.initial)
        }else{
          _this.infoAlert('','信息保存失败')
        }
        // console.log(res)
      })
    },
    dateProcess(date,time){
      // console.log(date)
      let year = date.getFullYear();
      let month= date.getMonth()+1;
      month = month < 10 ? ('0' + month) : month;
      let day  = date.getDate();
      day = day < 10 ? ('0' + day) : day;
      let hours = time.slice(0,-3)
      return year + "-" + month + "-" + day + "-" + hours
    },
    infoAlert(message,title){
      let _this = this
      _this.$info.alert(message, title, {
        confirmButtonText: '确定',
      });
    },
  },
  mounted() {
    //检查ID
    // let _this = this
  }
}
</script>

<style scoped>

.text_style{
  text-align:center;
  background: #96CDCD;
  width:700px;
  font-size: 25px;
  font-family: 微软雅黑;
}

.input-style{
  width: 300px;
}

.table-position{
  margin:0 auto;
  width:700px;
  min-height: 36px;
  background-color:#96CDCD
}
.content-position{
  margin:0 auto;
  width:700px;
  min-height: 36px;
}
</style>